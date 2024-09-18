package educational.materials.homeworks.homework_02;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MainPage {

    private final WebDriverWait wait;

    public MainPage(WebDriver driver, WebDriverWait wait) {
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }
    @FindBy(css = "nav li.mdc-menu-surface--anchor a")
    private WebElement usernameInMenu;

    // Group table block
    @FindBy(xpath = "//*[@id='create-btn']")
    private WebElement createGroupButton;

    @FindBy(xpath = "//*[@type='text']")
    private WebElement groupNameField;

    @FindBy(css = "form div.submit button")
    private WebElement submitButtonOnModalWindow;

    @FindBy(xpath = "//span[text()='Creating Study Group']"
                    + "//ancestor::div[contains(@class, 'form-modal-header')]//button")
    private WebElement closeCreateGroupIcon;

    @FindBy(xpath = "//table[@aria-label='Tutors list']/tbody/tr")
    private List<WebElement> rowsInGroupTable;


    // Students table block
    @FindBy(xpath = "//*[@type='number']")
    private WebElement creatingNewLoginsStudentsInput;

    @FindBy(css = "div#generateStudentsForm-content div.submit button")
    private WebElement saveNumberNewLoginsStudents;

    @FindBy(xpath = "//h2[@id='generateStudentsForm-title']/../button")
    private WebElement closeNewLoginsStudentsForm;

    @FindBy(xpath = "//table[@aria-label='User list']/tbody/tr")
    private List<WebElement> rowsInStudentsIdentitiesTable;

    public WebElement waitGroupTitleByText(String title) {
        String xpath = String.format("//table[@aria-label='Tutors list']/tbody//td[text()='%s']", title);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    public void createGroup(String groupName) {
        wait.until(ExpectedConditions.visibilityOf(createGroupButton)).click();
        wait.until(ExpectedConditions.visibilityOf(groupNameField)).sendKeys(groupName);
        wait.until(ExpectedConditions.textToBePresentInElementValue(groupNameField, groupName));
        submitButtonOnModalWindow.click();
        waitGroupTitleByText(groupName);
    }

    public void closeCreateGroupModalWindow() {
        closeCreateGroupIcon.click();
        wait.until(ExpectedConditions.invisibilityOf(closeCreateGroupIcon));
    }

    public String getUsernameLabelText() {
        return wait.until(ExpectedConditions.visibilityOf(usernameInMenu))
                .getText().replace("\n", " ");
    }

    public void clickTrashIconOnGroupWithTitle(String title) {
        getRowByTitle(title).clickTrashIcon();
    }

    public void clickRestoreFromTrashIconOnGroupWithTitle(String title) {
        getRowByTitle(title).clickRestoreFromTrashIcon();
    }

    public String getStatusOfGroupWithTitle(String title) {
        return getRowByTitle(title).getStatus();
    }

    private ElementGroupTablePage getRowByTitle(String title) {
        return rowsInGroupTable.stream()
                .map(ElementGroupTablePage::new)
                .filter(x -> x.getTitle().equals(title))
                .findFirst()
                .orElseThrow();
    }


    public void clickOnCreatingNewLoginsStudentsByTitle(String title) {
        getRowByTitle(title).clickOnCreatingNewLoginsStudentsIcon();
    }

    public void enteringTheNumberOfNewLoginsStudents(int studentQuantity) {
        wait.until(ExpectedConditions.visibilityOf(creatingNewLoginsStudentsInput))
                .sendKeys(String.valueOf(studentQuantity));
    }

    public void clickSaveNumberNewLoginsStudents(){
        wait.until(ExpectedConditions.visibilityOf(saveNumberNewLoginsStudents)).click();
    }

    public void clickCloseNewLoginsStudentsForm() {
        closeNewLoginsStudentsForm.click();
        wait.until(ExpectedConditions.invisibilityOf(closeNewLoginsStudentsForm));
    }

    public void clickOnStudentsIdentitiesByTitle(String title) {
        getRowByTitle(title).clickOnStudentsIdentitiesIcon();
    }

    public String getStudentUsernameByIndex(int studentIndex){
        wait.until(ExpectedConditions.visibilityOfAllElements(rowsInStudentsIdentitiesTable));
        return rowsInStudentsIdentitiesTable.stream()
                .map(ElementStudentTablePage::new)
                .toList().get(studentIndex).getName();
    }

    public void waitForChangeNumberOfLoginsStudents(String groupName, int quantityStudent) {
        getRowByTitle(groupName).waitForChangeNumberOfLoginsStudents(quantityStudent);
    }

    public void clickTrashIconOnStudentByUsername(String username) {
        getStudentRowUsername(username).clickTrashIcon();
    }

    public void clickRestoreFromTrashIconOnStudentByUsername(String username) {
        getStudentRowUsername(username).clickRestoreFromTrashIcon();
    }

    public String getStatusOfStudentByUsername(String username) {
        return getStudentRowUsername(username).getStatus();
    }

    private ElementStudentTablePage getStudentRowUsername(String name) {
        wait.until(ExpectedConditions.visibilityOfAllElements(rowsInStudentsIdentitiesTable));
        return rowsInStudentsIdentitiesTable.stream()
                .map(ElementStudentTablePage::new)
                .filter(row -> row.getName().equals(name))
                .findFirst().orElseThrow();
    }
}