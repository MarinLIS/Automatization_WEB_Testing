package educational.materials.seminars.seminar_01.exercise_02;

import educational.materials.AbstractTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class Example_02 extends AbstractTestClass {

    @Test
    void loginTest() {
        String pathToChromeDriver = "src\\main\\resources\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", pathToChromeDriver);

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get(getBaseUrl());
        WebElement usernameField = driver.findElement(By.cssSelector("form#login input[type='text']"));
        WebElement passwordField = driver.findElement(By.cssSelector("form#login input[type='password']"));

        usernameField.sendKeys(getUsername());
        passwordField.sendKeys(getPassword());

        WebElement loginButton = driver.findElement(By.cssSelector("form#login button"));
        loginButton.click();

        WebElement usernameLink = driver.findElement(By.partialLinkText(getUsername()));
        String actualUsername = usernameLink.getText().replace("\n", " ").trim();

        Assertions.assertEquals(String.format("Hello, %s", getUsername()), actualUsername);

        driver.quit();
    }
}
