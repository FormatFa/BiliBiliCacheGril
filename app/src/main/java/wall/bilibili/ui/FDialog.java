package wall.bilibili.ui;

import android.app.AlertDialog;
import android.content.Context;

public class FDialog {

    public static void simpleDialog(Context context, String msg)
    {
        AlertDialog.Builder ab = new AlertDialog.Builder(context);
        ab.setTitle("！<+_+>");
        ab.setMessage(msg);
        ab.setPositiveButton("ok", null).show();
    }
}
