package ru.vladislav117.silicon.node;

import com.google.gson.*;
import ru.vladislav117.silicon.function.SiHandlerFunction;
import ru.vladislav117.silicon.function.SiDoubleHandlerFunction;
import ru.vladislav117.silicon.function.SiFilterFunction;

import org.jetbrains.annotations.Nullable;
import ru.vladislav117.silicon.file.SiFile;
import ru.vladislav117.silicon.json.SiJson;

import java.util.*;

/**
 * Узел. Может быть как обычным значением (null, логическое, целочисленное, дробное, символ, строка),
 * так и структурой (список и таблица).
 */
public class SiNode {
    /**
     * Тип узла.
     */
    public enum Type {
        NULL,
        BOOLEAN,
        INTEGER,
        FLOAT,
        STRING,
        LIST,
        MAP
    }

    protected Type type = Type.NULL;
    protected Double number = null;
    protected String string = null;
    protected ArrayList<SiNode> list = null;
    protected HashMap<String, SiNode> map = null;

    /**
     * Создание нового узла. Принимает следующие типы данных: null, boolean, short, int,
     * long, float, double, char, String, List<?>, Map<?, ?>.
     *
     * @param value Значение узла
     */
    public SiNode(@Nullable Object value) {
        if (value == null) {
            type = Type.NULL;
        } else if (value instanceof Boolean b) {
            type = Type.BOOLEAN;
            number = b ? 1.0 : 0;
        } else if (value instanceof Short s) {
            type = Type.INTEGER;
            number = s.doubleValue();
        } else if (value instanceof Integer i) {
            type = Type.INTEGER;
            number = i.doubleValue();
        } else if (value instanceof Long l) {
            type = Type.INTEGER;
            number = l.doubleValue();
        } else if (value instanceof Float f) {
            type = (f == f.longValue()) ? Type.INTEGER : Type.FLOAT;
            number = f.doubleValue();
        } else if (value instanceof Double d) {
            type = (d == d.longValue()) ? Type.INTEGER : Type.FLOAT;
            number = d;
        } else if (value instanceof Character c) {
            type = Type.STRING;
            string = c.toString();
        } else if (value instanceof String s) {
            type = Type.STRING;
            string = s;
        } else if (value instanceof List<?> l) {
            type = Type.LIST;
            list = new ArrayList<>();
            for (Object element : l) list.add(new SiNode(element));
        } else if (value instanceof Map<?, ?> m) {
            type = Type.MAP;
            map = new HashMap<>();
            for (Object key : m.keySet()) map.put(key.toString(), new SiNode(m.get(key)));
        } else if (value instanceof SiNode n) {
            type = n.type;
            number = n.number;
            string = n.string;
            list = n.list;
            map = n.map;
        }
    }

    /**
     * Создание узла с пустой таблицей.
     *
     * @return Узел с пустой таблицей.
     */
    public static SiNode emptyMap() {
        return new SiNode(new HashMap<>());
    }

    /**
     * Создание узла с пустым списком.
     *
     * @return Узел с пустым списком.
     */
    public static SiNode emptyList() {
        return new SiNode(new ArrayList<>());
    }

    /**
     * Преобразование JsonElement в узел.
     *
     * @param jsonElement Элемент Json
     * @return Преобразованный узел.
     */
    public static SiNode parseJson(JsonElement jsonElement) {
        if (jsonElement.isJsonPrimitive()) {
            JsonPrimitive primitive = jsonElement.getAsJsonPrimitive();
            if (primitive.isBoolean()) return new SiNode(primitive.getAsBoolean());
            if (primitive.isNumber()) return new SiNode(primitive.getAsNumber().doubleValue());
            if (primitive.isString()) return new SiNode(primitive.getAsString());
        } else if (jsonElement.isJsonArray()) {
            JsonArray array = jsonElement.getAsJsonArray();
            ArrayList<SiNode> list = new ArrayList<>();
            for (JsonElement arrayElement : array) {
                list.add(parseJson(arrayElement));
            }
            return new SiNode(list);
        } else if (jsonElement.isJsonObject()) {
            JsonObject object = jsonElement.getAsJsonObject();
            HashMap<String, SiNode> map = new HashMap<>();
            for (String key : object.keySet()) {
                map.put(key, parseJson(object.get(key)));
            }
            return new SiNode(map);
        }
        return new SiNode(null);
    }

    /**
     * Преобразование узла в Json-элемент.
     *
     * @return Json-элемент из узла.
     */
    public JsonElement toJson() {
        if (type == Type.NULL) return JsonNull.INSTANCE;
        if (type == Type.BOOLEAN) return new JsonPrimitive(number != 0);
        if (type == Type.INTEGER || type == Type.FLOAT) return new JsonPrimitive(number);
        if (type == Type.STRING) return new JsonPrimitive(string);
        if (type == Type.LIST) {
            JsonArray array = new JsonArray();
            for (SiNode element : list) array.add(element.toJson());
            return array;
        }
        if (type == Type.MAP) {
            JsonObject object = new JsonObject();
            for (String key : map.keySet()) object.add(key, map.get(key).toJson());
            return object;
        }
        return JsonNull.INSTANCE;
    }

    /**
     * Преобразование Json-строки в узел.
     *
     * @param jsonString Json-строка
     * @return Преобразованный узел.
     */
    public static SiNode parseJsonString(String jsonString) {
        return parseJson(SiJson.parse(jsonString));
    }

    /**
     * Преобразование узла в Json-строку.
     *
     * @return Json-строка из узла.
     */
    public String toJsonString() {
        return toJson().toString();
    }

    /**
     * Преобразование Json-файла в узел.
     *
     * @param jsonFile Json-файла
     * @return Преобразованный узел.
     */
    public static SiNode readJson(SiFile jsonFile) {
        return parseJson(jsonFile.readJson());
    }

    /**
     * Запись узла в Json-файл.
     *
     * @param file Файл, в который будет записан узел
     * @param file Файл, в который будет записан узел
     * @return Этот же узел.
     */
    public SiNode writeJson(SiFile file) {
        file.writeNode(this);
        return this;
    }

    /**
     * Получение длины строки, размера списка или размера таблицы.
     *
     * @return Длина строки, размер списка, размер таблицы или -1, если у узла не может быть размера.
     */
    public int getSize() {
        if (type.equals(Type.STRING)) return string.length();
        if (type.equals(Type.LIST)) return list.size();
        if (type.equals(Type.MAP)) return map.size();
        return -1;
    }

    /**
     * Проверка, имеет ли узел индекс списка.
     *
     * @param index Индекс, который будет проверяться
     * @return true, если индекс есть. Если узел не является списком или индекс за границами списка, то false.
     */
    public boolean hasIndex(int index) {
        return type.equals(Type.LIST) && 0 <= index && index < list.size();
    }

    /**
     * Проверка, имеет ли узел дочерний узел.
     *
     * @param child Имя дочернего узла
     * @return true, если дочерний узел есть. Если узел не является таблицей или у него нет дочернего узла, то false.
     */
    public boolean hasChild(String child) {
        return type.equals(Type.MAP) && map.containsKey(child);
    }

    /**
     * Получение элемента списка.
     *
     * @param index Индекс элемента
     * @return Узел списка или null, если узел не является списком или индекс за границами списка.
     */
    @Nullable
    public SiNode get(int index) {
        if (!hasIndex(index)) return null;
        return list.get(index);
    }

    /**
     * Получение дочернего узла.
     *
     * @param child Имя дочернего узла
     * @return Дочерний узел или null, если узел не является таблицей или у него нет дочернего узла.
     */
    @Nullable
    public SiNode get(String child) {
        if (!hasChild(child)) return null;
        return map.get(child);
    }

