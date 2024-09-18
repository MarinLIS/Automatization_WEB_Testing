package educational.materials.homeworks.homework_03;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ProfilePage {

    private final SelenideElement fullNameInAdditionalInfo = $x("//h3/following-sibling::div //div[contains(text(), 'Full name')]/following-sibling::div");
    private final SelenideElement fullNameUnderAvatar = $("div.mdc-card h2");

    public String getFullNameInAdditionalInfo() {
        return fullNameInAdditionalInfo.should(Condition.visible).text();
    }

    public String getFullNameUnderAvatar() {
        return fullNameUnderAvatar.should(Condition.visible).text();
    }
}