package project2;

/** 
 * This class handles the binary search tree operations presented to the user
 * in the menu found in the project 2 class.
 */

public class BinarySearchTree {
	
	  // BinarySearchTree attributes
	  private Node root;
	  /**
	   * Arrays for storing top and bottom x countries
	   */
	  Node[] bottomArray = new Node[145];
	  Node[] topArray = new Node[145];
	  
	  /** 
	  * BinarySearchTree constructor
	  * An empty tree is created by setting the root node to null
	  */
	  public BinarySearchTree() {
		  root = null; // tree is empty
	  }
	  
	  /** 
	  * Inserts a node in the binary search tree at the proper position
	  * regarding the country name
	  * 
	  * @param String name
	  * @para, double GDP per capita
	  * @return inserts the node in the tree at the proper position
	   */
	  public void insert(String name, double GDPPC) {
		  Node newNode = new Node(); // enter a new node
		  newNode.countryName = name;
		  newNode.GDPPC = GDPPC;
		  
		  // place the first node at the root
		  if (root == null) {
			  root = newNode; // when there is no root node
		  }
		  
		  // if the root node is occupied
		  else {
			  Node current = root;
			  Node parent;
			  while(true) {
				  parent = current;
				  if (name.compareTo(current.countryName)<0) {
					  current = current.leftChild;
					  if (current == null) {
						  parent.leftChild = newNode;
						  return;
					  }
				  }// end go left
				  else {
					  current = current.rightChild;
					  if (current == null) {
						  parent.rightChild = newNode;
						  return;
					  }
				  } // end else go right
			  }// end while
		  }// end else root exists
	  }// end insert
	  
	  /** 
	  * Prints the path to the node when the user requests 
	  * to find a country's name and GDP per capita
	  * 
	  * @param  String name
	  * @return the path taken to find the node
	   */
	  public void path(String name) {
		  Node current = root;
		  
		  while(name.compareTo(current.countryName)!=0) {
			  System.out.print(current.countryName);
			  System.out.print(" -> ");
			  
			  if (name.compareTo(current.countryName)<0) {
				  current = current.leftChild; // left link
			  }
			  else {
				  current = current.rightChild; // right link
			  }
			  if (current == null) {
				  // country not found
			  }
			  
		  } // end while
		  System.out.println(current.countryName);
	  }
	  
	  /** 
	  * Searches for a country by name within the binary search tree and outputs
	  * the GDP per capita to the user.
	  * 
	  * @param  String name
	  * @return double GDP per capita
	   */
	  public double find(String name) {
		  Node current = root;
		  
		  while(name.compareTo(current.countryName)!=0) {
			  
			  if (name.compareTo(current.countryName)<0) {
				  current = current.leftChild; // left link
			  }
			  else {
				  current = current.rightChild; // right link
			  }
			  if (current == null) {
				  return -1; // if country is not found
			  }
		  } // end while
		  
		  return current.GDPPC;
	  }// end find
	  
	  /** 
	  * Deleted the country specified by name from the binary search tree 
	  * 
	  * @param  String name
	  * @return deletes the node
	   */
	  public void delete(String name) {
		  Node current = root;
		  Node parent = root;
		  boolean isLeftChild = true;
		  
		  // no children
		  while(current.countryName.compareTo(name)!=0) {
			  parent = current;
			  if (name.compareTo(current.countryName)<0) {
				  isLeftChild = true;
				  current = current.leftChild;
			  }
			  else {
				  isLeftChild = false;
				  current = current.rightChild;
			  }
			  if (current == null) {
				  // country not found
			  }
		  }// end while
		  
		  if (current.leftChild == null && current.rightChild == null) {
		  if (current == root) {
			  root = null;
		  }
		  else if(isLeftChild) {
			  parent.leftChild = null;
		  }
		  else {
			  parent.rightChild = null;
		  }
		  } // end no child
		  
		  else if (current.rightChild == null) {// one child, replace with left subtree
			  if (current == root) {
				  root = current.leftChild;
			  }
			  else if (isLeftChild) {
				  parent.leftChild = current.leftChild;
			  }
			  else {
				  parent.rightChild = current.rightChild;
			  }
		  } // end replace with left tree
		  
		  else if (current.leftChild == null) { // one child, replace with right subtree
			  if (current == root) {
				  root = current.rightChild;
			  }
			  else if (isLeftChild) {
				  parent.leftChild = current.rightChild;
			  }
			  else {
				  parent.rightChild = current.rightChild;
			  }
		  } // end replace with right tree
		  
		  else {// two children
			  Node successor = getSuccessor(current);
			  
			  if (current == root) {
				  root = successor;
			  }
			  else if (isLeftChild) {
				  parent.leftChild = successor;
			  }
			  else {
				  parent.rightChild = successor;
			  }
			  successor.leftChild = current.leftChild;
		  }// end has two children
		  System.out.println(name + " deleted");
	  }
	  
