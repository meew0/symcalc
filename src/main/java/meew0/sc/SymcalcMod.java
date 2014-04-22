package meew0.sc;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import meew0.sc.items.ItemSymmetryCalculator;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.Logger;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.config.ConfigResearch;

@Mod(modid = SymcalcMod.MODID, version = SymcalcMod.VERSION, name = SymcalcMod.NAME, dependencies = SymcalcMod.DEP)
public class SymcalcMod
{
    public static final String MODID = "symcalc";
    public static final String VERSION = "0.1";
    public static final String NAME = "SymCalc";
    public static final String DEP = "required-after:Thaumcraft@[4.1.1.10,)";

    public static final String SYMCALC_KEY = "SYMMETRY_CALCULATOR";

    public static Item itemSymCalc;
    public static ResearchItem researchSymCalc;

    public static Logger log;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        log = event.getModLog();
        log.info(NAME + " " + VERSION + " initializing");

        itemSymCalc = new ItemSymmetryCalculator()
                .setUnlocalizedName("symmetryCalculator")
                .setTextureName("symcalc:symmetry_calculator")
                .setMaxStackSize(1)
                .setCreativeTab(CreativeTabs.tabAllSearch);

        GameRegistry.registerItem(itemSymCalc, "symmetryCalculator");

        log.info("Added symmetry calculator item");
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        ConfigResearch.recipes.put("SymmetryCalculator",
                ThaumcraftApi.addArcaneCraftingRecipe(
                        SYMCALC_KEY,
                        new ItemStack(itemSymCalc),

                        new AspectList()
                                .add(Aspect.ORDER, 60)
                                .add(Aspect.WATER, 25),

                        " AW", "SBS", "SSS",
                        'S', new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 6),      // arcane stone
                        'B', new ItemStack(ConfigBlocks.blockCosmeticOpaque, 1, 0),     // amber block
                        'A', new ItemStack(ConfigItems.itemResource, 1, 6),             // amber
                        'W', new ItemStack(ConfigItems.itemShard, 1, 2)                 // water shard
                ));

        log.info("Added arcane recipe");

        researchSymCalc = new ResearchItem(
                SYMCALC_KEY, "ARTIFICE",
                new AspectList()
                        .add(Aspect.ORDER, 5)                                   // ordo
                        .add(Aspect.CRAFT, 8)                                   // fabrico
                        .add(Aspect.MAGIC, 3)                                   // praecantatio
                        .add(Aspect.MECHANISM, 3),                              // machina
                -6, 6, 2,

                new ItemStack(itemSymCalc, 1, 0))
                .setPages(
                        new ResearchPage[] {
                                new ResearchPage("symcalc.research.1"),
                                new ResearchPage((IArcaneRecipe) ConfigResearch.recipes.get("SymmetryCalculator"))
                        })
                .setParents(new String[]{"INFUSION"})
                .setHidden()
                .setItemTriggers(new ItemStack(ConfigBlocks.blockStoneDevice, 1, 2))    // runic matrix
                .registerResearchItem();

        log.info("Added research");
    }
}
