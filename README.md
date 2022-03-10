Automation Page Object Model Framework - Using Java, Selenium, Maven, TestNG, Extent Report and Monte for screen recorder
Overview
This project is to provide a good starting structure point for those looking to use Java, Selenium and some other extensions. 
It is also intended to demonstrate how to implement design patterns in a test framework (Page Object Model), Where many test frameworks 
will give some solution demonstrates like scalable, maintainable, readable and repeatable.


Resources

Selenium
TestNg
Extent Report



SCENARIO: tc_01_LogInSuccessfully:
1: Go to log in page: https://test.salesforce.com
2: Log In with a username and password: healthcareqa@skedulo.com.full/Skedulo2022@@
3: Verify HomePage is displayed with text: Home


SCENARIO: tc_02_createJobSuccessfully:
Precondition: Log In Successfully
1. Click on the search bar at the top and search Contact Name: Jenny John Dow
2. Open the Contact: Jenny John Dow
3. Click on the Schedule Job button
4. Choose the Date is Today + 5
5. Choose the Start Time is 10:00 AM
6. Choose the Duration is 2 hrs
7. Choose Urgency is Normal 
8. Input to Description is: This is a Test Job
9. Choose the Region that is Sydney
10. Choose Address Type that is Client and change Address field to14 Victoria Avenue, Castle Hill NSW, Australia
11. Choose Billable field is Billable, and Travel Area is Remote. 
12. Choose Service Setting in Additional Field section, choosing Service Setting is Online Service.
13. In the Service Details section, please select follow up details below:
+ Delivery Method: Fixed Quantity. 
+ Quantity: 2. + Category: Uncategorised. 
+ Service Agreement Item: Choose the first item - “Manly SIL - Ass to access community, social and rec activities - indiv -per public holiday - 1:1 - null - Vic/NSW/Qld/Tas 2018/19 ($17,762.39) - NDIS.

14. In the Resource Requirement section: 
+ Remove all the pre-populate Tag: Peg Feeding, Drivers License. 
+ Click on Create Job button. 
15. Click on Allocate Resources button on the Resource Allocation modal. 
16. At the Available Resource section, click on the first resource in the list
17. Click on Allocate button when the name of the resource appears in the Selected Resource section. 
18. The resource appears in the Allocated Resource section and clicks the Update Job button.

SCENARIO: tc_03_VerifyJobCreatedWithCorrectInfo:
Precondition: Log In Successfully and create Job Successfully:

EXPECTED RESULT: 
1. Checking Job record is created successfully (Job Tab) 
2. Job record details should have this information: 
+ Date/Time is the same when choosing to schedule the job
+ Job Status is Dispatched
+ Move to Related tab and check Job Allocation has one record
+ Open Job Allocation record and checking Name of resource is same with the resource that we have allocated and Status is Dispatched

Pre-requisites
Tools & Libs:
Java 8 or Above
Right-click "Pom.xml" > Maven > Reimport
Intellij
Import Maven Project

Running Tests
Edit runSkedulo.xml to run suite

Intellij menu, click Build > Build Project

$ mvn clean compile

Command Line
Run the test from file "runSkedulo.bat"

Run all tests through maven by runSkedulo.xml suites
$ mvn test

Result:

Report 

https://github.com/dzunguyenit/SkeduloAssignment/blob/main/ReportsImage/report.png

Video demo for this project on link youtube:
https://www.youtube.com/watch?v=ncHvd-1BVaI
