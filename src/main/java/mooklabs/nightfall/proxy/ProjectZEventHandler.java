package mooklabs.nightfall.proxy;

import mooklabs.nightfall.MLib;
import mooklabs.nightfall.NFMain;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.DamageSource;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.WorldEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ProjectZEventHandler {

	// for attacks FROM THE PLAYER
	@SubscribeEvent
	public void entityattack(AttackEntityEvent event) {
		if (event.target instanceof EntityPlayer) {
			ExtendedPlayer props = ExtendedPlayer.get(event.entityPlayer);
			props.changeMana(4);
			MLib.printDebugToPlayer("-sanity for attacking player");

			ExtendedPlayer propsForAttacked = ExtendedPlayer.get((EntityPlayer) event.target);
			// propsForAttacked.setBleeding(true);
		} else {
			event.entityPlayer.addStat(NFMain.getDrink, 1);
			ExtendedPlayer props = ExtendedPlayer.get(event.entityPlayer);
			props.changeMana(-1);
			MLib.printDebugToPlayer("+sanity for attacking zombie");
		}

		// FOR STATS PROBLY WONT USE
		if (event.entityPlayer.worldObj.isRemote) {
			// give more if kill player?
			// props.playerStats.addLevel(EnumPlayerStat.Strength, event.target instanceof EntityPlayer ? 2 : 1);
		}

	}

	// for entityattacks
	@SubscribeEvent
	public void entityattack(LivingAttackEvent event) {
		if (event.source.getEntity() instanceof EntityZombie) if (event.entityLiving instanceof EntityPlayer) {
			ExtendedPlayer props = ExtendedPlayer.get((EntityPlayer) event.entityLiving);
			// props.setBleeding(true);
			// props.visibility++;
		}
	}

	public static DamageSource bleeding = new DamageSource("bleeding").setDamageBypassesArmor();

	/*@SubscribeEvent
	public void onTick(LivingUpdateEvent event) {
		if (event.entityLiving instanceof EntityPlayer) {
			if(true){
				EntityPlayer player = Minecraft.getMinecraft().thePlayer;
				ExtendedPlayer props = ExtendedPlayer.get(player);
				//System.out.println(props.getBleeding());
				if (props.getBleeding()) player.attackEntityFrom(bleeding, .5F);


			}
			ArrayList<EntityPlayerMP> playerList = new ArrayList<EntityPlayerMP>();
			ListIterator itl;
			if (MinecraftServer.getServer().isSinglePlayer()) {
				EntityPlayer player = Minecraft.getMinecraft().thePlayer;
				ExtendedPlayer props = ExtendedPlayer.get(player);

				props.lowerVisibility();

				if (props.getBleeding()) player.attackEntityFrom(bleeding, .5F);

			} else {
				for (int i = 0; i < MinecraftServer.getServer().worldServers.length; i++) {
					itl = MinecraftServer.getServer().worldServers[i].playerEntities.listIterator();
					while (itl.hasNext())
						playerList.add((EntityPlayerMP) itl.next());

				}

				ExtendedPlayer props;
				for (EntityPlayer player : playerList) {
					props = ExtendedPlayer.get(player);
					props.visibility -= .1;

					if (props.getBleeding()) player.attackEntityFrom(bleeding, .5F);
				}
			}
		}
	}*/

	/** for falling-will make more visible the faster you fall */
	@SubscribeEvent
	public void entityfall(LivingFallEvent event) {

		if (event.entityLiving instanceof EntityPlayer) {// make sure its a player
			//this.alterVisibility((EntityPlayer)event.entityLiving, .4);

			ExtendedPlayer props = ExtendedPlayer.get((EntityPlayer) event.entityLiving);
			// props.changeMana(-4);
			// System.out.println(props.getInsanity()+"++4"+((EntityPlayer) event.entityLiving).getCommandSenderName()+" is the name");
		}
	}

	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		/*Be sure to check if the entity being constructed is the correct type for the extended properties you're about to add! The null check may not be necessary - I only use it to make sure properties are only registered once per entity */
		if (event.entity instanceof EntityPlayer && ExtendedPlayer.get((EntityPlayer) event.entity) == null) {
			// NFMain.logger.info("current " + ExtendedPlayer.get((EntityPlayer) event.entity).currentInsanity);
			ExtendedPlayer.register((EntityPlayer) event.entity);
			NFMain.logger.info("Creating new playerExt for ");// + event.entity.getCommandSenderName());
		}

	}

	/**
	 * ensure no other mobs spawn
	 * 
	 * @param event
	 */
	@SubscribeEvent
	public void PotentialSpawns(WorldEvent.PotentialSpawns e) {
		if (e.type.equals(EnumCreatureType.monster)) {
			for (SpawnListEntry s : e.list)
				if (s.entityClass != EntityZombie.class) {
					e.setCanceled(true);
					break;
				}
		}
	}

	// we already have this event, but we need to modify it some
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {

			// NOTE: See step 6 for a way to do this all in one line!!!
			// before syncing the properties, we must first check if the player has some saved in the proxy
			// recall that 'getEntityData' also removes it from the map, so be sure to store it locally
			// NBTTagCompound playerData = NFMain.proxy.getEntityData(((EntityPlayer) event.entity).getCommandSenderName());
			// make sure the compound isn't null
			// ExtendedPlayer props = ExtendedPlayer.get((EntityPlayer) event.entity);

			// if (playerData != null) {
			// then load the data back into the player's IExtendedEntityProperties
			// ((ExtendedPlayer) (event.entity.getExtendedProperties(ExtendedPlayer.EXT_PROP_NAME))).loadNBTData(playerData);
			// }
			// finally, we sync the data between server and client (we did this earlier in 3.3)
			// ((ExtendedPlayer)(event.entity.getExtendedProperties(ExtendedPlayer.EXT_PROP_NAME))).syncExtendedProperties();
		}

	}

	public void onPlayerOpenInventory(PlayerInteractEvent event) {
		if (event.action.equals(Action.RIGHT_CLICK_BLOCK) && IInventory.class.isInstance(event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z))) {
			((IInventory) (event.entityPlayer.worldObj.getTileEntity(event.x, event.y, event.z))).getInventoryName();

		}
	}

	public void alterVisibility(EntityPlayer player, double change) {
		alterVisibility(player, change, 4, 1000);
	}

	public void alterVisibility(final EntityPlayer player, final double change, final int delay, final int delayStep) {
		player.experience+=change;//add exp(vis)
		new Thread(new Runnable() {
			double dec = delay;
			double rte = change / delay;
			@Override
			public void run() {
				while (dec > 0) {
					dec--;
					try {Thread.sleep(delayStep);
					} catch (InterruptedException e) {e.printStackTrace();}
					player.experience-=rte;//slowly reduce it

				}
			}
		}).start();
	}

}
