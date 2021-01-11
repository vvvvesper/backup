package com.course.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultBackoffStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class GetMethod {
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
   public void testGetWithCookies() throws IOException {
      String testUrl = this.url + bundle.getString("uri3");
      HttpGet get = new HttpGet(testUrl);
      DefaultHttpClient client = new DefaultHttpClient();
      client.setCookieStore(this.store);
      HttpResponse response = client.execute(get);

      //获取响应的状态码
      int statusCode = response.getStatusLine().getStatusCode();
      System.out.println("statusCode = " + statusCode);

      if(statusCode == 200){
         String result = EntityUtils.toString(response.getEntity());
         System.out.println(result);
      }
   }
}
