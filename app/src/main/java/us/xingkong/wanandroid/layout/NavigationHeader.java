package us.xingkong.wanandroid.layout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import us.xingkong.wanandroid.R;
import us.xingkong.wanandroid.util.UIUtil;

public class NavigationHeader extends AppCompatActivity {

    private LinearLayout navi_heaader;
    private TextView navi_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi_header);

        navi_heaader = findViewById(R.id.navi_header);
        navi_heaader.setPadding(15, UIUtil.getStatusBarHeight(NavigationHeader.this), 15, 15);

        navi_username = findViewById(R.id.navi_username);
        navi_username.setText("健康行动");
    }
}
