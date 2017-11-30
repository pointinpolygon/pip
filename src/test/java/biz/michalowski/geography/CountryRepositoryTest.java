package biz.michalowski.geography;

import biz.michalowski.TestValues;
import biz.michalowski.app.Resources;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CountryRepositoryTest {

    private final CountryRepository countryRepository = new CountryRepository(Resources.getCountriesFile());

    @Test
    public void wroclaw_is_in_Poland() {
        Optional<String> country = countryRepository.countryAt(TestValues.WROCLAW_LAT, TestValues.WROCLAW_LON);

        assertThat(country, is(Optional.of("Poland")));
    }
    
    @Test
    public void northPole_is_a_no_mans_land() {
        Optional<String> country = countryRepository.countryAt(90d, 0d);

        assertThat(country, is(Optional.empty()));
    }

    @Test
    public void gets_all_countries() {
        List<Country> countries = countryRepository.getAll();

        assertThat(countries.size(), is(177));
    }
}
