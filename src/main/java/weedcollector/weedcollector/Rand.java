package weedcollector.weedcollector;

import java.util.Random;

public final class Rand {
    public static int randomInt(int min,int max) {
        Random randomGenerator = new Random();
        return randomGenerator.nextInt(max+1-min) + min;
    }
}
