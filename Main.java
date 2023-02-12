import java.io.*;

class Main {
  public static void main(String[] args) {
    collectData();

  }

  public static double ComputeSumPowers(double a, double x, int n) {
    double sum = 0;
    double prod = 1;
    for (int i = 1; i <= n; i++) {
      sum = sum + prod;
      prod = prod * x;
    }
    return a * sum;
  }

  public static void collectData() {
    try (PrintWriter writer = new PrintWriter("results.csv")) {
      // write the header into the csv file
      String header = "n, n * root(n), n * n, n * log(n) \n";
      writer.write(header);

      // iterate through 500,000 numbers and create a new entry with the running time
      for (int n = 2500; n <= 500000; n += 250) {
        long startTime = System.nanoTime();
        ComputeSumPowers(4, 0.25, n);
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        double totalTimeD = (double) totalTime;

        double nRootN = totalTimeD / (n * (Math.sqrt(n)));
        double nSquared = totalTimeD / (n * n);
        double nLogN = totalTimeD / (n * Math.log(n));

        StringBuilder sb = new StringBuilder();
        sb.append(n);
        sb.append(',');
        sb.append(nRootN);
        sb.append(',');
        sb.append(nSquared);
        sb.append(',');
        sb.append(nLogN);
        sb.append('\n');
        writer.write(sb.toString());
      }

    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    }
  }
}
