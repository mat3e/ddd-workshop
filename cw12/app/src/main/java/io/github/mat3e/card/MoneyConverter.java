package io.github.mat3e.card;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.math.BigDecimal;

@Converter(autoApply = true)
class MoneyConverter implements AttributeConverter<MonetaryAmount, String> {
    @Override
    public String convertToDatabaseColumn(MonetaryAmount attribute) {
        return attribute.with(Monetary.getDefaultRounding()).toString();
    }

    @Override
    public MonetaryAmount convertToEntityAttribute(String dbData) {
        String[] currencyAndValue = dbData.split(" ");
        return Monetary.getDefaultAmountFactory()
                .setCurrency(currencyAndValue[0])
                .setNumber(new BigDecimal(currencyAndValue[1]))
                .create();
    }
}
