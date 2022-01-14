package io.github.mat3e.card;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

// @Configuration
class CardConfiguration {
    @SuppressWarnings("SpringConfigurationProxyMethods")
    CardService service() {
        return service(new InMemoryCardRepository());
    }

    @Bean
    @Profile("!memory")
    CardService service(CardRepository repository) {
        return new CardService(repository);
    }
}
