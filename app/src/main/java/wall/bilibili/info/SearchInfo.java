package wall.bilibili.info;

public class SearchInfo extends BaseVideoInfo {
    public String title;
    public int bangumi_id;
    public long pubdate;

public String cover;
public int season_id;

    @Override
    public String toString() {
        return "SearchInfo{" +
                "title='" + title + '\'' +
                ", bangumi_id=" + bangumi_id +
                ", pubdate=" + pubdate +
                ", cover='" + cover + '\'' +
                ", season_id=" + season_id +
                '}';
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getPicUrl() {
        return cover;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
