package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private static Integer sleepTime = 1500;
	private static String firstName = "firstname";
	private static String lastName = "lastname";
	private static String userName = "testuser";
	private static String password = "password";
	private static WebDriver driver;

	@BeforeAll
	static void beforeAll()
	{
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	public void getHomePage() {
		driver.get("http://localhost:" + this.port + "/home");
	}

	public void getSignUpPage() {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	public void login() throws InterruptedException {
		Thread.sleep(sleepTime);
		WebElement uName = driver.findElement(By.id("inputUsername"));
		uName.sendKeys(userName);
		WebElement pass = driver.findElement(By.id("inputPassword"));
		pass.sendKeys(password);
		pass.submit();
		Thread.sleep(sleepTime);
	}

	public void logout() throws InterruptedException {
		Thread.sleep(sleepTime);
		WebElement logoutButton = driver.findElement(By.id("logout-button"));
		logoutButton.click();
		Thread.sleep(sleepTime);
	}

	@Order(1)
	@Test
	public void verifyHomePageWithoutLogin()
	{
		getHomePage();
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(2)
	public void testLoginFlow() throws InterruptedException {
		getSignUpPage();
		WebElement fName = driver.findElement(By.id("inputFirstName"));
		fName.sendKeys(firstName);
		WebElement lName = driver.findElement(By.id("inputLastName"));
		lName.sendKeys(lastName);
		WebElement uName = driver.findElement(By.id("inputUsername"));
		uName.sendKeys(userName);
		WebElement pass = driver.findElement(By.id("inputPassword"));
		pass.sendKeys(password);
		pass.submit();
		Thread.sleep(sleepTime);
		Assertions.assertEquals("Login", driver.getTitle());
		login();
		Assertions.assertEquals("Home", driver.getTitle());
		logout();
		Assertions.assertEquals("Login", driver.getTitle());
		getHomePage();
		Thread.sleep(sleepTime);
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Order(3)
	@Test
	public void addNotes() throws InterruptedException {
		login();
		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		notesTab.click();
		int i;
		for( i =1 ; i<=3 ; i++)
		{
			Thread.sleep(sleepTime);
			WebElement addNewNote = driver.findElement(By.id("addNewNote"));
			WebElement title = driver.findElement(By.id("note-title"));
			WebElement desc = driver.findElement(By.id("note-description"));
			addNewNote.click();
			Thread.sleep(sleepTime);
			title.sendKeys("Test "+i);
			desc.sendKeys("Test description "+i);
			desc.submit();
		}
		List<WebElement> notesRow = driver.findElements(new By.ByClassName("notes-row"));
		Assertions.assertEquals(i-1, notesRow.size());
		logout();
	}

	@Order(4)
	@Test
	public void editNotes() throws InterruptedException {
		login();
		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		notesTab.click();
		Thread.sleep(sleepTime);
		List<WebElement> notesRow = driver.findElements(By.className("notes-row"));
		WebElement editButton = notesRow.get(0).findElement(By.tagName("button"));
		editButton.click();
		Thread.sleep(sleepTime);
		WebElement title = driver.findElement(By.id("note-title"));
		WebElement desc = driver.findElement(By.id("note-description"));
		title.clear();
		title.sendKeys("new Edited");
		desc.clear();
		desc.sendKeys("new Edited Description");
		desc.submit();
		Thread.sleep(sleepTime);
		List<WebElement> notesRowNew = driver.findElements(new By.ByClassName("notes-row"));
		String description = notesRowNew.get(0).findElement(By.className("notes-desc")).getText();
		Assertions.assertEquals("new Edited Description", description);
		logout();
	}

	@Order(6)
	@Test
	public void deleteNotes() throws InterruptedException {
		login();
		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		notesTab.click();
		Thread.sleep(sleepTime);
		List<WebElement> notesRow = driver.findElements(By.className("notes-row"));
		int rowSize = notesRow.size();
		WebElement deleteButton = notesRow.get(0).findElement(By.tagName("a"));
		deleteButton.click();
		Thread.sleep(sleepTime);
		List<WebElement> notesRowNew = driver.findElements(By.className("notes-row"));
		Assertions.assertEquals(rowSize-1, notesRowNew.size());
		logout();
	}

	@Order(7)
	@Test
	public void addCredentials() throws InterruptedException {
		login();
		WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		credentialsTab.click();
		int i;
		for( i =1 ; i<=3 ; i++)
		{
			Thread.sleep(sleepTime);
			WebElement addNewCredential = driver.findElement(By.id("addNewCredential"));
			WebElement url = driver.findElement(By.id("credential-url"));
			WebElement username = driver.findElement(By.id("credential-username"));
			WebElement password = driver.findElement(By.id("credential-password"));
			addNewCredential.click();
			Thread.sleep(sleepTime);
			url.sendKeys("www.url"+i+".com");
			username.sendKeys("Test User "+i);
			password.sendKeys("password"+i);
			password.submit();
		}
		List<WebElement> credentialsRow = driver.findElements(new By.ByClassName("credentials-row"));
		Assertions.assertEquals(i-1, credentialsRow.size());
		logout();
	}

	@Order(8)
	@Test
	public void editCredentials() throws InterruptedException {
		login();
		WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		credentialsTab.click();
		Thread.sleep(sleepTime);
		List<WebElement> credentialsRow = driver.findElements(new By.ByClassName("credentials-row"));
		WebElement editButton = credentialsRow.get(0).findElement(By.tagName("button"));
		editButton.click();
		Thread.sleep(sleepTime);
		WebElement url = driver.findElement(By.id("credential-url"));
		WebElement username = driver.findElement(By.id("credential-username"));
		WebElement password = driver.findElement(By.id("credential-password"));
		url.clear();
		url.sendKeys("www.editedurl.com");
		username.clear();
		username.sendKeys("edited user");
		password.clear();
		password.sendKeys("edited password");
		password.submit();
		Thread.sleep(sleepTime);
		List<WebElement> notesRowNew = driver.findElements(new By.ByClassName("credentials-row"));
		String uname = notesRowNew.get(0).findElement(By.className("credential-uname")).getText();
		Assertions.assertEquals("edited user", uname);
		logout();
	}

	@Order(9)
	@Test
	public void deleteCredentials() throws InterruptedException {
		login();
		WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		credentialsTab.click();
		Thread.sleep(sleepTime);
		List<WebElement> credentialsRow = driver.findElements(new By.ByClassName("credentials-row"));
		WebElement deleteButton = credentialsRow.get(0).findElement(By.tagName("a"));
		deleteButton.click();
		Thread.sleep(sleepTime);
		List<WebElement> newCredentialsRow = driver.findElements(new By.ByClassName("credentials-row"));
		Assertions.assertEquals(credentialsRow.size()-1, newCredentialsRow.size());
		logout();
	}

}
