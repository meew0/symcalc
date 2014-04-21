package meew0.sc;

import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import thaumcraft.common.config;

@Mod(modid = SymcalcMod.MODID, version = SymcalcMod.VERSION, name = SymcalcMod.NAME)
public class SymcalcMod
{
    public static final String MODID = "symcalc";
    public static final String VERSION = "0.01";
    public static final String NAME = "SymCalc";

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        System.out.println("DIRT BLOCK >> "+Blocks.dirt.getUnlocalizedName());
        System.out.println("TABLE >> "+ConfigBlocks.blockTable.getUnlocalizedName());
    }
}
