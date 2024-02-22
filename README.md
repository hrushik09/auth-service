# auth-server

Fetch access token

- Run Auth Server using

```shell
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

- Open following in browser

```text
http://localhost:8080/oauth2/authorize?response_type=code&client_id=client&scope=openid&redirect_uri=http://localhost:8080/authorized&code_challenge=J1OXNN3WieK6On42C3Jbl5cBeDKA9ojgXOEnB5yyB-w&code_challenge_method=S256
```

- Login using user credentials
- Get redirected to `/authorized` endpoint. Url will contain `code` parameter, copy it.
- Fetch access token by

```shell
curl --location 'http://localhost:8080/oauth2/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Authorization: Basic Y2xpZW50OnNlY3JldA==' \
--data-urlencode 'client_id=client' \
--data-urlencode 'redirect_uri=http://localhost:8080/authorized' \
--data-urlencode 'grant_type=authorization_code' \
--data-urlencode 'code=code' \
--data-urlencode 'code_verifier=GLcWPlp-9GTJOTm9YK_0d63nx2m4K-6pwu6uABlMuwM'
```

- To decode access token, use [jwt.io](https://jwt.io/).