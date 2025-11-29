# CSRF Protection

Protection from Cross Site Request Forgery

## When to enable?

- Enabled by default
- Must be enabled when web app is served from backend

## When to disable?

- REST API

## Configuration

- Exclude certain endpoints from CSRF Protection

# CORS

Cross Origin Resource Sharing

- Mechanism to allow sharing of resources to different origin
- Not a security mechanism
- Only applicable to requests, which are made from
  - Web Browser (AJAX) and
  - Different Origin (Domain)

## Configuration

- Using `@CrossOrigin` annotation

## Recommendation

- Don’t touch them if everything is working fine for you app
- If you need to serve web app from server (using Thymeleaf, JTE, etc)
  - Use `_csrf` parameter in forms to send csrf token
  - Don’t disable csrf protection
- If you are creating pure RESTful APIs:
  - Disable CSRF
- If you are creating APIs to be consumed by web browsers on different domains using javascript:
  - Configure CORS to allow requests
  - If possible, only allow specific origins (domains)
