package io.github.mat3e.card;

import io.github.mat3e.card.acl.ratio.MoneyConverter;
import io.github.mat3e.card.vo.CardId;
import io.github.mat3e.card.vo.PersonId;
import io.github.mat3e.event.customer.CustomerCreated;
import io.github.mat3e.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {
    private static final PersonId PERSON_ID = PersonId.of(11);
    private static final CardId CARD_ID = CardId.of("1234567890123456", PERSON_ID.getId());
    private static final int LIMIT = 100;
    private CardService toTest;

    @Mock
    MoneyConverter converter;

    @BeforeEach
    void setup() {
        toTest = new CardConfiguration().service(converter);
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
        assertDoesNotThrow(() -> toTest.withdraw(LIMIT / 2, CARD_ID));
    }

    @Test
    void withdraw_differentCurrency_chargesExtra() {
        // given
        given(converter.getRatio(any(CurrencyUnit.class), any(CurrencyUnit.class)))
                .willReturn(new BigDecimal("3.95687"));

        // when
        var exception = catchThrowable(() -> toTest.withdraw(usd(LIMIT / 4), CARD_ID));

        // then
        assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("having " + pln(LIMIT));
    }

    @Test
    void withdraw_aboveLimit_failsAsExpected() {
        // given
        int toWithdrawAboveLimit = 1;
        // and
        assertDoesNotThrow(() -> toTest.withdraw(LIMIT, CARD_ID));

        // when
        var exception = catchThrowable(() -> toTest.withdraw(toWithdrawAboveLimit, CARD_ID));

        // then
        assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("" + toWithdrawAboveLimit);
    }

    @Test
    void withdraw_blockedCard_failsAsExpected() {
        // given
        toTest.block(CARD_ID);

        // when
        var exception = catchThrowable(() -> toTest.withdraw(LIMIT / 2, CARD_ID));

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

    private MonetaryAmount usd(int integralPart) {
        return Monetary.getDefaultAmountFactory().setNumber(integralPart).setCurrency("USD").create().with(Monetary.getDefaultRounding());
    }
}