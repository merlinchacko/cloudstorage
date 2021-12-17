package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class SignUp
{
    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    @FindBy(id = "inputUsername")
    private WebElement inputUserName;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "submitButton")
    private WebElement submitButton;

    public SignUp(WebDriver webDriver)
    {
        PageFactory.initElements(webDriver, this);
    }

    public void submit(String firstName, String lastName, String username, String password)
    {
        inputFirstName.sendKeys(firstName);
        inputLastName.sendKeys(lastName);
        inputUserName.sendKeys(username);
        inputPassword.sendKeys(password);
        submitButton.click();
    }

}
