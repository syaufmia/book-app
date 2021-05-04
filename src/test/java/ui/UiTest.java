package ui;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class UiTest {

    @Test
    public void testUi() {
        String driverPath = "C:\\dev\\env\\chromedriver\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", driverPath);
        WebDriver webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.navigate().to("http://localhost:8080/book_app_war/");

        webDriver.findElement(By.id("u-name-field")).sendKeys("testuser");
        webDriver.findElement(By.id("p-word-field")).sendKeys("1234");
        webDriver.findElement(By.id("submit-login")).click();



    }
}
