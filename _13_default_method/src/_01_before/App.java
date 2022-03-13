package _01_before;

public class App {
    public static void main(String[] args) {
        ExtendClass extendClass = new ExtendClass();
        ExtendClass2 extendClass2 = new ExtendClass2();

        extendClass.defaultMethod1();   // 오버라이드된 메서드 실행
        extendClass2.defaultMethod1();  // 기본 메서드 실행
    }
}
