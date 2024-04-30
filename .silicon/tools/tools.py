from __future__ import annotations
import os
import sys
import json

# ================================
# Конфигурация
# ================================

TOOLS_ROOT = ".silicon\\tools"  # Директория с инструментами
TOOLS_CACHE = TOOLS_ROOT + "\\tools-cache.json"  # Файл с кешем инструментов


# ================================
# ================================
# ================================

class RunType:
    def __init__(self, name: str):
        self.__name = name
        RunTypes.add(self)

    def name(self) -> str:
        return self.__name

    def run(self, directory: str, config: dict) -> None:
        pass


class PythonRunType(RunType):
    def run(self, directory: str, config: dict) -> None:
        if not config.__contains__("file"):
            print("Не найдено поле \"file\" для запуска скрипта.")
            return
        file = f"{directory}\\{config['file']}"
        if not os.path.exists(file):
            print(f"Файл \"{file}\" не найден.")
            return
        os.system(f"py {file}")


class RunTypes:
    __run_types = dict()

    @classmethod
    def get(cls, name: str) -> RunType | None:
        return cls.__run_types.get(name)

    @classmethod
    def add(cls, run_type: RunType) -> None:
        cls.__run_types[run_type.name()] = run_type


PythonRunType("python-script")


def read_json(file: str) -> bool | int | float | str | list | dict | None:
    if not os.path.exists(file):
        return None
    fp = open(file, "rt", encoding="utf-8")
    data = json.load(fp)
    fp.close()
    return data


def write_json(data: bool | int | float | str | list | dict, file: str) -> None:
    fp = open(file, "wt", encoding="utf-8")
    json.dump(data, file)
    fp.close()
    return data


def cache():
    cached = dict()
    cached["tools"] = dict()
    cached["directories"] = dict()
    cached["aliases"] = dict()
    for r, _, found_files in os.walk(".silicon\\tools"):
        for file in found_files:
            if not file.__eq__("tool.json"):
                continue
            file_path = os.path.join(r, file)
            fp = open(file_path, "rt", encoding="utf-8")
            tool = json.load(fp)
            fp.close()
            if not tool.__contains__("name"):
                print("Ошибка в " + file_path + ": Инструмент не содержит названия.")
                continue
            if not tool.__contains__("run"):
                print("Ошибка в " + file_path + ": Инструмент не содержит конфигурации выполнения.")
                continue
            cached["tools"][tool["name"]] = tool
            cached["directories"][tool["name"]] = r
            cached["aliases"][tool["name"]] = tool["name"]
            if tool.__contains__("aliases"):
                for alias in tool["aliases"]:
                    cached["aliases"][alias] = tool["name"]
    fp = open(TOOLS_CACHE, "wt", encoding="utf-8")
    json.dump(cached, fp)
    fp.close()
    print("Инструменты обновлены.")


def run(alias: str) -> None:
    cached = read_json(TOOLS_CACHE)
    if cached is None:
        print("Не найден кеш инструментов. Используйте команду \"tool update\" для кеширования.")
        return
    if alias not in cached["aliases"]:
        print("Указанное имя или псевдоним инструмента не найдено. Попробуйте обновить кеш командой \"tool update\".")
        return
    tool_name = cached["aliases"][alias]
    tool = cached["tools"][tool_name]
    directory = cached["directories"][tool_name]
    run_type = RunTypes.get(tool["run"]["type"])
    if run_type is None:
        print(f"Тип запуска {tool['run']['type']} не найден.")
        return
    run_type.run(directory, tool["run"])


if not os.path.exists(".silicon") or not os.path.exists("src"):
    print("Кажется, вы запускаете инструменты не из корневого каталога проекта.")
elif sys.argv.__len__() < 2:
    print("Не указан аргумент. Используйте \"tool help\" для помощи.")
elif sys.argv[1].__eq__("help"):
    # TODO: 24.01.2024 Объединить всю помощь в единую строку с одним вызовом print
    print("Помощь по команде tool:")
    print("\"tool help\" - Помощь по команде tool")
    print("\"tool update\" - Обновить список инструментов")
    print("\"tool <инструмент>\" - Вызвать инструмент")
elif sys.argv[1].__eq__("update"):
    cache()
else:
    run(sys.argv[1])

# TODO: 24.01.2024 Добавить "tool all" или "tool list" для вывода инструментов и их описаний
