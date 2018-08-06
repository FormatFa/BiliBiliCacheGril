package wall.bilibili.info;

public class EpInfo {
    //章节信息
  public   int aid;
    public int cid;
    public    String cover;
    public  int duration;
    public int ep_id;
    public  int episode_status;
    public  String from;
    public  String index;
    public  String index_title;
    public int mid;
    public  int page;
    public String pub_real_time;
    public  String vid;


    @Override
    public String toString() {
        return "EpInfo{" +
                "aid=" + aid +
                ", cid=" + cid +
                ", cover='" + cover + '\'' +
                ", duration=" + duration +
                ", ep_id=" + ep_id +
                ", episode_status=" + episode_status +
                ", from='" + from + '\'' +
                ", index=" + index +
                ", index_title='" + index_title + '\'' +
                ", mid=" + mid +
                ", page=" + page +
                ", pub_real_time='" + pub_real_time + '\'' +
                ", vid=" + vid +
                '}';
    }
}
