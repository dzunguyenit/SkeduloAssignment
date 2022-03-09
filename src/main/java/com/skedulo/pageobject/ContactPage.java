package com.skedulo.pageobject;

import common.BaseElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ContactPage extends BaseElement {

    public ContactPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//button[contains(.,'Schedule Job')]")
    WebElement btnScheduleJob;

    @FindBy(css = "input[name=startDate]")
    WebElement datetimeStartDay;

    @FindBy(xpath = "//option[text()='Billable']")
    WebElement optionBillable;

    @FindBy(css = "input[name=startTime]")
    WebElement txtStartTime;

    @FindBy(css = "input[name=jobDuration]")
    WebElement txtDuration;

    @FindBy(css = "select[name=jobUrgency]")
    WebElement cbxUrgency;

    @FindBy(css = "select[name=billableType]")
    WebElement cbxBillable;

    @FindBy(css = "select[name=travelArea]")
    WebElement cbxTravelArea;

    @FindBy(css = "select[name=skedhealthcareLocationType]")
    WebElement cbxServiceSetting;

    @FindBy(css = "select[name*=serviceDeliveryMethod]")
    WebElement cbxDeliveryMethod;

    @FindBy(css = "select[name*=serviceCategory]")
    WebElement cbxServiceCategory;

    @FindBy(css = "select[name*=serviceAgreementItem]")
    WebElement cbxServiceAgreementItem;

    @FindBy(xpath = "//iframe[contains(@class,'sked-modal-iframe')]")
    WebElement iFrameScheduleJob;

    @FindBy(xpath = "//textarea[@name='description']")
    WebElement txtDescription;

    @FindBy(xpath = "//span[contains(@class,'address')]/following-sibling::button[@title='Remove']")
    WebElement btnRemoveAddress;

    @FindBy(xpath = "//span[contains(@class,'address')]/ancestor::div[@class='slds-pill_container ng-hide']/following-sibling::div/input")
    WebElement txtAddress;

    @FindBy(xpath = "//div[@title='14 Victoria Avenue, Castle Hill NSW, Australia']")
    WebElement lbAddressResult;

    @FindBy(css = "input[name*=quantity]")
    WebElement txtQuantity;

    @FindBy(xpath = "//button[text()='Create Job']")
    WebElement btnCreateJob;

    @FindBy(xpath = "//span[text()='Save']/..")
    WebElement btnSave;

    @FindBy(xpath = "//button[text()='Refresh Available Resources']")
    WebElement btnRefreshResources;

    @FindBy(xpath = "//p[text()='Job has been created successfully. Select options below to go to further step.']")
    WebElement lbCreateScheduleJobSuccess;

    @FindBy(xpath = "//p[text()='Job has been updated successfully. Select options below to go to further step.']")
    WebElement lbUpdatedScheduleJobSuccess;

    @FindBy(xpath = "//p[text()='There are conflicts for the time this Job is scheduled. Do you wish to proceed?']")
    WebElement lbCreateCreateJobConflict;

    @FindBy(xpath = "//button[text()='Allocate Resources']")
    WebElement btnAllocateResources;

    @FindBy(xpath = "//button[text()='Allocate']")
    WebElement btnAllocate;

    @FindBy(xpath = "//button[text()='Update Job']")
    WebElement btnUpdateJob;

    @FindBy(xpath = "//button[text()='Go to Job Details Page']")
    WebElement btnGoToJobDetailsPage;

    @FindBy(xpath = "(//*[contains(@class,'available-resources__list')]//p[@class='ng-binding'])[1]")
    WebElement lbFirstResource;

    @FindBy(xpath = "//div[@role='status']//div[contains(@class,'sked-spinner-medium')]")
    WebElement loadingBarCreateJob;

    @FindBy(css = ".slds-spinner .sked-spinner-medium")
    WebElement loadingBarsAllocation;

    @FindBy(xpath = "//span[text()='Jobs']")
    WebElement lbJobs;

    public JobPage openJobsPage() {
        waitVisible(lbJobs);
        clickByJavascript(lbJobs);
        return PageFactory.initElements(driver, JobPage.class);
    }

    public void clickScheduleJob() {
        waitVisible(btnScheduleJob);
        clickByJavascript(btnScheduleJob);
    }

    public void chooseStartDay(String startDay) {
        waitVisible(loadingBarCreateJob, 20);
        waitInvisible(loadingBarCreateJob, 20);
        waitVisible(datetimeStartDay);
        input(datetimeStartDay, startDay);
        inputKeyBoard(datetimeStartDay, Keys.TAB);
        waitInvisible(loadingBarCreateJob, 3);
//        clickByJavascript(datetimeStartDay);
    }

    public void chooseStartTime(String startTime) {
        waitVisible(txtStartTime);
        input(txtStartTime, startTime);
        inputKeyBoard(txtStartTime, Keys.ENTER);
    }

    public void inputDuration(String duration) {
        waitVisible(txtDuration);
        input(txtDuration, duration);
    }

    public void inputDescription(String description) {
        waitVisible(txtDescription);
        input(txtDescription, description);
        clickByJavascript(txtDescription);
    }

    public void switchFrameScheduleJob() {
        switchIframe(iFrameScheduleJob);
    }

    public void switchDefault() {
        switchDefaultContent();
    }

    public void selectUrgency(String urgency) {
        selectCombobox(cbxUrgency, urgency);
    }

    public void selectBillable(String billable) {
        selectCombobox(cbxBillable, billable);
    }

    public void selectTravelArea(String travelArea) {
        selectCombobox(cbxTravelArea, travelArea);
    }

    public void selectServiceSetting(String serviceSetting) {
        selectCombobox(cbxServiceSetting, serviceSetting);
    }

    public void selectDeliveryMethod(String deliveryMethod) {
        selectCombobox(cbxDeliveryMethod, deliveryMethod);
    }

    public void selectCategory(String category) {
        selectCombobox(cbxServiceCategory, category);
    }

    public void selectServiceAgreementItem(String serviceAgreementItem) {
        selectCombobox(cbxServiceAgreementItem, serviceAgreementItem);
    }

    public void chooseAddress(String address) {
        waitVisible(btnRemoveAddress);
        clickByJavascript(btnRemoveAddress);
        waitVisible(txtAddress);
        input(txtAddress, address);
        waitVisible(lbAddressResult);
        clickByJavascript(lbAddressResult);
    }

    public void inputQuantity(String quantity) {
        waitVisible(txtQuantity);
        input(txtQuantity, quantity);
    }

    public void removeTag(String tag) {
        String locator = String.format("//span[text()='%s']/following-sibling::button", tag);
        waitVisible(locator);
        clickByJavascript(locator);
    }

    public void clickCreateJob() {
        waitInvisible(loadingBarsAllocation,5);
        waitVisible(btnCreateJob);
        clickByJavascript(btnCreateJob);
    }

    public String getTextCreateScheduleJobSuccess() {
        waitVisible(lbCreateScheduleJobSuccess);
        return getText(lbCreateScheduleJobSuccess);
    }
    public String getTextUpdatedScheduleJobSuccess() {
        waitVisible(lbUpdatedScheduleJobSuccess);
        return getText(lbUpdatedScheduleJobSuccess);
    }

    public void clickAllocateResources() {
        waitVisible(btnAllocateResources);
        clickByJavascript(btnAllocateResources);
        waitInvisible(loadingBarsAllocation, 10);
    }

    public void clickAllocate() {
        waitVisible(btnAllocate);
        clickByJavascript(btnAllocate);
    }

    public void clickSave() {
        waitVisible(btnSave);
        clickByJavascript(btnSave);
    }

    public void clickOnFirstResource() {
        waitVisible(lbFirstResource);
        clickByJavascript(lbFirstResource);
    }

    public void clickUpdateJob() {
        waitVisible(btnUpdateJob);
        clickByJavascript(btnUpdateJob);
    }

    public void clickGoToJobDetailsPage() {
        waitVisible(btnGoToJobDetailsPage);
        clickByJavascript(btnGoToJobDetailsPage);
    }


    public void scrollToRefreshResources() {
        waitVisible(btnRefreshResources);
        scrollToElement(btnRefreshResources);
    }


    public String getTextCreateJobConflict() {
        String text = "";
        try {
            waitVisible(lbCreateCreateJobConflict, 10);
            text = getText(lbCreateCreateJobConflict);
        } catch (Exception e) {

        }
        return text;
    }


}