package com.nagarro.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.io.IoBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Helper {
    public static Logger logger = LogManager.getLogger(Helper.class);

    /**
     * Helps to read config.properties file
     *
     * @return Properties object of config.properties file
     */
    public Properties readConfigFile() {
        final String RESOURCE_NAME = "/config.properties";
        Properties properties = new Properties();

        try {
            InputStream inputStream = getClass().getResourceAsStream(RESOURCE_NAME);
            properties.load(inputStream);
        } catch (IOException ioException) {
            logger.error(ioException);
            throw new RuntimeException("Problem reading config file");
        }
        return properties;
    }


    /**
     * Method use to get path for Json Schema file
     * @param fileName
     * @return path
     */
    public String getJsonSchemaFilePath(String fileName) {
        File file;
        final String RESOURCE_NAME = "jsonSchemas/" + fileName + ".json";
        logger.info("Reading '" + RESOURCE_NAME + "' file...");

        try {
            URL url = getClass().getClassLoader().getResource(RESOURCE_NAME);
            file = Paths.get(url.toURI()).toFile();

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        logger.info("Absolute path for '" + RESOURCE_NAME + "' is: " + file.getAbsolutePath());
        return file.getAbsolutePath();
    }

    /**
     * @param logger is object of Log4j Logger
     * @return print stream of rest assured
     */
    public PrintStream setRestAssuredLogStream(Logger logger) {
        PrintStream logStream = IoBuilder.forLogger(logger).buildPrintStream();
        RestAssuredConfig restAssuredConfig = new RestAssuredConfig();
        LogConfig logConfig = restAssuredConfig.getLogConfig();
        logConfig.defaultStream(logStream).enablePrettyPrinting(true);
        return logStream;
    }

    /**
     * Method used to convert response to json path object
     * @param response
     * @return jsonPath object
     */
    public JsonPath getRawToJson(Response response) {
        JsonPath jsonPath = new JsonPath(response.asString());
        return jsonPath;
    }

    /**
     * Method to convert PojoObject to String
     * @param pojoObject
     * @return string representation of PojoObject
     */
    public String getPojoToString(Object pojoObject) {
        ObjectMapper objectMapper = new ObjectMapper();
        String value;
        try {
            value = objectMapper.writeValueAsString(pojoObject);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return value;
    }
}
