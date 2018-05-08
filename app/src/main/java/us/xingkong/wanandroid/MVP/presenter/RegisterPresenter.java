package us.xingkong.wanandroid.MVP.presenter;


import android.os.Handler;
import android.util.Log;
import android.view.View;

import us.xingkong.wanandroid.MVP.model.LoginModel;
import us.xingkong.wanandroid.MVP.model.RegisterModel;
import us.xingkong.wanandroid.MVP.view.RegisterContract;
import us.xingkong.wanandroid.bean.LoginBean;

/**
 * @作者: Xuer
 * @包名: us.xingkong.wanandroid.presenter
 * @类名: registerPresenter
 * @创建时间: 2018/4/3 13:36
 * @最后修改于:
 * @版本: 1.0
 * @描述:
 * @更新日志:
 */

public class RegisterPresenter implements RegisterContract.Presenter {
    private RegisterModel.RegisterInterface mRegisterInterface;
    private RegisterContract.View mRegisterView;
    private Handler mHandler = new Handler();

    public RegisterPresenter(RegisterContract.View registerView) {
        this.mRegisterView = registerView;
        mRegisterView.setPresenter(this);
        mRegisterInterface = new RegisterModel.RegisterImpl();
    }

    @Override
    public void register() {
        mRegisterView.setEnable(false);
        mRegisterView.setVisibility(View.VISIBLE);
        mRegisterInterface.register(
                mRegisterView.getUserName(),
                mRegisterView.getPassword(),
                mRegisterView.getRepassword(),
                new LoginModel.OnRequestListener() {
                    @Override
                    public void requestSuccess(LoginBean.data data) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mRegisterView.setVisibility(View.INVISIBLE);
                                mRegisterView.setEnable(true);
                                mRegisterView.showMessage("注册成功");
                                Log.i("RegisterPresenter", "注册成功");
                                mRegisterView.toOtherActivity();
                            }
                        });
                    }

                    @Override
                    public void requestFailure(final String errorMsg) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mRegisterView.setVisibility(View.INVISIBLE);
                                mRegisterView.setEnable(true);
                                mRegisterView.showMessage(errorMsg);
                                Log.i("RegisterPresenter", "注册失败");
                            }
                        });
                    }
                });
    }

    @Override
    public void start() {

    }
}