    /**
     * Добавление элемента в список. Если узел не является списком, то ничего не произойдёт.
     *
     * @param node Элемент, который будет добавлен
     * @return Этот же узел.
     */
    public SiNode add(SiNode node) {
        if (!type.equals(Type.LIST)) return this;
        list.add(node);
        return this;
    }

    /**
     * Замена элемента списка в конкретном индексе на новый. Если узел не является списком, то ничего не произойдёт.
     * Если индекс меньше 0, то ничего не произойдёт.
     * Если индекс больше или равен размеру списка, то элемент будет добавлен в конец списка.
     *
     * @param index Индекс, в который будет установлен элемент
     * @param node  Элемент, который будет установлен
     * @return Этот же узел.
     */
    public SiNode set(int index, SiNode node) {
        if (!type.equals(Type.LIST)) return this;
        if (index < 0) return this;
        if (index < list.size()) {
            list.set(index, node);
        } else {
            list.add(node);
        }
        return this;
    }

    /**
     * Вставка элемента списка в конкретный индекс со сдвигом элементов справа.
     * Если узел не является списком, то ничего не произойдёт.
     * Если индекс меньше 0, то ничего не произойдёт.
     * Если индекс больше или равен размеру списка, то элемент будет добавлен в конец списка.
     *
     * @param index Индекс, в который будет вставлен элемент
     * @param node  Элемент, который будет вставлен
     */
    public SiNode insert(int index, SiNode node) {
        if (!type.equals(Type.LIST)) return this;
        if (index < 0) return this;
        if (index < list.size()) {
            list.add(index, node);
        } else {
            list.add(node);
        }
        return this;
    }

    /**
     * Установка дочернего узла с заданным именем. Если узел не является таблицей, то ничего не произойдёт.
     *
     * @param child Имя дочернего узла
     * @param node  Дочерний узел
     * @return Этот же узел.
     */
    public SiNode set(String child, SiNode node) {
        if (!type.equals(Type.MAP)) return this;
        map.put(child, node);
        return this;
    }

    /**
     * Удаление элемента списка из конкретного индекса.
     * Если узел не является списком, то ничего не произойдёт.
     * Если индекс за границей списка, то ничего не произойдёт.
     *
     * @param index Индекс, элемент по которому будет удалён
     * @return Этот же узел.
     */
    public SiNode remove(int index) {
        if (!hasIndex(index)) return this;
        list.remove(index);
        return this;
    }

    /**
     * Удаление элементов списка по фильтру.
     * Если узел не является списком, то ничего не произойдёт.
     *
     * @param filter Фильтр элементов
     * @return Этот же узел.
     */
    public SiNode removeIf(SiFilterFunction<? super SiNode> filter) {
        if (!type.equals(Type.LIST)) return this;
        list.removeIf(filter::isSuitable);
        return this;
    }

    /**
     * Удаление дочернего узла с заданным именем. Если узел не является таблицей, то ничего не произойдёт.
     * Ели дочернего узла с таким именем нет, то ничего не произойдёт.
     *
     * @param child Имя дочернего узла
     * @return Этот же узел.
     */
    public SiNode remove(String child) {
        if (!hasChild(child)) return this;
        map.remove(child);
        return this;
    }

    /**
     * Получение множества имён дочерних узлов.
     *
     * @return Множество дочерних узлов или пустое множество, если узел не является таблицей.
     */
    public Set<String> getKeySet() {
        if (!type.equals(Type.MAP)) return new HashSet<>();
        return map.keySet();
    }

    /**
     * Обработка каждого дочернего узла в списке.
     *
     * @param handler Обработчик узлов
     * @return Этот же узел.
     */
    public SiNode forEach(SiHandlerFunction<SiNode> handler) {
        if (type.equals(Type.LIST)) list.forEach(handler::handle);
        return this;
    }

    /**
     * Обработка каждого дочернего узла в таблице.
     *
     * @param handler Обработчик узлов
     * @return Этот же узел.
     */
    public SiNode forEach(SiDoubleHandlerFunction<String, SiNode> handler) {
        if (type.equals(Type.MAP)) map.forEach(handler::handle);
        return this;
    }

    @Override
    public String toString() {
        if (type == Type.NULL) return "null";
        if (type == Type.BOOLEAN) return number != 0 ? "true" : "false";
        if (type == Type.INTEGER) return String.valueOf(number.longValue());
        if (type == Type.FLOAT) return String.valueOf(number.doubleValue());
        if (type == Type.STRING) return string;
        if (type == Type.LIST) return list.toString();
        if (type == Type.MAP) return map.toString();
        return "unknown";
    }


    /**
     * Проверяет, является ли этот узел Boolean.
     *
     * @return true, если узел является Boolean, иначе false.
     */
    public boolean isBoolean() {
        return type.equals(Type.BOOLEAN);
    }

    /**
     * Проверяет, является ли узел по индексу Boolean.
     *
     * @param index Индекс узла списка
     * @return true, если узел по индексу является Boolean.false если не является, или если индекс за границей списка, или если узел не является списком.
     */
    public boolean isBoolean(int index) {
        return hasIndex(index) && get(index).isBoolean();
    }

    /**
     * Проверяет, является ли дочерний узел Boolean.
     *
     * @param child Имя дочернего узла
     * @return true, если дочерний узел является Boolean.false если не является, или если дочернего узла с таким именем нет, или если узел не является таблицей.
     */
    public boolean isBoolean(String child) {
        return hasChild(child) && get(child).isBoolean();
    }

    /**
     * Преобразует узел к boolean.
     * <p>
     * ВАЖНО! Если узел другого типа, то может возникнуть ошибка!
     *
     * @return boolean значение узла.
     */
    public boolean asBoolean() {
        return number != 0;
    }

    /**
     * Получение Boolean значения узла из списка.
     *
     * @param index Индекс узла в списке
     * @return Boolean значение или null, если узел другого типа, или если индекс за границей списка, или если узел не является списком.
     */
    @Nullable
    public Boolean getBoolean(int index) {
        SiNode node = get(index);
        if (node == null) return null;
        if (!node.isBoolean()) return null;
        return node.asBoolean();
    }

    /**
     * Получение Boolean значения узла из списка.
     *
     * @param index        Индекс узла в списке
     * @param defaultValue Значение по умолчанию
     * @return Boolean значение или значение по умолчанию, если узел другого типа, или если индекс за границей списка, или если узел не является списком.
     */
    public boolean getBoolean(int index, boolean defaultValue) {
        SiNode node = get(index);
        if (node == null) return defaultValue;
        if (!node.isBoolean()) return defaultValue;
        return node.asBoolean();
    }

    /**
     * Получение Boolean значения дочернего узла.
     *
     * @param child Имя дочернего узла
     * @return Boolean значение или null, если дочерний узел другого типа, или если дочернего узла с таким именем нет, или если узел не является таблицей.
     */
    @Nullable
    public Boolean getBoolean(String child) {
        SiNode node = get(child);
        if (node == null) return null;
        if (!node.isBoolean()) return null;
        return node.asBoolean();
    }

    /**
     * Получение Boolean значения дочернего узла.
     *
     * @param child        Имя дочернего узла
     * @param defaultValue Значение по умолчанию
     * @return Boolean значение или значение по умолчанию, если дочерний узел другого типа, или если дочернего узла с таким именем нет, или если узел не является таблицей.
     */
    public boolean getBoolean(String child, boolean defaultValue) {
        SiNode node = get(child);
        if (node == null) return defaultValue;
        if (!node.isBoolean()) return defaultValue;
        return node.asBoolean();
    }

