package _03_default;

public interface Jumpable {
    default void jump() {
        System.out.println("점프!");
    }
}
