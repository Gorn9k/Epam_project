import entity.Person;
import entity.PersonType;

public class Main {

    public static void main(String[] args) {
        Person person = new Person("Alex232", PersonType.DISPATCHER);
        System.out.println(person);
    }
}
