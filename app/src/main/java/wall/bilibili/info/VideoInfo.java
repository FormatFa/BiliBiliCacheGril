package wall.bilibili.info;

public class VideoInfo extends BaseVideoInfo {
    private int comment;
    private int typeid;
    private int play;
    private String pic;

    private String description;
    private String copyright;
    private String title;
    private int review;
    private String author;
    private int mid;
    private long created;
    private String length;
    private int video_review;
    private int favorites;
    private int aid;
    private boolean hide_click;


    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public int getPlay() {
        return play;
    }

    public void setPlay(int play) {
        this.play = play;
    }

    public String getPic() {
        return pic;
    }

    @Override
    public String toString() {
        return "VideoInfo{" +
                "comment=" + comment +
                ", typeid=" + typeid +
                ", play=" + play +
                ", pic='" + pic + '\'' +
                ", description='" + description + '\'' +
                ", copyright='" + copyright + '\'' +
                ", title='" + title + '\'' +
                ", review=" + review +
                ", author='" + author + '\'' +
                ", mid=" + mid +
                ", created=" + created +
                ", length='" + length + '\'' +
                ", video_review=" + video_review +
                ", favorites=" + favorites +
                ", aid=" + aid +
                ", hide_click=" + hide_click +
                '}';
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }


    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getPicUrl() {
        return pic;
    }

    @Override
    public String getMessage() {
        return null;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public int getVideo_review() {
        return video_review;
    }

    public void setVideo_review(int video_review) {
        this.video_review = video_review;
    }

    public int getFavorites() {
        return favorites;
    }

    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public boolean isHide_click() {
        return hide_click;
    }

    public void setHide_click(boolean hide_click) {
        this.hide_click = hide_click;
    }
}
