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
import automationpractice.com.pageObject.CreateAccount;
import automationpractice.com.pageObject.CreateAccountForm;
import automationpractice.com.pageObject.Homepage;
import automationpractice.com.pageObject.SignInForm;
import utils.EmailsGenerator;
import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateAccountFormTest {

	private WebDriver driver;
	ExcelFileManager testDataReader;
	private Homepage homepage;
	private CreateAccount createAccount;
	private CreateAccountForm createAccountForm;
	private SignInForm signin;
	private Account account;
 
	
    @BeforeClass
    public void beforeClass1() {
		 System.setProperty("testDataFilePath", "src/test/resources/TestDataFiles/testSuite01/TestData2.xlsx");
	     testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));
	     
    }

	@BeforeMethod
	public void setup() {
		driver = BrowserFactory.getBrowser();
		homepage = new Homepage(driver);
		createAccount = new CreateAccount(driver);
		createAccountForm = new CreateAccountForm(driver);
		signin = new SignInForm(driver);
		account = new Account(driver);
		String baseUrl = testDataReader.getCellData("BaseURL", "Data1");
		driver.manage().window().maximize();
		driver.get(baseUrl);
	}

	@AfterMethod
	public void closeAll() {
		BrowserActions.closeCurrentWindow(driver);
	}

	@Test(priority = 1)
	public void authenticationPage() {
		homepage.getSignInBtn().click();

		Assert.assertTrue(createAccount.getCreateAccountForm().isDisplayed());
		Assert.assertTrue(createAccount.getCreatAccountEmailField().isDisplayed());
		Assert.assertTrue(createAccount.getCreateAccountBtn().isDisplayed());
		Assert.assertTrue(signin.getSignInForm().isDisplayed());
	}

	@Test(priority = 2)
	public void authenticationPageEmailField() {
		homepage.getSignInBtn().click();

		// Without email
		createAccount.getCreateAccountBtn().click();

		Assert.assertTrue(createAccount.getEmailErrorMessage().isDisplayed());

		// Wrong email format (hesham, hesham@gmail ...)
		createAccount.setCreateAccountEmailField(testDataReader.getCellData("Invalid Email", "Data1"));
		createAccount.getCreateAccountBtn().click();

		Assert.assertTrue(createAccount.getEmailErrorMessage().isDisplayed());
		Assert.assertTrue(createAccount.getEmailFieldHighlightedRed().isDisplayed());

		// Registered email
		createAccount.setCreateAccountEmailField(testDataReader.getCellData("Registered Email", "Data1"));
		createAccount.getCreateAccountBtn().click();

		Assert.assertTrue(createAccount.getEmailBeenRegistered().isDisplayed());

		// Correct email
		createAccount.setCreateAccountEmailField(testDataReader.getCellData("New Email", "Data1"));
		createAccount.getCreateAccountBtn().click();

		Assert.assertTrue(createAccountForm.getAccountCreationForm().isDisplayed());
	}

	@Test(priority = 3)
	public void personalInfoFields() {
		homepage.getSignInBtn().click();

		// Correct email
		createAccount.setCreateAccountEmailField(testDataReader.getCellData("New Email", "Data1"));
		createAccount.getCreateAccountBtn().click();

		Assert.assertTrue(createAccountForm.getAccountCreationForm().isDisplayed());
		// With values
		createAccountForm.setCustomerFirstNameField(testDataReader.getCellData("First Name", "Data1"));
		createAccountForm.setCustomerLastNameField(testDataReader.getCellData("Last Name", "Data1"));
		createAccountForm.setCustomerEmailField(testDataReader.getCellData("New Email", "Data1"));
		createAccountForm.setCustomerPasswordField(testDataReader.getCellData("Password", "Data1"));

		createAccountForm.getAccountCreationForm().click();

		Assert.assertTrue(createAccountForm.getFirstNameHighlightedGreen().isDisplayed());
		Assert.assertTrue(createAccountForm.getLastNameHighlightedGreen().isDisplayed());
		Assert.assertTrue(createAccountForm.getEmailHighlightedGreen().isDisplayed());
		Assert.assertTrue(createAccountForm.getPasswordHighlightedGreen().isDisplayed());

		// Without values
		createAccountForm.setCustomerFirstNameField("");
		createAccountForm.setCustomerLastNameField("");
		createAccountForm.setCustomerEmailField("");
		createAccountForm.setCustomerPasswordField("");

		createAccountForm.getAccountCreationForm().click();

		Assert.assertTrue(createAccountForm.getFirstNameHighlightedRed().isDisplayed());
		Assert.assertTrue(createAccountForm.getLastNameHighlightedRed().isDisplayed());
		Assert.assertTrue(createAccountForm.getEmailHighlightedRed().isDisplayed());
		Assert.assertTrue(createAccountForm.getPasswordHighlightedRed().isDisplayed());
	}

	@Test(priority = 4)
	public void requiredFieldsEmpty() {
		homepage.getSignInBtn().click();

		// Correct email
		createAccount.setCreateAccountEmailField(testDataReader.getCellData("New Email", "Data1"));
		createAccount.getCreateAccountBtn().click();

		Assert.assertTrue(createAccountForm.getAccountCreationForm().isDisplayed());
		
		createAccountForm.getAddressAliasField().clear();
		createAccountForm.setCustomerEmailField("");
		createAccountForm.selectCountry("-");
		createAccountForm.getRegisterBtn().click();

		Assert.assertTrue(createAccountForm.getPhoneNumberError().isDisplayed());
		Assert.assertTrue(createAccountForm.getLastNameError().isDisplayed());
		Assert.assertTrue(createAccountForm.getFirstNameError().isDisplayed());
		Assert.assertTrue(createAccountForm.getEmailRequiredError().isDisplayed());
		Assert.assertTrue(createAccountForm.getPasswordRequiredError().isDisplayed());
		Assert.assertTrue(createAccountForm.getCountryRequiredError().isDisplayed());
		Assert.assertTrue(createAccountForm.getAddressRequiredError().isDisplayed());
		Assert.assertTrue(createAccountForm.getAddressAliasRequiredError().isDisplayed());
		Assert.assertTrue(createAccountForm.getCityRequiredError().isDisplayed());
		Assert.assertTrue(createAccountForm.getCountryUnselectedError().isDisplayed());

		createAccountForm.selectCountry(testDataReader.getCellData("Country", "Data1"));
		createAccountForm.getRegisterBtn().click();

		Assert.assertTrue(createAccountForm.getStateRequredError().isDisplayed());
		Assert.assertTrue(createAccountForm.getPostalCodeError().isDisplayed());
	}

	@Test(priority = 5)
	public void requiredFieldsInputFormat() throws Exception {
		homepage.getSignInBtn().click();

		// Correct email
		createAccount.setCreateAccountEmailField(testDataReader.getCellData("New Email", "Data1"));
		createAccount.getCreateAccountBtn().click();

		Assert.assertTrue(createAccountForm.getAccountCreationForm().isDisplayed());
		// Wrong format
		createAccountForm.setCustomerEmailField(testDataReader.getCellData("New Email", "Data2"));
		createAccountForm.setCustomerPasswordField(testDataReader.getCellData("WrongPasswordFormat", "Data1"));
		createAccountForm.setPostalCodeField(testDataReader.getCellData("PostalCode", "Data1"));
		createAccountForm.setHomePhoneField(testDataReader.getCellData("HomePhone", "Data1"));
		createAccountForm.setMobilePhoneField(testDataReader.getCellData("MobilePhone", "Data1"));

		createAccountForm.getRegisterBtn().click();

		Assert.assertTrue(createAccountForm.getEmailInvalidError().isDisplayed());
		Assert.assertTrue(createAccountForm.getPasswordInvalidError().isDisplayed());
		Assert.assertTrue(createAccountForm.getPostalCodeError().isDisplayed());
		Assert.assertTrue(createAccountForm.getHomePhoneInvalidError().isDisplayed());
		Assert.assertTrue(createAccountForm.getMobilePhoneInvalidError().isDisplayed());

		// Correct format
		createAccountForm.setCustomerEmailField(testDataReader.getCellData("New Email", "Data1"));
		createAccountForm.setCustomerPasswordField(testDataReader.getCellData("Password", "Data1"));
		createAccountForm.setPostalCodeField(testDataReader.getCellData("PostalCode", "Data2"));
		createAccountForm.setHomePhoneField(testDataReader.getCellData("HomePhone", "Data1"));
		createAccountForm.setMobilePhoneField(testDataReader.getCellData("MobilePhone", "Data1"));

		Assert.assertTrue(createAccountForm.getEmailInvalidError().isDisplayed());
		Assert.assertTrue(createAccountForm.getPasswordInvalidError().isDisplayed());
		Assert.assertTrue(createAccountForm.getPostalCodeError().isDisplayed());
		Assert.assertTrue(createAccountForm.getHomePhoneInvalidError().isDisplayed());
		Assert.assertTrue(createAccountForm.getMobilePhoneInvalidError().isDisplayed());
	}

	@Test(priority = 6)
	public void createAccountSuccessfully() {
		homepage.getSignInBtn().click();

		// Correct email
		//createAccount.setCreateAccountEmailField(testDataReader.getCellData("New Email", "Data1"));
		createAccount.setCreateAccountEmailField(EmailsGenerator.getNextEmail());
		createAccount.getCreateAccountBtn().click();

		Assert.assertTrue(createAccountForm.getAccountCreationForm().isDisplayed());
		
		// Required fields filled
		createAccountForm.setCustomerFirstNameField(testDataReader.getCellData("First Name", "Data1"));
		createAccountForm.setCustomerLastNameField(testDataReader.getCellData("Last Name", "Data1"));
		createAccountForm.setCustomerPasswordField(testDataReader.getCellData("Password", "Data1"));
		createAccountForm.selectCustomerDateOfBirthDay(testDataReader.getCellData("DateOfBirthDay", "Data1"));
		createAccountForm.selectCustomerDateOfBirthMonth(testDataReader.getCellData("DateOfBirthMonth", "Data1"));
		createAccountForm.selectCustomerDateOfBirthYear(testDataReader.getCellData("DateOfBirthYear", "Data1"));
		createAccountForm.setAddressField(testDataReader.getCellData("Address", "Data1"));
		createAccountForm.setCityField(testDataReader.getCellData("City", "Data1"));
		createAccountForm.selectState(testDataReader.getCellData("State", "Data1"));
		createAccountForm.setPostalCodeField(testDataReader.getCellData("PostalCode", "Data2"));
		createAccountForm.setHomePhoneField(testDataReader.getCellData("HomePhone", "Data2"));
		createAccountForm.setMobilePhoneField(testDataReader.getCellData("MobilePhone", "Data2"));
		createAccountForm.setAddressAliasField(testDataReader.getCellData("AddressAlias", "Data1"));
		createAccountForm.getRegisterBtn().click();

		Assert.assertTrue(createAccountForm.getEmailBeenRegistered().isDisplayed());

		//createAccountForm.setCustomerEmailField(testDataReader.getCellData("New Email", "Data1"));
		createAccountForm.setCustomerEmailField(EmailsGenerator.getCurrentEmail());
		createAccountForm.setCustomerPasswordField(testDataReader.getCellData("Password", "Data1"));
		createAccountForm.getRegisterBtn().click();

		Assert.assertTrue(createAccountForm.successfullyCreatedAccount().isDisplayed());
		
		account.getAccountLogout().click();
	}
}
