package mooklabs.nightfall.items;

import java.util.UUID;

import mooklabs.nightfall.MLib;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import baubles.api.BaubleType;
import baubles.api.IBauble;

public class HealthRing extends Item implements IBauble {

	private static final UUID healthBoostModifierUUID = UUID.fromString("1f0ab580-1f14-11e4-8c21-0800200c9a66");
    private static final AttributeModifier healthBoostModifier = (new AttributeModifier(healthBoostModifierUUID, "Health boost", 50, 2)).setSaved(true);
   
	
	public HealthRing() {
		this.setMaxStackSize(1);
		this.setUnlocalizedName("healthRing");
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.RING;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		//if (player.getEntityAttribute(SharedMonsterAttributes.maxHealth).getModifier(healthBoostModifierUUID) == null)
		//	player.getEntityAttribute(SharedMonsterAttributes.maxHealth).applyModifier(healthBoostModifier);
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		IAttributeInstance iattributeinstance = player.getEntityAttribute(SharedMonsterAttributes.maxHealth);
		if (iattributeinstance.getModifier(healthBoostModifierUUID) == null)
			iattributeinstance.applyModifier(healthBoostModifier);
		else
			MLib.printToPlayer("You allready have that one");
        
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
		 IAttributeInstance iattributeinstance = player.getEntityAttribute(SharedMonsterAttributes.maxHealth);
	        if (iattributeinstance.getModifier(healthBoostModifierUUID) != null)
	        {
	            iattributeinstance.removeModifier(healthBoostModifier);
	        }

	}

	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

}
