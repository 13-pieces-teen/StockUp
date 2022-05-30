package com.example.stockup;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class doGet_json {
    public static String url="https://www.mxnzp.com/api/barcode/goods/details";
    public  static String doGet(String url) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;
        try {
            //1.建立连接
            URL requestURL = new URL(url);
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setRequestProperty("Charset", "utf-8");
            urlConnection.connect();

            //获取二进制流
            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
//                builder.append("\n");
            }

            if (builder.length() == 0) {
                return null;
            }

            // 5.StringBuilder拼接成最终的字符串文本
            bookJSONString = builder.toString();
            } catch (Exception e) {
            e.printStackTrace();
            } finally {
            // 关闭连接
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return bookJSONString;
    }
    public static String get_jsonname(String object_Json){
        String httpUrl=url+"?"+"barcode="+object_Json+"&app_id=olnqxyrmliporeq9&app_secret=c1U0d0lBMmhUM1VJYjlvOEo4SUVtQT09";
        String ob_name=doGet(httpUrl);
        ob_name=retName(ob_name);
        return ob_name;
    }

    public static String retName(String ob_name) {
        String name = null;
        try {
            JSONObject jsonObject=new JSONObject(ob_name);
            JSONObject jsonObject1=jsonObject.getJSONObject("data");
            name=jsonObject1.optString("goodsName","");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return name;
    }
}
