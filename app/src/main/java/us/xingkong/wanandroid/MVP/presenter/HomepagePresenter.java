package us.xingkong.wanandroid.MVP.presenter;


import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.List;

import us.xingkong.wanandroid.MVP.model.HomepageModel;
import us.xingkong.wanandroid.MVP.view.HomepageContract;
import us.xingkong.wanandroid.R;
import us.xingkong.wanandroid.adapter.ArticleAdapter;
import us.xingkong.wanandroid.bean.ArticleBean;
import us.xingkong.wanandroid.bean.BannerBean;
import us.xingkong.wanandroid.util.UIUtil;

/**
 * @作者: Xuer
 * @包名: us.xingkong.wanandroid.presenter
 * @类名: bannerPresenter
 * @创建时间: 2018/4/3 21:06
 * @最后修改于:
 * @版本: 1.0
 * @描述:
 * @更新日志:
 */

public class HomepagePresenter implements HomepageContract.Presenter {
    private HomepageModel.BannerInterface mBannerInterface;
    private HomepageModel.ArticlesInterface mArticlesInterface;
    private HomepageContract.View mHomepageView;
    private Handler mHandler = new Handler();
    private Context mContext;
    private Banner mBanner;
    private ArticleAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<ArticleBean.datas> mList;

    public HomepagePresenter(HomepageContract.View homepageView) {
        this.mHomepageView = homepageView;
        mHomepageView.setPresenter(this);
        mBannerInterface = new HomepageModel.BannerImpl();
        mArticlesInterface = new HomepageModel.ArticlesImpl();
        mContext = mHomepageView.getContext();
        mRecyclerView = mHomepageView.getRecyclerView();
    }

    @Override
    public void refresh() {
        mAdapter.refresh();
    }

    @Override
    public void loadArticles() {
        mHomepageView.setRefreshing(true);
        mHomepageView.setEnable(true);
        mArticlesInterface.loadArticles(mHomepageView.getPage(), new HomepageModel.OnRequestListener() {
            @Override
            public void requestSuccess(List<String> titleList, List<String> imageList, List<String> urlList) {

            }

            @Override
            public void requestSuccess(final List<ArticleBean.datas> list) {
                mList = list;
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mHomepageView.setRefreshing(false);
                        mAdapter = new ArticleAdapter(mContext, mList);
                        mRecyclerView.setAdapter(mAdapter);
                        setHeaderView(mRecyclerView);
                        setFooterView(mRecyclerView);
                    }
                });
            }

            @Override
            public void requestFailure(final String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mHomepageView.setRefreshing(false);
                        mHomepageView.setEnable(false);
                        setEmptyView(mRecyclerView);
                        mAdapter.setItemClickListener(new ArticleAdapter.onItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                mHomepageView.reload();
                            }
                        });
                        mHomepageView.showMessage(errorMsg);
                    }
                });
            }
        });
    }

    @Override
    public void insertArticles() {
        mArticlesInterface.loadArticles(mHomepageView.getPage(), new HomepageModel.OnRequestListener() {
            @Override
            public void requestSuccess(List<String> titleList, List<String> imageList, List<String> urlList) {

            }

            @Override
            public void requestSuccess(final List<ArticleBean.datas> list) {
                mList = list;
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.insertArticles(mList, 22);
                        //mHomepageView.insert();
                        mAdapter.refresh();
                    }
                });
            }

            @Override
            public void requestFailure(String errorMsg) {

            }
        });
    }

    @Override
    public void setEmptyView(RecyclerView view) {
        View empty = LayoutInflater.from(mContext).inflate(R.layout.activity_recycler_empty, view, false);
        mAdapter.setEmptyView(empty);
    }

    @Override
    public void setHeaderView(RecyclerView view) {
        View header = LayoutInflater.from(mContext).inflate(R.layout.activity_recycler_header, view, false);
        mBanner = header.findViewById(R.id.banner);
        mBannerInterface.loadBanner(new HomepageModel.OnRequestListener() {
            @Override
            public void requestSuccess(final List<String> titleList, final List<String> imageList, final List<String> urlList) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                                .setImageLoader(new UIUtil.bannerLoader())
                                .setBannerTitles(titleList)
                                .setImages(imageList)
                                .setBannerAnimation(Transformer.Stack)
                                .setDelayTime(4000)
                                .isAutoPlay(true)
                                .setIndicatorGravity(BannerConfig.CENTER);
                        mBanner.setOnBannerListener(new OnBannerListener() {
                            @Override
                            public void OnBannerClick(int position) {
                                BannerBean.data data = new BannerBean.data();
                                String title = titleList.get(position);
                                String url = urlList.get(position);
                                data.setTitle(title);
                                data.setUrl(url);
                                mHomepageView.toOtherActivity(data);
                            }
                        });
                        mBanner.start();
                    }
                });
            }

            @Override
            public void requestSuccess(List<ArticleBean.datas> list) {

            }

            @Override
            public void requestFailure(final String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mHomepageView.setRefreshing(false);
                        mHomepageView.showMessage(errorMsg);
                    }
                });
            }
        });
        mAdapter.setHeaderView(header);
    }

    @Override
    public void setFooterView(RecyclerView view) {
        View footer = LayoutInflater.from(mContext).inflate(R.layout.activity_recycler_footer, view, false);
        mAdapter.setFooterView(footer);
    }

    @Override
    public void start() {
        loadArticles();
    }
}
