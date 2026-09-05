class Vowels {

    static int getCount(String str) {
        String[] strArr = {str.split("")};
        int count = 0;

        for(int i = 0; i < strArr.length; i++) {
            switch (strArr[i]) {
                case "a": count++; break;
                case "e": count++; break;
                case "i": count++; break;
                case "o": count++; break;
                case "u": count++; break;
            }
        }

        return count;
    }

    public static void main(String[] args) {
        test("abracadabra", 5);
        test("", 0);
        test("pear tree", 4);
        test("o a kak ushakov lil vo kashu kakao", 13);
    }

    static void test(String input, int expected) {
        int actual = getCount(input);

        if (actual == expected) {
            System.out.println("PASS");
        } else {
            System.out.println("FAIL - expected " + expected + ", got " + actual);
        }
    }
}