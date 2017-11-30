package biz.michalowski.geography;

import java.util.Random;

public class CoordinateGenerator {

    private final Random random;

    public CoordinateGenerator(Random random) {
        this.random = random;
    }

    public Coordinate getRandom() {
        double lat = randomBetween(-90d, 90d);
        double lon = randomBetween(-180d, 180d);
        return new Coordinate(lat, lon);
    }

    private double randomBetween(double rangeMin, double rangeMax) {
        return rangeMin + (rangeMax - rangeMin) * random.nextDouble();
    }
}
