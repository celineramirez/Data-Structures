package project3;

/**
 * <p>
 * Hash table class creates the hash table functions called in project3 class.
 * </p>
 * 
 * <p>
 * The hash table is implemented as an array of double ended singly linked list
 * of nodes
 * </p>
 * 
 * @author Celine Ramirez
 * @version June 24, 2022
 *
 */
public class HashTable {

	private class Node {
		String name;
		long population;
		long cases;
		Node nextNode;

		/**
		 * Constructor for node class.
		 * 
		 * @param String name
		 * @param long   population
		 * @param long   cases
		 */
		public Node(String name, long population, long cases) {
			this.name = name;
			this.population = population;
			this.cases = cases;
		}

		/**
		 * Method to display the node
		 * 
		 * @return formatted node information
		 */
		public void printNode() {
			System.out.printf("%-30s %-20.2f\n", name, (double) cases / population * 100000);
		}
	} // end class Node

	private class linkList {
		private Node first;
		private Node last;

		/**
		 * Constructor for the linked list
		 * 
		 */
		public linkList() {
			first = null;
			last = null;
		}

		/**
		 * Insert a node into the linked list
		 * 
		 * @param Node inNode
		 */
		public void insertList(Node inNode) {
			String key = inNode.name;
			Node previous = null;
			Node current = first;

			while (current != null) {
				previous = current;
				current = current.nextNode;
			}

			if (previous == null) {
				first = inNode;
			}

			else {
				previous.nextNode = inNode;
			}
			inNode.nextNode = current;
		}// end insert sortedList

		/**
		 * Delete a node from the linked list by country name
		 * 
		 * @param String name
		 */
		public void delete(String name) {

			Node previous = null;
			Node current = first;

			while (current != null && name.compareTo(current.name) > 0) {
				previous = current;
				current = current.nextNode;
			}

			// disconnect the link

			if (previous == null) { // if first link
				first = first.nextNode;

			} else {
				previous.nextNode = current.nextNode;
			}

		}// end delete sortedList

		/**
		 * Traverse the linked list at array index to find node
		 * 
		 * @param String name
		 * @return found Node
		 */
		public Node findList(String name) {// iterate through linked list at index
			Node current = first;

			while (current != null && current.name.compareTo(name) < 0) {
				if (current.name == name) {
					return current;
				} else {
					current = current.nextNode;

				}
			} // end while

			return null;
		}// end find sortedList

		/**
		 * Display the linked list at an array index
		 * 
		 * @return linked list of nodes at array index
		 */
		public void displayList() {
			Node current = first;
			if (current == null) {
				System.out.println("Empty");
			}
			while (current != null) {
				current.printNode();
				current = current.nextNode;
			}
			System.out.println("");
			
		}

	} // end sortedList

	private linkList[] hashArray;
	private int arraySize;

	/**
	 * Hash table constructor
	 * 
	 * @param int size of hash table array
	 */
	public HashTable(int size) {
		arraySize = size;
		hashArray = new linkList[arraySize];
		for (int i = 0; i < arraySize; i++) {
			hashArray[i] = new linkList();
		}
	}// end hashTable constructor

	/**
	 * The hash function. This function calculates the numerical value of the
	 * country name, sums up the value of all the characters integer values,
	 * modulus' the result by the size of the array which is 293, then hashes the
	 * function at the calculated hash value
	 * 
	 * @param String country name
	 * @return index to hash data item at
	 */
	private int hashFunc(String name) {

		int sum = 0;

		for (int i = 0; i < name.length(); i++) {

			char c = name.charAt(i);
			sum += c;

		}
		return sum % 293;
	}// end hashFunc

	/**
	 * Insert a country into the link list at the hashed array index calculated by
	 * hashFunc()
	 * 
	 * @param String country
	 * @param long   population
	 * @long cases
	 */
	public void insert(String country, long population, long cases) {

		Node cur = new Node(country, population, cases);
		int hashVal = hashFunc(country); // hash key
		hashArray[hashVal].insertList(cur);

	}// end hashTable insert

	/**
	 * Traverse the array to find the country
	 * 
	 * @param String country name
	 * @return -1 if not found
	 * @return int hashVal - index where country was found
	 */
	public int find(String country) {// iterate through array of linked lists
		int hashVal = hashFunc(country);
//		Node findNode = hashArray[hashVal].findList(country);
		try {
			while (hashArray[hashVal] != null) {
				if (hashArray[hashVal].first.name.compareTo(country) == 0) {
					return hashVal;
				}
				hashArray[hashVal].first = hashArray[hashVal].first.nextNode;
				hashVal %= 293;
			}
			return -1;
		} catch (NullPointerException e) {
			return -1;
		}

	}// end hashTable delete

	/**
	 * Retrieve the case rate of the country to find
	 * 
	 * @param int hashVal - array index country was found at
	 */
	public double caseRate(int hashVal) {
		double CR = (double) hashArray[hashVal].first.cases / hashArray[hashVal].first.population * 100000;
		return CR;
	}

	/**
	 * Delete a country from the hash table
	 * 
	 * @param String country name
	 */
	public void delete(String country) {
		int hashVal = hashFunc(country);
		try {
			hashArray[hashVal].delete(country);
			System.out.println(country + " was deleted from hash table");
		} catch (NullPointerException n) {
			System.out.println(country + " is not a country");
		}
	}// end hashTable delete

	/**
	 * Print the hash table
	 * 
	 * @return the entire hash table
	 */
	public void display() {
		for (int k = 0; k < 293; k++) {
			System.out.print(k + ". ");
			hashArray[k].displayList();
		}
	}// end hashTable display

	/**
	 * Print the amount of empty cells and cells that had multiple data items hashed
	 * to the same index (collisions)
	 * 
	 * @return Information about the number of empty cells and collisions in the
	 *         hash table
	 */
	public void printEmptyandCollidedCells() {
		int empty = 0;
		int colls = 0;

		for (int i = 0; i < 293; i++) {
			if (hashArray[i].first == null) {
				empty++;
			}
			if (hashArray[i].first != null && hashArray[i].first.nextNode != null) {
				colls++;
			}
		} // end for
		System.out.printf("There are %d empty cells and %d collisions in the hash table\n", empty, colls);
	}// end printEmptyandCollided cells
}// end hashTable
