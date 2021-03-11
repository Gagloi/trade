package com.trade.entity.dto;

import java.math.BigDecimal;

public class InvoiceDto {

    private BigDecimal amount;

    public InvoiceDto() {
    }

    public InvoiceDto(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
