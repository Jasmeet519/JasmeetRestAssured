package payloads.pojo.response;

import java.util.ArrayList;

public class QuoteApiResponse {


    public int page;
    public boolean last_page;
    public ArrayList<Quote> quotes;

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

    public ArrayList<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(ArrayList<Quote> quotes) {
        this.quotes = quotes;
    }

    @Override
    public String toString() {
        return "QuoteApiResponse{" +
                "page=" + page +
                ", last_page=" + last_page +
                ", quotes=" + quotes +
                '}';
    }
}
