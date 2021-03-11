package com.trade.repository.jpa;

import com.trade.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentJPARepository extends JpaRepository<PaymentEntity, Long> {
}
