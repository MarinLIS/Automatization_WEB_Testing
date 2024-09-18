package educational.materials.homeworks.homework_02;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private final WebDriverWait wait;

    @FindBy(xpath="//*[@type='text']")
    private WebElement usernameField;

    @FindBy(xpath="//*[@type='password']")
    private WebElement passwordField;

    @FindBy(xpath="//*[@class='mdc-button__label']")
    private WebElement loginButton;

    @FindBy(xpath="//*[@class='error-block svelte-uwkxn9']")
    private WebElement errorBlock;

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        PageFactory.initElements(driver, this);
        this.wait = wait;
    }

    public void login(String username, String password) {
        typeUsernameInField(username);
        typePasswordInField(password);
        clickLoginButton();
    }

    public void typeUsernameInField(String username) {
        wait.until(ExpectedConditions.visibilityOf(usernameField)).sendKeys(username);
    }

    public void typePasswordInField(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordField)).sendKeys(password);
    }

    public void clickLoginButton() {
        wait.until(ExpectedConditions.visibilityOf(loginButton)).click();
    }

    public String getErrorBlockText() {
        return wait.until(ExpectedConditions.visibilityOf(errorBlock))
                .getText()
                .replace("\n", " ");
    }
}
