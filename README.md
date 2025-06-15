# http-client

AUTHOR: Mariano Dominguez  
<marianodominguez@hotmail.com>  
https://www.linkedin.com/in/marianodominguez

VERSION: 1.0

FEEDBACK/BUGS: Please contact me by email.

## Description
HTTP client with SSL and redirection support.

- HTTP status code displayed by default
- Optionally, display:
  - Response body
  - Response headers
  - Final redirect location
- Configurable timeout

## Compilation and Usage
List of JAR files used to compile and test the code:
```
commons-cli-1.3.jar
commons-lang3-3.12.0.jar
commons-logging-1.1.3.jar
httpclient-4.5.14.jar
httpcore-4.4.16.jar
```
Links to [Maven artifacts](https://github.com/mrdominguez/http-client/blob/master/README.md#dependencies) below.
```
$ mvn clean package
[INFO] Scanning for projects...
[INFO]
[INFO] -----------------< com.github.mrdominguez:HttpClient >------------------
[INFO] Building HttpClient 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
       <... output omitted>
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  12.639 s
[INFO] Finished at: 2025-05-28T17:47:11-04:00
[INFO] ------------------------------------------------------------------------
$
$ java -cp target/HttpClient-1.0-SNAPSHOT-jar-with-dependencies.jar com.github.mrdominguez.HttpClient
Missing required option: u
usage: HttpClient [-b] [-h] [-l] [-t <arg>] -u <arg>
 -b,--body            Display responde body
 -h,--headers         Display responde headers
 -l,--location        Display final location
 -t,--timeout <arg>   Timeout (seconds)
 -u,--url <arg>       URL address
```

## Sample Output
```
$ java -cp HttpClient.jar com.github.mrdominguez.HttpClient -u http://apache.org/ -h -l
\__ Executing request GET http://apache.org/ HTTP/1.1
Final HTTP location: https://apache.org/
HTTP status code: 200 (OK)
Response headers:
        Connection                          | keep-alive
        Server                              | Apache
        Last-Modified                       | Wed, 28 May 2025 17:23:10 GMT
        ETag                                | "fe8e-636356f68813b-gzip"
        Cache-Control                       | max-age=3600
        Expires                             | Wed, 28 May 2025 18:23:12 GMT
        Access-Control-Allow-Origin         | *
        Content-Security-Policy             | default-src 'self' data: ...;
        Access-Control-Expose-Headers       | Content-Security-Policy
        Content-Type                        | text/html
        Via                                 | 1.1 varnish, 1.1 varnish
        Accept-Ranges                       | bytes
        Date                                | Wed, 28 May 2025 18:13:52 GMT
        Age                                 | 3039
        X-Served-By                         | cache-hel1410021-HEL, cache-iad-kjyo7100115-IAD
        X-Cache                             | HIT, HIT
        X-Cache-Hits                        | 4, 1
        X-Timer                             | S1748456032.252248,VS0,VE2
        Vary                                | Accept-Encoding
        Strict-Transport-Security           | max-age=31536000; includeSubDomains; preload
```

## Dependencies
- https://mvnrepository.com/artifact/commons-cli/commons-cli
- https://mvnrepository.com/artifact/commons-logging/commons-logging
- https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
- https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient
- https://mvnrepository.com/artifact/org.apache.httpcomponents/httpcore
