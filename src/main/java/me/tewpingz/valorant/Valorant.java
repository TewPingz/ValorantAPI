package me.tewpingz.valorant;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.tewpingz.valorant.auth.ValAuthentication;
import me.tewpingz.valorant.auth.ValHeader;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

class Valorant {

    private JsonParser jsonParser;
    private CloseableHttpClient httpClient;

    protected Valorant() {
        this.jsonParser = new JsonParser();
        try {
            this.resetExecutor();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected ValAuthentication auth(String username, String password) throws IOException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("client_id", "play-valorant-web-prod");
        jsonObject.addProperty("nonce", 1);
        jsonObject.addProperty("redirect_uri", "https://beta.playvalorant.com/opt_in");
        jsonObject.addProperty("response_type", "token id_token");

        this.post("https://auth.riotgames.com/api/v1/authorization", jsonObject.toString());

        JsonObject authObject = new JsonObject();
        authObject.addProperty("type", "auth");
        authObject.addProperty("username", username);
        authObject.addProperty("password", password);

        JsonObject authResponse = this.put("https://auth.riotgames.com/api/v1/authorization", authObject.toString()).getAsJsonObject();
        JsonObject responseObject = authResponse.get("response").getAsJsonObject();
        JsonObject parametersObject = responseObject.get("parameters").getAsJsonObject();
        String uri = String.valueOf(parametersObject.get("uri"));
        String[] parts = uri.replace("https://beta.playvalorant.com/opt_in#", "").split("&");
        String token = parts[0].replace("access_token=", "").replace("\"", "");

        ValHeader authHeader = new ValHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        JsonObject entitlementResponse = this.post("https://entitlements.auth.riotgames.com/api/token/v1", "{}", authHeader).getAsJsonObject();
        String entitlementToken = entitlementResponse.get("entitlements_token").getAsString();

        JsonObject userResponse = this.post("https://auth.riotgames.com/userinfo", "{}", authHeader).getAsJsonObject();
        String userId = userResponse.get("sub").getAsString();

        return new ValAuthentication(userId, token, entitlementToken);
    }

    protected JsonElement get(String url, ValHeader... headers) throws IOException {
        HttpGet httpGet = new HttpGet(url);

        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Content-type", "application/json");

        for (ValHeader header : headers){
            if (header != null) {
                httpGet.setHeader(header.getKey(), header.getValue());
            }
        }

        CloseableHttpResponse response = this.httpClient.execute(httpGet);
        String responseJSON = EntityUtils.toString(response.getEntity());
        return this.jsonParser.parse(responseJSON);
    }

    protected JsonElement post(String url, String json, ValHeader... headers) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);

        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        for (ValHeader header : headers){
            if (header != null) {
                httpPost.setHeader(header.getKey(), header.getValue());
            }
        }

        CloseableHttpResponse response = this.httpClient.execute(httpPost);
        String responseJSON = EntityUtils.toString(response.getEntity());
        return this.jsonParser.parse(responseJSON);
    }

    protected JsonElement put(String url, String json, ValHeader... headers) throws IOException {
        HttpPut httpPut = new HttpPut(url);
        StringEntity entity = new StringEntity(json);
        httpPut.setEntity(entity);

        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-type", "application/json");

        for (ValHeader header : headers){
            if (header != null) {
                httpPut.setHeader(header.getKey(), header.getValue());
            }
        }

        CloseableHttpResponse response = this.httpClient.execute(httpPut);
        String responseJSON = EntityUtils.toString(response.getEntity());
        return this.jsonParser.parse(responseJSON);
    }

    protected void resetExecutor() throws IOException {
        if (this.httpClient != null){
            this.httpClient.close();
        }

        this.httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
                .build();
    }
}
