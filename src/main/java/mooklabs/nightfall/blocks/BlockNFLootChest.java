package mooklabs.nightfall.blocks;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import mooklabs.nightfall.NFMain;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockNFLootChest extends BlockChest implements IWailaBlock {

	static public ArrayList<String> names = new ArrayList();

	public BlockNFLootChest() {
		super(0);
		this.setHardness(2.5f);// TODO maybe make it unbreakable just in case
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
		this.names = readFromConfig();


	}

	/**
	 * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		Block it = this;
		ItemStack i = new ItemStack(this, 1, 0);
		list.add(i);
		int num = 1;

		for (String s : this.names) {
			it.setBlockName("Loot Chest");
			i = new ItemStack(it, 1, num++);
			i.setStackDisplayName(s);
			list.add(i);
		}



	}

	public void writeToConfig() {
		try {
			try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("chestSpawns.txt"), "utf-8"))) {
				// writer.write("this is where config goes");
			}
		} catch (IOException ex) {
			NFMain.logger.fatal("File Write Error!");
		}
	}

	public static ArrayList<String> readFromConfig() {
		ArrayList<String> names = new ArrayList();
		String title = "";
		String str = "";
		String modid = "";

		try {

			try (Scanner scanner = new Scanner(new File("config/chestSpawns.txt"), "utf-8")) {
				while (scanner.hasNextLine()) {
					str = scanner.nextLine();

					if (str.startsWith("-")) {
						title = str.replaceFirst("-", "");
						names.add(title);
						NFMain.logger.info(title);
					} else {

						String[] st = str.split("\\|");
						if (st.length == 3)
							//new LootWithChance(modid,st[0],st[1],st[2]);
							if (st.length == 2)
								//new LootWithChance(modid,st[0],"100",st[1]);
								if (st.length == 1)
									modid = st[0];
						if (st.length == 0) NFMain.logger.fatal("Chest Spawn config wrong! Missing argument!");

					}

				}
			}
		} catch (Exception e) {
			NFMain.logger.fatal("File Read Error!");
			NFMain.logger.fatal(e.getMessage());
			e.printStackTrace();

		}
		return names;

	}

	class LootWithChance {

		int chance;
		ItemStack loot;
		final int amount;

		LootWithChance(String modid, String itemStr, String chan, String amt) {
			amount = Integer.parseInt(amt);
			chance = Integer.parseInt(chan);
			loot = new ItemStack(GameRegistry.findItem(modid, itemStr.replaceFirst("Item=", "").trim()), amount);
		}
	}

	// {{Waila

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		currenttip.add(SpecialChars.RESET + this.getLocalizedName());
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		/*TileEntityNFChest tile = ((TileEntityNFChest) accessor.getTileEntity());
		for (ItemStack i : tile.itemsToAdd)
			currenttip.add(i.stackSize + " " + i.getDisplayName() + " will be added");
		currenttip.add("every " + tile.refreshRate + " seconds");*/
		currenttip.add("WOOO LOOT");

		currenttip.add(itemStack.getDisplayName());

		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		currenttip.add(SpecialChars.BLUE + SpecialChars.ITALIC + NFMain.name + "ddddd");
		return currenttip;
	}
	// }}

}