package com.nagarro.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.nagarro.utils.Reporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;

public class ReportListener implements ITestListener {
    private static Logger logger = LogManager.getLogger(ReportListener.class);
    String reportPath;

    @Override
    public void onTestStart(ITestResult iTestResult) {
        System.out.println("onTestStart");
        Reporter.test.set(Reporter.extentReports.createTest(iTestResult.getMethod().getMethodName()));
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        System.out.println("onTestSuccess");
        Reporter.test.get().log(Status.PASS, "This test is Passed");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        System.out.println("onTestFailure");
        Reporter.test.get().log(Status.FAIL, "This test is Failed: " + iTestResult.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("onTestSkipped");
        Reporter.test.get().log(Status.SKIP, "This test is Failed: " + iTestResult.getThrowable());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {
        ThreadContext.put("logFilename", Thread.currentThread().getName());
        System.out.println("onStart");
        reportPath = Reporter.intializeReport();
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        System.out.println("onFinish");
        Reporter.finalizeReport();
    }
}
