package com.pair.settlement.owner;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    private final AccountRepository accountRepository;

    public OwnerService(OwnerRepository ownerRepository, AccountRepository accountRepository) {
        this.ownerRepository = ownerRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Long enroll(Owner owner) {
        checkNotNull(owner);
        ownerRepository.save(owner);
        return owner.getId();
    }

    @Transactional
    public Long enrollAccount(Owner owner, Account account) {
        checkNotNull(account);
        accountRepository.save(account);
        return account.getId();
    }

    @Transactional(readOnly = true)
    public Owner findOwner(Long ownerId) {
        return ownerRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("찾으려는 업주가 없습니다."));
    }

    @Transactional
    public List<Owner> searchOwner(String id, String name, String email) {
        return ownerRepository.findOwner(id, name, email);
    }

    public Owner update(Long id, Owner updateOwner) {
        Owner owner = findOwner(id);
        updateOwner.setId(owner.getId());
        ownerRepository.save(updateOwner);
        return updateOwner;
    }
}
