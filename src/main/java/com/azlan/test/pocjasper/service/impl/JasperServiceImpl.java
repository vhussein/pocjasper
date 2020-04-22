package com.azlan.test.pocjasper.service.impl;

import com.azlan.test.pocjasper.model.Payment;
import com.azlan.test.pocjasper.service.ifc.JasperServiceIfc;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.jasperreports.JasperReportsUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class JasperServiceImpl implements JasperServiceIfc {

    @Value("${myapp.jasperpath}")
    private String jasperPath;

    private static final String LOGO_PATH = "images/smiley-small.png";
    private static final String RECEIPT_TEMPLATE_PATH = "receipt_template.jrxml";

    public ByteArrayInputStream generateReceipt(Payment payment) {
        log.debug("Generate Receipt");

        log.debug("PATH IS HERE ==> " + jasperPath);

        // Initiate a FileOutputStream
        try(ByteArrayOutputStream out = new ByteArrayOutputStream())
        {
            // Load the invoice jrxml template.
            final JasperReport report = loadTemplate();

            // Create parameters map.
            final Map<String, Object> parameters = parameters(payment);
            // Create an empty datasource.
            final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList("Payment"));

            // Render the PDF file
            JasperReportsUtils.renderAsPdf(report, parameters, dataSource, out);

            return new ByteArrayInputStream(out.toByteArray());
        }
        catch (final Exception e)
        {
            log.error(String.format("An error occured during PDF creation: %s", e));
        }

        return null;
    }

    // Load invoice jrxml template
    private JasperReport loadTemplate() throws JRException {

        log.info(String.format("Invoice template path : %s", jasperPath + RECEIPT_TEMPLATE_PATH));

        final InputStream reportInputStream = getClass().getResourceAsStream(jasperPath + RECEIPT_TEMPLATE_PATH);
        final JasperDesign jasperDesign = JRXmlLoader.load(reportInputStream);

        return JasperCompileManager.compileReport(jasperDesign);
    }

    // Fill template order parametres
    private Map<String, Object> parameters(Payment payment) {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("logo", getClass().getResourceAsStream(jasperPath + LOGO_PATH));
        parameters.put("payment",  payment);
        return parameters;
    }

}
