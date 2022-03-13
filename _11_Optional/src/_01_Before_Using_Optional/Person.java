package _01_Before_Using_Optional;

public class Person {
    private Car car;

    public Person() {
    }

    public Person(Car car) {
        this.car = car;
    }

    public Car getCar() {
        return car;
    }
}