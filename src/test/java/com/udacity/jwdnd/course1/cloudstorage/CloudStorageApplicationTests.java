package com.udacity.jwdnd.course1.cloudstorage;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.github.bonigarcia.wdm.WebDriverManager;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests
{
    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll()
    {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach()
    {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach()
    {
        if (this.driver != null)
        {
            driver.quit();
        }
    }

    @Test
    void getLoginPage()
    {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    void unauthorizedAccessTest()
    {
        driver.get("http://localhost:" + this.port + "/home.html");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    void userJourneyHappyFlowTest()
    {
        signUpAndLogIn("merlin1");

        Assertions.assertEquals("Home", driver.getTitle());

        driver.findElement(By.id("logoutButton")).click();
        Assertions.assertEquals("Login", driver.getTitle());

        unauthorizedAccessTest();
    }

    @Test
    void addNoteTest()
    {
        signUpAndLogIn("merlin2");

        Home home = new Home(driver);
        home.addNote("Check List", "ToDo items list");

        Assertions.assertEquals("Result", driver.getTitle());
        Assertions.assertEquals("Note created successfully.", driver.findElement(By.id("result")).getText());

        home.viewNote();

        Assertions.assertTrue(driver.getPageSource().contains("Check List"));
    }

    @Test
    void editNoteTest()
    {
        signUpAndLogIn("merlin3");

        Home home = new Home(driver);
        home.editNote("Check List", "ToDo items list", "TODO List");

        Assertions.assertEquals("Result", driver.getTitle());
        Assertions.assertEquals("Note updated successfully.", driver.findElement(By.id("result")).getText());

        home.viewNote();

        Assertions.assertTrue(driver.getPageSource().contains("TODO List"));
    }

    @Test
    void deleteNoteTest()
    {
        signUpAndLogIn("merlin4");

        Home home = new Home(driver);
        home.deleteNote("Check List", "ToDo items list");

        Assertions.assertEquals("Result", driver.getTitle());
        Assertions.assertEquals("Note deleted successfully.", driver.findElement(By.id("result")).getText());

        home.viewNote();

        Assertions.assertFalse(driver.getPageSource().contains("Check List"));
    }

    @Test
    void addCredentialTest()
    {
        signUpAndLogIn("merlin5");

        Home home = new Home(driver);
        home.addCredential("http://localhost:8080/login", "merlin123", "test@123");

        Assertions.assertEquals("Result", driver.getTitle());
        Assertions.assertEquals("Credential created successfully.", driver.findElement(By.id("result")).getText());

        home.viewCredential();

        Assertions.assertTrue(driver.getPageSource().contains("merlin123"));
    }

    @Test
    void editCredentialTest()
    {
        signUpAndLogIn("merlin6");

        Home home = new Home(driver);
        home.editCredential("http://localhost:8080/login", "merlin123", "test@123", "merlin1234");

        Assertions.assertEquals("Result", driver.getTitle());
        Assertions.assertEquals("Credential updated successfully.", driver.findElement(By.id("result")).getText());

        home.viewCredential();

        Assertions.assertTrue(driver.getPageSource().contains("merlin1234"));
    }

    @Test
    void deleteCredentialTest()
    {
        signUpAndLogIn("merlin7");

        Home home = new Home(driver);
        home.deleteCredential("http://localhost:8080/login", "merlin123", "test@123");

        Assertions.assertEquals("Result", driver.getTitle());
        Assertions.assertEquals("Credential deleted successfully.", driver.findElement(By.id("result")).getText());

        home.viewCredential();

        Assertions.assertFalse(driver.getPageSource().contains("merlin123"));
    }

    private void signUpAndLogIn(String username)
    {
        driver.get("http://localhost:" + this.port + "/signup");

        SignUp signUp = new SignUp(driver);
        signUp.submit("Merlin", "Chacko", username, "test@123");

        LogIn logIn = new LogIn(driver);
        logIn.submit(username, "test@123");
    }

    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doMockSignUp(String firstName, String userName){
        // Create a dummy account for logging in later.

        // Visit the sign-up page.
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        driver.get("http://localhost:" + this.port + "/signup");
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

        // Fill out credentials
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
        WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
        inputFirstName.click();
        inputFirstName.sendKeys(firstName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
        WebElement inputLastName = driver.findElement(By.id("inputLastName"));
        inputLastName.click();
        inputLastName.sendKeys("Test");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        inputUsername.click();
        inputUsername.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        inputPassword.click();
        inputPassword.sendKeys("123");

        // Attempt to sign up.
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submitButton")));
        WebElement buttonSignUp = driver.findElement(By.id("submitButton"));
        buttonSignUp.click();

		/* Check that the sign up was successful.
		// You may have to modify the element "success-msg" and the sign-up
		// success message below depening on the rest of your code.
		*/
       Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
    }



    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doLogIn(String userName)
    {
        // Log in to our dummy account.
        driver.get("http://localhost:" + this.port + "/login");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement loginUserName = driver.findElement(By.id("inputUsername"));
        loginUserName.click();
        loginUserName.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement loginPassword = driver.findElement(By.id("inputPassword"));
        loginPassword.click();
        loginPassword.sendKeys("123");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submitButton")));
        WebElement loginButton = driver.findElement(By.id("submitButton"));
        loginButton.click();

        webDriverWait.until(ExpectedConditions.titleContains("Home"));

    }

    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     *
     * If this test is failing, please ensure that you are handling redirecting users
     * back to the login page after a succesful sign up.
     * Read more about the requirement in the rubric:
     * https://review.udacity.com/#!/rubrics/2724/view
     */
    @Test
    void testRedirection() {
        // Create a test account
        doMockSignUp("Redirection", "RT");

        // Check if we have been redirected to the log in page.
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
    }

    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     *
     * If this test is failing, please ensure that you are handling bad URLs
     * gracefully, for example with a custom error page.
     *
     * Read more about custom error pages at:
     * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
     */
    @Test
    void testBadUrl() {
        // Create a test account
        doMockSignUp("URL", "UT");
        doLogIn("UT");

        // Try to access a random made-up URL.
        driver.get("http://localhost:" + this.port + "/some-random-page");
        Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
    }


    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     *
     * If this test is failing, please ensure that you are handling uploading large files (>1MB),
     * gracefully in your code.
     *
     * Read more about file size limits here:
     * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
     */
    @Test
    void testLargeUpload() {
        // Create a test account
        doMockSignUp("Large File", "LFT");
        doLogIn("LFT");

        // Try to upload an arbitrary large file
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        String fileName = "upload5m.zip";

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFile")));
        WebElement fileSelectButton = driver.findElement(By.id("inputFile"));
        fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

        WebElement uploadButton = driver.findElement(By.id("submitButton"));
        uploadButton.click();
        try {
            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("result")));
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("Large File upload failed");
        }
        Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

    }


}
