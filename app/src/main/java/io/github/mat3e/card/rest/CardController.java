package io.github.mat3e.card.rest;

import io.github.mat3e.card.CardService;
import io.github.mat3e.card.vo.CardId;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.net.URI;

@Validated
@RestController
@RequestMapping("/customers/{customerId}/cards/{cardNumber}")
class CardController {
    private final CardService service;

    CardController(CardService service) {
        this.service = service;
    }

    @PatchMapping
    ResponseEntity<Void> activateCard(
            @PathVariable int customerId,
            @Pattern(regexp = "\\d{16}") @PathVariable String cardNumber,
            @Valid @RequestBody Activation activation
    ) {
        service.activate(CardId.of(cardNumber, customerId), activation.toMoney());
        return ResponseEntity.created(URI.create("/")).build();
    }

    @PostMapping("/withdrawals")
    ResponseEntity<Void> withdrawMoney(
            @PathVariable int customerId,
            @Pattern(regexp = "\\d{16}") @PathVariable String cardNumber,
            @Valid @RequestBody Withdrawal withdrawal
    ) {
        if (withdrawal.isLocal()) {
            service.withdraw(withdrawal.getAmount(), CardId.of(cardNumber, customerId));
            return ResponseEntity.noContent().build();
        }
        service.withdraw(withdrawal.toMoney(), CardId.of(cardNumber, customerId));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    ResponseEntity<Void> blockCard(
            @PathVariable int customerId,
            @Pattern(regexp = "\\d{16}") @PathVariable String cardNumber
    ) {
        service.block(CardId.of(cardNumber, customerId));
        return ResponseEntity.noContent().build();
    }
}
