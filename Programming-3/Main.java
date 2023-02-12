import java.util.concurrent.ThreadLocalRandom;
import java.io.*;
import java.util.Arrays;
import java.time.*;
import java.lang.*;

class Main {
  public static void main(String[] args) {
    System.out.println("Hello world!");
    
    int[] testArray = new int[] { 10, 3, 5, 1, 6, 2, 4, 7, 8, 9 };
    
    System.out.println("Array before merge sort:");
    System.out.println(Arrays.toString(testArray));
    MergeSort(testArray, 0, testArray.length - 1);
    
    System.out.println("Array after merge sort:");
    System.out.println(Arrays.toString(testArray));

    testArray = new int[] { 10, 3, 5, 1, 6, 2, 4, 7, 8, 9 };
    System.out.println("Array before insertion sort:");
    System.out.println(Arrays.toString(testArray));
    InsertionSort(testArray);
    
    System.out.println("Array after insertion sort:");
    System.out.println(Arrays.toString(testArray));
    
  }

  public static void InsertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
  }

  public static void MergeSort(int[] A, int p, int r) {
    if (p < r) {
      int q = (int) Math.floor((p + r) / 2);
      MergeSort(A, p, q);
      MergeSort(A, q + 1, r);
      Merge(A, p, q, r);
    }
  }

  public static void Merge(int[] A, int p, int q, int r) {
    int n1 = q - p + 1;
    int n2 = r - q;

    int[] L = new int[n1 + 1 + 1]; // extra index
    int[] R = new int[n2 + 1 + 1]; // extra index

    for (int i = 1; i <= n1; i++) {
      L[i] = A[p + i - 1];
    }
    for (int j = 1; j <= n2; j++) {
      R[j] = A[q + j];
    }

    L[n1 + 1] = Integer.MAX_VALUE;
    R[n2 + 1] = Integer.MAX_VALUE;

    int i = 1;
    int j = 1;

    for (int k = p; k <= r; k++) {
      if (L[i] <= R[j]) {
        A[k] = L[i];
        i = i + 1;
      } else {
        A[k] = R[j];
        j = j + 1;
      }
    }
  }
}