import java.util.*;

public class Problem1 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = input.nextInt();
        }
        System.out.println(mergeSortCount(arr, 0, n - 1));
    }

    public static int mergeSortCount(int[] arr, int left, int right) {
        int swapCount = 0;
        if (left < right) {
            int mid = (left + right) / 2;
            swapCount += mergeSortCount(arr, left, mid);
            swapCount += mergeSortCount(arr, mid + 1, right);
            swapCount += merge(arr, left, mid, right);
        }
        return swapCount;
    }

    public static int merge(int[] arr, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;
        int swapCount = 0;
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
                swapCount += mid - i + 1;
            }
        }
        while (i <= mid) {
            temp[k++] = arr[i++];
        }
        while (j <= right) {
            temp[k++] = arr[j++];
        }
        for (i = left, k = 0; i <= right; i++, k++) {
            arr[i] = temp[k];
        }
        return swapCount;
    }
}