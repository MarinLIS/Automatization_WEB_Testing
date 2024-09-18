package educational.materials.seminars.seminar_04;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import educational.materials.AbstractTestClass;
import educational.materials.seminars.seminar_04.page_object_model.LoginPage;
import educational.materials.seminars.seminar_04.page_object_model.MainPage;
import educational.materials.seminars.seminar_04.page_object_model.ProfilePage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Пример использования самых базовых методов библиотеки Selenium.
 */
public class Example_02_5 extends AbstractTestClass {

    private LoginPage loginPage;
    private MainPage mainPage;

    private static final String USERNAME = getUsername();
    private static final String PASSWORD = getPassword();
    private static final String FULL_NAME = getFull_name();

    @BeforeAll
    public static void setupClass() {

        Configuration.remote = "http://localhost:4444/wd/hub";
        Map<String, Object> options = new HashMap<>();
        options.put("enableVNC", true);
        options.put("enableLog", true);
        Configuration.browserCapabilities.setCapability("selenoid:options", options);

        // Первый пункт задания
        Configuration.browser = "chrome";
        Configuration.browserVersion = "127";

        // Второй пункт задания
//        Configuration.browser = "chrome";
//        Configuration.browserVersion = "126";

        // Третий пункт задания
//        Configuration.browser = "firefox";
//        Configuration.browserVersion = "125";


    }

    @BeforeEach
    public void setupTest() {
        // Навигация на https://test-stand.gb.ru/login с помощью Selenide
        Selenide.open(getBaseUrl());
        // Сохраняем WebDriver из Selenide
        WebDriver driver = WebDriverRunner.getWebDriver();
        // Объект созданного Page Object
        loginPage = new LoginPage(driver, new WebDriverWait(driver, Duration.ofSeconds(30)));
    }

    @Test
    public void testLoginWithEmptyFields() {
        // Клик на кнопку LOGIN без ввода данных в поля
        loginPage.clickLoginButton();
        // Проверка, что появился блок с ожидаемой ошибкой
        assertEquals("401 Invalid credentials.", loginPage.getErrorBlockText());
        Selenide.sleep(5000);
    }

    @Test
    public void testAddingGroupOnMainPage() {
        // Логин в систему с помощью метода из класса Page Object
        loginPage.login(USERNAME, PASSWORD);
        // Инициализация объекта класса MainPage
        mainPage = Selenide.page(MainPage.class);
        assertTrue(mainPage.getUsernameLabelText().contains(USERNAME));
        // Создание группы. Даём ей уникальное имя, чтобы в каждом запуске была проверка нового имени
        String groupTestName = "New Test Group " + System.currentTimeMillis();
        mainPage.createGroup(groupTestName);
        // Проверка, что группа создана и находится в таблице
        assertTrue(mainPage.waitAndGetGroupTitleByText(groupTestName).isDisplayed());
    }

    @Test
    void testArchiveGroupOnMainPage() {
        // Обычный логин + создание группы
        loginPage.login(USERNAME, PASSWORD);
        mainPage = Selenide.page(MainPage.class);
        assertTrue(mainPage.getUsernameLabelText().contains(USERNAME));
        String groupTestName = "New Test Group " + System.currentTimeMillis();
        mainPage.createGroup(groupTestName);
        // Требуется закрыть модальное окно
        mainPage.closeCreateGroupModalWindow();
        // Изменение созданной группы с проверками
        assertEquals("active", mainPage.getStatusOfGroupWithTitle(groupTestName));
        mainPage.clickTrashIconOnGroupWithTitle(groupTestName);
        assertEquals("inactive", mainPage.getStatusOfGroupWithTitle(groupTestName));
        mainPage.clickRestoreFromTrashIconOnGroupWithTitle(groupTestName);
        assertEquals("active", mainPage.getStatusOfGroupWithTitle(groupTestName));
    }

    @Test
    void testBlockingStudentInTableOnMainPage() {
        // Обычный логин + создание группы
        loginPage.login(USERNAME, PASSWORD);
        mainPage = Selenide.page(MainPage.class);
        assertTrue(mainPage.getUsernameLabelText().contains(USERNAME));
        String groupTestName = "New Test Group " + System.currentTimeMillis();
        mainPage.createGroup(groupTestName);
        // Требуется закрыть модальное окно
        mainPage.closeCreateGroupModalWindow();
        // Добавление студентов
        int studentsCount = 3;
        mainPage.clickAddStudentsIconOnGroupWithTitle(groupTestName);
        mainPage.typeAmountOfStudentsInCreateStudentsForm(studentsCount);
        mainPage.clickSaveButtonOnCreateStudentsForm();
        mainPage.closeCreateStudentsModalWindow();
        mainPage.waitStudentsCount(groupTestName, studentsCount);
        mainPage.clickZoomInIconOnGroupWithTitle(groupTestName);
        // Проверка переходов статуса первого студента из таблицы
        String firstGeneratedStudentName = mainPage.getStudentNameByIndex(0);
        assertEquals("active", mainPage.getStatusOfStudentWithName(firstGeneratedStudentName));
        mainPage.clickTrashIconOnStudentWithName(firstGeneratedStudentName);
        assertEquals("block", mainPage.getStatusOfStudentWithName(firstGeneratedStudentName));
        mainPage.clickRestoreFromTrashIconOnStudentWithName(firstGeneratedStudentName);
        assertEquals("active", mainPage.getStatusOfStudentWithName(firstGeneratedStudentName));
    }

    @Test
    void testFullNameOnProfilePage() {
        // Логин в систему с помощью метода из класса Page Object
        loginPage.login(USERNAME, PASSWORD);
        // Инициализация объекта класса MainPage
        mainPage = Selenide.page(MainPage.class);
        assertTrue(mainPage.getUsernameLabelText().contains(USERNAME));
        // Навигация на Profile page
        mainPage.clickUsernameLabel();
        mainPage.clickProfileLink();
        // Инициализация ProfilePage с помощью Selenide
        ProfilePage profilePage = Selenide.page(ProfilePage.class);
        assertEquals(FULL_NAME, profilePage.getFullNameFromAdditionalInfo());
        assertEquals(FULL_NAME, profilePage.getFullNameFromAvatarSection());
    }

    @Test
    public void testAvatarOnEditingPopupOnProfilePage() {
        // Логин в систему с помощью метода из класса Page Object
        loginPage.login(USERNAME, PASSWORD);
        // Инициализация объекта класса MainPage
        mainPage = Selenide.page(MainPage.class);
        assertTrue(mainPage.getUsernameLabelText().contains(USERNAME));
        // Навигация на Profile page
        mainPage.clickUsernameLabel();
        mainPage.clickProfileLink();
        // Инициализация ProfilePage с помощью Selenide
        ProfilePage profilePage = Selenide.page(ProfilePage.class);
        profilePage.clickEditIconInAvatarSection();
        //Проверка поля до загрузки файла
        assertEquals("", profilePage.getFullNameFromAdditionalSettingPopup());
        // Здесь подставьте свой путь до файла
        String filePath = "\\src\\test\\resources\\test_data\\TopAvatar.png";
        profilePage.uploadPictureFileToAvatarField(filePath);
        Selenide.sleep(60000);
        // Проверка поля после загрузки файла, сравниваем имя файла
        assertEquals(filePath.substring(filePath.lastIndexOf("\\") + 1), profilePage.getFullNameFromAdditionalSettingPopup());
    }

    @AfterEach
    public void teardown() {
        // Закрываем все окна браузера и процесс драйвера
        WebDriverRunner.closeWebDriver();
    }

}


