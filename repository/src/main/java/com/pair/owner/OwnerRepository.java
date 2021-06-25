package com.pair.owner;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long>, OwnerRepositoryCustom {
}
