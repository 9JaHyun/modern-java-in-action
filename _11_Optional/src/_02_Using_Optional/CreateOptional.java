package _02_Using_Optional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CreateOptional {

    public static void main(String[] args) {
        CreateOptional createOptional = new CreateOptional();
        createOptional.nullableOptional();
    }
    private Optional<Car> createEmptyOptional() {
        return Optional.empty();
    }

    private Optional<Car> createOptional() {
        return Optional.of(new Car(new Insurance("보험A")));
    }

    private void nullableOptional() {
        Car car1 = new Car(new Insurance("보험A"));
        Car car2 = null;
        Car car3 = null;
        Car car4 = new Car(new Insurance("보험B"));

        List<Optional<Car>> optionalCars = new ArrayList<>();
        optionalCars.add(Optional.ofNullable(car1));    // Optional<Car>
        optionalCars.add(Optional.ofNullable(car2));    // Optional.empty
        optionalCars.add(Optional.ofNullable(car3));    // Optional.empty
        optionalCars.add(Optional.ofNullable(car4));    // Optional<Car<

        System.out.println(optionalCars);
    }
}
