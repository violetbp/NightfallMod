package mooklabs.nightfall.config;

import java.util.List;

import mooklabs.nightfall.NFMain;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.util.EnumHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class LootItem extends ItemSword{

	public String lore;
	public String name;
	int dura;

	public LootItem(String name,String lore,String tex, float dam, int maxDura, int dura)
	{
		super(EnumHelper.addToolMaterial(name, 0, maxDura, 1F, dam, 0));
		this.lore = lore;
		this.name = name;
		this.dura = dura;
		this.setUnlocalizedName(name);
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setTextureName(NFMain.modid+":"+tex);

	}


	/**
	 * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		ItemStack i = new ItemStack(item, 1, 0);
		i.setItemDamage(this.dura);
		list.add(i);

	}

	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add(this.lore);

	}

	@Override
	public String getItemStackDisplayName(ItemStack itemStack)
	{
		return name;
	}


}
