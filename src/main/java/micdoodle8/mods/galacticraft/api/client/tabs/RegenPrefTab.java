package micdoodle8.mods.galacticraft.api.client.tabs;

import me.suff.mc.regen.client.gui.GuiPreferences;
import me.suff.mc.regen.handlers.RegenObjects;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class RegenPrefTab extends AbstractTab {
    public RegenPrefTab() {
        super(0, 0, 0, new ItemStack(RegenObjects.Items.FOB_WATCH.get()));
    }

    @Override
    public void onTabClicked() {
        Minecraft.getInstance().setScreen(new GuiPreferences());
    }

    @Override
    public boolean shouldAddToList() {
        return true;
    }
}