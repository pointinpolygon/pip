package biz.michalowski.app;

import java.io.File;

public class Resources {

    private static final String COUNTRIES_SHP = "/countries/ne_110m_admin_0_countries.shp";

    public static File getCountriesFile() {
        return new File(Resources.class.getResource(COUNTRIES_SHP).getFile());
    }
}
