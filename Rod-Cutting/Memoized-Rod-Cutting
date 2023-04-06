import java.util.*;

public class Main {
  public static void main(String[] args) {
    int[] p = { 0, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30 };
    int n = 5;
    int maxRevenue = memoizedCutRod(p, n);
    System.out.println("Maximum revenue for rod of length " + n + " is " + maxRevenue);
    // This should return 13 for a length of 5

    n = 8;
    maxRevenue = memoizedCutRod(p, n);
    System.out.println("Maximum revenue for rod of length " + n + " is " + maxRevenue);
    // This should return 22 for a length of 8

  }

  public static int memoizedCutRod(int[] p, int n) {
    int[] r = new int[n + 1];
    for (int i = 0; i <= n; i++) {
      r[i] = -1;
    }
    return memoizedCutRodAux(p, n, r);
  }

  public static int memoizedCutRodAux(int[] p, int n, int[] r) {
    if (r[n] >= 0) {
      return r[n];
    }
    int q;
    if (n == 0) {
      q = 0;
    } else {
      q = Integer.MIN_VALUE;
      for (int i = 1; i <= n; i++) {
        q = Math.max(q, p[i] + memoizedCutRodAux(p, n - i, r));
      }
    }
    r[n] = q;
    return q;
  }

}
