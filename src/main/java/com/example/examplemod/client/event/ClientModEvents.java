package com.example.examplemod.client.event;

import com.example.examplemod.client.SpecialPaintingSprites;
import com.example.examplemod.client.renderer.SpecialPaintingRenderer;
import com.example.examplemod.content.entity.SpecialMotive;
import com.example.examplemod.core.ExampleMod;
import com.example.examplemod.core.ModEntities;
import com.example.examplemod.core.ModPaintings;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = ExampleMod.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
	static final String PAINTING_ATLAS = "textures/atlas/paintings.png";
	
	@SubscribeEvent
	public static void clientSetup(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(ModEntities.SPECIAL_PAINTING_ENTITY_TYPE, SpecialPaintingRenderer::new);
	}

	// Since the paintings in ModPaintings are not registered to the default motives registry, we include them in
	// the painting atlas manually here.
	@SubscribeEvent
	public static void beforeTextureStitch(TextureStitchEvent.Pre event) {
		if (event.getAtlas().location().getPath() != PAINTING_ATLAS) return;
		
		for(RegistryObject<SpecialMotive> entry : ModPaintings.SPECIAL_PAINTING_MOTIVES.getEntries()) {
			ResourceLocation loc = new ResourceLocation(ExampleMod.MODID, "painting/" + entry.getId().getPath());
			event.addSprite(loc);
		}
	}
	
	// Once its done stitching we get all special motives from the custom registry and save their TextureAtlasSprite
	// somewhere so we can later reference them to get the UVs in the renderer.
	@SubscribeEvent
	public static void afterTextureStitch(TextureStitchEvent.Post event) {
		if (event.getAtlas().location().getPath() != PAINTING_ATLAS) return;
		
		for(RegistryObject<SpecialMotive> entry : ModPaintings.SPECIAL_PAINTING_MOTIVES.getEntries()) {
			ResourceLocation loc = new ResourceLocation(ExampleMod.MODID, "painting/" + entry.getId().getPath());
			TextureAtlasSprite sprite = event.getAtlas().getSprite(loc);
			SpecialPaintingSprites.put(loc, sprite);
		}
		
		// Getting the back sprite is not currently working. TODO fix this
		ResourceLocation loc = new ResourceLocation("back");
		TextureAtlasSprite sprite = event.getAtlas().getSprite(loc);
		SpecialPaintingSprites.put(loc, sprite);
	}
	
	public static TextureAtlasSprite getSpecialPaintingSprite(ResourceLocation location) {
		return SpecialPaintingSprites.get(location);
	}
}
