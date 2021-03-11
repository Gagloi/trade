package com.trade.controller;

import com.trade.entity.InvoiceEntity;
import com.trade.entity.dto.InvoiceDto;
import com.trade.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController("api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<InvoiceEntity> createInvoice(@RequestParam Long dealId, @RequestBody InvoiceDto invoiceDto) {
        InvoiceEntity savedInvoice = invoiceService.createInvoice(dealId, modelMapper.map(invoiceDto, InvoiceEntity.class));
        return new ResponseEntity<>(savedInvoice, HttpStatus.OK);
    }

    @PutMapping("/{invoiceId}")
    public ResponseEntity<InvoiceEntity> updateInvoice(Long dealId, @PathVariable Long invoiceId, @RequestBody InvoiceDto invoiceDto) {
        InvoiceEntity savedInvoice = invoiceService.updateInvoice(dealId, invoiceId, modelMapper.map(invoiceDto, InvoiceEntity.class));
        return new ResponseEntity<>(savedInvoice, HttpStatus.OK);
    }

}
