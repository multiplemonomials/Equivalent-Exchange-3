package net.multiplemonomials.eer.util;

import net.multiplemonomials.eer.exchange.EnergyRegistry;

public class EmcInitializationHelper implements Runnable
{
    private static EmcInitializationHelper instance = new EmcInitializationHelper();

    public static void initEmcRegistry()
    {
        new Thread(instance, "EE3 - DynEmc Init Thread").start();
    }

    @Override
    public void run()
    {
    	try
    	{
	        long startTime = System.currentTimeMillis();
	        EnergyRegistry.getInstance();
	        long duration = System.currentTimeMillis() - startTime;
	        if (duration > 10)
	        {
	            LogHelper.info(String.format("DynEmc system initialized after %s ms", duration));
	        }
    	}
    	catch(RuntimeException error)
    	{
    		LogHelper.error("Um, the EER DynEMC system failed to initialize.  Uh-oh.");
    		error.printStackTrace();
    	}
    }
}
