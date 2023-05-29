# http-client

AUTHOR: Mariano Dominguez  
<marianodominguez@hotmail.com>  
https://www.linkedin.com/in/marianodominguez

VERSION: 1.0

FEEDBACK/BUGS: Please contact me by email.

## Description
GET HTTP client with SSL and redirection support.

- HTTP status code displayed by default
- Optionally, display:
  - Response body
  - Response headers
  - Final redirect location
- Configurable timeout

## Compilation and Usage
See [dependencies](https://github.com/mrdominguez/http-client/blob/master/README.md#dependencies) below.
```
$ javac -cp *:. HttpClient.java && java -cp *:. HttpClient
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
$ java -cp HttpClient.jar HttpClient -u http://apache.org/ -h -l
\__ Executing request GET http://apache.org/ HTTP/1.1
Final HTTP location: https://apache.org/
HTTP status code: 200 (OK)
Response headers:
	Connection	|	keep-alive
	Server	|	Apache
	Last-Modified	|	Wed, 24 May 2023 16:23:17 GMT
	ETag	|	"1197c-5fc72ebe490e1-gzip"
	Cache-Control	|	max-age=3600
	Expires	|	Wed, 24 May 2023 17:23:20 GMT
	Content-Security-Policy	|	default-src 'self' 'unsafe-inline' https://www.apachecon.com/ https://www.google.com/cse/ https://cse.google.com/ https://www.googleapis.com/generate_204 http://*.google.com/generate_204 https://afs.googlesyndication.com/ https://csp.withgoogle.com/ https://www.google.com/images/ https://ssl.gstatic.com/ui/ https://docs.google.com/forms/ https://www.youtube.com/embed/; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://cse.google.com/ http://cse.google.com/adsense/search/async-ads.js https://www.google.com/cse/ https://partner.googleadservices.com/; style-src 'self' 'unsafe-inline' https://www.google.com/cse/; frame-ancestors 'none';
	Strict-Transport-Security	|	max-age=31536000; preload
	Content-Type	|	text/html
	Via	|	1.1 varnish, 1.1 varnish
	Accept-Ranges	|	bytes
	Date	|	Wed, 24 May 2023 17:00:18 GMT
	Age	|	2218
	X-Served-By	|	cache-hel1410032-HEL, cache-chi-kigq8000167-CHI
	X-Cache	|	HIT, HIT
	X-Cache-Hits	|	2, 1
	X-Timer	|	S1684947618.173562,VS0,VE2
	Vary	|	Accept-Encoding
```

## Dependencies
- https://mvnrepository.com/artifact/commons-cli/commons-cli
- https://mvnrepository.com/artifact/commons-logging/commons-logging
- https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
- https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient
- https://mvnrepository.com/artifact/org.apache.httpcomponents/httpcore

This is the list of JAR files I used to compile and test the code:
```
commons-cli-1.3.jar
commons-lang3-3.12.0.jar
commons-logging-1.1.3.jar
httpclient-4.5.9.jar
httpcore-4.4.11.jar
```
