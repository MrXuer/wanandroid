package us.xingkong.wanandroid.bean;

import java.io.Serializable;
import java.util.List;

import us.xingkong.wanandroid.BaseBean;

/**
 * @作者: Xuer
 * @包名: us.xingkong.wanandroid
 * @类名: articleList
 * @创建时间: 2018/3/17 19:33
 * @最后修改于:
 * @版本: 1.0
 * @描述:
 * @更新日志:
 */

public class BannerBean extends BaseBean<List<BannerBean.data>> implements Serializable {

    public static class data implements Serializable {
        String imagePath;
        String title;
        String url;

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
