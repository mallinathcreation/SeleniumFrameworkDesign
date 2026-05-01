package PageObjects;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import AbstractComponents.AbstractClass;

public class LandingPage extends AbstractClass{

public WebDriver driver;

//WebElement userEmail = driver.findElement(By.id("userEmail"));

public LandingPage(WebDriver driver)

{
	super(driver);
	this.driver = driver;
	PageFactory.initElements(driver, this);
}


@FindBy(id= "userEmail")
WebElement userEmail;

@FindBy(id= "userPassword")
WebElement passwordEle;

@FindBy(id= "login")
WebElement userlogin;

@FindBy(css = "[class*='flyInOut']")
WebElement errorMessage;

//div[aria-label='Incorrect email or password.']
//div[@class='ng-tns-c4-8 ng-star-inserted ng-trigger ng-trigger-flyInOut ngx-toastr toast-error']

public void goTo()
{
	driver.get("https://rahulshettyacademy.com/client");
}


public ProductCatalogue logInApplication(String Email, String Password)
{
	userEmail.sendKeys(Email);
	passwordEle.sendKeys(Password);
	userlogin.click();
	
	ProductCatalogue productcataloue = new ProductCatalogue(driver);
	return productcataloue;
}

public String getErrorMessage()

{
	waitForWebElementsToAppear(errorMessage);
	return errorMessage.getText();
}

}
