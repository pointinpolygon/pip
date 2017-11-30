package biz.michalowski.geo;

import biz.michalowski.geometry.BoundingBox;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import org.geotools.data.DataStore;
import org.geotools.data.FeatureSource;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.opengis.feature.Feature;
import org.opengis.feature.GeometryAttribute;
import org.opengis.feature.Property;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class CountryRepository {

    private static final String COUNTRY_NAME_PROPERTY = "NAME";

    private final GeometryFactory geometryFactory;
    private final FeatureCollection countryCollection;

    CountryRepository(File shpFile) {
        this.geometryFactory = JTSFactoryFinder.getGeometryFactory();
        this.countryCollection = initCollection(shpFile);
    }

    private FeatureCollection initCollection(File shpFile) {
        try {
            DataStore dataStore = FileDataStoreFinder.getDataStore(shpFile);
            String typeName = Arrays.stream(dataStore.getTypeNames())
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException(shpFile + " file is empty!"));

            FeatureSource featureSource = dataStore.getFeatureSource(typeName);
            return featureSource.getFeatures();
        } catch (IOException e) {
            throw new RuntimeException("Could not initCollection the repository", e);
        }
    }

    Optional<String> countryAt(double lat, double lon) {
        return getAll()
                .stream()
                .filter(country -> country.contains(new Coordinate(lat, lon)))
                .map(Country::getName)
                .findFirst();
    }

    List<Country> getAll() {
        Stream.Builder<Country> countries = Stream.builder();
        try (FeatureIterator iterator = countryCollection.features()) {
            while (iterator.hasNext()) {
                Feature feature = iterator.next();
                Optional<Country> country = convertToCountry(feature);
                country.ifPresent(countries::add);
            }
        }
        return countries.build().collect(Collectors.toList());
    }

    private Optional<Country> convertToCountry(Feature feature) {
        GeometryAttribute defaultGeometryProperty = feature.getDefaultGeometryProperty();
        MultiPolygon multiPolygon = (MultiPolygon) defaultGeometryProperty.getValue();
        org.opengis.geometry.BoundingBox bounds = feature.getBounds();
        Collection<Property> names = feature.getProperties(COUNTRY_NAME_PROPERTY);
        Optional<String> countryName = names
                .stream()
                .findFirst()
                .map(name -> name.getValue().toString());

        Country.Borders borders = convertToBorders(multiPolygon);
        BoundingBox boundingBox = convertToBoundingBox(bounds);
        return countryName.map(name -> new Country(name, borders, boundingBox));
    }

    private BoundingBox convertToBoundingBox(org.opengis.geometry.BoundingBox bounds) {
        return point -> bounds.contains(point.x, point.y);
    }

    private Country.Borders convertToBorders(MultiPolygon multiPolygon) {
        return coordinate -> {
            Point geoToolsPoint = geometryFactory.createPoint(
                    new com.vividsolutions.jts.geom.Coordinate(coordinate.lon, coordinate.lat)
            );
            return multiPolygon.contains(geoToolsPoint);
        };
    }
}
