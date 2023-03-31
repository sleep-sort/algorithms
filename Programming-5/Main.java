import java.io.*;
import java.util.*;

public class Main {

  public static void main(String[] args) {
    // read in graph properly
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter file path: ");
    String filePath = scanner.nextLine();
    scanner.close();

    try {
      // store graph properly
      Graph graph = Graph.fromFile(filePath);
      System.out.println(graph);
      graph.bellmanFord();
    } catch (IOException e) {
      System.out.println("Error reading file: " + e.getMessage());
    }
  }
}
