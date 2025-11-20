package utils;

import java.util.concurrent.ThreadLocalRandom;

public class TestData {

    private static final String[] FIRST_NAMES = {
            "Leonard", "Martin", "Oliver", "Daniel", "Samuel",
            "James", "Henry", "Lucas", "Mason", "Ethan",
            "Noah", "Logan", "Felix", "Jonathan", "Caleb"
    };

    private static final String[] LAST_NAMES = {
            "Miller", "Anderson", "Bennett", "Harris", "Thompson",
            "Clark", "Mitchell", "Brooks", "Parker", "Foster",
            "Stewart", "Gibson", "Higgins", "Wright", "Peterson"
    };

    private static String pick(String[] array) {
        return array[ThreadLocalRandom.current().nextInt(array.length)];
    }

    public static String randomFirstName() {
        return pick(FIRST_NAMES);
    }

    public static String randomLastName() {
        return pick(LAST_NAMES);
    }

    public static String randomDisplayName(String first, String last) {
        return first + " " + last;
    }
}
