package net.multiplemonomials.eer.interfaces;

public interface ITileEntityAcceptsEMC
{
	/**
	 * Returns the amount of EMC that the tile entity is currently storing
	 * @return
	 */
	public double getStoredEMC();
	
	/**
	 * Returns the max EMC that the tile entity can store
	 * @return
	 */
	public double getMaxEMC();
	
	/**
	 * Try to add EMC to the TileEntity's storage.
	 * Returns the amount of EMC that was added
	 * @return
	 */
	public double tryAddEMC(double amountToAdd);
}
