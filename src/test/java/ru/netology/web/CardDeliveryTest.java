package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    public String generateDate(int days, String pattern){
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @BeforeEach
    void setup(){
        Selenide.open("http://localhost:9999");
    }

    @Test
    void shouldSendForm() {
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='city'] input").setValue("Самара");
        $("[data-test-id='date'] .input__control")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE)
                .setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Мария Иванова");
        $("[data-test-id='phone'] input").setValue("+79270000000");
        $("[data-test-id='agreement']").click();
        $(".button_theme_alfa-on-white").click();
        $("[data-test-id='notification'] .notification__title")
                .shouldHave(Condition.text("Успешно!"), Duration.ofSeconds(15))
                .shouldBe(visible);
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(visible);
    }

}
