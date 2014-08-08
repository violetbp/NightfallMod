package mooklabs.nightfall.items;

import java.util.UUID;

import mooklabs.nightfall.MLib;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import baubles.api.BaubleType;
import baubles.api.IBauble;

public class FlyBelt extends Item implements IBauble {

	
	public FlyBelt() {
		this.setMaxStackSize(1);
		this.setUnlocalizedName("flyBelt");
	}
	
	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.BELT;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		((EntityPlayer)player).capabilities.allowFlying = true;//LAG may cause lag(need if also has creative
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		if (((EntityPlayer)player).capabilities.allowFlying == true)
			MLib.printToPlayer("You already have Flight");

	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
        ((EntityPlayer)player).capabilities.allowFlying = false;

	}

	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
		if ( ((EntityPlayer)player).capabilities.isCreativeMode == true)
			MLib.printToPlayer("You are creative, don't flippin use this");
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

}
