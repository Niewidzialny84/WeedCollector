package niewidzialny84.github.weedcollector.utils;

import java.util.Random;

public final class RandomGenerator
{
    private static final Random randomGenerator = new Random();

    public static int randomInt(int min,int max) {
        return randomGenerator.nextInt(max+1-min) + min;
    }
}
