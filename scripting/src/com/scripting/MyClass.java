package com.scripting;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyClass {

	public static void main(String[] args) {

		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\emiliyant\\Downloads\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.libib.com/login");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		
		try {

			driver.findElement(By.id("email")).sendKeys("nos_jelezen@yahoo.com");
			driver.findElement(By.id("password")).sendKeys("libib@test.m3");
			driver.findElement(By.id("submit")).click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Add Items")));
			driver.findElement(By.linkText("Add Items")).click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Manual Entry")));
			driver.findElement(By.linkText("Manual Entry")).click();

			for (int i = 0; i < 2; i++) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit_manual")));
				driver.findElement(By.id("title")).sendKeys("test123" + i);
				driver.findElement(By.id("creators")).sendKeys("go6o i to6o");
				driver.findElement(By.id("publish_year")).sendKeys("1995");
				driver.findElement(By.className("setting_transition")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("book_tags")));
				driver.findElement(By.id("book_tags")).sendKeys("български, tag2");
				driver.findElement(By.id("submit_manual")).click();

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Enter Another Item")));
				driver.findElement(By.linkText("Enter Another Item")).click();
			}
		} finally {
			driver.close();
		}
	}

}
