package com.udacity.jwdnd.course1.cloudstorage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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

}
