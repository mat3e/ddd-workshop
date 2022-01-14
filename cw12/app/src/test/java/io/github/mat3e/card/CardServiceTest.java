package io.github.mat3e.card;

import io.github.mat3e.card.vo.CardId;
import io.github.mat3e.card.vo.PersonId;
import io.github.mat3e.event.customer.CustomerCreated;
import io.github.mat3e.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class CardServiceTest {
    private static final PersonId PERSON_ID = PersonId.of(11);
    private static final CardId CARD_ID = CardId.of("1234567890123456", PERSON_ID.getId());
    private static final int LIMIT = 100;
    private CardService toTest;

    @BeforeEach
    void setup() {
        toTest = new CardConfiguration().service();
        toTest.applyCustomerInfo(new CustomerCreated("" + PERSON_ID.getId(), "Jan", "Nowak"));
        toTest.activate(CARD_ID, pln(LIMIT));
    }

    @Test
    void activate_noCustomer_failsAsExpected() {
        // given
        int nonexistentCustomerId = 999;

        // when
        var exception = catchThrowable(() -> toTest.activate(CardId.of("0987654321123456", nonexistentCustomerId), pln(LIMIT)));

        // then
        assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("" + nonexistentCustomerId);
    }

    @Test
    void activate_activated_failsAsExpected() {
        // when
        var exception = catchThrowable(() -> toTest.activate(CARD_ID, pln(LIMIT * 2)));

        // then
        assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("already activated");
    }

    @Test
    void activate_blocked_failsAsExpected() {
        // when
        toTest.block(CARD_ID);
        // and
        var exception = catchThrowable(() -> toTest.activate(CARD_ID, pln(LIMIT * 2)));

        // then (card should be unblocked, not activated)
        assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("already activated");
    }

    @Test
    void withdraw_belowLimit_worksAsExpected() {
        // expect
        assertDoesNotThrow(() -> toTest.withdraw(pln(LIMIT / 2), CARD_ID));
    }

    @Test
    void withdraw_noCurrency_usesDefaultCurrency() {
        // expect
        assertDoesNotThrow(() -> toTest.withdraw(LIMIT / 2, CARD_ID));
    }

    @Test
    void withdraw_aboveLimit_failsAsExpected() {
        // given
        var toWithdrawAboveLimit = pln(1);
        // and
        assertDoesNotThrow(() -> toTest.withdraw(pln(LIMIT), CARD_ID));

        // when
        var exception = catchThrowable(() -> toTest.withdraw(toWithdrawAboveLimit, CARD_ID));

        // then
        assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(toWithdrawAboveLimit.toString());
    }

    @Test
    void withdraw_blockedCard_failsAsExpected() {
        // given
        toTest.block(CARD_ID);

        // when
        var exception = catchThrowable(() -> toTest.withdraw(pln(LIMIT / 2), CARD_ID));

        // then
        assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("blocked");
    }

    @Test
    void block_blockedCard_failsAsExpected() {
        // given
        toTest.block(CARD_ID);

        // when
        var exception = catchThrowable(() -> toTest.block(CARD_ID));

        // then
        assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("blocked");
    }

    private MonetaryAmount pln(int integralPart) {
        return Monetary.getDefaultAmountFactory().setNumber(integralPart).setCurrency("PLN").create().with(Monetary.getDefaultRounding());
    }
}