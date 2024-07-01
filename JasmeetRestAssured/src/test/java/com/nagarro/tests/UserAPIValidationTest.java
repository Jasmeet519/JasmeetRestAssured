package com.nagarro.tests;

import com.nagarro.utils.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import payloads.pojo.request.UsersPayload;
import com.nagarro.testclient.TestClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Map;

public class UserAPIValidationTest extends TestClient {

    private static Logger logger = LogManager.getLogger(UserAPIValidationTest.class);
    private Helper helper = new Helper();

    private RestClientWrapper restClientWrapper;
    private String userToken;
    private UsersPayload createUsersPayload;
    private UsersPayload updateUsersPayload;

    private Map<String, String> userAPIData;
    private Map<String, String> createUserPayloadTestData;
    private Map<String, String> updateUserPayloadTestData;
    private Map<String, String> expectedGetUserDetailResponse;


    private JsonPath jsonPath;


    @BeforeClass
    public void setup() {
        TestDataReader.init();

        // Reading test data stored in xml file
        userAPIData = TestDataReader.getDataMap("UserAPIValidationTestData");
        createUserPayloadTestData = TestDataReader.getDataMap("createUserPayloadTestData");
        expectedGetUserDetailResponse = TestDataReader.getDataMap("expectedGetUserDetailResponse");
        updateUserPayloadTestData = TestDataReader.getDataMap("updateUserPayloadTestData");


        // Initializing RestClientWrapper object
        restClientWrapper = new RestClientWrapper(requestSpecification);

        // Creating Payload using test data
        createUsersPayload = Payloads.getCreateUserPayload(createUserPayloadTestData);
        updateUsersPayload = Payloads.getUpdateUserPayload(updateUserPayloadTestData);

    }

    @BeforeMethod
    public void init() {
        FilterableRequestSpecification filterableRequestSpecification = (FilterableRequestSpecification) restClientWrapper.getRequestSpecification();
        filterableRequestSpecification.removeHeader("User-Token");
        filterableRequestSpecification.removePathParam("login");
    }

    @Test(priority = 1, description = "Create a new user")
    public void createNewUser() {
        Response serverResponse = restClientWrapper.post(Resources.createUser, createUsersPayload)
                .then().assertThat().statusCode(200).extract().response();
        jsonPath = helper.getRawToJson(serverResponse);
        userToken = jsonPath.getString("User-Token");
        logger.info("User Token is: " + userToken);
        Assert.assertNotNull(userToken);
        Assert.assertEquals(jsonPath.get("login"), createUsersPayload.getUser().getLogin());

        // Verifying: JSON Schema for create user response
        MatcherAssert.assertThat(serverResponse.body().asString(), JsonSchemaValidator.matchesJsonSchema(new File(helper.getJsonSchemaFilePath("createUserJsonSchema"))));
    }

    @Test(priority = 2, description = "Get user details")
    public void getUserDetails() {
        // Setting path parameter and user token
        restClientWrapper.getRequestSpecification().pathParam("login", createUsersPayload.getUser().getLogin()).header("User-Token", userToken);

        Response serverResponse = restClientWrapper.get(Resources.getUser)
                .then().assertThat().statusCode(200).extract().response();
        jsonPath = helper.getRawToJson(serverResponse);

        expectedGetUserDetailResponse.put("login", createUsersPayload.getUser().getLogin());
        expectedGetUserDetailResponse.put("account_details.email", createUsersPayload.getUser().getEmail());
        expectedGetUserDetailResponse.put("pro", null);
        expectedGetUserDetailResponse.forEach((key, value) -> {
            logger.info("Validating response for '" + key + "' key should be: " + value);
            Assert.assertEquals(jsonPath.getString(key), value);
        });

    }

    @Test(priority = 3, description = "Update user details")
    public void updateUserDetails() {
        restClientWrapper.getRequestSpecification().pathParam("login", createUsersPayload.getUser().getLogin()).header("User-Token", userToken);

        Response serverResponse = restClientWrapper.put(Resources.updateUser, updateUsersPayload)
                .then().assertThat().statusCode(200).extract().response();
        jsonPath = helper.getRawToJson(serverResponse);

        Assert.assertEquals(jsonPath.getString("message"), userAPIData.get("updateUserSuccessMsg"));
    }


}