	  /** 
	  * Gets the successor node in the case of deleting a node with two children
	  * in the delete() method
	  * 
	  * @param  Node to delete
	  * @return successor node
	   */
	  private Node getSuccessor(Node delNode) {
		  Node successorParent = delNode;
		  Node successor = delNode;
		  Node current = delNode.rightChild;
		  
		  while(current != null) {
			  successorParent = successor;
			  successor = current;
			  current = current.leftChild;
		  }// end while
		  
		  if (successor != delNode.rightChild) {
			  successorParent.leftChild = successor.rightChild;
			  successor.rightChild = delNode.rightChild;
		  }
		  
		  return successor;
	  }
	  
	  /** 
	  * Prints the binary search tree in order
	  */
	  public void printInorder() {
		  inOrder(root);
	  }// end printInorder
	  
	  /** 
	  * Sets the in order format
	  * 
	  * @param root node
	  */
	  private void inOrder(Node localRoot) { // LNR
		  
		  if(localRoot != null) { 
			  inOrder(localRoot.leftChild);
			  localRoot.printNode();
			  inOrder(localRoot.rightChild); 
		  } 
		  
	  }// end inOrder
	  
	  /** 
	  * Prints the binary search tree pre order
	  */
	  public void printPreorder() {
		  preOrder(root);
	  }// end printPreorder
	  
	  /** 
	  * Sets the pre order format
	  * 
	  * @param root node
	  */
	  private void preOrder(Node localRoot) { // NLR
		  
		  if (localRoot != null) {
			  localRoot.printNode();
			  preOrder(localRoot.leftChild);
			  preOrder(localRoot.rightChild); 
		  }
	  }// end preOrder
	  
	  /** 
	  * Prints the binary search tree post order
	  */
	  public void printPostorder() {
		  postOrder(root);
	  }// end printPostorder
	  
	  /** 
	  * Sets the post order format
	  * 
	  * @param root node
	  */
	  private void postOrder(Node localRoot) { // LRN
		  
		  if (localRoot != null) {
			  postOrder(localRoot.leftChild);
			  postOrder(localRoot.rightChild); 
			  localRoot.printNode();
		  }
	  }// end preOrder
	  
	  /** 
	  * Searches for the bottom x countries
	  */
	  private Node getMinGDP(Node current) {
		  
		  Node child = current;
		  
		  if (current == null) {
			  return null; // tree is empty
		  }
		  
		  if (current.leftChild == null && current.rightChild == null) {
			  
			  return current;
		  }
		  else if (current.rightChild == null) {
			  child = current.leftChild;
			  if (current.GDPPC > child.GDPPC) {
				  return child;
			  }
			  else {
				  return current;
			  }
		  }
		  else if (current.leftChild == null) {
			  child = current.rightChild;
			  if (current.GDPPC > child.GDPPC) {
				  return child;
			  }
			  else {
				  return current;
			  }
		  }
		  else {
			  if (current.GDPPC > current.leftChild.GDPPC) {
				  current = current.leftChild;
				  return current;
			  }
			  else if (current.GDPPC > current.rightChild.GDPPC) {
				  current = current.rightChild;
				  return current;
			  }
			  else {
				  return current;
			  }
			  
		  }
		  
		  
//		  while(current != null)            // until the bottom,
//		  { 
//		  min = current;                // remember node 
//		  current = current.leftChild;   // go to left child 
//		  i++;
//		  }
		  
	  }// end getSmallestGDP
	  
	  /** 
	  * Print the bottom x specified countries by the user
	  * regarding GDP per capita
	  * 
	  * @param number of countries to print
	  */
	  public void printBottomCountries(int c) {
		  
		  for (int i = 0; i <= c; i++) {
			  bottomArray[i] = getMinGDP(root);
		  }
		  
		  for (int j = 0; j <= c; j++) {
			  bottomArray[j].printNode();
			  
		  }
		  
	  }
	  
	  
	  /** 
	  * Searches for the top x countries
	  */
	  private void getMaxGDP() {
		  
		  Node current;
		  Node max = null; 
		  int i = 0;
		  
		  current = root;                   // start at root 
		  while(current != null)            // until the bottom,
		  { 
		  max = current;                // remember node 
		  current = current.rightChild;   // go to left child 
		  topArray[i] = max;
		  i++;
		  }
	  }
	  
	  /** 
	  * Print the top x specified countries by the user
	  * regarding GDP per capita
	  * 
	  * @param number of countries to print
	  */
	  public void printTopCountries(int c) {
		  getMaxGDP();
		  for (int i = 0; i < c; i++) {
			  topArray[i].printNode();
		  }
	  }
	  
}// end BinarySearchTree
