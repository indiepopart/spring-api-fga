# Add Fine Grained Authorization to a Java Spring Boot API

This is a simplified Spring Boot document API, for the purpose of demonstrating Fine-grained authorization (FGA) using OpenFGA, with Auth0 as the identity provider.

> **Prerequisites:**
> - [Java OpenJDK 21](https://jdk.java.net/java-se-ri/21)
> - [Auth0 account](https://auth0.com/signup)
> - [Auth0 CLI 1.4.0](https://github.com/auth0/auth0-cli#installation)
> - [Docker 24.0.7](https://docs.docker.com/desktop/)

## Clone the API

To download the API project, execute the following commands:

```bash
git clone https://github.com/indiepopart/spring-api-fga.git
cd spring-api-fga/final
```

## Register the API to Auth0

Sign up at [Auth0](https://auth0.com/signup) and install the [Auth0 CLI](https://github.com/auth0/auth0-cli). Then in the command line run:

```shell
auth0 login
```

The command output will display a device confirmation code and open a browser session to activate the device.

Register the API within your tenant:

```shell
auth0 apis create \
  --name "Spring API" \
  --identifier https://document-api.okta.com
```

The first line in the command output will contain your Auth0 domain.

## Run the Spring Boot API resource server

Create a file `application.yml` at the root of the `final` project with the following content:

```yaml
okta:
  oauth2:
    issuer: https://<your-auth0-domain>/
    audience: https://document-api.okta.com
```

Run the API with:

```shell
./gradlew bootRun
```

## Test the API

Create a test access token:

```shell
auth0 test token -a https://document-api.okta.com -s openid
```

Save the access token in an environment variable:

```shell
ACCESS_TOKEN=<access-token>
```

Use the access token to make a request to the API. Create document:

```shell
curl -X POST \
  -H "Authorization:Bearer $ACCESS_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name": "planning.doc"}' \
  http://localhost:8080/document
```

## Help

Please post any questions as comments on the [blog post](), or on the [Okta Developer Forums](https://devforum.okta.com/).

## License

Apache 2.0, see [LICENSE](LICENSE).
