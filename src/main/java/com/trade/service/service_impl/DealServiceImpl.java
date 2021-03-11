package com.trade.service.service_impl;

import com.trade.entity.ClientEntity;
import com.trade.entity.DealEntity;
import com.trade.entity.PaymentEntity;
import com.trade.entity.enums.InvoiceStatus;
import com.trade.exception.EntityNotFoundException;
import com.trade.repository.jpa.DealJPARepository;
import com.trade.service.DealService;
import com.trade.service.util.ServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DealServiceImpl implements DealService {

    private final DealJPARepository dealRepository;

    private final ServiceUtil serviceUtil;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<DealEntity> getDeals(Integer pageNumber, Integer pageSize, String sortBy, String sortType, Set<Long> clientIds, List<String> dealType) {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.fromString(sortType), sortBy));

        Page<DealEntity> dealEntityPage = getDealsByCriteria(clientIds, dealType, paging);
        List<DealEntity> resultDealEntities = dealEntityPage.getContent().stream()
                .map(dealEntity -> {
//                    BigDecimal fullSum = BigDecimal.ZERO;
//                    for (PaymentEntity p: dealEntity.getInvoice().getPayments()){
//                        if(p.getStatus() != null && p.getStatus().getName().equals("ACCEPTED")) {
//                            fullSum = fullSum.add(p.getAmount());
//                        }
//                    }
//                    dealEntity.getInvoice().setStatus(checkInvoiceStatus(fullSum, dealEntity.getInvoice().getAmount()));
                    dealEntity.getInvoice().setStatus(serviceUtil.checkStatus(dealEntity.getInvoice()));
                    return dealEntity;
                })
                .collect(Collectors.toList());
        return new PageImpl<>(resultDealEntities);
    }

    @Override
    public DealEntity getDealById(Long id) {
        return dealRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cannot find deal by id", "Deal does no exist"));
    }

    private Page<DealEntity> getDealsByCriteria(Set<Long> clientIds, List<String> types, Pageable paging) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<DealEntity> cq = cb.createQuery(DealEntity.class);
        Root<DealEntity> root = cq.from(DealEntity.class);

        List<Predicate> predicates = new ArrayList<>();
        if (clientIds != null) {
            Join<DealEntity, ClientEntity> clientEntityJoin = root.join("clients");
            Predicate pr1 = cb.and(clientEntityJoin.get("clientId").get("id").in(clientIds));
            predicates.add(pr1);
        }
        if (types != null) {
            Predicate pr2 = cb.and(root.get("type").in(types));
            predicates.add(pr2);
        }
        if (!predicates.isEmpty())
            cq.where(cb.and(predicates.toArray(new Predicate[] {})));

        cq.orderBy(QueryUtils.toOrders(paging.getSort(), root, cb));

        TypedQuery<DealEntity> query = entityManager.createQuery(cq.distinct(true));
        query.setFirstResult(paging.getPageNumber() * paging.getPageSize());
        query.setMaxResults(paging.getPageSize());

        return new PageImpl<>(query.getResultList());
    }

    private InvoiceStatus checkInvoiceStatus(BigDecimal fullSum, BigDecimal dealSum) {
        if(dealSum.compareTo(BigDecimal.ZERO) == 0) {
            return InvoiceStatus.PAID;
        }
        BigDecimal percent = fullSum.divide(dealSum, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
        if (percent.compareTo(new BigDecimal(50)) >= 0 && percent.compareTo(new BigDecimal(100)) < 0) {
            return InvoiceStatus.PARTIALLY_PAID;
        } else if(percent.compareTo(new BigDecimal(100)) > 0) {
            return InvoiceStatus.PAID;
        } else {
            return InvoiceStatus.NOT_PAID;
        }
    }
}
