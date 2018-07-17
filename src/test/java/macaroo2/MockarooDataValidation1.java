package macaroo2;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MockarooDataValidation1 {
	WebDriver driver;
	List<String> allList = new ArrayList<String>();
	List<String> cityList = new ArrayList<String>();
	List<String> countryList = new ArrayList<String>();
	Set<String> city ;
	Set<String> country;
	
	List<String> uniqueCityList = new ArrayList<String>();
	List<String> uniqueCountryList = new ArrayList<String>();

	
	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().fullscreen();
		//java2.Navigate to https://mockaroo.com/
		driver.get("https://mockaroo.com/");

		
	}
	@Test(priority=1)
	public void titleVerification() {
		//3.Assert title is correct.
		assertEquals("Mockaroo - Random Data Generator and API Mocking Tool | JSON / CSV / SQL / Excel", driver.getTitle());}
	
	@Test(priority=2)
	public void display() {
		//4.Assert Mockaroo andrealistic data generator are displayed
		assertEquals(driver.findElement(By.xpath("//div[@class='brand']")).getText(), "mockaroo");
		assertEquals( driver.findElement(By.xpath("//div[@class='tagline']")).getText(),"realistic data generator");
}
	@Test(priority=3)
	public void clickX() {
		//5. Remote all existing fields by clicking on x icon link
		List<WebElement> x = driver.findElements(By.xpath("//a[@class='close remove-field remove_nested_fields']"));
		for(WebElement y : x) {
			y.click();}}
	
	@Test(priority=4) 
	public void displayFTO() {
		//6. Assert that ‘Field Name’ , ‘Type’, ‘Options’  labelsare displayed
		assertEquals(driver.findElement(By.xpath("//div[@class='column column-header column-name']")).getText(), "Field Name");
		assertEquals(driver.findElement(By.xpath("//div[@class='column column-header column-type']")).getText(), "Type");
		assertEquals(driver.findElement(By.xpath("//div[@class='column column-header column-options']")).getText(), "Options");
}
	@Test(priority=5)
	public void isEnable() {
		//7. Assert that ‘Add another field’ button is enabled. 
		//Find using xpath withtagname and text. isEnabled() method in selenium
		assertEquals(driver.findElement(By.id("columns_fields_blueprint")).isEnabled(),true);}
	@Test(priority=6)
	public void defaultNFL() {
		//8. Assert that default number of rows is 1000.
		//9. Assert thatdefault format selection is CSV 
		//10. Assert that Line Ending is Unix(LF)
		assertEquals(driver.findElement(By.xpath("//input[@id='num_rows']")).getAttribute("value"), "1000");
		assertEquals(driver.findElement(By.xpath("//select[@id='schema_file_format']")).getAttribute("value"), "csv");
		assertEquals(driver.findElement(By.xpath("//select[@id='schema_line_ending']")).getAttribute("value"), "unix");
	}
	@Test(priority=7) 
	public void checked() {
		//11. Assert that header checkbox is checked and BOM is unchecked
		assertTrue(driver.findElement(By.xpath("//input[@id='schema_include_header']")).isSelected());
		assertFalse(driver.findElement(By.xpath("//input[@id='schema_bom']")).isSelected());

}
	@Test(priority=8) 
	public void addFieldCity() {
		//12. Click on ‘Add another field’ and enter name “City”
		driver.findElement(By.xpath("(//a[@data-blueprint-id='columns_fields_blueprint'])[1]")).click();
		driver.findElement(By.xpath("//div[@id='fields']/div[7]/div[2]/input[starts-with(@id,'schema_columns_attributes_')]")).sendKeys("City");
		
}
	@Test(priority=9) 
	public void choseType() throws InterruptedException {
		//13. Click on Choose type and assert that Choose a Type dialog box is displayed.
		//14. Search for “city” and click on City on search results.
		assertEquals(driver.findElement(By.xpath("//div[@id='fields']/div[7]/div[3]//input[@class='btn btn-default']")).isDisplayed(),true);
		driver.findElement(By.xpath("//div[@id='fields']/div[7]/div[3]//input[@class='btn btn-default']")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@id='type_search_field']")).sendKeys("city");
		driver.findElement(By.xpath("//div[@class='examples']")).click();

		
}
	//15. Repeat steps 12-14 with field name and type “Country”
	@Test(priority=10) 
	public void forCountry() throws InterruptedException {
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//a[@data-blueprint-id='columns_fields_blueprint'])[1]")).click();
		driver.findElement(By.xpath("//div[@id='fields']/div[8]/div[2]/input[starts-with(@id,'schema_columns_attributes_')]")).sendKeys("Country");
		assertEquals(driver.findElement(By.xpath("//div[@id='fields']/div[8]/div[3]//input[@class='btn btn-default']")).isDisplayed(),true);
		driver.findElement(By.xpath("//div[@id='fields']/div[8]/div[3]//input[@class='btn btn-default']")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@id='type_search_field']")).sendKeys("country");
		driver.findElement(By.xpath("(//div[@class='examples'])[1]")).click();}
