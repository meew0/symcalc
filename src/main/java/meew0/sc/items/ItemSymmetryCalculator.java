package meew0.sc.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
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

                player.addChatComponentMessage(new ChatComponentText("This infusion altar has a stability value of " +
                        te2.symmetry));

                Field fri = te2.getClass().getDeclaredField("recipeInstability");
                fri.setAccessible(true);
                int recipeInstability = (Integer) fri.get(te2);

                if(recipeInstability > 0) {
                    if(te2.symmetry >= recipeInstability) {
                        player.addChatComponentMessage(new ChatComponentText(
                                "This is enough to stabilize the current recipe with an instability of " +
                                        recipeInstability + "."));
                    } else {
                        player.addChatComponentMessage(new ChatComponentText(
                                "This is not enough to stabilize the current recipe with an instability of " +
                                        recipeInstability + "."));
                    }
                }
                return true;
            }
        } catch(Exception e) {
            player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED +
                    "Stability checking failed! More info in server console"));
            System.out.println("[symcalc] Player " + player.getCommandSenderName() +
                    " failed to get the stability of the runic matrix at " + x + ", " + y + ", " + z + ": ");
            e.printStackTrace();
        }
        return false;
    }
}
