package com.trade.controller;

import com.trade.entity.DealEntity;
import com.trade.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController("api/deals")
@RequiredArgsConstructor
public class DealController {

    private final DealService dealService;

    @GetMapping
    public ResponseEntity<List<DealEntity>> getDeals(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortType,
            @RequestParam(required = false) Set<Long> clientIds,
            @RequestParam(required = false) ArrayList<String> dealTypes
    ) {
        Page<DealEntity> response = dealService.getDeals(pageNumber, pageSize, sortBy, sortType, clientIds, dealTypes);
        return new ResponseEntity<>(response.getContent(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DealEntity> getDealById(@PathVariable Long id) {
        DealEntity res = dealService.getDealById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
