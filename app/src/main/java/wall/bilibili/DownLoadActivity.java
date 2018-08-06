package wall.bilibili;

import android.app.ActionBar;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONException;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import wall.bilibili.Adapter.VideoAdapter;
import wall.bilibili.info.DownLoaadEntyInfo;
import wall.bilibili.ui.FDialog;
import wall.bilibili.utils.IOUtils;

public class DownLoadActivity extends Activity implements AdapterView.OnItemLongClickListener,AdapterView.OnItemClickListener {


    File downloadDir;

    File[] seasons;
    String dirname = "Android/data/tv.danmaku.bili/download";
    ListView listView;
    private List<DownLoaadEntyInfo> downloads;
    ActionBar actionBar ;

    @Override
    public boolean onNavigateUp() {
        finish();
        return super.onNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);

        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        initView();
        try {
            loadData();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initView() {

        listView = findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
    }

    private void loadData() throws PackageManager.NameNotFoundException {

        System.out.println(Environment.getDataDirectory().getAbsolutePath());
//        PackageManager pm = getPackageManager();
//        PackageInfo info = pm.getPackageInfo("tv.danmaku.bili",PackageManager.GET_ACTIVITIES);


        downloadDir = new File(Environment.getExternalStorageDirectory(),dirname);
        if(!downloadDir.exists())
        {
            FDialog.simpleDialog(this,"数据目录不存在:"+ downloadDir.getAbsolutePath());
            return;
        }
        System.out.println("data dir:"+downloadDir.getAbsolutePath());


        try {
            scanfFile();
        } catch (Exception e) {
            e.printStackTrace();
        }


        loadList();
    }

    private void loadList() {
        VideoAdapter adapter = new VideoAdapter<>(this, downloads);


        listView.setAdapter(adapter);


    }

    //扫描下载项
    private void scanfFile() throws Exception {

        downloads = new ArrayList<DownLoaadEntyInfo>();
        seasons = downloadDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().startsWith("s_");
            }
        });


        for(File season:seasons)
        {

            File[] episodes = season.listFiles();
            for(File episode:episodes)
            {
                File entry = new File(episode,"entry.json");
                if(entry.exists()==false)
                {
                    throw new Exception("entry不存在");
                }
                String data = IOUtils.readString(entry.getAbsolutePath());
               System.out.println(entry.getAbsolutePath());
                try {
                    DownLoaadEntyInfo d = DownLoaadEntyInfo.get(data);
                    System.out.println(d.toString());
                    downloads.add(d);
                } catch (JSONException e) {
                  e.printStackTrace();
                }


            }


        }


    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        FDialog.simpleDialog(this,downloads.get(position).toString());
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if(!downloads.get(position).is_completed)
        {
            FDialog.simpleDialog(this,"这个还没下载完成!");
            return;
        }

        FDialog.simpleDialog(this,"这个页面暂时只供查看》_《");

    }
}
