package wall.bilibili.utils;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import wall.bilibili.info.EpInfo;
import wall.bilibili.info.BangumiInfo;
public class B {

    //B站工具类
    public static File getDataDir(Context context,boolean isExternalDir)
    {
        if(isExternalDir)
        {

        }
        return new File(Environment.getExternalStorageDirectory(), "Android/data/tv.danmaku.bili/download");


    }
    public static  String[] getPaths(Context c)
    {

        StorageManager manager = (StorageManager)c.getSystemService(c.STORAGE_SERVICE);
        try {
            Method m = StorageManager.class.getMethod("getVolumePaths",new Class[]{});
            m.setAccessible(true);
            try {
                String [] result =(String[]) m.invoke(manager,new Object[]{});
                return result;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
    {"downloaded_bytes":678939278,"spid":0,"avid":18402853,"guessed_total_bytes":0,"type_
    tag":"lua.flv720.bili2api.64","total_bytes":678939278,
    "prefered_video_quality":64,"total_time_milli":3529120
    ,"page_data":{"has_alias":false,"offsite":"","weblink":"","rich_vid":"
    ","page":1,"link":"","tid":27,"from":"vupload","part":"P1","vid":"","cid":30037308},
    "seasion_id":0,
    "cover":"http:\/\/i0.hdslb.com\/bfs\/archive\/64f53eb846bf653cdd8877938b5ee2588e843296.jpg
    ","title":"震惊！我在B站看进击的巨人第三季","t
    ime_create_stamp":1533901319814,"danmaku_count":2531,"
    is_completed":true,"time_update_stamp":1533902094400}
     */
    //更具downloadedbytes判断
    public static File addAvCache(File down,BangumiInfo ban,EpInfo info,int prefered_quality)
    {


        File aid = new File(down,String.valueOf(info.aid)+"/1");


        File entry = new File(aid, "entry.json");
        if(entry.getParentFile().exists()==false)entry.getParentFile().mkdirs();
        try {
            JSONObject ob = new JSONObject();
            ob.put("downloaded_bytes", 0);
            ob.put("spid", 0);
            ob.put("avid", info.aid);
            ob.put("guessed_total_bytes", 0);
            ob.put("type_tag", "");
            ob.put("total_bytes", 0);
            ob.put("prefered_video_quality", prefered_quality);
            ob.put("total_time_milli", 0);
            ob.put("seasion_id", 0);
            ob.put("cover", info.cover);
            ob.put("title", ban.mediaInfo.title+" "+info.index);

            JSONObject pagedata = new JSONObject();
            pagedata.put("page",info.page);
            pagedata.put("has_alias",false);
            pagedata.put("offsite","");
            pagedata.put("weblink","");
            pagedata.put("rich_vid","");
            pagedata.put("from","vupload");
            pagedata.put("vid","");

            pagedata.put("part",info.index);
            pagedata.put("cid",info.cid);

            ob.put("page_data",pagedata);
            String data = ob.toString();
            IOUtils.writeString(entry.getAbsolutePath(), data);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }





        return null;

    }

    //伪造缓存文件
    public static File addToCache(File down,String season_id,int epid,String type,String cover,String title,int cid,int av_id,EpInfo epInfo,boolean iscomplete)
    {

        //s_xxx文件夹
        File seasonDir = new File(down,"s_"+season_id);
        if(!seasonDir.exists())seasonDir.mkdirs();


        //entry.json
        JSONObject jo = new JSONObject();
        try {
            jo.put("type_tag",type);
            jo.put("season_id",season_id);
            jo.put("cover",cover);
            jo.put("title",title);
            JSONObject source = new JSONObject();
            //ource":{"website":"bangumi","cid":3346118,"av_id":2166324}
            source.put("website","bangumi");
            source.put("cid",cid);
            source.put("av_id",av_id);
            jo.put("source",source);
            jo.put("is_completed",iscomplete);
            /*\
            {"index":"1","cover":"http:\/\/i0.hdslb.com\/bfs\/bangumi\/9418fbb4ea075dae7bfc1d2800f7bc0e63fe8794.jpg","page":1,"season_type,"is_completed":false,"time_update_stamp":1533385439000}
             */
            JSONObject  ep = new JSONObject();
            ep.put("index",epInfo.index);
            ep.put("cover",epInfo.cover);
            ep.put("page",epInfo.page);

            ep.put("episode_id",epInfo.ep_id);
            ep.put("av_id",epInfo.aid);
            ep.put("from","bangumi");
            ep.put("index_title",epInfo.index_title);
            jo.put("ep",ep);

        } catch (JSONException e) {

        }



        //下面的ep文件夹
        File epDir = new File(seasonDir,String.valueOf(epid));
        if(!epDir.exists())epDir.mkdirs();



        File entryFile = new File(epDir, "entry.json");
        IOUtils.writeString(entryFile.getAbsolutePath(),jo.toString());


        return  epDir;
        //目录
     //   File dataDir = new



        /*
        {"marlin_token":"","type_tag":"lua.flv.bb2api.80","available_perio
        d_milli":0,"parse_timestamp_milli":1533441498497,"from":"bangumi","time_length":1450000,"video_codec_id":7,"local_proxy_type":0,"is_r
        esolved":true,"player_codec_config_list":[{"player":"IJK_PLAYER","use_ijk_media_codec":false,"use_list_player":false},{"player":"ANDR
        OID_PLAYER","use_ijk_media_codec":false,"use_list_player":false}],"is_downloaded":false,"description":"高清 1080P","segment_list":[{"backup_
        urls":["http:\/\/117.161.13.68\/upgcxcode\/18\/61\/3346118\/3346118-1-80.flv?expires=1533448500&platform=android&ssig=0JTC_l0s0XbCInHsNJ6Ydg&o
        i=3746146460&nfa=7YaHycOrw0UDEXwF5P\/9zQ==&dynamic=1&hfa=2035635509&hfb=NzUxMjI5MWJlMDBjMDY0YTQxNjFjMTJiYWE0MjEwYmQ=&npcybs=1&trid=ea489abb10ed4
        06f9379a2b9e39c51f6&nfc=1","http:\/\/upos-hz-mirrorcos.acgvideo.com\/upgcxcode\/18\/61\/3346118\/3346118-1-80.flv?um_deadline=1533448698&platform
        =android&rate=1200000&oi=3746146460&um_sign=61d4b71c74565d8bd94e3cd3a3dc3ee1&gen=playurl&os=cos&trid=ea489abb10ed406f9379a2b9e39c51f6"],"vhead":"
        ","duration":1450000,"meta_url":"","bytes":123680753,"url":"http:\/\/112.13.92.198\/upgcxcode\/18\/61\/3346118\/3346118-1-80.flv?expires=153
        3448500&platform=android&ssig=0JTC_l0s0XbCInHsNJ6Ydg&oi=3746146460&nfa=7YaHycOrw0UDEXwF5P\/9zQ==&dynamic=1&hfa=2035635509&hfb=NzUxMjI5MWJlMDBj
        MDY0YTQxNjFjMTJiYWE0MjEwYmQ=&npcybs=1&trid=ea489abb10ed406f9379a2b9e39c51f6&nfc=1","ahead":""}],"user_agent":"Bilibili Freedoooooom\/MarkII","
        video_project":true,"psedo_bitrate":0,"is_stub":false}
         */





    }
}
