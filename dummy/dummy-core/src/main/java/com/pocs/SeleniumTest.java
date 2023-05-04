package com.pocs;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.String.format;

public class SeleniumTest {

	public static void main(String[] args) throws MalformedURLException {

		String serverUrl = "http://localhost:%s/wd/hub";

		String chrome53 = "4421";
		String chrome67 = "4431";
		String chrome90 = "4411";

		String ffMarionette = "4401";
		String ffQuantum = "4402";
		String ff88 = "4403";

		serverTest(new ChromeOptions(), format(serverUrl, chrome53), "chrome53");
//		serverTest(new ChromeOptions(), format(serverUrl, chrome67), "chrome67");
//		serverTest(new ChromeOptions(), format(serverUrl, chrome90), "chrome90");

//		FirefoxProfile profile = new FirefoxProfile();
//		profile.setPreference("network.proxy.type", 0);
//		profile.setPreference("network.proxy.http", "");
//		profile.setPreference("network.proxy.http_port", 80);
//		FirefoxOptions options = new FirefoxOptions();
//		options.setCapability(FirefoxDriver.PROFILE, profile);
//
//		serverTest(options, format(serverUrl, ff88), "ff88");
//		serverTest(options, format(serverUrl, ffMarionette), "ffMarionette");
//		serverTest(options, format(serverUrl, ffQuantum), "ffQuantum");
	}


	private static void serverTest(Capabilities capabilities, String serverUrl, String testName) throws MalformedURLException {
		System.out.println(format("======================  START  %s ===========================", testName));
		test(capabilities, serverUrl, "https://dir.bg/");
		//test(capabilities, serverUrl, "https://www.bing.com");
		test(capabilities, serverUrl, "https://www.unicredit.it/it/privati.html");
	}

	private static void test(Capabilities capabilities, String serverUrl, String address) throws MalformedURLException {
		WebDriver driver = new RemoteWebDriver(new URL(serverUrl), capabilities);
		System.out.println(format(" ================== %s ", address));
		try {
			driver.get(address);
			String pageSource = driver.getPageSource();
			System.out.println(" " + pageSource.substring(0, 1000));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			driver.quit();
			System.out.println(" ================== ");
		}
	}
}
