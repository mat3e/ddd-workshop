package io.github.mat3e.card;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
class CardConfiguration {
    @SuppressWarnings("SpringConfigurationProxyMethods")
    CardService service() {
        return service(new InMemoryActiveCardRepository(), new InMemoryRestrictedCardRepository());
    }

    @Bean
    @Profile("!memory")
    CardService service(ActiveCardRepository activeCardRepository, RestrictedCardRepository restrictedCardRepository) {
        return new CardService(activeCardRepository, restrictedCardRepository);
    }
}
