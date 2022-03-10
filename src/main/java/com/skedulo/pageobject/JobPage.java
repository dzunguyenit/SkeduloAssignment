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

    @FindBy(xpath = "//span[text()='Job Status']/../following-sibling::div//lightning-formatted-text[text()='Dispatched']")
    WebElement lbJobStatusDispatched;

    @FindBy(xpath = "//span[text()='Status']/../following-sibling::div//lightning-formatted-text[text()='Dispatched']")
    WebElement lbJobStatusDispatchedDetailScreen;

    @FindBy(xpath = "//span[@title='Job Allocations']/following-sibling::span[@title='(1)']")
    WebElement lbQuantityRecord;

    @FindBy(css = "a[title*=JA-]")
    WebElement lbJobNameOnRelatedTab;

    @FindBy(xpath = "//span[text()='Job Allocation Name']/../following-sibling::div")
    WebElement lbJobNameOnDetailTab;

    public void clickLatestJob() {
        waitVisible(lbLatestJob);
        click(lbLatestJob);
        sleep(1);
        refreshPage();
        sleep(3);
    }

    public void clickJobNameRecord() {
        waitVisible(lbJobNameOnRelatedTab);
        clickByJavascript(lbJobNameOnRelatedTab);
    }

    public String getJobNameOnRelatedTab() {
        String text = "";
        waitVisible(lbJobNameOnRelatedTab);
        text = getText(lbJobNameOnRelatedTab);
        return text;
    }

    public String getJobNameOnDetailTab() {
        waitVisible(lbJobNameOnDetailTab);
        return getText(lbJobNameOnDetailTab);
    }

    public void clickRelatedTab() {
        scrollToElement(lbTabRelated);
        waitVisible(lbTabRelated);
        clickByJavascript(lbTabRelated);
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
        String status = "";
        try {
            sleep(3);
            refreshPage();
            sleep(3);
            scrollToElement(lbJobStatusDispatched);
            waitVisible(lbJobStatusDispatched);
            status = getText(lbJobStatusDispatched);
        } catch (Exception e) {
        }
        return status;
    }

    public String getJobStatusDispatchedDetail() {
        String status = "";
        try {
            scrollToElement(lbJobStatusDispatchedDetailScreen);
            waitVisible(lbJobStatusDispatchedDetailScreen);
            status = getText(lbJobStatusDispatchedDetailScreen);
        } catch (Exception e) {
        }
        return status;
    }

    public boolean isDisplayedOneRecord() {
        waitVisible(lbQuantityRecord);
        return isDisplayed(lbQuantityRecord);
    }

    public void clickOnJobAllocationRecord() {
        waitVisible(lbQuantityRecord);
        click(lbQuantityRecord);
    }


}
