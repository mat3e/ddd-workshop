package io.github.mat3e.card;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
class CardConfiguration {
    @SuppressWarnings("SpringConfigurationProxyMethods")
    CardService service() {
        return service(
                new InMemoryActiveCardRepository(),
                new InMemoryRestrictedCardRepository(),
                new InMemoryCustomerRepository()
        );
    }

    @Bean
    @Profile("!memory")
    CardService service(
            ActiveCardRepository activeCardRepository,
            RestrictedCardRepository restrictedCardRepository,
            ExistingCustomerRepository existingCustomerRepository
    ) {
        return new CardService(activeCardRepository, restrictedCardRepository, existingCustomerRepository);
    }
}
