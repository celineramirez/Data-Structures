/** 
 * Country class to define a country object called in project 1
 * get and set entity attributes
 * and display country data via a display() method
 * 
 * @author Celine Ramirez
 * @version May 20, 2022 
 */
package project1;

public class Country {
  // country attributes
  private String name;
  private String capitol;
  private float population;
  private float GDP;
  private float covidCases;
  private float covidDeaths;
  private int area;

  /** 
  * Country object constructor
  * 
  * @param  country name, country capitol, population, gross domestic product (GPD), total covid cases, total covid deaths, country area
   */
  public Country(String name, String capitol, float population, float GDP, float covidCases, float covidDeaths, int area) {
    this.name = name;
    this.capitol = capitol;
    this.population = population;
    this.GDP = GDP;
    this.covidCases = covidCases;
    this.covidDeaths = covidDeaths;
    this.area = area;
  }

  /** 
  * Get the country name
  * 
  * @return country name
   */
  public String getName() {
    return name;
  }

  /** 
  * Set the country name
  * 
  * @param  String name
   */
  public void setName(String name) {
    this.name = name;
  }

  /** 
  * Get the country capitol
  * 
  * @return country capitol
   */
  public String getCapitol() {
    return capitol;
  }

  /** 
  * Set the country capitol
  * 
  * @param  String capitol
   */
  public void setCapitol(String capitol) {
    this.capitol = capitol;
  }

  /** 
  * Get the country population
  * 
  * @return country population
   */
  public float getPopulation() {
    return population;
  }

  /** 
  * Set the country population
  * 
  * @param  float population
   */
  public void setPopulation(float population) {
    this.population = population;
  }

  /** 
  * Get the country gross domestic product
  * 
  * @return country GDP
   */
  public float getGDP() {
    return GDP;
  }

  /** 
  * Set the country gross domestic product
  * 
  * @param  float GDP
   */
  public void setGDP(float GDP) {
    this.GDP = GDP;
  }

  /** 
  * Get a country's reported covid cases
  * 
  * @return covid cases
   */
  public float getCovidCases() {
    return covidCases;
  }

  /** 
  * Set the country's number of reported covid cases
  * 
  * @param  int covidCases
   */
  public void setCovidCases(int covidCases) {
    this.covidCases = covidCases;
  }

  /** 
  * Get a country's reported covid deaths
  * 
  * @return covid deaths
   */
  public float getCovidDeaths() {
    return covidDeaths;
  }

  /** 
  * Set the country's number of reported covid deaths
  * 
  * @param  int covidDeaths
   */
  public void setCovidDeaths(int covidDeaths) {
    this.covidDeaths = covidDeaths;
  }

  /** 
  * Get the country area
  * 
  * @return country area
   */
  public int getArea() {
    return area;
  }
  
  /** 
  * Set the country's area
  * 
  * @param  int area
   */
  public void setArea(int area) {
    this.area = area;
  }
  
  /** 
  * Format and display country data
  * 
  * @return country data as a string
   */
  public void display() {// name, capitol, GDPPC, CFR .6, case rate, death rate, popDensity
		 double GDPPC = GDP/population;
		 double CFR = (covidDeaths/covidCases);
		 double caseRate = (covidCases/population)*100000;
		 double deathRate = (covidDeaths/population)*100000;
		 double popDensity = population/area;
    System.out.printf("|%-40s | %-20s | %15.3f | %20.6f | %15.3f | %15.3f | %15.3f|\n", name, capitol, GDPPC, CFR, caseRate, deathRate, popDensity);
  }
  
  /** 
  * Format and display country data for binary and sequential searches
  * 
  * @return country data as a string
   */
  public void displaySearch() {
		 double GDPPC = GDP/population;
		 double CFR = (covidDeaths/covidCases);
		 double caseRate = (covidCases/population)*100000;
		 double deathRate = (covidDeaths/population)*100000;
		 double popDensity = population/area;
	   	 
	        System.out.println("\nFound country: ");
	        System.out.println("Name:         " +name);
	        System.out.println("Capitol:      " +capitol);
	        System.out.printf("GDPPC:        %.3f\n",GDPPC);
	        System.out.printf("CFR:          %.6f\n",CFR);
	        System.out.printf("CaseRate:     %.3f\n",caseRate);
	        System.out.printf("DeathRate:    %.3f\n",deathRate);
	        System.out.printf("PopDensity:   %.3f\n",popDensity);
	    }
}