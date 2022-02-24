package com.example.mybank.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Transaction save(Transaction Transaction);

    Page<Transaction> findAll(Pageable pageable);

    Optional<Transaction> findById(Long id);

    @Query("""
            SELECT t from Transaction t
            WHERE CAST(t.createdAt AS date) >= CAST(:fromDate AS date)
            AND CAST(t.createdAt AS date) <= CAST(:toDate AS date)
            ORDER BY t.createdAt""")
    Page<Transaction> filterByDate(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate, Pageable pageable);

    @Query("""
            SELECT t from Transaction t
            WHERE CAST(t.createdAt AS date) >=  CAST(:fromDate AS date)
            AND CAST(t.createdAt AS date) <=  CAST(:toDate AS date)
            AND t.type = :type
            ORDER BY t.createdAt""")
    Page<Transaction> filterByDateAndType(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate, Pageable pageable, TransactionType type);

    default Page<Transaction> filterByDateAndType(LocalDate fromDate, LocalDate toDate, Pageable pageable, Optional<TransactionType> type) {
        if(type.isPresent()) return filterByDateAndType(fromDate, toDate, pageable, type.get());
        return filterByDate(fromDate, toDate, pageable);
    }
}
