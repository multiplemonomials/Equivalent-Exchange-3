package com.pahimar.ee3.reference;

public class Reference
{
    public static final String MOD_ID = "EE3";
    public static final String MOD_NAME = "Equivalent Exchange 3";
    public static final String FINGERPRINT = "@FINGERPRINT@";
    public static final String VERSION = "@VERSION@";
    public static final String SERVER_PROXY_CLASS = "com.pahimar.ee3.proxy.ServerProxy";
    public static final String CLIENT_PROXY_CLASS = "com.pahimar.ee3.proxy.ClientProxy";
    
    //TODO: move below settings to config file
    
    //damage of Dark Matter sword on lowest charge level
	public static final float DM_SWORD_BASE_DAMAGE = 8;
	
	//maximum number of times a power item can be charged
	public static final int MAX_ITEM_CHARGES = 4;
	
	//how many ticks the Calcinator will burn per EMC in the fuel
	public static final int FURNACE_TICKS_PER_FUEL_EMC = 2;
	
	//how many ticks the Calcinator will have to burn to cook an item with 1 emc 
	public static final float FURNACE_TICKS_PER_ITEM_EMC = .1F;
	
	public static final int CONDENSER_OUTPUT_ITEMS_PER_TICK = 10;
}
