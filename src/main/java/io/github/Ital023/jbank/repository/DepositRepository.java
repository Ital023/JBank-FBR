package io.github.Ital023.jbank.repository;

import io.github.Ital023.jbank.entities.Deposit;
import io.github.Ital023.jbank.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DepositRepository extends JpaRepository<Deposit, UUID> {
}
