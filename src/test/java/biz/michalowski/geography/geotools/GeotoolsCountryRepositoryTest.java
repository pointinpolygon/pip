package biz.michalowski.geography.geotools;

import biz.michalowski.TestValues;
import biz.michalowski.app.Resources;
import biz.michalowski.geography.Country;
import biz.michalowski.geography.CountryRepository;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class GeotoolsCountryRepositoryTest {

    private final CountryRepository countryRepository = new GeotoolsCountryRepository(Resources.getCountriesFile());

    @Test
    public void contains_Poland() {
        Country poland = countryRepository.getAll()
                .stream()
                .filter(c -> c.getName().equals("Poland"))
                .findFirst()
                .get();

        assertThat(poland.getName(), is("Poland"));
        assertTrue(poland.contains(TestValues.WROCLAW_COORDINATE));
    }

    @Test
    public void gets_all_countries() {
        List<Country> countries = countryRepository.getAll();

        assertThat(countries.size(), is(177));
    }
}
