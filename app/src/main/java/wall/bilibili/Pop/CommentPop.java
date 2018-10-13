package wall.bilibili.Pop;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import wall.bilibili.Adapter.CommentAdapter;
import wall.bilibili.R;
import wall.bilibili.info.RepliesInfo;
import wall.bilibili.utils.IOUtils;

//查看评论View
public class CommentPop  implements View.OnClickListener,AdapterView.OnItemClickListener {


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        RepliesInfo info = (RepliesInfo) parent.getItemAtPosition(position);
        this.info = info;
        if(info==null||info.getReplies()==null)
        {
            Toast.makeText(context,"没有更多了",Toast.LENGTH_SHORT).show();
            return;
        }
        preClick = position;
        setAdapterData();
    }

    class loadCommentTask extends AsyncTask<Object,Object,Object>
    {

        public loadCommentTask() {
            super();
        }

        @Override
        protected void onPreExecute() {

            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Object o) {
            progress.setVisibility(View.INVISIBLE);
            info = new RepliesInfo(null, ((JsonObject)o).get("data").getAsJsonObject());
            setAdapterData();
      //      nextPage.setText(String.format("第%d页",pagenum+1));
      //      prePage.setText(String.format("第%d页",pagenum-1));


        }

        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(Object o) {
            super.onCancelled(o);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Object doInBackground(Object... objects) {

            OkHttpClient client = new OkHttpClient();

            String url = String.format("https://api.bilibili.com/x/v2/reply?jsonp=jsonp&type=1&sort=1&oid=%d&pn=%d&nohot=1",aid,pagenum);

            Request re = new Request.Builder().get().url(url).build();

            Call call = client.newCall(re);
            try {
                Response response =  call.execute();
                String json = response.body().string();
                System.out.println("get comment:"+json);
                JsonObject object = new JsonParser().parse(json).getAsJsonObject();

                replyCount = object.getAsJsonObject("data").getAsJsonObject("page").get("count").getAsInt();
                return object;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    private Context context;

    private int aid;

    PopupWindow window;
    private ListView list;
    private Button prePage,nextPage;
    RepliesInfo info;

    private ProgressBar progress;

    //页数等
    private TextView textinfo;
    //第一次json的data.page.count
    private int replyCount;

    int pagenum =1 ;

    //点击<返回后回到原来的位置
    private int preClick;


    //直接静态，方便
    public static boolean isShowAvatar;
    public CommentPop(Context context) {
        this.context = context;

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
        isShowAvatar = sh.getBoolean("loadhead",true);



        loadView();
      //  loadData();

    }

    private void loadData() {
     //   String json = IOUtils.readString("/sdcard/test.json");
    //    System.out.println("json"+json);
  //     info = new RepliesInfo(null, new JsonParser().parse(json).getAsJsonObject().get("data").getAsJsonObject());

    //   setAdapterData();

     //   aid=26399324;
        new loadCommentTask().execute(0);
    }
    private void setAdapterData()
    {
        if(info.getReplies().size()==0)
        {
       //     Toast.makeText(context,"没有评论了",Toast.LENGTH_SHORT).show();
            return;
        }
        CommentAdapter adapter = new CommentAdapter(context,info);


        list.setAdapter(adapter);
        textinfo.setText(String.format("评论数(%d),当前页数(%d)", replyCount,pagenum));


    }

    private void loadView() {
        View view = LayoutInflater.from(context).inflate(R.layout.popu_comment,null);

        window = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        window.setTouchable(true);
        window.setFocusable(true);

        list = view.findViewById(R.id.poplist);
        textinfo = view.findViewById(R.id.info);
        progress = view.findViewById(R.id.progress);
       nextPage =  view.findViewById(R.id.nextpage);
       nextPage.setOnClickListener(this);

       prePage =  view.findViewById(R.id.prepage);
       prePage.setOnClickListener(this);
       view.findViewById(R.id.back).setOnClickListener(this);

       list.setOnItemClickListener(this);
        ColorDrawable drawable = new ColorDrawable(Color.argb(225,0x49,0x45,0x45));

        window.setBackgroundDrawable(drawable);
    }


    public void setAid(int aid)
    {
        preClick=0;
        pagenum=1;
        this.aid = aid;
        loadData();

    }
    public void show(View view)
    {

        window.showAtLocation(view, Gravity.CENTER,0,0);


    }


    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.nextpage:
                pagenum+=1;
                loadData();
                break;
            case R.id.prepage:
                break;
            case R.id.back:
                if(info==null)
                {
                    window.dismiss();
                    return;
                }
                if(info.getParent()==null)
                {

                    window.dismiss();
                }
                else
                {
                    info = info.getParent();
                    setAdapterData();
//                    list.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            list.smoothScrollToPosition(preClick);
//                        }
//                    });

                    list.setSelection(preClick);

                }

                break;


        }
    }
}
