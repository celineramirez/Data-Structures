package project2;

/** 
 * COP 3530: Project 2 – Binary Search Trees 
 * <p> 
 * This class reads in the Countries2.csv file into a binary search tree
 * Then, it prompts a menu of 9 binary search tree operations to the user
 * 
 * @author Celine Ramirez
 * @version June 12, 2022
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class project2 {
	
	public static void header() {
		System.out.printf("%-40s | %-40s\n", "Country Name", "GDP per capita");
		System.out.println("------------------------------------------------------------");
	}// end header
	
	/**
	 * This class reads in the Countries2.csv file into a binary search tree
	 * Then, it prompts a menu of 9 binary search tree operations to the user
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		BinarySearchTree bst = new BinarySearchTree();
		
        // input streams
        Scanner fileReader = null;
        Scanner sc = new Scanner(System.in);
        Scanner un = new Scanner(System.in);

        // Prompt user for the file name
//        System.out.println("Enter the file name:");
//        String fileName = sc.next();
        String fileName = "Countries2.csv";

        // Handles file not found exception
        try {
            fileReader = new Scanner(new File(fileName));
        } catch (FileNotFoundException f) {
            System.out.println("File not found");
            System.exit(1);
        }// end try catch

        // parse the file
        fileReader.useDelimiter(",|\r\n");
        fileReader.nextLine();
        while (fileReader.hasNext()) {
            String name = fileReader.next();
            fileReader.next();
            float population = fileReader.nextFloat();
            float GDP = fileReader.nextFloat();
            fileReader.nextInt();
            fileReader.nextInt();
            fileReader.nextInt();
            
            double GDPPC = GDP/population;
            
            // insert data into binary search tree
            bst.insert(name, GDPPC);
        }// end parse file
        
        fileReader.close(); // close the scanner
		
        int command = 0; // user choice variable
        String cname = " "; // country search key variable
        double enterGDPPC = 0;
        int numCountries = 0;
        
        do { // loop to repeat menu

            //menu
            System.out.println("\n1. Print tree inorder\n"
                    + "2. Print tree preorder\n"
                    + "3. Print tree postorder\n"
                    + "4. Insert a country with name and GDP per capita\n"
                    + "5. Delete a country for a given name\n"
                    + "6. Search and print a country and its path for a given name\n"
                    + "7. Print bottom countries regarding GDPPC\n"
                    + "8. Print top countries regarding GDPPC\n"
                    + "9. Exit\n"
                    + "Enter a numerical value\n");

            try {
                command = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice! Enter 1-9:");
                sc.next();
                continue;
            }

            switch (command) {
                case 1: // print in order
                	header();
                    bst.printInorder();    
                    break;
                    
                case 2: // print pre order
                	header();
                	bst.printPreorder();
                    break;
                    
                case 3: // print post order
                	header();
                	bst.printPostorder();
                    break;
                    
                case 4: // insert new country w/ name and GDPPC
                	System.out.println("Enter country name:");
                	cname = un.nextLine();
                	System.out.println("Enter country GDP per capita:");
                	try {
                	enterGDPPC = un.nextDouble();
                	System.out.println("Inserting: " + cname + " " + enterGDPPC);
                	}
                	catch(InputMismatchException i) {
                		System.out.println("Invalid input GDP per capita must be a numerical value");
                	}
                	bst.insert(cname, enterGDPPC);
                	un.nextLine();
                    break;
                    
                case 5: // delete a country by name
                	System.out.println("Enter country name to delete: ");
                    cname = un.nextLine();
                	try {
                	bst.delete(cname);
                	}
                	catch (NullPointerException e) {
                		System.out.println("Country was not found");
                	}
                	
                    break;
                    
                case 6: // find and print a country by name
                	System.out.println("Enter country name:");
                	cname = un.nextLine();
                	System.out.println("Searching for: " + cname);
                	double foundGDPPC = bst.find(cname);
                	
                	// output
                	if (foundGDPPC == -1) {
                		System.out.println(cname + " not found");
                	}
                	else {
                		System.out.printf("%s was found with a GDP per capita of %.3f\n",cname, foundGDPPC);
                		System.out.println("Path to " + cname + " is: ");
                		bst.path(cname);
                	}
                    break;
                    
                case 7: // print bottom countries
                	System.out.println("Enter the number of countries");
                	numCountries = un.nextInt();
                	System.out.println("Bottom " + numCountries + " regarding GDPPC");
                	bst.printBottomCountries(numCountries);
                    break;
                    
                case 8: // print top countries
                	System.out.println("Enter the number of countries");
                	numCountries = un.nextInt();
                	System.out.println("Top " + numCountries + " regarding GDPPC");
                	bst.printTopCountries(numCountries);
                    break;
                    
                case 9:
                    System.out.println("You have exited the program");
                    System.exit(0);
                    break;
                    
                default:
                    System.out.println("Invalid choice! Enter 1-9:");
            }// end menu
        } while (command != 9);

        sc.close();
        un.close();

    }// end main
}// end project2
