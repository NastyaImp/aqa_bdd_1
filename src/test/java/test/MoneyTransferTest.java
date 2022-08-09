package test;

import data.DataHelper;
import lombok.val;
import org.junit.jupiter.api.Test;
import page.LoginPage;
import page.VerificationPage;

import java.lang.module.Configuration;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {
    @Test
    void shouldTransferZeroMoneyBetweenCards() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val balanceFirstCardBefore = dashboardPage.getFirstCardBalance();
        val balanceSecondCardBefore = dashboardPage.getSecondCardBalance();
        val transferPage = dashboardPage.secondCard();
        int amount = 0;
        transferPage.transferMoney(amount, DataHelper.FirstCard());
        val balanceFirstCardAfter = dashboardPage.getFirstCardBalance();
        val balanceSecondCardAfter = dashboardPage.getSecondCardBalance();
        assertEquals(balanceFirstCardBefore, (balanceFirstCardAfter - amount));
        assertEquals(balanceSecondCardBefore, (balanceSecondCardAfter + amount));
    }

    @Test
    void shouldTransferAllMoneyBetweenCards() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val balanceFirstCardBefore = dashboardPage.getFirstCardBalance();
        val balanceSecondCardBefore = dashboardPage.getSecondCardBalance();
        val transferPage = dashboardPage.secondCard();
        int amount = 10000;
        transferPage.transferMoney(amount, DataHelper.FirstCard());
        val balanceFirstCardAfter = dashboardPage.getFirstCardBalance();
        val balanceSecondCardAfter = dashboardPage.getSecondCardBalance();
        assertEquals((balanceFirstCardBefore - amount), balanceFirstCardAfter);
        assertEquals((balanceSecondCardBefore + amount), balanceSecondCardAfter);

    }
}
