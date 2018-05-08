package us.xingkong.wanandroid.bean;

import java.io.Serializable;
import java.util.List;

import us.xingkong.wanandroid.BaseBean;

/**
 * @作者: Xuer
 * @包名: us.xingkong.wanandroid
 * @类名: jsonBean
 * @创建时间: 2018/3/20 22:50
 * @最后修改于:
 * @版本: 1.0
 * @描述:
 * @更新日志:
 */

public class SearchBean extends BaseBean<SearchBean.data> {

    public class data {
        public int curPage;
        public List<datas> datas;
        public int pageCount;

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public List<datas> getDatas() {
            return datas;
        }

        public void setDatas(List<datas> datas) {
            this.datas = datas;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }
    }

    public class datas implements Serializable {
        public String author;
        public String superChapterName;
        public String chapterName;
        public String niceDate;
        public String title;
        public String link;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        public String getSuperChapterName() {
            return superChapterName;
        }

        public void setSuperChapterName(String superChapterName) {
            this.superChapterName = superChapterName;
        }

        public String getNiceDate() {
            return niceDate;
        }

        public void setNiceDate(String niceDate) {
            this.niceDate = niceDate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
}
