package ru.vladislav117.silicon.weapon.bullet;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.content.SiContent;
import ru.vladislav117.silicon.sound.SiSoundEmitter;

import java.util.HashMap;

/**
 * Тип снаряда.
 */
public class SiBulletType extends SiContent {
    protected int lifetime = 4 * 20;
    protected double speed = 1;
    protected SiBulletTypeActions actions = null;
    protected SiSoundEmitter spawnSound = null;

    /**
     * Создание нового типа снаряда.
     *
     * @param name Имя типа снаряда
     */
    public SiBulletType(String name) {
        super(name);
        SiBulletTypes.all.add(this);
    }

    /**
     * Получение взаимодействий снаряда.
     *
     * @return Взаимодействия снаряда или null.
     */
    @Nullable
    public SiBulletTypeActions getActions() {
        return actions;
    }

    /**
     * Создание снаряда.
     *
     * @param location  Позиция
     * @param direction Направление
     * @param owner     Владелец
     */
    public void spawn(Location location, Vector direction, String owner) {
        SiBulletTypes.bulletDisplayEntity.spawn(location, direction, new HashMap<>() {{
            put("bullet_type", name);
            put("lifetime", lifetime);
            put("speed", speed);
            put("owner", owner);
        }});
        if (spawnSound != null) {
            spawnSound.emit(location);
        }
    }
}
