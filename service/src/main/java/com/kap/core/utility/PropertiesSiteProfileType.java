package com.kap.core.utility;


public class PropertiesSiteProfileType {
    private static String siteProfile = COApplicationProperties.getProperty("site-gubun");
    public static String getSiteProfile(){
        return siteProfile;
    }
}