    /**
     * Установка boolean значения в узел.
     *
     * @param value boolean значение
     * @return Этот же узел.
     */
    public SiNode setBoolean(boolean value) {
        type = Type.BOOLEAN;
        number = value ? 1.0 : 0;
        return this;
    }

    /**
     * Замена элемента списка в конкретном индексе на новый с boolean значением. Если узел не является списком, то ничего не произойдёт.
     * Если индекс меньше 0, то ничего не произойдёт.
     * Если индекс больше или равен размеру списка, то элемент будет добавлен в конец списка.
     *
     * @param index Индекс, в который будет установлен элемент
     * @param value Элемент, который будет установлен
     * @return Этот же узел.
     */
    public SiNode setBoolean(int index, boolean value) {
        set(index, new SiNode(value));
        return this;
    }

    /**
     * Вставка boolean элемента списка в конкретный индекс со сдвигом элементов справа.
     * Если узел не является списком, то ничего не произойдёт.
     * Если индекс меньше 0, то ничего не произойдёт.
     * Если индекс больше или равен размеру списка, то элемент будет добавлен в конец списка.
     *
     * @param index Индекс, в который будет вставлен элемент
     * @param value Элемент, который будет вставлен
     * @return Этот же узел.
     */
    public SiNode insertBoolean(int index, boolean value) {
        insert(index, new SiNode(value));
        return this;
    }

    /**
     * Добавление boolean элемента в список. Если узел не является списком, то ничего не произойдёт.
     *
     * @param value Элемент, который будет добавлен
     * @return Этот же узел.
     */
    public SiNode addBoolean(boolean value) {
        add(new SiNode(value));
        return this;
    }

    /**
     * Установка дочернего узла с заданным именем. Если узел не является таблицей, то ничего не произойдёт.
     *
     * @param child Имя дочернего узла
     * @param value boolean значение узла
     * @return Этот же узел.
     */
    public SiNode setBoolean(String child, boolean value) {
        set(child, new SiNode(value));
        return this;
    }

    /**
     * Проверяет, является ли этот узел Short.
     *
     * @return true, если узел является Short, иначе false.
     */
    public boolean isShort() {
        return type.equals(Type.INTEGER) && Short.MIN_VALUE <= number && number <= Short.MAX_VALUE;
    }

    /**
     * Проверяет, является ли узел по индексу Short.
     *
     * @param index Индекс узла списка
     * @return true, если узел по индексу является Short.false если не является, или если индекс за границей списка, или если узел не является списком.
     */
    public boolean isShort(int index) {
        return hasIndex(index) && get(index).isShort();
    }

    /**
     * Проверяет, является ли дочерний узел Short.
     *
     * @param child Имя дочернего узла
     * @return true, если дочерний узел является Short.false если не является, или если дочернего узла с таким именем нет, или если узел не является таблицей.
     */
    public boolean isShort(String child) {
        return hasChild(child) && get(child).isShort();
    }

    /**
     * Преобразует узел к short.
     * <p>
     * ВАЖНО! Если узел другого типа, то может возникнуть ошибка!
     *
     * @return short значение узла.
     */
    public short asShort() {
        return number.shortValue();
    }

    /**
     * Получение Short значения узла из списка.
     *
     * @param index Индекс узла в списке
     * @return Short значение или null, если узел другого типа, или если индекс за границей списка, или если узел не является списком.
     */
    @Nullable
    public Short getShort(int index) {
        SiNode node = get(index);
        if (node == null) return null;
        if (!node.isShort()) return null;
        return node.asShort();
    }

    /**
     * Получение Short значения узла из списка.
     *
     * @param index        Индекс узла в списке
     * @param defaultValue Значение по умолчанию
     * @return Short значение или значение по умолчанию, если узел другого типа, или если индекс за границей списка, или если узел не является списком.
     */
    public short getShort(int index, short defaultValue) {
        SiNode node = get(index);
        if (node == null) return defaultValue;
        if (!node.isShort()) return defaultValue;
        return node.asShort();
    }

    /**
     * Получение Short значения дочернего узла.
     *
     * @param child Имя дочернего узла
     * @return Short значение или null, если дочерний узел другого типа, или если дочернего узла с таким именем нет, или если узел не является таблицей.
     */
    @Nullable
    public Short getShort(String child) {
        SiNode node = get(child);
        if (node == null) return null;
        if (!node.isShort()) return null;
        return node.asShort();
    }

    /**
     * Получение Short значения дочернего узла.
     *
     * @param child        Имя дочернего узла
     * @param defaultValue Значение по умолчанию
     * @return Short значение или значение по умолчанию, если дочерний узел другого типа, или если дочернего узла с таким именем нет, или если узел не является таблицей.
     */
    public short getShort(String child, short defaultValue) {
        SiNode node = get(child);
        if (node == null) return defaultValue;
        if (!node.isShort()) return defaultValue;
        return node.asShort();
    }

    /**
     * Установка short значения в узел.
     *
     * @param value short значение
     * @return Этот же узел.
     */
    public SiNode setShort(short value) {
        type = Type.INTEGER;
        number = (double) value;
        return this;
    }

    /**
     * Замена элемента списка в конкретном индексе на новый с short значением. Если узел не является списком, то ничего не произойдёт.
     * Если индекс меньше 0, то ничего не произойдёт.
     * Если индекс больше или равен размеру списка, то элемент будет добавлен в конец списка.
     *
     * @param index Индекс, в который будет установлен элемент
     * @param value Элемент, который будет установлен
     * @return Этот же узел.
     */
    public SiNode setShort(int index, short value) {
        set(index, new SiNode(value));
        return this;
    }

    /**
     * Вставка short элемента списка в конкретный индекс со сдвигом элементов справа.
     * Если узел не является списком, то ничего не произойдёт.
     * Если индекс меньше 0, то ничего не произойдёт.
     * Если индекс больше или равен размеру списка, то элемент будет добавлен в конец списка.
     *
     * @param index Индекс, в который будет вставлен элемент
     * @param value Элемент, который будет вставлен
     * @return Этот же узел.
     */
    public SiNode insertShort(int index, short value) {
        insert(index, new SiNode(value));
        return this;
    }

    /**
     * Добавление short элемента в список. Если узел не является списком, то ничего не произойдёт.
     *
     * @param value Элемент, который будет добавлен
     * @return Этот же узел.
     */
    public SiNode addShort(short value) {
        add(new SiNode(value));
        return this;
    }

    /**
     * Установка дочернего узла с заданным именем. Если узел не является таблицей, то ничего не произойдёт.
     *
     * @param child Имя дочернего узла
     * @param value short значение узла
     * @return Этот же узел.
     */
    public SiNode setShort(String child, short value) {
        set(child, new SiNode(value));
        return this;
    }

    /**
     * Проверяет, является ли этот узел Integer.
     *
     * @return true, если узел является Integer, иначе false.
     */
    public boolean isInteger() {
        return type.equals(Type.INTEGER) && Integer.MIN_VALUE <= number && number <= Integer.MAX_VALUE;
    }

    /**
     * Проверяет, является ли узел по индексу Integer.
     *
     * @param index Индекс узла списка
     * @return true, если узел по индексу является Integer.false если не является, или если индекс за границей списка, или если узел не является списком.
     */
    public boolean isInteger(int index) {
        return hasIndex(index) && get(index).isInteger();
    }

    /**
     * Проверяет, является ли дочерний узел Integer.
     *
     * @param child Имя дочернего узла
     * @return true, если дочерний узел является Integer.false если не является, или если дочернего узла с таким именем нет, или если узел не является таблицей.
     */
    public boolean isInteger(String child) {
        return hasChild(child) && get(child).isInteger();
    }

