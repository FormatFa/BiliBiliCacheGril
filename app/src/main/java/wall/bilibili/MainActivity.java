package wall.bilibili;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.FileObserver;

import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity  {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,SearchActivity.class);
                i.putExtra("aid" , 28367490);
                startActivity(i);
            }
        });
        BObserver b = new BObserver("/storage/emulated/0/Android/data/tv.danmaku.bili/download/s_1657/30019/lua.flv.bb2api.80/index.json");
        b.startWatching();
        tv.setText(stringFromJNI());
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    class BObserver extends FileObserver
    {


        public BObserver(String path) {
            super(path);
        }

        public BObserver(String path, int mask) {
            super(path, mask);
        }

        @Override
        public void onEvent(int event, String path) {
            final int action = event & FileObserver.ALL_EVENTS;
            switch (action) {
                case FileObserver.ACCESS:
                    System.out.println("event: 文件或目录被访问, path: " + path);
                    break;

                case FileObserver.DELETE:
                    System.out.println("event: 文件或目录被删除, path: " + path);
                    break;

                case FileObserver.OPEN:
                    System.out.println("event: 文件或目录被打开, path: " + path);
                    break;

                case FileObserver.MODIFY:
                    System.out.println("event: 文件或目录被修改, path: " + path);
                    break;
            }
        }
    }
    public native String stringFromJNI();
}
