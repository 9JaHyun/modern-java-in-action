package _03_default;

public class Character implements Attackable, Jumpable, Movable{

    public static void main(String[] args) {
        Character character = new Character();
        character.attack();
        character.move();
        character.jump();
    }
}
