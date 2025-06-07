package io.github.Ital023.jbank.controller.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletDto(UUID walletId,
                        String cpf,
                        String name,
                        String email,
                        BigDecimal balance) {
}
