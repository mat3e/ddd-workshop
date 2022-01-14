package io.github.mat3e.card;

import io.github.mat3e.card.acl.ratio.MoneyConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
class CardConfiguration {
    @SuppressWarnings("SpringConfigurationProxyMethods")
    CardService service(MoneyConverter converter) {
        return service(
                new InMemoryActiveCardRepository(),
                new InMemoryRestrictedCardRepository(),
                new InMemoryCustomerRepository(),
                converter
        );
    }

    @Bean
    @Profile("!memory")
    CardService service(
            ActiveCardRepository activeCardRepository,
            RestrictedCardRepository restrictedCardRepository,
            ExistingCustomerRepository existingCustomerRepository,
            MoneyConverter converter
    ) {
        return new CardService(
                activeCardRepository,
                restrictedCardRepository,
                existingCustomerRepository,
                new CardDomainService(new MoneyConvertingPolicyFactory(existingCustomerRepository)),
                converter
        );
    }
}
