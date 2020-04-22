package com.azlan.test.pocjasper.service.ifc;

import com.azlan.test.pocjasper.model.Payment;

import java.io.ByteArrayInputStream;

public interface JasperServiceIfc {

    ByteArrayInputStream generateReceipt(Payment payment);
}
