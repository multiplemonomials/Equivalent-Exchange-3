package net.multiplemonomials.eer.configuration;

/**
 * Enum used to deduce which config files(s) to reload when a config file is recieved
 * @author Jamie
 *
 */
public enum ReceivedConfigAction
{
	LOAD_AS_COMMON_CONFIG,
	SAVE;
}
