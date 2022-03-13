package _03_default;

public class Rabbit implements Jumpable, Movable{

    @Override
    public void jump() {
        System.out.println("더욱 높게 뜁니다");
    }

    public static void main(String[] args) {
        Rabbit rabbit = new Rabbit();
        rabbit.move();
        rabbit.jump();
    }
}
