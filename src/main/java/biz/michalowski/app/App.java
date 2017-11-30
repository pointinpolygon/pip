package biz.michalowski.app;

import biz.michalowski.geography.Coordinate;
import biz.michalowski.geography.CoordinateGenerator;
import biz.michalowski.geography.Country;
import biz.michalowski.geography.CountrySearch;

import java.util.Optional;
import java.util.stream.IntStream;

class App {

    private static final String N_A = "n/a";

    private final CountrySearch countrySearch;
    private final CoordinateGenerator coordinateGenerator;

    App(CountrySearch countrySearch, CoordinateGenerator coordinateGenerator) {
        this.countrySearch = countrySearch;
        this.coordinateGenerator = coordinateGenerator;
    }

    void run() {
        IntStream.range(0, 1_000_000).forEach(i -> {
            Coordinate coordinate = coordinateGenerator.getRandom();
            Optional<Country> country = countrySearch.findCountry(coordinate);
            String countryName = country.map(Country::getName).orElse(N_A);
            System.out.println(String.format("%s -> %s", coordinate, countryName));
        });
    }
}
