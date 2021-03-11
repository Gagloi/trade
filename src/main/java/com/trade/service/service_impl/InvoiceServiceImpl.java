package com.trade.service.service_impl;

import com.trade.entity.DealEntity;
import com.trade.entity.InvoiceEntity;
import com.trade.exception.EntityNotFoundException;
import com.trade.repository.jpa.InvoiceJPARepository;
import com.trade.service.DealService;
import com.trade.service.InvoiceService;
import com.trade.service.util.ServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceJPARepository invoiceRepository;

    private final DealService dealService;

    private final ServiceUtil serviceUtil;

    @Override
    public InvoiceEntity createInvoice(Long dealId, InvoiceEntity invoice) {
        DealEntity deal = dealService.getDealById(dealId);
        invoice.setDeal(deal);
        return invoiceRepository.save(invoice);
    }

    @Override
    public InvoiceEntity updateInvoice(Long dealId, Long invoiceId, InvoiceEntity invoiceEntity) {
        DealEntity deal = dealService.getDealById(dealId);
        InvoiceEntity invoice = invoiceRepository.findById(invoiceId).orElseThrow(() -> new EntityNotFoundException("Cannot find invoice by id", "Invoice does no exist"));
        invoiceEntity.setPayments(invoice.getPayments());
        invoice.setDeal(deal);
        invoice.setStatus(serviceUtil.checkStatus(invoiceEntity));
        invoice.setAmount(invoiceEntity.getAmount());
        return invoiceRepository.save(invoice);
    }
}
