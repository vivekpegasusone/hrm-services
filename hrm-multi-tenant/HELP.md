# HRM multi-tenant library
This library can be used as dependency in any project that needs multi-tenant support.
This library will need a tenant database to load tenants and their respective datasources.

The sample script for creating a mysql database : 

```sql
CREATE DATABASE `tenant_master`;
use tenant_master;

CREATE TABLE `tenant_data_source` (
  `id` int NOT NULL AUTO_INCREMENT,  
  `tenant` varchar(255) NOT NULL,
  `module` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `driver_class_name` varchar(255) NOT NULL,
  `is_active` bit(1) NOT NULL,
  `maximum_pool_size` int NOT NULL,
  `minimum_idle` int NOT NULL,
  `max_lifetime` int NOT NULL,
  `keep_alive_time` int NOT NULL,
  `connection_timeout` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UK_TENANT_DATA_SOURCE_NAME_MODULE` (`tenant`,`module`)
) 
-- root encrypted to VB9vUergflhaSpEDhEXsRw==
INSERT INTO `tenant_master`.`tenant_data_source` (`id`, `tenant`, `module`, `url`, `user_name`, `password`, `driver_class_name`,
`is_active`, `maximum_pool_size`, `minimum_idle`, `max_lifetime`, `keep_alive_time`, `connection_timeout`)
VALUES(1, 'drishti', 'user', 'jdbc:mysql://localhost:3306/anpl_user?useSSL=false','root', 'VB9vUergflhaSpEDhEXsRw==',
'com.mysql.cj.jdbc.Driver', 1, 10, 10, 1800000, 120000, 30000);

INSERT INTO `tenant_master`.`tenant_data_source` (`id`, `tenant`, `module`, `url`, `user_name`, `password`, `driver_class_name`,
`is_active`, `maximum_pool_size`, `minimum_idle`, `max_lifetime`, `keep_alive_time`, `connection_timeout`)
VALUES(2, 'whizzy', 'user', 'jdbc:mysql://localhost:3306/anpl_user?useSSL=false','root', 'VB9vUergflhaSpEDhEXsRw==',
'com.mysql.cj.jdbc.Driver', 1, 10, 10, 1800000, 120000, 30000);
```

# Properties Required

Following properties(with sample values) are required for this library to function.

```yaml
master:
  datasource:
    username: sa
    password: encrypted_value
    driver-class-name: com.mysql.cj.jdbc.Driver
    jdbc-url: "Database URL"
    max-lifetime: 1800000
    minimum-idle: 2
    keepalive-time: 120000
    maximum-pool-size: 2
    connection-timeout: 30000

encryption:
  salt: "some_salt"
  secret: "encryption_secret"
```

Use command below to encrypt a password :

```cmd
java -cp .\hrm-multi-tenant-1.0.0.jar com.whizzy.hrm.multitenant.util.EncryptionUtil the_default_salt the_#secret_@key password encrypt
```
and to decrypt encrypted password :

```cmd
java -cp .\hrm-multi-tenant-1.0.0.jar com.whizzy.hrm.multitenant.util.EncryptionUtil the_default_salt the_#secret_@key a+PxfP7ind7jD+8uWpZoyA== decrypt
```

make sure the salt and secret key are 16 character long strings.

# Dependencies Required

The hrm-multi-tenant-x.x.x.jar build by this project is thin jar. 
To add it as dependency, following dependencies must be satisfied.

```xml
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
```
