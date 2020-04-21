package com.azlan.test.pocjasper.Service.Ifc;

import com.azlan.test.pocjasper.model.Payment;

import java.io.IOException;

public interface JasperServiceIfc {

    void generateReceipt(Payment payment) throws IOException;
}
