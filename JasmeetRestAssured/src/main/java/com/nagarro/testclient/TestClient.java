package com.nagarro.testclient;

import com.nagarro.utils.Helper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

import java.util.Properties;

public class TestClient {
    private Helper helper = new Helper();
    public Properties config = helper.readConfigFile();
    private static Logger logger = LogManager.getLogger(TestClient.class);

    public RequestSpecification requestSpecification;


    @BeforeClass(alwaysRun = true)
    public void initialize() {
        // Comment added for log4J2.xml
        ThreadContext.put("logFilename", Thread.currentThread().getName());

        // Setting Rest Assured Base URI
        logger.info("Initializing Base URI from config.properties: " + config.getProperty("BaseURI"));
        RestAssured.baseURI = config.getProperty("BaseURI");

        //Setting Request specification
        logger.info("Initializing Request Specification...");
        requestSpecification = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Token token=\"" + config.getProperty("authorizationToken") + "\"");

    }


}
