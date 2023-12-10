

public class Homework {
    public static void main(String[] args) {

        // Создаем объект класса Person и записываем его в файл
        Person person = new Person("Alex");
        Serializator.serialize(person);

        Person person1 = (Person) Serializator.deserialize("Person_7aba9341-775e-4dcb-a4ce-4937fde0791c");
        System.out.println(person1);
    }
}
