/* ShortestPaths.java
   CSC 226 - Fall 2019

   Vicky Nguyen - V00906571 - 4 Nov 2019
   
   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
   java ShortestPaths
   
   To conveniently test the algorithm with a large input, create a text file
   containing one or more test graphs (in the format described below) and run
   the program with
   java ShortestPaths file.txt
   where file.txt is replaced by the name of the text file.
   
   The input consists of a series of graphs in the following format:
   
   <number of vertices>
   <adjacency matrix row 1>
   ...
   <adjacency matrix row n>
   
   Entry A[i][j] of the adjacency matrix gives the weight of the edge from 
   vertex i to vertex j (if A[i][j] is 0, then the edge does not exist).
   Note that since the graph is undirected, it is assumed that A[i][j]
   is always equal to A[j][i].
   
   An input file can contain an unlimited number of graphs; each will be processed separately.
   
   NOTE: For the purpose of marking, we consider the runtime (time complexity)
         of your implementation to be based only on the work done starting from
	 the ShortestPaths() method. That is, do not not be concerned with the fact that
	 the current main method reads in a file that encodes graphs via an
	 adjacency matrix (which takes time O(n^2) for a graph of n vertices).
   
   
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.*;

//Do not change the name of the ShortestPaths class
public class ShortestPaths {

	// TODO: Your code here
	public static int n; // number of vertices
	private static int dist[];
	private static int parent[];
	private static IndexMinPQ<Integer> pq;
	private static Set<Integer> settled;
	/*
	 * ShortestPaths(adj) Given an adjacency list for an undirected, weighted graph,
	 * calculates and stores the shortest paths to all the vertices from the source
	 * vertex.
	 * 
	 * The number of vertices is adj.length For vertex i: adj[i].length is the
	 * number of edges adj[i][j] is an int[2] that stores the j'th edge for vertex
	 * i, where: the edge has endpoints i and adj[i][j][0] the edge weight is
	 * adj[i][j][1] and assumed to be a positive integer
	 * 
	 * All weights will be positive.
	 */

	static void ShortestPaths(int[][][] adj, int source) {
		n = adj.length;

		// TODO: Your code here
<<<<<<< HEAD
		// https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-in-java-using-priorityqueue/
=======

		// https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-in-java-using-priorityqueue/

>>>>>>> just found bug in pq. Should check pq.contains(vertex)? remove, insert : insert
		// Inittialize
		init_dist();
		parent = new int[n];
		settled = new HashSet<Integer>();

		pq = new IndexMinPQ<Integer>(n);
		pq.insert(source, 0);
		dist[source] = 0;
		parent[source] = -1; // no parent

		while (settled.size() != n) {
			int u = pq.delMin();
			settled.add(u);
			e_Neighbours(u, adj);
		}

	}

	static void e_Neighbours(int u, int[][][] adj) {
		int edgeDistance = -1;
		int newDistance = -1;
		for (int i = 0; i < adj[u].length; i++) {
			int vertex = adj[u][i][0];
			int cost = adj[u][i][1];

			// If current node hasn't already been processed
			if (!settled.contains(vertex)) {
				edgeDistance = cost;
				newDistance = dist[u] + edgeDistance;

				// If new distance is cheaper in cost
				if (newDistance < dist[vertex]) {
					dist[vertex] = newDistance;

					// remove vertex, check if vertex is in pq, then insert...
					if (pq.contains(vertex)) {
						pq.delete(vertex);
					}
					pq.insert(vertex, newDistance);
					parent[vertex] = u;
				}
			}
		}
	}

	static void init_dist() {
		dist = new int[n];
		for (int i = 0; i < n; i++) {
			dist[i] = Integer.MAX_VALUE;
		}
	}

	// to do
	static void PrintPaths(int source) {
		// TODO: Your code here
		for (int i = 0; i < n; i++) {
			String path = "" + i;
			int cur_vertex = i;
			while (parent[cur_vertex] != -1) {
				path = parent[cur_vertex] + "->" + path;
				cur_vertex = parent[cur_vertex];
			}
			System.out.println(i + ": " + dist[i] + ", path: " + path);
		}
	}

	/*
	 * main() Contains code to test the ShortestPaths function. You may modify the
	 * testing code if needed, but nothing in this function will be considered
	 * during marking, and the testing process used for marking will not execute any
	 * of the code below.
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Scanner s;
		if (args.length > 0) {
			// If a file argument was provided on the command line, read from the file
			try {
				s = new Scanner(new File(args[0]));
			} catch (java.io.FileNotFoundException e) {
				System.out.printf("Unable to open %s\n", args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n", args[0]);
		} else {
			// Otherwise, read from standard input
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}

		int graphNum = 0;
		double totalTimeSeconds = 0;

		// Read graphs until EOF is encountered (or an error occurs)
		while (true) {
			graphNum++;
			if (graphNum != 1 && !s.hasNextInt())
				break;
			System.out.printf("Reading graph %d\n", graphNum);
			int n = s.nextInt();
			int[][][] adj = new int[n][][];

			int valuesRead = 0;
			for (int i = 0; i < n && s.hasNextInt(); i++) {
				LinkedList<int[]> edgeList = new LinkedList<int[]>();
				for (int j = 0; j < n && s.hasNextInt(); j++) {
					int weight = s.nextInt();
					if (weight > 0) {
						edgeList.add(new int[] { j, weight });
					}
					valuesRead++;
				}
				adj[i] = new int[edgeList.size()][2];
				Iterator it = edgeList.iterator();
				for (int k = 0; k < edgeList.size(); k++) {
					adj[i][k] = (int[]) it.next();
				}
			}
			if (valuesRead < n * n) {
				System.out.printf("Adjacency matrix for graph %d contains too few values.\n", graphNum);
				break;
			}

			// output the adjacency list representation of the graph
			for (int i = 0; i < n; i++) {
				System.out.print(i + ": ");
				for (int j = 0; j < adj[i].length; j++) {
					System.out.print("(" + adj[i][j][0] + ", " + adj[i][j][1] + ") ");
				}
				System.out.print("\n");
			}

			long startTime = System.currentTimeMillis();

			ShortestPaths(adj, 0);
			PrintPaths(0);
			long endTime = System.currentTimeMillis();
			totalTimeSeconds += (endTime - startTime) / 1000.0;

			// System.out.printf("Graph %d: Minimum weight of a 0-1 path is
			// %d\n",graphNum,totalWeight);
		}
		graphNum--;
		System.out.printf("Processed %d graph%s.\nAverage Time (seconds): %.2f\n", graphNum, (graphNum != 1) ? "s" : "",
				(graphNum > 0) ? totalTimeSeconds / graphNum : 0);
	}
}
