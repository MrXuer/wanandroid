package us.xingkong.wanandroid.MVP.model;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;
import us.xingkong.wanandroid.app.Constants;
import us.xingkong.wanandroid.bean.HotkeyBean;
import us.xingkong.wanandroid.bean.SearchBean;
import us.xingkong.wanandroid.util.HttpUtil;
import us.xingkong.wanandroid.util.OkUtils;

/**
 * @作者: Xuer
 * @包名: us.xingkong.wanandroid.MVP.model
 * @类名: searchModel
 * @创建时间: 2018/4/5 20:03
 * @最后修改于:
 * @版本: 1.0
 * @描述:
 * @更新日志:
 */

public class SearchModel {

    public interface onRequestListener {

        void hotkeySuccess(List<HotkeyBean.data> list);

        void requestSuccess(List<SearchBean.datas> list);

        void requestFailure(String errorMsg);
    }

    public interface HotkeyInterface {
        void loadHotkey(onRequestListener listener);
    }

    public interface SearchInterface {
        void loadSearch(String search, int page, onRequestListener listener);
    }

    public static class HotkeyImpl implements HotkeyInterface {

        @Override
        public void loadHotkey(final onRequestListener listener) {
            OkUtils.get(Constants.hotkey, null, new OkUtils.DataCallBack() {
                @Override
                public void onSuccess(String result) throws Exception {
                    Log.i("HotkeyModel", result);
                    HotkeyBean bean = HttpUtil.parseGson(result, HotkeyBean.class);
                    List<HotkeyBean.data> list = bean.getData();
                    int errorCode = bean.getErrorCode();
                    String errorMsg = bean.getErrorMsg();
                    if (errorCode == 0) {
                        listener.hotkeySuccess(list);
                    } else {
                        listener.requestFailure(errorMsg);
                    }
                }

                @Override
                public void onFailure(Request request, IOException e) {
                    e.printStackTrace();
                    Log.i("HotkeyModel", "hotkey初始化失败");
                    listener.requestFailure("网络连接错误，无法获取热门搜索");
                }
            });
        }
    }

    public static class SearchImpl implements SearchInterface {

        @Override
        public void loadSearch(String search, int page, final onRequestListener listener) {
            HashMap<String,String> params = new HashMap<>();
            params.put("k",search);
            OkUtils.post(Constants.search + page + "/json", params, new OkUtils.DataCallBack() {
                @Override
                public void onSuccess(String result) throws Exception {
                    Log.i("searchModel", result);
                    SearchBean bean = HttpUtil.parseGson(result, SearchBean.class);
                    List<SearchBean.datas> list = bean.getData().getDatas();
                    int errorCode = bean.getErrorCode();
                    String errorMsg = bean.getErrorMsg();
                    if (errorCode == 0) {
                        listener.requestSuccess(list);
                    } else {
                        listener.requestFailure(errorMsg);
                    }
                }

                @Override
                public void onFailure(Request request, IOException e) {
                    e.printStackTrace();
                    Log.i("SearchModel", "Search初始化失败");
                    listener.requestFailure("网络连接错误，无法获取搜索列表");
                }
            });
        }
    }
}
