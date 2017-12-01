package biz.michalowski.app;

import biz.michalowski.geography.CoordinateGenerator;
import biz.michalowski.geography.CountrySearch;
import biz.michalowski.geography.CountryRepository;
import biz.michalowski.geography.geotools.GeotoolsCountryRepository;
import biz.michalowski.geometry.canvas.Canvas;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        CountryRepository countryRepository = new GeotoolsCountryRepository(Resources.getCountriesFile());
        CountrySearch countrySearch = new CountrySearch(countryRepository, Canvas.CanvasFactory.getDefault());
        CoordinateGenerator coordinateGenerator = new CoordinateGenerator(new Random());

        App app = new App(countrySearch, coordinateGenerator);
        app.run();
    }
}
