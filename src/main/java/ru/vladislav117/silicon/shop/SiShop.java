package ru.vladislav117.silicon.shop;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.Silicon;
import ru.vladislav117.silicon.color.SiPalette;
import ru.vladislav117.silicon.command.SiCommand;
import ru.vladislav117.silicon.command.tabCompleteArgumentHandlers.SiIntegerTabCompleteArgumentHandler;
import ru.vladislav117.silicon.comparator.SiComparator;
import ru.vladislav117.silicon.content.SiContent;
import ru.vladislav117.silicon.economy.SiCurrency;
import ru.vladislav117.silicon.file.SiFile;
import ru.vladislav117.silicon.inventory.SiPlayerInventoryUtils;
import ru.vladislav117.silicon.item.SiItemStack;
import ru.vladislav117.silicon.menu.SiMenu;
import ru.vladislav117.silicon.menu.SiMenuElement;
import ru.vladislav117.silicon.menu.SiMenus;
import ru.vladislav117.silicon.node.SiNode;
import ru.vladislav117.silicon.permission.SiPermission;
import ru.vladislav117.silicon.text.SiText;
import ru.vladislav117.silicon.text.SiTextLike;
import ru.vladislav117.silicon.text.pattern.feature.SiFeaturePattern;
import ru.vladislav117.silicon.text.pattern.feature.SiFeaturePatternStyles;
import ru.vladislav117.silicon.world.SiWorld;
import ru.vladislav117.silicon.world.SiWorlds;

import java.util.*;

/**
 * Магазин предметов.
 */
public class SiShop extends SiContent {
    protected SiFile directory;
    protected SiTextLike displayName;
    protected SiCurrency currency;
    protected int defaultMaxPositions = 5;
    protected boolean ignoreWorld = false;
    protected SiNode data = SiNode.emptyMap();

    protected HashMap<String, SiShopPosition> positions = new HashMap<>();
    protected ArrayList<SiShopPosition> orderedPositions = new ArrayList<>();
    protected HashMap<String, Integer> ownedPositions = new HashMap<>();

    /**
     * Создание нового магазина.
     *
     * @param name Имя магазина
     */
    public SiShop(String name, SiTextLike displayName, SiCurrency currency) {
        super(name);
        this.displayName = displayName;
        this.currency = currency;
        directory = SiShops.directory.getChild(name).mkdirs();
        for (SiNode node : directory.readAllJsonNodeFiles()) {
            SiShopPosition position = SiShopPosition.parseNode(node);
            addPosition(position);
        }
        SiShops.all.add(this);
        SiFile dataFile = directory.getChild(".shop");
        if (!dataFile.exists()) {
            dataFile.writeNode(data);
        }
        data = dataFile.readNode();
    }

    /**
     * Получение данных магазина.
     *
     * @return Данные магазина.
     */
    public SiNode getData() {
        return data;
    }

    /**
     * Сохранение данных магазина.
     *
     * @return Этот же магазин.
     */
    public SiShop saveData() {
        directory.getChild(".shop").writeNode(data);
        return this;
    }

    /**
     * Добавление позиции в магазин.
     *
     * @param position Позиция
     */
    protected void addPosition(SiShopPosition position) {
        positions.put(position.getUuid(), position);
        orderedPositions.add(position);
        orderedPositions.sort(Comparator.comparingLong(SiShopPosition::getDate));
        ownedPositions.put(position.getOwner(), ownedPositions.getOrDefault(position.getOwner(), 0) + 1);
    }

    /**
     * Сохранение позиции.
     *
     * @param uuid UUID позиции
     */
    protected void savePosition(String uuid) {
        SiFile file = directory.getChild(uuid + ".json");
        file.writeNode(positions.get(uuid).toNode());
    }

    /**
     * Добавление позиции от лица игрока.
     *
     * @param player    Игрок
     * @param itemStack Предмет
     * @param cost      Цена
     */
    public void addPositionByPlayer(Player player, ItemStack itemStack, int cost) {
        String uuid = UUID.randomUUID().toString();
        SiShopPosition position = new SiShopPosition(uuid, player.getName(), System.currentTimeMillis(), cost, itemStack, player.getWorld().getName());
        addPosition(position);
        savePosition(uuid);
    }

    /**
     * Удаление позиции.
     *
     * @param uuid UUID позиции
     */
    public void removePosition(String uuid) {
        orderedPositions.remove(positions.get(uuid));
        ownedPositions.put(positions.get(uuid).getOwner(), ownedPositions.get(positions.get(uuid).getOwner()) - 1);
        directory.getChild(uuid + ".json").delete();
        positions.remove(uuid);
    }

