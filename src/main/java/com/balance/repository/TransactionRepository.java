package com.balance.repository;

import com.balance.entity.Transaction;
import com.balance.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer>, JpaSpecificationExecutor<Transaction> {
    List<Transaction> findByUser(User user);

    List<Transaction> findTop10ByUserOrderByDateDesc(User user);

    Optional<Transaction> findByIdAndUser(Integer id, User user);

}
