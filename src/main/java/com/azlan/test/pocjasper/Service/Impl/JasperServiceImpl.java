package com.azlan.test.pocjasper.Service.Impl;

import com.azlan.test.pocjasper.Service.Ifc.JasperServiceIfc;
import com.azlan.test.pocjasper.model.Payment;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.stereotype.Service;
import org.springframework.ui.jasperreports.JasperReportsUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class JasperServiceImpl implements JasperServiceIfc {

    private static final String logoPath = "/jasper/images/smiley-small.png";

    public void generateReceipt(Payment payment) throws IOException {
        log.debug("Generate Receipt");

        // Create a temporary PDF file
        File pdfFile = File.createTempFile("my-receipt", ".pdf");

        // Initiate a FileOutputStream
        try(FileOutputStream pos = new FileOutputStream(pdfFile))
        {
            // Load the invoice jrxml template.
            final JasperReport report = loadTemplate();

            // Create parameters map.
            final Map<String, Object> parameters = parameters(payment);
            // Create an empty datasource.
            final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList("Payment"));

            // Render the PDF file
            JasperReportsUtils.renderAsPdf(report, parameters, dataSource, pos);
        }
        catch (final Exception e)
        {
            log.error(String.format("An error occured during PDF creation: %s", e));
        }
    }

    // Load invoice jrxml template
    private JasperReport loadTemplate() throws JRException {

        String invoice_template_path = "/jasper/receipt_template.jrxml";
        log.info(String.format("Invoice template path : %s", invoice_template_path));

        final InputStream reportInputStream = getClass().getResourceAsStream(invoice_template_path);
        final JasperDesign jasperDesign = JRXmlLoader.load(reportInputStream);

        return JasperCompileManager.compileReport(jasperDesign);
    }

    // Fill template order parametres
    private Map<String, Object> parameters(Payment payment) {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("logo", getClass().getResourceAsStream(logoPath));
        parameters.put("payment",  payment);
        return parameters;
    }

}
