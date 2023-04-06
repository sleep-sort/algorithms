import java.util.Arrays;

public class Main {
  public static void main(String[] args) {
    int[] p = { 0, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30 };
    int n = 5;
    int[] result = memoizedCutRodWithCuts(p, n, p.length);
    System.out.println("Maximum revenue for rod of length " + n + " is " + result[0]);
    System.out.println("Cuts for rod of length " + n + " are " + Arrays.toString(getCutPositions(result, n, p)));

    n = 8;
    result = memoizedCutRodWithCuts(p, n, p.length);
    System.out.println("Maximum revenue for rod of length " + n + " is " + result[0]);
    System.out.println("Cuts for rod of length " + n + " are " + Arrays.toString(getCutPositions(result, n, p)));
  }

  public static int[] memoizedCutRodWithCuts(int[] p, int n, int m) {
    int[] r = new int[n + 1];
    int[] s = new int[n + 1];
    Arrays.fill(r, -1);
    int maxRevenue = memoizedCutRodAuxWithCuts(p, n, r, s, m);
    return new int[] { maxRevenue, n > 0 ? s[n] : 0 };
  }

  public static int memoizedCutRodAuxWithCuts(int[] p, int n, int[] r, int[] s, int m) {
    if (r[n] >= 0) {
      return r[n];
    }
    if (n == 0) {
      return 0;
    }
    int maxRevenue = Integer.MIN_VALUE;
    for (int i = 1; i <= Math.min(n, m - 1); i++) {
      int revenue = p[i] + memoizedCutRodAuxWithCuts(p, n - i, r, s, m);
      if (revenue > maxRevenue) {
        maxRevenue = revenue;
        s[n] = i;
      }
    }
    r[n] = maxRevenue;
    return maxRevenue;
  }

  public static int[] getCutPositions(int[] result, int n, int[] p) {
    int[] cuts = new int[result[1] > 0 ? result[1] : 1];
    int i = cuts.length - 1;
    while (n > 0) {
      cuts[i--] = result[1];
      n -= result[1];
      result = memoizedCutRodWithCuts(p, n, p.length);
    }
    return cuts;
  }
}
