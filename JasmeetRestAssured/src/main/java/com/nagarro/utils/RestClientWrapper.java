package com.nagarro.utils;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import payloads.pojo.request.UsersPayload;

import java.io.PrintStream;

public class RestClientWrapper {
    private static Logger logger = LogManager.getLogger(RestClientWrapper.class);

    private RequestSpecification requestSpecification;
    private Response restResponse;
    private Helper helper = new Helper();
    private PrintStream logStream;

    public RestClientWrapper(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;

        // Setting logStream to read log from rest assured and set it in Log4j logger
        logStream = helper.setRestAssuredLogStream(logger);
    }

    /**
     * API get method
     *
     * @param resource
     * @return Response object
     */
    public Response get(String resource) {
        restResponse = requestSpecification.filters(RequestLoggingFilter.logRequestTo(logStream), ResponseLoggingFilter.logResponseTo(logStream)).when().get(resource);
        return restResponse;
    }

    /**
     * API post method
     *
     * @param resource
     * @param bodyString is a payload string
     * @return Response object
     */

    public Response post(String resource, String bodyString) {
        restResponse = requestSpecification.body(bodyString).filters(RequestLoggingFilter.logRequestTo(logStream), ResponseLoggingFilter.logResponseTo(logStream)).when().post(resource);
        return restResponse;
    }

    /**
     * API post method
     *
     * @param resource
     * @param object   is a POJO object for payload
     * @return Response object
     */
      public <T> Response post(String resource, T object) {
        Response restResponse = null;

        if (object != null) {
            restResponse = requestSpecification
                    .body(object)
                    .filters(RequestLoggingFilter.logRequestTo(logStream), ResponseLoggingFilter.logResponseTo(logStream))
                    .when()
                    .post(resource);
        }

        return restResponse;
    }

    /**
     * API put method
     *
     * @param resource
     * @param bodyString is a payload string
     * @return Response object
     */
    public Response put(String resource, String bodyString) {
        restResponse = requestSpecification.body(bodyString).filters(RequestLoggingFilter.logRequestTo(logStream), ResponseLoggingFilter.logResponseTo(logStream)).when().put(resource);
        return restResponse;
    }

    /**
     * API put method
     *
     * @param resource
     * @param object   is a POJO object for payload
     * @return Response object
     */
      public <T> Response put(String resource, T object) {
        Response restResponse = null;

        if (object != null) {
            restResponse = requestSpecification
                    .body(object)
                    .filters(RequestLoggingFilter.logRequestTo(logStream), ResponseLoggingFilter.logResponseTo(logStream))
                    .when()
                    .put(resource);
        }

        return restResponse;
    }
    /**
     * API put method
     *
     * @param resource
     * @return Response object
     */
    public Response put(String resource) {
        restResponse = requestSpecification.filters(RequestLoggingFilter.logRequestTo(logStream), ResponseLoggingFilter.logResponseTo(logStream)).when().put(resource);
        return restResponse;
    }


    /**
     * API delete method
     *
     * @param resource
     * @return Response object
     */
    public Response delete(String resource) {
        restResponse = requestSpecification.filters(RequestLoggingFilter.logRequestTo(logStream), ResponseLoggingFilter.logResponseTo(logStream)).when().delete(resource);
        return restResponse;
    }

    /**
     * Get Request Specification
     *
     * @return return Request specification
     */
    public RequestSpecification getRequestSpecification() {
        return requestSpecification;
    }


}
