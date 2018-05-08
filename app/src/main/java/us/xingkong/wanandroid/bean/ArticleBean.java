package us.xingkong.wanandroid.bean;

import java.util.List;

import us.xingkong.wanandroid.BaseBean;

/**
 * @作者: Xuer
 * @包名: us.xingkong.wanandroid.bean
 * @类名: ArticleTest
 * @创建时间: 2018/5/4 13:41
 * @最后修改于:
 * @版本: 1.0
 * @描述:
 * @更新日志:
 */
public class ArticleBean extends BaseBean<ArticleBean.data> {

    public class data {
        public int curPage;
        public List<datas> datas;
        public int pageCount;
        public boolean over;

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

        public boolean getOver() {
            return over;
        }

        public void setOver(boolean over) {
            this.over = over;
        }
    }

    public class datas {
        public String author;
        public String superChapterName;
        public String chapterName;
        public boolean collect;
        public boolean fresh;
        public String link;
        public String niceDate;
        public String title;
        public List<tags> tags;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getSuperChapterName() {
            return superChapterName;
        }

        public void setSuperChapterName(String superChapterName) {
            this.superChapterName = superChapterName;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        public boolean getCollect() {
            return collect;
        }

        public void setCollect(boolean collect) {
            this.collect = collect;
        }

        public boolean getFresh() {
            return fresh;
        }

        public void setFresh(boolean fresh) {
            this.fresh = fresh;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
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

        public List<tags> getTags() {
            return tags;
        }

        public void setTags(List<tags> tags) {
            this.tags = tags;
        }
    }

    public class tags {
        public String name;
        public String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
