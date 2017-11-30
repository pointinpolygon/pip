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
    private final CountrySearch smartCountrySearch = new CountrySearch(countryRepository, Canvas.CanvasFactory.binsearch());
    private final CoordinateGenerator coordinateGenerator = new CoordinateGenerator(new Random());

    @Test
    public void smart_and_naive_lookups_are_equally_correct() {
        for (int i = 0; i < 10000; i++) {
            Coordinate randomCoordinate = coordinateGenerator.getRandom();
            Optional<Country> slowCountry = naiveCountrySearch.findCountry(randomCoordinate);
            Optional<Country> fastCountry = smartCountrySearch.findCountry(randomCoordinate);

            assertThat(fastCountry.map(Country::getName), equalTo(slowCountry.map(Country::getName)));
        }
    }
}
