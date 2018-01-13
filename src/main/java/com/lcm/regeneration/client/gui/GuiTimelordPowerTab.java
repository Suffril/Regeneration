package com.lcm.regeneration.client.gui;

import com.lcm.regeneration.RegenerationMod;

import lucraft.mods.lucraftcore.superpowers.gui.GuiSuperpowerAbilities;
import lucraft.mods.lucraftcore.util.helper.StringHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;

public class GuiTimelordPowerTab extends GuiSuperpowerAbilities {
	
	public GuiTimelordPowerTab(EntityPlayer player) {
		super(player);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		buttonList.removeIf(button -> button.displayString.equals("?"));
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		if (RegenerationMod.getConfig().disableTraits) {
			int i = (this.width - this.xSize) / 2;
			int j = (this.height - this.ySize) / 2 + ySize/2;
			
			String txt = StringHelper.translateToLocal("lcm-regen.messages.traitsDisabled");
			int x = this.xSize / 2 - mc.fontRenderer.getStringWidth(txt) / 2;
			mc.fontRenderer.drawString(TextFormatting.BOLD.toString() + TextFormatting.RED + txt, i + x, j, 0x373737);
		}
	}
	
}