package educational.materials.homeworks.homework_04.part_one;

import com.codeborne.selenide.WebDriverRunner;
import educational.materials.AbstractTestClass;
import educational.materials.homeworks.homework_02.LoginPage;
import educational.materials.homeworks.homework_02.MainPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SecondOptionPartOne extends AbstractTestClass {

    private static WebDriver driver;
    private WebDriverWait wait;
    private LoginPage loginPage;
    private MainPage mainPage;

    private static final String LOGIN = getUsername();
    private static final String PASSWORD = getPassword();
    private static final String BASE_URL = getBaseUrl();
    private static final String SELENOID_URL = "http://localhost:4444/wd/hub";

    @BeforeAll
    public static void setupClass() throws MalformedURLException {
        ChromeOptions browser = new ChromeOptions();
        browser.setCapability("browserVersion", "127.0");
        Map<String, Object> map = new HashMap<>();
        map.put("enableVnc", true);
        map.put("enableLog", true);
        browser.setCapability("selenoid:options", map);
        driver = new RemoteWebDriver(new URL(SELENOID_URL), browser);
    }

    @BeforeEach
    public void setupTest() {
        driver.get(BASE_URL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        loginPage = new LoginPage(driver, wait);
    }

    @Test
    @Order(2)
    public void createGroupTest() {
        loginToAccount();
        String uniqueGroupName = "TestGroup_" + System.currentTimeMillis();
        mainPage.createGroup(uniqueGroupName);
        mainPage.closeCreateGroupModalWindow();
        assertEquals("active", mainPage.getStatusOfGroupWithTitle(uniqueGroupName));
        mainPage.clickTrashIconOnGroupWithTitle(uniqueGroupName);
        assertEquals("inactive", mainPage.getStatusOfGroupWithTitle(uniqueGroupName));
        mainPage.clickRestoreFromTrashIconOnGroupWithTitle(uniqueGroupName);
        assertEquals("active", mainPage.getStatusOfGroupWithTitle(uniqueGroupName));
        saveScreenshot("createGroup.png");
    }

    @Test
    @Order(1)
    public void authorizationWithoutLoginAndPasswordTest() {
        loginPage.clickLoginButton();
        assertEquals("401 Invalid credentials.", loginPage.getErrorBlockText());
        saveScreenshot("authorizationWithoutLoginAndPassword.png");
    }

    @Test
    public void studentStatusTest() {
        loginToAccount();
        String uniqueGroupName = "TestGroup_" + System.currentTimeMillis();
        mainPage.createGroup(uniqueGroupName);
        mainPage.closeCreateGroupModalWindow();
        int studentQuantity = 2;
        mainPage.clickOnCreatingNewLoginsStudentsByTitle(uniqueGroupName);
        mainPage.enteringTheNumberOfNewLoginsStudents(studentQuantity);
        mainPage.clickSaveNumberNewLoginsStudents();
        mainPage.clickCloseNewLoginsStudentsForm();
        mainPage.waitForChangeNumberOfLoginsStudents(uniqueGroupName, studentQuantity);
        mainPage.clickOnStudentsIdentitiesByTitle(uniqueGroupName);
        int studentIndex = 0;
        String studentUsername = mainPage.getStudentUsernameByIndex(studentIndex);
        assertEquals("active", mainPage.getStatusOfStudentByUsername(studentUsername));
        mainPage.clickTrashIconOnStudentByUsername(studentUsername);
        assertEquals("block", mainPage.getStatusOfStudentByUsername(studentUsername));
        mainPage.clickRestoreFromTrashIconOnStudentByUsername(studentUsername);
        assertEquals("active", mainPage.getStatusOfStudentByUsername(studentUsername));
        saveScreenshot("studentStatus.png");
    }

    @AfterEach
    public void tearDown() {
        WebDriverRunner.closeWebDriver();
    }

    public void loginToAccount() {
        loginPage.login(getUsername(), getPassword());
        mainPage = new MainPage(driver, wait);
        assertTrue(mainPage.getUsernameLabelText().contains(getUsername()));
    }

    private void saveScreenshot(String fileName) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            ImageIO.write(ImageIO.read(screenshot), "png", new File("src\\test\\resources\\screenshots_homework_02\\" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
