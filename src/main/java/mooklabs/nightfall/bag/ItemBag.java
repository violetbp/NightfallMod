package mooklabs.nightfall.bag;

import mooklabs.nightfall.NFMain;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class ItemBag extends Item {

	public ItemBag() {

		// ItemStacks that store an NBT Tag Compound are limited to stack size of 1
		this.setMaxStackSize(1);
		this.setUnlocalizedName("bag");
	}

	// Without this method, your inventory will NOT work!!!
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 1; // return any value greater than zero
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		// only want to check on server - the client gui will be opened automatically by the gui handler
		if (!world.isRemote) {
			// you may or may not want to check if the player is sneaking - up to you
			if (!player.isSneaking()) {
				// open the inventory:
				player.openGui(NFMain.instance, NFMain.GUI_ITEM_INV, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
			} else {
				// if you're sneaky, I'll throw some diamonds in your back for you, but only in the first slot!
				new InventoryItemBag(player.getHeldItem()).setInventorySlotContents(0, new ItemStack(Items.diamond, 4));
			}
			//CHANGE VISIBILEITY WHEN OPEN BAG
		}
		return stack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister)
	{
		itemIcon = iconRegister.registerIcon(NFMain.modid +":" + getUnlocalizedName().substring(5));
	}
}