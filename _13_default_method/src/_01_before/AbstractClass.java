package _01_before;

// 과거의 인터페이스가 하지 못하는 기본형 로직을 쓸 수 있었다.
public abstract class AbstractClass {

    // 꼭 각 클래스별로 분리를 시키려면
    public abstract void absMethod1();

    public void defaultMethod1(){
        System.out.println("오버라이드를 안할 시 이 로직이 실행됩니다.");
    }
}

class ExtendClass extends AbstractClass {

    @Override
    public void absMethod1() {
        System.out.println(this.getClass().getName() + "에서 구현한 메서드");
    }

    // 필요에 따라 오버라이드 할 수 있다.
    @Override
    public void defaultMethod1() {
        System.out.println(this.getClass().getName() + "에서 오버라이드한 메서드");
    }
}

class ExtendClass2 extends AbstractClass {


    @Override
    public void absMethod1() {
        System.out.println(this.getClass().getName() + "에서 구현한 메서드");
    }
}
