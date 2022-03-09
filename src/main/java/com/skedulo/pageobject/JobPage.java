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

    @FindBy(xpath = "(//a[@data-refid='recordId' and contains(text(),'JOB-')])[1]")
    WebElement lbTabRelated;
    @FindBy(xpath = "//span[text()='Finish']/../following-sibling::div")
    WebElement lbFinishDay;

    @FindBy(xpath = "//span[text()='Start']/../following-sibling::div")
    WebElement lbStartDay;

    @FindBy(xpath = "//span[text()='Job Status']/../following-sibling::div")
    WebElement lbJobStatus;

    @FindBy(xpath = "//flexipage-tab2[@id='tab-48']//span[@title='Job Allocations']")
    WebElement lbTitleJobApplication;

    @FindBy(xpath = "//flexipage-tab2[@id='tab-48']//span[@title='Job Allocations']/following-sibling::span")
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

    public String getTitleJobApplication() {
        waitVisible(lbTitleJobApplication);
        return getText(lbTitleJobApplication);
    }

    public String getQuantityRecord() {
        waitVisible(lbQuantityRecord);
        return getText(lbQuantityRecord);
    }


}
