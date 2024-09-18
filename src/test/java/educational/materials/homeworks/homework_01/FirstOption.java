package educational.materials.homeworks.homework_01;

import educational.materials.AbstractTestClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class FirstOption extends AbstractTestClass {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setup() {
        String pathToChromeDriver = "src\\main\\resources\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", pathToChromeDriver);

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void loginTest() {

        driver.get(getBaseUrl());

        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("form#login input[type='text']")));
        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("form#login input[type='password']")));
        usernameField.sendKeys(getUsername());
        passwordField.sendKeys(getPassword());
        WebElement loginButton = driver.findElement(By.cssSelector("form#login button"));
        loginButton.click();
        wait.until(ExpectedConditions.invisibilityOf(loginButton));
        WebElement usernameLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(getUsername())));
        String actualUsername = usernameLink.getText().replace("\n", " ").trim();
        Assertions.assertEquals(String.format("Hello, %s", getUsername()), actualUsername);

        WebElement createGroupButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("create-btn")));
        createGroupButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='text']")));
        WebElement groupNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='text']")));
        String uniqueGroupName = "TestGroup_" + System.currentTimeMillis();
        groupNameField.sendKeys(uniqueGroupName);
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".submit .mdc-button")));
        saveButton.click();
        WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.mdc-dialog__close")));
        closeButton.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".mdc-dialog--open")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mdc-data-table__table-container")));
        String tableTitleXpath = "//td[contains(text(), '%s')]";
        WebElement expectedTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(tableTitleXpath, uniqueGroupName))));
        Assertions.assertTrue(expectedTitle.isDisplayed());

        saveScreenshot("group_creation_screenshot.png");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    private void saveScreenshot(String fileName) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            ImageIO.write(ImageIO.read(screenshot), "png", new File("src\\test\\resources\\screenshots_homework_01\\" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
