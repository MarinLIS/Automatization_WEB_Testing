package educational.materials.seminars.seminar_01.exercise_02;

import educational.materials.AbstractTestClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Example_03 extends AbstractTestClass {

    private WebDriver driver;
    private static String USERNAME;
    private static String PASSWORD;

    @BeforeAll
    public static void setupClass() {
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        USERNAME = getUsername();
        PASSWORD = getPassword();
    }

    @BeforeEach
    public void setupTest() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testStandGeekBrains() {
        driver.get(getBaseUrl());
        driver.findElement(By.cssSelector("form#login input[type='text']")).sendKeys(USERNAME);
        driver.findElement(By.cssSelector("form#login input[type='password']")).sendKeys(PASSWORD);
        driver.findElement(By.cssSelector("form#login button")).click();
        WebElement usernameLink = driver.findElement(By.partialLinkText(USERNAME));
        assertEquals(String.format("Hello, %s", USERNAME), usernameLink.getText().replace("\n", " ").trim());

        driver.quit();
    }
}
