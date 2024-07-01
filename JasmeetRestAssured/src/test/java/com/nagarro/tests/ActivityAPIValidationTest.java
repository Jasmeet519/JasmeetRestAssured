package com.nagarro.tests;

import com.nagarro.testclient.TestClient;
import com.nagarro.utils.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import payloads.pojo.response.Activity;
import payloads.pojo.response.ActivityApiResponse;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ActivityAPIValidationTest extends TestClient {

    private static Logger logger = LogManager.getLogger(ActivityAPIValidationTest.class);
    private Helper helper = new Helper();
    private RestClientWrapper restClientWrapper;
    private Map<String, String> queryParameters;
    private Map<String, String> expectedResponseFollowActivity;
    private Map<String, String> expectedResponseUnfollowActivity;
    private Map<String, String> expectedResponseDeleteActivity;

    private String userToken;
    private String activityId;
    private JsonPath jsonPath;


    @BeforeClass
    public void setup() {
        TestDataReader.init();

        queryParameters = TestDataReader.getDataMap("UserActivityAPIValidationTestData/QueryParametersTestData");
        expectedResponseFollowActivity = TestDataReader.getDataMap("UserActivityAPIValidationTestData/followActivityResponse");
        expectedResponseUnfollowActivity = TestDataReader.getDataMap("UserActivityAPIValidationTestData/unfollowActivityResponse");
        expectedResponseDeleteActivity = TestDataReader.getDataMap("UserActivityAPIValidationTestData/deleteActivityResponse");

        // Initializing RestClientWrapper object
        restClientWrapper = new RestClientWrapper(requestSpecification);
        userToken = createUserSession();
        activityId = getUserActivityId();

    }

    @BeforeMethod
    public void init() {
        FilterableRequestSpecification filterableRequestSpecification = (FilterableRequestSpecification) restClientWrapper.getRequestSpecification();
        filterableRequestSpecification.removeHeader("User-Token");
    }

    @Test(priority = 8, description = "Get user activity")
    public void getUserActivity() {
        restClientWrapper.getRequestSpecification().header("User-Token", userToken).queryParams(queryParameters).expect().defaultParser(Parser.JSON);

        Response serverResponse = restClientWrapper.get(Resources.getActivity)
                .then().assertThat().statusCode(200).extract().response();

        ActivityApiResponse activityApiResponse = serverResponse.as(ActivityApiResponse.class);
        List<Activity> listActivities = activityApiResponse.getActivities();
        listActivities.forEach(activity -> {
            logger.info("Validating response for activity Id: " + activity.getActivity_id());
            Assert.assertEquals(activity.getOwner_type().toLowerCase(), queryParameters.get("type"));
            Assert.assertEquals(activity.getOwner_id(), queryParameters.get("filter"));
            Assert.assertEquals(activity.getOwner_value(), queryParameters.get("filter"));
        });

        // Validating JSON Schema
        MatcherAssert.assertThat(serverResponse.body().asString(), JsonSchemaValidator.matchesJsonSchema(new File(helper.getJsonSchemaFilePath("activityJsonSchema"))));

    }

    @Test(priority = 9, description = "Follow user activity")
    public void followUserActivity() {
        restClientWrapper.getRequestSpecification().header("User-Token", userToken).expect().defaultParser(Parser.JSON);

        Response serverResponse = restClientWrapper.put(Resources.followActivity)
                .then().assertThat().statusCode(200).extract().response();
        jsonPath = helper.getRawToJson(serverResponse);
        expectedResponseFollowActivity.forEach((key, value) -> Assert.assertEquals(jsonPath.getString(key), value));
    }

    @Test(priority = 10, description = "Unfollow user activity")
    public void unfollowUserActivity() {
        restClientWrapper.getRequestSpecification().header("User-Token", userToken).expect().defaultParser(Parser.JSON);

        Response serverResponse = restClientWrapper.put(Resources.unfollowActivity)
                .then().assertThat().statusCode(200).extract().response();
        jsonPath = helper.getRawToJson(serverResponse);
        expectedResponseUnfollowActivity.forEach((key, value) -> Assert.assertEquals(jsonPath.getString(key), value));
    }

    @Test(priority = 11, description = "Delete user activity")
    public void deleteUserActivity() {
        restClientWrapper.getRequestSpecification().header("User-Token", userToken).expect().defaultParser(Parser.JSON);
        restClientWrapper.getRequestSpecification().pathParam("activity_id", activityId);

        Response serverResponse = restClientWrapper.delete(Resources.deleteActivity)
                .then().assertThat().statusCode(200).extract().response();
        jsonPath = helper.getRawToJson(serverResponse);
        expectedResponseDeleteActivity.forEach((key, value) -> Assert.assertEquals(jsonPath.getString(key), value));
    }


    public String createUserSession() {
        Response serverResponse = restClientWrapper.post(Resources.createSession, Payloads.getCreateSessionPayload())
                .then().assertThat().statusCode(200).extract().response();
        JsonPath jsonPath = helper.getRawToJson(serverResponse);
        String userToken = jsonPath.getString("User-Token");
        logger.info("User Token is: " + userToken);
        Assert.assertNotNull(userToken);
        return userToken;
    }

    public String getUserActivityId() {
        // Setting query parameter on request specification
        restClientWrapper.getRequestSpecification().header("User-Token", userToken);

        Response serverResponse = restClientWrapper.get(Resources.getActivity)
                .then().assertThat().statusCode(200).extract().response();

        ActivityApiResponse activityApiResponse = serverResponse.as(ActivityApiResponse.class);

        Optional<Activity> optionalActivity = activityApiResponse.getActivities().stream().filter(activity -> activity.getActivity_id() != 0).findAny();
        if (optionalActivity.isPresent()) {
            Activity activity = optionalActivity.get();
            logger.info("User Activity Id with user session is: " + activity.getActivity_id());
            return Integer.toString(activity.getActivity_id());
        }
        return null;

    }


}
