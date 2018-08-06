package wall.bilibili.info;

public class SeasonInfo {


    public int season_id;
    public String title;
    public String season_title;

    @Override
    public String toString() {
        return "SeasonInfo{" +

                ", season_id=" + season_id +
                ", title='" + title + '\'' +
                ", season_title='" + season_title + '\'' +
                '}';
    }
}
