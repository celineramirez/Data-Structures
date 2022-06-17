/**
 * COP 3530: Project 1 – Array Searches and Sorts
 * <p>
 * Project 1 class begins by creating a country object array, parsing Countries1.csv and stores information in array.
 * The menu of options is repeatedly prompted to the user.
 * Options consist of searching and sorting.
 * This class also prints the spearman rho matrix when selected.
 * The program ends the program when user selects option 7.
 *
 * @author Celine Ramirez
 * @version May 20, 2022
 */
package project1;

import java.util.*;
import java.io.*;

public class project1 {

    /**
     *  Parse the countries1.csv file and store the contents in a country object array.
     *  Prompt the user with a menu of options repeatedly until the quit condition (7) is selected
     *
     * @param  String args[]
     * @return report of sorted countries, single country if searched for and found, and terminate program
     */
    public static void main(String args[]) {

        // variables
        int index = 0;
        Country[] countryArray = null;
        countryArray = new Country[145];

        // input streams
        Scanner fileReader = null;
        Scanner sc = new Scanner(System.in);
        Scanner un = new Scanner(System.in);

        // Prompt user for the file name
        System.out.println("Enter the file name:");
        String fileName = sc.next();

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
            String capitol = fileReader.next();
            float population = fileReader.nextFloat();
            float GDP = fileReader.nextFloat();
            int covidCases = fileReader.nextInt();
            int covidDeaths = fileReader.nextInt();
            int area = fileReader.nextInt();

            // load country objects into country array
            countryArray[index] = new Country(name, capitol, population, GDP, covidCases, covidDeaths, area);
            // increment element counter
            index++;
        }

        fileReader.close(); // close the scanner

        Boolean sort = false; // check if name sort was previously done
        int command = 0; // user choice variable
        String cname = " "; // country search key variable

        
        do { // loop to repeat menu

            //menu
            System.out.println("\n1. Print a countries report\n"
                    + "2. Sort by Name\n"
                    + "3. Sort by Case Fatality Rate\n"
                    + "4. Sort by GDP per capita\n"
                    + "5. Find and print a given country\n"
                    + "6. Print Spearman’s rho matrix\n"
                    + "7. Quit\nEnter a numerical value");

            try {
                command = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice! Enter 1-7:");
                sc.next();
                continue;
            }

            switch (command) {
                case 1: // print a report
                    countryReport(countryArray, index);
                    break;
                case 2: // sort by name, insertion sort
                	sort = true;
                    insertionSort(countryArray, index);
                    System.out.println("Countries sorted by name, enter 1 to see sorted list");

                    break;
                case 3: // sort by CFR, deaths/cases, selection sort
                	sort = false;
                    selectionSort(countryArray, index);
                    System.out.println("Countries sorted by CFR, enter 1 to see sorted list");
                    break;
                case 4: // sort by GDP , bubble sort
                	sort = false;
                    bubbleSort(countryArray, index);
                    System.out.println("Countries sorted by GDP, enter 1 to see sorted list");

                    break;
                case 5: // sequential and binary search
                    System.out.println("Enter the country name:");
                    cname = un.nextLine();
                    System.out.println("Searching for " + cname);

                    if (sort == true) {
                        // search through sorted array using binary search
                        System.out.println("Using binary search...");
                        binarySearch(countryArray, index, cname);
                    } else {
                        // if unsorted use sequential search
                        System.out.println("Using sequential search...");
                        sequentialSearch(countryArray, index, cname);
                    }
                    break;

                case 6: // Spearman's rho
                    spearmanRho(countryArray, index);
                    break;
                case 7:
                    System.out.println("You have exited the program");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice! Enter 1-7:");
            }// end menu
        } while (command != 7);

