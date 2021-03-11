package com.trade.repository.jpa;

import com.trade.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientJPARepository extends JpaRepository<ClientEntity, Long> {
}
