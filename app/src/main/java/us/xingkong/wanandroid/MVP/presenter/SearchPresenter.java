package us.xingkong.wanandroid.MVP.presenter;


import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import java.util.List;

import us.xingkong.wanandroid.MVP.model.SearchModel;
import us.xingkong.wanandroid.MVP.view.SearchViewContract;
import us.xingkong.wanandroid.adapter.HotkeyAdapter;
import us.xingkong.wanandroid.adapter.SearchAdapter;
import us.xingkong.wanandroid.bean.HotkeyBean;
import us.xingkong.wanandroid.bean.SearchBean;

/**
 * @作者: Xuer
 * @包名: us.xingkong.wanandroid.MVP.presenter
 * @类名: searchPresenter
 * @创建时间: 2018/4/5 20:57
 * @最后修改于:
 * @版本: 1.0
 * @描述:
 * @更新日志:
 */

public class SearchPresenter implements SearchViewContract.Presenter {
    private SearchModel.HotkeyInterface mHotkeyInterface;
    private SearchModel.SearchInterface mSearchInterface;
    private SearchViewContract.View mSearchView;
    private Handler handler = new Handler();
    private Context mContext;
    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager sManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
    private LinearLayoutManager lManager;
    private HotkeyAdapter hAdapter;
    private SearchAdapter sAdapter;

    public SearchPresenter(SearchViewContract.View searchView) {
        this.mSearchView = searchView;
        mSearchView.setPresenter(this);
        mHotkeyInterface = new SearchModel.HotkeyImpl();
        mSearchInterface = new SearchModel.SearchImpl();
        mContext = mSearchView.getContext();
    }

    @Override
    public void loadHotkey() {
        mSearchView.setVisibility(View.VISIBLE);
        mRecyclerView = mSearchView.getRecyclerView();
        mRecyclerView.setLayoutManager(sManager);
        mHotkeyInterface.loadHotkey(new SearchModel.onRequestListener() {
            @Override
            public void hotkeySuccess(final List<HotkeyBean.data> list) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        hAdapter = new HotkeyAdapter(mContext, list);
                        mRecyclerView.setAdapter(hAdapter);
                        hAdapter.setItemClickListener(new HotkeyAdapter.onItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                HotkeyBean.data name = list.get(position);
                                String query = name.getName();
                                list.clear();
                                loadSearch(query);
                            }
                        });
                    }
                });
            }

            @Override
            public void requestSuccess(List<SearchBean.datas> list) {

            }

            @Override
            public void requestFailure(final String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mSearchView.showMessage(errorMsg);
                        Log.e("hotkeyPresenter", "热词获取失败");
                    }
                });
            }
        });
    }

    @Override
    public void loadSearch(String query) {
        mSearchView.setVisibility(View.GONE);
        mRecyclerView = mSearchView.getRecyclerView();
        lManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(lManager);
        hAdapter.clear();
        mSearchInterface.loadSearch(query, mSearchView.getPage(), new SearchModel.onRequestListener() {
            @Override
            public void hotkeySuccess(List<HotkeyBean.data> list) {

            }

            @Override
            public void requestSuccess(final List<SearchBean.datas> list) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        sAdapter = new SearchAdapter(mContext, list);
                        mRecyclerView.setAdapter(sAdapter);
                        sAdapter.setItemClickListener(new SearchAdapter.onItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                /*searchBean.datas url = resultList.get(position);
                                url.setLink(url.getLink());
                                Intent intent = new Intent(search.this, webview.class);
                                intent.putExtra("url", url);
                                //startActivityForResult(intent, 2);
                                startActivity(intent);*/
                            }
                        });
                    }
                });
            }

            @Override
            public void requestFailure(String errorMsg) {
                mSearchView.showMessage(errorMsg);
                Log.e("searchPresenter", "搜索获取失败");
            }
        });
    }

    @Override
    public void start() {
        loadHotkey();
    }
}
