package Philipp;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Grundlegende Sprachelemente
 *
 * @author Daniel Appenmaier
 * @version 1.0
 *
 */
public class D01_JavaBasics {

    public static void arithmeticOperations() {
        int result1 = 5 + 3;
        int result2 = 5 - 3;
        int result3 = 5 * 3;
        int result4 = 5 / 3;
        int result5 = 5 % 3;
        double result6 = (double) 5 / 3;

        double d1 = 4; // widening cast
        int i1 = (int) 4.9; // narrowing cast

        int c = 0;
        c = c + 1;
        c += 1;
        c++;

        double result7 = Math.sqrt(49);

        Random myRandom = new Random();
        int randomNumber = myRandom.nextInt(1, 101);

        System.out.println("result1: " + result1);
        System.out.println("result2: " + result2);
        System.out.println("result3: " + result3);
        System.out.println("result4: " + result4);
        System.out.println("result5: " + result5);
        System.out.println("result6: " + result6);
        System.out.println("d1: " + d1);
        System.out.println("i1: " + i1);
        System.out.println("result7: " + result7);
        System.out.println("randomNumber: " + randomNumber);
    }

    public static void arrays() {
        String[] names = new String[3]; // C/C++: String names[3];
        int[] numbers = {6, 4, 2, -1, 6};

        names[1] = "Max";
        names[names.length - 1] = "Lisa";
        names[0] = "Peter";

        for (int i = 0; i < numbers.length; i++) {
            int number = numbers[i];
            System.out.println(number);
        }

        for (String name : names) {
            System.out.println(name);
        }

        ArrayList<String> names2 = new ArrayList<>();

        names2.add("Max");
        names2.add("Lisa");
        names2.add("Peter");
        names2.add(1, "Fritz");
        names2.remove("Max");
        names2.remove(2);

        int numberOfNames = names2.size();

        for (int i = 0; i < names2.size(); i++) {
            System.out.println(names2.get(i));
        }

        System.out.println("numberOfNames: " + numberOfNames);

    }

    public static void cases() {
        /* Bedingte Anweisungen */
        int age = 17;
        char gender = 'w';

        // Logische Operatoren: &&, ||, !
        // Vergleichsoperatoren: >, <, >=, <=, !=, ==
        // Bedingungs-Operator: ?
        if (gender == 'm' && age >= 12 && age < 18) {
            System.out.println("männlicher Teenager");
        } else if (gender == 'w') {
            System.out.println("weiblich");
        } else {
            System.out.println("divers, männliches Kind, Mann");
        }

        String genderText;
        if (gender == 'm') {
            genderText = "männlich";
        } else if (gender == 'w') {
            genderText = "weiblich";
        } else {
            genderText = "divers";
        }
        System.out.println(genderText);

        genderText = (gender == 'm') ? "männlich" : (gender == 'w') ? "weiblich" : "divers";
        System.out.println(genderText);

        /* Fallunterscheidungen */
        switch (gender) {
            case 'M':
            case 'm':
                genderText = "männlich";
                break;
            case 'W':
            case 'w':
                genderText = "weiblich";
                break;
            default:
                genderText = "divers";
                break;
        }
        System.out.println(genderText);

        genderText = switch (gender) {
            case 'M', 'm' -> "männlich";
            case 'W', 'w' -> "weiblich";
            default -> "divers";
        };
        System.out.println(genderText);

    }

    @SuppressWarnings("resource")
    public static void io() {
        Scanner myScanner = new Scanner(System.in);

        System.out.print("Ganze Zahl: ");
        int int1 = myScanner.nextInt();

        System.out.print("Kommazahl: ");
        double double1 = myScanner.nextDouble();

        System.out.print("Wahrheitswert: ");
        boolean boolean1 = myScanner.nextBoolean();

        System.out.print("Zeichen: ");
        char char1 = myScanner.next().charAt(0);

        System.out.print("Vollständiger Name: ");
        String fullName = myScanner.next();

        System.out.println("int1: " + int1);
        System.out.println("double1: " + double1);
        System.out.println("boolean1: " + boolean1);
        System.out.println("char1: " + char1);
        System.out.println("fullName: " + fullName);

        double d = 5 / 3.0;
        System.out.println("d: " + d);
        System.out.print("d: " + d + "\n");
        System.out.printf("d: %.2f%n", d);
    }

    public static void loops() {
        int i = 0;
        while (i < 10) {
            System.out.println(i + 1);
            i++;
        }

        i = 0;
        do {
            System.out.println(i + 1);
            i++;
        } while (i < 10);

        for (i = 0; i < 1000; i++) {
            if (i % 2 == 0) {
                continue; // break;
            }
            System.out.println(i + 1);
        }
    }

    public static void main(String[] args) {

        /* Primitive Datentypen */
        primitiveDatatypes();
        System.out.println();

        /* Arithmetische Operatoren und Typumwandlungen */
        arithmeticOperations();
        System.out.println();

        /* Bedingte Anweisungen und Fallunterscheidungen */
        cases();
        System.out.println();

        /* Schleifen */
        loops();
        System.out.println();

        /* Zeichenketten */
        strings();
        System.out.println();

        /* Konsoleneingabe und -ausgabe */
        io();
        System.out.println();

        /* Felder (Arrays) */
        arrays();

    }

    public static void primitiveDatatypes() {
        /* Ganze Zahlen: byte, short, int, long */
        int i = 42;

        /* Gleitkommazahlen: float, double */
        double salaryInEuro = 4_999.33;

        /* Boolscher Wahrheitswert: boolean */
        boolean isSuper = true;

        /* Zeichen: char */
        char a1 = 'A';
        char a2 = 65;
        char a3 = 0b1000001; // 0b (Binär), 0 (Oktal), 0x (Hexadezimal)
        char a4 = '\u0041';

        System.out.println("i: " + i); // C: printf("i: %d\n", i); // C++: cout << "i: " << i << endl;
        System.out.println("salsalaryInEuro: " + salaryInEuro);
        System.out.println("isSuper: " + isSuper);
        System.out.println("a1: " + a1);
        System.out.println("a2: " + a2);
        System.out.println("a3: " + a3);
        System.out.println("a4: " + a4);
    }

    public static void strings() {
        String name = "Max"; // new String("Max"); C: char name[] = "Max";

        int length = name.length(); // C: int length = strlen(name);
        String fullName = name + " Maier"; // C/C++: strcat(name, " Maier");

        if (fullName.equals("Max Maier")) {
            System.out.println("beide Objekt sind gleich");
        }
        if (fullName == "Max Maier") {
            System.out.println("beide Objekte sind identisch");
        }

        char charAt6 = fullName.charAt(6);

        System.out.println("length: " + length);
        System.out.println("fullName: " + fullName);
        System.out.println("charAt6: " + charAt6);
    }

}