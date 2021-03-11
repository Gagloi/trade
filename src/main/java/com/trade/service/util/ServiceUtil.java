package com.trade.service.util;

import com.trade.entity.InvoiceEntity;
import com.trade.entity.PaymentEntity;
import com.trade.entity.enums.InvoiceStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ServiceUtil {

    public InvoiceStatus checkStatus(InvoiceEntity invoiceEntity) {
        BigDecimal fullSum = BigDecimal.ZERO;
        for (PaymentEntity p: invoiceEntity.getPayments()){
            if(p.getStatus() != null && p.getStatus().getName().equals("ACCEPTED")) {
                fullSum = fullSum.add(p.getAmount());
            }
        }
        return checkInvoiceStatus(fullSum, invoiceEntity.getAmount());
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
