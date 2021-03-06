package ru.netology.web.page;

import static com.codeborne.selenide.Condition.*;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");

    public VerificationPage(){
        codeField.waitUntil(Condition.visible, 15000);
    }

    public PersonalArea validVerify(DataHelper.VerificationCode verificationCode){
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
        return page(PersonalArea.class);
    }
}
