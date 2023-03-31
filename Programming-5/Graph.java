import java.io.*;
import java.util.*;


public class Graph {

  private Map<String, Node> nodes;
  private Node originNode;

  public Graph() {
    nodes = new HashMap<>();
  }

  public void setOriginNode(String name) {
    originNode = getNode(name);
  }

  public void addEdge(String srcName, String destName, int weight) {
    Node src = getNode(srcName);
    Node dest = getNode(destName);
    src.addNeighbor(dest, weight);
  }

  private Node getNode(String name) {
    Node node = nodes.get(name);
    if (node == null) {
      node = new Node(name);
      nodes.put(name, node);
    }
    return node;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Node node : nodes.values()) {
      sb.append(node.getName()).append(" -> ");
      for (Edge edge : node.getNeighbors()) {
        sb.append(edge.getDest().getName()).append("(").append(edge.getWeight()).append(") ");
      }
      sb.append("\n");
    }
    sb.append(originNode.getName());
    sb.append(" is the origin node!");
    return sb.toString();
  }

public static Graph fromFile(String fileName) throws IOException {
    Graph graph = new Graph();
    BufferedReader reader = new BufferedReader(new FileReader(fileName));
    String line;
    int counter = 0;
    while ((line = reader.readLine()) != null) {
      if(counter == 0) {
        graph.setOriginNode(line);
      } else {
        String[] parts = line.split(" ");
        String srcName = parts[0];
        for (int i = 1; i < parts.length; i++) {
          String[] edgeParts = parts[i].split(",");
          String destName = edgeParts[0];
          int weight = Integer.parseInt(edgeParts[1]);
          graph.addEdge(srcName, destName, weight);
        }
      }
      counter++;
    }
    reader.close();
    return graph;
}


  private static class Node {
    private String name;
    private Map<Node, Integer> neighbors;

    public Node(String name) {
      this.name = name;
      neighbors = new HashMap<>();
    }

    public void addNeighbor(Node node, int weight) {
      neighbors.put(node, weight);
    }

    public String getName() {
      return name;
    }

    public Iterable<Edge> getNeighbors() {
      return new Iterable<Edge>() {
        @Override
        public java.util.Iterator<Edge> iterator() {
          return new java.util.Iterator<Edge>() {
            private java.util.Iterator<Map.Entry<Node, Integer>> iter = neighbors.entrySet().iterator();

            @Override
            public boolean hasNext() {
              return iter.hasNext();
            }

            @Override
            public Edge next() {
              Map.Entry<Node, Integer> entry = iter.next();
              Node dest = entry.getKey();
              int weight = entry.getValue();
              return new Edge(dest, weight);
            }

            @Override
            public void remove() {
              throw new UnsupportedOperationException();
            }
          };
        }
      };
    }
  }

  private static class Edge {
    private Node dest;
    private int weight;

    public Edge(Node dest, int weight) {
      this.dest = dest;
      this.weight = weight;
    }

    public Node getDest() {
      return dest;
    }

    public int getWeight() {
      return weight;
    }
  }

public void bellmanFord() {
    Map<Node, Integer> distances = new HashMap<>();
    Map<Node, Node> predecessors = new HashMap<>();

    for (Node node : nodes.values()) {
        distances.put(node, Integer.MAX_VALUE);
        predecessors.put(node, null);
    }

    distances.put(originNode, 0);

    for (int i = 1; i < nodes.size(); i++) {
        for (Node node : nodes.values()) {
            for (Edge edge : node.getNeighbors()) {
                Node dest = edge.getDest();
                int weight = edge.getWeight();
                int newDistance = distances.get(node) + weight;
                if (newDistance < distances.get(dest)) {
                    distances.put(dest, newDistance);
                    predecessors.put(dest, node);
                }
            }
        }
    }

    try {
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));

        for (Node node : nodes.values()) {
            if (node == originNode) {
                continue;
            }

            writer.write(node.getName() + ": ");

            List<Node> path = new ArrayList<>();
            Node current = node;

            while (current != null) {
                path.add(current);
                current = predecessors.get(current);
            }

            Collections.reverse(path);

            for (int i = 1; i < path.size() - 1; i++) {
                writer.write(path.get(i).getName() + ", ");
            }

            writer.write(node.getName() + "\n");
        }

        writer.close();

    } catch (IOException e) {
        e.printStackTrace();
    }
}


}
