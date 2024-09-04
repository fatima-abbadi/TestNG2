package automationFramework;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Test2 {
    WebDriver driver;

    @BeforeClass
    public void setUp() {
        // Set up ChromeDriver path
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ProBook\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe"); 
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @DataProvider(name = "data")
    public Object[][] loginDataProvider() throws IOException {
        // Excel file path
        String filePath = "C:\\Users\\ProBook\\eclipse-workspace\\Homework2\\src\\automationFramework\\data.xlsx";
        FileInputStream file = new FileInputStream(new File(filePath));
        // Check if file exists
        File file1 = new File(filePath);
        if (!file1.exists()) {
            throw new IOException("Excel file not found at path: " + filePath);
        }
        
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheet("Sheet1");

        int rowCount = sheet.getLastRowNum();
        Object[][] data = new Object[rowCount][3];

        for (int i = 1; i <= rowCount; i++) {
            Row row = sheet.getRow(i);
            data[i - 1][0] = row.getCell(0).getStringCellValue(); 
            data[i - 1][1] = row.getCell(1).getStringCellValue(); 

            // Define the expected message for each row
            if (i == 1) {
                data[i - 1][2] = "Invalid email format, password empty.";
            } else if (i == 2) {
                data[i - 1][2] = "Login failed: Wrong password.";
            } else if (i == 3) {
                data[i - 1][2] = "Login successful.";
            }
        }
        workbook.close();
        return data;
    }

    @Test(dataProvider = "data")
    public void testLogin(String username, String password, String expectedMessage) {
       
        driver.get("https://www.frontgate.com/ShoppingCartView");

        // Enter username and password
        WebElement usernameField = driver.findElement(By.id("gwt-sign-in-modal"));
        WebElement passwordField = driver.findElement(By.id("passwordReset")); 
        WebElement loginButton = driver.findElement(By.id("logonButton")); 

        usernameField.clear();
        usernameField.sendKeys(username);
        passwordField.clear();
        passwordField.sendKeys(password);
        loginButton.click();

        // Get the actual message displayed
        WebElement messageElement = driver.findElement(By.id("messageElement")); // Replace with the actual ID of the message element
        String actualMessage = messageElement.getText();

        // Assert the expected and actual messages
        Assert.assertEquals(actualMessage, expectedMessage);
    }

    @AfterClass
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
}
