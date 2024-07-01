package payloads.pojo.request;

public class User {
    private String login;
    private String email;
    private String password;
    private String pic;
    private boolean profanity_filter;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public boolean isProfanity_filter() {
        return profanity_filter;
    }

    public void setProfanity_filter(boolean profanity_filter) {
        this.profanity_filter = profanity_filter;
    }


}
