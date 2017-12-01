package biz.michalowski.geography.geotools;

import biz.michalowski.geometry.BoundingBox;
import biz.michalowski.geometry.Point;
import biz.michalowski.geometry.Polygon;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.MultiPolygon;

class GeotoolsPolygon implements Polygon {

    private final MultiPolygon multiPolygon;
    private final GeometryFactory geometryFactory;

    GeotoolsPolygon(MultiPolygon multiPolygon, GeometryFactory geometryFactory) {
        this.multiPolygon = multiPolygon;
        this.geometryFactory = geometryFactory;
    }

    @Override
    public boolean contains(Point point) {
        Coordinate coordinate = new Coordinate(point.x, point.y);
        com.vividsolutions.jts.geom.Point geoToolsPoint = geometryFactory.createPoint(coordinate);
        return multiPolygon.contains(geoToolsPoint);
    }

    @Override
    public boolean intersects(BoundingBox boundingBox) {
        double minX = boundingBox.minX();
        double maxX = boundingBox.maxX();
        double minY = boundingBox.minY();
        double maxY = boundingBox.maxY();
        com.vividsolutions.jts.geom.Coordinate coordinate = new com.vividsolutions.jts.geom.Coordinate(minX, minY);
        com.vividsolutions.jts.geom.Coordinate[] coordinates = new com.vividsolutions.jts.geom.Coordinate[] {
                coordinate,
                new com.vividsolutions.jts.geom.Coordinate(minX, maxY),
                new com.vividsolutions.jts.geom.Coordinate(maxX, maxY),
                new com.vividsolutions.jts.geom.Coordinate(maxX, minY),
                coordinate
        };
        com.vividsolutions.jts.geom.Polygon polygon = geometryFactory.createPolygon(coordinates);
        return multiPolygon.intersects(polygon);
    }
}
