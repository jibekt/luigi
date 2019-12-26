package be.vdab.luigi.restclients;

import be.vdab.luigi.exceptions.KoersClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

@Component
public class FixerKoersClient implements KoersClient {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final URL url;
    FixerKoersClient(){
        try{
            url= new URL(
                    "http://data.fixer.io/api/latest?access_key=5fc8ed4ce49c6b12c137e71c8988ed5c&symbols=USD");
        } catch (MalformedURLException ex){
            String fout = "Fixeer URL is verkeerd";
            logger.error(fout, ex);
            throw new KoersClientException(fout);
        }
    }
    @Override
    public BigDecimal getDollarKoers(){
        try(InputStream stream = url.openStream();
            Scanner scanner = new Scanner(stream)){
            String lijn =scanner.nextLine();
            int beginPositieKoers = lijn.indexOf("USD") + 5;
            int accoladePositie = lijn.indexOf('}', beginPositieKoers);
            return new BigDecimal(lijn.substring(beginPositieKoers, accoladePositie));
        } catch (IOException | NumberFormatException ex) {
            String fout = "kan koers niet lezen via Fixer";
            logger.error(fout, ex);
            throw new KoersClientException(fout);
        }
    }

}
