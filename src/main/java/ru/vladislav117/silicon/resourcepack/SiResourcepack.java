package ru.vladislav117.silicon.resourcepack;

import ru.vladislav117.silicon.Silicon;
import ru.vladislav117.silicon.event.SiBuiltinEvents;
import ru.vladislav117.silicon.event.SiEvents;
import ru.vladislav117.silicon.file.SiFile;
import ru.vladislav117.silicon.node.SiNode;

import java.util.HashMap;

/**
 * Ресурспак.
 */
public class SiResourcepack {
    static HashMap<String, SiNode> groups = new HashMap<>();

    /**
     * Добавление группы данных в ресурспак.
     *
     * @param name Имя группы
     * @param node Узел
     */
    public static void addGroup(String name, SiNode node) {
        groups.put(name, node);
    }

    /**
     * Инициализация.
     */
    public static void init() {
        SiEvents.addHandler(SiBuiltinEvents.SecondaryLoadEndEvent.class, event -> {
            SiEvents.call(new SiBuiltinEvents.ResourcepackWriteStartEvent());
            SiNode resourcepack = SiNode.emptyMap();
            for (String group : groups.keySet()) resourcepack.set(group, groups.get(group));
            Silicon.getDirectory().getChild("resourcepack.json").writeNode(resourcepack);
        });
    }
}
