package io.github.mat3e.card.acl.ratio;

import org.springframework.stereotype.Service;

import javax.money.CurrencyUnit;
import java.math.BigDecimal;

@Service
class AlwaysBelow4MoneyConverter implements MoneyConverter {
    @Override
    public BigDecimal getRatio(CurrencyUnit from, CurrencyUnit to) {
        return new BigDecimal("3.95687");
    }
}
