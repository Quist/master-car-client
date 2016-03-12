package carsystem;

import com.google.gson.Gson;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CarHttp {

    private final URL baseUrl;

    public CarHttp(URL baseUrl) {
        this.baseUrl = baseUrl;
    }

    public HttpResponse post(String uri, String payload) throws IOException {
        byte[] payloadBytes = payload.getBytes();

        URL url = new URL(baseUrl.toString() + uri);
        return sendPost(url, payloadBytes);
    }

    public HttpResponse get(String uri) throws IOException {
        return sendGet(new URL(baseUrl.toString() + uri));
    }


    public HttpResponse delete(String uri) throws IOException {
        return sendDelete(new URL(baseUrl.toString() + uri));
    }

    public HttpResponse put(String uri, String payload) throws IOException {
        return sendPut(new URL(baseUrl.toString() + uri), payload.getBytes());
    }

    private HttpResponse sendPut(URL url, byte[] bytes) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Content-Type","application/json");
        con.setRequestMethod("PUT");
        con.setDoOutput(true);

        System.out.println("Making PUT request to URL: " + url.toString());

        OutputStream os = con.getOutputStream();
        os.write(bytes);
        os.close();

        printHeaders(con.getHeaderFields());
        String response = "";
        int responseCode = con.getResponseCode();
        if (responseCode >= 200 && responseCode < 300) {
            response = readRequestBody(con.getInputStream());
        }

        HttpResponse httpResponse= new HttpResponse(responseCode, new Gson().toJson(response));

        return httpResponse;
    }

    private HttpResponse sendDelete(URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestMethod("DELETE");

        System.out.println("Making DELETE request to URL: " + url.toString());
        con.connect();
        printHeaders(con.getHeaderFields());

        String response= readRequestBody(con.getInputStream());

        int responseCode = con.getResponseCode();
        HttpResponse httpResponse= new HttpResponse(responseCode, new Gson().toJson(response));

        return httpResponse;
    }

    private HttpResponse sendPost(URL url, byte[] payload) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Content-Type","application/json");
        con.setRequestMethod("POST");
        con.setDoOutput(true);

        System.out.println("Making POST request to URL: " + url.toString());

        OutputStream os = con.getOutputStream();
        os.write(payload);
        os.close();

        String response= readRequestBody(con.getInputStream());

        int responseCode = con.getResponseCode();
        HttpResponse httpResponse= new HttpResponse(responseCode, response);

        return httpResponse;
    }

    private HttpResponse sendGet(URL url) throws IOException {
        System.out.println("\nSending 'GET' request to URL : " + url);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();

        printHeaders(con.getHeaderFields());
        String response = "";
        int responseCode = con.getResponseCode();
        if (responseCode >= 200 && responseCode < 300) {
            response = readRequestBody(con.getInputStream());
        }

        HttpResponse httpResponse= new HttpResponse(responseCode, response);
        return httpResponse;
    }

    private void printHeaders(Map<String, List<String>> headerFields) {
        System.out.println("\n##### HTTP response headers ######");
        Iterator<String> iterator = headerFields.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            System.out.println(key + ": " + headerFields.get(key));
        }
        System.out.println("####################################\n");
    }

    private String readRequestBody(InputStream httpExchange) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(httpExchange));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

}
