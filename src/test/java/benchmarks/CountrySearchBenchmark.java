package benchmarks;

import biz.michalowski.app.Resources;
import biz.michalowski.geography.Coordinate;
import biz.michalowski.geography.CoordinateGenerator;
import biz.michalowski.geography.CountrySearch;
import biz.michalowski.geography.CountryRepository;
import biz.michalowski.geometry.canvas.Canvas;
import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class CountrySearchBenchmark {

    private final CountryRepository countryRepository = new CountryRepository(Resources.getCountriesFile());
    private final CountrySearch naiveCountrySearch = new CountrySearch(countryRepository, Canvas.CanvasFactory.naive());
    private final CountrySearch binsearchCountrySearch = new CountrySearch(countryRepository, Canvas.CanvasFactory.binsearch());
    private final CountrySearch quadtreeCountrySearch = new CountrySearch(countryRepository, Canvas.CanvasFactory.quadtree());
    private final CoordinateGenerator coordinateGenerator = new CoordinateGenerator(new Random(0));

    @Benchmark
    public int measureNaive() {
        Coordinate randomCoordinate = coordinateGenerator.getRandom();
        return naiveCountrySearch.findCountry(randomCoordinate).map(f -> 1).orElse(0);
    }

    @Benchmark
    public int measureBinsearch() {
        Coordinate randomCoordinate = coordinateGenerator.getRandom();
        return binsearchCountrySearch.findCountry(randomCoordinate).map(f -> 1).orElse(0);
    }

    @Benchmark
    public int measureQuadtree() {
        Coordinate randomCoordinate = coordinateGenerator.getRandom();
        try {
            return quadtreeCountrySearch.findCountry(randomCoordinate).map(f -> 1).orElse(0);
        } catch (Exception e) {
            System.out.println(randomCoordinate);
        }
        return 1;
    }
}
