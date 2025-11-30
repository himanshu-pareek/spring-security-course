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

public class TokenRevokeFlow {

  private static final String revokeEndpoint = "http://localhost:8080/oauth2/revoke";
  
  private static final String clientId = "oidc-client";
  private static final String clientSecret = "secret";

  public static void main(String[] args) {
    if (args.length < 1) {
      System.err.println("Please provide token to revoke");
      System.exit(1);
    }
    String token = args[0];
    String revokeTokenResponse = revokeToken(token);
    System.out.println("\n----------\n" + revokeTokenResponse);
  }

  private static String revokeToken(String token) {
    HttpRequest request = createTokenRevokeRequest(token);
    try {
      HttpResponse<String> response = HttpClient.newHttpClient()
        .send(request, HttpResponse.BodyHandlers.ofString());
      System.out.println("Response code - " + response.statusCode());
      return response.body();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static HttpRequest createTokenRevokeRequest(String token) {
    Map<String, String> data = Map.of(
        "token", token
    );
    String formData = data.entrySet().stream()
      .map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
      .collect(Collectors.joining("&"));
    System.out.println(formData);
    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create(revokeEndpoint))
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
