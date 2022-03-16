package com.example.examplemod.core;

import com.example.examplemod.content.item.SpecialPaintingItem;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
			ExampleMod.MODID);

	public static final RegistryObject<Item> SPECIAL_PAINTING = ITEMS.register("special_painting",
			() -> new SpecialPaintingItem(ModEntities.SPECIAL_PAINTING_ENTITY_TYPE, new Item.Properties().tab(CreativeModeTab.TAB_MISC).rarity(Rarity.RARE)));
}
