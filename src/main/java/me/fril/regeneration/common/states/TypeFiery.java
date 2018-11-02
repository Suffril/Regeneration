package me.fril.regeneration.common.states;

import java.util.Random;

import me.fril.regeneration.common.capability.CapabilityRegeneration;
import me.fril.regeneration.common.capability.IRegeneration;
import me.fril.regeneration.util.PlayerUtil;
import me.fril.regeneration.util.RegenObjects;
import me.fril.regeneration.util.RegenConfig;
import net.minecraft.block.BlockFire;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

/**
 * Created by Sub
 * on 16/09/2018.
 */
public class TypeFiery implements IRegenType {
	@Override
	public String getName() {
		return "FIERY";
	}
	
	@Override
	public void onUpdateInitial(EntityPlayer player) {
		player.extinguish();
		player.setArrowCountInEntity(0);
	}
	
	@Override
	public void onUpdateMidRegen(EntityPlayer player) {
		player.extinguish();
		
		Random rand = player.world.rand;
		player.rotationPitch += (rand.nextInt(10) - 5) * 0.2;
		player.rotationYaw += (rand.nextInt(10) - 5) * 0.2;
		
		if (player.world.isRemote)
			return;
		
		if (player.world.getBlockState(player.getPosition()).getBlock() instanceof BlockFire)
			player.world.setBlockToAir(player.getPosition());
		double x = player.posX + player.getRNG().nextGaussian() * 2;
		double y = player.posY + 0.5 + player.getRNG().nextGaussian() * 2;
		double z = player.posZ + player.getRNG().nextGaussian() * 2;
		
		IRegeneration capa = CapabilityRegeneration.get(player);
		
		if (capa.getTicksRegenerating() > 150 && capa.getTicksRegenerating() < 152) {
			if (!player.world.isRemote) {
				PlayerUtil.damagePlayerArmor((EntityPlayerMP) player);
			}
		}
		
		player.world.newExplosion(player, x, y, z, 1, RegenConfig.fieryRegen, false);
		for (BlockPos bs : BlockPos.getAllInBox(player.getPosition().north().west(), player.getPosition().south().east()))
			if (player.world.getBlockState(bs).getBlock() instanceof BlockFire) {
				player.world.setBlockToAir(bs);
			}
	}
	
	@Override
	public void onFinish(EntityPlayer player) {
		if (player.world.isRemote)
			return;
		
		IRegeneration handler = CapabilityRegeneration.get(player);
		// handler.setTrait(TraitHandler.getRandomTrait());
		// player.sendStatusMessage(new TextComponentTranslation(handler.getTrait().getMessage()), true);
		handler.sync();
	}
	
	@Override
	public SoundEvent getSound() {
		return RegenObjects.Sounds.REGENERATION;
	}
	
	@Override
	public boolean blockMovement() {
		return true;
	}
	
	@Override
	public boolean isLaying() {
		return false;
	}
}