package utils;

import java.util.Random;
import java.util.UUID;
public class Generator {
    public static String randomString() {
        return UUID.randomUUID().toString().replaceAll("-", "").replaceAll("[0-9]", "");
    }

    public static int randomInt() {
        return new Random().nextInt();
    }
}
