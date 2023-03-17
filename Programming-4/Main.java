import java.io.FileWriter;
import java.io.IOException;

class Main {
  public static void main(String[] args) {
    BSTHeightCollector.collectData(7);
  }
}

class Node {
  int key;
  Node left, right;

  public Node(int item) {
    key = item;
    left = right = null;
  }
}

class BinarySearchTree {
  Node root;

  BinarySearchTree() {
    root = null;
  }

  void Tree_Insert(int key) {
    root = Tree_Insert_Recursive(root, key);
  }

  Node Tree_Insert_Recursive(Node root, int key) {
    if (root == null) {
      root = new Node(key);
      return root;
    }

    if (key < root.key)
      root.left = Tree_Insert_Recursive(root.left, key);
    else if (key > root.key)
      root.right = Tree_Insert_Recursive(root.right, key);

    return root;
  }

  void printInorder() {
    printInorder_Recursive(root);
  }

  void printInorder_Recursive(Node node) {
    if (node != null) {
      printInorder_Recursive(node.left);
      System.out.print(node.key + " ");
      printInorder_Recursive(node.right);
    }
  }
  
    int Tree_Height(Node node) {
        if (node == null) {
            return -1;
        }

        int leftHeight = Tree_Height(node.left);
        int rightHeight = Tree_Height(node.right);

        return Math.max(leftHeight, rightHeight) + 1;
    }
}

class BSTHeightCollector {
  public static void collectData(int m) {
    String filename = "height_data.csv";
    try {
      FileWriter writer = new FileWriter(filename);

      for (int n = 1000; n <= 20000; n += 1000) {
        int sumHeight = 0;
        for (int j = 0; j < m; j++) {
          BinarySearchTree bst = new BinarySearchTree();

          for (int i = 0; i < n; i++) {
            int p = (int) (Math.random() * 10001);
            Node z = new Node(p);
            bst.Tree_Insert(z.key);
          }

          int height = bst.Tree_Height(bst.root);
          sumHeight += height;

          bst = null; // discard the tree
        }

        double avgHeight = (double) sumHeight / m;
        writer.write(n + "," + avgHeight + "\n");
      }

      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
