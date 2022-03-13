package _04_defaultCompetition;

public interface Interface3 {

    default void doSomething() {
        System.out.println("Interface3 do Something");
    }
}

interface Interface4 extends Interface3 {

    @Override
    default void doSomething() {
        System.out.println("Interface4 do Something");;
    }
}
