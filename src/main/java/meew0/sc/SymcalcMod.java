package meew0.sc;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import meew0.sc.items.ItemSymmetryCalculator;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.item.Item;
import thaumcraft.common.config.ConfigBlocks;

@Mod(modid = SymcalcMod.MODID, version = SymcalcMod.VERSION, name = SymcalcMod.NAME)
public class SymcalcMod
{
    public static final String MODID = "symcalc";
    public static final String VERSION = "0.01";
    public static final String NAME = "SymCalc";

    public static Item itemSymCalc;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        itemSymCalc = new ItemSymmetryCalculator().setUnlocalizedName("symmetryCalculator").setTextureName("symcalc:symmetry_calculator").setMaxStackSize(1);
        GameRegistry.registerItem(itemSymCalc, "symmetryCalculator");
    }
}
