package utils;

import java.util.Scanner;

public class ConsoleHelper {

    public static void printMessage(String message) {
        System.out.println(message);
    }

    public static String readString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
