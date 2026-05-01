package rahulshettyacademy.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import PageObjects.LandingPage;

public class BaseTest {

	public WebDriver driver;

	public LandingPage landingpage;
	// initilizationDriver() - this prepares our driver

	public WebDriver initilizationDriver() throws IOException {
		Properties prop = new Properties();
		// System.getProperty("user.dir") --> helps to retrive our system project path
		// (Eg, path of Selenium Framework Design
		// "src\\test\\java\\rahulshettyacademy\\Resources\\GlobalData.properties" -- >
		// right click on the file GlobalData.properties file to get the path
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
				+ "\\src\\test\\java\\rahulshettyacademy\\Resources\\GlobalData.properties");
		prop.load(fis);

		// code to read the maven comnad if we pass any paramenters like browser
		// dyanmically using java terminal operator

		// terminal operator --> Contrion ? (if condition is true execute this
		// statement) : (if condition is false execute this statement)

		String browserName = System.getProperty("browser") != null ? System.getProperty("browser")
				: prop.getProperty("browser");

		// key --> browser, value --> chrome. We can get in GlobalData.property file
		// String browserName = prop.getProperty("browser");

		if (browserName.contains("chrome")) {

			ChromeOptions option = new ChromeOptions();

			option.addArguments("--remote-allow-origins=*");
			if (browserName.contains("headless")) {
				option.addArguments("--remote-allow-origins=*").addArguments("headless");
			}

			System.setProperty("webdriver.chrome.driver", "D:\\Drivers\\chromedriver_win32\\chromedriver.exe");
			driver = new ChromeDriver(option);
			driver.manage().window().setSize(new Dimension(1440, 900));

		}

		else if (browserName.contains("edge")) {
			EdgeOptions option2 = new EdgeOptions();
			option2.addArguments("--remote-allow-origins=*");
			if (browserName.contains("headless")) {
				option2.addArguments("--remote-allow-origins=*").addArguments("--headless");
			}
			System.setProperty("webdriver.edge.driver", "D:\\Drivers\\edgedriver_win64\\msedgedriver.exe");
			driver = new EdgeDriver(option2);
		}

		else if (browserName.contains("firefox")) {
			FirefoxOptions option1 = new FirefoxOptions();
			option1.addArguments("--remote-allow-origins=*");
			if (browserName.contains("headless")) {
				option1.addArguments("--remote-allow-origins=*").addArguments("--headless");
			}
			System.setProperty("webdriver.gecko.driver", "D:\\Drivers\\geckodriver-v0.33.0-win64\\geckodriver.exe");
			driver = new FirefoxDriver(option1);
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return driver;
	}

	public List<HashMap<String, String>> getJasonDataToMap(String filePath) throws IOException {
		// read Jason to string
		@SuppressWarnings("deprecation")
		String jasonContent = FileUtils.readFileToString(new File(filePath));
		// Convert string to Hash Map (Get a dependncy Jackson DataBind)
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jasonContent,
				new TypeReference<List<HashMap<String, String>>>() {
				});
		return data;
	}

	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
	}

	@BeforeMethod(alwaysRun = true)
	public LandingPage launchApplication() throws IOException {
		driver = initilizationDriver();
		landingpage = new LandingPage(driver);
		landingpage.goTo();
		return landingpage; // we return landing page object beacuse to access the landing page object in
							// other methods in submit order test.
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		// driver.close(); -- this gives some unnecesary erros like
		// "java.net.SocketException: Connection reset"
		// to avoid it use driver.quit();

		driver.quit();
	}

}