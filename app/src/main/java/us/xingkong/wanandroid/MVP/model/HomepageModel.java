package us.xingkong.wanandroid.MVP.model;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import us.xingkong.wanandroid.app.Constants;
import us.xingkong.wanandroid.bean.ArticleBean;
import us.xingkong.wanandroid.bean.BannerBean;
import us.xingkong.wanandroid.util.HttpUtil;
import us.xingkong.wanandroid.util.OkUtils;

/**
 * @作者: Xuer
 * @包名: us.xingkong.wanandroid.model
 * @类名: homePageModel
 * @创建时间: 2018/4/3 20:31
 * @最后修改于:
 * @版本: 1.0
 * @描述:
 * @更新日志:
 */

public class HomepageModel {

    public interface OnRequestListener {

        void requestSuccess(List<String> titleList, List<String> imageList, List<String> urlList);

        void requestSuccess(List<ArticleBean.datas> list);

        void requestFailure(String errorMsg);
    }

    public interface BannerInterface {

        void loadBanner(OnRequestListener listener);
    }

    public interface ArticlesInterface {

        void loadArticles(int page, OnRequestListener listener);
    }

    public static class BannerImpl implements BannerInterface {

        @Override
        public void loadBanner(final OnRequestListener listener) {
            OkUtils.get(Constants.banner, null, new OkUtils.DataCallBack() {
                @Override
                public void onSuccess(String result) throws Exception {
                    Log.i("BannerModel", result);
                    List<String> titleList = new ArrayList<>();
                    List<String> imageList = new ArrayList<>();
                    List<String> urlList = new ArrayList<>();
                    BannerBean bean = HttpUtil.parseGson(result, BannerBean.class);
                    List<BannerBean.data> list = bean.getData();
                    int errorCode = bean.getErrorCode();
                    String errorMsg = bean.getErrorMsg();
                    if (errorCode == 0) {
                        for (int i = 0; i < list.size(); i++) {
                            titleList.add(list.get(i).getTitle());
                            imageList.add(list.get(i).getImagePath());
                            urlList.add(list.get(i).getUrl());
                        }
                        listener.requestSuccess(titleList, imageList, urlList);
                    } else {
                        listener.requestFailure(errorMsg);
                    }
                }

                @Override
                public void onFailure(Request request, IOException e) {
                    e.printStackTrace();
                    Log.i("HomepageModel", "banner初始化失败");
                    listener.requestFailure("网络连接错误，无法获取banner");
                }
            });
        }
    }

    public static class ArticlesImpl implements ArticlesInterface {

        @Override
        public void loadArticles(int page, final OnRequestListener listener) {
            OkUtils.get(Constants.article + page + "/json", null, new OkUtils.DataCallBack() {
                @Override
                public void onSuccess(String result) throws Exception {
                    Log.i("ArticleModel", result);
                    //Log.i("page", String.valueOf(page));
                    ArticleBean bean = HttpUtil.parseGson(result, ArticleBean.class);
                    List<ArticleBean.datas> list = bean.getData().getDatas();
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
                    Log.i("HomepageModel", "article初始化失败");
                    listener.requestFailure("网络连接错误，无法获取文章列表");
                }
            });
        }
    }
}
