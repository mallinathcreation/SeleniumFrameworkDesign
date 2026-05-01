package rahulshettyacademy;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.NoInjection;
import org.testng.annotations.Test;

import com.sun.net.httpserver.Authenticator.Retry;

import PageObjects.CartPage;
import PageObjects.Checkout;
import PageObjects.ConfirmationPage;
import PageObjects.LandingPage;
import PageObjects.ProductCatalogue;
import rahulshettyacademy.TestComponents.BaseTest;

public class ErrorValidationTest  extends BaseTest{

	String productName = "ZARA COAT 3";
	//String methodName = "productCatalogueErrorTest(@NoInjection String productName)";
	
	//retryAnalyzer =  written like --> package.classname.class
	
	@Test(groups= {"Error Handling"}, retryAnalyzer = rahulshettyacademy.TestComponents.Retry.class)
	public void loginErrorTest() throws IOException, InterruptedException {
		// TODO Auto-generated method stub

		//String productName = "ZARA COAT 3";
		// String selectedcountry = "India";

		//WebDriverManger -- same works like System.set property. Useful as it downloads automatically the
		// latest driver version which is compatible to our browser.
		
		landingpage.logInApplication("joyeee@gmail.com", "Joyeee@007");
		//Assert.assertFalse(true);
		Assert.assertEquals("Incorrect email or password.", landingpage.getErrorMessage());

	}
	
	@Test(groups= {"Error Handling"})
	public void productCatalogueErrorTest() throws InterruptedException
	
	{
		//productName = "ZARA COAT 3";
		ProductCatalogue productcataloue = landingpage.logInApplication("joyee@gmail.com", "Joyee@007");
		List<WebElement> products = productcataloue.getProductList();
		productcataloue.getProduct(productName);
		productcataloue.addToCart(productName);
		CartPage cartpage = productcataloue.clickCartHeader();
		Boolean match = cartpage.verifyCartElements("ZARA COAT 33");
		Assert.assertFalse(match);
	
	}
	
	


}
