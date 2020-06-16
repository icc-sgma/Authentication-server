# Authorization-server-mysql

I create this project based on this web site [Click here](https://blog.marcosbarbero.com/centralized-authorization-jwt-spring-boot2/).
I just removed uneccessary code and configuration. You can take a look on that website to read the theory.

## Installation

Clone this project, but before run it, you need to setup MySQL database and tables.

- Create MySQL database (spring-jwt)
- Create tables defined in data.sql file (resources folder)
- Create user to access created database

The username, password and database name is in application.properties file:
```
spring.datasource.url=jdbc:mysql://localhost:3306/spring-jwt
spring.datasource.username=spring-user
spring.datasource.password=r8PdxX7$sX98ETHc;
```

Prerequisites:

- Java8 or greater
- Eclipse or similar
- A computer :D
- Time

## Usage

After run this project you can hit it with the following url: 

```bash
http://127.0.0.1:9090/authentication-server/oauth/token
```
This server is running in port 9090, you can change it in application.properties file.
```
server.port=9090
server.servlet.contextPath=/authentication-server
```

See the images auth-1.png and auth-2.png to understand hhow to hit the server using Postman. You can find the images in resource folder.

## Configuration

If you want to change basic authentication credentials, go to data.sql file and modify this line:
```bash
INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, authorities, access_token_validity)
  VALUES ('brucewayne', '{bcrypt}$2a$10$vCXMWCn7fDZWOcLnIEhmK.74dvK1Eh8ae2WrWlhr2ETPLoxQctN4.', 'read,write', 'password,refresh_token,client_credentials', 'ROLE_CLIENT', 300);
```
The username is `brucewayne` and passwords is `sercret`. You can use this web tool to create a different encrypted password [Click here](https://www.browserling.com/tools/bcrypt).

The encrypted password is going to looks like this:
```
$2a$10$gnDjVZpTV6GgGPUHTbWVNOdJbw56KLV.cKwACYb.Fp.D/4M3liBrO
```

Don't forget to add {bcrypt} at the begining, example:
```
{bcrypt}$2a$10$gnDjVZpTV6GgGPUHTbWVNOdJbw56KLV.cKwACYb.Fp.D/4M3liBrO
```

If you want to increment the number of rounds in the web tool, you need to change the number in the class `sgma.auth.server.security.util.DefaultPasswordEncoderFactories`. Change the number of rounds in the following line:
```
delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(new BCryptPasswordEncoder(10));
```

If you want to change username and password used in the body, you need to change the folling record in data.sql file. Remember that username must match in table: `authorities` column: `username`.
```
INSERT INTO users (id, username, password, enabled) VALUES (1, 'batman', '{bcrypt}$2a$10$lxTsT9rC0ZhoBcpJIsxLuexdRuP56Y6wLh1OsJ8M/jwcYowmAgSd.', 1);

INSERT INTO authorities (username, authority) VALUES ('batman', 'ROLE_USER');
```

## JKS File

This server is going to use jks file to create the token and the resource server is going to use the public key to validate the token. You can find the jks file in resources folder and the public key is in `resource-server project` in resources folder.
```
auth-server.jks
```

You can find in my space related projects. You can find in one of my projects an example of how to create jks file and public key.

Enjoy it!!!!
