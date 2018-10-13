package wall.bilibili.info;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

//回复
public class RepliesInfo {

    class level_info
    {



    }

    public int getCount()
    {
        return count;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RepliesInfo.member getMember() {
        return member;
    }

    public int getFloor()
    {
        return floor;
    }
    public void setMember(RepliesInfo.member member) {
        this.member = member;
    }

    public RepliesInfo.vip getVip() {
        return vip;
    }

    public void setVip(RepliesInfo.vip vip) {
        this.vip = vip;
    }

    public RepliesInfo getParent() {
        return parent;
    }

    public void setParent(RepliesInfo parent) {
        this.parent = parent;
    }

    public void setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public class member
    {

     private String mid;
     private String uname;
        private String sex;
        private  String sign;
        private  String avatar;
        private int rank;
        private  int DisplayRank;

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public int getDisplayRank() {
            return DisplayRank;
        }

        public void setDisplayRank(int displayRank) {
            DisplayRank = displayRank;
        }
    }
    //vip信息
    public class vip
    {
    //vip类型,2为大会员，0为普通
        private int vipType;
        //过期时间，13位数
        private long vipDueDate;

        public int getVipType() {
            return vipType;
        }

        public void setVipType(int vipType) {
            this.vipType = vipType;
        }

        public long getVipDueDate() {
            return vipDueDate;
        }

        public void setVipDueDate(long vipDueDate) {
            this.vipDueDate = vipDueDate;
        }
    }

    private RepliesInfo parent;
    private JsonObject jsonObject;

    private List<RepliesInfo> replies;

    private String message;
    //楼层数
    private int floor;
    //好像是回复数，但和实际又不相等
    private int count;
    member member;
    vip vip;

    public  List<RepliesInfo> getReplies()
    {
        return replies;
    }
    public  JsonObject getJsonObject()
    {
        return jsonObject;
    }
    public RepliesInfo(RepliesInfo parent, JsonObject jsonObject) {
        this.parent = parent;
        this.jsonObject = jsonObject;

        System.out.println(parent+" " + jsonObject);
        //第一个的没有s
        if(parent!=null) {
            JsonObject memberjson = jsonObject.getAsJsonObject("member");


            member = new member();
            member.setAvatar(memberjson.get("avatar").getAsString());
            member.setDisplayRank(memberjson.get("DisplayRank").getAsInt());
            member.setMid(memberjson.get("mid").getAsString());
            member.setRank(memberjson.get("rank").getAsInt());
            member.setSex(memberjson.get("sex").getAsString());
            member.setSign(memberjson.get("sign").getAsString());
            member.setUname(memberjson.get("uname").getAsString());

            JsonObject vipjson = memberjson.getAsJsonObject("vip");
            vip = new vip();
            if(vipjson==null)
            {
                System.out.println("vip json null");
            }
            if(vipjson.get("vipDueDate")==null)
            {
                System.out.println("vip due null:"+ vipjson);

            }
            vip.setVipDueDate(vipjson.get("vipDueDate").getAsLong());
            vip.setVipType(vipjson.get("vipType").getAsInt());
            message = jsonObject.get("content").getAsJsonObject().get("message").getAsString();

            floor = jsonObject.get("floor").getAsInt();
            count = jsonObject.get("count").getAsInt();



        }
        if(jsonObject.get("replies").isJsonNull())
        {
            return;
        }
        JsonArray repliesjson = jsonObject.getAsJsonArray("replies");



        replies = new ArrayList<RepliesInfo>();
        for(int i = 0;i< repliesjson.size();i+=1)
        {
            JsonObject obj = repliesjson.get(i).getAsJsonObject();

            RepliesInfo item = new RepliesInfo(this,obj);


            replies.add(item);
        }


    }
}