    /**
     * Преобразует узел к int.
     * <p>
     * ВАЖНО! Если узел другого типа, то может возникнуть ошибка!
     *
     * @return int значение узла.
     */
    public int asInteger() {
        return number.intValue();
    }

    /**
     * Получение Integer значения узла из списка.
     *
     * @param index Индекс узла в списке
     * @return Integer значение или null, если узел другого типа, или если индекс за границей списка, или если узел не является списком.
     */
    @Nullable
    public Integer getInteger(int index) {
        SiNode node = get(index);
        if (node == null) return null;
        if (!node.isInteger()) return null;
        return node.asInteger();
    }

    /**
     * Получение Integer значения узла из списка.
     *
     * @param index        Индекс узла в списке
     * @param defaultValue Значение по умолчанию
     * @return Integer значение или значение по умолчанию, если узел другого типа, или если индекс за границей списка, или если узел не является списком.
     */
    public int getInteger(int index, int defaultValue) {
        SiNode node = get(index);
        if (node == null) return defaultValue;
        if (!node.isInteger()) return defaultValue;
        return node.asInteger();
    }

    /**
     * Получение Integer значения дочернего узла.
     *
     * @param child Имя дочернего узла
     * @return Integer значение или null, если дочерний узел другого типа, или если дочернего узла с таким именем нет, или если узел не является таблицей.
     */
    @Nullable
    public Integer getInteger(String child) {
        SiNode node = get(child);
        if (node == null) return null;
        if (!node.isInteger()) return null;
        return node.asInteger();
    }

    /**
     * Получение Integer значения дочернего узла.
     *
     * @param child        Имя дочернего узла
     * @param defaultValue Значение по умолчанию
     * @return Integer значение или значение по умолчанию, если дочерний узел другого типа, или если дочернего узла с таким именем нет, или если узел не является таблицей.
     */
    public int getInteger(String child, int defaultValue) {
        SiNode node = get(child);
        if (node == null) return defaultValue;
        if (!node.isInteger()) return defaultValue;
        return node.asInteger();
    }

    /**
     * Установка int значения в узел.
     *
     * @param value int значение
     * @return Этот же узел.
     */
    public SiNode setInteger(int value) {
        type = Type.INTEGER;
        number = (double) value;
        return this;
    }

    /**
     * Замена элемента списка в конкретном индексе на новый с int значением. Если узел не является списком, то ничего не произойдёт.
     * Если индекс меньше 0, то ничего не произойдёт.
     * Если индекс больше или равен размеру списка, то элемент будет добавлен в конец списка.
     *
     * @param index Индекс, в который будет установлен элемент
     * @param value Элемент, который будет установлен
     * @return Этот же узел.
     */
    public SiNode setInteger(int index, int value) {
        set(index, new SiNode(value));
        return this;
    }

    /**
     * Вставка int элемента списка в конкретный индекс со сдвигом элементов справа.
     * Если узел не является списком, то ничего не произойдёт.
     * Если индекс меньше 0, то ничего не произойдёт.
     * Если индекс больше или равен размеру списка, то элемент будет добавлен в конец списка.
     *
     * @param index Индекс, в который будет вставлен элемент
     * @param value Элемент, который будет вставлен
     * @return Этот же узел.
     */
    public SiNode insertInteger(int index, int value) {
        insert(index, new SiNode(value));
        return this;
    }

    /**
     * Добавление int элемента в список. Если узел не является списком, то ничего не произойдёт.
     *
     * @param value Элемент, который будет добавлен
     * @return Этот же узел.
     */
    public SiNode addInteger(int value) {
        add(new SiNode(value));
        return this;
    }

    /**
     * Установка дочернего узла с заданным именем. Если узел не является таблицей, то ничего не произойдёт.
     *
     * @param child Имя дочернего узла
     * @param value int значение узла
     * @return Этот же узел.
     */
    public SiNode setInteger(String child, int value) {
        set(child, new SiNode(value));
        return this;
    }

    /**
     * Проверяет, является ли этот узел Long.
     *
     * @return true, если узел является Long, иначе false.
     */
    public boolean isLong() {
        return type.equals(Type.INTEGER) && Long.MIN_VALUE <= number && number <= Long.MAX_VALUE;
    }

    /**
     * Проверяет, является ли узел по индексу Long.
     *
     * @param index Индекс узла списка
     * @return true, если узел по индексу является Long.false если не является, или если индекс за границей списка, или если узел не является списком.
     */
    public boolean isLong(int index) {
        return hasIndex(index) && get(index).isLong();
    }

    /**
     * Проверяет, является ли дочерний узел Long.
     *
     * @param child Имя дочернего узла
     * @return true, если дочерний узел является Long.false если не является, или если дочернего узла с таким именем нет, или если узел не является таблицей.
     */
    public boolean isLong(String child) {
        return hasChild(child) && get(child).isLong();
    }

    /**
     * Преобразует узел к long.
     * <p>
     * ВАЖНО! Если узел другого типа, то может возникнуть ошибка!
     *
     * @return long значение узла.
     */
    public long asLong() {
        return number.longValue();
    }

    /**
     * Получение Long значения узла из списка.
     *
     * @param index Индекс узла в списке
     * @return Long значение или null, если узел другого типа, или если индекс за границей списка, или если узел не является списком.
     */
    @Nullable
    public Long getLong(int index) {
        SiNode node = get(index);
        if (node == null) return null;
        if (!node.isLong()) return null;
        return node.asLong();
    }

    /**
     * Получение Long значения узла из списка.
     *
     * @param index        Индекс узла в списке
     * @param defaultValue Значение по умолчанию
     * @return Long значение или значение по умолчанию, если узел другого типа, или если индекс за границей списка, или если узел не является списком.
     */
    public long getLong(int index, long defaultValue) {
        SiNode node = get(index);
        if (node == null) return defaultValue;
        if (!node.isLong()) return defaultValue;
        return node.asLong();
    }

    /**
     * Получение Long значения дочернего узла.
     *
     * @param child Имя дочернего узла
     * @return Long значение или null, если дочерний узел другого типа, или если дочернего узла с таким именем нет, или если узел не является таблицей.
     */
    @Nullable
    public Long getLong(String child) {
        SiNode node = get(child);
        if (node == null) return null;
        if (!node.isLong()) return null;
        return node.asLong();
    }

    /**
     * Получение Long значения дочернего узла.
     *
     * @param child        Имя дочернего узла
     * @param defaultValue Значение по умолчанию
     * @return Long значение или значение по умолчанию, если дочерний узел другого типа, или если дочернего узла с таким именем нет, или если узел не является таблицей.
     */
    public long getLong(String child, long defaultValue) {
        SiNode node = get(child);
        if (node == null) return defaultValue;
        if (!node.isLong()) return defaultValue;
        return node.asLong();
    }

    /**
     * Установка long значения в узел.
     *
     * @param value long значение
     * @return Этот же узел.
     */
    public SiNode setLong(long value) {
        type = Type.INTEGER;
        number = (double) value;
        return this;
    }

    /**
     * Замена элемента списка в конкретном индексе на новый с long значением. Если узел не является списком, то ничего не произойдёт.
     * Если индекс меньше 0, то ничего не произойдёт.
     * Если индекс больше или равен размеру списка, то элемент будет добавлен в конец списка.
     *
     * @param index Индекс, в который будет установлен элемент
     * @param value Элемент, который будет установлен
     * @return Этот же узел.
     */
    public SiNode setLong(int index, long value) {
        set(index, new SiNode(value));
        return this;
    }

