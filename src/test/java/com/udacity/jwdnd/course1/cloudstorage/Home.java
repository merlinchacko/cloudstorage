package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class Home
{
    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "noteTitle")
    private WebElement noteTitle;

    @FindBy(id = "noteDescription")
    private WebElement noteDescription;

    @FindBy(id = "addNote")
    private WebElement addNote;

    @FindBy(id = "submitNote")
    private WebElement submitNote;

    @FindBy(id = "editNote")
    private WebElement editNote;

    @FindBy(id = "deleteNote")
    private WebElement deleteNote;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "credentialUrl")
    private WebElement credentialUrl;

    @FindBy(id = "credentialUsername")
    private WebElement credentialUsername;

    @FindBy(id = "credentialPassword")
    private WebElement credentialPassword;

    @FindBy(id = "addCredential")
    private WebElement addCredential;

    @FindBy(id = "submitCredential")
    private WebElement submitCredential;

    @FindBy(id = "editCredential")
    private WebElement editCredential;

    @FindBy(id = "deleteCredential")
    private WebElement deleteCredential;

    @FindBy(id = "home")
    private WebElement home;

    private WebDriverWait wait;

    public Home(WebDriver driver) {
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait (driver, 30);
    }

    public void addNote(String title, String description) {

        wait.until(ExpectedConditions.elementToBeClickable(notesTab)).click();
        wait.until(ExpectedConditions.elementToBeClickable(addNote)).click();
        wait.until(ExpectedConditions.elementToBeClickable(noteTitle)).click();

        noteTitle.sendKeys(title);
        noteDescription.sendKeys(description);

        submitNote.click();
    }

    public void editNote(String title, String description, String newTitle) {

        addNote(title, description);
        viewNote();

        wait.until(ExpectedConditions.elementToBeClickable(editNote)).click();
        wait.until(ExpectedConditions.elementToBeClickable(noteTitle)).click();

        noteTitle.sendKeys(newTitle);
        noteDescription.sendKeys(description);

        submitNote.click();
    }

    public void viewNote()
    {
        home.click();
        wait.until(ExpectedConditions.elementToBeClickable(notesTab)).click();
    }

    public void deleteNote(String title, String description) {

        addNote(title, description);
        viewNote();

        wait.until(ExpectedConditions.elementToBeClickable(deleteNote)).click();
    }

    public void addCredential(String url, String username, String password) {

        wait.until(ExpectedConditions.elementToBeClickable(credentialsTab)).click();
        wait.until(ExpectedConditions.elementToBeClickable(addCredential)).click();
        wait.until(ExpectedConditions.elementToBeClickable(credentialUrl)).click();

        credentialUrl.sendKeys(url);
        credentialUsername.sendKeys(username);
        credentialPassword.sendKeys(password);

        submitCredential.click();
    }

    public void editCredential(String url, String username, String password, String newUsername) {

        addCredential(url, username, password);
        viewCredential();

        wait.until(ExpectedConditions.elementToBeClickable(editCredential)).click();
        wait.until(ExpectedConditions.elementToBeClickable(credentialUrl)).click();

        credentialUrl.sendKeys(url);
        credentialUsername.sendKeys(newUsername);
        credentialPassword.sendKeys(password);

        submitCredential.click();
    }

    public void viewCredential()
    {
        home.click();
        wait.until(ExpectedConditions.elementToBeClickable(credentialsTab)).click();
    }

    public void deleteCredential(String url, String username, String password) {

        addCredential(url, username, password);
        viewCredential();

        wait.until(ExpectedConditions.elementToBeClickable(deleteCredential)).click();
    }
}
