package biz.michalowski.geography.geotools;

import biz.michalowski.geography.Country;
import biz.michalowski.geography.CountryRepository;
import biz.michalowski.geometry.Polygon;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.MultiPolygon;
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

public class GeotoolsCountryRepository implements CountryRepository {

    private static final String COUNTRY_NAME_PROPERTY = "NAME";

    private final GeometryFactory geometryFactory;
    private final FeatureCollection countryCollection;

    public GeotoolsCountryRepository(File shpFile) {
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

    @Override
    public List<Country> getAll() {
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
        MultiPolygon multiPolygon = extractPolygon(feature);
        Optional<String> countryName = extractCountryName(feature);
        Polygon polygon = new GeotoolsPolygon(multiPolygon, geometryFactory);
        return countryName.map(name -> new Country(name, polygon));
    }

    private MultiPolygon extractPolygon(Feature feature) {
        GeometryAttribute defaultGeometryProperty = feature.getDefaultGeometryProperty();
        return (MultiPolygon) defaultGeometryProperty.getValue();
    }

    private Optional<String> extractCountryName(Feature feature) {
        Collection<Property> names = feature.getProperties(COUNTRY_NAME_PROPERTY);
        return names
                .stream()
                .findFirst()
                .map(name -> name.getValue().toString());
    }
}
