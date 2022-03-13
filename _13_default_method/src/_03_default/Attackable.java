package _03_default;

public interface Attackable {
    default void attack() {
        System.out.println("공격!");
    }
}
