from __future__ import annotations
import os

# ================================
# Конфигурация
# ================================

DIRECTORY = "src"  # Директория с исходным кодом
DOT_LIKE_SYMBOLS = ".!?"  # Знаки препинания, означающие конец предложения
ENDING_SYMBOLS = DOT_LIKE_SYMBOLS + ",;"  # Знаки препинания, означающие конец синтаксической конструкции


# ================================
# ================================
# ================================

class Checker:
    splitter = "=" * 52

    def __init__(self, rules: list[Rule, ...], files: Files):
        self.__rules = rules
        self.__files = files

    def rules(self) -> list[Rule, ...]:
        return self.__rules

    def files(self) -> Files:
        return self.__files

    def check(self) -> bool:
        print(self.splitter)
        print("Поиск ошибок...")
        found_any_error_in_files = False
        for file in self.__files.files():
            found_any_error_in_file = False
            file_errors = list()
            for line_index in range(file.lines_amount()):
                line = file.line(line_index)
                for rule in self.__rules:
                    if rule.line_checker()(line):
                        continue
                    file_errors.append(f"Строка {line_index + 1}: {rule.message()}")
                    found_any_error_in_file = True
                    found_any_error_in_files = True
            if found_any_error_in_file:
                print(self.splitter)
                print(f"* Файл: {file.path()}")
                print("Найдены следующие ошибки:")
                for file_error in file_errors:
                    print(file_error)
                print(self.splitter)
        if found_any_error_in_files:
            print("Итог: При проверке обнаружены ошибки!")
        else:
            print("Итог: Ошибок не обнаружено")
        print(self.splitter)
        return found_any_error_in_files


class Rule:
    def __init__(self, message: str, line_checker: callable):
        self.__message = message
        self.__line_checker = line_checker

    def message(self) -> str:
        return self.__message

    def line_checker(self) -> callable:
        return self.__line_checker


class Files:
    def __init__(self, directory: str, file_filter: callable):
        self.__directory = directory
        self.__file_filter = file_filter
        self.__files = list()

        for r, _, found_files in os.walk(self.__directory):
            for file in found_files:
                file_path = os.path.join(r, file)
                if file_filter(file_path):
                    fp = open(file_path, "rt", encoding="utf-8")
                    file_text = fp.read()
                    fp.close()
                    self.__files.append(File(
                        path=file_path,
                        raw_text=file_text,
                        files=self
                    ))

    def directory(self) -> str:
        return self.__directory

    def file_filter(self) -> callable:
        return self.__file_filter

    def files(self) -> list[File, ...]:
        return self.__files


class File:
    def __init__(self, path: str, raw_text: str, files: Files):
        self.__path = path.replace("\\", "/")
        self.__raw_text = raw_text
        self.__files = files
        self.__lines = list()
        raw_lines = self.__raw_text.split("\n")
        line_index = 0
        for raw_line in raw_lines:
            self.__lines.append(Line(
                index=line_index,
                raw_text=raw_line,
                file=self
            ))
            line_index += 1
        self.__lines_amount = line_index

    def path(self) -> str:
        return self.__path

    def raw_text(self) -> str:
        return self.__raw_text

    def files(self) -> Files:
        return self.__files

    def line(self, index: int) -> Line | None:
        if 0 <= index < self.__lines_amount:
            return self.__lines[index]
        return None

    def lines_amount(self) -> int:
        return self.__lines_amount


class Line:
    def __init__(self, index: int, raw_text: str, file: File):
        self.__index = index
        self.__raw_text = raw_text
        self.__file = file

        self.__text = raw_text.replace("\n", "")
        self.__spaceless_text = self.__text.replace("\t", "").replace(" ", "")
        self.__is_documentation = self.__spaceless_text.startswith("*")

    def index(self) -> int:
        return self.__index

    def raw_text(self) -> str:
        return self.__raw_text

    def file(self) -> File:
        return self.__file

    def text(self) -> str:
        return self.__text

    def spaceless_text(self) -> str:
        return self.__spaceless_text

    def is_documentation(self) -> bool:
        return self.__is_documentation

    def next_line(self, offset: int = 1) -> Line | None:
        return self.__file.line(self.__index + offset)

    def previous_line(self, offset: int = 1) -> Line | None:
        return self.__file.line(self.__index - offset)


