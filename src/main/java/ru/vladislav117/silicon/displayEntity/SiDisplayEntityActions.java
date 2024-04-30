package ru.vladislav117.silicon.displayEntity;

/**
 * Взаимодействия DisplayEntity.
 */
public interface SiDisplayEntityActions {
    /**
     * Создание DisplayEntity.
     *
     * @param displayEntity DisplayEntity.
     */
    void spawn(SiDisplayEntity displayEntity);

    /**
     * Обновление DisplayEntity.
     *
     * @param displayEntity DisplayEntity.
     */
    void update(SiDisplayEntity displayEntity);
}
