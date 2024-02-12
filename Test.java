public class Test {

    public static void assertB(boolean condition) {
        if (!condition) {
            throw new RuntimeException();
        }
    }
    public static void main(String[] args) {
        test();
    }

    public static void test() {
        assertB(true);
    }
}
