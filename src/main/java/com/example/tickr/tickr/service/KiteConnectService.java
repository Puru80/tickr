package com.example.tickr.tickr.service;

import com.example.tickr.tickr.config.AppConfig;
import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.User;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KiteConnectService {

    @Getter
    private final KiteConnect kiteConnect;
    private final AppConfig appConfig;

    @Autowired
    public KiteConnectService(KiteConnect kiteConnect, AppConfig appConfig) {
        this.kiteConnect = kiteConnect;
        this.kiteConnect.setUserId(appConfig.getKiteUserId());
        this.appConfig = appConfig;
    }

    public void generateSession(String requestToken) throws IOException, KiteException {
        // Get accessToken as follows,
        User user = kiteConnect.generateSession(requestToken, appConfig.getKiteApiSecret());

        // Set request token and public token which are obtained from login process.
        kiteConnect.setAccessToken(user.accessToken);
        kiteConnect.setPublicToken(user.publicToken);
    }

    /*public String generateRequestToken() throws IOException, CodeGenerationException {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .followRedirects(false)
                .build();

        // First call to /api/login
        RequestBody loginBody = new FormBody.Builder()
                .add("user_id", appConfig.getKiteUserId().trim())
                .add("password", appConfig.getKitePassword().trim())
                .build();

        Request loginRequest = new Request.Builder()
                .url("https://kite.zerodha.com/api/login")
                .post(loginBody)
                .build();

        //Request ID
        String requestId;
        try (Response response = client.newCall(loginRequest).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            ResponseBody responseBody = response.body();
            if (responseBody == null) throw new IOException("Response body is null from login request");

            String responseString = responseBody.string();
            System.out.println("Login Response: " + responseString);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseJson = objectMapper.readTree(responseString);

            if (responseJson.has("data") && responseJson.get("data").has("request_id")) {
                requestId = responseJson.get("data").get("request_id").asText();
            } else {
                String message = responseJson.has("message") ? responseJson.get("message").asText() : "request_id not found";
                throw new IOException("Failed to get request_id: " + message);
            }
        }

        // Second call to /api/twofa
        String totp = totpGenerator.generateTOTP();

        RequestBody twofaBody = new FormBody.Builder()
            .add("user_id", appConfig.getKiteUserId().trim())
            .add("request_id", requestId)
            .add("twofa_value", totp)
            .add("twofa_type", "totp")
            .add("skip_session", String.valueOf(true))
            .build();

        Request twofaRequest = new Request.Builder()
            .url("https://kite.zerodha.com/api/twofa")
            .post(twofaBody)
            .build();

        try (Response response = client.newCall(twofaRequest).execute()) {
            ResponseBody responseBody = response.body();
            assert responseBody != null;
            String responseString = responseBody.string();
            System.out.println("TwoFA Response: " + responseString);

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        }

        Request kiteLoginReq = new Request.Builder()
            .url(kiteConnect.getLoginURL())
            .get()
            .build();

        System.out.println("Kite Login Request: " + kiteLoginReq);

        String redirectLocation = "";

        try (Response response = client.newCall(kiteLoginReq).execute()) {
            if (response.isRedirect()) {
                String location = response.header("Location");
                System.out.println("Redirect Location: " + location);
                if (location != null && location.contains("request_token=")) {
                    try {
                        URI uri = new URI(location);
                        String query = uri.getQuery();
                        if (query != null) {
                            String[] pairs = query.split("&");
                            for (String pair : pairs) {
                                int idx = pair.indexOf("=");
                                if (idx > 0 && pair.substring(0, idx).equals("request_token")) {
                                    String requestToken = pair.substring(idx + 1);
                                    System.out.println("Final Request Token: " + requestToken);
                                    return requestToken;
                                }
                            }
                        }
                    } catch (URISyntaxException e) {
                        throw new IOException("Invalid redirect URI: " + location, e);
                    }
                } else if(location != null) {
                    redirectLocation = location;
                }
            }

//            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//
//            ResponseBody responseBody = response.body();
//            if (responseBody == null) throw new IOException("Response body is null from kite login request");

//            System.out.println("Kite Login Response Headers: " + response.headers().toString());
//
//            String responseString = responseBody.string();
//            System.out.println("Kite Login Response: " + responseString);
        }

        Request redirectRequest = new Request.Builder()
            .url(redirectLocation)
            .get()
            .build();

        try (Response response = client.newCall(redirectRequest).execute()) {
            if (response.isRedirect()) {
                String location = response.header("Location");
                System.out.println("Final Redirect Location: " + location);
                if (location != null && location.contains("request_token=")) {
                    try {
                        URI uri = new URI(location);
                        String query = uri.getQuery();
                        if (query != null) {
                            String[] pairs = query.split("&");
                            for (String pair : pairs) {
                                int idx = pair.indexOf("=");
                                if (idx > 0 && pair.substring(0, idx).equals("request_token")) {
                                    String requestToken = pair.substring(idx + 1);
                                    System.out.println("Final Request Token: " + requestToken);
                                    return requestToken;
                                }
                            }
                        }
                    } catch (URISyntaxException e) {
                        throw new IOException("Invalid redirect URI: " + location, e);
                    }
                }
            }

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            ResponseBody responseBody = response.body();
            if (responseBody == null) throw new IOException("Response body is null from redirect request");

            String responseString = responseBody.string();
            System.out.println("Redirect Response: " + responseString);
        }

        return "";
    }*/

}
