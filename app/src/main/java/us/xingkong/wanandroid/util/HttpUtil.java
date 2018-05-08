package us.xingkong.wanandroid.util;

import android.util.Log;
import android.webkit.CookieManager;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import us.xingkong.wanandroid.OnHttpListener;

/**
 * @作者: Xuer
 * @包名: us.xingkong.wanandroid
 * @类名: jsonUtil
 * @创建时间: 2018/3/17 19:40
 * @最后修改于:
 * @版本: 1.0
 * @描述:
 * @更新日志:
 */

public class HttpUtil {

    protected static String getCookie() {
        CookieManager cookieManager = CookieManager.getInstance();
        String cookie = cookieManager.getCookie("Set-Cookie");
//        Log.i("cookie",cookie);
        if (cookie != null) {
            return cookie;
        } else {
            return "";
        }

    }

    protected static void setCookie(String cookie) {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setCookie("JSESSIONID", cookie);
    }

    public static void parseJson(final String url, final String method, final OnHttpListener response) {

        new Thread(new Runnable() {
            private List<String> setCookies;
            private InputStream inputStream = null;
            private HttpURLConnection connection = null;

            @Override
            public void run() {
                try {
                    URL u = new URL(url);
                    //String cookie = getCookie();
                    //HttpURLConnection.setFollowRedirects(true);
                    connection = (HttpURLConnection) u.openConnection();
                    //connection.setRequestProperty("Cookie", getCookie());
                    //Log.i("cookie", getCookie());
                    if (method.equals("GET")) {
                        connection.setRequestMethod("GET");
                    } else if (method.equals("POST")) {
                        connection.setRequestMethod("POST");
                        /*Map<String, List<String>> cookies = connection.getHeaderFields();
                        setCookies = cookies.get("Set-Cookie");
                        String cookie = "";
                        for (int i = 0; i < setCookies.size(); i++) {
                            //cookie += setCookies.get(i);
                            Log.i("cookie",setCookies.get(i));
                        }*/
                        //cookie += setCookies.get(0) + ";" + setCookies.get(1) + ";" + setCookies.get(2);
                        //connection.setRequestProperty("Cookie", cookie);
                    }

                    connection.setReadTimeout(8000);
                    connection.setConnectTimeout(8000);
                    //connection.setDoInput(true);
                    //connection.setDoOutput(true); //这句注释掉后正常
                    inputStream = connection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuffer stringBuffer = new StringBuffer();
                    String line;

                    if (connection.getResponseCode() == 200) {
                        while ((line = reader.readLine()) != null) {
                            stringBuffer.append(line);
                        }
                        response.isSuccess(stringBuffer.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    response.isFail(e);
                    Log.e("Error", "网络请求错误！");
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();    }

    public static <T> T parseGson(String string, Class<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(string, type);
    }
}
