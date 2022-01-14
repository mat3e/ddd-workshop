package io.github.mat3e.cardinfo;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;

@Validated
@RestController
@RequestMapping("/customers/{customerId}/cards/{cardNumber}")
class CardInfoController {
    private final CardRepository repository;

    CardInfoController(CardRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    ResponseEntity<Card> getCard(@PathVariable int customerId, @Pattern(regexp = "\\d{16}") @PathVariable String cardNumber) {
        return ResponseEntity.of(repository.findActive(customerId, cardNumber));
    }
}
