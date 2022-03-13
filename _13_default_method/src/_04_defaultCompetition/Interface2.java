package _04_defaultCompetition;

public interface Interface2 {

    default void doSomething() {
        System.out.println("Interface2 do Something");
    }
}
