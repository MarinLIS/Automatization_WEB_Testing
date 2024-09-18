package educational.materials.homeworks.homework_04;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.io.File;
import java.time.Duration;
import java.util.Objects;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ProfilePage {

    private final SelenideElement fullNameInAdditionalInfo = $x("//h3/following-sibling::div"
                                                                + "//div[contains(text(), 'Full name')]/following-sibling::div");
    private final SelenideElement fullNameUnderAvatar = $("div.mdc-card h2");
    private final SelenideElement editProfileButton = $("button[title='More options']");
    private final SelenideElement formEditProfile = $("form#update-item");
    private final SelenideElement inputAvatar = formEditProfile.$("input[type='file']");
    private final SelenideElement inputBirthDate = formEditProfile.$("input.mdc-text-field__input[type='date']");
    private final SelenideElement saveFormEditProfileButton = formEditProfile.$("button[type='submit']");
    private final SelenideElement closeFormEditProfileButton = $x("//button[text()='close']");


    public String getFullNameInAdditionalInfo() {
        return fullNameInAdditionalInfo.should(Condition.visible).text();
    }

    public String getFullNameUnderAvatar() {
        return fullNameUnderAvatar.should(Condition.visible).text();
    }

    public void editProfileButton() {
        editProfileButton.should(Condition.visible).click();
    }

    public void uploadNewAvatar(File file) {
        inputAvatar.should(Condition.visible).uploadFile(file);
    }

    public String getAvatarValue() {
        String inputValue = inputAvatar.should(visible).getValue();
        return Objects.requireNonNull(inputValue).substring(inputValue.lastIndexOf("\\") + 1);
    }

    public void setBirthDate(String birthDate) {
        inputBirthDate.should(Condition.visible, Duration.ofSeconds(10)).clear();
        $("input.mdc-text-field__input[type='date']").shouldHave(exactText(""));
        $("input.mdc-text-field__input[type='date']").shouldBe(visible, Duration.ofSeconds(30));
        inputBirthDate.should(visible).setValue(birthDate);
    }


    public void saveFormEditProfileButton() {
        saveFormEditProfileButton.should(visible).click();
    }

    public void closeFormEditProfileButton() {
        closeFormEditProfileButton.should(visible).click();
    }

    public SelenideElement getBirthDateInfo(String birthDate) {
        return $x(String.format("//div[text()='%s']", birthDate)).shouldBe(visible, Duration.ofSeconds(10));
    }
}