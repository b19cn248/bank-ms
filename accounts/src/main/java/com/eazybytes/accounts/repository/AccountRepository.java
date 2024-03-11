package com.eazybytes.accounts.repository;

import com.eazybytes.accounts.entity.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

  Optional<Account> findByCustomerId(Long customerId);

  @Modifying
  @Transactional
  @Query("delete from Account a where a.customerId = :id")
  void deleteByCustomerId(Long id);
}
