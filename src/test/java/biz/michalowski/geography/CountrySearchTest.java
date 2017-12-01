package biz.michalowski.geography;

import biz.michalowski.TestValues;
import biz.michalowski.geometry.canvas.Canvas;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Optional;

import static biz.michalowski.TestValues.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CountrySearchTest {

    private final CountryRepository countryRepository = mock(CountryRepository.class);
    private final Country.Border polishBorder = mock(Country.Border.class);
    private final Country poland = new Country("Poland", polishBorder, TestValues.POLAND_BOUNDING_BOX);

    private CountrySearch countrySearch;

    @Before
    public void setUp() throws Exception {
        when(polishBorder.contains(any(Coordinate.class))).thenReturn(false);
        when(countryRepository.getAll()).thenReturn(Collections.singletonList(poland));
        countrySearch = new CountrySearch(countryRepository, Canvas.CanvasFactory.quadtree());
    }

    @Test
    public void finds_a_country_by_coordinates_inside() {
        when(polishBorder.contains(any(Coordinate.class))).thenReturn(true);
        Optional<Country> country = countrySearch.findCountry(WROCLAW_COORDINATE);

        assertThat(country.get().getName(), is("Poland"));
    }

    @Test
    public void no_country_is_found_for_coordinates_outside_the_bounding_box() {
        Optional<Country> country = countrySearch.findCountry(LA_COORDINATE);

        assertThat(country, is(Optional.empty()));
    }

    @Test
    public void no_country_is_found_for_coordinates_outside_the_country_but_inside_its_bounding_box() {
        Optional<Country> country = countrySearch.findCountry(LIBEREC_COORDINATE);

        assertThat(country, is(Optional.empty()));
    }
}
