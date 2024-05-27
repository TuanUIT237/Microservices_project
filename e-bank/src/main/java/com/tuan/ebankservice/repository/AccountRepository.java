package com.tuan.ebankservice.repository;

import com.tuan.ebankservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,String> {
    boolean existsByName(String name);
}
