package biz.michalowski.geography;

import biz.michalowski.geography.geotools.GeotoolsCountryRepository;
import biz.michalowski.geometry.Point;
import biz.michalowski.geometry.Polygon;
import biz.michalowski.geometry.canvas.Canvas;
import biz.michalowski.geometry.TestPolygon;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

import static biz.michalowski.TestValues.WROCLAW_COORDINATE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class CountrySearchTest {

    private final CountryRepository countryRepository = mock(GeotoolsCountryRepository.class);
    private final Polygon squarelandPolygon = TestPolygon.from(0, 1, 0, 1).toPolygon();
    private final Country squareland = new Country("Squareland", squarelandPolygon);

    private Canvas<Country> canvas = mock(Canvas.class);
    private CountrySearch countrySearch;

    @Before
    public void setUp() throws Exception {
        when(countryRepository.getAll()).thenReturn(Collections.singletonList(squareland));
        countrySearch = new CountrySearch(countryRepository, () -> canvas);
    }

    @Test
    public void initializes_canvas_in_constructor() {
        verify(canvas).init(Collections.singletonList(squareland));
    }

    @Test
    public void queries_canvas_to_find_a_country() {
        when(canvas.findContaining(any(Point.class))).thenReturn(Stream.of(squareland));

        Optional<Country> country = countrySearch.findCountry(WROCLAW_COORDINATE);

        assertThat(country, is(Optional.of(squareland)));
    }

    @Test
    public void returns_empty_when_no_country_found() {
        when(canvas.findContaining(any(Point.class))).thenReturn(Stream.empty());

        Optional<Country> country = countrySearch.findCountry(WROCLAW_COORDINATE);

        assertThat(country, is(Optional.empty()));
    }
}
