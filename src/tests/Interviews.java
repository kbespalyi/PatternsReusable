package tests;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;

import patterns.BST;
import patterns.SMW;

@SuppressWarnings("unused")
public class Interviews {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	//@Disabled("for demonstration purposes")
	public void Search_Max_Weights_test() {
		/**
		 * Optimizing Box Weights
		 */
		
		List<Integer> sumArray = List.of(2,3,5,6,7);

		// Test getting sums
		assertEquals(23, SMW.getSum(sumArray));
		assertEquals(33, SMW.getSum(sumArray, 10));
		
		
		System.out.println("\nTest1");

		// TEST 1
		List<Integer> arr = List.of();
		List<Integer> result = SMW.minimalHeaviestSetA(arr);		
        System.out.println("Target: " + result.toString());

		assertEquals(0, result.size());
		
		System.out.println("\nTest2");

		// TEST 2
		arr = List.of(7, 2);
		result = SMW.minimalHeaviestSetA(arr);		
        System.out.println("Target: " + result.toString());

		assertEquals(List.of(2, 7), result);
		
		System.out.println("\nTest3");

		// TEST 3
		arr = List.of(1, 2, 3);
		result = SMW.minimalHeaviestSetA(arr);		
        System.out.println("Target: " + result.toString());

		assertEquals(List.of(3), result);
		
		System.out.println("\nTest4");

		// TEST 4
		arr = List.of(2, 3, 5, 6, 7);
		result = SMW.minimalHeaviestSetA(arr);		
        System.out.println("Target: " + result.toString());

		assertEquals(List.of(6,7), result);
		
		System.out.println("\nTest5");

		// TEST 5		
		arr = List.of(5, 2, 3, 5, 6, 7, 1, 3);
		result = SMW.minimalHeaviestSetA(arr);
        System.out.println("Target: " + result.toString());

		assertEquals(List.of(5,6,7), result);

		System.out.println("\nTest6");

		// TEST 6		
		arr = List.of(15, -1, 10, -30, 1, 18, 50, 3);
		result = SMW.minimalHeaviestSetA(arr);
        System.out.println("Target: " + result.toString());

		assertEquals(List.of(50), result);

		System.out.println("\nTest7");

		// TEST 7		
		arr = List.of(25, 20, 10, 45, 15, 18, 35, 3, 30);
		result = SMW.minimalHeaviestSetA(arr);
        System.out.println("Target: " + result.toString());

		assertEquals(List.of(30, 35, 45), result);

		System.out.println("\nTest8");

		// TEST 8		
		arr = List.of(25, 20, 1, 2, 12, 19, 24, -5, 10, 45, 15, 18, 35, 3, 30);
		result = SMW.minimalHeaviestSetA(arr);
        System.out.println("Target: " + result.toString());

		assertEquals(List.of(25, 30, 35, 45), result);

		System.out.println();
	}

