package us.xingkong.wanandroid.MVP.model;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;
import us.xingkong.wanandroid.app.Constants;
import us.xingkong.wanandroid.bean.LoginBean;
import us.xingkong.wanandroid.util.HttpUtil;
import us.xingkong.wanandroid.util.OkUtils;

/**
 * @作者: Xuer
 * @包名: us.xingkong.wanandroid
 * @类名: loginModel
 * @创建时间: 2018/3/29 23:50
 * @最后修改于:
 * @版本: 1.0
 * @描述:
 * @更新日志:
 */


public class LoginModel {

    public interface OnRequestListener {
        //登录成功
        void requestSuccess(LoginBean.data data);

        //登录失败
        void requestFailure(String errorMsg);
    }

    public interface LoginInterface {

        void login(String username, String password, OnRequestListener listener);
    }

    public static class LoginImpl implements LoginInterface {

        @Override
        public void login(final String username, final String password, final OnRequestListener listener) {
            HashMap<String, String> params = new HashMap<>();
            params.put("username", username);
            params.put("password", password);
            OkUtils.post(Constants.login, params, new OkUtils.DataCallBack() {
                @Override
                public void onSuccess(String result) throws Exception {
                    Log.i("LoginModel", result);
                    LoginBean bean = HttpUtil.parseGson(result, LoginBean.class);
                    int errorCode = bean.getErrorCode();
                    String errorMsg = bean.getErrorMsg();
                    String email = bean.getData().getEmail();
                    if (errorCode == 0) {
                        LoginBean.data data = new LoginBean.data();
                        data.setUsername(username);
                        data.setPassword(password);
                        data.setEmail(email);
                        listener.requestSuccess(data);
                    } else {
                        listener.requestFailure(errorMsg);
                    }
                }

                @Override
                public void onFailure(Request request, IOException e) {
                    e.printStackTrace();
                    Log.i("LoginModel", "登录失败");
                    listener.requestFailure("网络连接错误，无法登陆");
                }
            });
        }
    }
}
