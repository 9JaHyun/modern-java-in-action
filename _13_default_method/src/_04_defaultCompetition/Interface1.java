package _04_defaultCompetition;

public interface Interface1 {
    default void doSomething() {
        System.out.println("Interface1 do Something");
    }

}
