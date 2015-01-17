package net.multiplemonomials.eer.configuration;

import net.minecraftforge.common.config.Configuration;
import net.multiplemonomials.eer.reference.Reference;
import net.multiplemonomials.eer.util.LogHelper;

public class CommonConfiguration
{
		//name of common config file
		public static final String FILENAME = "common.properties";
	
	
		//damage of Dark Matter sword on lowest charge level
		public static double DM_SWORD_BASE_DAMAGE;
		
		//maximum number of times a power item can be charged
		public static int MAX_ITEM_CHARGES;
		
		//how many ticks the Calcinator will burn per EMC in the fuel
		public static double FURNACE_TICKS_PER_FUEL_EMC;
		
		//how many ticks the Calcinator will have to burn to cook an item with 1 emc 
		public static double FURNACE_TICKS_PER_ITEM_EMC;
		
		public static int CONDENSER_INPUT_ITEMS_PER_TICK;
		
		//how many ticks it will take the Talisman of Repair to repair 1 durability
		public static int TALISMAN_OF_REPAIR_TICKS_PER_DURABILITY;
		
		//how much EMC the Ring of Flight will spend per tick flying
		public static double FLYING_RING_EMC_DRAIN_PER_TICK;
		
		//how much EMC the Ring of Flight will spend per tick pushing mobs away
		public static double FLYING_RING_EMC_DRAIN_PER_TICK_MOB_PUSH;
		
		//half how many EMC a Klein Star Ichi will store (having it be half increases performance of the total EMC calculation algorithm)
		public static double HALF_KLEIN_STAR_ICHI_EMC;
		
		public static double DM_FLINT_REQUIRED_EMC_PER_BLOCK;
		
		//control the use of a more complicated tick handler for the flying ring
		//that is more laggy but doesn't break other mods' creative-flight systems
		public static boolean USE_FLYING_RING_COMPATIBILITY_FIX = true;
		
		//show player-like chat messages for all living things (except bats who burn to death)
		public static boolean SHOW_ALL_DEATH_MESSAGES = false;
		
		//show death messages for bats burning to death
		public static boolean SHOW_BATS_TO_BURN_TO_DEATH = false;
		
		//emc/tick of the energy collector levels at light level 16
		public static double [] ENERGY_COLLECTOR_EMC_PER_TICK = new double[3];
		
		//storage of an energy collector
		public static double[] ENERGY_COLLECTOR_EMC_STORAGE = new double[3];
		
		//emc per tick that an energy collector drains to a klein star
		public static double[] ENERGY_COLLECTOR_DRAIN_RATE = new double[3];
		
		//emc per tick that an antimatter relay will pass to the next thing
		public static int[] ANTIMATTER_RELAY_EMC_PER_TICK = new int[3];
		
		//decimal amount of emc passed through that an antimatter relay will lose
		public static double[] ANTIMATTER_RELAY_EMC_LOSS_COEFFICIENT = new double[3];
		
		//radius of the cube where the Black Hole Band will pick up items
		public static int MAGNET_RING_BOUNDING_BOX_RADIUS;
		
		//required EMC for the Red Matter Pickaxe to mine one block
		public static double RM_PICK_REQUIRED_EMC_PER_BLOCK;

		//Red Matter Pickaxe right-click ability enabled
		public static boolean RM_PICK_ABILITY_ENABLED;
		
		//required EMC for the Red Matter Hoe to hoe one block
		public static double RM_HOE_REQUIRED_EMC_PER_BLOCK;
		
		//required EMC for the Red Matter shovel to dig one block
		public static double RM_SHOVEL_REQUIRED_EMC_PER_BLOCK;
		
		//required EMC for the Red Matter sword to deal one damage point in an area
		public static double RM_SWORD_REQUIRED_EMC_PER_DAMAGE_POINT;
		
		//if true, do not send sounds to other players
		public static boolean SOUNDS_SELF_ONLY;
		
