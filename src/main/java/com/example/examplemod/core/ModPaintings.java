package com.example.examplemod.core;

import java.util.function.Supplier;

import com.example.examplemod.content.entity.SpecialMotive;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

public class ModPaintings {
	// Motives for the Special Painting are not included in the default painting registry in order to 
	// provide separate textures than the regular painting's.
	public static final DeferredRegister<SpecialMotive> SPECIAL_PAINTING_MOTIVES = DeferredRegister
			.create(SpecialMotive.class, ExampleMod.MODID);

	public static final Supplier<IForgeRegistry<SpecialMotive>> SPECIAL_PAINTING_REGISTRY = SPECIAL_PAINTING_MOTIVES
			.makeRegistry("special_painting_registry", RegistryBuilder::new);

	// We register the special motives here.
	public static final RegistryObject<SpecialMotive> TEST_MOTIVE = SPECIAL_PAINTING_MOTIVES.register("test",
			() -> new SpecialMotive(32, 32));
}
