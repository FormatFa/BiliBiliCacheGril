package wall.bilibili.info;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;

public class DownLoaadEntyInfo   extends BaseVideoInfo {


    public static DownLoaadEntyInfo get(String json) throws Exception {
        System.out.println("json:"+json);
        JsonObject object =(JsonObject) new JsonParser().parse(json);

        DownLoaadEntyInfo d = new DownLoaadEntyInfo();
        d.cover = object.get("cover").getAsString();


            JsonElement temp;
          temp =  object.get("danmaku_count");
          if(temp!=null)
        d.danmaku_count =temp.getAsInt();

          try {
              d.downloaded_bytes = object.get("downloaded_bytes").getAsLong();
              d.guessed_total_bytes = object.get("guessed_total_bytes").getAsLong();
              d.prefered_video_quality = object.get("prefered_video_quality").getAsInt();
              d.total_bytes = object.get("total_bytes").getAsLong();
          }
          catch (Exception e)
          {

          }
            JsonObject fep = object.get("ep").getAsJsonObject();

            DownLoaadEntyInfo.Ep ep = new DownLoaadEntyInfo.Ep();
            ep.av_id = fep.get("av_id").getAsInt();
            ep.cover = fep.get("cover").getAsString();
            try {
                ep.danmaku = fep.get("danmaku").getAsInt();
                ep.season_type = fep.get("season_type").getAsInt();
            }catch (Exception e)
            {

            }
            ep.episode_id = fep.get("episode_id").getAsInt();
            ep.from = fep.get("from").getAsString();
            ep.index = fep.get("index").getAsInt();
            ep.index_title = fep.get("index_title").getAsString();
            System.out.println("get index:"+ep.index_title);
            ep.page = fep.get("page").getAsInt();



            d.ep = ep;


            d.is_completed = object.get("is_completed").getAsBoolean();

            d.season_id = object.get("season_id").getAsString();
            d.title = object.get("title").getAsString();



        Source s = new Source();
        JsonObject js = object.get("source").getAsJsonObject();
        s.av_id = js.get("av_id").getAsInt();
        s.cid = js.get("cid").getAsInt();
        s.website = js.get("website").getAsString();
        d.source = s;
        return d;
    }
    @Override
    public String getTitle() {

        if(ep==null)return title;
        return title+" _ "+ ep==null?"": ep.index_title;
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
    public String toString() {
        return "DownLoaadEntyInfo{" +
                "downloaded_bytes=" + downloaded_bytes +
                ", guessed_total_bytes=" + guessed_total_bytes +
                ", total_bytes=" + total_bytes +
                ", prefered_video_quality=" + prefered_video_quality +
                ", season_id='" + season_id + '\'' +
                ", total_time_milli=" + total_time_milli +
                ", cover='" + cover + '\'' +
                ", title='" + title + '\'' +
                ", time_create_stamp=" + time_create_stamp +
                ", source=" + source +
                ", danmaku_count=" + danmaku_count +
                ", ep=" + ep +
                ", is_completed=" + is_completed +
                ", time_update_stamp=" + time_update_stamp +
                '}';
    }

    public  static  class Source
    {

        @Override
        public String toString() {
            return "Source{" +
                    "website='" + website + '\'' +
                    ", cid=" + cid +
                    ", av_id=" + av_id +
                    '}';
        }

        public String website;
        public long cid;
        public int av_id;
    }
    public static class Ep{
        @Override
        public String toString() {
            return "Ep{" +
                    "index=" + index +
                    ", cover='" + cover + '\'' +
                    ", page=" + page +
                    ", season_type=" + season_type +
                    ", episode_id=" + episode_id +
                    ", av_id=" + av_id +
                    ", from='" + from + '\'' +
                    ", danmaku=" + danmaku +
                    ", index_title='" + index_title + '\'' +
                    '}';
        }

        public int index;
            public String cover;
            public int page;
            public int season_type;
            public int episode_id;
            public int av_id;
            public String from;
            public int danmaku;
            public String index_title;


}
    //下载目录下的entry.json
    public long downloaded_bytes;
    public long guessed_total_bytes;
    public long total_bytes;
    public int prefered_video_quality;
    public String season_id;
    public long total_time_milli;
    public String cover;
    public String title;
    public long time_create_stamp;

    public Source source;
    int danmaku_count;
    public Ep ep;
    public boolean is_completed;
    public long time_update_stamp;
}
