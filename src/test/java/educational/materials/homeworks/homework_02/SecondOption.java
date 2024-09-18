package educational.materials.homeworks.homework_02;

import educational.materials.AbstractTestClass;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SecondOption extends AbstractTestClass {

    private WebDriver driver;
    private WebDriverWait wait;
    private static String USERNAME;
    private static String PASSWORD;
    private LoginPage loginPage;
    private MainPage mainPage;

    @BeforeAll
    public static void setupClass() {
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        USERNAME = getUsername();
        PASSWORD = getPassword();
    }

    @BeforeEach
    public void setupTest() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.get(getBaseUrl());
        driver.manage().window().maximize();
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
    }

    @Test
    @Order(1)
    public void authorizationWithoutLoginAndPasswordTest() {
        loginPage.clickLoginButton();
        assertEquals("401 Invalid credentials.", loginPage.getErrorBlockText());
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
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    public void loginToAccount() {
        loginPage.login(USERNAME, PASSWORD);
        mainPage = new MainPage(driver, wait);
        assertTrue(mainPage.getUsernameLabelText().contains(USERNAME));
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
// TODO saveScreenshot("group_creation_screenshot.png");