package educational.materials.homeworks.homework_02;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.function.Function;

public class ElementGroupTablePage {

    private final WebElement root;

    public ElementGroupTablePage(WebElement root) {
        this.root = root;
    }

    private void waitUntil(Function<WebElement, WebElement> condition) {
        new FluentWait<>(root)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class)
                .until(condition);
    }


    public String getTitle() {
        return root.findElement(By.xpath("./td[2]")).getText();
    }

    public String getStatus() {
        return root.findElement(By.xpath("./td[3]")).getText();
    }

    public void clickTrashIcon() {
        root.findElement(By.xpath("./td/button[text()='delete']")).click();
        waitUntil(root -> root.findElement(By.xpath("./td/button[text()='restore_from_trash']")));
    }

    public void clickRestoreFromTrashIcon() {
        root.findElement(By.xpath("./td/button[text()='restore_from_trash']")).click();
        waitUntil(root -> root.findElement(By.xpath("./td/button[text()='delete']")));
    }


    public void clickOnCreatingNewLoginsStudentsIcon() {
        root.findElement(By.xpath("//*[@class='material-icons mdc-button__icon']")).click();
    }

    public void waitForChangeNumberOfLoginsStudents(int number) {
        waitUntil(root -> root.findElement(By.xpath("./td[4]//span[text()='%s']".formatted(number))));
    }

    public void clickOnStudentsIdentitiesIcon() {
        root.findElement(By.xpath(".//td/button[contains(., 'zoom_in')]")).click();
    }
}
