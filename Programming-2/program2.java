import java.util.concurrent.ThreadLocalRandom;
import java.io.*;
import java.util.Arrays;
import java.time.*;
import java.lang.*;

class program2 {
  public static int NPlus1 = 550001;
  public static int REGULAR_N = 550000; 

  public static void main(String[] args) {

    int[] testArray = new int[] { 10, 3, 5, 1, 6, 2, 4, 7, 8, 9 };
    System.out.println("Array before merge sort:");
    System.out.println(Arrays.toString(testArray));
    MergeSort(testArray, 0, testArray.length - 1);
    System.out.println("Array after merge sort:");
    System.out.println(Arrays.toString(testArray));

    collectData();

  }

  public static void collectData() {
    try (PrintWriter writer = new PrintWriter("results.csv")) {
      // write the header into the csv file
      String header = "n, rootN, nRootLogN, nLogN \n";
      writer.write(header);

      // Create gigantic test array with random numbers
      int[] giantArray = new int[NPlus1];
      for (int i = 1; i < REGULAR_N; i++) {
        int randNum = ThreadLocalRandom.current().nextInt(0, 2147483647);
        giantArray[i] = randNum;
      }

      // loop through different lengths of arrays and print out time
      int[] A = new int[NPlus1];
      for (int n = 5000; n < REGULAR_N; n += 1500) {
        System.arraycopy(giantArray, 1, A, 1, REGULAR_N);
        // start time
        long startTime = System.nanoTime();
        MergeSort(A, 0, n - 1);
        // end time
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        double totalTimeD = (double) totalTime;

        double rootN = (double) totalTimeD / ((Math.sqrt(n)));
        double nRootLogN = (double) totalTimeD / (n * Math.sqrt(log2(n)));
        double nLogN = (double) totalTimeD / (n * Math.log(n));
        // store values in excel
        StringBuilder sb = new StringBuilder();
        sb.append(n);
        sb.append(',');
        sb.append(rootN);
        sb.append(',');
        sb.append(nRootLogN);
        sb.append(',');
        sb.append(nLogN);
        sb.append('\n');
        writer.write(sb.toString());
      }
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    }
  }

  public static void MergeSort(int[] A, int p, int r) {
    if (p < r) {
      int q = (int) Math.floor((p + r ) / 2);
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

  public static int log2(int x) {
    return (int) (Math.log(x) / Math.log(2));
}
}