    /**
     * Вставка long элемента списка в конкретный индекс со сдвигом элементов справа.
     * Если узел не является списком, то ничего не произойдёт.
     * Если индекс меньше 0, то ничего не произойдёт.
     * Если индекс больше или равен размеру списка, то элемент будет добавлен в конец списка.
     *
     * @param index Индекс, в который будет вставлен элемент
     * @param value Элемент, который будет вставлен
     * @return Этот же узел.
     */
    public SiNode insertLong(int index, long value) {
        insert(index, new SiNode(value));
        return this;
    }

    /**
     * Добавление long элемента в список. Если узел не является списком, то ничего не произойдёт.
     *
     * @param value Элемент, который будет добавлен
     * @return Этот же узел.
     */
    public SiNode addLong(long value) {
        add(new SiNode(value));
        return this;
    }

    /**
     * Установка дочернего узла с заданным именем. Если узел не является таблицей, то ничего не произойдёт.
     *
     * @param child Имя дочернего узла
     * @param value long значение узла
     * @return Этот же узел.
     */
    public SiNode setLong(String child, long value) {
        set(child, new SiNode(value));
        return this;
    }

    /**
     * Проверяет, является ли этот узел Float.
     *
     * @return true, если узел является Float, иначе false.
     */
    public boolean isFloat() {
        return (type.equals(Type.FLOAT) || type.equals(Type.INTEGER)) && Float.MIN_VALUE <= number && number <= Float.MAX_VALUE;
    }

    /**
     * Проверяет, является ли узел по индексу Float.
     *
     * @param index Индекс узла списка
     * @return true, если узел по индексу является Float.false если не является, или если индекс за границей списка, или если узел не является списком.
     */
    public boolean isFloat(int index) {
        return hasIndex(index) && get(index).isFloat();
    }

    /**
     * Проверяет, является ли дочерний узел Float.
     *
     * @param child Имя дочернего узла
     * @return true, если дочерний узел является Float.false если не является, или если дочернего узла с таким именем нет, или если узел не является таблицей.
     */
    public boolean isFloat(String child) {
        return hasChild(child) && get(child).isFloat();
    }

    /**
     * Преобразует узел к float.
     * <p>
     * ВАЖНО! Если узел другого типа, то может возникнуть ошибка!
     *
     * @return float значение узла.
     */
    public float asFloat() {
        return number.floatValue();
    }

    /**
     * Получение Float значения узла из списка.
     *
     * @param index Индекс узла в списке
     * @return Float значение или null, если узел другого типа, или если индекс за границей списка, или если узел не является списком.
     */
    @Nullable
    public Float getFloat(int index) {
        SiNode node = get(index);
        if (node == null) return null;
        if (!node.isFloat()) return null;
        return node.asFloat();
    }

    /**
     * Получение Float значения узла из списка.
     *
     * @param index        Индекс узла в списке
     * @param defaultValue Значение по умолчанию
     * @return Float значение или значение по умолчанию, если узел другого типа, или если индекс за границей списка, или если узел не является списком.
     */
    public float getFloat(int index, float defaultValue) {
        SiNode node = get(index);
        if (node == null) return defaultValue;
        if (!node.isFloat()) return defaultValue;
        return node.asFloat();
    }

    /**
     * Получение Float значения дочернего узла.
     *
     * @param child Имя дочернего узла
     * @return Float значение или null, если дочерний узел другого типа, или если дочернего узла с таким именем нет, или если узел не является таблицей.
     */
    @Nullable
    public Float getFloat(String child) {
        SiNode node = get(child);
        if (node == null) return null;
        if (!node.isFloat()) return null;
        return node.asFloat();
    }

    /**
     * Получение Float значения дочернего узла.
     *
     * @param child        Имя дочернего узла
     * @param defaultValue Значение по умолчанию
     * @return Float значение или значение по умолчанию, если дочерний узел другого типа, или если дочернего узла с таким именем нет, или если узел не является таблицей.
     */
    public float getFloat(String child, float defaultValue) {
        SiNode node = get(child);
        if (node == null) return defaultValue;
        if (!node.isFloat()) return defaultValue;
        return node.asFloat();
    }

    /**
     * Установка float значения в узел.
     *
     * @param value float значение
     * @return Этот же узел.
     */
    public SiNode setFloat(float value) {
        type = (value == ((Float) value).longValue()) ? Type.INTEGER : Type.FLOAT;
        number = (double) value;
        return this;
    }

    /**
     * Замена элемента списка в конкретном индексе на новый с float значением. Если узел не является списком, то ничего не произойдёт.
     * Если индекс меньше 0, то ничего не произойдёт.
     * Если индекс больше или равен размеру списка, то элемент будет добавлен в конец списка.
     *
     * @param index Индекс, в который будет установлен элемент
     * @param value Элемент, который будет установлен
     * @return Этот же узел.
     */
    public SiNode setFloat(int index, float value) {
        set(index, new SiNode(value));
        return this;
    }

    /**
     * Вставка float элемента списка в конкретный индекс со сдвигом элементов справа.
     * Если узел не является списком, то ничего не произойдёт.
     * Если индекс меньше 0, то ничего не произойдёт.
     * Если индекс больше или равен размеру списка, то элемент будет добавлен в конец списка.
     *
     * @param index Индекс, в который будет вставлен элемент
     * @param value Элемент, который будет вставлен
     * @return Этот же узел.
     */
    public SiNode insertFloat(int index, float value) {
        insert(index, new SiNode(value));
        return this;
    }

    /**
     * Добавление float элемента в список. Если узел не является списком, то ничего не произойдёт.
     *
     * @param value Элемент, который будет добавлен
     * @return Этот же узел.
     */
    public SiNode addFloat(float value) {
        add(new SiNode(value));
        return this;
    }

    /**
     * Установка дочернего узла с заданным именем. Если узел не является таблицей, то ничего не произойдёт.
     *
     * @param child Имя дочернего узла
     * @param value float значение узла
     * @return Этот же узел.
     */
    public SiNode setFloat(String child, float value) {
        set(child, new SiNode(value));
        return this;
    }

    /**
     * Проверяет, является ли этот узел Double.
     *
     * @return true, если узел является Double, иначе false.
     */
    public boolean isDouble() {
        return type.equals(Type.FLOAT) || type.equals(Type.INTEGER);
    }

    /**
     * Проверяет, является ли узел по индексу Double.
     *
     * @param index Индекс узла списка
     * @return true, если узел по индексу является Double.false если не является, или если индекс за границей списка, или если узел не является списком.
     */
    public boolean isDouble(int index) {
        return hasIndex(index) && get(index).isDouble();
    }

    /**
     * Проверяет, является ли дочерний узел Double.
     *
     * @param child Имя дочернего узла
     * @return true, если дочерний узел является Double.false если не является, или если дочернего узла с таким именем нет, или если узел не является таблицей.
     */
    public boolean isDouble(String child) {
        return hasChild(child) && get(child).isDouble();
    }

    /**
     * Преобразует узел к double.
     * <p>
     * ВАЖНО! Если узел другого типа, то может возникнуть ошибка!
     *
     * @return double значение узла.
     */
    public double asDouble() {
        return number;
    }

    /**
     * Получение Double значения узла из списка.
     *
     * @param index Индекс узла в списке
     * @return Double значение или null, если узел другого типа, или если индекс за границей списка, или если узел не является списком.
     */
    @Nullable
    public Double getDouble(int index) {
        SiNode node = get(index);
        if (node == null) return null;
        if (!node.isDouble()) return null;
        return node.asDouble();
    }

