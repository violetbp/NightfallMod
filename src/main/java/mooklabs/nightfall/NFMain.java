package mooklabs.nightfall;

import java.util.ArrayList;

import mooklabs.nightfall.blocks.BlockNFChest;
import mooklabs.nightfall.config.LootItem;
import mooklabs.nightfall.config.Parse;
import mooklabs.nightfall.proxy.CommonProxy;
import mooklabs.nightfall.proxy.ProjectZEventHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = NFMain.modid, name = NFMain.name, version = "Alpha 0.0.00001", dependencies = "required-after:weaponmod")
public class NFMain{

	public static final String modid = "nightfall";
	public static final String VERSION = "0.0.04";
	public static final String name = "Project: Nightfall";

	public static final Logger logger = LogManager.getLogger(NFMain.name);

	// The instance of your mod that Forge uses.
	@Instance("nightfall")
	public static NFMain instance = new NFMain();

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide = "mooklabs.nightfall.proxy.ClientProxy", serverSide = "mooklabs.nightfall.proxy.CommonProxy")
	public static CommonProxy proxy;

	Block chest = new BlockNFChest();
	/**
	 * holds the list of items added by the config
	 */
	ArrayList<Item> itemList= new ArrayList();
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.registerRenderers();
		MinecraftForge.EVENT_BUS.register(new ProjectZEventHandler());

		itemList.addAll(Parse.readFromConfig());


		achievements();
		itemBlockNameReg();


	}

	@EventHandler
	public void serverLoad(FMLServerStartedEvent event) {

		if(event.getSide().equals(Side.SERVER)){
			MinecraftServer.getServer().setOnlineMode(false);
		}
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {

		System.out.println("************************\nWelcome to ProjectZ: Nightfall!!!\nEnjoy your limited existance...\n..if you can!\n***************************");

		// entittys
		//registerEntity(EntityZombie.class, "AugZombie", 0xeaeaea, 0x111111);
		//LanguageRegistry.instance().addStringLocalization("entity.ZZombie.name", "ZZombie");
	}

	private void itemBlockNameReg() {
		registerBlock(chest, "Chest*");

		logger.info("Registering Unique Items:");
		for(Item i:itemList){
			if(i instanceof LootItem)
				registerItem(i, ((LootItem)i).name);
			else registerItem(i, i.getUnlocalizedName());

			logger.info(i.getUnlocalizedName());
		}


	}

	// {{ Achievements/////////////////
	public static Achievement getDrink;
	public static Achievement bandageUse;
	public static Achievement waterFill;
	public static Achievement isBleeding;

	public static AchievementPage projectZAchievementPage;

	private void achievements() {
		NFMain.isBleeding = new Achievement("isBleeding", "Use your bandage", -1, 0, new ItemStack(Items.paper), (Achievement) null).registerStat();

		NFMain.getDrink = new Achievement("getADrink", "Get a drink", 0, 0, new ItemStack(Items.potionitem, 1, 0), (Achievement) null).registerStat();
		NFMain.waterFill = new Achievement("waterFill", "Fill water bottle", 1, 0, Items.glass_bottle, NFMain.getDrink).registerStat();

		NFMain.bandageUse = new Achievement("bandageUse", "Use a bandage", 0, 1, Items.diamond_sword, NFMain.getDrink).registerStat();

		// NFMain.bandageUse = new Achievement("killPlayer", "Kill a player", 0, 1, Items.diamond_sword, NFMain.getDrink).setSpecial().registerStat();
		// NFMain.bandageUse = new Achievement("killPlayerZombie", "Kill a player's zombie", 0, 1, Items.diamond_sword, NFMain.getDrink).registerStat();

		projectZAchievementPage = new AchievementPage("ProjectZ Achievements", getDrink, bandageUse, waterFill,isBleeding);
		AchievementPage.registerAchievementPage(projectZAchievementPage);

	}// }}

	// {{///////////extra crap////////////
	public static void registerBlock(Block block, String name) {
		GameRegistry.registerBlock(block, block.getUnlocalizedName());
		LanguageRegistry.addName(block, name);
	}

	public static void registerItem(Item item, String name) {
		GameRegistry.registerItem(item, item.getUnlocalizedName());
		LanguageRegistry.addName(item, name);
	}

	// Tile Entities
	public void tileEntities() {
		// GameRegistry.registerTileEntity(TileEntityGliderBuilder.class, "GliderBuilderTileEntity");
	}

	// Network Registry
	public void networkRegisters() {
		// NetworkRegistry.instance().registerGuiHandler(this, guiHandlerGliderBuilder);
	}
	// }}

}
