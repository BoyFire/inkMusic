package com.ruoyi.music.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {

    public static Map<Object, Object> doGet(String url) {
        ObjectMapper objectMapper = new ObjectMapper();
        // 响应模型
        CloseableHttpResponse response = null;
        String responseContent = null;
        try (
                // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
                CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        ) {
            // 创建Get请求
            HttpGet httpGet = new HttpGet(url);


            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                responseContent = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            return objectMapper.readValue(responseContent, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取结果
     * @param path 端口后的请求地址
     * @param ids 字段类型
     * @param id 字段值
     * @return
     */
    public static Map<Object, Object> httpGet(String path, String ids, String id) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(ids, String.valueOf(id)));
        URI uri = null;
        try {
            uri = new URIBuilder().setScheme("http").setHost("localhost").setPort(3000).setPath(path).setParameters(params).build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return doGet(uri.toString());
    }
}
