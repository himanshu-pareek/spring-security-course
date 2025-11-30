# OAuth2 Authorization Server

> Look at the References to learn more about OAuth2

## Prerequisites

* Basic understanding of **OAuth2 Authorization** along with **OpenID Connect Protocol**
* Basic understanding of roles in OAuth2:
  * Authorization Server
  * Client / Application
  * Resource Server / API Server
  * Resource Owner / User
* Basic understanding of different formats of Access Token
  * JWT
  * Opaque
* Basic understanding of OAuth2 flows
  * Authorization Code Grant Flow
  * Client Credentials Flow
  * Token Introspection

## Authorization Server

- Stores user information
- Authenticates users and provides Access Tokens
- Validates opaque tokens
- Enable resource server to validate JWT tokens using public key

### Authorization Server Configurations:

1. Protocol Endpoints Configuration
2. Authentication Configuration
3. User Details Management
4. Client Details Management
5. Key Pairs Management
6. General App Settings

- [x]  Test Authorization Code Grant Flow
1. Authorization server configuration endpoint: http://localhost:8080/.well-known/openid-configuration
2. Create a login url:

```
<http://localhost:8080/oauth2/authorize>? // authorize end point
    response_type=code& // for code grant flow
    client_id=client& // Client ID
    scope=openid& // comma separated scops to request for (must be among registered)
    redirect_uri=https://example.net/authorized& // Redirect URL (must be one of the registered)
    code_challenge=x_ECinPKACtGzwXiiZNO2GVb01-rZS86uZo6MdfmxpQ& // For PKCE
    code_challenge_method=S256 // For PKCE

```

1. Token Request:

```bash
curl localhost:8080/oauth2/token \\ # Token endpoint
    -u client:secret \\ # For client authentication (Http Basic)
    -X POST \\ # Post request
    --header 'Content-Type: application/x-www-form-urlencoded; charset=utf-8' \\ # Content type must be x-www-form-urlencoded
    --data-urlencode "client_id=client" \\ # Client ID
    --data-urlencode "redirect_uri=https://example.net/authorized" \\ # Redirect URI (must match the above request)
    --data-urlencode "grant_type=authorization_code" \\ # Grant type must be `authorization_code` for code grant flow
    --data-urlencode "code=rohwVFUE0UpSnom-bL8R-C2mvE..." \\ # Authorization code received on redirect uri
    --data-urlencode "code_verifier=dQ3LQ_JpeIbgKLQQ..." # Code verifier used to generate code challenge in above request

```

- [x]  Test Client Credentials Grant Flow
1. Client Credentials Access Token request

```bash
curl localhost:8080/oauth2/token \\ # Token Endpoint
    -u client:secret \\ # For client authentication (Http Basic)
    -X POST \\ # Post request
    --header 'Content-Type: application/x-www-form-urlencoded; charset=utf-8' \\
    --data-urlencoded "grant_type=client_credentials" \\ # Grant type must be `client_credentials`
    --data-urlencoded "scope=sync" # Scope to use for the client (must be registered)

```

- [x]  Using Opaque (Reference) tokens
1. Test Authorization code grant flow
2. Test Client credentials grant flow

- [x]  Introspecting tokens

Token introspection request:

```bash
curl localhost:8080/oauth2/introspect \\ # Introspection endpoint
    -X POST \\
    -u client:secret \\ # Client authentication
    --header 'Content-Type: application/x-www-form-urlencoded; charset=utf-8' \\
    --data-urlencoded "token=soj89losijholj98u0islknj..."

```

- [x]  Revoking Tokens

Token Revoke request:

```bash
curl localhost:8080/oauth2/revoke \\ # Revoke endpoint
    -X POST \\
    -u client:secret \\ # client authentication
    --header 'Content-Type: application/x-www-form-urlencoded; charset=utf-8' \\
    --data-urlencoded "token=lsohlsjmu098ohsl..."

```

## References

- https://www.rfc-editor.org/rfc/rfc6749
- https://oauth.net/2/
- https://openid.net/developers/how-connect-works/
- https://youtube.com/playlist?list=PLbk-lC-jmHejz7ZTfOqFyXH15yt6b3WHe&si=gINwqRwP4KTsnlaA
- https://docs.spring.io/spring-security/reference/servlet/oauth2/authorization-server/getting-started.html
