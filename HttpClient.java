/*
 * Copyright 2025 Mariano Dominguez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 

/*
 * HTTP client with SSL and redirection support.
 * Author: Mariano Dominguez
 * Version: 1.0
 */

package com.github.mrdominguez;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class HttpClient {

  public static void main(String[] args) throws IOException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
	HttpClient hc = new HttpClient();
	Options options = new Options();

	Option urlOpt = new Option("u", "url", true, "URL address");
	urlOpt.setRequired(true);
	options.addOption(urlOpt);

	Option headersOpt = new Option("h", "headers", false, "Display responde headers");
	headersOpt.setRequired(false);
	options.addOption(headersOpt);

	Option locationOpt = new Option("l", "location", false, "Display final location");
	locationOpt.setRequired(false);
	options.addOption(locationOpt);

	Option bodyOpt = new Option("b", "body", false, "Display responde body");
	bodyOpt.setRequired(false);
	options.addOption(bodyOpt);

	Option timeoutOpt = new Option("t", "timeout", true, "Timeout (seconds)");
	timeoutOpt.setRequired(false);
	options.addOption(timeoutOpt);

	CommandLineParser parser = new DefaultParser();
	HelpFormatter formatter = new HelpFormatter();
	CommandLine cmd = null;

	try {
		cmd = parser.parse(options, args);
	} catch (ParseException e) {
		System.out.println(e.getMessage());
		formatter.printHelp(100, hc.getClass().getSimpleName(), null, options, null, true);
		System.exit(1);
	}

	String url = cmd.getOptionValue("url");
	Boolean headers = cmd.hasOption("headers") ? true : false;
	Boolean location = cmd.hasOption("location") ? true : false;
	Boolean body = cmd.hasOption("body") ? true : false;
	int timeout = cmd.hasOption("timeout") ? Integer.parseInt(cmd.getOptionValue("timeout")): 30;

	RequestConfig config = RequestConfig.custom()
		.setConnectTimeout(timeout * 1000)
		.setConnectionRequestTimeout(timeout * 1000)
		.setSocketTimeout(timeout * 1000).build();

	CloseableHttpClient httpClient = HttpClients
		.custom()
		.setRedirectStrategy(new LaxRedirectStrategy())
		.setDefaultRequestConfig(config)
		.setSSLContext(new SSLContextBuilder()
			.loadTrustMaterial(TrustAllStrategy.INSTANCE).build())
		.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
		.build();

	try {
		HttpClientContext context = HttpClientContext.create();
		HttpGet httpGet = new HttpGet(url);
		System.out.println("\\__ Executing request " + httpGet.getRequestLine());

		HttpResponse httpResponse = httpClient.execute(httpGet, context);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		String reasonPhrase = httpResponse.getStatusLine().getReasonPhrase();

		HttpHost target = context.getTargetHost();
		List<URI> redirectLocations = context.getRedirectLocations();
		URI finalLocation = URIUtils.resolve(httpGet.getURI(), target, redirectLocations);

		if ( location )
			System.out.println("Final HTTP location: " + finalLocation.toASCIIString());

		System.out.print("HTTP status code: " + statusCode);
		if ( !StringUtils.isBlank(reasonPhrase) )
			System.out.print(" (" + reasonPhrase + ")");
		System.out.println();

		if ( headers ) {
			System.out.println("Response headers:");
			Header[] responseHeaders = httpResponse.getAllHeaders();
			for (Header header : responseHeaders) {
				System.out.println(String.format("\t%-35s | %s", header.getName(), header.getValue()));
			}
		}

		String responseBody = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
		if ( body )
			System.out.println("Response body:\n" + responseBody);
	} finally {
		httpClient.close();
	}
  }
}
