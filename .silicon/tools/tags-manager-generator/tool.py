# Чтобы было красиво надо ещё прожать Ctrl+Alt+L в Intellij IDEA

import os


class DataType:
    def __init__(self, primitive_name: str, class_name: str, type_name: str,
                 full_name: str = None):
        self.primitive_name = primitive_name
        self.class_name = class_name
        self.type_name = type_name
        if full_name is None:
            full_name = class_name
        self.full_name = full_name


data_types = [
    DataType(
        primitive_name="boolean",
        class_name="Boolean",
        type_name="BOOLEAN"
    ),
    DataType(
        primitive_name="short",
        class_name="Short",
        type_name="SHORT"
    ),
    DataType(
        primitive_name="int",
        class_name="Integer",
        type_name="INTEGER"
    ),
    DataType(
        primitive_name="long",
        class_name="Long",
        type_name="LONG"
    ),
    DataType(
        primitive_name="float",
        class_name="Float",
        type_name="FLOAT"
    ),
    DataType(
        primitive_name="double",
        class_name="Double",
        type_name="DOUBLE"
    ),
    DataType(
        primitive_name="String",
        class_name="String",
        type_name="STRING"
    ),
    DataType(
        primitive_name="byte[]",
        class_name="byte @Nullable []",
        type_name="BYTE_ARRAY",
        full_name="ByteArray"
    ),
    DataType(
        primitive_name="int[]",
        class_name="int @Nullable []",
        type_name="INTEGER_ARRAY",
        full_name="IntegerArray"
    ),
    DataType(
        primitive_name="long[]",
        class_name="long @Nullable []",
        type_name="LONG_ARRAY",
        full_name="LongArray"
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


tags_manager_class_text = read_file(".silicon/tools/tags-manager-generator/class.txt")
tags_manager_methods_text = read_file(".silicon/tools/tags-manager-generator/methods.txt")

methods = ""

for data_type in data_types:
    tags_manager_methods = tags_manager_methods_text.replace("{primitive_name}", data_type.primitive_name)
    tags_manager_methods = tags_manager_methods.replace("{class_name}", data_type.class_name)
    tags_manager_methods = tags_manager_methods.replace("{full_name}", data_type.full_name)
    tags_manager_methods = tags_manager_methods.replace("{type_name}", data_type.type_name)

    tags_manager_methods = tags_manager_methods.replace("\n", "\n    ")
    methods += tags_manager_methods

tags_manager_class = tags_manager_class_text.replace("{methods}", methods)

try:
    os.mkdir("src/main/java/ru/vladislav117/silicon/tags")
except:
    pass
write_file("src/main/java/ru/vladislav117/silicon/tags/SiTagsManager.java", tags_manager_class)
