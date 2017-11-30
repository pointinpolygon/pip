package biz.michalowski;

import biz.michalowski.geo.Coordinate;
import biz.michalowski.geometry.BoundingBox;
import biz.michalowski.geometry.SimpleBoundingBox;

public class TestValues {

    public static final double LIBEREC_LAT = 50.766280d;
    public static final double LIBEREC_LON = 15.054339;

    public static final double WROCLAW_LON = 17.038538d;
    public static final double WROCLAW_LAT = 51.107883d;

    public static final double LA_LAT = 30.9842977d;
    public static final double LA_LON = -91.96233269999999;

    public static final Coordinate WROCLAW_COORDINATE = new Coordinate(WROCLAW_LAT, WROCLAW_LON);
    public static final Coordinate LIBEREC_COORDINATE = new Coordinate(LIBEREC_LAT, LIBEREC_LON);
    public static final Coordinate LA_COORDINATE = new Coordinate(LA_LAT, LA_LON);

    public static final BoundingBox POLAND_BOUNDING_BOX = new SimpleBoundingBox(14.074521111719434, 24.029985792748903, 49.02739533140962, 54.85153595643291);

}
