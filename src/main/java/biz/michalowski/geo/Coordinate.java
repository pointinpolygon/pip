package biz.michalowski.geo;

import com.google.common.base.Preconditions;

public class Coordinate {

    final double lat;
    final double lon;

    public Coordinate(double lat, double lon) {
        Preconditions.checkArgument(-90 <= lat && lat <= 90);
        Preconditions.checkArgument(-180 <= lon && lon <= 180);
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public String toString() {
        return String.format("(%4.3f, %4.3f)", lat, lon);
    }
}
