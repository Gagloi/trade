package com.trade.service;

import com.trade.entity.InvoiceEntity;

public interface InvoiceService {

    InvoiceEntity createInvoice(Long dealId, InvoiceEntity invoice);

    InvoiceEntity updateInvoice(Long dealId, Long invoiceId, InvoiceEntity invoiceEntity);

}
