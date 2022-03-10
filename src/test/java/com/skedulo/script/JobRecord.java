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
//        1: Go to login page: https://test.salesforce.com
//        2: Log In with a username and password: healthcareqa@skedulo.com.full/Skedulo2022@@
//        3: Verify HomePage is displayed with text: Home

        logger = extent.createTest("tc_01_LogInSuccessfully");
        loginPage = PageFactory.initElements(driver, LoginPage.class);
//        Log In with a username and password: healthcareqa@skedulo.com.full/Skedulo2022@@
        loginPage.inputEmail(urlEnviroment.username());
        loginPage.inputPassword(urlEnviroment.password());
//        click Log In
        homePage = loginPage.clickLogIn();
//        Verify HomePage is displayed with text: Home
        verifyTrue(homePage.isDisplayedHomePage());

    }

    @Test
    public void tc_02_createJobSuccessfully() {

        logger = extent.createTest("tc_02_createJobSuccessfully");

        String contactName = "Jenny John Dow";
        String description = "This is a Test Job";
//        Search contact name: Jenny John Dow
        contactPage = homePage.searchContactName(contactName);
        contactPage.clickScheduleJob();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println(now.format(timeFormatter));

//       Choose startday = now + 5
        int day = now.getDayOfMonth() + 1;
        System.out.println(day);
//      If ( day < 10 ) startday
//      Example = 9, startday = "09/03/2022"
//      Example = 11, startday = "11/03/2022"
        if (day < 10) {
            startDay = "0" + day + "/03/2022";
        } else {
            startDay = day + "/03/2022";
        }

//        Switch to Iframe Schedule Job
        contactPage.switchFrameScheduleJob();
//        Choose start day on Datepicker
        contactPage.chooseStartDay(startDay);
//        Choose Random Start Time to avoid duplicate Resource Available
        randomStartTime = getRandomStartTime();
        System.out.println("Start time = " + randomStartTime);
        contactPage.chooseStartTime(randomStartTime);

//       Input Duration: 2 hours
        contactPage.inputDuration("2");
//       Select Urgency: Normal
        contactPage.selectUrgency("Normal");
//        Input description: This is a Test Job
        contactPage.inputDescription(description);

//        Input Address: 14 Victoria Avenue, Castle Hill NSW, Australia
        contactPage.chooseAddress("14 Victoria Avenue, Castle Hill NSW, Australia");

//        Select Urgency: Normal
//        contactPage.selectUrgency("Normal");
//        Select Billable: Billable
        contactPage.selectBillable("Billable");
//        Select Travel Area: Remote
        contactPage.selectTravelArea("Remote");
//        Select Service Setting: Online Service
        contactPage.selectServiceSetting("Online Service");
//        Select Delivery Method: Fixed Quantity
        contactPage.selectDeliveryMethod("Fixed Quantity");
//        Input Quantity: 2
        contactPage.inputQuantity("2");
//        Select Category: Uncategorised
        contactPage.selectCategory("Uncategorised");
//        Select Service Agreement Item: Manly SIL - Ass to access community, social and rec activities - indiv -per public holiday - 1:1 - null - Vic/NSW/Qld/Tas 2018/19 ($17,762.39) - NDIS
        contactPage.selectServiceAgreementItem("Manly SIL - Ass to access community, social and rec activities - indiv -per public holiday - 1:1 - null - Vic/NSW/Qld/Tas 2018/19 ($17,762.39) - NDIS");

//        Remove tags: Peg Feeding, Drivers License
        contactPage.removeTag("Peg Feeding");
        contactPage.removeTag("Drivers License");
//       Click Create Job button
        contactPage.clickCreateJob();
//       Check Job Conflict: If user create same start time -> Check message is displayed: "There are conflicts for the time this Job is scheduled. Do you wish to proceed?"
//       And Click Save
        checkJobConflict();

//        Verify text: "Job has been created successfully. Select options below to go to further step." is displayed
        String createScheduleJobSuccessMessage = contactPage.getTextCreateScheduleJobSuccess();
        verifyEquals(createScheduleJobSuccessMessage, "Job has been created successfully. Select options below to go to further step.");

//        Click Allocate Resources button
        contactPage.clickAllocateResources();
//        Scroll down to Refresh Resources
        contactPage.scrollToRefreshResources();
//        Click On First Person Resource
        contactPage.clickOnFirstResource();
//        Click Allocate
        contactPage.clickAllocate();
//        Click Update Job button
        contactPage.clickUpdateJob();

//        Check Job Conflict: If user create same start time -> Check message is displayed: "There are conflicts for the time this Job is scheduled. Do you wish to proceed?"
//       And Click Save
        checkJobConflict();

//        Verify text: "Job has been updated successfully. Select options below to go to further step." is displayed
        String updatedJobSuccessMessage = contactPage.getTextUpdatedScheduleJobSuccess();
        verifyEquals(updatedJobSuccessMessage, "Job has been updated successfully. Select options below to go to further step.");

//       Switch to default Content -> Quit iframe
        contactPage.switchDefault();
    }


    @Test
    public void tc_03_VerifyJobCreatedWithCorrectInfo() {

        logger = extent.createTest("tc_03_VerifyJobCreatedWithCorrectInfo");
        jobPage = contactPage.openJobsPage();

//        Click on the latest Job created
        jobPage.clickLatestJob();
//       Get Start Day -> Actual result Start Day
        String actualStartDay = jobPage.getStartDay();
//        Verify start day at testcase: tc_02_createJobSuccessfully -> Expected result
//        and start Day at testcase: tc_03_VerifyJobCreatedWithCorrectInfo-> Actual result
        verifyTrue(actualStartDay.contains(startDay));
//        verifyEquals(startDay, "9/03/2022 9:00 AM");

//        Verify finish day at testcase: tc_02_createJobSuccessfully + duration: 2 hours -> Expected result
//        and finish Day at testcase: tc_03_VerifyJobCreatedWithCorrectInfo-> Actual result
        String finishDay = jobPage.getFinishDay();
        verifyTrue(finishDay.contains(finishDay));
//        verifyEquals(finishDay, "9/03/2022 10:00 AM");
        String jobStatus = "";

//       Using for because web is unstable, sometime must refresh page to Status Dispatched is appear
        for (int i = 0; i <= 2; i++) {
            jobStatus = jobPage.getJobStatus();
            if (jobStatus.equals("Dispatched")) {
                break;
            }
        }

//        Verify job Status is: Dispatched
        verifyEquals(jobStatus, "Dispatched");
//        Click on tab: Related
        jobPage.clickRelatedTab();

//        Verify One Record is Displayed: Job Allocations (1)
        verifyTrue(jobPage.isDisplayedOneRecord());

//      Click on text: Job Allocations (1)
        jobPage.clickOnJobAllocationRecord();

//      Get Job Name On Related Tab -> Expected Result
        String jobNameOnRelatedTab = jobPage.getJobNameOnRelatedTab();
//      Click Job Name On Related Tab -> Actual Result
        jobPage.clickJobNameRecord();

//        Get Job Name On Detail Tab
        String jobNameOnDetailTab = jobPage.getJobNameOnDetailTab();
//        Verify job Name On Related Tab and job Name On Detail
        verifyEquals(jobNameOnDetailTab, jobNameOnRelatedTab);

//        Verify job Status is: Dispatched
        String jobStatusOnDetailTab = jobPage.getJobStatusDispatchedDetail();
        verifyEquals(jobStatusOnDetailTab, "Dispatched");


    }

    //    Random value time to avoid duplicate time when creating Job
    private String getRandomStartTime() {
        List<String> listStartTime = Arrays.asList("00:00", "02:00", "04:00", "06:00", "08:00", "10:00", "12:00", "14:00", "16:00", "18:00", "20:00", "22:00");
        Random r = new Random();
        int randomItem = r.nextInt(listStartTime.size());
        return listStartTime.get(randomItem);
    }

    //    Check Job Conflict: If user create same start time -> Check message is displayed: "There are conflicts for the time this Job is scheduled. Do you wish to proceed?"
    //       And Click Save
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
