package mooklabs.nightfall.items;

import mooklabs.nightfall.NFMain;
import mooklabs.nightfall.bag.ContainerItem;
import mooklabs.nightfall.bag.GuiItemInventory;
import mooklabs.nightfall.bag.InventoryItemBag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class NFGuiHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z)
	{
		// Hooray, no 'magic' numbers - we know exactly which Gui this refers to
		if (guiId == NFMain.GUI_ITEM_INV)
		{
			// Use the player's held item to create the inventory
			return new ContainerItem(player, player.inventory, new InventoryItemBag(player.getHeldItem()));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z)
	{
		if (guiId == NFMain.GUI_ITEM_INV)
		{
			// We have to cast the new container as our custom class
			// and pass in currently held item for the inventory
			return new GuiItemInventory(new ContainerItem(player, player.inventory, new InventoryItemBag(player.getHeldItem())));
		}
		return null;
	}
}