package educational.materials.homeworks.homework_03;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import educational.materials.AbstractTestClass;
import org.junit.jupiter.api.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SecondOption extends AbstractTestClass {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        Selenide.open(getBaseUrl());
        driver = WebDriverRunner.getWebDriver();
    }

    @Test
    @Order(2)
    public void createGroupTest() {
        loginToAccount();
        String groupTestName = "TestGroup_" + System.currentTimeMillis();
        MainPage mainPage = Selenide.page(MainPage.class);
        mainPage.createGroup(groupTestName);
    }

    @Test
    void authorizationWithoutLoginAndPasswordTest() {
        LoginPage loginPage = Selenide.page(LoginPage.class);
        loginPage.clickLoginButton();
        assertEquals("401 Invalid credentials.", loginPage.getErrorBlockText());
        saveScreenshot("authorizationWithoutLoginAndPassword.png");
    }

    @Test
    @Order(1)
    void groupStatusActiveOrInactiveTest() {
        loginToAccount();
        String groupTestName = "TestGroup_" + System.currentTimeMillis();
        MainPage mainPage = Selenide.page(MainPage.class);
        mainPage.createGroup(groupTestName);
        mainPage.closeCreateGroupModalWindow();
        assertEquals("active", mainPage.getStatusOfGroupWithTitle(groupTestName));
        mainPage.clickTrashIconOnGroupWithTitle(groupTestName);
        assertEquals("inactive", mainPage.getStatusOfGroupWithTitle(groupTestName));
        mainPage.clickRestoreFromTrashIconOnGroupWithTitle(groupTestName);
        assertEquals("active", mainPage.getStatusOfGroupWithTitle(groupTestName));

    }

    @Test
    void studentStatusTest() {
        loginToAccount();
        String groupName = "TestGroup_" + System.currentTimeMillis();
        MainPage mainPage = Selenide.page(MainPage.class);
        mainPage.createGroup(groupName);
        mainPage.closeCreateGroupModalWindow();
        int studentQuantity = 2;
        mainPage.clickOnCreatingNewLoginsStudentsByTitle(groupName);
        mainPage.enteringTheNumberOfNewLoginsStudents(studentQuantity);
        mainPage.clickSaveNumberNewLoginsStudents();
        mainPage.clickCloseNewLoginsStudentsForm();
        mainPage.waitForChangeNumberOfLoginsStudents(groupName, studentQuantity);
        mainPage.clickOnStudentsIdentitiesByTitle(groupName);
        int studentIndex = 0;
        String studentUsername = mainPage.getStudentUsernameByIndex(studentIndex);
        assertEquals("active", mainPage.getStatusOfStudentByUsername(studentUsername));
        mainPage.clickTrashIconOnStudentByUsername(studentUsername);
        assertEquals("block", mainPage.getStatusOfStudentByUsername(studentUsername));
        mainPage.clickRestoreFromTrashIconOnStudentByUsername(studentUsername);
        assertEquals("active", mainPage.getStatusOfStudentByUsername(studentUsername));
        saveScreenshot("studentStatus.png");
    }

    @Test
    void valueFullNameInProfilePageTest() {
        loginToAccount();
        MainPage mainPage = Selenide.page(MainPage.class);
        mainPage.clickProfileButton();
        ProfilePage profilePage = Selenide.page(ProfilePage.class);
        assertEquals(getFull_name(), profilePage.getFullNameInAdditionalInfo());
        assertEquals(getFull_name(), profilePage.getFullNameUnderAvatar());
    }


    private void loginToAccount() {
        LoginPage loginPage = Selenide.page(LoginPage.class);
        loginPage.login(getUsername(), getPassword());
        MainPage mainPage = Selenide.page(MainPage.class);
        assertTrue(mainPage.getUsernameLabelText().contains(getUsername()));
    }

    private void saveScreenshot(String fileName) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            ImageIO.write(ImageIO.read(screenshot), "png", new File("src\\test\\resources\\screenshots_homework_03\\" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @AfterEach
    public void closeApp() {
        WebDriverRunner.closeWebDriver();
    }
}