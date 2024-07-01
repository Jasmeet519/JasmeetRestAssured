package com.nagarro.tests;

import com.nagarro.utils.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import com.nagarro.testclient.TestClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import payloads.pojo.response.Quote;
import payloads.pojo.response.QuoteApiResponse;

import java.io.File;
import java.util.Optional;

public class QuoteAPIValidationTest extends TestClient {

    private static Logger logger = LogManager.getLogger(QuoteAPIValidationTest.class);
    private Helper helper = new Helper();
    private RestClientWrapper restClientWrapper;
    private int quoteId;
    private String userToken;
    private Quote quotePojo;


    @BeforeClass
    public void setup() {
        TestDataReader.init();

        // Initializing RestClientWrapper object
        restClientWrapper = new RestClientWrapper(requestSpecification);
        userToken = createUserSession();

        // Fetching Quote response with object "hidden": false
        quotePojo = getUnHiddenAndNonFavoriteQuote();
        quoteId = quotePojo.getId();
        logger.info("Quote Id of quote: " + quoteId);

    }

    @BeforeMethod
    public void init() {
        FilterableRequestSpecification filterableRequestSpecification = (FilterableRequestSpecification) restClientWrapper.getRequestSpecification();
        filterableRequestSpecification.removePathParam("quote_id");
    }

    @Test(priority = 4, description = "Hide a quote")
    public void hideAQuote() {
        restClientWrapper.getRequestSpecification().pathParam("quote_id", quoteId);

        // Updating expected quote response with object "hidden": true
        quotePojo.getUser_details().setHidden(true);

        Response serverResponse = restClientWrapper.put(Resources.hideQuote)
                .then().assertThat().statusCode(200).extract().response();

        Quote actualHiddenQuoteResponse = serverResponse.as(Quote.class);
        logger.info("Expected response: " + helper.getPojoToString(quotePojo));
        logger.info("Actual response: " + helper.getPojoToString(actualHiddenQuoteResponse));

        Assert.assertEquals(helper.getPojoToString(actualHiddenQuoteResponse), helper.getPojoToString(quotePojo));

        // Validating JSON Schema
        MatcherAssert.assertThat(serverResponse.body().asString(), JsonSchemaValidator.matchesJsonSchema(new File(helper.getJsonSchemaFilePath("quoteJsonSchema"))));

    }

    @Test(priority = 5, description = "Unhide a quote")
    public void unHideAQuote() {
        restClientWrapper.getRequestSpecification().pathParam("quote_id", quoteId);

        // Updating expected quote response with object "hidden": false
        quotePojo.getUser_details().setHidden(false);

        Response serverResponse = restClientWrapper.put(Resources.unHideQuote)
                .then().assertThat().statusCode(200).extract().response();

        Quote actualUnHiddenQuoteResponse = serverResponse.as(Quote.class);
        logger.info("Expected response: " + helper.getPojoToString(quotePojo));
        logger.info("Actual response: " + helper.getPojoToString(actualUnHiddenQuoteResponse));

        Assert.assertEquals(helper.getPojoToString(actualUnHiddenQuoteResponse), helper.getPojoToString(quotePojo));

        // Validating JSON Schema
        MatcherAssert.assertThat(serverResponse.body().asString(), JsonSchemaValidator.matchesJsonSchema(new File(helper.getJsonSchemaFilePath("quoteJsonSchema"))));

    }

    @Test(priority = 6, description = "Add quote as Favourite")
    public void addQuoteAsFavourite() {
        restClientWrapper.getRequestSpecification().pathParam("quote_id", quoteId);

        // Updating expected quote response with object "favorite": true and "favorites_count":<favorites_count + 1>
        quotePojo.getUser_details().setFavorite(true);
        quotePojo.setFavorites_count(quotePojo.getFavorites_count() + 1);

        Response serverResponse = restClientWrapper.put(Resources.favQuote)
                .then().assertThat().statusCode(200).extract().response();

        Quote actualUnHiddenQuoteResponse = serverResponse.as(Quote.class);
        logger.info("Expected response: " + helper.getPojoToString(quotePojo));
        logger.info("Actual response: " + helper.getPojoToString(actualUnHiddenQuoteResponse));

        Assert.assertEquals(helper.getPojoToString(actualUnHiddenQuoteResponse), helper.getPojoToString(quotePojo));

        // Validating JSON Schema
        MatcherAssert.assertThat(serverResponse.body().asString(), JsonSchemaValidator.matchesJsonSchema(new File(helper.getJsonSchemaFilePath("quoteJsonSchema"))));
    }

    @Test(priority = 7, description = "Add quote as UnFavourite")
    public void addQuoteAsUnFavourite() {
        restClientWrapper.getRequestSpecification().pathParam("quote_id", quoteId);

        // Updating expected quote response with object "favorite": false and "favorites_count":<favorites_count - 1>
        quotePojo.getUser_details().setFavorite(false);
        quotePojo.setFavorites_count(quotePojo.getFavorites_count() - 1);

        Response serverResponse = restClientWrapper.put(Resources.unfavQuote)
                .then().assertThat().statusCode(200).extract().response();

        Quote actualUnHiddenQuoteResponse = serverResponse.as(Quote.class);
        logger.info("Expected response: " + helper.getPojoToString(quotePojo));
        logger.info("Actual response: " + helper.getPojoToString(actualUnHiddenQuoteResponse));

        Assert.assertEquals(helper.getPojoToString(actualUnHiddenQuoteResponse), helper.getPojoToString(quotePojo));

        // Validating JSON Schema
        MatcherAssert.assertThat(serverResponse.body().asString(), JsonSchemaValidator.matchesJsonSchema(new File(helper.getJsonSchemaFilePath("quoteJsonSchema"))));
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

    public Quote getUnHiddenAndNonFavoriteQuote() {
        restClientWrapper.getRequestSpecification().header("User-Token", userToken).expect().defaultParser(Parser.JSON);

        Response serverResponse = restClientWrapper.get(Resources.getQuotes)
                .then().assertThat().statusCode(200).extract().response();

        QuoteApiResponse quoteApiResponse = serverResponse.as(QuoteApiResponse.class);
        System.out.println(quoteApiResponse.toString());

        Optional isHiddenAndNonFavoriteQuoteOptional = quoteApiResponse.quotes.stream().filter(quote -> !(quote.getUser_details().isHidden() && quote.getUser_details().isFavorite())).findAny();
        if (isHiddenAndNonFavoriteQuoteOptional.isPresent()) {
            Quote quote = (Quote) isHiddenAndNonFavoriteQuoteOptional.get();
            logger.info("Unhidden and UnFavorite Quote Response: " + helper.getPojoToString(quote));
            return quote;
        }

        return null;
    }
}
