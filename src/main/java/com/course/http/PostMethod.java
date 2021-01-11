package com.course.http;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class PostMethod {
    private String url;
    private ResourceBundle bundle;
    private CookieStore store;
    @BeforeTest
    public void beforeTest(){
        bundle = ResourceBundle.getBundle("application",Locale.CHINA);
        url = bundle.getString("url1");
    }

    @Test
    public void testGetCookies() throws IOException {
        String result;
        String testUrl = this.url + bundle.getString("uri2");
        HttpGet get = new HttpGet(testUrl);
        DefaultHttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(get);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);

        this.store = client.getCookieStore();
        List<Cookie> cookieList = store.getCookies();
        for(Cookie cookie:cookieList){
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println("cookie name:"+name+"  value:"+value);
        }
    }

    @Test(dependsOnMethods = {"testGetCookies"})
    public void testPostWithCookie() throws JSONException, IOException {
        String result;
        String testUrl = this.url + bundle.getString("uri4");
        HttpPost post = new HttpPost(testUrl);
        DefaultHttpClient client = new DefaultHttpClient();
        JSONObject param = new JSONObject();
        param.put("name","huhansan");
        param.put("age","18");
        StringEntity entity = new StringEntity(param.toString());
        post.setEntity(entity);
        post.setHeader("content-type","application/json");
        client.setCookieStore(this.store);
        HttpResponse response = client.execute(post);
        result = EntityUtils.toString(response.getEntity());
        System.out.println(result);

        JSONObject  resultJson = new JSONObject(result);
        String success = (String)resultJson.get("huhansan");
        String status = (String )resultJson.get("status");
//        System.out.println(success);
//        System.out.println(status);
        Assert.assertEquals("success",success);
        Assert.assertEquals("1",status);
    }
}
