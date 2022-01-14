package io.github.mat3e.card.acl.ratio;

import javax.money.CurrencyUnit;
import java.math.BigDecimal;

public interface MoneyConverter {
    BigDecimal getRatio(CurrencyUnit from, CurrencyUnit to);
}
