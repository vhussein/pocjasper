package com.azlan.test.pocjasper.controller;


import com.azlan.test.pocjasper.Service.Ifc.JasperServiceIfc;
import com.azlan.test.pocjasper.model.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/jasper")
public class JasperController {

    private final JasperServiceIfc jasperServiceIfc;

    public JasperController(JasperServiceIfc jasperServiceIfc) {
        this.jasperServiceIfc = jasperServiceIfc;
    }

    @PostMapping
    public void generatePdf() throws IOException {

        Payment payment = new Payment();
        payment.setRefNumber("12312313132");
        payment.setDate(new Date());
        payment.setFromAccount("my account");
        payment.setToAccount("target account");

        jasperServiceIfc.generateReceipt(payment);

    }
}