        sc.close();
        un.close();

    }

    /**
     * Header labels displayed before country data
     *
     * @param  none
     * @return data labels
     */
    public static void header() {
        System.out.printf("|%-40s | %-20s | %-15s | %-20s | %-15s | %-15s | %-15s|\n", "Name", "Capitol", "GDPPC", "CFR", "Case Rate", "Death Rate", "PopDensity");
        for (int j = 0; j <= 160; j++) {
            System.out.print("-");
        }
        System.out.print("\n");
    }

    /**
     * Displays a country report to the user
     *
     * @param  countryArray, nElems
     * @return country report
     */
    public static void countryReport(Country[] countryArray, int nElems) { // print out a country report
        header();
        for (int i = 0; i < nElems; i++) {
            countryArray[i].display();
        } // end for loop
    }// end country report


    /**
     * Use selection sort to sort countries by CFR
     *
     * @param  countryArray, nElems
     * @return countries sorted by case fatality rate
     */
    public static void selectionSort(Country[] countryArray, int nElems) {
        for (int out = 0; out < nElems - 1; out++) {
            int min = out;
            for (int in = out + 1; in < nElems; in++) {
                if ((countryArray[in].getCovidCases() / countryArray[in].getCovidDeaths()) > (countryArray[min].getCovidCases() / countryArray[min].getCovidDeaths())) {
                    min = in;
                }
            }// end inner loop
            if (min != out) {
                Country temp = countryArray[min];
                countryArray[min] = countryArray[out];
                countryArray[out] = temp;
            }
        }// end outer loop
    }// end selection sort

    /**
     * Use bubble sort to sort countries by ascending GDP
     *
     * @param  countryArray, nElems
     * @return countries sorted by ascending GDP
     */
    public static void bubbleSort(Country[] countryArray, int nElems) {
        int out, in = 0;

        for (out = 0; out < nElems; out++) {
            for (in = nElems - 1; in > out; in--) {
                if ((countryArray[in].getGDP() / countryArray[in].getPopulation()) < (countryArray[in - 1].getGDP() / countryArray[in - 1].getPopulation())) {
                    Country temp = countryArray[in];
                    countryArray[in] = countryArray[in - 1];
                    countryArray[in - 1] = temp;
                }// end if
            }// end inner for
        }// end outer for
    }// end bubble sort

    /**
     * Alphabetical sort of countries using insertion sort
     *
     * @param  countryArray, nElems
     * @return countries sorted by name
     */
    public static void insertionSort(Country[] countryArray, int nElems) {

        int in, out = 0;
        Country temp;

        for (out = 1; out < nElems; out++) {
            temp = countryArray[out];
            in = out - 1;

            while ((in >= 0) && (countryArray[in].getName().compareTo(temp.getName())) > 0) {
                countryArray[in + 1] = countryArray[in];
                in--;
            }// end while
            countryArray[in + 1] = temp;
        }// end for
    }// end insertion sort

    /**
     *  If the country array is unsorted, we search sequentially
     *
     * @param  countryArray, nElems, user entered country name
     * @return country if found, otherwise display "Country not found"
     */
    public static void sequentialSearch(Country[] countryArray, int nElems, String userCountry) {

        int i = 0;

        // iterate through array until reaching the country that matches the user input country
        for (i = 0; i < nElems; i++) { // iterate through the array
            if (countryArray[i].getName().compareToIgnoreCase(userCountry) == 0) { // compare each country to user input
                break; // when found break and display
            }
        }
        if (i == nElems) { // if reached end of array, not found
            System.out.println("Country " + userCountry + " not found");
        } else { // display the country
            countryArray[i].displaySearch();
        }

    }// end Sequential Search

    /**
     *  If the country array is sorted, we can use the more efficient binary search algorithm
     *
     * @param  countryArray, nElems, user entered country name
     * @return country if found, otherwise display "Country not found"
     */
    public static void binarySearch(Country[] countryArray, int nElems, String userCountry) {

        int lowerBound = 0;
        int upperBound = nElems - 1;
        int mid = 0;

        while (lowerBound <= upperBound) { // while the two walkers do not cross

            mid = (lowerBound + upperBound) / 2;
            if (countryArray[mid].getName().compareToIgnoreCase(userCountry) == 0) {
                countryArray[mid].displaySearch();
                break;
            }
             
            else if (countryArray[mid].getName().compareToIgnoreCase(userCountry) > 0) {
                    upperBound = mid - 1; //search in the lower half
                }
            else {
                    lowerBound = mid + 1; //search in the upper half
                }
            
        } // end while

        if (lowerBound > upperBound) {
        	System.out.println("Country " + userCountry + " not found");
        }
    } // end binary search 

    /**
     *  Use insertion sort to sort popDensity
     *
     * @param  rank of array being sorted, number of elements
     * @return sorted array
     */
    public static void sortPopDensity(Country[] rankPopDensity, int nElems) {
        int in, out = 0;
        Country temp;

        for (out = 1; out < nElems; out++) {
            temp = rankPopDensity[out];
            in = out - 1;

            while ((in >= 0) && (rankPopDensity[in].getPopulation()/rankPopDensity[in].getArea()) > (temp.getPopulation()/temp.getArea())) {
            	rankPopDensity[in + 1] = rankPopDensity[in];
                in--;
            }// end while
            rankPopDensity[in + 1] = temp;
        }// end for
    }
    
    /**
     *  Use insertion sort to sort covid deaths
     *
     * @param  rank of array being sorted, number of elements
     * @return sorted array
     */
    public static void sortDeaths(Country[] rankDeath, int nElems) {
        int in, out = 0;
        Country temp;

        for (out = 1; out < nElems; out++) {
            temp = rankDeath[out];
            in = out - 1;

            while ((in >= 0) && ((rankDeath[in].getCovidDeaths()/rankDeath[in].getPopulation())*100000 < (temp.getCovidDeaths()/temp.getPopulation())*100000)) {
            	rankDeath[in + 1] = rankDeath[in];
                in--;
            }// end while
            rankDeath[in + 1] = temp;
        }// end for
    }
    
    /**
     *  Use insertion sort to sort covid cases
     *
     * @param  rank of array being sorted, number of elements
     * @return sorted array
     */
    public static void sortCases(Country[] rankCase, int nElems) {
        int in, out = 0;
        Country temp;

        for (out = 1; out < nElems; out++) {
            temp = rankCase[out];
            in = out - 1;

            while ((in >= 0) && ((rankCase[in].getCovidCases()/rankCase[in].getPopulation())*100000 > (temp.getCovidCases()/temp.getPopulation())*100000)) {
            	rankCase[in + 1] = rankCase[in];
                in--;
            }// end while
            rankCase[in + 1] = temp;
        }// end for
    }
    
    /**
     *  Calculate Spearman's rho value
     *
     * @param  two arrays being compared, number of elements
     * @return double Rho
     */
    public static double calcRho(Country[] rankX, Country[] rankY, int nElems) {
    	String countryName;
    	
    	double nElemsSquared = Math.pow(nElems, 2);
    	int rankDiff = 0;
    	double rankDiffSquared = 0;
    	int xIndex = 0;
    	int yIndex = 0;
    	int x = 0;
    	int y = 0;
    	int sum = 0;
    	double rho = 0;
    	
    	for (xIndex = 0; xIndex < nElems; xIndex++) {
    		countryName = rankX[xIndex].getName();
            x = xIndex;
    		for (yIndex = 0; yIndex < nElems; yIndex++) {
                if (rankY[xIndex].getName().compareToIgnoreCase(countryName) == 0) { // compare each country to user input
        			y = yIndex;
                	rankDiff = (x-y);
                	rankDiffSquared = Math.pow(rankDiff,2);
                    sum += rankDiffSquared;
                }// end if
            }// end sequential search
    	}// end outer loop
    	// calculate spearman's rho
    	rho = 1-((6*sum)/(nElems*(nElemsSquared - 1)));
    	return rho;
    }
    /**
     *  Display Spearman's Rho matrix to user
     *
     * @param  countryArray, nElems
     * @return Spearman's Rho matrix
     */
    public static void spearmanRho(Country[] countryArray, int nElems) {
    	Country[] rankGDPPC = countryArray.clone();
    	Country[] rankPopDensity = countryArray.clone();
    	Country[] rankDeaths = countryArray.clone();
    	Country[] rankCases = countryArray.clone();
    	
    	// sort the arrays
    	bubbleSort(rankGDPPC, nElems);
    	sortPopDensity(rankPopDensity, nElems);
    	sortDeaths(rankDeaths, nElems);
    	sortCases(rankCases, nElems);
    	
    	// get the rho values
    	double rho1 = calcRho(rankGDPPC, rankCases, nElems);
    	double rho2 = calcRho(rankGDPPC, rankDeaths, nElems);
    	double rho3 = calcRho(rankPopDensity, rankCases, nElems);
    	double rho4 = calcRho(rankPopDensity, rankDeaths, nElems);
    	
        // format and display
        System.out.println("-----------------------------------------------------------");
        System.out.println("|                |GPD Per Capita       |Population Density|");
        System.out.println("-----------------------------------------------------------");
        System.out.printf("|COVID Case rate |       %.3f        |      %.3f       |\n",rho1, rho3);
        System.out.println("-----------------------------------------------------------");
        System.out.printf("|COVID Death rate|        %.3f        |      %.3f       |\n",rho2,rho4);
        System.out.println("-----------------------------------------------------------");
    }
}// end project1 class