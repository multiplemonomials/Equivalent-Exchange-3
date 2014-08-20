package net.multiplemonomials.eer.reference;

public class Reference
{
    public static final String MOD_ID = "EER";
    public static final String MOD_NAME = "Equivalent Exchange Reborn";
    public static final String FINGERPRINT = "@FINGERPRINT@";
    public static final String VERSION = "0.2.1";
    public static final String SERVER_PROXY_CLASS = "net.multiplemonomials.eer.proxy.ServerProxy";
    public static final String CLIENT_PROXY_CLASS = "net.multiplemonomials.eer.proxy.ClientProxy";
    
    //This can't be in Textures.java because that class can't be loaded on a server
    public static final String RESOURCE_PREFIX = Reference.MOD_ID.toLowerCase() + ":";
    
    //TODO: move below settings to config file
    
    //damage of Dark Matter sword on lowest charge level
	public static final float DM_SWORD_BASE_DAMAGE = 8;
	
	//maximum number of times a power item can be charged
	public static final int MAX_ITEM_CHARGES = 4;
	
	//how many ticks the Calcinator will burn per EMC in the fuel
	public static final int FURNACE_TICKS_PER_FUEL_EMC = 2;
	
	//how many ticks the Calcinator will have to burn to cook an item with 1 emc 
	public static final float FURNACE_TICKS_PER_ITEM_EMC = .1F;
	
	public static final int CONDENSER_INPUT_ITEMS_PER_TICK = 5;
	
	//how many ticks it will take the Talisman of Repair to repair 1 durability
	public static final int TALISMAN_OF_REPAIR_TICKS_PER_DURABILITY = 100;
	
	//how much EMC the Ring of Flight will spend flying
	public static final float FLYING_RING_EMC_DRAIN_PER_TICK = 0.4F;
	
	//how much EMC the Ring of Flight will spend pushing mobs away
	public static final float FLYING_RING_EMC_DRAIN_PER_TICK_MOB_PUSH = 1.0F;
	
	//half how many EMC a Klein Star Ichi will store
	public static final double HALF_KLEIN_STAR_ICHI_EMC = 8192.0;
	
	public static final double DM_FLINT_REQUIRED_EMC_PER_BLOCK = 32.0;
	
	//control the use of a more complicated tick handler for the flying ring
	//that is more laggy but doesn't break other mods' creative-flight systems
	public static boolean USE_FLYING_RING_COMPATIBILITY_FIX = true;
	
	//show player-like chat messages for all living things (except bats who burn to death)
	public static boolean SHOW_ALL_DEATH_MESSAGES = true;
}
