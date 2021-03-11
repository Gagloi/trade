package com.trade.repository.jpa;

import com.trade.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceJPARepository extends JpaRepository<InvoiceEntity, Long> {
}
