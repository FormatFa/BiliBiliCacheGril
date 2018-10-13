package wall.bilibili;
import android.os.Environment;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.Toast;
import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ShareActionProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import wall.bilibili.Adapter.VideoAdapter;
import wall.bilibili.Pop.CommentPop;
import wall.bilibili.info.BangumiInfo;
import wall.bilibili.info.SearchInfo;
import wall.bilibili.ui.FDialog;
import wall.bilibili.utils.B;

import android.net.Uri;

import com.baidu.mobstat.StatService;

public class SearchActivity extends Activity implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {



    //当前是第几页
    private int page;
    private AutoCompleteTextView text;
    String queryBack = "f1";
    String json;
    int nowPage;
    ListView list;
    List<SearchInfo> searchResult;
    private CheckBox all;
    private SharedPreferences sharedPreferences;
  public static   File storageDir;
    private String url = "\n" +
            "https://s.search.bilibili.com/main/suggest?jsoncallback=f1&func=suggest&suggest_type=accurate&sub_type=tag&main_ver=v1&highlight=&bangumi_acc_num=3&special_acc_num=0&upuser_acc_num=0&tag_num=10&term=%s";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatService.start(this);
        setContentView(R.layout.activity_search);
        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        loadPermission();
        initView();
        loadPop();
        OkHttpTest();
    }

    String permission []=  {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private void loadPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(permission[0])!= PackageManager.PERMISSION_GRANTED)
            {
                AlertDialog.Builder ab = new AlertDialog.Builder(this);
                ab.setMessage("为了能够写入文件，软件必须要获得存储权限，请您在确定后授权").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(permission,10);
                        }
                    }
                }).setNegativeButton("取消",null).show();
            }
        }


    }
    String tag = "BiliBiliDebug";
    private void OkHttpTest() {
        Log.e(tag,"start okhttp:");
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request.Builder reb = new Request.Builder();
        reb.get().url("http://cn-jxjj-dx-v-11.acgvideo.com/upgcxcode/23/26/4672623/4672623-1-80.flv?expires=1538977800&platform=pc&ssig=tgzQngNQL2zwP-GJ6LqAOA&oi=2006120407&nfa=uTIiNt+AQjcYULykM2EttA==&dynamic=1&hfa=2039085003&hfb=Yjk5ZmZjM2M1YzY4ZjAwYTMzMTIzYmIyNWY4ODJkNWI=&trid=5dfbfb91fb944ab99f8567a70774bb5f&nfc=1");



        reb.addHeader("User-Agent","Mozilla/5.0 (Linux; Android 4.4.4; en-us; Nexus 5 Build/JOP40D) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2307.2 Mobile Safari/537.36");
        reb.addHeader("Referer","https://www.bilibili.com/bangumi/play/ep72065");

        Request re = reb.build();


        Call call =client.newCall(re);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


                Log.e(tag, "fail :"+ e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.e(tag, "request success"+response.code()+" "+response.toString());

            }
        });









    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,  int[] grantResults) {

        if(grantResults[0]!=PackageManager.PERMISSION_GRANTED)
        {
            FDialog.simpleDialog(this,"没有写入权限..");
        }
        else
        {
            FDialog.simpleDialog(this,"申请写入权限成功..");
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //票进来的效果

    private SharedPreferences sp;
    private String[] searchHistory = new String[10];
    //当前是哪个搜索
    private int historyIndex = 0;
    private void loadPop() {


        for(int i = 0;i< searchHistory.length;i+=1)
        {
            searchHistory[i] = "";
        }
        String[] temp = sp.getString("history","").split("\\|\\|");
        for(int i = 0 ;i< 10;i+=1)
        {
            if(i>=temp.length)break;
            searchHistory[i] = temp[i];
        }
        historyIndex = sp.getInt("historyIndex", 1 );


        if(searchHistory[0].isEmpty())
        {
            //searchHistory[1]= "av18652954";
        searchHistory[0] = "进击的";}
    }

    ActionBar actionBar;

    @Override
    public boolean onNavigateUp() {
//        finish();
        exit();
        return super.onNavigateUp();
    }

    private void initView() {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        storageDir = new File(sharedPreferences.getString("path", Environment.getExternalStorageDirectory().getAbsolutePath()));


        all = findViewById(R.id.all);
        list = findViewById(R.id.list);
        text = findViewById(R.id.text);
        list.setOnItemClickListener(this);
        list.setOnItemLongClickListener(this);
        text.setThreshold(0);
        text.setAdapter(new ArrayAdapter(this,android.R.layout.simple_list_item_1,android.R.id.text1,new String[]{"FormatFa","Apktool","AndroidKill","Flying","MY"}));
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                System.out.println("on text change:"+s);

            }

            @Override
            public void afterTextChanged(Editable s) {

             //   querySuggest();
            }
        });

    }


    //时间搓，请求时的
    long timpstamp;
    private void querySuggest()
    {
        timpstamp = System.currentTimeMillis();
        String input = text.getText().toString();

        OkHttpClient client= new OkHttpClient();

        Request re = new Request.Builder().url(String.format(url,text.getText().toString())).build();
        Call call = client.newCall(re);


        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                 String result = response.body().string();
                System.out.println(result);


                json = result.substring(queryBack.length()+1,result.length()-1);

                System.out.println(json);
                try {
                    JSONObject object = new JSONObject(json);

                    JSONArray res = object.getJSONArray("result");
                    for(int i = 0;i<res.length();i+=1)
                        System.out.println(res.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                     //   text.setText(result);
                    }
                });

            }
        });



    }

    public void nextpage(View view)
    {
        if(searchResult==null)
        {
            return;
        }
        page +=1;

        new SearchTask().execute("");

    }
    public void recent(View view)
    {
        //CommentPop pop= new CommentPop(this);
     //   pop.show(text);
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setItems(searchHistory, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                text.setText(searchHistory[which]);

            }
        }).setNegativeButton("取消",null).show();
    }

    public void search(View view)
    {
        String te = text.getText().toString();
        if(te.startsWith("av"))
        {
            Intent intent = new Intent(this,BangumiActivity.class);
            intent.putExtra("aid",te.substring(2));
            startActivity(intent);
            return;
        }
        searchResult = new ArrayList<SearchInfo>();
        //页数要崇xin
        page=1;
        Toast.makeText(this,"开始搜索...",Toast.LENGTH_SHORT).show();
        new SearchTask().execute("");


        for(String s:searchHistory)
        {
            if(te.equals(s))return;
        }

        searchHistory[historyIndex] = text.getText().toString();
        StringBuilder sb = new StringBuilder();
        sb.append(searchHistory[0]);
        for(int i = 1;i< searchHistory.length;i+=1)
        {
            sb.append("||");
            sb.append(searchHistory[i]);
        }
        historyIndex+=1;
        if(historyIndex>=10)historyIndex=0;
        sp.edit().putInt("historyIndex", historyIndex).putString("history",sb.toString()).commit();



    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(this, BangumiActivity.class);
        if(all.isChecked())
        {
            intent.putExtra("aid",""+ searchResult.get(position).aid);
        }
        else
        intent.putExtra("ss", searchResult.get(position).season_id);
        startActivity(intent);


    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        FDialog.simpleDialog(this, searchResult.get(position).toString());
        return true;
    }
    //"{"keyword":"%E8%87%AA%E7%94%B1%E4%B9%8B%E7%BF%BC","page":1,"pagesize":20,"platform":"h5","search_type":"bangumi","main_ver":"v3","order":"totalrank}
    //https://m.bilibili.com/search/searchengine

    //点下一页前的位置
    int preoffset = 0;
    class SearchTask extends AsyncTask<String , Object , String>
    {

        //不要seachresulu.isze是因为下一页的判断不了
        int searchlen = 0;

        @Override
        protected void onPostExecute(String s) {

            if(s!=null)
            {
               // FDialog.simpleDialog(SearchActivity.this,"error:"+s);
            }
            if(searchlen==0)
            {
                Toast.makeText(SearchActivity.this,"没有搜索到:"+s,Toast.LENGTH_LONG ).show();
                return;
            }
            findViewById(R.id.nextpage).setVisibility(View.VISIBLE);
            VideoAdapter adapter = new VideoAdapter(SearchActivity.this, searchResult);
            list.setAdapter(adapter);
            list.setSelection(preoffset);

        }

        @Override
        protected void onPreExecute() {
            preoffset = searchResult.size();
            try {
                keyword = URLEncoder.encode(text.getText().toString(), "UTF-8");
            }
            catch(Exception e)
            {

            }
        }

        String keyword;
        @Override
        protected String doInBackground(String... strings) {


            MediaType type = MediaType.parse("application/json");
            JSONObject para = new JSONObject();
            try {
                if(all.isChecked())
                {
                    para.put("search_type","all");
                }
                else
                para.put("search_type","bangumi");
                 para.put("page",page);
                 para.put("pagesize",20);
                 para.put("platform","h5");
                 para.put("main_ver","v3");
            //    para.put("order","totalrank");
                para.put("bangumi_num",3);
                para.put("movie_num",3);
                 para.put("keyword", URLEncoder.encode(text.getText().toString(),"UTF-8"));
                 System.out.println("encode key worl:"+ URLEncoder.encode(text.getText().toString(),"UTF-8"));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            OkHttpClient client = new OkHttpClient();

            System.out.println("test"+para.toString());
            Request re = new Request.Builder().url("https://m.bilibili.com/search/searchengine").addHeader("Referer","https://m.bilibili.com/search.html?keyword="+keyword).post( RequestBody.create(type,para.toString())).build();

            Call call = client.newCall(re);
            try {
                Response response = call.execute();
                String result = response.body().string();

                //番剧
                if(!all.isChecked()) {
                    JSONObject object = new JSONObject(result);
                    JSONArray ja = object.getJSONArray("result");
                    for (int i = 0; i < ja.length(); i += 1) {
                        JSONObject item = ja.getJSONObject(i);
                        SearchInfo info = new SearchInfo();
                        info.bangumi_id = item.getInt("bangumi_id");
                        info.cover = item.getString("cover");
                        info.pubdate = item.getLong("pubdate");
                        info.season_id = item.getInt("season_id");
                        info.title = item.getString("title");
                        searchResult.add(info);
                        searchlen+=1;
                    }

                }
                else
                {
                    JSONObject object = new JSONObject(result);
                    System.out.println(object.toString());
                    JSONArray ja = object.getJSONObject("result").getJSONArray("video");
                    for (int i = 0; i < ja.length(); i += 1) {
                        JSONObject item = ja.getJSONObject(i);
                        SearchInfo info = new SearchInfo();
                        info.aid= item.getInt("aid");
                        info.cover = item.getString("pic");
                        info.pubdate = item.getLong("pubdate");

                        info.title = item.getString("title");
                        searchResult.add(info);
                        searchlen+=1;
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } catch (JSONException e) {
                e.printStackTrace();
                return e.toString();
            }


            return null;
        }
    }

    @Override
    public void onBackPressed() {
        exit();
    }

    void switchPaht()
    {
        final String[] path = B.getPaths(this);
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle(">_<");
        int p = 0;
        String nowpath = storageDir.getAbsolutePath();
        for(int i = 0 ;i< path.length;i+=1)
        {
            if(nowpath.equals(path[i]))
            {
                p=i;break;
            }
        }
        ab.setSingleChoiceItems(path, p, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                File choosed = new File(path[which], "Android/data/tv.danmaku.bili/download");

                if(choosed.canWrite()==false)
                {
                    FDialog.simpleDialog(SearchActivity.this,choosed.getAbsolutePath()+" 不可写入！")
                    ;
                    return;
                }
                storageDir = choosed ;

                sp.edit().putString("path",storageDir.getAbsolutePath()).commit();

                FDialog.simpleDialog(SearchActivity.this,choosed.getAbsolutePath()+" 切换成功,这个目录要和BiliBili的设置里的缓存目录对应");
            }
        }).setPositiveButton("不懂?点击我取消",null).show();




    }
    void exit()
    {

        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle(">_<");
        ab.setMessage("是否退出？");
        ab.setPositiveButton("取消",null).setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              android.os.Process.killProcess(android.os.Process.myPid());
            }
        }).show();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.about)
        {

            AlertDialog.Builder ab = new AlertDialog.Builder(this);
            ab.setMessage("版本1.3 \n自动检测到您的视频缓存地址为:"+ storageDir.getAbsolutePath()+"\n\nB站视频缓存工具\n\n这版本增加查看评论\n\n添加后，请重启哔哩哔哩（任务栏划掉它，再不行重启手机）\n\n" +
                    "只支持番剧和电视剧解析，不支持单个视频\n\n可以选择缓存到B站或者本地\n1.缓存到b站就是在B战的下载管理可以看到，可以看弹幕\n\n2.缓存到本地，就是本地自定义的目录\n\n下版本待更新：提取缓存视频");

            ab.setPositiveButton("切换目录", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
switchPaht();
                }
            });
            ab.setNegativeButton("取消",null).show();

        }
        else if(item.getItemId()==R.id.setting)
        {
            Intent intent = new Intent(this,SettingActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.download)
        {
            Intent intent = new Intent(this,DownLoadActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.update)
        {
            Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://api.formatfa.top/bilibili/index.php?ver=3"));
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
