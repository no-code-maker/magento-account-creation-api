package com.magenta.account.creation;


import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountCreationTest {

   /* @Test
    public void testAdd() throws Exception{
       // HttpPost post = new HttpPost("http://107.23.133.112/customer/account/createpost/");
        URL url = new URL("http://107.23.133.112/customer/account/createpost/");
        // add request parameter, form parameters
        // form parameters
        Map<String, Object> data = new HashMap<>();
        data.put("firstname", "firstname");
        data.put("lastname", "lastname");
        data.put("email", "firtest11@last.com");
        data.put("password", "123456");
        data.put("confirmation", "123456");


        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : data.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setInstanceFollowRedirects(true);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setRequestProperty("form_key", UUID.randomUUID().toString());

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





        OutputStreamWriter out = new OutputStreamWriter(
                conn.getOutputStream());
        out.write(postData.toString().toCharArray());
        out.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        conn.getInputStream()));
        String decodedString;
        while ((decodedString = in.readLine()) != null) {
            System.out.println(decodedString);
        }
        in.close();
        *//*post.setEntity(new UrlEncodedFormEntity(urlParameters));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            System.out.println("RESPONSE====>" + EntityUtils.toString(response.getEntity()));
        }*//*
    }

    @Test
    public void sendPost() throws Exception {
        final HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(30))
                .executor(Executors.newFixedThreadPool(2))
                .priority(1) //HTTP/2 priority
                .proxy(ProxySelector.getDefault())
                .sslContext(SSLContext.getDefault())
                .sslParameters(new SSLParameters())
                .build();

        // form parameters
        Map<Object, Object> data = new HashMap<>();
        data.put("firstname", "firstname");
        data.put("lastname", "lastname");
        data.put("email", "fir222@last.com");
        data.put("password", "123456");
        data.put("confirmation", "123456");
        data.put("is_subscribed", false);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(buildFormDataFromMap(data))
                .uri(URI.create("http://107.23.133.112/customer/account/createpost/"))
                //.setHeader("User-Agent", "Java 11 HttpClient Bot")
                //.setHeader("form_key", "KElcBvo988EMspXI")// add request header
                .header("Content-Type", "multipart/form-data")
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

             //HtmlPage htmlPage = webClient.getPage(AccountCreationConstants.SERVICE_URL);

            HtmlPage htmlPage = webClient.getPage( "http://localhost:8080/");

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
            email.setText("fir11@last.com");
            password.setText("123456");
            confirmPassword.setText("123456");

            form.click();


            // Now submit the form by clicking the button and get back the second page.
            //form.setActionAttribute("http://107.23.133.112/customer/account/createpost/");



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

        HttpClient httpClient = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://107.23.133.112/customer/account/createpost/"))
                .build();




        HttpPost httpPost = new HttpPost("http://107.23.133.112/customer/account/createpost/");

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("firstname", "firstname"));
        params.add(new BasicNameValuePair("lastname", "lastname"));
            params.add(new BasicNameValuePair("email_address", "firtest@last.com"));
        params.add(new BasicNameValuePair("password", "123456"));
        params.add(new BasicNameValuePair("confirmation", "123456"));
        httpPost.setEntity(new UrlEncodedFormEntity(params));

        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setHeader("form_key", UUID.randomUUID().toString());


    }

    @Test
    public void redirect(){

        try {

            // form parameters
            Map<String, Object> data = new HashMap<>();
            data.put("firstname", "firstname");
            data.put("lastname", "lastname");
            data.put("email_address", "firtest11@last.com");
            data.put("password", "123456");
            data.put("confirmation", "123456");

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> param : data.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }

            String url = "http://107.23.133.112/customer/account/createpost/";

            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(
                    conn.getOutputStream());
            out.write(postData.toString().toCharArray());
            out.close();

            System.out.println("Request URL ... " + url);

            boolean redirect = false;

            // normally, 3xx is redirect
            int status = conn.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK) {
                if (status == HttpURLConnection.HTTP_MOVED_TEMP
                        || status == HttpURLConnection.HTTP_MOVED_PERM
                        || status == HttpURLConnection.HTTP_SEE_OTHER)
                    redirect = true;
            }

            System.out.println("Response Code ... " + status);

            if (redirect) {

                // get redirect url from "location" header field
                String newUrl = conn.getHeaderField("Location");

                // get the cookie if need, for login
                String cookies = conn.getHeaderField("Set-Cookie");

                // open the new connnection again
                conn = (HttpURLConnection) new URL(newUrl).openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                OutputStreamWriter out1 = new OutputStreamWriter(
                        conn.getOutputStream());
                out1.write(postData.toString().toCharArray());
                out1.close();
                System.out.println("Redirect to URL : " + newUrl);

            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer html = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                html.append(inputLine);
            }
            in.close();

            System.out.println("URL Content... \n" + html.toString());
            System.out.println("Done");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @Test
    public void httpRedirect() throws Exception{


        HttpClient instance = createHttpClient(10000);


        HttpPost httpPost = new HttpPost("http://107.23.133.112/customer/account/createpost/");

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("firstname", "firstname"));
        params.add(new BasicNameValuePair("lastname", "lastname"));
        params.add(new BasicNameValuePair("email", "firtestfir@last.com"));
        params.add(new BasicNameValuePair("password", "123456"));
        params.add(new BasicNameValuePair("confirmation", "123456"));
        httpPost.setEntity(new UrlEncodedFormEntity(params, java.nio.charset.StandardCharsets.UTF_8));

        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setHeader("form_key", UUID.randomUUID().toString());
        //httpPost.setHeader("Location", "http://107.23.133.112/customer/account/index/");

        HttpResponse response = instance.execute(httpPost);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.getEntity().writeTo(out);
        String responseString = out.toString();
        out.close();
        System.out.println(responseString);


    }

    public static CloseableHttpClient createHttpClient(final int maxRedirects) throws Exception {
         final BasicCookieStore cookieStore = new BasicCookieStore();
        return HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy())
                .setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).setMaxRedirects(maxRedirects).setRedirectsEnabled(true).build())
                .setDefaultCookieStore(cookieStore)
                .setRetryHandler(new StandardHttpRequestRetryHandler())
                .build();
    }

   /* @Test
    public  void postMethod() throws IOException {

        DefaultHttpClient  httpclient = new DefaultHttpClient();
        *//*httpclient.setRedirectStrategy(new DefaultRedirectStrategy() {
            public boolean isRedirected(HttpRequest request, HttpResponse response, HttpContext context)  {
                boolean isRedirect=false;
                try {
                    isRedirect = super.isRedirected(request, response, context);
                } catch (ProtocolException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (!isRedirect) {
                    int responseCode = response.getStatusLine().getStatusCode();
                    if (responseCode == 301 || responseCode == 302) {
                        return true;
                    }
                }
                return isRedirect;
            }
        });*//*
        CookieStore cookieStore = new BasicCookieStore();
        httpclient.getParams().setParameter(
                ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
        HttpContext context = new BasicHttpContext();
        context.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
        //ResponseHandler<String> responseHandler = new BasicResponseHandler();

        HttpPost httpPost = new HttpPost("http://107.23.133.112/customer/account/createpost/");

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("firstname", "firstname"));
        params.add(new BasicNameValuePair("lastname", "lastname"));
        params.add(new BasicNameValuePair("email", "firtestfir@last.com"));
        params.add(new BasicNameValuePair("password", "123456"));
        params.add(new BasicNameValuePair("confirmation", "123456"));
        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setHeader("form_key", UUID.randomUUID().toString());
        httpPost.setHeader("Location", "index.html");

        HttpResponse response = httpclient.execute(httpPost, context);
        System.out.println(response);

        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
            throw new IOException(response.getStatusLine().toString());

        HttpUriRequest currentReq = (HttpUriRequest) context.getAttribute(
                ExecutionContext.HTTP_REQUEST);
        HttpHost currentHost = (HttpHost)  context.getAttribute(
                ExecutionContext.HTTP_TARGET_HOST);
        String currentUrl = currentHost.toURI() + currentReq.getURI();
        System.out.println(currentUrl);

        HttpEntity entity = response.getEntity();
        if (entity != null) {
            long len = entity.getContentLength();
            if (len != -1 && len < 2048) {
                System.out.println(EntityUtils.toString(entity));
            } else {
                // Stream content out
            }
        }


    }*/



}
