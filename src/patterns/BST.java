package patterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * Binary Search Tree is a node-based binary tree data structure which has the
 * following properties: - The left subtree of a node contains only nodes with
 * keys lesser than the node’s key. - The right subtree of a node contains only
 * nodes with keys greater than the node’s key. - The left and right subtree
 * each must also be a binary search tree. Java Program to implement a binary
 * search tree. A binary search tree is a sorted binary tree, where value of a
 * node is greater than or equal to its left the child and less than or equal to
 * its right child.
 * 
 * Insertion of a key A new key is always inserted at leaf. We start searching a
 * key from root till we hit a leaf node. Once a leaf node is found, the new
 * node is added as a child of the leaf node.
 *
 * 100 100 / \ Insert 40 / \ 20 500 ---------> 20 500 / \ / \ 10 30 10 30 \ 40
 * 
 * @author WINDOWS 8
 *
 */
public class BST {

	public static class Node {
		private int data;
		private Node left, right;

		public Node(int value) {
			data = value;
			left = right = null;
		}

		public int getValue() {
			return data;
		}
	}
	
	private Node root;

	public BST() {
		root = null;
	}

	/**
	 * Java function to clear the binary search tree. Time complexity of this
	 * method is O(1)
	 */
	public void clear() {
		root = null;
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	/**
	 * Java function to check if binary tree is empty or not Time Complexity of
	 * this solution is constant O(1) for best, average and worst case.
	 * 
	 * @return true if binary search tree is empty
	 */
	public boolean isEmpty() {
		return null == root;
	}

	/**
	 * Java function to return number of nodes in this binary search tree. Time
	 * complexity of this method is O(n)
	 * 
	 * @return size of this binary search tree
	 */
	public int size() {
		Node current = root;
		int size = 0;
		Stack<Node> stack = new Stack<Node>();
		while (!stack.isEmpty() || current != null) {
			if (current != null) {
				stack.push(current);
				current = current.left;
			} else {
				size++;
				current = stack.pop();
				current = current.right;
			}
		}
		return size;
	}

	public boolean findValue(int key) {
		return search(root, key) == null ? false : true;
	}

	/**
	 * A utility function to search a given key in BST 1. Start from root. 2.
	 * Compare the inserting element with root, if less than root, then recurse
	 * for left, else recurse for right. 3. If element to search is found
	 * anywhere, return true, else return false.
	 * 
	 * @param root
	 * @param key
	 * @return
	 */
	Node search(Node root, int key) {
		// Base Cases: root is null or key is present at root
		if (root == null || root.data == key)
			return root;

		if (root.data > key)
			// value is greater than root's key
			return search(root.left, key);
		else
			// value is less than root's key
			return search(root.right, key);
	}

	// This method mainly calls insertRec()
	public void insert(int key) {
		root = insertRec(root, key);
	}

	/* A recursive function to insert a new key in BST */
	Node insertRec(Node root, int key) {

		/* If the tree is empty, return a new node */
		if (root == null) {
			root = new Node(key);
			return root;
		}

		/* Otherwise, recur down the tree */
		if (key < root.data)
			root.left = insertRec(root.left, key);
		else if (key > root.data)
			root.right = insertRec(root.right, key);

		/* return the (unchanged) node pointer */
		return root;
	}

	// This method mainly calls InorderRec()
	public void inorder() {
		System.out.println();
		inorderRec(root);
		System.out.println();
	}

	public void inorder(String message) {
		System.out.println();
		System.out.print(message);
		inorderRec(root);
		System.out.println();
	}

	// A utility function to do in-order traversal of BST
	void inorderRec(Node root) {
		if (root != null) {
			inorderRec(root.left);
			System.out.print(root.data + " ");
			inorderRec(root.right);
		}
	}

	// This method mainly calls LevelorderRec()
	public void levelorder() {
		System.out.println();
		levelorderRec(root, 0);
		System.out.println();
	}

	public void levelorder(String message) {
		System.out.println();
		System.out.print(message);
		levelorderRec(root, 0);
		System.out.println();
	}

	// A utility function to do level-order traversal of BST
	void levelorderRec(Node root, int level) {
		if (root != null) {
			String lpad = new String(new char[level]).replace("\0", " ");
			System.out.print("\n" + lpad + root.data + (root.left == null ? "" : "\n" + lpad + "/") + lpad + (root.right == null ? "" : lpad + "\\"));			
			levelorderRec(root.left, level + 1);
			levelorderRec(root.right, level + 1);
		}
	}

	// This method mainly calls deleteRec()
	public void deleteKey(int key) {
		root = deleteRec(root, key);
	}

	/* A recursive function to insert a new key in BST */
	Node deleteRec(Node root, int key) {
		/* Base Case: If the tree is empty */
		if (root == null)
			return root;

		/* Otherwise, recur down the tree */
		if (key < root.data)
			root.left = deleteRec(root.left, key);
		else if (key > root.data)
			root.right = deleteRec(root.right, key);

		// if key is same as root's key, then This is the node
		// to be deleted
		else {
			// node with only one child or no child
			if (root.left == null)
				return root.right;
			else if (root.right == null)
				return root.left;

			// node with two children: Get the inorder successor (smallest
			// in the right subtree)
			root.data = minValue(root.right);

			// Delete the inorder successor
			root.right = deleteRec(root.right, root.data);
		}

		return root;
	}

	/*
	 * This function traverse the skewed binary tree and stores its nodes
	 * pointers in vector nodes[]
	 */
	void storeBSTNodes(Node root, Vector<Node> nodes) {
		// Base case
		if (root == null)
			return;

		// Store nodes in Inorder (which is sorted
		// order for BST)
		storeBSTNodes(root.left, nodes);
		nodes.add(root);
		storeBSTNodes(root.right, nodes);
	}

	/* Recursive function to construct binary tree */
	Node buildTreeUtil(Vector<Node> nodes, int start, int end) {
		// base case
		if (start > end)
			return null;

		/* Get the middle element and make it root */
		int mid = (start + end) / 2;
		Node node = nodes.get(mid);

		/*
		 * Using index in Inorder traversal, construct left and right subtress
		 */
		node.left = buildTreeUtil(nodes, start, mid - 1);
		node.right = buildTreeUtil(nodes, mid + 1, end);

		return node;
	}

	// This functions converts an unbalanced BST to
	// a balanced BST
	public Node buildTree(Node root) {
		// Store nodes of given BST in sorted order
		Vector<Node> nodes = new Vector<Node>();
		storeBSTNodes(root, nodes);

		// Constucts BST from nodes[]
		int n = nodes.size();
		return buildTreeUtil(nodes, 0, n - 1);
	}

	// Method that merges two trees into a single one.
	public Node mergeTrees(Node node1, Node node2) {
		// Stores Inorder of tree1 to list1
		List<Integer> list1 = storeInorder(node1);

		// Stores Inorder of tree2 to list2
		List<Integer> list2 = storeInorder(node2);

		// Merges both list1 and list2 into list3
		List<Integer> list3 = merge(list1, list2, list1.size(), list2.size());

		// Eventually converts the merged list into resultant BST
		Node node = ALtoBST(list3, 0, list3.size() - 1);
		return node;
	}

	// A Utility Method that stores inorder traversal of a tree
	public ArrayList<Integer> storeInorderUtil(Node node, ArrayList<Integer> list) {
		if (node == null)
			return list;

		// recur on the left child
		storeInorderUtil(node.left, list);

		// Adds data to the list
		list.add(node.data);

		// recur on the right child
		storeInorderUtil(node.right, list);

		return list;
	}

	// Method that stores inorder traversal of a tree
	ArrayList<Integer> storeInorder(Node node) {
		ArrayList<Integer> list1 = new ArrayList<>();
		ArrayList<Integer> list2 = storeInorderUtil(node, list1);
		return list2;
	}

	// Method that merges two ArrayLists into one.
	List<Integer> merge(List<Integer> list1, List<Integer> list2, int m, int n) {
		// list3 will contain the merge of list1 and list2
		List<Integer> list3 = new ArrayList<>();
		int i = 0;
		int j = 0;

		// Traversing through both ArrayLists
		while (i < m && j < n) {
			// Smaller one goes into list3
			if (list1.get(i) < list2.get(j)) {
				list3.add(list1.get(i));
				i++;
			} else {
				list3.add(list2.get(j));
				j++;
			}
		}

		// Adds the remaining elements of list1 into list3
		while (i < m) {
			list3.add(list1.get(i));
			i++;
		}

		// Adds the remaining elements of list2 into list3
		while (j < n) {
			list3.add(list2.get(j));
			j++;
		}
		return list3;
	}

	// Method that converts an ArrayList to a BST
	Node ALtoBST(List<Integer> list, int start, int end) {
		// Base case
		if (start > end)
			return null;

		// Get the middle element and make it root
		int mid = (start + end) / 2;
		Node node = new Node(list.get(mid));

		/*
		 * Recursively construct the left subtree and make it left child of root
		 */
		node.left = ALtoBST(list, start, mid - 1);

		/*
		 * Recursively construct the right subtree and make it right child of
		 * root
		 */
		node.right = ALtoBST(list, mid + 1, end);

		return node;
	}

	/*
	 * A function that constructs Balanced Binary Search Tree from a sorted
	 * array
	 */
	public Node arrayToBST(int arr[], int start, int end) {
		List<Integer> list = Arrays
				.stream(arr)
				.sorted()
				.boxed()
				.collect(Collectors.toList());		
		return ALtoBST(list, start, end);
	}

	public int getMaxValue() {
		return maxValue(root);
	}

	public int getMinValue() {
		return minValue(root);
	}

	public int getTotalSum() {
		return inorderSum(root);
	}

	// A utility function to do inorder traversal of BST
	int inorderSum(Node root) {
		return (root != null) ? root.data + inorderSum(root.left) + inorderSum(root.right) : 0;
	}

	public int countNodes() {
		return inorderCountNodes(root);
	}

	int inorderCountNodes(Node root) {
		return (root != null) ? 1 + inorderCountNodes(root.left) + inorderCountNodes(root.right) : 0;
	}

	public int countTrees() {
		return inorderCountTrees(root, 1);
	}

	int inorderCountTrees(Node root, int count) {
		if (root != null) {
			int left = inorderCountTrees(root.left, count);
			int right = inorderCountTrees(root.right, count);
			return (left + right) > 0 ? left + right : count;
		} else return 0;
	}

	int minValue(Node root) {
		int minv = root.data;
		while (root.left != null) {
			minv = root.left.data;
			root = root.left;
		}
		return minv;
	}

	int maxValue(Node root) {
		int maxv = root.data;
		while (root.right != null) {
			maxv = root.right.data;
			root = root.right;
		}
		return maxv;
	}

}
