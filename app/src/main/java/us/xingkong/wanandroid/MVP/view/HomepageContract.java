package us.xingkong.wanandroid.MVP.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import us.xingkong.wanandroid.BasePresenter;
import us.xingkong.wanandroid.BaseView;
import us.xingkong.wanandroid.bean.BannerBean;

/**
 * @作者: Xuer
 * @包名: us.xingkong.wanandroid.view
 * @类名: IHomepageView
 * @创建时间: 2018/4/3 21:07
 * @最后修改于:
 * @版本: 1.0
 * @描述:
 * @更新日志:
 */

public interface HomepageContract {

    interface Presenter extends BasePresenter {
        void refresh();

        void loadArticles();

        void insertArticles();

        void setEmptyView(RecyclerView view);

        void setHeaderView(RecyclerView view);

        void setFooterView(RecyclerView view);
    }

    interface View extends BaseView<Presenter>{

        int getPage();

        Context getContext();

        RecyclerView getRecyclerView();

        void setRefreshing(boolean refreshing);

        void reload();

        void setEnable(boolean enable);

        void showMessage(String message);

        void toOtherActivity(BannerBean.data data);
    }
}
