package _02_Using_Optional;

import java.util.List;
import java.util.Optional;

public class DefaultOptional {

    public static void main(String[] args) {
        DefaultOptional defaultOptional = new DefaultOptional();
        defaultOptional.orElse();
    }

    private void orElse() {
        Person person1 = new Person(Optional.ofNullable(new Car(new Insurance("보험1"))));
        Person person2 = new Person(Optional.ofNullable(null));
        Person person3 = new Person(Optional.ofNullable(new Car(new Insurance("보험2"))));
        Person person4 = new Person(Optional.ofNullable(null));

        List<Person> people = List.of(person1, person2, person3, person4);

        people.forEach(p ->
              System.out.println(p.getCar()
                    .map(Car::getInsurance)
                    .map(Insurance::getName)
                    .orElse("무보험입니다.")
              ));
    }

}
