package us.xingkong.wanandroid;

/**
 * @作者: Xuer
 * @包名: us.xingkong.wanandroid.bean
 * @类名: BaseBean
 * @创建时间: 2018/5/4 13:39
 * @最后修改于:
 * @版本: 1.0
 * @描述:
 * @更新日志:
 */
public class BaseBean<T> {
    public T data;
    public int errorCode;
    public String errorMsg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
