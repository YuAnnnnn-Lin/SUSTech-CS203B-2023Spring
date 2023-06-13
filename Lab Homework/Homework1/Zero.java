import java.util.Scanner;
import java.io.*;

public class Zero {

    //找到数组中最小非0元素x
    public static int findSmallestNonzero(int[] arr) {
        int x = 101;
        for (int j = 0; j < arr.length; j++) {
            if (arr[j] < x && arr[j] > 0) {
                x = arr[j];
            }
        }
        return x;
    }

    //判断是否有正元素
    public static boolean whetherPositive(int[] arr) {
        int a = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 0) {
                a++;
            }
        }
        return (a > 0);
    }

    //对数组做减法得到新数组
    public static int[] subtractToGetNewArray(int[] arr) {
        int[] arr2 = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            arr2[i] = arr[i] - findSmallestNonzero(arr);
        }
        return arr2;
    }

    public static void main(String[] args) {
        //输入数组
        File file = new File("array.txt");
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        int n = sc.nextInt();
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        sc.close();

        //循环操作
        int ans = 0;
        if (whetherPositive(arr)) {
            for (int i = 0; i < arr.length; i++) {
                if (whetherPositive(arr)) {
                    arr = subtractToGetNewArray(arr);
                    ans++;
                }
            }
            System.out.println(ans);
        } else {
            System.out.println(0);
        }
    }
}