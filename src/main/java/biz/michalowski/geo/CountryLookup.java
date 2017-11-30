package biz.michalowski.geo;

import biz.michalowski.geometry.BoundingBoxLookup;
import biz.michalowski.geometry.Point;

import java.util.Optional;

class CountryLookup {

    private final BoundingBoxLookup<Country> boundingBoxLookup = new BoundingBoxLookup<>();

    CountryLookup(CountryRepository countryRepository) {
        countryRepository.getAll().forEach(country -> boundingBoxLookup.put(country.getBoundingBox(), country));
    }

    Optional<Country> findCountry(Coordinate coordinate) {
        Point point = new Point(coordinate.lon, coordinate.lat);
        return boundingBoxLookup
                .find(point)
                .filter(country -> country.contains(coordinate))
                .findFirst();
    }
}
