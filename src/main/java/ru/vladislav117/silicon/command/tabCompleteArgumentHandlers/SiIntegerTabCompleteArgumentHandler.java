package ru.vladislav117.silicon.command.tabCompleteArgumentHandlers;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import ru.vladislav117.silicon.command.SiCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Подстановка аргументов целочисленного типа.
 */
public class SiIntegerTabCompleteArgumentHandler implements SiCommand.Handler.TabCompleteArgumentHandler {
    public static final SiIntegerTabCompleteArgumentHandler any = new SiIntegerTabCompleteArgumentHandler(Type.ANY);
    public static final SiIntegerTabCompleteArgumentHandler onlyPositive = new SiIntegerTabCompleteArgumentHandler(Type.ONLY_POSITIVE);
    public static final SiIntegerTabCompleteArgumentHandler onlyNegative = new SiIntegerTabCompleteArgumentHandler(Type.ONLY_NEGATIVE);

    protected Type type;

    /**
     * Создание новой подстановки аргументов целочисленного типа.
     *
     * @param type Тип числа
     */
    public SiIntegerTabCompleteArgumentHandler(Type type) {
        this.type = type;
    }

    @Override
    public List<String> tabComplete(int argumentIndex, String argument, CommandSender sender, String alias, String[] args, Location location) {
        if (argument.equals("")) {
            if (type.equals(Type.ONLY_POSITIVE)) {
                return new ArrayList<>() {{
                    for (int d = 0; d < 10; d++) add(String.valueOf(d));
                }};
            }
            if (type.equals(Type.ONLY_NEGATIVE)) {
                return new ArrayList<>() {{
                    for (int d = 0; d > -10; d--) add(String.valueOf(d));
                }};
            }
            return new ArrayList<>() {{
                for (int d = 0; d < 10; d++) add(String.valueOf(d));
                for (int d = -1; d > -10; d--) add(String.valueOf(d));
            }};
        }
        int current;
        try {
            current = Integer.parseInt(argument);
        } catch (NumberFormatException exception) {
            return List.of("[!] Число содержит ошибку");
        }
        if (type.equals(Type.ONLY_POSITIVE) && current < 0) {
            return List.of("[!] Число должно быть больше нуля");
        }
        if (type.equals(Type.ONLY_NEGATIVE) && current > 0) {
            return List.of("[!] Число должно быть меньше нуля");
        }
        return new ArrayList<>() {{
            for (int d = 0; d < 10; d++) add(argument + d);
        }};
    }

    /**
     * Тип числа.
     */
    public enum Type {
        ANY,
        ONLY_POSITIVE,
        ONLY_NEGATIVE
    }
}

