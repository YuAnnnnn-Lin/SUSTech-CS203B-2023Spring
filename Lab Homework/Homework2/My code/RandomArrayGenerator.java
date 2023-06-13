import java.io.*;
import java.util.*;

public class RandomArrayGenerator {
    public static void main(String[] args) {
        Random rand = new Random();
        int arraySize = rand.nextInt(1000000) + 1;
        System.out.println(arraySize);

        int[] randArray = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            randArray[i] = rand.nextInt(1000000000 + 1);
        }
        try {
            FileWriter writer = new FileWriter("array.txt");
            writer.write(arraySize + "\n");
            for (int i = 0; i < arraySize; i++) {
                writer.write(randArray[i] + " ");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
