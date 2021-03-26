package com.magenta.account.creation;


import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlEmailInput;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.magento.account.creation.constants.AccountCreationConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.util.TextUtils;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.UUID;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class AccountCreationTest {

    @Test
    public void testAdd() throws Exception{
       // HttpPost post = new HttpPost("http://107.23.133.112/customer/account/createpost/");
        URL url = new URL("http://107.23.133.112/customer/account/createpost/");
        // add request parameter, form parameters
        // form parameters
        Map<String, Object> data = new HashMap<>();
        data.put("firstname", "firstname");
        data.put("lastname", "lastname");
        data.put("email_address", "firtest@last.com");
        data.put("password", "123456");
        data.put("confirmation", "12345");

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : data.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();


        java.net.CookieManager msCookieManager = new java.net.CookieManager();

        Map<String, List<String>> headerFields = conn.getHeaderFields();
        List<String> cookiesHeader = headerFields.get("Set-Cookie");

        if (msCookieManager.getCookieStore().getCookies().size() > 0) {
            // While joining the Cookies, use ',' or ';' as needed. Most of the servers are using ';'
            conn.setRequestProperty("Cookie",
                    StringUtils.join(";",  msCookieManager.getCookieStore().getCookies()));
        }

        if (cookiesHeader != null) {
            for (String cookie : cookiesHeader) {
                msCookieManager.getCookieStore().add(null,HttpCookie.parse(cookie).get(0));
            }
        }

        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setRequestProperty("form_key", UUID.randomUUID().toString());

        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);

        OutputStreamWriter out = new OutputStreamWriter(
                connection.getOutputStream());
        out.write(postData.toString().toCharArray());
        out.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()));
        String decodedString;
        while ((decodedString = in.readLine()) != null) {
            System.out.println(decodedString);
        }
        in.close();
        /*post.setEntity(new UrlEncodedFormEntity(urlParameters));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            System.out.println("RESPONSE====>" + EntityUtils.toString(response.getEntity()));
        }*/
    }

    @Test
    public void sendPost() throws Exception {
        final HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        // form parameters
        Map<Object, Object> data = new HashMap<>();
        data.put("firstname", "firstname");
        data.put("lastname", "lastname");
        data.put("email_address", "fir@last.com");
        data.put("password", "123456");
        data.put("confirmation", "123456");

        HttpRequest request = HttpRequest.newBuilder()
                .POST(buildFormDataFromMap(data))
                .uri(URI.create("http://107.23.133.112/customer/account/createpost/"))
                //.setHeader("User-Agent", "Java 11 HttpClient Bot")
                //.setHeader("form_key", "KElcBvo988EMspXI")// add request header
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());

        // print response body
        System.out.println(response.body());

    }

    private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {
        var builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        System.out.println(builder.toString());
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }


    @Test
    public void formdata(){
        try{
             WebClient webClient = new WebClient();

             HtmlPage htmlPage = webClient.getPage(AccountCreationConstants.SERVICE_URL);

             HtmlForm form = htmlPage.getHtmlElementById("form-validate");

            HtmlTextInput firstName = form.getInputByName("firstname");

            //final HtmlTextArea firstName = form.getTextAreaByName("firstname");
            //firstName.setText("firstname");

             HtmlTextInput middleName = form.getInputByName("middlename");
             HtmlTextInput lastName = form.getInputByName("lastname");
             HtmlEmailInput email = form.getInputByName("email");
            HtmlPasswordInput password = form.getInputByName("password");
            HtmlPasswordInput confirmPassword = form.getInputByName("confirmation");
             HtmlCheckBoxInput selectedSubscription = form.getInputByName("is_subscribed");
            //HtmlSubmitInput button = form.getb;

            firstName.setText("firstname");
            lastName.setText("lastname");
            email.setText("fir@last.com");
            password.setText("123456");
            confirmPassword.setText("123456");


            // Now submit the form by clicking the button and get back the second page.
            form.setActionAttribute("http://107.23.133.112/customer/account/createpost/");
            form.click();

           // page2.toString();

        } catch (IOException ioEx) {

        }
    }

    @Test
    public void testConn() throws  Exception{
        CookieHandler cookieHandler = null;
        CookieManager.setDefault(cookieHandler);


        HttpURLConnection urlConnection = (HttpURLConnection)
                new URL("http://107.23.133.112/customer/account/create/").openConnection();

        StringBuilder stringBuilder =new StringBuilder();



        Map<String, Object> data = new HashMap<>();
        data.put("firstname", "firstname");
        data.put("lastname", "lastname");
        data.put("email_address", "firtest@last.com");
        data.put("password", "123456");
        data.put("confirmation", "12345");

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : data.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
        int    postDataLength = postDataBytes.length;

        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        urlConnection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        urlConnection.setRequestProperty("Keep-Alive", "false");


        int c;


        Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
        writer.write(postData.toString());
        writer.flush();

        urlConnection.getContent();

        while ((c=urlConnection.getInputStream().read())!=-1){
            System.out.print((char)c);

        }

    }

    @Test
    public void postData() throws Exception{

        URL url = new URL("http://107.23.133.112/customer/account/createpost/");

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://107.23.133.112/customer/account/createpost/?___store=default&amp;___from_store=default");

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("firstname", "firstname"));
        params.add(new BasicNameValuePair("lastname", "lastname"));
            params.add(new BasicNameValuePair("email_address", "firtest@last.com"));
        params.add(new BasicNameValuePair("password", "123456"));
        params.add(new BasicNameValuePair("confirmation", "12345"));
        httpPost.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpResponse response = client.execute(httpPost);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();

    }
}
