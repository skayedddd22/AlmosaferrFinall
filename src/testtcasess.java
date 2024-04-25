import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.bouncycastle.crypto.signers.ISOTrailers;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
public class testtcasess extends ParametersClass{

	@BeforeTest
	public void Setup() {
		thebeginingofTheWebSite();
	}



	@Test(enabled = false)
	public void CheckTheDepatureAndArrivalDate() {

		// we got two elements from the website 1- for the depature date 2- for the
		// return date
		WebElement ActualDepatureDate = driver
				.findElement(By.cssSelector("div[class='sc-OxbzP sc-lnrBVv gKbptE'] span[class='sc-fvLVrH hNjEjT']"));
		WebElement ActualReturnDate = driver
				.findElement(By.cssSelector("div[class='sc-OxbzP sc-bYnzgO bojUIa'] span[class='sc-fvLVrH hNjEjT']"));

		

		int ActualDeptureDateValue = Integer.parseInt(ActualDepatureDate.getText());

		int ActualReturnDateValue = Integer.parseInt(ActualReturnDate.getText());


		LocalDate today = LocalDate.now();

		int ExpectedDepatureDateValue = today.plusDays(1).getDayOfMonth();
		int ExpectedReturnDateValue = today.plusDays(2).getDayOfMonth();

		Assert.assertEquals(ActualDeptureDateValue, ExpectedDepatureDateValue);
		Assert.assertEquals(ActualReturnDateValue, ExpectedReturnDateValue);

		String dayElementOnTheWebsite = driver
				.findElement(By.cssSelector("div[class='sc-OxbzP sc-lnrBVv gKbptE'] span[class='sc-eSePXt ljMnJa']"))
				.getText().toUpperCase();
		Assert.assertEquals(dayElementOnTheWebsite, today.plusDays(1).getDayOfWeek().toString());

		String helooooooooooooo = today.plusDays(1).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.FRANCE)
				.toString();

		System.out.println(today.getMonth());
	}

	@Test(enabled = false)

	public void changeTheLanguageOfTheWebsiteRandomly() throws InterruptedException {

		String[] myUrls = { "https://global.almosafer.com/ar", "https://global.almosafer.com/en" };
		int randomIndex = rand.nextInt(myUrls.length);
		driver.get(myUrls[randomIndex]);
		WebElement hotelTab = driver.findElement(By.id("uncontrolled-tab-example-tab-hotels"));
		hotelTab.click();

		Thread.sleep(2000);

		WebElement SearchHotelInput = driver.findElement(By.className("phbroq-2"));

		String ActualLanguage = driver.findElement(By.tagName("html")).getAttribute("lang");
		if (driver.getCurrentUrl().contains("ar")) {

			Assert.assertEquals(ActualLanguage, "ar");
			SearchHotelInput.sendKeys(arabicCitiesNames[randomArabic]);
			Thread.sleep(2000);

			WebElement cityList = driver.findElement(By.className("phbroq-4"));
			List<WebElement> myItems = cityList.findElements(By.tagName("li"));
			myItems.get(1).click();

		} else {

			Assert.assertEquals(ActualLanguage, "en");
			SearchHotelInput.sendKeys(englishCitiesNames[randomEnglish]);
			Thread.sleep(2000);
			WebElement cityList = driver.findElement(By.className("phbroq-4"));
			List<WebElement> myItems = cityList.findElements(By.tagName("li"));
			myItems.get(1).click();

		}
		WebElement vistorinput = driver.findElement(By.className("tln3e3-1"));

		Select mySelector = new Select(vistorinput);
		int randomIndexForVistor = rand.nextInt(2);

		mySelector.selectByIndex(randomIndexForVistor);
		WebElement SearchButton = driver.findElement(By.className("sc-1vkdpp9-6"));
		SearchButton.click();

		Thread.sleep(20000);

		String HotelSearchResult = driver.findElement(By.className("sc-cClmTo")).getText();
		
		if (driver.getCurrentUrl().contains("ar")) {
			boolean ActualResult = HotelSearchResult.contains("وجدنا");
			boolean ExpectedResult = true;
			Assert.assertEquals(ActualResult, ExpectedResult);
			WebElement LowestPriceFilter = driver.findElement(By.className("kgqEve"));
			LowestPriceFilter.click();

		} else {
			boolean ActualResult = HotelSearchResult.contains("found");
			boolean ExpectedResult = true;
			Assert.assertEquals(ActualResult, ExpectedResult);
			WebElement LowestPriceFilter = driver.findElement(By.className("jurIdk"));
			LowestPriceFilter.click();

		}

		Thread.sleep(5000);

		WebElement PricesSection = driver.findElement(By.cssSelector(".sc-htpNat.KtFsv.col-9"));

		List<WebElement> myPrices = PricesSection.findElements(By.className("Price__Value"));

		int lowestPrice = Integer.parseInt(myPrices.get(0).getText());
		int highestPrice = Integer.parseInt(myPrices.get(myPrices.size() - 1).getText());
		
	
		System.out.println(lowestPrice+ " this is the lowest price " );
		System.out.println(highestPrice+ " this is the highest price " );
		Assert.assertEquals(highestPrice > lowestPrice, true);

	}

	@AfterTest
	public void myAfterTest() {
	}

}