    /**
     * Создание меню.
     *
     * @return Меню магазина.
     */
    public SiMenu buildMenu() {
        ArrayList<SiMenuElement> elements = new ArrayList<>();
        for (SiShopPosition position : orderedPositions) {
            elements.add(new SiMenuElement("shop_" + name + "_buy_" + position.getUuid()) {{
                setItemStack(new SiItemStack(position.getItemStack().clone()) {{
                    ArrayList<SiTextLike> description = new ArrayList<>();
                    description.add(new SiFeaturePattern("Цена", position.getCost() + currency.getSymbol(), SiFeaturePatternStyles.Colon.greenYellow).build());
                    description.add(new SiFeaturePattern("Продавец", position.getOwner(), SiFeaturePatternStyles.Colon.greenYellow).build());
                    if (!ignoreWorld) {
                        SiWorld world = SiWorlds.all.get(position.getWorld());
                        SiTextLike worldName = world == null ? SiText.string(position.getWorld()) : world.getDisplayName();
                        description.add(new SiFeaturePattern(SiText.string("Место"), worldName, SiFeaturePatternStyles.Colon.greenYellow).build());
                    }
                    List<Component> lore = position.getItemStack().lore();
                    if (lore != null) {
                        for (Component component : lore) description.add(SiText.component(component));
                    }
                    setDescription(description);
                }});
                setClickHandler((player, item, event) -> {
                    if (!positions.containsKey(position.getUuid())) {
                        SiText.string("Эта позиция уже выкуплена", SiPalette.Interface.red).toMessageTask().addPlayer(player).send();
                        player.closeInventory();
                        return;
                    }
                    if (!ignoreWorld && !player.getWorld().getName().equals(position.getWorld())) {
                        SiText.string("Вы в другом месте", SiPalette.Interface.red).toMessageTask().addPlayer(player).send();
                        player.closeInventory();
                        return;
                    }
                    if (player.getName().equals(position.getOwner())) {
                        SiPlayerInventoryUtils.give(player, position.getItemStack().clone());
                        removePosition(position.getUuid());
                        SiText.string("Вы убрали позицию", SiPalette.Interface.green).toMessageTask().addPlayer(player).send();
                        openMenu(player);
                        return;
                    }
                    boolean bought = currency.makeTransaction(player.getName(), position.getOwner(), position.getCost());
                    if (!bought) {
                        SiText.string("У вас недостаточно средств", SiPalette.Interface.red).toMessageTask().addPlayer(player).send();
                        player.closeInventory();
                        return;
                    }
                    SiPlayerInventoryUtils.give(player, position.getItemStack().clone());
                    removePosition(position.getUuid());
                    SiText.string("Вы купили предмет", SiPalette.Interface.green).toMessageTask().addPlayer(player).send();
                    openMenu(player);
                });
            }});
        }
        return SiMenus.buildMenuCluster("shop_" + name, displayName, elements);
    }

    /**
     * Открыть меню магазина.
     *
     * @param player Игрок
     */
    public void openMenu(Player player) {
        buildMenu().open(player);
    }

    /**
     * Создание команды открытия магазина.
     *
     * @param name       Имя команды
     * @param aliases    Псевдонимы команды
     * @param permission Разрешение или null
     */
    public void createOpenCommand(String name, List<String> aliases, @Nullable SiPermission permission) {
        new SiCommand(name, aliases, permission, new SiCommand.Handler((sender, commandLabel, args) -> {
            if (!(sender instanceof Player player)) {
                SiText.string("Команду может отправлять только игрок", SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            openMenu(player);
            return true;
        }));
    }

    /**
     * Создание команды для выставления предмета на продажу.
     *
     * @param name       Имя команды
     * @param aliases    Псевдонимы команды
     * @param permission Разрешение или null
     */
    public void createSellCommand(String name, ArrayList<String> aliases, @Nullable SiPermission permission) {
        new SiCommand(name, aliases, permission, new SiCommand.Handler((sender, commandLabel, args) -> {
            if (!(sender instanceof Player player)) {
                SiText.string("Команду может отправлять только игрок", SiPalette.Interface.red).toMessageTask().addOtherReceiver(sender).send();
                return false;
            }
            if (args.length == 0) {
                SiText.string("Не задана цена предмета", SiPalette.Interface.red).toMessageTask().addPlayer(player).send();
                return false;
            }
            if (ownedPositions.getOrDefault(player.getName(), 0) >= defaultMaxPositions) {
                SiText.string("У вас выставлено максимальное число позиций", SiPalette.Interface.red).toMessageTask().addPlayer(player).send();
                return false;
            }
            ItemStack itemStack = player.getInventory().getItemInMainHand();
            if (SiComparator.isAir(itemStack)) {
                SiText.string("Вы должны держать в руке предмет", SiPalette.Interface.red).toMessageTask().addPlayer(player).send();
                return false;
            }
            int cost;
            try {
                cost = Integer.parseInt(args[0]);
            } catch (NumberFormatException exception) {
                SiText.string("Цена должна быть числом", SiPalette.Interface.red).toMessageTask().addPlayer(player).send();
                return false;
            }
            if (cost <= 0) {
                SiText.string("Цена должна быть больше нуля", SiPalette.Interface.red).toMessageTask().addPlayer(player).send();
                return false;
            }
            int maxPositions = data.getInteger(player.getName() + "_max_positions", defaultMaxPositions);
            if (ownedPositions.getOrDefault(player.getName(), 0) >= maxPositions) {
                SiText.string("У вас уже выставлено максимальное число позиций", SiPalette.Interface.red).toMessageTask().addPlayer(player).send();
                return false;
            }
            addPositionByPlayer(player, itemStack, cost);
            player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            SiText.string("Вы выставили предмет на продажу", SiPalette.Interface.green).toMessageTask().addPlayer(player).send();
            return true;
        }) {{
            addTabCompleteArgumentHandler(0, SiIntegerTabCompleteArgumentHandler.onlyPositive);
        }});
    }
}
