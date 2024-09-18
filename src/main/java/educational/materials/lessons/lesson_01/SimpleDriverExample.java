package educational.materials.lessons.lesson_01;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class SimpleDriverExample {

    public static void main( String[] args ) throws InterruptedException {

        String pathToChromeDriver = "src\\main\\resources\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", pathToChromeDriver);

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.google.com");
        Thread.sleep(5000);

        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File("src/main/resources/screenshot_lesson_01.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver.quit();

    }
}
