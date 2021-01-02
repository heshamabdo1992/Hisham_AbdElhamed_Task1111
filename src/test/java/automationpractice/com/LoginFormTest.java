package automationpractice.com;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.shaft.gui.browser.BrowserActions;
import com.shaft.gui.browser.BrowserFactory;
import com.shaft.tools.io.ExcelFileManager;
import automationpractice.com.pageObject.Account;
import automationpractice.com.pageObject.CreateAccountForm;
import automationpractice.com.pageObject.SignInForm;
import utils.EmailsGenerator;
import io.github.bonigarcia.wdm.WebDriverManager;
public class LoginFormTest {

	private WebDriver driver;
	private CreateAccountForm createAccountForm;
	private SignInForm signin;
	private Account account;
	ExcelFileManager testDataReader;
	
    @BeforeClass
    public void beforeClass1() {
		 System.setProperty("testDataFilePath", "src/test/resources/TestDataFiles/testSuite01/TestData2.xlsx");
	     testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
	     
    }
	
	@BeforeMethod
	public void setup() {
		driver = BrowserFactory.getBrowser();
		createAccountForm = new CreateAccountForm(driver);
		signin = new SignInForm(driver);
		account = new Account(driver);
		String baseUrl = testDataReader.getCellData("BaseURL", "Data2");
		driver.manage().window().maximize();
		driver.get(baseUrl);
	}

	@AfterMethod
	public void closeAll() {
		 BrowserActions.closeCurrentWindow(driver);
	}

	@Test(priority = 1)
	public void loginPage() {
		Assert.assertTrue(signin.getSignInForm().isDisplayed());
		Assert.assertTrue(signin.getSignInEmailField().isDisplayed());
		Assert.assertTrue(signin.getSignInPasswordField().isDisplayed());
		Assert.assertTrue(signin.getSignInBtn().isEnabled());
	}

	@Test(priority = 2)
	public void invalidCredentials() {
		// username: heshamabdelhamed@gmail.com
		// password: Password

		signin.setEmailField(testDataReader.getCellData("WrongEmail", "Data1"));
		signin.setPasswordField(testDataReader.getCellData("WrongPassword", "Data1"));
		signin.getSignInBtn().click();

		Assert.assertTrue(signin.getAuthenticationFailedError().isDisplayed());

		signin.setEmailField(testDataReader.getCellData("CorrectEmail", "Data1"));
		signin.setPasswordField(testDataReader.getCellData("WrongPassword", "Data1"));
		signin.getSignInBtn().click();

		Assert.assertTrue(signin.getAuthenticationFailedError().isDisplayed());

		signin.setEmailField(testDataReader.getCellData("WrongEmail", "Data1"));
		signin.setPasswordField(testDataReader.getCellData("CorrectPassword", "Data1"));
		signin.getSignInBtn().click();

		Assert.assertTrue(signin.getAuthenticationFailedError().isDisplayed());

	}

	@Test(priority = 3)
	public void loginWithoutCredentials() {
		signin.setEmailField("");
		signin.setPasswordField(testDataReader.getCellData("CorrectPassword", "Data1"));
		signin.getSignInBtn().click();

		Assert.assertTrue(signin.getEmailRequiredError().isDisplayed());

		signin.setEmailField(testDataReader.getCellData("CorrectEmail", "Data1"));
		signin.setPasswordField("");
		signin.getSignInBtn().click();

		Assert.assertTrue(signin.getPasswordRequiredError().isDisplayed());

		signin.setEmailField("");
		signin.setPasswordField("");
		signin.getSignInBtn().click();

		Assert.assertTrue(signin.getEmailRequiredError().isDisplayed());
	}

	@Test(priority = 4)
	public void emailFormat() {
		signin.setEmailField(testDataReader.getCellData("Invalid Email", "Data1"));
		signin.getSignInPasswordField().click();

		Assert.assertTrue(signin.getEmailHighlightedRed().isDisplayed());

		signin.setEmailField(testDataReader.getCellData("Invalid Email", "Data2"));
		signin.getSignInPasswordField().click();

		Assert.assertTrue(signin.getEmailHighlightedRed().isDisplayed());

		signin.setEmailField(testDataReader.getCellData("Invalid Email", "Data3"));
		signin.getSignInPasswordField().click();

		Assert.assertTrue(signin.getEmailHighlightedGreen().isDisplayed());
	}

	@Test(priority = 5)
	public void successfulLogin() {
		signin.setEmailField(testDataReader.getCellData("CorrectEmail", "Data1"));
		signin.setPasswordField(testDataReader.getCellData("CorrectPassword", "Data1"));
		signin.getSignInBtn().click();

		Assert.assertTrue(createAccountForm.successfullyCreatedAccount().isDisplayed());
		
		account.getAccountLogout().click();
	}

}