	@Test
	//@Disabled("for demonstration purposes")
	public void Binary_Search_Tree_test() {
		/**
		 * https://javarevisited.blogspot.com/2015/10/how-to-implement-binary-search-tree-in-java-example.html#axzz4wnEtnNB3
		 * 
		 * A binary search tree or BST is a popular data structure which is used to keep elements in order.
		 * A binary search tree is a binary tree where the value of a left child is less than or equal to the parent
		 * node and value of the right child is greater than or equal to the parent node. Since its a binary tree,
		 * it can only have 0, 1 or two children. What makes a binary search tree special is its ability to reduce
		 * the time complexity of fundamental operations like add, remove and search, also known as insert, delete
		 * and find. In a BST, all these operations (insert, remove and find) can be performed in O(log(n)) time.
		 * The reason for this improvement in speed is because of the unique property of binary search tree, where
		 * for each node, the data in the left child is less than (or equal) and the data in the right child is
		 * greater than (or equal) to the data in said node.
		 * Here, You will learn how to create a binary search tree with integer nodes.
		 * I am not using Generics just to keep the code simple but if you like you can extend the problem to use Generics,
		 * which will allow you to create a Binary tree of String, Integer, Float or Double.
		 * Remember, you make sure that node of BST must implement the Comparable interface
		 * 
		 * Pre-order (NLR): F, B, A, D, C, E, G, I, H.
		 * - Check if the current node is empty or null.
		 * - Display the data part of the root (or current node).
		 * - Traverse the left subtree by recursively calling the pre-order function.
		 * - Traverse the right subtree by recursively calling the pre-order function.
		 * 
		 * In-order (LNR): A, B, C, D, E, F, G, H, I.
		 * - Check if the current node is empty or null.
		 * - Traverse the left subtree by recursively calling the in-order function.
		 * - Display the data part of the root (or current node).
		 * - Traverse the right subtree by recursively calling the in-order function.
		 * 
		 * Post-order (LRN): A, C, E, D, B, H, I, G, F.
		 * - Check if the current node is empty or null.
		 * - Traverse the left subtree by recursively calling the post-order function.
		 * - Traverse the right subtree by recursively calling the post-order function.
		 * - Display the data part of the root (or current node).
		 * 
		 * Level-order (BFS): F, B, G, A, D, I, C, E, H.
		 */
		
		BST tree = new BST();
		assertTrue(tree.getRoot() == null);
		
        tree.insert(50); 
        tree.insert(30); 
        tree.insert(20); 
        tree.insert(40); 
        tree.insert(70); 
        tree.insert(60); 
        tree.insert(80); 
        tree.insert(90); 
        tree.insert(25); 

        tree.levelorder("Level-order: \n");
        System.out.println();
        tree.inorder("In-order: ");
        
		assertTrue(tree.getRoot().getValue() == 50);
        
        System.out.println("Root is " + tree.getRoot().getValue());

        System.out.println();        
        System.out.println("Number of nodes in BST is " + tree.countNodes());
        System.out.println("Number of trees in BST is " + tree.countTrees());

        assertTrue(tree.size() == 9);

        assertTrue(tree.countNodes() == 9);

        assertTrue(tree.countTrees() == 4);

        assertTrue(tree.findValue(80));

        assertTrue(tree.findValue(20));

        assertTrue(tree.findValue(50));
        
        tree.deleteKey(50);

        assertTrue(tree.findValue(50) == false);
        
        tree.inorder();
        
        assertTrue(tree.size() == 8);

		assertTrue(tree.getRoot().getValue() == 60);
        
        System.out.println("New root is " + tree.getRoot().getValue());
        
        tree.setRoot(tree.buildTree(tree.getRoot()));

        tree.inorder("Preorder traversal of balanced BST is ");

        System.out.println("New root is " + tree.getRoot().getValue());

		BST tree2 = new BST();
		
		tree2.insert(100); 
		tree2.insert(10); 
		tree2.insert(5); 
		tree2.insert(18); 
		tree2.insert(67); 
		tree2.insert(9); 
		tree2.insert(4); 

		tree.setRoot(tree.mergeTrees(tree.getRoot(), tree2.getRoot()));

        tree.inorder("Following is Inorder traversal of the merged balanced tree ");

        System.out.println("New root after merge is " + tree.getRoot().getValue());

        int arr[] = new int[]{1, 94, 3, 28, 21, 6, 7};        
		BST tree3 = new BST();

        tree3.setRoot(tree.arrayToBST(arr, 0, arr.length - 1));

		tree.setRoot(tree.mergeTrees(tree.getRoot(), tree3.getRoot()));
        
        tree.inorder("Preorder traversal of constructed BST after insert ");

        System.out.println("New root after insert from array is " + tree.getRoot().getValue());

        System.out.println();        
        System.out.println("Min value in BST is " + tree.getMinValue());
        System.out.println("Max value in BST is " + tree.getMaxValue());

        System.out.println();        
        System.out.println("Sum of values in BST is " + tree.getTotalSum());
	}

}
