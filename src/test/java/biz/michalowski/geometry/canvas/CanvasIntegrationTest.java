package biz.michalowski.geometry.canvas;

import biz.michalowski.TestValues;
import biz.michalowski.app.Resources;
import biz.michalowski.geography.*;
import biz.michalowski.geography.geotools.GeotoolsCountryRepository;
import org.junit.Test;

import java.util.Optional;
import java.util.Random;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CanvasIntegrationTest {

    private final CountryRepository countryRepository = new GeotoolsCountryRepository(Resources.getCountriesFile());
    private final CountrySearch naiveCountrySearch = new CountrySearch(countryRepository, Canvas.CanvasFactory.naive());
    private final CountrySearch quadtreeCountrySearch = new CountrySearch(countryRepository, Canvas.CanvasFactory.quadtree());
    private final CoordinateGenerator coordinateGenerator = new CoordinateGenerator(new Random());

    @Test
    public void wroclaw_is_in_Poland() {
        Optional<Country> country = quadtreeCountrySearch.findCountry(TestValues.WROCLAW_COORDINATE);

        assertThat(country.map(Country::getName), is(Optional.of("Poland")));
    }

    @Test
    public void alaska_is_in_the_USA() {
        Optional<Country> country = quadtreeCountrySearch.findCountry(TestValues.ALASKA_COORDINATE);

        assertThat(country.map(Country::getName), is(Optional.of("United States of America")));
    }

    @Test
    public void north_pole_is_a_no_mans_land() {
        Optional<Country> country = quadtreeCountrySearch.findCountry(TestValues.NORTH_POLE_COORDINATE);

        assertThat(country, is(Optional.empty()));
    }

    @Test
    public void quadtree_and_naive_lookups_return_the_same_country() {
        compare(naiveCountrySearch, quadtreeCountrySearch);
    }

    private void compare(CountrySearch naiveSearch, CountrySearch comparedSearch) {
        for (int i = 0; i < 100; i++) {
            Coordinate randomCoordinate = coordinateGenerator.getRandom();
            Optional<Country> naiveResult = naiveSearch.findCountry(randomCoordinate);
            Optional<Country> comparedResult = comparedSearch.findCountry(randomCoordinate);

            Optional<String> actualName = comparedResult.map(Country::getName);
            Optional<String> expectedName = naiveResult.map(Country::getName);
            assertThat("Failed on: " + randomCoordinate, actualName, equalTo(expectedName));
        }
    }
}
