package io.github.Ital023.jbank.services;

import io.github.Ital023.jbank.controller.dto.CreateWalletDto;
import io.github.Ital023.jbank.entities.Wallet;
import io.github.Ital023.jbank.exception.WalletDataAlreadyExistsException;
import io.github.Ital023.jbank.repository.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }


    public Wallet createWallet(CreateWalletDto dto) {
        var walletDb = walletRepository.findByCpfOrEmail(dto.cpf(), dto.email());

        if(walletDb.isPresent()) {
            throw new WalletDataAlreadyExistsException("cpf or email already exists");
        }

        var wallet = new Wallet();
        wallet.setCpf(dto.cpf());
        wallet.setEmail(dto.email());
        wallet.setName(dto.name());

        return walletRepository.save(wallet);
    }

}
