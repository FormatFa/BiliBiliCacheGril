package wall.bilibili.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DownLoadDialog {
    private Context context;

    HashMap<File, String> downloads;

    AlertDialog.Builder ab ;
    LinearLayout view;
    List<ProgressBar> progressBarList;
    public DownLoadDialog(Context context, HashMap<File, String> downloads) {
        this.context = context;
        this.downloads = downloads;
    }


    public void start()
    {
       ab= new AlertDialog.Builder(context);


       progressBarList = new ArrayList<ProgressBar>();


       view = new LinearLayout(context);
       view.setOrientation(LinearLayout.VERTICAL);

       for(File file:downloads.keySet())
       {
       ProgressBar p = new ProgressBar(context);
       progressBarList.add(p);
       view.addView(p);


       }
       ;
    }
}
