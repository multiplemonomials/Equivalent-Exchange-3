package net.multiplemonomials.eer.reference;

public class Reference
{
    public static final String MOD_ID = "EER";
    public static final String MOD_NAME = "Equivalent Exchange Reborn";
    public static final String FINGERPRINT = "@FINGERPRINT@";
    public static final String VERSION = "0.4.0";
    public static final String SERVER_PROXY_CLASS = "net.multiplemonomials.eer.proxy.ServerProxy";
    public static final String CLIENT_PROXY_CLASS = "net.multiplemonomials.eer.proxy.ClientProxy";
    
    //This can't be in Textures.java because that class can't be loaded on a server
    public static final String RESOURCE_PREFIX = Reference.MOD_ID.toLowerCase() + ":";
}
