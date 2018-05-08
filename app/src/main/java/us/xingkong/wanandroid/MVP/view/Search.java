package us.xingkong.wanandroid.MVP.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import us.xingkong.wanandroid.MVP.presenter.SearchPresenter;
import us.xingkong.wanandroid.R;
import us.xingkong.wanandroid.bean.HotkeyBean;

public class Search extends AppCompatActivity implements SearchViewContract.View {

    private Toolbar mToolbar;
    private SearchView mSearchView;
    private RecyclerView mRecyclerView;
    private TextView mHotSearch;
    private SearchViewContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        new SearchPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    private void initView() {
        mHotSearch = findViewById(R.id.hotSearch);
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_search_menu, menu);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        MenuItem item = menu.findItem(R.id.query);
        mSearchView = (SearchView) MenuItemCompat.getActionView(item);
        mSearchView.setIconified(false); //一开始处于展开状态
        mSearchView.onActionViewExpanded(); // 当展开无输入内容的时候，没有关闭的图标
        mSearchView.setIconifiedByDefault(false); //默认为true在框内，设置false则在框外
        mSearchView.setSubmitButtonEnabled(true); //显示提交按钮
        mSearchView.setQueryHint("请输入搜索内容");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPresenter.loadSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public String getSearch() {
        return null;
    }

    @Override
    public int getPage() {
        return 0;
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
    public void setVisibility(int visibility) {
        mHotSearch.setVisibility(visibility);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toOtherActivity(HotkeyBean.data data) {

    }

    @Override
    public void setPresenter(SearchViewContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