def file_filter__java(file_path: str) -> bool:
    return file_path.endswith(".java")


def checker__override(line: Line) -> bool:
    if not line.spaceless_text().startswith("@Override"):
        return True
    previous_line = line.previous_line()
    if previous_line is None:
        return True
    if previous_line.spaceless_text().startswith("@"):
        return False
    return True


def checker__annotation(line: Line) -> bool:
    if line.spaceless_text().count("public@") > 0 or line.spaceless_text().count("protected@") > 0:
        return False
    return True


def checker__notnull(line: Line) -> bool:
    # Disabled
    if True:
        return True
    if line.text().count("@NotNull") > 0 or line.text().count("@Notnull") > 0:
        return False
    return True


def checker__nullable(line: Line) -> bool:
    if line.text().count("javax.annotation.Nullable") > 0:
        return False
    return True


def text_endswith_dot_like_symbols(text: str) -> bool:
    for symbol in DOT_LIKE_SYMBOLS:
        if text.endswith(symbol):
            return True
    return False


def text_endswith_ending_symbols(text: str) -> bool:
    for symbol in ENDING_SYMBOLS:
        if text.endswith(symbol):
            return True
    return False


def checker__documentation__dot_after_param(line: Line) -> bool:
    if not line.is_documentation():
        return True
    if line.spaceless_text().count("@param") == 0:
        return True
    if text_endswith_ending_symbols(line.text()):
        return False
    return True


def checker__documentation__dot_after_return(line: Line) -> bool:
    if not line.is_documentation():
        return True
    if line.spaceless_text().count("@return") == 0:
        return True
    if not text_endswith_dot_like_symbols(line.text()):
        return False
    return True


def checker__documentation__dot_after_line(line: Line) -> bool:
    if not line.is_documentation():
        return True
    if line.spaceless_text() == "*/" or line.spaceless_text() == "*<p>" or line.spaceless_text() == "*":
        return True
    if line.spaceless_text().startswith("*@"):
        return True
    if not text_endswith_ending_symbols(line.text()):
        return False
    return True


def checker__documentation__empty_line(line: Line) -> bool:
    if not line.is_documentation():
        return True
    if not line.spaceless_text().replace("*", "", 1).startswith("@"):
        return True
    previous_line = line.previous_line()
    if previous_line is None:
        return True
    if not previous_line.is_documentation():
        return True
    if previous_line.spaceless_text().replace("*", "", 1).startswith("@"):
        return True
    if previous_line.spaceless_text() == "*":
        return True
    return False


def checker__documentation__code(line: Line) -> bool:
    if not line.is_documentation():
        return True
    if line.spaceless_text().count("{@code") == 0:
        return True
    return False


checker = Checker(
    rules=[
        Rule(
            message="Над @Override не должны находиться другие другие аннотации",
            line_checker=checker__override
        ),
        Rule(
            message="Аннотация должна быть над методом",
            line_checker=checker__annotation
        ),
        Rule(
            message="@NotNull и @Notnull не должны использоваться",
            line_checker=checker__notnull
        ),
        Rule(
            message="Все @Nullable должны быть из org.jetbrains.annotations",
            line_checker=checker__nullable
        ),
        Rule(
            message="После @param не должно быть знаков окончания синтаксических конструкций",
            line_checker=checker__documentation__dot_after_param
        ),
        Rule(
            message="После @return должен быть знак окончания предложения",
            line_checker=checker__documentation__dot_after_return
        ),
        Rule(
            message="После строки документации должен быть знак окончания синтаксической конструкции",
            line_checker=checker__documentation__dot_after_line
        ),
        Rule(
            message="Перед @param или @return должна быть пустая строка документации",
            line_checker=checker__documentation__empty_line
        ),
        Rule(
            message="Конструкции {@code } больше не должны использоваться",
            line_checker=checker__documentation__code
        )
    ],
    files=Files(
        directory=DIRECTORY,
        file_filter=file_filter__java
    )
)

checker.check()
