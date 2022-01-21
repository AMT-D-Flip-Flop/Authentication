/**
 * Date de création     : Janvier2021
 * Groupe               : AMT-D-Flip-Flop
 * Description          : Constantes de l'application
 */


package com.amt.dflipflop;

public final class Constantes {
    //Decide if you are in prod or not
    public final static Boolean IS_PROD = true;

    //File for prod or local use if you have the same path for file
    public final static String jwtfileNamePath = "/opt/tomcat/webapps/zone_secret/jwt.txt";
    //Example for Windows
    //public final static String jwtfileNamePath = "C:\\amt\\jwt.txt";

    //Local Use only
    //Use if IS_PROD = false;
    public final static String tokenSecretDefault = "secret";
}
