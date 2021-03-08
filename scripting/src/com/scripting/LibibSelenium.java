package com.scripting;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LibibSelenium {

	private static final String USER = "";
	private static final String PASS = "";
	private static final String DRIVER_ADDRESS = "chromedriver.exe";
	private static final String FILE1 = "Inventarna kniga 1999.xlsx";
	private static final String EMPTY = "";
	private static final String SPACE = " ";
	private static final String QUOTE = "\"";

	public static void main(String[] args) {

		List<Book> books = readFileV1();
		addBooks(books);
		updateCopies(books);
		books.toArray();
	}

	// add call
	
	private static final List<Book> readFileV1() {
		File file = new File(FILE1);
		List<Book> books = new ArrayList<>();

		try (FileInputStream inStream = new FileInputStream(file); //
				Workbook workbook = new XSSFWorkbook(inStream)) {
			Sheet sheet = workbook.getSheetAt(0);

			for (int i = 4; i < 1561; i++) {
				Row row = sheet.getRow(i);
				Book book = new Book();
				book.title = getStringValue(row, 5);
				String familyName = getStringValue(row, 1);
				book.authorFamily = familyName.contains(SPACE) ? QUOTE + familyName + QUOTE : familyName;
				book.authorName = getStringValue(row, 2);
				book.year = getIntValue(row, 9);

				int index = books.indexOf(book);
				if (index > -1) {
					book = books.get(index);
					book.barcodes.add(barcode(getIntValue(row, 0)));
					book.ids.add(getIntValue(row, 0));
					continue;
				}
				book.barcodes.add(barcode(getIntValue(row, 0)));
				book.ids.add(getIntValue(row, 0));
				familyName = getStringValue(row, 3);
				book.coAuthorFamily = familyName.contains(SPACE) ? QUOTE + familyName + QUOTE : familyName;
				book.coAuthorName = getStringValue(row, 4);
				String language = getStringValue(row, 8);
				if (!language.isEmpty()) book.tags.add(language);
				String comment = getStringValue(row, 10);
				if (!comment.isEmpty())	book.tags.add(comment);
				books.add(book);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}
	

	private static void addBooks(List<Book> books) {

		System.setProperty("webdriver.chrome.driver", DRIVER_ADDRESS);
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.libib.com/login");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		try {

			driver.findElement(By.id("email")).sendKeys(USER);
			driver.findElement(By.id("password")).sendKeys(PASS);
			driver.findElement(By.id("submit")).click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Add Items")));
			driver.findElement(By.linkText("Add Items")).click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Manual Entry")));
			driver.findElement(By.linkText("Manual Entry")).click();

			for (Book book : books) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit_manual")));
				driver.findElement(By.id("title")).sendKeys(book.title);
				driver.findElement(By.id("creators")).sendKeys(book.getCreators());
				driver.findElement(By.id("publish_year")).sendKeys(Integer.toString(book.year));
				List<WebElement> settings = driver.findElements(By.className("setting_transition"));
				for(WebElement setting : settings) {
					setting.click();
				}
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("book_tags"))); 
				driver.findElement(By.id("book_tags")).sendKeys(book.getTags());
				// paid version only
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("call_number")));
				driver.findElement(By.id("call_number")).sendKeys(book.getIds());

				driver.findElement(By.id("submit_manual")).click();

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Enter Another Item")));
				driver.findElement(By.linkText("Enter Another Item")).click();
				
				System.out.println("Added " + book.toString());
			}
		} finally {
			driver.close();
		}
	}
	
	private static void updateCopies(List<Book> books) {
		System.setProperty("webdriver.chrome.driver", DRIVER_ADDRESS);
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.libib.com/login");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		try {

			driver.findElement(By.id("email")).sendKeys(USER);
			driver.findElement(By.id("password")).sendKeys(PASS);
			driver.findElement(By.id("submit")).click();


			for (Book book : books) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Libraries")));
				driver.findElement(By.id("search_local")).sendKeys("call: " + book.getIds(), Keys.ENTER);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.copies.contracted")));

				driver.findElement(By.cssSelector("div.copies.contracted")).click();
				// add copies
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("copies-to-add")));
				List<Long> codes = book.barcodes;
				if(codes.size() > 1) {
					String additionalCopies = Integer.toString(codes.size() - 1);
					WebElement copies = driver.findElement(By.className("copies-to-add"));
					copies.clear();
					copies.sendKeys(additionalCopies);
				}
				driver.findElement(By.className("add-copy")).click();
				// allow editing the barcode
				wait.until(visibilityOfNElementsLocatedBy(By.xpath("//*[@class='tiny security-remove']"), codes.size()));
				List<WebElement> securityButtons = driver.findElements(By.xpath("//*[@class='tiny security-remove']"));
				for(WebElement btn : securityButtons) {
					wait.until(ExpectedConditions.elementToBeClickable(btn));
					btn.click();
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='modal-delete']")));
					driver.findElement(By.xpath("//*[@class='modal-delete']")).click();
					
				}
				// update barcodes
				wait.until(visibilityOfNElementsLocatedBy(By.xpath("//*[@class='item-barcode']"), codes.size()));
				List<WebElement> bcodes = driver.findElements(By.xpath("//*[@class='item-barcode']"));
				for(int i = 0; i < bcodes.size(); i++){
					WebElement bcode = bcodes.get(i);
					bcode.clear();
					bcode.sendKeys(Long.toString(codes.get(i)));
				}
				//save new barcodes
				wait.until(visibilityOfNElementsLocatedBy(By.xpath("//*[@class='save-barcode']"), codes.size()));
				List<WebElement> saveButtons = driver.findElements(By.xpath("//*[@class='save-barcode']"));
				for(WebElement btn : saveButtons) {
					wait.until(ExpectedConditions.elementToBeClickable(btn));
					btn.click();
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='notification-success'][@style='display: block;']")));
				}
				
				System.out.println("Updated " + book.toString());
			}
		} finally {
			driver.close();
		}
	}

	private static String getStringValue(Row row, int cellnum) {
		Cell cell = row.getCell(cellnum);
		if (cell == null)
			return EMPTY;
		if (cell.getCellType() != CellType.STRING)
			return EMPTY;
		String value = cell.getStringCellValue();
		return value == null ? EMPTY : value;
	}

	private static int getIntValue(Row row, int cellnum) {
		Cell cell = row.getCell(cellnum);
		if (cell == null)
			return 0;
		if (cell.getCellType() != CellType.NUMERIC)
			return 0;
		return (int) cell.getNumericCellValue();
	}
	
	private static long barcode(int inventoryId) {
		char[] stringId = Integer.toString(inventoryId).toCharArray();
		char[] charId = new char[] { '2', '0', '9', '0', '0', '0', '0', '0', '0', '0', '0', '0' };
		int index = 11;
		// fill in chars backwards, keep 210 as beginning
		for (int j = stringId.length - 1; j >= 0; j--) {
			charId[index--] = stringId[j];
		}
		int odd = 0, even = 0;
		// go backwards
		for (int i = 11; i >= 0; i--) {
			int j = Character.getNumericValue(charId[i]);
			if (i % 2 == 0)
				even += j;
			else
				odd += j;
		}
		int check = (10 - ((3 * odd + even) % 10)) % 10;
		String result = new String(charId) + check;
		return Long.parseLong(result);
	}

	static class Book {
		String authorName = "";
		String authorFamily = "";
		String coAuthorName = "";
		String coAuthorFamily = "";
		String title = "";
		int year = 0000;
		List<String> tags = new ArrayList<>();
		List<Long> barcodes = new ArrayList<>();
		List<Integer> ids = new ArrayList<>();

		String getTags() {
			return String.join(", ", tags);
		}

		String getIds() {
			return ids.stream().map(id -> Integer.toString(id)).collect(Collectors.joining(","));
		}
		
		String getCreators() {
			String creators = EMPTY;
			creators += authorName.isEmpty() ? EMPTY : authorName + SPACE;
			creators += authorFamily.isEmpty() ? EMPTY : authorFamily;
			creators += coAuthorName.isEmpty() ? EMPTY : ", " + coAuthorName + SPACE;
			creators += coAuthorFamily.isEmpty() ? EMPTY : coAuthorName.isEmpty() ? ", " + coAuthorFamily : coAuthorFamily;
			return creators.trim();
		}
		
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((authorFamily == null) ? 0 : authorFamily.hashCode());
			result = prime * result + ((authorName == null) ? 0 : authorName.hashCode());
			result = prime * result + ((title == null) ? 0 : title.hashCode());
			result = prime * result + year;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Book other = (Book) obj;
			if (authorFamily == null) {
				if (other.authorFamily != null)
					return false;
			} else if (!authorFamily.equals(other.authorFamily))
				return false;
			if (authorName == null) {
				if (other.authorName != null)
					return false;
			} else if (!authorName.equals(other.authorName))
				return false;
			if (title == null) {
				if (other.title != null)
					return false;
			} else if (!title.equals(other.title))
				return false;
			if (year != other.year)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return String.format("%s - %s - %d", authorFamily, title, year);
		}
	}
	
	
	public static ExpectedCondition<List<WebElement>> visibilityOfNElementsLocatedBy(
		      final By locator, final int elementsCount) {
		    return new ExpectedCondition<List<WebElement>>() {
		      @Override
		      public List<WebElement> apply(WebDriver driver) {
		        List<WebElement> elements = driver.findElements(locator);
		        if (elements.size() < elementsCount) {
		          return null;
		        }

		        for(WebElement element : elements){
		          if(!element.isDisplayed()){
		            return null;
		          }
		        }
		        return elements;
		      }

		      @Override
		      public String toString() {
		        return "visibility of N elements located by " + locator;
		      }
		    };
		  }
}