    /**
     * Получение Double значения узла из списка.
     *
     * @param index        Индекс узла в списке
     * @param defaultValue Значение по умолчанию
     * @return Double значение или значение по умолчанию, если узел другого типа, или если индекс за границей списка, или если узел не является списком.
     */
    public double getDouble(int index, double defaultValue) {
        SiNode node = get(index);
        if (node == null) return defaultValue;
        if (!node.isDouble()) return defaultValue;
        return node.asDouble();
    }

    /**
     * Получение Double значения дочернего узла.
     *
     * @param child Имя дочернего узла
     * @return Double значение или null, если дочерний узел другого типа, или если дочернего узла с таким именем нет, или если узел не является таблицей.
     */
    @Nullable
    public Double getDouble(String child) {
        SiNode node = get(child);
        if (node == null) return null;
        if (!node.isDouble()) return null;
        return node.asDouble();
    }

    /**
     * Получение Double значения дочернего узла.
     *
     * @param child        Имя дочернего узла
     * @param defaultValue Значение по умолчанию
     * @return Double значение или значение по умолчанию, если дочерний узел другого типа, или если дочернего узла с таким именем нет, или если узел не является таблицей.
     */
    public double getDouble(String child, double defaultValue) {
        SiNode node = get(child);
        if (node == null) return defaultValue;
        if (!node.isDouble()) return defaultValue;
        return node.asDouble();
    }

    /**
     * Установка double значения в узел.
     *
     * @param value double значение
     * @return Этот же узел.
     */
    public SiNode setDouble(double value) {
        type = (value == ((Double) value).longValue()) ? Type.INTEGER : Type.FLOAT;
        number = value;
        return this;
    }

    /**
     * Замена элемента списка в конкретном индексе на новый с double значением. Если узел не является списком, то ничего не произойдёт.
     * Если индекс меньше 0, то ничего не произойдёт.
     * Если индекс больше или равен размеру списка, то элемент будет добавлен в конец списка.
     *
     * @param index Индекс, в который будет установлен элемент
     * @param value Элемент, который будет установлен
     * @return Этот же узел.
     */
    public SiNode setDouble(int index, double value) {
        set(index, new SiNode(value));
        return this;
    }

    /**
     * Вставка double элемента списка в конкретный индекс со сдвигом элементов справа.
     * Если узел не является списком, то ничего не произойдёт.
     * Если индекс меньше 0, то ничего не произойдёт.
     * Если индекс больше или равен размеру списка, то элемент будет добавлен в конец списка.
     *
     * @param index Индекс, в который будет вставлен элемент
     * @param value Элемент, который будет вставлен
     * @return Этот же узел.
     */
    public SiNode insertDouble(int index, double value) {
        insert(index, new SiNode(value));
        return this;
    }

    /**
     * Добавление double элемента в список. Если узел не является списком, то ничего не произойдёт.
     *
     * @param value Элемент, который будет добавлен
     * @return Этот же узел.
     */
    public SiNode addDouble(double value) {
        add(new SiNode(value));
        return this;
    }

    /**
     * Установка дочернего узла с заданным именем. Если узел не является таблицей, то ничего не произойдёт.
     *
     * @param child Имя дочернего узла
     * @param value double значение узла
     * @return Этот же узел.
     */
    public SiNode setDouble(String child, double value) {
        set(child, new SiNode(value));
        return this;
    }

    /**
     * Проверяет, является ли этот узел Character.
     *
     * @return true, если узел является Character, иначе false.
     */
    public boolean isCharacter() {
        return type.equals(Type.STRING) && string.length() == 1;
    }

    /**
     * Проверяет, является ли узел по индексу Character.
     *
     * @param index Индекс узла списка
     * @return true, если узел по индексу является Character.false если не является, или если индекс за границей списка, или если узел не является списком.
     */
    public boolean isCharacter(int index) {
        return hasIndex(index) && get(index).isCharacter();
    }

    /**
     * Проверяет, является ли дочерний узел Character.
     *
     * @param child Имя дочернего узла
     * @return true, если дочерний узел является Character.false если не является, или если дочернего узла с таким именем нет, или если узел не является таблицей.
     */
    public boolean isCharacter(String child) {
        return hasChild(child) && get(child).isCharacter();
    }

    /**
     * Преобразует узел к char.
     * <p>
     * ВАЖНО! Если узел другого типа, то может возникнуть ошибка!
     *
     * @return char значение узла.
     */
    public char asCharacter() {
        return string.charAt(0);
    }

    /**
     * Получение Character значения узла из списка.
     *
     * @param index Индекс узла в списке
     * @return Character значение или null, если узел другого типа, или если индекс за границей списка, или если узел не является списком.
     */
    @Nullable
    public Character getCharacter(int index) {
        SiNode node = get(index);
        if (node == null) return null;
        if (!node.isCharacter()) return null;
        return node.asCharacter();
    }

    /**
     * Получение Character значения узла из списка.
     *
     * @param index        Индекс узла в списке
     * @param defaultValue Значение по умолчанию
     * @return Character значение или значение по умолчанию, если узел другого типа, или если индекс за границей списка, или если узел не является списком.
     */
    public char getCharacter(int index, char defaultValue) {
        SiNode node = get(index);
        if (node == null) return defaultValue;
        if (!node.isCharacter()) return defaultValue;
        return node.asCharacter();
    }

    /**
     * Получение Character значения дочернего узла.
     *
     * @param child Имя дочернего узла
     * @return Character значение или null, если дочерний узел другого типа, или если дочернего узла с таким именем нет, или если узел не является таблицей.
     */
    @Nullable
    public Character getCharacter(String child) {
        SiNode node = get(child);
        if (node == null) return null;
        if (!node.isCharacter()) return null;
        return node.asCharacter();
    }

    /**
     * Получение Character значения дочернего узла.
     *
     * @param child        Имя дочернего узла
     * @param defaultValue Значение по умолчанию
     * @return Character значение или значение по умолчанию, если дочерний узел другого типа, или если дочернего узла с таким именем нет, или если узел не является таблицей.
     */
    public char getCharacter(String child, char defaultValue) {
        SiNode node = get(child);
        if (node == null) return defaultValue;
        if (!node.isCharacter()) return defaultValue;
        return node.asCharacter();
    }

    /**
     * Установка char значения в узел.
     *
     * @param value char значение
     * @return Этот же узел.
     */
    public SiNode setCharacter(char value) {
        type = Type.STRING;
        string = String.valueOf(value);
        return this;
    }

    /**
     * Замена элемента списка в конкретном индексе на новый с char значением. Если узел не является списком, то ничего не произойдёт.
     * Если индекс меньше 0, то ничего не произойдёт.
     * Если индекс больше или равен размеру списка, то элемент будет добавлен в конец списка.
     *
     * @param index Индекс, в который будет установлен элемент
     * @param value Элемент, который будет установлен
     * @return Этот же узел.
     */
    public SiNode setCharacter(int index, char value) {
        set(index, new SiNode(value));
        return this;
    }

    /**
     * Вставка char элемента списка в конкретный индекс со сдвигом элементов справа.
     * Если узел не является списком, то ничего не произойдёт.
     * Если индекс меньше 0, то ничего не произойдёт.
     * Если индекс больше или равен размеру списка, то элемент будет добавлен в конец списка.
     *
     * @param index Индекс, в который будет вставлен элемент
     * @param value Элемент, который будет вставлен
     * @return Этот же узел.
     */
    public SiNode insertCharacter(int index, char value) {
        insert(index, new SiNode(value));
        return this;
    }

    /**
     * Добавление char элемента в список. Если узел не является списком, то ничего не произойдёт.
     *
     * @param value Элемент, который будет добавлен
     * @return Этот же узел.
     */
    public SiNode addCharacter(char value) {
        add(new SiNode(value));
        return this;
    }

