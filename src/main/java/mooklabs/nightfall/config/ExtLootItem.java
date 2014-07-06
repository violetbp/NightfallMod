package mooklabs.nightfall.config;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class ExtLootItem extends Item{

	public String lore;
	public String name;
	int dura;
	Item specialItem;

	public ExtLootItem(Item item, String name,String lore, int dura)
	{
		this.lore = lore;
		this.name = name;
		this.dura = dura;

		this.specialItem = item;
		this.specialItem.setHasSubtypes(true);


	}


	/**
	 * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		ItemStack i = new ItemStack(this.specialItem, 1, 0);
		list.add(i);
		i.setItemDamage(dura);
		i.setStackDisplayName(name);

		list.add(i);

		this.specialItem.setUnlocalizedName(name);
		this.specialItem.setHasSubtypes(true);
		this.specialItem.setCreativeTab(CreativeTabs.tabMaterials);
		ItemStack it = new ItemStack(this.specialItem, 1, 0);
		it.setItemDamage(dura);
		it.setStackDisplayName(name);
		list.add(it);
	}

	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add(lore);

	}

	@Override
	public String getItemStackDisplayName(ItemStack itemStack)
	{
		return name;
	}


}
