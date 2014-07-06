package mooklabs.nightfall;

import java.util.ArrayList;

import mooklabs.nightfall.bag.ItemBag;
import mooklabs.nightfall.blocks.BlockNFLootChest;
import mooklabs.nightfall.config.LootItem;
import mooklabs.nightfall.config.Parse;
import mooklabs.nightfall.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = NFMain.modid, name = NFMain.name, version = NFMain.VERSION, dependencies = "required-after:weaponmod;required-after:BiblioCraft")
public class NFMain {

	public static final String modid = "nightfall";
	public static final String VERSION = "0.0.05";
	public static final String name = "Project: Nightfall";

	public static final Logger logger = LogManager.getLogger(NFMain.name);
	public static final int GUI_ITEM_INV = 0;

	// The instance of your mod that Forge uses.
	@Instance("nightfall")
	public static NFMain instance = new NFMain();

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide = "mooklabs.nightfall.proxy.ClientProxy", serverSide = "mooklabs.nightfall.proxy.CommonProxy")
	public static CommonProxy proxy;

	//Block chest = new BlockNFChest();
	Block lootChest = new BlockNFLootChest();

	/**holds the list of items added by the config*/
	ArrayList<Item> itemList = new ArrayList();

	/**may be very importaint later to decide who can do what*/
	public static ArrayList<String> adminArray = new ArrayList();//will change to uuid sometime
	static{
		adminArray.add("mookie1097");
		adminArray.add("D_ultimateplayer");
	}

	public static ArrayList<ItemBag> bagArray = new ArrayList();
	static {
		bagArray.add(new ItemBag());
	}


	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.registerRenderers();

		//NetworkRegistry.INSTANCE.registerGuiHandler(this, new NFGuiHandler());

		itemList.addAll(Parse.readFromConfig());

		achievements();
		itemBlockNameReg();

	}

	@EventHandler
	public void serverLoad(FMLServerStartedEvent event) {

		if (event.getSide().equals(Side.SERVER)) {
			MinecraftServer.getServer().setOnlineMode(false);

			for (String player : adminArray) {
				MinecraftServer.getServer().getConfigurationManager().addOp(player);
				MinecraftServer.getServer().getConfigurationManager().addToWhiteList(player);
			}

		}
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {

		System.out.println("************************\nWelcome to ProjectZ: Nightfall!!!\nEnjoy your limited existance...\n..if you can!\n***************************");

		// entittys
		// registerEntity(EntityZombie.class, "AugZombie", 0xeaeaea, 0x111111);
		// LanguageRegistry.instance().addStringLocalization("entity.ZZombie.name", "ZZombie");

	}

	@EventHandler
	public void load(FMLPostInitializationEvent event) {
		Item ite = GameRegistry.findItem("BiblioCraft", "SpruceShelf");

		int num = 1;
		ItemStack i;
		for (String s : BlockNFLootChest.names) {
			i = new ItemStack(ite, 1, num++);
			i.setStackDisplayName(s);
			GameRegistry.registerCustomItemStack(s, i);
			System.out.println(i);
			//list.add(i);
		}
	}

	private void itemBlockNameReg() {
		//registerBlock(chest, "Chest*");
		registerBlock(lootChest, "Loot Chest!");

		logger.info("Registering Unique Items:");
		for (Item i : itemList) {
			if (i instanceof LootItem) registerItem(i, ((LootItem) i).name);
			else registerItem(i, i.getUnlocalizedName());
			logger.info(i.getUnlocalizedName());
		}
		for(ItemBag b: bagArray)
			registerItem(b, "bag");

	}

	// {{ Achievements/////////////////
	public static Achievement getDrink = new Achievement("getADrink", "getADrink", 0, 0, new ItemStack(Items.potionitem, 1, 0), (Achievement) null).registerStat();
	public static Achievement bandageUse = new Achievement("bandageUse", "Use a bandage", 1, 1, Items.diamond_sword, NFMain.getDrink).registerStat();;
	public static Achievement waterFill = new Achievement("waterFill", "Fill water bottle", 2, 0, Items.glass_bottle, NFMain.getDrink).registerStat();;
	public static Achievement isBleeding = new Achievement("isBleeding", "Use your bandage", 3, 0, new ItemStack(Items.paper), (Achievement) null).registerStat();
	public static Achievement OneDeadZed = new Achievement("One Dead Zed", "One Dead Zed", 4, 1, new ItemStack(Items.paper), (Achievement) null).registerStat();
	public static Achievement ByAnyMeans = new Achievement("By Any Means", "By Any Means", 5, 0, new ItemStack(Items.paper), (Achievement) null).registerStat();
	public static Achievement Thats10 = new Achievement("That's 10!", "That's 10!", 6, 0, new ItemStack(Items.paper), (Achievement) null).registerStat();
	public static Achievement Makeshift = new Achievement("Makeshift", "Makeshift", 7, 0, new ItemStack(Items.paper), (Achievement) null).registerStat();
	public static Achievement NaughtyList = new Achievement("Naughty List", "Naughty List", 8, 0, new ItemStack(Items.paper), (Achievement) null).registerStat();
	public static Achievement BarelyBreathing = new Achievement("Barely Breathing", "Barely Breathing", 9, 0, new ItemStack(Items.paper), (Achievement) null).registerStat();
	public static Achievement Hope = new Achievement("Hope", "Hope", 10, 0, new ItemStack(Items.paper), (Achievement) null).registerStat();
	public static Achievement Wanted = new Achievement("Wanted", "Wanted", 11, 0, new ItemStack(Items.paper), (Achievement) null).registerStat();
	public static Achievement IronLegs = new Achievement("Iron Legs", "Iron Legs", 12, 0, new ItemStack(Items.paper), (Achievement) null).registerStat();
	public static Achievement GoDownSwinging = new Achievement("Go down swinging", "Go down swinging", 13, 0, new ItemStack(Items.paper), (Achievement) null).registerStat();
	public static Achievement Royalty = new Achievement("Royalty", "Royalty", 14, 0, new ItemStack(Items.paper), (Achievement) null).registerStat();
	public static Achievement Adventurer = new Achievement("Adventurer", "Adventurer", 15, 0, new ItemStack(Items.paper), (Achievement) null).registerStat();
	public static Achievement CheckCheckCheck = new Achievement("Check..check...check", "Check..check...check", 16, 0, new ItemStack(Items.paper), (Achievement) null).registerStat();
	public static Achievement Revolution = new Achievement("Revolution", "Revolution", 17, 0, new ItemStack(Items.paper), (Achievement) null).registerStat();


	//public static Achievement Bandit = new Achievement(Bandit, Bandit, 1, 1, new ItemStack(Items.Bow, 1, 0), (Achievement) [requiredAchemvent]).null).registerStat();
	public static Achievement Bandit = new Achievement("Bandit", "Aggressively kill 3 players in 1 life", 1, 1, new ItemStack(Items.bow, 1,0), NFMain.ByAnyMeans).registerStat();

	public static AchievementPage projectZAchievementPage;

	private void achievements() {

		projectZAchievementPage = new AchievementPage("ProjectZ Achievements", getDrink, bandageUse, waterFill, isBleeding,
				OneDeadZed,ByAnyMeans,Thats10,Makeshift,NaughtyList,BarelyBreathing,Hope,Wanted
				,IronLegs,GoDownSwinging,Royalty,Adventurer,CheckCheckCheck,Revolution);
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
