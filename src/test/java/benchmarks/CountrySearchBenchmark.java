package benchmarks;

import biz.michalowski.app.Resources;
import biz.michalowski.geography.*;
import biz.michalowski.geography.geotools.GeotoolsCountryRepository;
import biz.michalowski.geometry.canvas.Canvas;
import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class CountrySearchBenchmark {

    private final Random random = new Random(0);

    private final CountryRepository countryRepository = new GeotoolsCountryRepository(Resources.getCountriesFile());
    private final CountrySearch naiveCountrySearch = new CountrySearch(countryRepository, Canvas.CanvasFactory.naive());
    private final CountrySearch quadtreeCountrySearch = new CountrySearch(countryRepository, Canvas.CanvasFactory.quadtree());
    private final CoordinateGenerator coordinateGenerator = new CoordinateGenerator(random);

    @Benchmark
    public int measureNaive() {
        Coordinate randomCoordinate = coordinateGenerator.getRandom();
        return naiveCountrySearch.findCountry(randomCoordinate).map(f -> 1).orElse(1);
    }

    @Benchmark
    public int measureQuadtree() {
        Coordinate randomCoordinate = coordinateGenerator.getRandom();
        return quadtreeCountrySearch.findCountry(randomCoordinate).map(f -> 1).orElse(1);
    }
}
