package _04_defaultCompetition;

public class InterfaceImpl2 implements Interface3, Interface4{

    @Override
    public void doSomething() {
        System.out.println("do Something by InterfaceImpl2");
    }

    public static void main(String[] args) {
        InterfaceImpl2 i = new InterfaceImpl2();
        i.doSomething();
    }
}
