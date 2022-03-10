package com.skedulo.pageobject;

import common.BaseElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BaseElement {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//span[@title='Jenny John Dow']")
    WebElement lbResultName;

    @FindBy(xpath = "//input[@title='Search...']")
    WebElement txtContactName;

    @FindBy(xpath = "//span[text()='Home']")
    WebElement lbHome;

    @FindBy(xpath = "//span[text()='Jobs']")
    WebElement lbJobs;

    public ContactPage searchContactName(String contactName) {
        waitVisible(txtContactName);
        input(txtContactName, contactName);
        sleep(3);
        waitVisible(lbResultName);
        click(lbResultName);
        return PageFactory.initElements(driver, ContactPage.class);
    }

    public boolean isDisplayedHomePage() {
        waitVisible(lbHome);
        return isDisplayed(lbHome);
    }

    public JobPage openJobsPage() {
        waitVisible(lbJobs);
        clickByJavascript(lbJobs);
        return PageFactory.initElements(driver, JobPage.class);
    }

}
