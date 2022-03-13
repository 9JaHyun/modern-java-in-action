package _02_static;

public interface StaticInterface {

    static void staticMethod1() {
        System.out.println("정적 메서드를 통해 구현할 수 있다.");
    }

    void method2();
}

// 문제점은 정저메스드는 오버라이드가 불가...
class StaticInterfaceImpl implements StaticInterface {

    @Override
    public void method2() {
        System.out.println(this.getClass().getName() + "가 오버라이드한 메서드ㄹ");
    }
}
