import java.util.*;
import java.util.regex.*;
import java.util.concurrent.*;

public class TextProcessor {
    private static String text = "Hi, I'm A.\n\nContact me at a@short.com or support@example.org.\n\nYou can also reach us via our assistant: info@company.net.\n\nCall us at 01012121212 or 01156789012 or 01234567890 or 01512345678.\n\nMy friends: Al, Bo, Ann, Joe, Z, K, Moe.\n\nRandom words: supercalifragilisticexpialidocious, ok, i, no.";
    private static List<String> names = new ArrayList<>();
    private static String modifiedText;
    private static List<String> phoneNumbers = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        // Create threads for each task
        Thread nameThread = new Thread(() -> findNames(text));
        Thread emailThread = new Thread(() -> replaceEmails(text));
        Thread phoneThread = new Thread(() -> extractPhoneNumbers(text));

        // Start the threads
        nameThread.start();
        emailThread.start();
        phoneThread.start();

        // Wait for all threads to finish
        nameThread.join();
        emailThread.join();
        phoneThread.join();

        // Print the results
        System.out.println("Names found: " + names);
        System.out.println("Modified text: " + modifiedText);
        System.out.println("Phone numbers found: " + phoneNumbers);
    }

    private static void findNames(String text) {
        Pattern namePattern = Pattern.compile("\\b[A-Z][a-z]{0,1}\\b");
        Matcher matcher = namePattern.matcher(text);
        while (matcher.find()) {
            names.add(matcher.group());
        }
    }

    private static void replaceEmails(String text) {
        modifiedText = text.replaceAll("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", "[EMAIL_HIDDEN]");
    }

    private static void extractPhoneNumbers(String text) {
        Pattern phonePattern = Pattern.compile("\\b01[0-9]{9}\\b");
        Matcher matcher = phonePattern.matcher(text);
        while (matcher.find()) {
            phoneNumbers.add(matcher.group());
        }
    }
}

