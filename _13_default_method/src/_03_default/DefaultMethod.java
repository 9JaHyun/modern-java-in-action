package _03_default;

public interface DefaultMethod {

    default void defaultMethod1() {
        System.out.println("Default 메서드를 통해 구현할 수 있다.");
    }

    void method2();
}

class DefaultMethodImpl implements DefaultMethod{

    @Override
    public void defaultMethod1() {
        // 구현의 선택지를 부여
        DefaultMethod.super.defaultMethod1();
    }

    @Override
    public void method2() {
        System.out.println(this.getClass().getName() + "에서 오버라이드한 메서드");
    }
}