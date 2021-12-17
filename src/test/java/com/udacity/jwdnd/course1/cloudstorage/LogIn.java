package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class LogIn
{
    @FindBy(id = "inputUsername")
    private WebElement inputUserName;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "submitButton")
    private WebElement submitButton;

    public LogIn(WebDriver webDriver)
    {
        PageFactory.initElements(webDriver, this);
    }

    public void submit(String username, String password) {
        inputUserName.sendKeys(username);
        inputPassword.sendKeys(password);
        submitButton.click();
    }
}
