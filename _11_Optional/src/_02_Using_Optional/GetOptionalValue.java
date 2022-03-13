package _02_Using_Optional;

import java.util.Optional;

public class GetOptionalValue {

    public static void main(String[] args) {
        GetOptionalValue getOptionalValue = new GetOptionalValue();
        getOptionalValue.usingMap();
        getOptionalValue.usingFlatMap();
    }
    private void usingMap() {
        Optional<Car> car1 = Optional.ofNullable(new Car(new Insurance("보험1")));

        // Map을 통해 값을 가져올 수 있다.
        Optional<String> insuranceName = car1.map(Car::getInsurance)
              .map(Insurance::getName);
        System.out.println(insuranceName.get());
    }

    private void usingFlatMap() {
        String message = new Matryoshka().getMatryoshka1()
              .flatMap(Matryoshka1::getMatryoshka2)
              .flatMap(Matryoshka2::getMatryoshka3)
              .map(Matryoshka3::getMessage).get();
    }

}
class Matryoshka{

    private Optional<Matryoshka1> matryoshka1;

    public Optional<Matryoshka1> getMatryoshka1() {
        return matryoshka1;
    }
}
class Matryoshka1{

    private Optional<Matryoshka2> matryoshka2;

    public Optional<Matryoshka2> getMatryoshka2() {
        return matryoshka2;
    }
}
class Matryoshka2{

    private Optional<Matryoshka3> matryoshka3;

    public Optional<Matryoshka3> getMatryoshka3() {
        return matryoshka3;
    }
}

class Matryoshka3{

    private String message = "이게 끝!";

    public String getMessage() {
        return message;
    }
}

