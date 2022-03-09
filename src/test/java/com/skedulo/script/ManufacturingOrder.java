package com.skedulo.script;

import Utils.PropertiesUtil;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.skedulo.pageobject.*;
import common.BaseTest;
import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ManufacturingOrder extends BaseTest {
    WebDriver driver;
    LoginPage loginPage;
    HomePage homePage;
    ContactPage contactPage;
    JobPage jobPage;
//    ProductPage productPage;
//    ManufacturingPage manufacturingPage;

    String pathData = "/data/";
    PropertiesUtil.Enviroment urlEnviroment;

    String randomProductName, randomOrderName;

    ExtentReports extent;
    ExtentTest logger;
    String startDay = "";


    @BeforeClass
    public void beforeClass() {

        ExtentHtmlReporter reporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/Reports/ManufacturingOrder.html");
        extent = new ExtentReports();
        extent.attachReporter(reporter);

//        Read file Property staging.properties
        ConfigFactory.setProperty("env", "staging");
        urlEnviroment = ConfigFactory.create(PropertiesUtil.Enviroment.class);

        log.info("----------OPEN BROWSER-----------");
        driver = openMultiBrowser(urlEnviroment.browser(), urlEnviroment.url(), urlEnviroment.version());

        loginPage = PageFactory.initElements(driver, LoginPage.class);

        randomProductName = "Auto Product Name " + randomUniqueNumber();
        log.info("----------randomProductName----------- " + randomProductName);

        randomOrderName = "Auto Order Name " + randomUniqueNumber();

        log.info("----------randomProductName----------- " + randomOrderName);
    }

    @Test
    public void tc_01_LogInSuccessfully() {

        String contactName = "Jenny John Dow";
        String description = "This is a Test Job";
//        SCENARIO: tc_01_LogInSuccessfully:
//        1: Go to aspire login page: https://aspireapp.odoo.com/
//        2: Log In with a username and password: user@aspireapp.com/@sp1r3app
//        3: Verify HomePage is open with url: https://aspireapp.odoo.com/web#cids=1&action=menu
//        and avatar user is displayed
        logger = extent.createTest("tc_01_LogInSuccessfully");
        loginPage.inputEmail(urlEnviroment.username());
        loginPage.inputPassword(urlEnviroment.password());
        homePage = loginPage.clickLogIn();
        verifyTrue(homePage.isDisplayedHomePage());

        contactPage = homePage.searchContactName(contactName);
        contactPage.clickScheduleJob();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println(now.format(timeFormatter));

        int day = now.getDayOfMonth() + 5;
        System.out.println(day);

        if (day < 10) {
            startDay = "0" + day + "/03/2022";
        } else {
            startDay = day + "/03/2022";
        }

        contactPage.switchFrameScheduleJob();
        contactPage.chooseStartDay(startDay);
        contactPage.chooseStartTime("10:00");
        contactPage.inputDuration("2");
        contactPage.selectUrgency("Normal");
        contactPage.inputDescription(description);

        contactPage.chooseAddress("14 Victoria Avenue, Castle Hill NSW, Australia");

        contactPage.selectUrgency("Normal");
        contactPage.selectBillable("Billable");
        contactPage.selectTravelArea("Remote");
        contactPage.selectServiceSetting("Online Service");
        contactPage.selectDeliveryMethod("Fixed Quantity");
        contactPage.inputQuantity("2");
        contactPage.selectCategory("Uncategorised");
        contactPage.selectServiceAgreementItem("Manly SIL - Ass to access community, social and rec activities - indiv -per public holiday - 1:1 - null - Vic/NSW/Qld/Tas 2018/19 ($17,762.39) - NDIS");

        contactPage.removeTag("Peg Feeding");
        contactPage.removeTag("Drivers License");
        contactPage.clickCreateJob();

//        contactPage.switchDefault();
        checkJobConflict();

        String createScheduleJobSuccessMessage = contactPage.getTextCreateScheduleJobSuccess();
        verifyEquals(createScheduleJobSuccessMessage, "Job has been created successfully. Select options below to go to further step.");

        contactPage.clickAllocateResources();
        contactPage.scrollToRefreshResources();
        contactPage.clickOnFirstResource();
        contactPage.clickAllocate();
        contactPage.clickUpdateJob();

        checkJobConflict();

//        String jobConflictMessage = contactPage.getTextCreateJobConflict();
//        if (StringUtils.isNotEmpty(createJobConflictMessage)) {
//            verifyEquals(jobConflictMessage, "There are conflicts for the time this Job is scheduled. Do you wish to proceed?");
//            contactPage.clickSave();
//        }
//        contactPage.switchFrameScheduleJob();
        // Must Scroll down to Available Resource

    }


    @Test
    public void tc_02_CreateProductWithoutName() {

//        SCENARIO: tc_02_CreateProductWithoutName:
//        Precondition: Log In Successfully
//        1. Click Inventory icon
//        2. Click Menu Products -> Products -> Go to Product Page
//        3. Click Create button
//        4. Click Save button
//        5. Verify error notification is displayed: "Invalid fields: Name"


        logger = extent.createTest("tc_02_CreateProductWithoutName");
        jobPage = contactPage.openJobsPage();
        jobPage.clickLatestJob();
        String startDay = jobPage.getStartDay();
        verifyEquals(startDay, "9/03/2022 9:00 AM");
        String finishDay = jobPage.getFinishDay();
        verifyEquals(finishDay, "9/03/2022 10:00 AM");
        String jobStatus = jobPage.getJobStatus();
        verifyEquals(jobStatus, "Dispatched");
        jobPage.clickRelatedTab();

        String titleJobApplication = jobPage.getTitleJobApplication();
        String quantityRecord = jobPage.getQuantityRecord();

        String totalRecord = titleJobApplication + quantityRecord;
        verifyEquals(totalRecord, "Job Allocations (1)");

        String jobNameOnRelatedTab = jobPage.getJobNameOnRelatedTab();
        jobPage.clickJobNameRecord();

        String jobNameOnDetailTab = jobPage.getJobNameOnDetailTab();
        verifyEquals(jobNameOnDetailTab, jobNameOnRelatedTab);

        String jobStatusOnDetailTab = jobPage.getJobStatus();
        verifyEquals(jobStatusOnDetailTab, "Dispatched");


    }

    private void checkJobConflict() {
        String createJobConflictMessage = contactPage.getTextCreateJobConflict();
        if (StringUtils.isNotEmpty(createJobConflictMessage)) {
            verifyEquals(createJobConflictMessage, "There are conflicts for the time this Job is scheduled. Do you wish to proceed?");
            contactPage.clickSave();
        }
    }
//
//    @Test
//    public void tc_03_CreateProductSuccessfully() {
//
////        SCENARIO: tc_03_CreateProductSuccessfully:
////        Precondition: Log In Successfully
////        1. Input random valid Product Name
////        2. Click tab Quantity: Update quantity > 10( data = 15)
////        3. Click Save button
//        logger = extent.createTest("tc_03_CreateProductSuccessfully");
//        productPage.inputProductName(randomProductName);
//        productPage.clickUpdateQuantity();
//        productPage.clickCreateQuantity();
//        productPage.inputQuantity("15");
//        productPage.btnSaveRecord();
//    }
//
//    @Test
//    public void tc_04_CreateManufacturingOrderWithoutName() {
//
////        SCENARIO: tc_04_CreateManufacturingOrderWithoutName:
////        Precondition: Log In Successfully and Create Manufacturing Order item
////        for the created Product on Scenerio: tc_03_CreateProductSuccessfully
////
////        1. Back to HomePage by clicking Application Icon
////        2. Click Manufacturing Menu
////        3. Click Create button
////        4. Click button Save Record
////        5. Verify error notification is displayed: "Invalid fields:"
////        6. Verify content notification is displayed:
////        Product
////        Product Unit of Measure
//
//        logger = extent.createTest("tc_04_CreateManufacturingOrderWithoutName");
//        homePage = productPage.clickApplicationIcon();
//        manufacturingPage = homePage.clickManufacturingMenu();
//        manufacturingPage.clickCreateManufacturingOrders();
//        manufacturingPage.btnSaveRecord();
//        // Don't input name -> show popup "Invalid fields:"
//        verifyEquals(productPage.getNotificationTitle(), "Invalid fields:");
//
////        Get String "Product" from the total content "Product\nProduct Unit of Measure"
//        String contentNotification = productPage.getNotificationContent();
//
////        Get String "Product" from the total content "Product\nProduct Unit of Measure"
//        String productNotification = contentNotification.substring(0, 7);
//        verifyEquals(productNotification, "Product");
//
////        Get String "Product Unit of Measure" from the total content "ProductProduct Unit of Measure"
//        String productUnitNotification = contentNotification.substring(7, contentNotification.length());
//        verifyEquals(productUnitNotification, "\nProduct Unit of Measure");
//    }
//
//    @Test
//    public void tc_05_CreateManufacturingOrderSuccessfully() {
//
////        SCENARIO: tc_05_CreateManufacturingOrderSuccessfully:
////        Precondition: Log In Successfully and Create Manufacturing Order item
////        for the created Product on Scenerio: tc_03_CreateProductSuccessfully
////
////        1. Input product name for the created Product on Scenario: tc_03_CreateProductSuccessfully
////        2. Get value of: quantity, scheduled date and responsible user -> Expected Result
////        3. Click Save
////        4. Status change -> draft -> Verify status draft is active
////        5. Click confirm -> Status change -> confirmed -> Verify status confirmed is active
////        5. Click Mark As Done -> Verify popup is displayed "There are no components to consume. Are you still sure you want to continue?"
////        6. Click Ok -> Verify popup is displayed "You have not recorded produced quantities yet, by clicking on apply Odoo will produce all the finished products and consume all components."
////        7. Click Apply -> Status change -> done -> Verify status done is active
////        8. Verify the new Manufacturing Order is created with corrected information includes: Product Name, Quantity, scheduledDate, responsibleUser
//
//        logger = extent.createTest("tc_05_CreateManufacturingOrderSuccessfully");
//        manufacturingPage.inputOrderName(randomProductName);
//
//        String quantity = "1.00";
//        String scheduledDate = manufacturingPage.getScheduledDate();
//        String responsibleUser = manufacturingPage.getResponsibleUser();
//
//        manufacturingPage.btnSaveRecord();
//
////        Wait current state until is "draft"
//        manufacturingPage.waitCurrentState("draft");
////        Check current state is "draft"
//        verifyEquals(manufacturingPage.getCurrentState("data-value"), "draft");
////        Check state "draft" is active by get attribute "aria-checked" is True
//        verifyTrue(Boolean.valueOf(manufacturingPage.getCurrentState("aria-checked")));
//        manufacturingPage.clickConfirm();
//
////        Wait current state until is "confirmed"
//        manufacturingPage.waitCurrentState("confirmed");
////        Check current state is "confirmed"
//        verifyEquals(manufacturingPage.getCurrentState("data-value"), "confirmed");
////        Check state "confirmed" is active by get attribute "aria-checked" is True
//        verifyTrue(Boolean.valueOf(manufacturingPage.getCurrentState("aria-checked")));
//
//        manufacturingPage.clickMarkAsDone();
//
//        verifyEquals(manufacturingPage.getConfirmationMessage(), "There are no components to consume. Are you still sure you want to continue?");
//        manufacturingPage.clickOk();
//
//        verifyEquals(manufacturingPage.getConfirmationImmediateProductionMessage(), "You have not recorded produced quantities yet, by clicking on apply Odoo will produce all the finished products and consume all components.");
//        manufacturingPage.clickApply();
//
////        Wait current state until is "done"
//        manufacturingPage.waitCurrentState("done");
////        Check current state is "done"
//        verifyEquals(manufacturingPage.getCurrentState("data-value"), "done");
////        Check state "done" is active by get attribute "aria-checked" is True
//        verifyTrue(Boolean.valueOf(manufacturingPage.getCurrentState("aria-checked")));
//
////        Validate the new Manufacturing Order is created with corrected information.
////        Verify Manufacturing Order Infomation includes: Product Name, Quantity, scheduledDate, responsibleUser
//
//        String productName = manufacturingPage.getProductName();
//        String quantityProduct = manufacturingPage.getQuantity();
//        String scheduleTimeFinal = manufacturingPage.getScheduledTime();
//        String responsibleUserFinal = manufacturingPage.getResponsible();
//
//        verifyEquals(productName, randomProductName);
//        verifyEquals(quantityProduct, quantity);
//        verifyEquals(scheduleTimeFinal, scheduledDate);
//        verifyEquals(responsibleUserFinal, responsibleUser);
//
//    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String temp = getScreenshot(driver);
            try {
                logger.fail(result.getThrowable().getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            String temp = getScreenshot(driver);
            try {
                logger.log(Status.PASS, "Pass", MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        extent.flush();

    }

    @AfterClass
    public void afterClass() {
        closeBrowser();
    }

}
