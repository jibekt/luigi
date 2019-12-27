package be.vdab.luigi.restclients;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ECBKoersClientTest {
    private ECBKoersClient client;
    @BeforeEach
    void beforeEach() {
        client = new ECBKoersClient();
    }
    @Test
    void deKoersMoetPositiefZijn() {
        assertThat(client.getDollarKoers()).isPositive();
    }
}