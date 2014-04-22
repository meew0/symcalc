package meew0.sc.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.common.tiles.TileInfusionMatrix;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by meew0 on 21.04.14.
 */
public class ItemSymmetryCalculator extends Item {

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int m, float dx, float dy, float dz) {
        try {
            TileEntity te = world.getTileEntity(x, y, z);
            if (te instanceof TileInfusionMatrix) {
                TileInfusionMatrix te2 = (TileInfusionMatrix) te;
                Method mgs = te2.getClass().getDeclaredMethod("getSurroundings"); // using reflection to force a getSurroundings

                mgs.setAccessible(true);
                mgs.invoke(te2);

                int sym = -te2.symmetry;

                player.addChatComponentMessage(new ChatComponentText(
                        StatCollector.translateToLocal("symcalc.message.value").replace("%s", "" + sym)));

                player.addChatComponentMessage(new ChatComponentText(
                        StatCollector.translateToLocal("symcalc.message.sufficient").replace("%i",
                                StatCollector.translateToLocal("symcalc.message.sufficient." +
                                Math.min(15, sym)))));

                Field fri = te2.getClass().getDeclaredField("recipeInstability");
                fri.setAccessible(true);
                int recipeInstability = (Integer) fri.get(te2);

                if(recipeInstability > 0) {
                    String msg = StatCollector.translateToLocal("symcalc.message.recipe").replace("%s", "" +
                            recipeInstability);
                    String y2 = "symcalc.message.recipe.no";
                    if(sym >= recipeInstability) {
                        y2 = "symcalc.message.recipe.yes";
                    }
                    player.addChatComponentMessage(new ChatComponentText(msg.replace("%y", y2)));
                }
                return true;
            }
        } catch(Exception e) {
            player.addChatComponentMessage(new ChatComponentText(
                    StatCollector.translateToLocal("symcalc.message.error")));
            System.out.println("[symcalc] Player " + player.getCommandSenderName() +
                    " failed to get the stability of the runic matrix at " + x + ", " + y + ", " + z + ": ");
            e.printStackTrace();
        }
        return false;
    }
}
