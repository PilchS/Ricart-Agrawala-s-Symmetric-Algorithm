import javax.swing.plaf.IconUIResource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class AllNodes {
    public static boolean isGoodInitiator(int[][] graph, int N, int node) {
        List<Integer> nodeCovered = new ArrayList<>();
        nodeCovered.add(node);

        while (true) {
            boolean newNodeAdded = false;
            List<Integer> newNodes = new ArrayList<>();

            for (int coveredNode : nodeCovered) {
                for (int i = 0; i < N; i++) {
                    if (graph[coveredNode][i] == 1 && !nodeCovered.contains(i) && !newNodes.contains(i)) {
                        newNodes.add(i);
                        newNodeAdded = true;
                    }
                }
            }

            if (!newNodeAdded) {
                break;
            }

            nodeCovered.addAll(newNodes);
        }

        if (nodeCovered.size() < N || !nodeCovered.containsAll(getAllNodes(N))) {
            return false;
        }

        return true;
    }

    private static List<Integer> getAllNodes(int N) {
        List<Integer> allNodes = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            allNodes.add(i);
        }
        return allNodes;
    }

    public static void main(String[] args) {
        // Read the adjacency matrix from a file
        int[][] graph = readAdjacencyMatrixFromFile("graph2.txt");
        if (graph == null) {
            System.out.println("Failed to read the adjacency matrix from the file.");
            return;
        }

        int N = graph.length;
        System.out.println("Nodes that are good initiators: ");
        for(int node = 0; node<N; node++){
            boolean isGood = isGoodInitiator(graph, N, node);
            if (!isGood){
                continue;
            }else{
                System.out.println(node);
            }
        }
    }

    private static int[][] readAdjacencyMatrixFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            int N = lines.size();
            int[][] graph = new int[N][N];
            for (int i = 0; i < N; i++) {
                String[] tokens = lines.get(i).split(" ");
                for (int j = 0; j < N; j++) {
                    graph[i][j] = Integer.parseInt(tokens[j]);
                }
            }

            return graph;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
