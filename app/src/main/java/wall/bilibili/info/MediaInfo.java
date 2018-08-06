package wall.bilibili.info;

import java.util.List;

public class MediaInfo {

public String actiors;
public String chn_name;
public String cover;



public int media_id;
public String origin_name;
public String title;
public String type_name;
public String evaluate;
    @Override
    public String toString() {
        return "MediaInfo{" +
                "actiors='" + actiors + '\'' +
                ", chn_name='" + chn_name + '\'' +
                ", cover='" + cover + '\'' +

                ", media_id=" + media_id +
                ", origin_name='" + origin_name + '\'' +
                ", title='" + title + '\'' +
                ", type_name='" + type_name + '\'' +
                '}';
    }
}