    public static void init(Configuration configuration)
    {
    	LogHelper.info("Loading common configuration...");
    	
    	try
    	{
    		DM_SWORD_BASE_DAMAGE = configuration.get(Configuration.CATEGORY_GENERAL, "dmSwordBaseDamage", 8.0, "damage of Dark Matter sword on lowest charge level").getDouble(8.0);
			
			MAX_ITEM_CHARGES = configuration.get(Configuration.CATEGORY_GENERAL, "maxPowerItemCharges", 4, "maximum number of times a power item can be charged (starts at 0)").getInt(4);
    		
			FURNACE_TICKS_PER_FUEL_EMC = configuration.get(Configuration.CATEGORY_GENERAL, "furnaceTicksPerFuelEMC", 2.0, "how many ticks the Calcinator will burn per EMC in the fuel").getDouble(2.0);

			FURNACE_TICKS_PER_ITEM_EMC = configuration.get(Configuration.CATEGORY_GENERAL, "furnaceTicksPerItemEMC", .1, "how many ticks the Calcinator will have to burn to cook an item with 1 emc ").getDouble(.1);

			CONDENSER_INPUT_ITEMS_PER_TICK = configuration.get(Configuration.CATEGORY_GENERAL, "condenserInputItemsPerTick", 5, "Amount of items the Energy Condenser will take per tick").getInt(5);

			TALISMAN_OF_REPAIR_TICKS_PER_DURABILITY = configuration.get(Configuration.CATEGORY_GENERAL, "talismanOfRepairTicksPerDurability", 200, "how many ticks it will take the Talisman of Repair to repair 1 durability").getInt(200);

			FLYING_RING_EMC_DRAIN_PER_TICK = configuration.get(Configuration.CATEGORY_GENERAL, "ringOfFlightFlyingEmcPerTick", 0.4, "how much EMC the Ring of Flight will spend per tick flying").getDouble(0.4);

			FLYING_RING_EMC_DRAIN_PER_TICK_MOB_PUSH = configuration.get(Configuration.CATEGORY_GENERAL, "ringOfFlightPusingEmcPerTick", 1.0, "how much EMC the Ring of Flight will spend per tick pushing mobs away").getDouble(1.0);

			HALF_KLEIN_STAR_ICHI_EMC = configuration.get(Configuration.CATEGORY_GENERAL, "kleinStarIchiEMC", 16384, "how many EMC a Klein Star Ichi will store (other klein stars scale from this by the formula  (2^level) * (thisvalue / 2))").getInt(16384)/2;

			DM_FLINT_REQUIRED_EMC_PER_BLOCK = configuration.get(Configuration.CATEGORY_GENERAL, "dmFlintRequiredEMCPerBlock", 32.0, "How much EMC the Dark Matter and Steel uses per block it sets to fire").getDouble(32.0);

			USE_FLYING_RING_COMPATIBILITY_FIX = configuration.get(Configuration.CATEGORY_GENERAL, "useRingOfFlightCompatibility", true, "use a more complicated tick handler for the flying ring that is more laggy but doesn't break other mods' creative-flight systems").getBoolean(true);
			
			SHOW_ALL_DEATH_MESSAGES = configuration.get(Configuration.CATEGORY_GENERAL, "showAllDeathMessages", false, "show death messages for all mobs and animals").getBoolean(false);
			
			SHOW_BATS_TO_BURN_TO_DEATH = configuration.get(Configuration.CATEGORY_GENERAL, "showBatFireDeathMessages", false, "show death messages for bats burning to death").getBoolean(false);
			
			ENERGY_COLLECTOR_EMC_PER_TICK[0] = (configuration.get(Configuration.CATEGORY_GENERAL, "energyCollectorEmcPerSecLvlOne", 1.0, "emc/s of the energy collector level 1 at light level 16").getDouble(1.0))/20.0;
			
			ENERGY_COLLECTOR_EMC_PER_TICK[1] = (configuration.get(Configuration.CATEGORY_GENERAL, "energyCollectorEmcPerSecLvlTwo", 2.5, "emc/s of the energy collector level 2 at light level 16").getDouble(2.5))/20.0;
			
			ENERGY_COLLECTOR_EMC_PER_TICK[2] = (configuration.get(Configuration.CATEGORY_GENERAL, "energyCollectorEmcPerSecLvlThree", 5, "emc/s of the energy collector level 3 at light level 16").getDouble(5.0))/20.0;
			
			ENERGY_COLLECTOR_EMC_STORAGE[0] = (configuration.get(Configuration.CATEGORY_GENERAL, "energyCollectorEmcStorage", 32768.0, "emc storage of a level 1 energy collector.  Each subsequent level has four times this.").getDouble(32768.0));
			ENERGY_COLLECTOR_EMC_STORAGE[1] = 4 * ENERGY_COLLECTOR_EMC_STORAGE[0];
			ENERGY_COLLECTOR_EMC_STORAGE[2] = 4 * ENERGY_COLLECTOR_EMC_STORAGE[1];
			
			ENERGY_COLLECTOR_DRAIN_RATE[0] = (configuration.get(Configuration.CATEGORY_GENERAL, "energyCollectorDrainRateLvlOne", 10.0, "rate at which a level 1 energy collector drains to an alcemical battery.").getDouble(10.0));
			
			ENERGY_COLLECTOR_DRAIN_RATE[1] = (configuration.get(Configuration.CATEGORY_GENERAL, "energyCollectorDrainRateLvlTwo", 30.0, "rate at which a level 2 energy collector drains to an alcemical battery.").getDouble(20.0));
			
			ENERGY_COLLECTOR_DRAIN_RATE[2] = (configuration.get(Configuration.CATEGORY_GENERAL, "energyCollectorDrainRateLvlThree", 100.0, "rate at which a level 3 energy collector drains to an alcemical battery.").getDouble(100.0));
			
			ANTIMATTER_RELAY_EMC_PER_TICK[0] = (configuration.get(Configuration.CATEGORY_GENERAL, "antimatterRelayEMCTransferance", 4096, "emc per tick that an antimatter relay will pass to the next thing.  Subsequent levels pass 2x more").getInt(4096));
			ANTIMATTER_RELAY_EMC_PER_TICK[1] = 2 * ANTIMATTER_RELAY_EMC_PER_TICK[0];
			ANTIMATTER_RELAY_EMC_PER_TICK[2] = 2 * ANTIMATTER_RELAY_EMC_PER_TICK[1];
			
			ANTIMATTER_RELAY_EMC_LOSS_COEFFICIENT[0] = 1 - (configuration.get(Configuration.CATEGORY_GENERAL, "antimatterRelayEMCLossPercent", 10.0, "percent of emc passed through that an antimatter relay will lose.  Each subsequent level has a tenth this").getDouble(10.0)) / 100;
			ANTIMATTER_RELAY_EMC_LOSS_COEFFICIENT[1] = 1 - ((-ANTIMATTER_RELAY_EMC_LOSS_COEFFICIENT[0] + 1) / 10);
			ANTIMATTER_RELAY_EMC_LOSS_COEFFICIENT[2] = 1 - ((-ANTIMATTER_RELAY_EMC_LOSS_COEFFICIENT[0] + 1) / 10);
			
			MAGNET_RING_BOUNDING_BOX_RADIUS = configuration.get(Configuration.CATEGORY_GENERAL, "ringMagnetPickupRadius", 30, "radius of the cube where the Black Hole Band will pick up items").getInt(30);
			
			RM_PICK_REQUIRED_EMC_PER_BLOCK = configuration.get(Configuration.CATEGORY_GENERAL, "rmPickRequiredEMCPerBlock", 64.0, "required EMC for the Red Matter Pickaxe to mine one block").getDouble(64.0);	
			
			RM_PICK_ABILITY_ENABLED = configuration.get(Configuration.CATEGORY_GENERAL, "rmPickAbilityEnabled", true, "Red Matter Pickaxe right-click ability enable").getBoolean(true);
			
			RM_HOE_REQUIRED_EMC_PER_BLOCK = configuration.get(Configuration.CATEGORY_GENERAL, "rmHoeRequiredEMCPerBlock", 32.0, "required EMC for the Red Matter Hoe to hoe one block").getDouble(32.0);
			
			RM_SHOVEL_REQUIRED_EMC_PER_BLOCK = configuration.get(Configuration.CATEGORY_GENERAL, "rmShovelRequiredEMCPerBlock", 64.0, "required EMC for the Red Matter shovel to dig one block").getDouble(64.0);
			
			RM_SWORD_REQUIRED_EMC_PER_DAMAGE_POINT = configuration.get(Configuration.CATEGORY_GENERAL, "rmSwordRequiredEMCPerDamagePoint", 32.0, "required EMC for the Red Matter sword to deal one damage point in an area").getDouble(32.0);

    	}
    	catch(Exception e)
    	{
            LogHelper.error(Reference.MOD_NAME + " has had a problem loading its common configuration");
        }
    }
    
    public static void initAndSave(Configuration configuration)
    {
    	init(configuration);
        configuration.save();

    }
}