    /**
     * Установка дочернего узла с заданным именем. Если узел не является таблицей, то ничего не произойдёт.
     *
     * @param child Имя дочернего узла
     * @param value char значение узла
     * @return Этот же узел.
     */
    public SiNode setCharacter(String child, char value) {
        set(child, new SiNode(value));
        return this;
    }

    /**
     * Проверяет, является ли этот узел String.
     *
     * @return true, если узел является String, иначе false.
     */
    public boolean isString() {
        return type.equals(Type.STRING);
    }

    /**
     * Проверяет, является ли узел по индексу String.
     *
     * @param index Индекс узла списка
     * @return true, если узел по индексу является String.false если не является, или если индекс за границей списка, или если узел не является списком.
     */
    public boolean isString(int index) {
        return hasIndex(index) && get(index).isString();
    }

    /**
     * Проверяет, является ли дочерний узел String.
     *
     * @param child Имя дочернего узла
     * @return true, если дочерний узел является String.false если не является, или если дочернего узла с таким именем нет, или если узел не является таблицей.
     */
    public boolean isString(String child) {
        return hasChild(child) && get(child).isString();
    }

    /**
     * Преобразует узел к String.
     * <p>
     * ВАЖНО! Если узел другого типа, то может возникнуть ошибка!
     *
     * @return String значение узла.
     */
    public String asString() {
        return string;
    }

    /**
     * Получение String значения узла из списка.
     *
     * @param index Индекс узла в списке
     * @return String значение или null, если узел другого типа, или если индекс за границей списка, или если узел не является списком.
     */
    @Nullable
    public String getString(int index) {
        SiNode node = get(index);
        if (node == null) return null;
        if (!node.isString()) return null;
        return node.asString();
    }

    /**
     * Получение String значения узла из списка.
     *
     * @param index        Индекс узла в списке
     * @param defaultValue Значение по умолчанию
     * @return String значение или значение по умолчанию, если узел другого типа, или если индекс за границей списка, или если узел не является списком.
     */
    public String getString(int index, String defaultValue) {
        SiNode node = get(index);
        if (node == null) return defaultValue;
        if (!node.isString()) return defaultValue;
        return node.asString();
    }

    /**
     * Получение String значения дочернего узла.
     *
     * @param child Имя дочернего узла
     * @return String значение или null, если дочерний узел другого типа, или если дочернего узла с таким именем нет, или если узел не является таблицей.
     */
    @Nullable
    public String getString(String child) {
        SiNode node = get(child);
        if (node == null) return null;
        if (!node.isString()) return null;
        return node.asString();
    }

    /**
     * Получение String значения дочернего узла.
     *
     * @param child        Имя дочернего узла
     * @param defaultValue Значение по умолчанию
     * @return String значение или значение по умолчанию, если дочерний узел другого типа, или если дочернего узла с таким именем нет, или если узел не является таблицей.
     */
    public String getString(String child, String defaultValue) {
        SiNode node = get(child);
        if (node == null) return defaultValue;
        if (!node.isString()) return defaultValue;
        return node.asString();
    }

    /**
     * Установка String значения в узел.
     *
     * @param value String значение
     * @return Этот же узел.
     */
    public SiNode setString(String value) {
        type = Type.STRING;
        string = value;
        return this;
    }

    /**
     * Замена элемента списка в конкретном индексе на новый с String значением. Если узел не является списком, то ничего не произойдёт.
     * Если индекс меньше 0, то ничего не произойдёт.
     * Если индекс больше или равен размеру списка, то элемент будет добавлен в конец списка.
     *
     * @param index Индекс, в который будет установлен элемент
     * @param value Элемент, который будет установлен
     * @return Этот же узел.
     */
    public SiNode setString(int index, String value) {
        set(index, new SiNode(value));
        return this;
    }

    /**
     * Вставка String элемента списка в конкретный индекс со сдвигом элементов справа.
     * Если узел не является списком, то ничего не произойдёт.
     * Если индекс меньше 0, то ничего не произойдёт.
     * Если индекс больше или равен размеру списка, то элемент будет добавлен в конец списка.
     *
     * @param index Индекс, в который будет вставлен элемент
     * @param value Элемент, который будет вставлен
     * @return Этот же узел.
     */
    public SiNode insertString(int index, String value) {
        insert(index, new SiNode(value));
        return this;
    }

    /**
     * Добавление String элемента в список. Если узел не является списком, то ничего не произойдёт.
     *
     * @param value Элемент, который будет добавлен
     * @return Этот же узел.
     */
    public SiNode addString(String value) {
        add(new SiNode(value));
        return this;
    }

    /**
     * Установка дочернего узла с заданным именем. Если узел не является таблицей, то ничего не произойдёт.
     *
     * @param child Имя дочернего узла
     * @param value String значение узла
     * @return Этот же узел.
     */
    public SiNode setString(String child, String value) {
        set(child, new SiNode(value));
        return this;
    }

    /**
     * Проверяет, является ли этот узел List.
     *
     * @return true, если узел является List, иначе false.
     */
    public boolean isList() {
        return type.equals(Type.LIST);
    }

    /**
     * Проверяет, является ли узел по индексу List.
     *
     * @param index Индекс узла списка
     * @return true, если узел по индексу является List.false если не является, или если индекс за границей списка, или если узел не является списком.
     */
    public boolean isList(int index) {
        return hasIndex(index) && get(index).isList();
    }

    /**
     * Проверяет, является ли дочерний узел List.
     *
     * @param child Имя дочернего узла
     * @return true, если дочерний узел является List.false если не является, или если дочернего узла с таким именем нет, или если узел не является таблицей.
     */
    public boolean isList(String child) {
        return hasChild(child) && get(child).isList();
    }

    /**
     * Преобразует узел к List<SiNode>.
     * <p>
     * ВАЖНО! Если узел другого типа, то может возникнуть ошибка!
     *
     * @return List<SiNode> значение узла.
     */
    public List<SiNode> asList() {
        return list;
    }

    /**
     * Получение List<SiNode> значения узла из списка.
     *
     * @param index Индекс узла в списке
     * @return List<SiNode> значение или null, если узел другого типа, или если индекс за границей списка, или если узел не является списком.
     */
    @Nullable
    public List<SiNode> getList(int index) {
        SiNode node = get(index);
        if (node == null) return null;
        if (!node.isList()) return null;
        return node.asList();
    }

    /**
     * Получение List<SiNode> значения узла из списка.
     *
     * @param index        Индекс узла в списке
     * @param defaultValue Значение по умолчанию
     * @return List<SiNode> значение или значение по умолчанию, если узел другого типа, или если индекс за границей списка, или если узел не является списком.
     */
    public List<SiNode> getList(int index, List<SiNode> defaultValue) {
        SiNode node = get(index);
        if (node == null) return defaultValue;
        if (!node.isList()) return defaultValue;
        return node.asList();
    }

    /**
     * Получение List<SiNode> значения дочернего узла.
     *
     * @param child Имя дочернего узла
     * @return List<SiNode> значение или null, если дочерний узел другого типа, или если дочернего узла с таким именем нет, или если узел не является таблицей.
     */
    @Nullable
    public List<SiNode> getList(String child) {
        SiNode node = get(child);
        if (node == null) return null;
        if (!node.isList()) return null;
        return node.asList();
    }

    /**
     * Получение List<SiNode> значения дочернего узла.
     *
     * @param child        Имя дочернего узла
     * @param defaultValue Значение по умолчанию
     * @return List<SiNode> значение или значение по умолчанию, если дочерний узел другого типа, или если дочернего узла с таким именем нет, или если узел не является таблицей.
     */
    public List<SiNode> getList(String child, List<SiNode> defaultValue) {
        SiNode node = get(child);
        if (node == null) return defaultValue;
        if (!node.isList()) return defaultValue;
        return node.asList();
    }

