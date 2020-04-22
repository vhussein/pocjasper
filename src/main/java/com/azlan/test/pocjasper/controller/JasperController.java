package com.azlan.test.pocjasper.controller;


import com.azlan.test.pocjasper.model.Payment;
import com.azlan.test.pocjasper.service.ifc.JasperServiceIfc;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/jasper")
public class JasperController {

    private final JasperServiceIfc jasperServiceIfc;

    public JasperController(JasperServiceIfc jasperServiceIfc) {
        this.jasperServiceIfc = jasperServiceIfc;
    }

    @GetMapping(produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generatePdf() {

        Payment payment = new Payment();
        payment.setRefNumber("12312313132");
        payment.setDate(new Date());
        payment.setFromAccount("my account");
        payment.setToAccount("target account");

        ByteArrayInputStream bis = jasperServiceIfc.generateReceipt(payment);

        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=" + payment.getRefNumber() + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
