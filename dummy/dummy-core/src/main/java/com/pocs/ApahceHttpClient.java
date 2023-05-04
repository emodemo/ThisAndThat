package com.pocs;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import java.net.URL;
import java.time.Duration;

public class ApahceHttpClient {

	public static void main(String[] args){
		//quantum
//		System.out.println("================= quantum 1 =================");
//		legacy("http://localhost:34444/wd/hub");
//		System.out.println("================= quantum 2 =================");
//		closable("http://localhost:34444/wd/hub");
//
//		// marionette
//		System.out.println("================= marionette 1 =================");
//		legacy("http://localhost:4421/wd/hub");
//		System.out.println("================= marionette 2 =================");
//		closable("http://localhost:4421/wd/hub");

//		System.out.println("================= A 1 =================");
//		legacy("http://ngpselenium5-stage.neterra.skrill.net:4444/wd/hub");
//		System.out.println("================= A 2 =================");
//		closable("http://ngpselenium5-stage.neterra.skrill.net:4444/wd/hub");
//
//		System.out.println("================= B 1 =================");
//		legacy("http://ngpselenium5-stage.neterra.skrill.net:4454/wd/hub");
//		System.out.println("================= B 2 =================");
//		closable("http://ngpselenium5-stage.neterra.skrill.net:4454/wd/hub");

		configurable();
	}


	private static void configurable(){
		HttpClientBuilder builder = httpClientBuilder();

		int TIMEOUT = (int) Duration.ofSeconds(3).toMillis();
		String address = "http://localhost:8000/api/sleep/10000";

		RequestConfig config = RequestConfig.custom()
				.setConnectTimeout(3000)
				.setConnectionRequestTimeout(3000)
				.setSocketTimeout(3000)
				.build();
		try {
			CloseableHttpClient client = builder.setDefaultRequestConfig(config)
					.build();
			URL url = new URL(address);
			HttpResponse response = client.execute(new HttpGet(url.toURI()));
			System.out.println("=========== RESPONSE ==========");
			System.out.println(response.toString());
			System.out.println("=========== RESPONSE ==========");
		} catch (Exception e){
			e.printStackTrace();
		}

	}


	private static HttpClientBuilder httpClientBuilder() {
		int timeout = (int) Duration.ofSeconds(5).toMillis();

		RequestConfig config = RequestConfig.custom()
				.setConnectTimeout(timeout)
				.setConnectionRequestTimeout(timeout)
				.setSocketTimeout(timeout)
				.build();

		return HttpClientBuilder.create()
				.setDefaultRequestConfig(config)
				.setMaxConnPerRoute(20)
				.useSystemProperties();
	}



	private static void closable(String address){
		try {
			HttpClient client = HttpClients.createSystem();
			URL url = new URL(address);
			HttpResponse response = client.execute(new HttpGet(url.toURI()));
			System.out.println(response.toString());
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	private static void legacy(String address){
		try(DefaultHttpClient http = new DefaultHttpClient()){
			ResponseHandler<String> handler = new BasicResponseHandler();
			URL url = new URL(address);
			String execute = http.execute(new HttpGet(url.toURI()), handler);
			System.out.println(execute.toString());
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

}