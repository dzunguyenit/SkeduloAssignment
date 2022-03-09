package com.skedulo.pageobject;

import common.BaseElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class JobPage extends BaseElement {

    public JobPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//span[@title='Jenny John Dow']")
    WebElement lbResultName;

    @FindBy(xpath = "(//a[@data-refid='recordId' and contains(text(),'JOB-')])[1]")
    WebElement lbLatestJob;

    @FindBy(xpath = "(//a[@data-refid='recordId' and contains(text(),'JOB-')])[1]")
    WebElement lbTabRelated;
    @FindBy(xpath = "//span[text()='Finish']/../following-sibling::div")
    WebElement lbFinishDay;

    @FindBy(xpath = "//span[text()='Start']/../following-sibling::div")
    WebElement lbStartDay;

    @FindBy(xpath = "//span[text()='Job Status']/../following-sibling::div")
    WebElement lbJobStatus;

    public void searchContactName(String contactName) {
        sleep(3);
        waitVisible(lbResultName);
        click(lbResultName);
    }

    public void clickLatestJob() {
        waitVisible(lbLatestJob);
        click(lbLatestJob);
    }

    public void clickRelatedTab() {
        waitVisible(lbTabRelated);
        click(lbTabRelated);
    }

    public String getStartDay() {
        waitVisible(lbStartDay);
        return getText(lbStartDay);
    }

    public String getFinishDay() {
        waitVisible(lbFinishDay);
        return getText(lbFinishDay);
    }

    public String getJobStatus() {
        waitVisible(lbJobStatus);
        return getText(lbJobStatus);
    }


}
