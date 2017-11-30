package biz.michalowski.geo;

import biz.michalowski.TestValues;
import biz.michalowski.geo.Country;
import biz.michalowski.geo.CountryRepository;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CountryRepositoryTest {

    private static final String COUNTRIES_SHP = "/countries/ne_110m_admin_0_countries.shp";

    private final String file = getClass().getResource(COUNTRIES_SHP).getFile();
    private final CountryRepository countryRepository = new CountryRepository(new File(file));

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

    //take the bounding box of each country
    //construct a QuadTree
    //max number of items 4?, max depth 10?
    //retrieve all the possibly-colliding countries
    //double check if colliding
}
