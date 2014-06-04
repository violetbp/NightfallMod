package mooklabs.nightfall.proxy;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.world.WorldEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ProjectZEventHandler {

	// for attacks FROM THE PLAYER
	/*@SubscribeEvent
	public void entityattack(AttackEntityEvent event) {
		if (event.target instanceof EntityPlayer) {
			ExtendedPlayer props = ExtendedPlayer.get((EntityPlayer) event.target);
			props.setBleeding(true);
		}else
			event.entityPlayer.addStat(Main.getDrink, 1);

		if (event.entityPlayer.worldObj.isRemote) {
			ExtendedPlayer props = ExtendedPlayer.get(event.entityPlayer);
			// give more if kill player
			props.playerStats.addLevel(EnumPlayerStat.Strength, event.target instanceof EntityPlayer ? 2 : 1);
		}


	}
	//for entityattacks
	@SubscribeEvent
	public void entityattack(LivingAttackEvent event) {
		if (event.source.getEntity() instanceof EntityZombie)
		if(event.entity instanceof EntityPlayer){
			ExtendedPlayer props = ExtendedPlayer.get((EntityPlayer) event.entityLiving);
			props.setBleeding(true);
			props.visibility++;
		}
	}*/

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
	//@SubscribeEvent
	public void entityfall(LivingFallEvent event) {

		if (event.entityLiving instanceof EntityPlayer) {// make sure its a player

		}
	}

	//@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {

	}

	@SubscribeEvent
	public void PotentialSpawns(WorldEvent.PotentialSpawns e) {
		if (e.type.equals(EnumCreatureType.monster)) if (e.list.get(0).entityClass != EntityZombie.class) e.setCanceled(true);
	}

	// we already have this event, but we need to modify it some
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {


			// NOTE: See step 6 for a way to do this all in one line!!!
			// before syncing the properties, we must first check if the player has some saved in the proxy
			// recall that 'getEntityData' also removes it from the map, so be sure to store it locally
			//NBTTagCompound playerData = NFMain.proxy.getEntityData(((EntityPlayer) event.entity).getCommandSenderName());
			// make sure the compound isn't null
			//ExtendedPlayer props = ExtendedPlayer.get((EntityPlayer) event.entity);

			//if (playerData != null) {
			// then load the data back into the player's IExtendedEntityProperties
			//	((ExtendedPlayer) (event.entity.getExtendedProperties(ExtendedPlayer.EXT_PROP_NAME))).loadNBTData(playerData);
			//}
			// finally, we sync the data between server and client (we did this earlier in 3.3)
			// ((ExtendedPlayer)(event.entity.getExtendedProperties(ExtendedPlayer.EXT_PROP_NAME))).syncExtendedProperties();
		}

	}
}
