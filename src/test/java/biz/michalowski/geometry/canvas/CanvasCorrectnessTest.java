package biz.michalowski.geometry.canvas;

import biz.michalowski.app.Resources;
import biz.michalowski.geography.CoordinateGenerator;
import biz.michalowski.geography.Coordinate;
import biz.michalowski.geography.Country;
import biz.michalowski.geography.CountrySearch;
import biz.michalowski.geography.CountryRepository;
import org.junit.Test;

import java.util.Optional;
import java.util.Random;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class CanvasCorrectnessTest {

    private final CountryRepository countryRepository = new CountryRepository(Resources.getCountriesFile());
    private final CountrySearch naiveCountrySearch = new CountrySearch(countryRepository, Canvas.CanvasFactory.naive());
    private final CountrySearch binsearchCountrySearch = new CountrySearch(countryRepository, Canvas.CanvasFactory.binsearch());
    private final CountrySearch quadtreeCountrySearch = new CountrySearch(countryRepository, Canvas.CanvasFactory.quadtree());
    private final CoordinateGenerator coordinateGenerator = new CoordinateGenerator(new Random());

    @Test
    public void binsearch_and_naive_lookups_are_equally_correct() {
        compare(naiveCountrySearch, binsearchCountrySearch);
    }

    @Test
    public void quadtree_and_naive_lookups_are_equally_correct() {
        compare(naiveCountrySearch, quadtreeCountrySearch);
    }

    private void compare(CountrySearch naiveSearch, CountrySearch comparedSearch) {
        for (int i = 0; i < 10000; i++) {
            Coordinate randomCoordinate = coordinateGenerator.getRandom();
            Optional<Country> naiveResult = naiveSearch.findCountry(randomCoordinate);
            Optional<Country> comparedResult = comparedSearch.findCountry(randomCoordinate);

            assertThat(comparedResult.map(Country::getName), equalTo(naiveResult.map(Country::getName)));
        }
    }

    @Test
    public void foo() {
        CoordinateGenerator coordinateGenerator = new CoordinateGenerator(new Random(0));
        Optional<Country> country1 = quadtreeCountrySearch.findCountry(coordinateGenerator.getRandom());
        Optional<Country> country2 = quadtreeCountrySearch.findCountry(coordinateGenerator.getRandom());
        Optional<Country> country3 = quadtreeCountrySearch.findCountry(coordinateGenerator.getRandom());

//
    }
}
