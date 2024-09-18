package educational.materials.seminars.seminar_01;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Example_01 {

    public static void main(String[] args) {

        String pathToChromeDriver = "src\\main\\resources\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", pathToChromeDriver);
        String pathToGeckoDriver = "src\\main\\resources\\geckodriver.exe";
        System.setProperty("webdriver.firefox.driver", pathToGeckoDriver);

//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--start-maximized");

        WebDriver driverChrome = new ChromeDriver();
        driverChrome.manage().window().maximize();
        driverChrome.get("https://www.google.com");
        System.out.println("Title at Chrome: " + driverChrome.getTitle());
        driverChrome.quit();

    }
}
