package com.example.examplemod.client;

import java.util.HashMap;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;

public class SpecialPaintingSprites {
	static HashMap<ResourceLocation, TextureAtlasSprite> STORE = new HashMap<>();
	
	public static void put(ResourceLocation key, TextureAtlasSprite value) {
		STORE.put(key, value);
	}
	
	public static TextureAtlasSprite get(ResourceLocation key) {
		return STORE.get(key);
	}
}
