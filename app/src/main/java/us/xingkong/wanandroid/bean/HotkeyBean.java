package us.xingkong.wanandroid.bean;

import java.util.List;

import us.xingkong.wanandroid.BaseBean;

/**
 * @作者: Xuer
 * @包名: us.xingkong.wanandroid
 * @类名: hotkeyBean
 * @创建时间: 2018/3/24 20:24
 * @最后修改于:
 * @版本: 1.0
 * @描述:
 * @更新日志:
 */

public class HotkeyBean extends BaseBean<List<HotkeyBean.data>> {

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static class data {
        public String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
