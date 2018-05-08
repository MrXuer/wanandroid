package us.xingkong.wanandroid;

/**
 * @作者: Xuer
 * @包名: us.xingkong.wanandroid
 * @类名: httpInterface
 * @创建时间: 2018/3/13 19:43
 * @最后修改于:
 * @版本: 1.0
 * @描述:
 * @更新日志:
 */

public interface OnHttpListener {
    //获取成功时调用
    void isSuccess(String response);

    //获取失败时调用
    void isFail(Exception e);
}
