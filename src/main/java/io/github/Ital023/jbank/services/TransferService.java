package io.github.Ital023.jbank.services;

import io.github.Ital023.jbank.controller.dto.TransferMoneyDto;
import io.github.Ital023.jbank.entities.Transfer;
import io.github.Ital023.jbank.entities.Wallet;
import io.github.Ital023.jbank.exception.TransferException;
import io.github.Ital023.jbank.exception.WalletNotFoundException;
import io.github.Ital023.jbank.repository.TransferRepository;
import io.github.Ital023.jbank.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransferService {

    private final TransferRepository transferRepository;
    private final WalletRepository walletRepository;


    public TransferService(TransferRepository transferRepository, WalletRepository walletRepository) {
        this.transferRepository = transferRepository;
        this.walletRepository = walletRepository;
    }


    @Transactional
    public void transferMoney(TransferMoneyDto dto) {

        var receiver = walletRepository.findById(dto.receiver())
                .orElseThrow(() -> new WalletNotFoundException("receiver does not exists"));

        var sender = walletRepository.findById(dto.sender())
                .orElseThrow(() -> new WalletNotFoundException("sender does not exists"));

        if (sender.getBalance().compareTo(dto.value()) == -1) {
            throw new TransferException("insufficient balance, you current balance is $" + sender.getBalance());
        }

        sender.setBalance(sender.getBalance().subtract(dto.value()));
        receiver.setBalance(receiver.getBalance().add(dto.value()));
        walletRepository.save(sender);
        walletRepository.save(receiver);

        var transfer = new Transfer();
        transfer.setReceiver(receiver);
        transfer.setSender(sender);
        transfer.setTransferValue(dto.value());
        transfer.setTransferDateTime(LocalDateTime.now());
        transferRepository.save(transfer);

        /*
        updateWallets(dto, sender, receiver);

        persistTransfer(dto, receiver, sender);
        */

    }

    private void updateWallets(TransferMoneyDto dto, Wallet sender, Wallet receiver) {
        sender.setBalance(sender.getBalance().subtract(dto.value()));
        receiver.setBalance(receiver.getBalance().add(dto.value()));
        walletRepository.save(sender);
        walletRepository.save(receiver);
    }

    private void persistTransfer(TransferMoneyDto dto, Wallet receiver, Wallet sender) {
        var transfer = new Transfer();
        transfer.setReceiver(receiver);
        transfer.setSender(sender);
        transfer.setTransferValue(dto.value());
        transfer.setTransferDateTime(LocalDateTime.now());
        transferRepository.save(transfer);
    }


}
