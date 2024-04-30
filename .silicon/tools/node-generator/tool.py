# Чтобы было красиво надо ещё прожать Ctrl+Alt+L в Intellij IDEA

import os


class DataType:
    def __init__(self, primitive_name: str, class_name: str, is_code: str, as_code: str, set_code: str,
                 full_name: str = None):
        self.primitive_name = primitive_name
        self.class_name = class_name
        self.is_code = is_code
        self.as_code = as_code
        self.set_code = set_code
        if full_name is None:
            full_name = class_name
        self.full_name = full_name


data_types = [
    DataType(
        primitive_name="boolean",
        class_name="Boolean",
        is_code="return type.equals(Type.BOOLEAN);",
        as_code="return number != 0;",
        set_code="type = Type.BOOLEAN;\nnumber = value ? 1.0 : 0;"
    ),
    DataType(
        primitive_name="short",
        class_name="Short",
        is_code="return type.equals(Type.INTEGER) && Short.MIN_VALUE <= number && number <= Short.MAX_VALUE;",
        as_code="return number.shortValue();",
        set_code="type = Type.INTEGER;\nnumber = (double) value;"
    ),
    DataType(
        primitive_name="int",
        class_name="Integer",
        is_code="return type.equals(Type.INTEGER) && Integer.MIN_VALUE <= number && number <= Integer.MAX_VALUE;",
        as_code="return number.intValue();",
        set_code="type = Type.INTEGER;\nnumber = (double) value;"
    ),
    DataType(
        primitive_name="long",
        class_name="Long",
        is_code="return type.equals(Type.INTEGER) && Long.MIN_VALUE <= number && number <= Long.MAX_VALUE;",
        as_code="return number.longValue();",
        set_code="type = Type.INTEGER;\nnumber = (double) value;"
    ),
    DataType(
        primitive_name="float",
        class_name="Float",
        is_code="return (type.equals(Type.FLOAT) || type.equals(Type.INTEGER)) && Float.MIN_VALUE <= number && number <= Float.MAX_VALUE;",
        as_code="return number.floatValue();",
        set_code="type = (value == ((Float) value).longValue()) ? Type.INTEGER : Type.FLOAT;\nnumber = (double) value;"
    ),
    DataType(
        primitive_name="double",
        class_name="Double",
        is_code="return type.equals(Type.FLOAT) || type.equals(Type.INTEGER);",
        as_code="return number;",
        set_code="type = (value == ((Double) value).longValue()) ? Type.INTEGER : Type.FLOAT;\nnumber = value;"
    ),
    DataType(
        primitive_name="char",
        class_name="Character",
        is_code="return type.equals(Type.STRING) && string.length() == 1;",
        as_code="return string.charAt(0);",
        set_code="type = Type.STRING;\nstring = String.valueOf(value);"
    ),
    DataType(
        primitive_name="String",
        class_name="String",
        is_code="return type.equals(Type.STRING);",
        as_code="return string;",
        set_code="type = Type.STRING;\nstring = value;"
    ),
    DataType(
        primitive_name="List<SiNode>",
        class_name="List<SiNode>",
        is_code="return type.equals(Type.LIST);",
        as_code="return list;",
        set_code="type = Type.LIST;\nlist = new ArrayList<>();\nlist.addAll(value);",

        full_name="List"
    ),
    DataType(
        primitive_name="Map<String, SiNode>",
        class_name="Map<String, SiNode>",
        is_code="return type.equals(Type.MAP);",
        as_code="return map;",
        set_code="type = Type.MAP;\nmap = new HashMap<>();\nfor (String key : value.keySet()) map.put(key, value.get(key));",

        full_name="Map"
    )
]


def read_file(path: str) -> str:
    file = open(path, "rt", encoding="utf-8")
    text = file.read()
    file.close()
    return text


def write_file(path: str, text: str) -> None:
    file = open(path, "wt", encoding="utf-8")
    file.write(text)
    file.close()


node_class_text = read_file(".silicon/tools/node-generator/class.txt")
node_methods_text = read_file(".silicon/tools/node-generator/methods.txt")

methods = ""

for data_type in data_types:
    node_methods = node_methods_text.replace("{primitive_name}", data_type.primitive_name)
    node_methods = node_methods.replace("{class_name}", data_type.class_name)
    node_methods = node_methods.replace("{full_name}", data_type.full_name)
    node_methods = node_methods.replace("{is_code}", data_type.is_code)
    node_methods = node_methods.replace("{as_code}", data_type.as_code)
    node_methods = node_methods.replace("{set_code}", data_type.set_code)

    node_methods = node_methods.replace("\n", "\n    ")
    methods += node_methods

node_class = node_class_text.replace("{methods}", methods)

try:
    os.mkdir("src/main/java/ru/vladislav117/silicon/node")
except:
    pass
write_file("src/main/java/ru/vladislav117/silicon/node/SiNode.java", node_class)
