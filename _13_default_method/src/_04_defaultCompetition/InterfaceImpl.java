package _04_defaultCompetition;

public class InterfaceImpl implements Interface1, Interface2 {

    // 어느 메서드를 사용할 지 결정
    @Override
    public void doSomething() {
        Interface1.super.doSomething();
//        Interface2.super.doSomething();
    }

    public static void main(String[] args) {
        InterfaceImpl i = new InterfaceImpl();
        i.doSomething();
    }
}
