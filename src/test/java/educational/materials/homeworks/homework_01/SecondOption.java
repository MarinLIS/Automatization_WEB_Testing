package educational.materials.homeworks.homework_01;

import educational.materials.AbstractTestClass;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class SecondOption extends AbstractTestClass {

    private WebDriver driver;
    private WebDriverWait wait;
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
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    public void loginToAccount() {
        driver.get(getBaseUrl());
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("form#login input[type='text']"))).sendKeys(USERNAME);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("form#login input[type='password']"))).sendKeys(PASSWORD);
        WebElement loginButton = driver.findElement(By.cssSelector("form#login button"));
        loginButton.click();
        wait.until(ExpectedConditions.invisibilityOf(loginButton));
    }

    @Test
    public void createGroupTest() {
        loginToAccount();
        WebElement createGroupButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#create-btn")));
        createGroupButton.click();
        WebElement groupNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='text']")));
        String uniqueGroupName = "TestGroup_" + System.currentTimeMillis();
        groupNameField.sendKeys(uniqueGroupName);
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".submit .mdc-button")));
        saveButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mdc-data-table__table-container")));
        String tableTitleXpath = "//td[contains(text(), '%s')]";
        WebElement expectedTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(tableTitleXpath, uniqueGroupName))));
        Assertions.assertTrue(expectedTitle.isDisplayed());

        saveScreenshot("group_creation_screenshot.png");
    }

    @AfterEach
    public void tearDown() {
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
