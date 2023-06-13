import java.io.*;
import java.util.*;

public class RandomGenerator {

    public static void main(String[] args) {
        int n = getRandomNumberInRange(1, 10000);
        Integer[] array = new Integer[n];
        for (int i = 0; i < n; i++) {
            array[i] = getRandomNumberInRange(-1, (int) Math.pow(10, 9));
        }
        int k = getRandomNumberInRange(1, 100);
        Integer[] kArray = new Integer[k];
        for (int i = 0; i < k; i++) {
            kArray[i] = getRandomNumberInRange(1, (int) Math.pow(10, 9));
        }
        try {
            File file = new File("output.txt");
            FileWriter writer = new FileWriter(file);
            writer.write(n + "\n");
            for (int i = 0; i < n; i++) {
                writer.write(array[i] + " ");
            }
            writer.write("\n" + k + "\n");
            for (int i = 0; i < k; i++) {
                writer.write(kArray[i] + " ");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
