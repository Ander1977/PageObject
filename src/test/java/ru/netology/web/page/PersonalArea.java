package ru.netology.web.page;

import static com.codeborne.selenide.Condition.*;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PersonalArea {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cardList = $$(".list .list__item");
    private SelenideElement fieldAmount = $("[data-test-id=amount] input");
    private SelenideElement fieldFrom = $("[data-test-id=from] input");
    private SelenideElement fieldTo = $("[data-test-id=to] input");
    private SelenideElement buttonAction = $("[data-test-id=action-transfer]");
    private SelenideElement buttonActionReload = $("[data-test-id=action-reload]");


    public PersonalArea() {
        heading.waitUntil(Condition.visible, 15000);
    }

    private void openTransferForm(String lastFourDigit) {
        cardList.findBy(Condition.text(lastFourDigit)).$(withText("Пополнить")).click();
        $(withText("Пополнение карты")).shouldBe(visible);
    }

    private void setAmountValue(int amount) {
        fieldAmount.clear();
        fieldAmount.setValue(String.valueOf(amount));
    }

    private void setFromValue(String cardNumber) {
        fieldFrom.setValue(cardNumber);
    }

    private void doTransfer() {
        fieldTo.shouldBe(disabled);
        buttonAction.click();
    }

    public void updateBalance() {
        heading.waitUntil(Condition.visible, 15000);
        buttonActionReload.click();
    }

    public int getBalance(String lastFourDigit) {
        String[] cardInfo = cardList.findBy(Condition.text(lastFourDigit)).getText().split(" ");
        return Integer.parseInt(cardInfo[5]);
    }

    public void moneyTransfer(String lastFourDigitCardTo, String cardNumberFrom, int amount) {
        openTransferForm(lastFourDigitCardTo);
        setAmountValue(amount);
        setFromValue(cardNumberFrom);
        doTransfer();
    }
}
