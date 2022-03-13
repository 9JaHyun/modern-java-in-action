package _01_Before_Using_Optional;

public class Car {
    private Insurance insurance;

    public Car(Insurance insurance) {
        this.insurance = insurance;
    }

    public Insurance getInsurance() {
        return insurance;
    }
}
