package ui;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.regex.Pattern;

@Disabled
public class UiTest {


    @Test
    @Disabled
    public void testUi() throws InterruptedException {
        String driverPath = "C:\\dev\\env\\chromedriver\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", driverPath);
        WebDriver webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.navigate().to("http://localhost:8080/book_app_war/");

        webDriver.findElement(By.id("u-name-field")).sendKeys("syaufmia");
        webDriver.findElement(By.id("p-word-field")).sendKeys("1234");
        webDriver.findElement(By.id("submit-login")).click();

        for (WebElement w : webDriver.findElements(By.className("alert-table-row"))) {
            System.out.println(w.getText());
//            Pattern pattern = Pattern.compile(".*\\d{2}.\\d{1}.\\d{4}.*");
            System.out.println(w.getText().matches(".*\\d{1,2}.\\d{1,2}.\\d{4}.*"));
        }
        webDriver.close();


    }
}
