package com.example.examplemod.core;

import com.example.examplemod.content.entity.SpecialPaintingEntity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES,
			ExampleMod.MODID);

	// This entity type is referenced in the Special Painting item so it is defined separately here.
	public static final EntityType<SpecialPaintingEntity> SPECIAL_PAINTING_ENTITY_TYPE = EntityType.Builder
			.<SpecialPaintingEntity>of(SpecialPaintingEntity::new, MobCategory.MISC).sized(1F, 1F)
			.clientTrackingRange(10)
			.build((new ResourceLocation(ExampleMod.MODID, "special_painting_entity").toString()));

	public static final RegistryObject<EntityType<SpecialPaintingEntity>> SPECIAL_PAINTING_ENTITY = ENTITIES
			.register("special_painting_entity", () -> SPECIAL_PAINTING_ENTITY_TYPE);
}
