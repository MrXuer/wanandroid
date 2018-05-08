package us.xingkong.wanandroid.bean;

import java.io.Serializable;

import us.xingkong.wanandroid.BaseBean;

/**
 * @作者: Xuer
 * @包名: us.xingkong.wanandroid
 * @类名: loginBean
 * @创建时间: 2018/3/24 15:32
 * @最后修改于:
 * @版本: 1.0
 * @描述:
 * @更新日志:
 */

public class LoginBean extends BaseBean<LoginBean.data> {

    public static class data implements Serializable {
        public String username;
        public String password;
        public String email;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}

