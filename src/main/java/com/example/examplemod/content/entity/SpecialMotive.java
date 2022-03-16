package com.example.examplemod.content.entity;

import net.minecraft.world.entity.decoration.Motive;

public class SpecialMotive extends net.minecraftforge.registries.ForgeRegistryEntry<SpecialMotive> {
	// This class only exists for the purpose of having a registry of it. It wraps Motive.
	Motive motive;

	public SpecialMotive(int w, int h) {
		motive = new Motive(w, h);
	}

	public Motive get() {
		return motive;
	}
};