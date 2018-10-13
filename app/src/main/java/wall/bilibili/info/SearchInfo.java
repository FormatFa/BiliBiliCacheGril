package wall.bilibili.info;

import wall.bilibili.utils.FormatFaUtils;

public class SearchInfo extends BaseVideoInfo {
    public String title;
    public int bangumi_id;
    public long pubdate;

public String cover;
public int season_id;

//上面是番剧的，video的也一起在这处理
    public int aid;
    public String pic;

    @Override
    public String toString() {
        return "SearchInfo{" +
                "title='" + title + '\'' +
                ", bangumi_id=" + bangumi_id +
                ", pubdate=" + pubdate +
                ", cover='" + cover + '\'' +
                ", season_id=" + season_id +
                ", aid=" + aid +
                ", pic='" + pic + '\'' +
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

    @Override
    public String getPubDate() {
        return FormatFaUtils.stampToStr( pubdate*1000);
    }
}
