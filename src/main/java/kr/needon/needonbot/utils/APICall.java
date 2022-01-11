package kr.needon.needonbot.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URISyntaxException;

public class APICall {

    public String call(URIBuilder b, String method) throws IOException, URISyntaxException {
        String url = b.build().toString();

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method(method, null)
                .build();
        Response response = client.newCall(request).execute();

        return response.body() != null ? response.body().string() : null;
    }

}
