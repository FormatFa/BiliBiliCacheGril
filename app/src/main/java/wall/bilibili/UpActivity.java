package wall.bilibili;

import android.app.Activity;
import android.app.AuthenticationRequiredException;
import android.content.Intent;
import android.os.AsyncTask;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import wall.bilibili.Adapter.VideoAdapter;
import wall.bilibili.info.VideoInfo;

//up主查看

//https://space.bilibili.com/ajax/member/getSubmitVideos?mid=49713578&pagesize=100&tid=0&page=1&keyword=&order=pubdate

public class UpActivity extends Activity  implements AdapterView.OnItemClickListener{


    private int mid;


    private ListView listView;

    private String spaceUrl = "https://space.bilibili.com/ajax/member/getSubmitVideos?mid=%s&pagesize=100&tid=0&page=1&keyword=&order=pubdate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up);

        listView = findViewById(R.id.videolist);
        listView.setOnItemClickListener(this);

        loadData();


    }

    private void loadData() {

        Intent intent = getIntent();
        mid = intent.getIntExtra("mid",-1);

        if(mid == -1)
        {
            Toast.makeText(this,"请输入mid在尝试...", Toast.LENGTH_SHORT).show();
            return;
        }
        new loadVideoTask().execute(0);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        VideoInfo info = (VideoInfo) parent.getItemAtPosition(position);
        System.out.println("info:"+ info);
        Intent intent = new Intent(this,BangumiActivity.class);
        intent.putExtra("aid",info.getAid());
        startActivity(intent);
    }


    class loadVideoTask extends AsyncTask
    {

        @Override
        protected void onPreExecute() {
            setTitle("加载数据中...");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            setTitle("加载数据完成..");
            System.out.println("ooo");

        }

        @Override
        protected Object doInBackground(Object[] objects) {
            OkHttpClient client = new OkHttpClient();
            Request request = new  Request.Builder().get().url(String.format(spaceUrl,mid)).build();
            Call call = client.newCall(request);


            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {


                    final String string = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                                parseResult(string);

                        }
                    });


                }
            });


            return null;
        }
    }







    private void parseResult(String result)
    {
        setTitle("加载数据完成..");

        JSONObject json = null;
        try {
            json = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray vlist = null;
        try {
            JSONObject data = json.getJSONObject("data");
            vlist = data.getJSONArray("vlist");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        List<VideoInfo > videos = new ArrayList<VideoInfo>();
        for(int i = 0;i< vlist.length();i+=1)
        {
            try {
                JSONObject item = vlist.getJSONObject(i);
                System.out.println("get pic:"+ item.get("pic")+" title:"+item.get("title"))
                ;
                VideoInfo info = new VideoInfo();
                info.setPic(item.getString("pic"));
                info.setTitle(item.getString("title"));
                info.setMid(item.getInt("mid"));
                //av号把s
                info.setAid(item.getInt("aid"));
                info.setFavorites(item.getInt("favorites"));
                info.setLength(item.getString("length"));
                info.setAuthor(item.getString("author"));
                info.setDescription(item.getString("description"));
                videos.add(info);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        VideoAdapter adapter = new VideoAdapter(this, videos);
        listView.setAdapter(adapter);


    }


}
