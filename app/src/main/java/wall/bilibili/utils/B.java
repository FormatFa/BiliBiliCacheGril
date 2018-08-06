package wall.bilibili.utils;

import android.os.Environment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import wall.bilibili.info.EpInfo;

public class B {

    //B站工具类
    public static File getDataDir()
    {
        return new File(Environment.getExternalStorageDirectory(), "Android/data/tv.danmaku.bili/download");


    }

    //伪造缓存文件
    public static File addToCache(String season_id,int epid,String type,String cover,String title,int cid,int av_id,EpInfo epInfo)
    {

        File down = getDataDir();
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
            jo.put("is_completed",true);
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
