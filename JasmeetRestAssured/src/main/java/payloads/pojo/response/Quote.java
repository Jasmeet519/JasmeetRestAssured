package payloads.pojo.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Quote {
    private int id;
    private boolean dialogue;
    private String source;
    @JsonProperty("private")
    private boolean myprivate;
    private ArrayList<String> tags;
    private String url;
    private int favorites_count;
    private int upvotes_count;
    private int downvotes_count;
    private String author;
    private String author_permalink;
    private String body;

    private UserDetails user_details;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDialogue() {
        return dialogue;
    }

    public void setDialogue(boolean dialogue) {
        this.dialogue = dialogue;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isMyprivate() {
        return myprivate;
    }

    public void setMyprivate(boolean myprivate) {
        this.myprivate = myprivate;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getFavorites_count() {
        return favorites_count;
    }

    public void setFavorites_count(int favorites_count) {
        this.favorites_count = favorites_count;
    }

    public int getUpvotes_count() {
        return upvotes_count;
    }

    public void setUpvotes_count(int upvotes_count) {
        this.upvotes_count = upvotes_count;
    }

    public int getDownvotes_count() {
        return downvotes_count;
    }

    public void setDownvotes_count(int downvotes_count) {
        this.downvotes_count = downvotes_count;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor_permalink() {
        return author_permalink;
    }

    public void setAuthor_permalink(String author_permalink) {
        this.author_permalink = author_permalink;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public UserDetails getUser_details() {
        return user_details;
    }

    public void setUser_details(UserDetails user_details) {
        this.user_details = user_details;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "id=" + id +
                ", dialogue=" + dialogue +
                ", source='" + source + '\'' +
                ", myprivate=" + myprivate +
                ", tags=" + tags +
                ", url='" + url + '\'' +
                ", favorites_count=" + favorites_count +
                ", upvotes_count=" + upvotes_count +
                ", downvotes_count=" + downvotes_count +
                ", author='" + author + '\'' +
                ", author_permalink='" + author_permalink + '\'' +
                ", body='" + body + '\'' +
                ", user_details=" + user_details +
                '}';
    }
}
