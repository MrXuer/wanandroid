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
 * @包名: us.xingkong.wanandroid.model
 * @类名: registerModel
 * @创建时间: 2018/4/3 13:27
 * @最后修改于:
 * @版本: 1.0
 * @描述:
 * @更新日志:
 */

public class RegisterModel {

    public interface RegisterInterface {

        void register(String username, String password, String repassword, LoginModel.OnRequestListener listener);
    }

    public static class RegisterImpl implements RegisterInterface {

        @Override
        public void register(final String username, final String password, String repassword, final LoginModel.OnRequestListener listener) {
            HashMap<String, String> params = new HashMap<>();
            params.put("username", username);
            params.put("password", password);
            params.put("repassword", repassword);
            OkUtils.post(Constants.register, params, new OkUtils.DataCallBack() {
                @Override
                public void onSuccess(String result) throws Exception {
                    Log.i("RegisterModel", result);
                    LoginBean bean = HttpUtil.parseGson(result, LoginBean.class);
                    int errorCode = bean.getErrorCode();
                    String errorMsg = bean.getErrorMsg();
                    if (errorCode == 0) {
                        listener.requestSuccess(null);
                    } else {
                        listener.requestFailure(errorMsg);
                    }
                }

                @Override
                public void onFailure(Request request, IOException e) {
                    e.printStackTrace();
                    Log.i("RegisterModel", "注册失败");
                    listener.requestFailure("网络连接错误，无法注册");
                }
            });
        }
    }
}
