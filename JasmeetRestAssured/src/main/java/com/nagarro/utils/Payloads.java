package com.nagarro.utils;

import com.nagarro.testclient.TestClient;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import payloads.pojo.request.User;
import payloads.pojo.request.UsersPayload;

import java.util.Map;
import java.util.Properties;
import java.util.Random;

public class Payloads {

    private static Logger logger = LogManager.getLogger(Payloads.class);
    private static Helper helper = new Helper();
    private static Properties config = helper.readConfigFile();
    private static String random = RandomStringUtils.randomNumeric(5);

    public static UsersPayload getCreateSessionPayload() {
        UsersPayload usersPayload = new UsersPayload();
        User user = new User();

        user.setLogin(config.getProperty("userName"));
        user.setPassword(config.getProperty("password"));
        usersPayload.setUser(user);

        return usersPayload;
    }

    public static UsersPayload getCreateUserPayload(Map<String, String> userPayload) {
        UsersPayload usersPayload = new UsersPayload();
        User user = new User();

        user.setLogin(userPayload.get("login") + random);
        user.setEmail(userPayload.get("email").replace("$", random));
        user.setPassword(userPayload.get("password"));

        usersPayload.setUser(user);
        return usersPayload;
    }

    public static UsersPayload getUpdateUserPayload(Map<String, String> userPayload) {
        UsersPayload usersPayload = new UsersPayload();
        User user = new User();


        user.setLogin(userPayload.get("login") + random);
        user.setEmail(userPayload.get("email").replace("$", random));
        user.setPassword(userPayload.get("password"));
        user.setPic(userPayload.get("pic"));
        user.setProfanity_filter(Boolean.parseBoolean(userPayload.get("profanity_filter")));

        usersPayload.setUser(user);
        return usersPayload;
    }
}
