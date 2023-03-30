import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    private static final int INF = 32767; // maximum weight in the graph, can modify accordingly 

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the input file: ");
        String fileName = scanner.nextLine();

        try {
            Scanner fileScanner = new Scanner(new File(fileName));
            char source = fileScanner.nextLine().charAt(0);
            HashSet<Character> nodes = new HashSet<>();
            boolean[][] edges = new boolean[26][26];
            int[] dist = new int[26];
            Arrays.fill(dist, INF);
            dist[source - 'a'] = 0;

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split(" ");
                char vertex = parts[0].charAt(0);
                nodes.add(vertex);
                for (int i = 1; i < parts.length; i++) {
                    char neighbor = parts[i].charAt(0);
                    int weight = Integer.parseInt(parts[i].substring(2));
                    nodes.add(neighbor);
                    edges[vertex - 'a'][neighbor - 'a'] = true;
                    relax(vertex - 'a', neighbor - 'a', weight, dist);
                }
            }

            System.out.println("Shortest distances from " + source + " to all other vertices:");
            for (char vertex : nodes) {
                int index = vertex - 'a';
                if (dist[index] == INF) {
                    System.out.println(vertex + ": unreachable");
                } else {
                    System.out.println(vertex + ": " + dist[index]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }
    }

    private static void relax(int u, int v, int weight, int[] dist) {
        if (dist[u] + weight < dist[v]) {
            dist[v] = dist[u] + weight;
        }
    }
}
