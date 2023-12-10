import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

/**
 * Написать класс с двумя методами:
 * 1. принимает объекты, имплементирующие интерфейс serializable, и сохраняющие их в файл. Название файл - class.getName() + "_" + UUID.randomUUID().toString()
 * 2. принимает строку вида class.getName() + "_" + UUID.randomUUID().toString() и загружает объект из файла и удаляет этот файл.
 * <p>
 * Что делать в ситуациях, когда файла нет или в нем лежит некорректные данные - подумать самостоятельно.
 */

public class Serializator {
    public static void serialize(Serializable obj) {
        Path path = Path.of(obj.getClass().getName() + "_" + UUID.randomUUID());
        try {
            Path file = Files.createFile(path);
            try (FileOutputStream fileOutputStream = new FileOutputStream(String.valueOf(file))) {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(obj);
                objectOutputStream.close();
                System.out.println("Файл был успешно записан");
            }
        } catch (IOException e) {
            throw new RuntimeException("Произошла ошибка при записи файла");
        }
    }

    public static Object deserialize(String path) {
        Path file = checkPath(path);
        try(FileInputStream fileInputStream = new FileInputStream(String.valueOf(file))) {
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Object obj = objectInputStream.readObject();
            objectInputStream.close();
            Files.delete(file);
            return obj;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Шеф, усё пропало...");
        }


    }

    // Метод для проверки правильности ввода пути к файлу
    private static Path checkPath(String path) {
        // Проверяем наличие файла
        Path file = Path.of(path);
        if (!Files.exists(file)) {
            throw new RuntimeException("Файл не найден");
        }
        // Проверяем наличие указанного класса
        String[] strArr = path.split("_");
        try {
            Class<?> name = Class.forName(strArr[0]);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Класс, которому принадлежит объект, не может быть найден");
        }
        return file;
    }
}

