import java.io.*;
import java.util.*;

public class Main {
    static Random random = new Random();
    static int[] activityIndices = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    static int[] startTimes = new int[]{1, 3, 0, 5, 3, 5, 6, 8, 8, 2, 12};
    static int[] finishTimes = new int[]{4, 5, 6, 7, 9, 9, 10, 11, 12, 14, 16};

    public static void main(String[] args) {
        // CODE FOR THE TERMINAL OUTPUT PORTION OF THE ASSIGNMENT
        System.out.println("Activities:");
        System.out.println("Index\tStart Time\tFinish Time");
        for (int i = 0; i < activityIndices.length; i++) {
            System.out.printf("%d\t%d\t\t%d\n", activityIndices[i], startTimes[i], finishTimes[i]);
        }

        System.out.println("RecursiveActivitySelector result:");
        List<Integer> selectedActivities = recursiveActivitySelector(startTimes, finishTimes, 0, activityIndices.length - 1);
        System.out.println(selectedActivities);

        System.out.println("GreedyActivitySelector result:");
        selectedActivities = greedyActivitySelector(startTimes, finishTimes);
        System.out.println(selectedActivities);

        StudyOverhead(150);
    }

    static void initializeArrays(int[] s, int[] f) {
        s[0] = 0;
        f[0] = 0;
        for (int i = 1; i < s.length; i++) {
            if (i % 2 == 0) {
                s[i] = f[i-2];
                f[i] = s[i] + 2;
            } else {
                s[i] = f[i-1] - 1;
                f[i] = f[i-1] + 1;
            }
        }
    }

    static List<Integer> recursiveActivitySelector(int[] s, int[] f, int i, int j) {
        List<Integer> selectedActivities = new ArrayList<>();
        selectedActivities.add(i);
        int k = i;

        for (int m = i + 1; m <= j; m++) {
            if (s[m] >= f[k]) {
                selectedActivities.add(m);
                k = m;
            }
        }

        List<Integer> result = new ArrayList<>();
        for (int index : selectedActivities) {
            if (index >= 0 && index < activityIndices.length) {
                result.add(activityIndices[index]);
            }
        }
        return result;
    }

    public static List<Integer> greedyActivitySelector(int[] s, int[] f) {
        List<Integer> selectedActivities = new ArrayList<Integer>();
        int n = s.length;
        int i = 0;
        selectedActivities.add(i);

        for (int j = 1; j < n; j++) {
            if (s[j] >= f[selectedActivities.get(selectedActivities.size()-1)]) {
                selectedActivities.add(j);
            }
        }

        List<Integer> result = new ArrayList<>();
        for (int index : selectedActivities) {
            if (index >= 0 && index < n) {
                result.add(index + 1);
            }
        }
        return result;
    }
public static void StudyOverhead(int NumberPoints) {
    // Initialize arrays for start times and finish times
    int[] s = new int[NumberPoints];
    int[] f = new int[NumberPoints];
    initializeArrays(s, f);

    // Initialize M array to hold ratios of recursive to iterative running times
    double[] M = new double[NumberPoints];

    try {
        // Create a FileWriter to write data to a CSV file
        FileWriter csvWriter = new FileWriter("results.csv");

        // Write the header row for the CSV file
        csvWriter.append("Number of Activities");
        csvWriter.append(",");
        csvWriter.append("Ratio of Recursive to Iterative Running Time");
        csvWriter.append("\n");

        // Loop over different numbers of activities and collect data
        for (int i = 1; i <= NumberPoints; i++) {
            double TimeRecursive = 0;
            double TimeIterative = 0;

            // Run both the recursive and iterative algorithms multiple times to get average running times
            for (int j = 1; j <= 100; j++) {
                // Run the recursive algorithm
                long startTime = System.nanoTime();
                recursiveActivitySelector(s, f, 0, i-1);
                long endTime = System.nanoTime();
                TimeRecursive += (endTime - startTime);

                // Run the iterative algorithm
                startTime = System.nanoTime();
                greedyActivitySelector(s, f);
                endTime = System.nanoTime();
                TimeIterative += (endTime - startTime);
            }

            // Calculate the ratio of recursive to iterative running times
            M[i-1] = TimeRecursive / TimeIterative;

            // Write the data to the CSV file
            csvWriter.append(String.valueOf(i));
            csvWriter.append(",");
            csvWriter.append(String.valueOf(M[i-1]));
            csvWriter.append("\n");
        }

        // Close the FileWriter
        csvWriter.flush();
        csvWriter.close();

        System.out.println("Data written to results.csv");

    } catch (IOException e) {
        System.out.println("An error occurred while writing to the CSV file");
        e.printStackTrace();
    }
}
}
