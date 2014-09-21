package net.multiplemonomials.eer.configuration;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.multiplemonomials.eer.reference.Reference;
import net.multiplemonomials.eer.util.LogHelper;

public class CommonConfiguration
{
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

		
    public static void init(File configPath)
    {
    	Configuration configuration = new Configuration(configPath);
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
			
			ENERGY_COLLECTOR_DRAIN_RATE[0] = (configuration.get(Configuration.CATEGORY_GENERAL, "energyCollectorDrainRateLvlOne", 4.0, "rate at which a level 1 energy collector drains to an alcemical battery.").getDouble(4.0));
			
			ENERGY_COLLECTOR_DRAIN_RATE[1] = (configuration.get(Configuration.CATEGORY_GENERAL, "energyCollectorDrainRateLvlTwo", 10.0, "rate at which a level 2 energy collector drains to an alcemical battery.").getDouble(10.0));
			
			ENERGY_COLLECTOR_DRAIN_RATE[2] = (configuration.get(Configuration.CATEGORY_GENERAL, "energyCollectorDrainRateLvlThree", 20.0, "rate at which a level 3 energy collector drains to an alcemical battery.").getDouble(20.0));

    	}
    	catch(Exception e)
    	{
            LogHelper.error(Reference.MOD_NAME + " has had a problem loading its common configuration");
        }
        finally
        {
            configuration.save();
        }
    }
}
