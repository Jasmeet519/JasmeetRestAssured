package payloads.pojo.response;

import java.util.ArrayList;

public class ActivityApiResponse {
    private int page;
    private boolean last_page;
    private ArrayList<Activity> activities;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean isLast_page() {
        return last_page;
    }

    public void setLast_page(boolean last_page) {
        this.last_page = last_page;
    }

    public ArrayList<Activity> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<Activity> activities) {
        this.activities = activities;
    }
}
