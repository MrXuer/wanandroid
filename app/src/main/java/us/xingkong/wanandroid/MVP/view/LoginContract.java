package us.xingkong.wanandroid.MVP.view;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.widget.CheckBox;

import us.xingkong.wanandroid.BasePresenter;
import us.xingkong.wanandroid.BaseView;
import us.xingkong.wanandroid.bean.LoginBean;

/**
 * @作者: Xuer
 * @包名: us.xingkong.wanandroid
 * @类名: loginInterface
 * @创建时间: 2018/3/29 23:00
 * @最后修改于:
 * @版本: 1.0
 * @描述:
 * @更新日志:
 */

public interface LoginContract extends BaseView {

    interface Presenter extends BasePresenter {
        void login();

        void setCheckBox();
    }

    interface View extends BaseView<Presenter> {
        Context getContext();

        CheckBox getCheckBox();

        AppCompatEditText getUsername();

        AppCompatEditText getPassword();

        void setVisibility(int visibility);

        void setEnable(boolean enable);

        void showMessage(String message);

        void toOtherActivity(LoginBean.data data);
    }
}
