package biz.michalowski;

import org.junit.Test;

import java.io.File;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CountryRepositoryTest {

    private static final String COUNTRIES_SHP = "/countries/ne_110m_admin_0_countries.shp";

    private final String file = getClass().getResource(COUNTRIES_SHP).getFile();
    private final CountryRepository countryRepository = new CountryRepository(new File(file));

    @Test
    public void wroclaw_is_in_Poland() {
        Optional<String> country = countryRepository.countryAt(17.038538d, 51.107883d);

        assertThat(country, is(Optional.of("Poland")));
    }
    
    @Test
    public void southPole_is_a_no_mans_land() {
        Optional<String> country = countryRepository.countryAt(-90d, 0d);

        assertThat(country, is(Optional.empty()));
    }
}
