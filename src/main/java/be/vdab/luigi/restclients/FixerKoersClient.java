package be.vdab.luigi.restclients;

import be.vdab.luigi.exceptions.KoersClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Scanner;

@Order(1)
@Component
public class FixerKoersClient implements KoersClient {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final URL url;
    FixerKoersClient(@Value("${fixerKoersURL}") URL url){
        this.url=url;
    }
    @Override
    public BigDecimal getDollarKoers(){
        try(InputStream stream = url.openStream();
            Scanner scanner = new Scanner(stream)){
            String lijn =scanner.nextLine();
            int beginPositieKoers = lijn.indexOf("USD") + 5;
            int accoladePositie = lijn.indexOf('}', beginPositieKoers);
            logger.info("koers gelezen via Fixer");
            return new BigDecimal(lijn.substring(beginPositieKoers, accoladePositie));
        } catch (IOException | NumberFormatException ex) {
            String fout = "kan koers niet lezen via Fixer";
            logger.error(fout, ex);
            throw new KoersClientException(fout);
        }
    }

}
