package com.skedulo.pageobject;

import common.BaseElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ContactPage extends BaseElement {

    public ContactPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//button[contains(.,'Schedule Job')]")
    WebElement btnScheduleJob;

    @FindBy(css = "input[name=startDate]")
    WebElement datetimeStartDay;

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

    @FindBy(xpath = "//button[text()='//p[text()='Job has been created successfully. Select options below to go to further step.']']")
    WebElement lbCreateScheduleJobSuccess;

    @FindBy(xpath = "//button[text()='Allocate Resources']")
    WebElement btnAllocateResources;

    public void clickScheduleJob() {
        waitVisible(btnScheduleJob);
        clickByJavascript(btnScheduleJob);
    }

    public void chooseStartDay(String startDay) {
        input(datetimeStartDay, startDay);
    }

    public void chooseStartTime(String startTime) {
        waitVisible(txtStartTime);
        input(txtStartTime, startTime);
    }

    public void inputDuration(String duration) {
        waitVisible(txtDuration);
        input(txtDuration, duration);
    }

    public void inputDescription(String description) {
        waitVisible(txtDescription);
        input(txtDescription, description);
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
        click(btnRemoveAddress);
        waitVisible(txtAddress);
        input(txtAddress, address);
        waitVisible(lbAddressResult);
        click(lbAddressResult);
    }

    public void inputQuantity(String quantity) {
        waitVisible(txtQuantity);
        input(txtQuantity, quantity);
    }

    public void removeTag(String tag) {
        String locator = String.format("//span[text()='%s']/following-sibling::button", tag);
        waitVisible(locator);
        click(locator);
    }

    public void clickCreateJob() {
        waitVisible(btnCreateJob);
        click(btnCreateJob);
    }

    public String getTextCreateScheduleJobSuccess() {
        waitVisible(lbCreateScheduleJobSuccess);
        return getText(lbCreateScheduleJobSuccess);
    }

    public void clickAllocateResources() {
        waitVisible(btnAllocateResources);
        click(btnAllocateResources);
    }
}