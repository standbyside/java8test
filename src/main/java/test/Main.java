package test;

public class Main {

    public static void main(String[] args) {

        String str = "1>=2 && 1=2";
        System.out.println(str.replaceAll("(?<!(=|>|<))=(?!=)", "=="));
    }
}