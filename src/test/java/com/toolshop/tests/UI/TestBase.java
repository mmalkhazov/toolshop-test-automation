package com.toolshop.tests.UI;

import com.toolshop.config.ApplicationManager;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;

public class TestBase {

    Logger logger = LoggerFactory.getLogger(TestBase.class);

    protected ApplicationManager app = new ApplicationManager(System.getProperty("browser", "chrome"));
    public static WebDriver driver;


    @BeforeSuite
    public void setUp() {
        driver = app.starTest();
    }


    @AfterSuite
    public void tearDown() {
        app.stopTest();
    }


    @BeforeMethod
    public void startTest(Method method){
        logger.info("Start test " + method.getName());



    }


    @AfterMethod
    public void stopTest(ITestResult result){
        if(result.isSuccess()){
            logger.info("PASSED: " + result.getMethod().getMethodName());
        }else{
            logger.error("FAILED: " + result.getMethod().getMethodName() + "Screenshot path: " + app.takeScreenshot());
        }
        logger.info("Stop test");
        logger.info("=============================================================");
    }
}