//	@Test(priority=11) 
//	public void download() throws InterruptedException {
//		//16. Click on Download Data.
//		Thread.sleep(3000);
//		driver.findElement(By.xpath("//button[@id='download']")).click();
//			}
	@Test(priority=12) 
	public void file() throws IOException {
		//17. Open the downloaded file using BufferedReader.
		FileReader fr = new FileReader("/Users/merveulgur/Downloads/MOCK_DATA.csv");
		BufferedReader br = new BufferedReader(fr);
		//18. Assert that first row is matching with Field names that we selected.
		String expected = "City,Country";
		String actual = br.readLine();
		assertEquals(actual, expected);
		//19. Assert that there are 1000 records
		String temp = br.readLine();
		while(temp != null) {
			allList.add(temp);
			temp = br.readLine();}
		assertEquals(allList.size(),1000);}
	@Test(priority=13) 
	public void loadingCities() {
		//20. From file add all Cities to Cities ArrayList
		for (int i = 0; i < allList.size(); i++) {
			cityList.add(allList.get(i).substring(0, allList.get(i).indexOf(",")));
			}System.out.println(cityList);
		
		
	}
	@Test(priority=13) 
	public void loadingCountries() {
		//21. Add all countries to Countries ArrayList
		for (int i = 0; i < allList.size(); i++) {
			countryList.add(allList.get(i).substring(allList.get(i).indexOf(",") + 1)  );
}			System.out.println(countryList);

	}
	@Test(priority=14) 
	public void longestCity() {
	//	22. Sort all cities and find the city with the longest name and shortest name
		Collections.sort(cityList);
		String longest = cityList.get(0);
		String shortest = cityList.get(0);
		for (int i = 1; i < cityList.size(); i++) {
			if(cityList.get(i).length()>longest.length())
				longest = cityList.get(i);
			if(cityList.get(i).length() <shortest.length())
				shortest = cityList.get(i);}
}
	@Test(priority=15)
	public void frequencyCountry() {
		//23. In Countries ArrayList, find how many times each Country is mentioned. and print out
		//24. From file add all Cities to citiesSet HashSet
		System.out.println("**********************************");
		country = new HashSet<String>(countryList);
		for(String each1 : country) {
//			int count = 0;
//			for(String each2 : countryList) {
//				if(each1.equals(each2)) {
//					count++;
//				}
		System.out.println(each1 + Collections.frequency(countryList, each1));
				}
			//System.out.println(each1 + "-" + count);}
		}
	@Test(priority=16) 
	public void uniqueCity() {
		
	//25. Count how many unique cities are in Cities ArrayList and assert that it is matching with the count of citiesSet HashSet.
		city =new HashSet<String>(cityList);

		for(int i = 0; i < cityList.size(); i++) {
			
			if(uniqueCityList.contains(cityList.get(i))) {
				continue;
			} uniqueCityList.add(cityList.get(i));
		}
		assertEquals(uniqueCityList.size(),city.size());
		System.out.println(uniqueCityList.size()+" uniquecity");
		System.out.println(city.size());
	
	}
	@Test(priority=17) 
	public void uniqueCountry() {
		//26. Add all Countries to countrySet HashSet 
		//27. Count how many unique countries are in Countries ArrayList and assert that it is matching with the count of countrySet HashSet.


		country = new HashSet<String>(countryList);

		
		for(int i = 0; i < cityList.size(); i++) {
			if(uniqueCountryList.contains(countryList.get(i))) {
				continue;
			} uniqueCountryList.add(countryList.get(i));
	}
		assertEquals(uniqueCountryList.size(), country.size());
		System.out.println(uniqueCountryList.size()+" uniquecountry");
		System.out.println(country.size());

	
	

	//28. Push the code to any GitHub repo that you have and submit the url
	
	


	}}

