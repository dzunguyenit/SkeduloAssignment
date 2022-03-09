package com.skedulo.pageobject;

import common.BaseElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class JobPage extends BaseElement {

    public JobPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//span[@title='Jenny John Dow']")
    WebElement lbResultName;

    @FindBy(xpath = "(//a[@data-refid='recordId' and contains(text(),'JOB-')])[1]")
    WebElement lbLatestJob;

    @FindBy(xpath = "//a[text()='Related']")
    WebElement lbTabRelated;

    @FindBy(xpath = "//span[text()='Finish']/../following-sibling::div//lightning-formatted-text")
    WebElement lbFinishDay;

    @FindBy(xpath = "//span[text()='Start']/../following-sibling::div//lightning-formatted-text")
    WebElement lbStartDay;

    @FindBy(xpath = "//span[text()='Job Status']/../following-sibling::div//lightning-formatted-text")
    WebElement lbJobStatus;

    @FindBy(xpath = "//span[@title='Job Allocations']/following-sibling::span[@title='(1)']")
    WebElement lbQuantityRecord;

    @FindBy(css = "span#window")
    WebElement lbJobNameOnRelatedTab;

    @FindBy(xpath = "//span[text()='Job Allocation Name']/../following-sibling::div")
    WebElement lbJobNameOnDetailTab;

    public void clickLatestJob() {
        waitVisible(lbLatestJob);
        click(lbLatestJob);
    }

    public void clickJobNameRecord() {
        waitVisible(lbJobNameOnRelatedTab);
        click(lbJobNameOnRelatedTab);
    }

    public String getJobNameOnRelatedTab() {
        waitVisible(lbJobNameOnRelatedTab);
        return getText(lbJobNameOnRelatedTab);
    }

    public String getJobNameOnDetailTab() {
        waitVisible(lbJobNameOnDetailTab);
        return getText(lbJobNameOnDetailTab);
    }

    public void clickRelatedTab() {
        scrollToElement(lbStartDay);
        waitVisible(lbTabRelated);
        click(lbTabRelated);
    }

    public String getStartDay() {
        waitVisible(lbStartDay);
        scrollToElement(lbStartDay);
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

    public boolean isDisplayedOneRecord() {
        waitVisible(lbQuantityRecord);
        return isDisplayed(lbQuantityRecord);
    }


}
