package biz.michalowski;

import com.vividsolutions.jts.geom.Coordinate;
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
import java.util.Optional;

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
        Point point = geometryFactory.createPoint(new Coordinate(lat, lon));
        try (FeatureIterator iterator = countryCollection.features()) {
            while (iterator.hasNext()) {
                Feature feature = iterator.next();
                GeometryAttribute defaultGeometryProperty = feature.getDefaultGeometryProperty();
                MultiPolygon multiPolygon = (MultiPolygon) defaultGeometryProperty.getValue();
                if (multiPolygon.contains(point)) {
                    return toCountryName(feature);
                }
            }
        }
        return Optional.empty();
    }

    private Optional<String> toCountryName(Feature feature) {
        Collection<Property> names = feature.getProperties(COUNTRY_NAME_PROPERTY);
        return names
                .stream()
                .findFirst()
                .map(name -> name.getValue().toString());
    }
}
