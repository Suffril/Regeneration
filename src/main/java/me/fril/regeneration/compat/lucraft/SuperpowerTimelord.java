package me.fril.regeneration.compat.lucraft;

import static me.fril.regeneration.compat.lucraft.LCCoreBarEntry.*;

import lucraft.mods.lucraftcore.superpowers.Superpower;
import lucraft.mods.lucraftcore.superpowers.SuperpowerPlayerHandler;
import lucraft.mods.lucraftcore.superpowers.capabilities.ISuperpowerCapability;
import me.fril.regeneration.RegenConfig;
import me.fril.regeneration.common.capability.CapabilityRegeneration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SuperpowerTimelord extends Superpower {
	
	public SuperpowerTimelord(String name) {
		super(name);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getCapsuleColor() {
		return 16745472;
	}
	
	@Override
	public void renderIcon(Minecraft mc, Gui gui, int x, int y) {
		GlStateManager.pushMatrix();
		mc.renderEngine.bindTexture(ICON_TEX);
		GlStateManager.translate(x, y, 0);
		GlStateManager.scale(2, 2, 0);
		gui.drawTexturedModalRect(0, 0, 9 * 16, 16, 16, 16);
		GlStateManager.popMatrix();
	}
	
	@Override
	public SuperpowerPlayerHandler getNewSuperpowerHandler(ISuperpowerCapability cap) {
		return new SuperpowerPlayerHandler(cap, this) {
			
			@Override
			public void onApplyPower() {
				super.onApplyPower();
				if (!getPlayer().world.isRemote) {
					CapabilityRegeneration.getForPlayer(getPlayer()).receiveRegenerations(RegenConfig.regenCapacity);
				}
			}
			
			// When superpower gets removed, remove all left regenerations
			@Override
			public void onRemove() {
				if (!getPlayer().world.isRemote)
					CapabilityRegeneration.getForPlayer(getPlayer()).extractRegeneration(CapabilityRegeneration.getForPlayer(getPlayer()).getRegenerationsLeft());
			}
		};
	}
}