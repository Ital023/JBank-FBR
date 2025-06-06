package io.github.Ital023.jbank.repository;

import io.github.Ital023.jbank.entities.Deposit;
import io.github.Ital023.jbank.entities.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransferRepository extends JpaRepository<Transfer, UUID> {
}
