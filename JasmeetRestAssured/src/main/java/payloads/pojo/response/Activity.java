package payloads.pojo.response;

public class Activity{
    private int activity_id;
    private String owner_type;
    private String owner_id;
    private String owner_value;
    private String action;
    private int trackable_id;
    private String trackable_type;
    private String trackable_value;
    private String message;

    public int getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(int activity_id) {
        this.activity_id = activity_id;
    }

    public String getOwner_type() {
        return owner_type;
    }

    public void setOwner_type(String owner_type) {
        this.owner_type = owner_type;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getOwner_value() {
        return owner_value;
    }

    public void setOwner_value(String owner_value) {
        this.owner_value = owner_value;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getTrackable_id() {
        return trackable_id;
    }

    public void setTrackable_id(int trackable_id) {
        this.trackable_id = trackable_id;
    }

    public String getTrackable_type() {
        return trackable_type;
    }

    public void setTrackable_type(String trackable_type) {
        this.trackable_type = trackable_type;
    }

    public String getTrackable_value() {
        return trackable_value;
    }

    public void setTrackable_value(String trackable_value) {
        this.trackable_value = trackable_value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
