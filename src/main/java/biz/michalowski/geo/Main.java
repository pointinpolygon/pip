package biz.michalowski.geo;

import java.io.File;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

public class Main {

    private static final String COUNTRIES_SHP = "/countries/ne_110m_admin_0_countries.shp";

    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        String file = Main.class.getResource(COUNTRIES_SHP).getFile();
        CountryRepository countryRepository = new CountryRepository(new File(file));
        CountryLookup countryLookup = new CountryLookup(countryRepository);

        IntStream.range(0, 1_000_000).forEach(i -> {
            Coordinate coordinate = getRandomCoordinate();
            Optional<Country> country = countryLookup.findCountry(coordinate);
            String countryName = country.map(Country::getName).orElse("n/a");
            System.out.println(String.format("%s -> %s", coordinate, countryName));
        });
    }

    private static Coordinate getRandomCoordinate() {
        double lat = randomBetween(-90d, 90d);
        double lon = randomBetween(-180d, 180d);
        return new Coordinate(lat, lon);
    }

    private static double randomBetween(double rangeMin, double rangeMax) {
        return rangeMin + (rangeMax - rangeMin) * RANDOM.nextDouble();
    }
}
