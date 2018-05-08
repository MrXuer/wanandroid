package us.xingkong.wanandroid.MVP.view;

import us.xingkong.wanandroid.BasePresenter;
import us.xingkong.wanandroid.BaseView;

/**
 * @作者: Xuer
 * @包名: us.xingkong.wanandroid.view
 * @类名: IRegisterView
 * @创建时间: 2018/4/3 13:36
 * @最后修改于:
 * @版本: 1.0
 * @描述:
 * @更新日志:
 */

public interface RegisterContract {

    interface Presenter extends BasePresenter {

        void register();
    }

    interface View extends BaseView<Presenter> {

        String getUserName();

        String getPassword();

        String getRepassword();

        void setVisibility(int visibility);

        void setEnable(boolean enable);

        void showMessage(String message);

        void toOtherActivity();
    }
}
