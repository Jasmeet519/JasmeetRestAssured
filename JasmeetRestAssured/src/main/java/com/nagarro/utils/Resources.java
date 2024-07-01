package com.nagarro.utils;

public class Resources {
    public static String createSession = "/api/session";
    public static String createUser = "/api/users";
    public static String getUser = "/api/users/{login}";
    public static String updateUser = "/api/users/{login}";

    public static String getQuotes = "/api/quotes";
    public static String hideQuote = "/api/quotes/{quote_id}/hide";
    public static String unHideQuote = "/api/quotes/{quote_id}/unhide";
    public static String favQuote = "/api/quotes/{quote_id}/fav";
    public static String unfavQuote = "/api/quotes/{quote_id}/unfav";
    public static String getActivity = "/api/activities/";
    public static String followActivity = "/api/activities/follow/";
    public static String unfollowActivity = "/api/activities/unfollow/";
    public static String deleteActivity = "/api/activities/{activity_id}";
}
