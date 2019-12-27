package be.vdab.luigi.restclients;

import be.vdab.luigi.exceptions.KoersClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;

@Order(2)
@Component
public class ECBKoersClient implements KoersClient {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final URL url;
    ECBKoersClient(@Value("${ecbKoersURL}") URL url) {
        this.url = url;
    }
    @Override
    public BigDecimal getDollarKoers() {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        try (InputStream stream = url.openStream()) {
            XMLStreamReader reader = factory.createXMLStreamReader(stream);
            try {
                while (reader.hasNext()) {
                    if (reader.next() == XMLStreamConstants.START_ELEMENT
&& "USD".equals(reader.getAttributeValue(null, "currency"))) {
                        logger.info("koers gelezen via ECB");
                        return new BigDecimal(reader.getAttributeValue(null, "rate"));
                    }
                }
                String fout = "XML van ECB bevat geen USD";
                logger.error(fout);
                throw new KoersClientException(fout);
            } finally {
                reader.close();
            }
        } catch (IOException | NumberFormatException | XMLStreamException ex) {
            String fout = "kan koers niet lezen via ECB";
            logger.error(fout, ex);
            throw new KoersClientException(fout);
        }
    }
}
