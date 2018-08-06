package wall.bilibili;

import android.app.Activity;

import android.os.Bundle;
import android.widget.ListView;

public class VideoInfoActivity extends Activity {

    private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_info);
        initData();
    }

    private void initData() {

        list = findViewById(R.id.videolist);
    }
}
