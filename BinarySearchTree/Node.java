package project2;

/** 
 * The Node class creates the Node object for use in the BinarySearchTree class
 * and consists of a printNode() method to display the node to the user.
 */

public class Node {
	
	/**
	 * Name of country
	 * GDP per capita
	 * Left child of node
	 * Right child of node
	 */
	String countryName;
	double GDPPC;
	Node leftChild;
	Node rightChild;
	
	/** 
	* Displays the node to the user consisting of the country name and 
	* the GDP per capita. 
	 */
	public void printNode() {
		System.out.printf("%-40s | %.3f\n", countryName, GDPPC);
	}
}// end Node
