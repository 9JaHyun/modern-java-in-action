package _03_default;

public interface Movable {
    default void move() {
        System.out.println("이동!");
    }
}