    /**
     * Установка List<SiNode> значения в узел.
     *
     * @param value List<SiNode> значение
     * @return Этот же узел.
     */
    public SiNode setList(List<SiNode> value) {
        type = Type.LIST;
        list = new ArrayList<>();
        list.addAll(value);
        return this;
    }

    /**
     * Замена элемента списка в конкретном индексе на новый с List<SiNode> значением. Если узел не является списком, то ничего не произойдёт.
     * Если индекс меньше 0, то ничего не произойдёт.
     * Если индекс больше или равен размеру списка, то элемент будет добавлен в конец списка.
     *
     * @param index Индекс, в который будет установлен элемент
     * @param value Элемент, который будет установлен
     * @return Этот же узел.
     */
    public SiNode setList(int index, List<SiNode> value) {
        set(index, new SiNode(value));
        return this;
    }

    /**
     * Вставка List<SiNode> элемента списка в конкретный индекс со сдвигом элементов справа.
     * Если узел не является списком, то ничего не произойдёт.
     * Если индекс меньше 0, то ничего не произойдёт.
     * Если индекс больше или равен размеру списка, то элемент будет добавлен в конец списка.
     *
     * @param index Индекс, в который будет вставлен элемент
     * @param value Элемент, который будет вставлен
     * @return Этот же узел.
     */
    public SiNode insertList(int index, List<SiNode> value) {
        insert(index, new SiNode(value));
        return this;
    }

    /**
     * Добавление List<SiNode> элемента в список. Если узел не является списком, то ничего не произойдёт.
     *
     * @param value Элемент, который будет добавлен
     * @return Этот же узел.
     */
    public SiNode addList(List<SiNode> value) {
        add(new SiNode(value));
        return this;
    }

    /**
     * Установка дочернего узла с заданным именем. Если узел не является таблицей, то ничего не произойдёт.
     *
     * @param child Имя дочернего узла
     * @param value List<SiNode> значение узла
     * @return Этот же узел.
     */
    public SiNode setList(String child, List<SiNode> value) {
        set(child, new SiNode(value));
        return this;
    }

    /**
     * Проверяет, является ли этот узел Map.
     *
     * @return true, если узел является Map, иначе false.
     */
    public boolean isMap() {
        return type.equals(Type.MAP);
    }

    /**
     * Проверяет, является ли узел по индексу Map.
     *
     * @param index Индекс узла списка
     * @return true, если узел по индексу является Map.false если не является, или если индекс за границей списка, или если узел не является списком.
     */
    public boolean isMap(int index) {
        return hasIndex(index) && get(index).isMap();
    }

    /**
     * Проверяет, является ли дочерний узел Map.
     *
     * @param child Имя дочернего узла
     * @return true, если дочерний узел является Map.false если не является, или если дочернего узла с таким именем нет, или если узел не является таблицей.
     */
    public boolean isMap(String child) {
        return hasChild(child) && get(child).isMap();
    }

    /**
     * Преобразует узел к Map<String, SiNode>.
     * <p>
     * ВАЖНО! Если узел другого типа, то может возникнуть ошибка!
     *
     * @return Map<String, SiNode> значение узла.
     */
    public Map<String, SiNode> asMap() {
        return map;
    }

    /**
     * Получение Map<String, SiNode> значения узла из списка.
     *
     * @param index Индекс узла в списке
     * @return Map<String, SiNode> значение или null, если узел другого типа, или если индекс за границей списка, или если узел не является списком.
     */
    @Nullable
    public Map<String, SiNode> getMap(int index) {
        SiNode node = get(index);
        if (node == null) return null;
        if (!node.isMap()) return null;
        return node.asMap();
    }

    /**
     * Получение Map<String, SiNode> значения узла из списка.
     *
     * @param index        Индекс узла в списке
     * @param defaultValue Значение по умолчанию
     * @return Map<String, SiNode> значение или значение по умолчанию, если узел другого типа, или если индекс за границей списка, или если узел не является списком.
     */
    public Map<String, SiNode> getMap(int index, Map<String, SiNode> defaultValue) {
        SiNode node = get(index);
        if (node == null) return defaultValue;
        if (!node.isMap()) return defaultValue;
        return node.asMap();
    }

    /**
     * Получение Map<String, SiNode> значения дочернего узла.
     *
     * @param child Имя дочернего узла
     * @return Map<String, SiNode> значение или null, если дочерний узел другого типа, или если дочернего узла с таким именем нет, или если узел не является таблицей.
     */
    @Nullable
    public Map<String, SiNode> getMap(String child) {
        SiNode node = get(child);
        if (node == null) return null;
        if (!node.isMap()) return null;
        return node.asMap();
    }

    /**
     * Получение Map<String, SiNode> значения дочернего узла.
     *
     * @param child        Имя дочернего узла
     * @param defaultValue Значение по умолчанию
     * @return Map<String, SiNode> значение или значение по умолчанию, если дочерний узел другого типа, или если дочернего узла с таким именем нет, или если узел не является таблицей.
     */
    public Map<String, SiNode> getMap(String child, Map<String, SiNode> defaultValue) {
        SiNode node = get(child);
        if (node == null) return defaultValue;
        if (!node.isMap()) return defaultValue;
        return node.asMap();
    }

    /**
     * Установка Map<String, SiNode> значения в узел.
     *
     * @param value Map<String, SiNode> значение
     * @return Этот же узел.
     */
    public SiNode setMap(Map<String, SiNode> value) {
        type = Type.MAP;
        map = new HashMap<>();
        for (String key : value.keySet()) map.put(key, value.get(key));
        return this;
    }

    /**
     * Замена элемента списка в конкретном индексе на новый с Map<String, SiNode> значением. Если узел не является списком, то ничего не произойдёт.
     * Если индекс меньше 0, то ничего не произойдёт.
     * Если индекс больше или равен размеру списка, то элемент будет добавлен в конец списка.
     *
     * @param index Индекс, в который будет установлен элемент
     * @param value Элемент, который будет установлен
     * @return Этот же узел.
     */
    public SiNode setMap(int index, Map<String, SiNode> value) {
        set(index, new SiNode(value));
        return this;
    }

    /**
     * Вставка Map<String, SiNode> элемента списка в конкретный индекс со сдвигом элементов справа.
     * Если узел не является списком, то ничего не произойдёт.
     * Если индекс меньше 0, то ничего не произойдёт.
     * Если индекс больше или равен размеру списка, то элемент будет добавлен в конец списка.
     *
     * @param index Индекс, в который будет вставлен элемент
     * @param value Элемент, который будет вставлен
     * @return Этот же узел.
     */
    public SiNode insertMap(int index, Map<String, SiNode> value) {
        insert(index, new SiNode(value));
        return this;
    }

    /**
     * Добавление Map<String, SiNode> элемента в список. Если узел не является списком, то ничего не произойдёт.
     *
     * @param value Элемент, который будет добавлен
     * @return Этот же узел.
     */
    public SiNode addMap(Map<String, SiNode> value) {
        add(new SiNode(value));
        return this;
    }

    /**
     * Установка дочернего узла с заданным именем. Если узел не является таблицей, то ничего не произойдёт.
     *
     * @param child Имя дочернего узла
     * @param value Map<String, SiNode> значение узла
     * @return Этот же узел.
     */
    public SiNode setMap(String child, Map<String, SiNode> value) {
        set(child, new SiNode(value));
        return this;
    }
}