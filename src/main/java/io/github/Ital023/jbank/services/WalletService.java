package io.github.Ital023.jbank.services;

import io.github.Ital023.jbank.controller.dto.*;
import io.github.Ital023.jbank.entities.Deposit;
import io.github.Ital023.jbank.entities.Wallet;
import io.github.Ital023.jbank.exception.DeleteWalletException;
import io.github.Ital023.jbank.exception.StatementException;
import io.github.Ital023.jbank.exception.WalletDataAlreadyExistsException;
import io.github.Ital023.jbank.exception.WalletNotFoundException;
import io.github.Ital023.jbank.repository.DepositRepository;
import io.github.Ital023.jbank.repository.WalletRepository;
import io.github.Ital023.jbank.repository.dto.StatementView;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final DepositRepository depositRepository;

    public WalletService(WalletRepository walletRepository, DepositRepository depositRepository) {
        this.walletRepository = walletRepository;
        this.depositRepository = depositRepository;
    }


    public Wallet createWallet(CreateWalletDto dto) {
        var walletDb = walletRepository.findByCpfOrEmail(dto.cpf(), dto.email());

        if(walletDb.isPresent()) {
            throw new WalletDataAlreadyExistsException("cpf or email already exists");
        }

        var wallet = new Wallet();
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setCpf(dto.cpf());
        wallet.setEmail(dto.email());
        wallet.setName(dto.name());

        return walletRepository.save(wallet);
    }

    public boolean deleteWallet(UUID walletId) {
        var wallet = walletRepository.findById(walletId);

        if(wallet.isPresent()) {

            if(wallet.get().getBalance().compareTo(BigDecimal.ZERO) != 0) {
                throw new DeleteWalletException("the balance is not zero, The current amount is $" + wallet.get().getBalance());
            }

            walletRepository.deleteById(walletId);
        }

        return wallet.isPresent();
    }


    @Transactional
    public void depositMoney(UUID walletId, DepositMoneyDto dto, String ipAddress) {

        var wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("There is no wallet with this id"));


        var deposit = new Deposit();
        deposit.setWallet(wallet);
        deposit.setDepositValue(dto.value());
        deposit.setDepositDateTime(LocalDateTime.now());
        deposit.setIpAddress(ipAddress);

        depositRepository.save(deposit);

        wallet.setBalance(wallet.getBalance().add(dto.value()));

        walletRepository.save(wallet);

    }

    public StatementDto getStatements(UUID walletId, Integer page, Integer pageSize) {

        var wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("There is no wallet with this id"));

        var pageRequest = PageRequest.of(page, pageSize, Sort.Direction.DESC, "statement_date_time");

        var statements = walletRepository.findStatements(walletId.toString(), pageRequest)
                .map(view -> mapToDto(walletId, view));

        WalletDto walletDto = new WalletDto(wallet.getWalletId(), wallet.getCpf(), wallet.getName(), wallet.getEmail(), wallet.getBalance());

        PaginationDto paginationDto = new PaginationDto(statements.getNumber(), statements.getSize(), statements.getTotalElements(), statements.getTotalPages());

        StatementDto statementDto = new StatementDto(walletDto, statements.getContent(), paginationDto);

        return statementDto;
    }

    private StatementItemDto mapToDto(UUID walletId, StatementView view) {

        if (view.getType().equalsIgnoreCase("deposit")) {
            return mapToDeposit(view);
        }

        if(view.getType().equalsIgnoreCase("transfer")
                && view.getStatementSender().equalsIgnoreCase(walletId.toString())) {

            return mapWhenTransferSent(walletId, view);
        }

        if(view.getType().equalsIgnoreCase("transfer")
                && view.getStatementReceiver().equalsIgnoreCase(walletId.toString())) {

            return mapWhenTransferReceived(walletId, view);
        }

        throw new StatementException("Invalid type " + view.getType());
    }

    private StatementItemDto mapWhenTransferReceived(UUID walletId, StatementView view) {
        return new StatementItemDto(
                view.getStatementId(),
                view.getType(),
                "money received from " + view.getStatementSender(),
                view.getStatementValue(),
                view.getStatementDateTime(),
                StatementOperation.CREDIT
        );
    }

    private StatementItemDto mapWhenTransferSent(UUID walletId, StatementView view) {
        return new StatementItemDto(
                view.getStatementId(),
                view.getType(),
                "money sent to " + view.getStatementReceiver(),
                view.getStatementValue(),
                view.getStatementDateTime(),
                StatementOperation.DEBIT
        );
    }

    private StatementItemDto mapToDeposit(StatementView view) {
        return new StatementItemDto(
                view.getStatementId(),
                view.getType(),
                "money deposit",
                view.getStatementValue(),
                view.getStatementDateTime(),
                StatementOperation.CREDIT
        );
    }


}
