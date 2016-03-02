package carsystem;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CarHttp {

    private final URL baseUrl;

    public CarHttp(URL baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void post(String uri, String payload) throws IOException {
        byte[] payloadBytes = payload.getBytes();

        URL url = new URL(baseUrl.toString() + uri);
        sendPost(url, payloadBytes);
    }

    public HttpResponse get(String uri) throws IOException {
        return sendGet(new URL(baseUrl.toString() + uri));
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

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        printHeaders(con.getHeaderFields());

        int responseCode = con.getResponseCode();
        HttpResponse httpResponse= new HttpResponse(responseCode, new Gson().toJson(response.toString()));

        return httpResponse;
    }

    private HttpResponse sendGet(URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        System.out.println("\nSending 'GET' request to URL : " + url);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer responseBuffer = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            responseBuffer.append(inputLine);
        }
        in.close();
        printHeaders(con.getHeaderFields());

        int responseCode = con.getResponseCode();

        HttpResponse httpResponse= new HttpResponse(responseCode, responseBuffer.toString());

        return httpResponse;
    }

    private void printHeaders(Map<String, List<String>> headerFields) {
        Iterator<String> iterator = headerFields.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            System.out.println(key + ": " + headerFields.get(key));
        }
    }

}
