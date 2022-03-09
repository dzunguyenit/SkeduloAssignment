package com.skedulo.script;

import Utils.PropertiesUtil;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.skedulo.pageobject.ContactPage;
import com.skedulo.pageobject.HomePage;
import com.skedulo.pageobject.JobPage;
import com.skedulo.pageobject.LoginPage;
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
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class JobRecord extends BaseTest {
    WebDriver driver;
    LoginPage loginPage;
    HomePage homePage;
    ContactPage contactPage;
    JobPage jobPage;

    String randomStartTime;

    PropertiesUtil.Enviroment urlEnviroment;

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

    }

    @Test
    public void tc_01_LogInSuccessfully() {
//        SCENARIO: tc_01_LogInSuccessfully:
//        1: Go to aspire login page: https://aspireapp.odoo.com/
//        2: Log In with a username and password: user@aspireapp.com/@sp1r3app
//        3: Verify HomePage is open with url: https://aspireapp.odoo.com/web#cids=1&action=menu
//        and avatar user is displayed

        logger = extent.createTest("tc_01_LogInSuccessfully");
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.inputEmail(urlEnviroment.username());
        loginPage.inputPassword(urlEnviroment.password());
        homePage = loginPage.clickLogIn();
        verifyTrue(homePage.isDisplayedHomePage());
    }

    @Test
    public void tc_02_createJobSuccessfully() {

        logger = extent.createTest("tc_02_createJobSuccessfully");

        String contactName = "Jenny John Dow";
        String description = "This is a Test Job";
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

        randomStartTime = getRandomStartTime();
        System.out.println("Start time = " + randomStartTime);
        contactPage.chooseStartTime(randomStartTime);

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

        checkJobConflict();

        String createScheduleJobSuccessMessage = contactPage.getTextCreateScheduleJobSuccess();
        verifyEquals(createScheduleJobSuccessMessage, "Job has been created successfully. Select options below to go to further step.");

        contactPage.clickAllocateResources();
        contactPage.scrollToRefreshResources();
        contactPage.clickOnFirstResource();
        contactPage.clickAllocate();
        contactPage.clickUpdateJob();

        checkJobConflict();

        String updatedJobSuccessMessage = contactPage.getTextUpdatedScheduleJobSuccess();
        verifyEquals(updatedJobSuccessMessage, "Job has been updated successfully. Select options below to go to further step.");

        contactPage.switchDefault();
    }


    @Test
    public void tc_03_VerifyJobCreatedWithCorrectInfo() {

//        SCENARIO: tc_02_CreateProductWithoutName:
//        Precondition: Log In Successfully
//        1. Click Inventory icon
//        2. Click Menu Products -> Products -> Go to Product Page
//        3. Click Create button
//        4. Click Save button
//        5. Verify error notification is displayed: "Invalid fields: Name"


        logger = extent.createTest("tc_03_VerifyJobCreatedWithCorrectInfo");
        jobPage = contactPage.openJobsPage();
        jobPage.clickLatestJob();
        String startDay = jobPage.getStartDay();
//        verifyEquals(startDay, "9/03/2022 9:00 AM");
        String finishDay = jobPage.getFinishDay();
//        verifyEquals(finishDay, "9/03/2022 10:00 AM");
        String jobStatus = jobPage.getJobStatus();
        verifyEquals(jobStatus, "Dispatched");
        jobPage.clickRelatedTab();

        verifyTrue(jobPage.isDisplayedOneRecord());

        String jobNameOnRelatedTab = jobPage.getJobNameOnRelatedTab();
        jobPage.clickJobNameRecord();

        String jobNameOnDetailTab = jobPage.getJobNameOnDetailTab();
        verifyEquals(jobNameOnDetailTab, jobNameOnRelatedTab);

        String jobStatusOnDetailTab = jobPage.getJobStatus();
        verifyEquals(jobStatusOnDetailTab, "Dispatched");


    }

    private String getRandomStartTime() {
        List<String> listStartTime = Arrays.asList("00:00", "04:00", "06:00", "08:00", "10:00", "12:00", "14:00", "16:00", "18:00", "20:00");
//        List<String> listStartTime = Arrays.asList("00:00", "02:00", "04:00", "06:00", "08:00", "10:00", "12:00", "14:00", "16:00", "18:00", "20:00", "22:00");
        Random r = new Random();
        int randomItem = r.nextInt(listStartTime.size());
        return listStartTime.get(randomItem);
    }


    private void checkJobConflict() {
        String createJobConflictMessage = contactPage.getTextCreateJobConflict();
        if (StringUtils.isNotEmpty(createJobConflictMessage)) {
            verifyEquals(createJobConflictMessage, "There are conflicts for the time this Job is scheduled. Do you wish to proceed?");
            contactPage.clickSave();
        }
    }

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
