package us.xingkong.wanandroid.MVP.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

import us.xingkong.wanandroid.MVP.presenter.HomepagePresenter;
import us.xingkong.wanandroid.R;
import us.xingkong.wanandroid.layout.Webview;
import us.xingkong.wanandroid.bean.BannerBean;
import us.xingkong.wanandroid.bean.LoginBean;


/**
 * @作者: Xuer
 * @包名: us.xingkong.wanandroid
 * @类名: homePage
 * @创建时间: 2018/3/26 20:12
 * @最后修改于:
 * @版本: 1.0
 * @描述: 玩安卓首页，支持banner，跳转到搜索
 * @更新日志: 目前存在的问题：
 * (1) cookie
 * (3) 对19以下的布局适配
 * (4) 搜索栏没居中有点歪
 * (7) 输入框和checkbox的样式
 */

public class Homepage extends AppCompatActivity implements HomepageContract.View {

    private Toolbar mToolbar;
    private SwipeRefreshLayout mRefresh;
    private long mExitTime;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawer;
    private LinearLayoutManager mManager;
    private RecyclerView mRecyclerView;
    private HomepageContract.Presenter mPresenter;
    private int page = 0;
    private boolean refresh ;
    private int lastVisibleItemPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        initView();
        initNavigation();
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mManager);
        new HomepagePresenter(this);
        //mPresenter.start();
        //mPresenter.loadArticles();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mNavigationView = findViewById(R.id.navigation);
        mNavigationView.setItemIconTintList(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        mDrawer = findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(Homepage.this, mDrawer, mToolbar, R.string.open, R.string.close);
        toggle.syncState();
        mDrawer.addDrawerListener(toggle);
        mRefresh = findViewById(R.id.refresh);
        mRefresh.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        mRecyclerView = findViewById(R.id.recyclerView);
    }

    private void initNavigation() {
        LoginBean.data data = (LoginBean.data) getIntent().getSerializableExtra("data");
        View headerView = mNavigationView.getHeaderView(0);

        TextView name = headerView.findViewById(R.id.navi_username);
        name.setText(data.getUsername());

        TextView email = headerView.findViewById(R.id.navi_email);
        if (data.getEmail().equals("")) {
            email.setText("未绑定邮箱");
        } else {
            email.setText(data.getEmail());
        }

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navi_collect:
                        Intent intent = new Intent(Homepage.this, Collect.class);
                        startActivity(intent);
                        break;
                    case R.id.navi_logout:
                        Intent intent2 = new Intent(Homepage.this, Login.class);
                        Toast.makeText(Homepage.this, "退出成功", Toast.LENGTH_SHORT).show();
                        startActivity(intent2);
                        finish();
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.search:
                        Intent intent = new Intent(Homepage.this, Search.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
        return true;
    }

    @Override
    public int getPage() {
        /*mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
                int visibleItemCount = manager.getChildCount();
                int totalItemCount = manager.getItemCount();
                if (visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Log.i("bottom", "true我到底了");
                    page++;
                    mPresenter.insertArticles();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });*/
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                int visibleItemCount = mManager.getChildCount();
                int totalItemCount = mManager.getItemCount();
                if (visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Log.i("bottom", "true我到底了");
                    page++;
                    mPresenter.insertArticles();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = mManager.findLastVisibleItemPosition();
            }
        });
        Log.i("page", page + "");
        return page;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        refresh = refreshing;
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefresh.setRefreshing(refresh);
                mPresenter.loadArticles();
            }
        });
    }

    @Override
    public void reload() {
        mPresenter.loadArticles();
        //presenter.mRefresh();
    }

    @Override
    public void setEnable(boolean enable) {
        mRefresh.setEnabled(enable);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toOtherActivity(BannerBean.data data) {
        Intent intent = new Intent(Homepage.this, Webview.class);
        intent.putExtra("url", data);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mExitTime > 2000) {
                Toast.makeText(Homepage.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /*@Override
    protected void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }*/

    public void getBitmapsFromVideo() {
        String path = Environment.getExternalStorageDirectory() + "/bad_apple.mkv";
        Log.i("video", "路径没问题");
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(path);
        String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        int seconds = Integer.valueOf(time) / 1000;
        for (int i = 1; i < seconds; i++) {
            Bitmap bitmap = retriever.getFrameAtTime(i * 1000 * 1000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
            String map = Environment.getExternalStorageDirectory() + File.separator + i + ".jpg";
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(map);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 60, fos);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setPresenter(HomepageContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}


