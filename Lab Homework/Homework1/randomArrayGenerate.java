import java.io.*;

public class randomArrayGenerate {
    public static void main(String[] args) {
        int n = (int) (Math.random() * 100) + 1;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = (int) (Math.random() * 101);
        }
        try {
            FileWriter writer = new FileWriter("array.txt");
            writer.write(n + "\n");
            for (int i = 0; i < n; i++) {
                writer.write(arr[i] + " ");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}