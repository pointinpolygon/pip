package biz.michalowski.geography;

import biz.michalowski.geometry.Point;
import biz.michalowski.geometry.canvas.Canvas;

import java.util.Optional;

public class CountrySearch {

    private final Canvas<Country> canvas;

    public CountrySearch(CountryRepository countryRepository, Canvas.CanvasFactory<Country> canvasFactory) {
        this.canvas = canvasFactory.get();
        this.canvas.init(countryRepository.getAll());
    }

    public Optional<Country> findCountry(Coordinate coordinate) {
        Point point = new Point(coordinate.lon, coordinate.lat);
        return canvas
                .findContaining(point)
                .findFirst();
    }
}
