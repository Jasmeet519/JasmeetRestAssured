package payloads.pojo.response;

public class UserDetails {
    private boolean favorite;
    private boolean upvote;
    private boolean downvote;
    private boolean hidden;

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isUpvote() {
        return upvote;
    }

    public void setUpvote(boolean upvote) {
        this.upvote = upvote;
    }

    public boolean isDownvote() {
        return downvote;
    }

    public void setDownvote(boolean downvote) {
        this.downvote = downvote;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "favorite=" + favorite +
                ", upvote=" + upvote +
                ", downvote=" + downvote +
                ", hidden=" + hidden +
                '}';
    }
}
