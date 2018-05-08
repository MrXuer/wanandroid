package us.xingkong.wanandroid.MVP.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import us.xingkong.wanandroid.R;
import us.xingkong.wanandroid.adapter.SearchAdapter;
import us.xingkong.wanandroid.bean.SearchBean;
import us.xingkong.wanandroid.app.Constants;
import us.xingkong.wanandroid.OnHttpListener;
import us.xingkong.wanandroid.util.HttpUtil;
import us.xingkong.wanandroid.util.UIUtil;

public class Collect extends AppCompatActivity {

    private Toolbar toolbar_collect;
    private int page = 0;
    private String parse;
    private RecyclerView recycler_collect;
    private List<SearchBean.datas> collectList = new ArrayList<>();
    private SearchAdapter collectAdapter;
    private int errorCode;
    private String errorMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        //CookieSyncManager.createInstance(this);
        initView();
        initCollect();
    }

    private void initCollect() {
        HttpUtil.parseJson(Constants.wanandroid + "lg/collect/list/" + page + "/json", "GET", new OnHttpListener() {
            @Override
            public void isSuccess(String response) {
                parse = response;
                Log.i("collect", parse);

                SearchBean searchBean = HttpUtil.parseGson(parse, SearchBean.class);
                collectList = searchBean.getData().getDatas();
                errorCode = searchBean.getErrorCode();
                errorMsg = searchBean.getErrorMsg();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (errorCode == 0) {
                            if (collectList == null) {
                                Toast.makeText(Collect.this, "什么都木有~~", Toast.LENGTH_SHORT).show();
                            } else {
                                collectAdapter = new SearchAdapter(Collect.this, collectList);
                                recycler_collect.setAdapter(collectAdapter);
                            }
                        } else {
                            Toast.makeText(Collect.this, errorMsg, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void isFail(Exception e) {
                Log.e("collect", "收藏获取失败");
            }

        });
    }

    private void initView() {
        toolbar_collect = findViewById(R.id.toolbar_collect);
        toolbar_collect.setPadding(0, UIUtil.getStatusBarHeight(Collect.this), 0, 0);
        setSupportActionBar(toolbar_collect);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recycler_collect = findViewById(R.id.recycler_collect);
        LinearLayoutManager manager = new LinearLayoutManager(Collect.this, LinearLayoutManager.VERTICAL, false);
        recycler_collect.setLayoutManager(manager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar_collect.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
