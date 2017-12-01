**PiP: Coordinate on the worldmap**

Prints out the country name for a given random geo coordinate. 

**Running** 

To run:
```
./gradlew run
```

To run the tests:
```
./gradlew test
```

**Implementation**

- There are 2 implementations of PiP algorithm. Naive and Quadtree. Quadtree is used by default. See `biz.michalowski.geometry.canvas.Canvas`.
- The SHP file access is implemented in `biz.michalowski.geography.geotools.GeotoolsCountryRepository` using _Geotools_ library (http://www.geotools.org/).

**Benchmark** 

There's also a jmh-based benchmark in `src/test/java/benchmarks/CountrySearchBenchmark.java`.
I used intellij to run it. (There's a Gradle plugin but it isn't too cooperative).

I got the following results:

```
Benchmark                                       Mode   Samples        Score  Score error    Units
b.CountrySearchBenchmark.measureNaive           avgt       200   206655.688     5078.656    ns/op
b.CountrySearchBenchmark.measureQuadtree        avgt       200   125386.619     3218.196    ns/op
```

Quadtree gives around 40% speedup comparing to the naive implementation.
Assuming our geo data sample (177 polygons) is used, random access querying.