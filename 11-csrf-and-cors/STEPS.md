# Understanding CSRF and CORS

[x] Server and Client Apps without any Security

- Server
  - ajax GET - ✔️
  - ajax POST - ✔️
  - form POST - ✔️
- Client
  - ajax GET - X (CORS Error)
  - ajax POST - X (CORS Error)
  - form POST - ✔️

[x] Add spring-security in server

- Server
  - ajax GET - ✔️
  - ajax POST - X (CSRF error)
  - form POST - X (CSRF error)
- Client
  - ajax GET - X (CORS Error)
  - ajax POST - X (CORS Error)
  - form POST - X (CSRF Error)

[x] Disable CSRF

- Server
  - ajax GET - ✔️
  - ajax POST - ✔️
  - form POST - ✔️
- Client
  - ajax GET - X (CORS Error)
  - ajax POST - X (CORS Error)
  - form POST - ✔️

[x] Enable CSRF protection and Add CSRF Token

- Server
  - ajax GET - ✔️
  - ajax POST - X (CSRF Error - 403)
  - form POST - ✔️
- Client
  - ajax GET - X (CORS Error)
  - ajax POST - X (CORS Error)
  - form POST - X (CSRF Error - 403)

[x] Enable CORS for client

- Server
  - ajax GET - ✔️
  - ajax POST - X (CSRF Error - `403`)
  - form POST - ✔️
- Client
  - ajax GET - ✔️ (Additional auth setup)
    - Disabled authorization for `GET /hello` requests
  - ajax POST - X (CORS Error)
  - form POST - X (CSRF Error - `403`)

[x] Enable CORS for client and Disable CSRF

- Server
  - ajax GET - ✔️
  - ajax POST - ✔️
  - form POST - ✔️
- Client
  - ajax GET - ✔️
  - ajax POST - ✔️ (Additional auth setup)
    - Add `Authorization` header in `fetch` request
    - Allow all requests with `OPTIONS` method
  - form POST - ✔️
