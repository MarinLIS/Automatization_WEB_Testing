package educational.materials.lessons.lesson_03;

import com.codeborne.selenide.*;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class ScreenCast {
    public static void main(String[] args) {
        SelenideElement selenideElementCss = $("div.item");
        SelenideElement selenideElementXpath = $x("//div[@class='item']");

        ElementsCollection selenideElementsCss = $$(".submit");
        ElementsCollection selenideElementsXpath = $$x("//div[@class='submit']");

        SelenideElement first = selenideElementsXpath.first();
        ElementsCollection filter = selenideElementsXpath.filter(visible);

        SelenideElement element = selenideElementCss.$(".child");


        SelenideElement selenideElemCss = $("div.item").shouldNotBe(visible);
        ElementsCollection selenideElemXpath = $$x("//div[@class='item']").shouldHave(sizeGreaterThan(0));
    }
}
