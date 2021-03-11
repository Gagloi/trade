package com.trade.service;

import com.trade.entity.DealEntity;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

public interface DealService {

    Page<DealEntity> getDeals(Integer pageNumber, Integer pageSize, String sortBy, String sortType, @Nullable Set<Long> clientsId,
                                    @Nullable List<String> dealType);

    DealEntity getDealById(Long id);
}
