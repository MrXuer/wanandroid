package us.xingkong.wanandroid.MVP.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import us.xingkong.wanandroid.BasePresenter;
import us.xingkong.wanandroid.BaseView;
import us.xingkong.wanandroid.bean.HotkeyBean;

/**
 * @作者: Xuer
 * @包名: us.xingkong.wanandroid.MVP.view
 * @类名: ISearchView
 * @创建时间: 2018/4/5 21:00
 * @最后修改于:
 * @版本: 1.0
 * @描述:
 * @更新日志:
 */

public interface SearchViewContract {

    interface Presenter extends BasePresenter {

        void loadHotkey();

        void loadSearch(String query);
    }

    interface View extends BaseView<Presenter>{

        String getSearch();

        int getPage();

        Context getContext();

        RecyclerView getRecyclerView();

        void setVisibility(int visibility);

        void showMessage(String message);

        void toOtherActivity(HotkeyBean.data data);
    }
}
