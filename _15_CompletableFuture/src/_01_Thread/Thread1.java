package _01_Thread;

public class Thread1 {

    private static long[] nums = new long[1_000_000];

    private static void init() {
        for (int i = 0; i < nums.length; i++) {
            nums[i] = i;
        }
    }

    public static void main(String[] args) {
        Thread1 thread1 = new Thread1();
        init();
        thread1.nonThread();
    }

    private void nonThread() {
        long start = System.currentTimeMillis();
        Long sum = 0L;
        for (int i = 0; i < 1_000_000; i++) {
            sum += i;
        }
        System.out.println("running time: " + (System.currentTimeMillis() - start));
        System.out.println("result: " + sum);
    }

}
