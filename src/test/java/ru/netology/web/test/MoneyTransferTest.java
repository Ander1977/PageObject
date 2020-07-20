package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.PersonalArea;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {
    private final String cardNunber0001 = "5559 0000 0000 0001";
    private final String cardNunber0002 = "5559 0000 0000 0002";

    private PersonalArea openPersonalArea() {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode();
        return verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldlogin() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode);
    }

    @Test
    public void shouldTransferFromCard2ToCard1() {
        PersonalArea page = openPersonalArea();
        page.updateBalance();
        int currentBalance = page.getBalance("0002");
        int expected = currentBalance + 4000;
        page.moneyTransfer("0002", cardNunber0001, 4000);
        page.moneyTransfer("0001", cardNunber0002, - 4000);
        page.updateBalance();
        assertEquals(expected, page.getBalance("0002"));
    }

    @Test
    public void shouldTransferFromCard1ToCard2() {
        PersonalArea page = openPersonalArea();
        page.updateBalance();
        int currentBalance = page.getBalance("0001");
        int expected = currentBalance + 9000;
        page.moneyTransfer("0001", cardNunber0002, 9000);
        page.moneyTransfer("0002", cardNunber0001, - 9000);
        page.updateBalance();
        assertEquals(expected, page.getBalance("0001"));
    }

    @Test
    public void shouldTransferMoreThanBalance() {
        PersonalArea page = openPersonalArea();
        page.updateBalance();
        int currentBalance = page.getBalance("0002");
        int expected = currentBalance;
        page.moneyTransfer("0002", cardNunber0001, 500000);
        page.updateBalance();
        assertEquals(expected, page.getBalance("0002"));
    }
}
