import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;

import java.nio.charset.StandardCharsets;

import java.util.stream.Collectors;
import java.util.Scanner;
import java.util.Base64;
import java.util.Map;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class ClientCredentialsGrantFlow {

  private static final String tokenEndpoint = "http://localhost:8080/oauth2/token";
  
  private static final String clientId = "oidc-client";
  private static final String clientSecret = "secret";

  private static final String scope = "books.list books.edit";
  private static final String grantType = "client_credentials";

  public static void main(String[] args) {
    String tokenResponse = getToken();
    System.out.println("\n----------\n" + tokenResponse);
  }

  private static String getToken() {
    HttpRequest request = createGetTokenRequest();
    try {
      HttpResponse<String> response = HttpClient.newHttpClient()
        .send(request, HttpResponse.BodyHandlers.ofString());
      System.out.println("Response code - " + response.statusCode());
      return response.body();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static HttpRequest createGetTokenRequest() {
    Map<String, String> data = Map.of(
        "scope", scope,
        "grant_type", grantType
    );
    String formData = data.entrySet().stream()
      .map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
      .collect(Collectors.joining("&"));
    System.out.println(formData);
    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create(tokenEndpoint))
      .header("Content-Type", "application/x-www-form-urlencoded")
      .header("Authorization", getClientAuthHeaderValue())
      .POST(HttpRequest.BodyPublishers.ofString(formData))
      .build();
    return request;
  }

  private static String getClientAuthHeaderValue() {
    String clientIdAndSecret = clientId + ":" + clientSecret;
    Base64.Encoder encoder = Base64.getEncoder();
    return "Basic " + encoder.encodeToString(clientIdAndSecret.getBytes());
  }
}
