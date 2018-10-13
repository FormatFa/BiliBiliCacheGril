package formatfa.common;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import wall.bilibili.utils.IOUtils;

public class UpdateUtils {
    private Context context;

    private String confAddress;
    String nowVersion;
    File newVersionFile;
    public UpdateUtils(Context context) {
        this.context = context;
        PackageManager pm = (PackageManager)context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(context.getPackageName(),PackageManager.GET_SIGNATURES);
            nowVersion = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        newVersionFile = new File( context.getExternalFilesDir(Environment.MEDIA_MOUNTED),"new.apk");
    }



    private void confirmDownload(String linkAndDesc)
    {

        final String[] datas = linkAndDesc.split("---");
        AlertDialog.Builder ab = new AlertDialog.Builder(context);
        ab.setTitle("有更新了");
        ab.setMessage("更新信息:"+datas[0]);
        ab.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                new downloadTask().execute(datas[1]);
            }
        }).setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();



    }


    public void install()
    {
        Intent i=new Intent(Intent.ACTION_VIEW);
        Uri u=Uri.fromFile(newVersionFile);
        //FileProvider.getUriForFile(c,"kpa.apktoolhelper.fileprovider",new File(path));



        i.setDataAndType(u,"application/vnd.android.package-archive");
        context.startActivity(i);
    }


    ProgressDialog dialog;
    class downloadTask extends AsyncTask<String ,String ,String>
    {


        @Override
        protected void onPreExecute() {

            dialog = ProgressDialog.show(context,">_<","下载中...:"+newVersionFile.getAbsolutePath());
        }

        @Override
        protected void onPostExecute(String s) {


            dialog.dismiss();
            if(s!=null)
            {
                install();

            }
            else
            {
                Toast.makeText(context,"下载失败.",Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... strings) {


            OkHttpClient client = new OkHttpClient.Builder().build();

            Request request = new Request.Builder().url(strings[0]).build();

            Call call =client.newCall(request);
            try {
                Response response = call.execute();
                InputStream is = response.body().byteStream();
                if(is==null)
                    return null;
                IOUtils.streamCopy(is,new FileOutputStream(newVersionFile));
                return "done";


            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }



        }
    }
    class checkCanUpdateTask extends AsyncTask<String,String ,String  >
    {


        @Override
        protected void onPreExecute() {

            Toast.makeText(context,"正在检查更新...",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String s) {
            if(s==null)
            {
                Toast.makeText(context,"无需更新.",Toast.LENGTH_SHORT).show();
            return;
            }
            confirmDownload(s);


        }

        @Override
        protected void onProgressUpdate(String... values) {

        }

        @Override
        protected String doInBackground(String... strings) {


            String data = IOUtils.download(confAddress);
            if(data==null)return  null;
            String [] datas = data.split("cccc");

            if(datas.length<2)return null;

            if(datas[0].equals(nowVersion))
            {
                return null;
            }
            return datas[1];



        }
    }
    public void checkUpdate(String url)
    {

        this.confAddress = url;
        new checkCanUpdateTask().execute();



    }

}
