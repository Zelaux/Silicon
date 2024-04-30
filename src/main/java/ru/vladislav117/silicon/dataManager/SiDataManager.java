package ru.vladislav117.silicon.dataManager;

import ru.vladislav117.silicon.file.SiFile;
import ru.vladislav117.silicon.log.SiLog;
import ru.vladislav117.silicon.node.SiNode;

import java.util.HashMap;

/**
 * Менеджер данных.
 */
public abstract class SiDataManager {
    protected SiFile directory;
    protected HashMap<String, SiNode> data = new HashMap<>();

    /**
     * Создание нового менеджера данных.
     *
     * @param directory Директория
     */
    public SiDataManager(SiFile directory) {
        this.directory = directory;
        directory.mkdirs();
    }

    /**
     * Получение данных по умолчанию.
     *
     * @param name Название данных
     * @return Данные.
     */
    public abstract SiNode getDefault(String name);

    /**
     * Получение файла данных.
     *
     * @param name Название данных
     * @return Файл данных.
     */
    public SiFile getFile(String name) {
        return directory.getChild(name + ".json");
    }

    /**
     * Получение данных. Если данные не найдены, то они будут загружены с диска. Если на диске нет данных,
     * будут созданы данные по умолчанию.
     *
     * @param name Название данных
     * @return Данные.
     */
    public SiNode get(String name) {
        if (data.containsKey(name)) return data.get(name);
        SiFile file = getFile(name);
        if (!file.exists()) {
            SiNode defaultData = getDefault(name);
            data.put(name, defaultData);
            file.writeNode(defaultData);
            return defaultData;
        }
        SiNode node = file.readNode();
        data.put(name, node);
        return node;
    }

    /**
     * Сохранение данных.
     *
     * @param name Название данных
     * @return Этот же менеджер данных.
     */
    public SiDataManager save(String name) {
        if (!data.containsKey(name)) {
            SiLog.warning("Data not found: " + name);
            return this;
        }
        getFile(name).writeNode(data.get(name));
        return this;
    }
}
