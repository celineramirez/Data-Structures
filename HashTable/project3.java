/** 
 * COP 3530: Project 3 – Hash Tables 
 * <p> 
 * Project3 class reads the countries3.csv file in and
 * prompts user with a menu of hash table operations
 * 
 * @author Celine Ramirez
 * @version June 24, 2022
 */
package project3;

import java.util.*;
import java.io.*;

public class project3 {

	/**
	 * Parse the countries3.csv file and store the contents in a country object
	 * array. Prompt the user with a menu of options repeatedly until the quit
	 * condition (6) is selected
	 * 
	 * @param args[]
	 */
	public static void main(String args[]) {

		// new hashTable
		HashTable ht = new HashTable(293);

		int count = 0;

		// input streams
		Scanner fileReader = null;
		Scanner sc = new Scanner(System.in);
		Scanner un = new Scanner(System.in);

		// Prompt user for the file name
		System.out.println("COP3530 Project 3\nEnter the file name:");
		String fileName = sc.next();

		// Handles file not found exception
		try {
			fileReader = new Scanner(new File(fileName));
		} catch (FileNotFoundException f) {
			System.out.println("File not found");
			System.exit(1);
		} // end try catch

		// parse the file
		fileReader.useDelimiter(",|\n");
		fileReader.nextLine();
		while (fileReader.hasNext()) {
			String name = fileReader.next();
			String capitol = fileReader.next();
			long population = fileReader.nextLong();
			double GDP = fileReader.nextDouble();
			int covidCases = fileReader.nextInt();
			int covidDeaths = fileReader.nextInt();
			int area = fileReader.nextInt();
			double CFR = fileReader.nextDouble();
			double GDPPC = fileReader.nextDouble();
			double caseRate = fileReader.nextDouble();
			double deathRate = fileReader.nextDouble();
			double popDensity = fileReader.nextDouble();

			long caseR = (long) caseRate; // cast

			// insert to hashTable
			ht.insert(name, population, caseR);
			count++;
		}

		System.out.println("There were " + count + " records read into the hash table.");
		fileReader.close(); // close the file reader

		int command = 0; // user choice variable
		String cname = " "; // country search key variable
		long pop = 0;
		long crate = 0;
		int findRes = 0;
		double findCR = 0;

		do { // loop to repeat menu

			// menu
			System.out.println("\n1. Print hash table\n" + "2. Delete a country of a given name\n"
					+ "3. Insert a country of a given name\n"
					+ "4. Search and print a country and its case rate for a given name\n"
					+ "5. Print numbers of empty cells and collided cells\n" + "6. Exit\n"
					+ "Enter a numerical value:");

			try {
				command = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Invalid choice! Enter 1-6:");
				sc.next();
				continue;
			}

			switch (command) {
			case 1: // print hash table
				System.out.printf("\n%-30s %-20s\n", "Country", "Case Rate");
				System.out.println("--------------------------------------------");
				ht.display();
				break;
			case 2: // delete by name
				System.out.println("Enter country to delete");
				cname = un.nextLine();
				ht.delete(cname);
				break;
			case 3: // insert by name
				try {
					System.out.println("Enter a country name");
					cname = un.nextLine();
					System.out.println("Enter the country population");
					pop = un.nextLong();
					System.out.println("Enter country COVID cases");
					crate = un.nextLong();
					ht.insert(cname, pop, crate);
					System.out.println(cname + " is inserted to hash table");
				} catch (InputMismatchException e) {
					System.out.println("Invalid input");
				}
				break;
			case 4: // search and print country and case rate by name
				System.out.println("Enter country name:");
				cname = un.nextLine();
				findRes = ht.find(cname);

				if (findRes != -1) {
					findCR = ht.caseRate(findRes);
					System.out.printf("%s was found at index %d with a case rate of %.3f\n", cname, findRes, findCR);
				} else if (findRes == -1) {
					System.out.println("Country not found");
				}
				break;
			case 5: // number of collided and empty cells
				ht.printEmptyandCollidedCells();
				break;
			case 6: // exit
				System.out.println("You have exited the program");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid choice! Enter 1-6:");
			}// end menu
		} while (command != 6);

		sc.close();
		un.close();

	}

}// end project3 class
