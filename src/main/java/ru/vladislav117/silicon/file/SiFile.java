package ru.vladislav117.silicon.file;

import com.google.gson.JsonElement;
import org.bukkit.configuration.file.YamlConfiguration;
import ru.vladislav117.silicon.json.SiJson;
import ru.vladislav117.silicon.node.SiNode;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

/**
 * Класс, представляющий собой файл или каталог. Является классом-обёрткой для File.
 */
public class SiFile {
    protected File file;

    /**
     * Создание нового файла.
     *
     * @param file  Родительский файл/каталог
     * @param child Дочерний файл/каталог
     */
    public SiFile(File file, String child) {
        this.file = new File(file, child);
    }

    /**
     * Создание нового файла.
     *
     * @param file Файл/каталог
     */
    public SiFile(File file) {
        this.file = file;
    }

    /**
     * Создание нового файла.
     *
     * @param file  Родительский файл/каталог
     * @param child Дочерний файл/каталог
     */
    public SiFile(SiFile file, String child) {
        this.file = new File(file.file, child);
    }

    /**
     * Создание нового файла.
     *
     * @param file Файл/каталог
     */
    public SiFile(SiFile file) {
        this.file = file.file;
    }

    /**
     * Создание нового файла.
     *
     * @param path  Родительский файл/каталог
     * @param child Дочерний файл/каталог
     */
    public SiFile(String path, String child) {
        this.file = new File(path, child);
    }

    /**
     * Создание нового файла.
     *
     * @param path Файл/каталог
     */
    public SiFile(String path) {
        this.file = new File(path);
    }

    /**
     * Создание нового дочернего файла.
     *
     * @param child Дочерний файл/каталог
     * @return Дочерний файл.
     */
    public SiFile getChild(String child) {
        return new SiFile(this, child);
    }

    /**
     * Создание всех каталогов по пути файла.
     *
     * @return Этот же файл.
     */
    public SiFile mkdirs() {
        file.mkdirs();
        return this;
    }

    /**
     * Проверка, существует ли файл/каталог.
     *
     * @return Существует ли файл/каталог.
     */
    public boolean exists() {
        return file.exists();
    }

    /**
     * Чтение байтов из файла.
     *
     * @return Прочитанные байты.
     */
    public byte[] readBytes() {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Запись байтов в файл.
     *
     * @param bytes Байты, которые будут записаны
     * @return Этот же файл.
     */
    public SiFile writeBytes(byte[] bytes) {
        try {
            com.google.common.io.Files.write(bytes, file);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return this;
    }

    /**
     * Чтение строки из файла.
     * <p>
     * ВАЖНО! Будет создана новая строка, которая игнорирует строковый кэш.
     *
     * @return Прочитанная строка.
     */
    public String readString() {
        return new String(readBytes(), StandardCharsets.UTF_8);
    }

    /**
     * Запись строки в файл.
     *
     * @param string Строка, которая будет записана
     * @return Этот же файл.
     */
    public SiFile writeString(String string) {
        return writeBytes(string.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Чтение Json из файла.
     *
     * @return Прочитанный Json.
     */
    public JsonElement readJson() {
        return SiJson.parse(readString());
    }

    /**
     * Запись Json в файл.
     *
     * @param json Json, который будет записан
     * @return Этот же файл.
     */
    public SiFile writeJson(JsonElement json) {
        return writeString(json.toString());
    }

    /**
     * Чтение узла из файла.
     *
     * @return Прочитанный узел.
     */
    public SiNode readNode() {
        return SiNode.parseJson(readJson());
    }

    /**
     * Запись узла в файл.
     *
     * @return Этот же файл.
     */
    public SiFile writeNode(SiNode node) {
        return writeJson(node.toJson());
    }

    /**
     * Чтение Yaml из файла.
     *
     * @return Yaml.
     */
    public YamlConfiguration readYaml() {
        return YamlConfiguration.loadConfiguration(file);
    }

    /**
     * Запись Yaml в файл.
     *
     * @param configuration Yaml
     * @return Этот же файл.
     */
    public SiFile writeYaml(YamlConfiguration configuration) {
        try {
            configuration.save(file);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return this;
    }

    /**
     * Удаление файла.
     *
     * @return Этот же файл.
     */
    public SiFile delete() {
        file.delete();
        return this;
    }

    /**
     * Получение всех файлов в директории.
     *
     * @return Все файлы в директории.
     */
    public ArrayList<SiFile> getEntryFiles() {
        ArrayList<SiFile> files = new ArrayList<>();
        for (File entryFile : file.listFiles()) {
            if (!entryFile.isDirectory()) {
                files.add(new SiFile(entryFile));
            }
        }
        return files;
    }

    /**
     * Чтение всех json-файлов директории как узлов.
     *
     * @return Все json-файлы в директории как узлы.
     */
    public ArrayList<SiNode> readAllJsonNodeFiles() {
        ArrayList<SiNode> nodes = new ArrayList<>();
        for (File entryFile : file.listFiles()) {
            if (!entryFile.isDirectory() && entryFile.getName().endsWith(".json")) {
                nodes.add(new SiFile(entryFile).readNode());
            }
        }
        return nodes;
    }

    @Override
    public String toString() {
        return file.toString();
    }
}
