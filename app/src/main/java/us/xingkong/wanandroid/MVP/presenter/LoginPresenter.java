package us.xingkong.wanandroid.MVP.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import us.xingkong.wanandroid.MVP.model.LoginModel;
import us.xingkong.wanandroid.MVP.view.LoginContract;
import us.xingkong.wanandroid.bean.LoginBean;

/**
 * @作者: Xuer
 * @包名: us.xingkong.wanandroid
 * @类名: loginPresenter
 * @创建时间: 2018/3/29 23:40
 * @最后修改于:
 * @版本: 1.0
 * @描述:
 * @更新日志:
 */

public class LoginPresenter implements LoginContract.Presenter {
    private LoginModel.LoginInterface mLoginInterface;
    private LoginContract.View mLoginView;
    private Handler mHandler = new Handler();
    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private AppCompatEditText mUsername, mPassword;
    private CheckBox mCheckBox;
    private LoginBean.data mData;

    public LoginPresenter(LoginContract.View loginView) {
        this.mLoginView = loginView;
        mLoginView.setPresenter(this);
        mLoginInterface = new LoginModel.LoginImpl();
        mContext = mLoginView.getContext();
        mUsername = mLoginView.getUsername();
        mPassword = mLoginView.getPassword();
        mCheckBox = mLoginView.getCheckBox();
    }

    @Override
    public void login() {
        mLoginView.setEnable(false);
        mLoginView.setVisibility(View.VISIBLE);
        mLoginInterface.login(
                mLoginView.getUsername().getText().toString(),
                mLoginView.getPassword().getText().toString(),
                new LoginModel.OnRequestListener() {
                    @Override
                    public void requestSuccess(LoginBean.data data) {
                        mData = data;
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mLoginView.setEnable(true);
                                mLoginView.setVisibility(View.INVISIBLE);
                                mLoginView.showMessage("登录成功");
                                Log.i("LoginPresenter", "登录成功");
                                mLoginView.toOtherActivity(mData);
                            }
                        });
                    }

                    @Override
                    public void requestFailure(final String errorMsg) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mLoginView.setVisibility(View.INVISIBLE);
                                mLoginView.setEnable(true);
                                mLoginView.showMessage(errorMsg);
                                Log.i("LoginPresenter", "登录失败");
                            }
                        });
                    }
                });
    }

    @Override
    public void setCheckBox() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        if (mCheckBox.isChecked()) {
            editor.putBoolean("state", true);
            editor.putString("username", mData.getUsername());
            editor.putString("password", mData.getPassword());
        } else {
            editor.clear();
        }
        editor.apply();
    }

    @Override
    public void start() {
        mSharedPreferences = mContext.getSharedPreferences("data", Context.MODE_PRIVATE);
        boolean state = mSharedPreferences.getBoolean("state", false);
        if (state) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mCheckBox.setChecked(true);
                    mUsername.setText(mSharedPreferences.getString("username", ""));
                    mPassword.setText(mSharedPreferences.getString("password", ""));
                }
            });
        }
    }
}
