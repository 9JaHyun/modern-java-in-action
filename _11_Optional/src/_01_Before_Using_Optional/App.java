package _01_Before_Using_Optional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {

    public static void main(String[] args) {
        Person person1 = new Person(new Car(new Insurance("A보험")));
        Person person2 = new Person(new Car(new Insurance("B보험")));
        Person person3 = new Person();  // NullPointer
        Person person4 = new Person(new Car(new Insurance("C보험")));
        Person person5 = new Person();  // NullPointer

        List<Person> personList = Arrays.asList(person1, person2, person3, person4, person5);

        personList.forEach(App::getCarInsuranceName);
    }

    private static String getCarInsuranceName(Person person) {
        return person.getCar().getInsurance().getName();
    }

    private static String getCarInsuranceNameV1(Person person) {
        if (person != null) {
            Car car = person.getCar();
            if (car != null) {
                Insurance insurance = car.getInsurance();
                if (insurance != null) {
                    return insurance.getName();
                }
            }
        }
        return "Unknown";
    }

}
