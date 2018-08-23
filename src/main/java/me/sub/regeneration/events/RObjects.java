package me.sub.regeneration.events;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import me.sub.regeneration.Regeneration;
import me.sub.regeneration.common.items.ItemChameleonArch;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

@Mod.EventBusSubscriber(modid = Regeneration.MODID)
public class RObjects {
	
	public static class Items {
		public static Item chameleonArch = createItem(new ItemChameleonArch(), "chameleonarch");
	}
	
	
	public static class SoundEvents {
		public static final SoundEvent regeneration = new RegenSoundEvent("regeneration");
		public static final SoundEvent fobwatch = new RegenSoundEvent("fob_watch");
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SubscribeEvent
	public static void registerObjects(RegistryEvent ev) {
		if (!(ev instanceof RegistryEvent.Register))
			return;
		IForgeRegistry registry = ((RegistryEvent.Register) ev).getRegistry();
		
		for (Class<?> aClass : RObjects.class.getDeclaredClasses()) {
			if (Arrays.stream(aClass.getDeclaredFields()).noneMatch(field -> registry.getRegistrySuperType().isAssignableFrom(field.getType())))
				continue;
			ArrayList<IForgeRegistryEntry> entries = new ArrayList<>();
			
			for (Field field : aClass.getDeclaredFields())
				try {
					entries.add((IForgeRegistryEntry) field.get(null));
				} catch (IllegalAccessException | ClassCastException e) {
					throw new RuntimeException("Incorrect field in object sub-class", e);
				}
			
			entries.forEach(registry::register);
		}
	}
	
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent ev) {
		for (Field f : Items.class.getDeclaredFields()) {
			try {
				Item item = (Item) f.get(null);
				ModelResourceLocation loc = new ModelResourceLocation(item.getRegistryName(), "inventory");
				ModelLoader.setCustomModelResourceLocation(item, 0, loc);
			} catch (IllegalAccessException | ClassCastException e) {
				throw new RuntimeException("Incorrect field in item sub-class", e);
			}
		}
	}
	
	public static Item createItem(Item item, String name){
		item.setRegistryName(Regeneration.MODID, name);
		item.setUnlocalizedName(name);
		item.setCreativeTab(CreativeTabs.MISC);
		return item;
	}
	
	
	public static class RegenSoundEvent extends SoundEvent {
		public RegenSoundEvent(String name) {
			super(new ResourceLocation(Regeneration.MODID, name));
			setRegistryName(Regeneration.MODID, name);
		}
	}
}
