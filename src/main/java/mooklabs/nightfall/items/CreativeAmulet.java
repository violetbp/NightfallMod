package mooklabs.nightfall.items;

import java.util.List;

import mooklabs.nightfall.MLib;
import mooklabs.nightfall.NFMain;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldSettings.GameType;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.common.lib.PlayerHandler;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CreativeAmulet extends Item implements IBauble {

	
	public CreativeAmulet() {
		this.setMaxStackSize(1);
		this.setUnlocalizedName("creativeAmulet");
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.AMULET;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		PlayerCapabilities cap = ((EntityPlayer)player).capabilities;
            cap.allowFlying = true;
            cap.isCreativeMode = true;
            cap.disableDamage = true;
            cap.allowEdit = true;  
            
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
			MLib.printToPlayer("Creative Amulet Active");
			MLib.printToPlayer("Effects are perminat unless removed");

	        ((EntityPlayer)player).setGameType(GameType.CREATIVE);

			
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
        ((EntityPlayer)player).setGameType(GameType.SURVIVAL);
        for(ItemStack bau: PlayerHandler.getPlayerBaubles((EntityPlayer) player).stackList)
        	if(bau != null && bau.getItem() == NFMain.flyBelt)
        		((EntityPlayer)player).capabilities.isFlying = true;
	}

	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List dataList, boolean bool) {
	    dataList.add("BE CAREFUL (hold shift)");
		if(FMLClientHandler.instance().getClient().currentScreen.isShiftKeyDown()){//is shift down?
			dataList.add("This item forces the EFFECTS of creative mode");
			dataList.add("No matter what they player's gamemode is,");
			dataList.add("they will be 'in' creative");
			dataList.add("(no itemloss, godmode, flight)");
			dataList.add("cannot instakill blocks");

		}
	}

}
