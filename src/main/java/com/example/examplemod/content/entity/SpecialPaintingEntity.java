package com.example.examplemod.content.entity;

import javax.annotation.Nullable;

import com.example.examplemod.core.ExampleMod;
import com.example.examplemod.core.ModEntities;
import com.example.examplemod.core.ModPaintings;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;

public class SpecialPaintingEntity extends HangingEntity implements IEntityAdditionalSpawnData {
	private final String DEFAULT_MOTIVE = "test";
	private String motiveId = DEFAULT_MOTIVE;
	public Motive motive = Motive.KEBAB; //Temp

	public SpecialPaintingEntity(EntityType<? extends SpecialPaintingEntity> entityType, Level level) {
		super(entityType, level);
	}

	public SpecialPaintingEntity(Level level, BlockPos blockpos, Direction direction) {
		this(ModEntities.SPECIAL_PAINTING_ENTITY.get(), level);
		this.pos = blockpos;
		this.setMotiveId(DEFAULT_MOTIVE);
	}

	public SpecialPaintingEntity(Level level, BlockPos blockpos, Direction direction, String motiveId) {
		this(level, blockpos, direction);
		this.setMotiveId(motiveId);
	}

	public ResourceLocation getTextureResourceLocation() {
		return new ResourceLocation(ExampleMod.MODID, "painting/" + motiveId);
	}

	// Sourced from Painting
	@Override
	public void dropItem(@Nullable Entity entity) {
		if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
			this.playSound(SoundEvents.PAINTING_BREAK, 1.0F, 1.0F);
			if (entity instanceof Player) {
				Player player = (Player) entity;
				if (player.getAbilities().instabuild) {
					return;
				}
			}

			ItemStack stack = new ItemStack(
					ForgeRegistries.ITEMS.getValue(new ResourceLocation(ExampleMod.MODID, "special_painting")));
			stack.getOrCreateTag().put(ExampleMod.MODID, new CompoundTag());
			CompoundTag nbt = stack.getOrCreateTag().getCompound(ExampleMod.MODID);
			nbt.putString("Motive", motiveId);

			this.spawnAtLocation(stack, 0F);
		}
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
    @Override
    public void readSpawnData(FriendlyByteBuf buffer) {
        setMotiveId(buffer.readUtf());
        this.direction = Direction.from2DDataValue(buffer.readByte());
        this.setDirection(this.direction);
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        buffer.writeUtf(this.motiveId);
        buffer.writeByte((byte) this.direction.get2DDataValue());
    }

	public void addAdditionalSaveData(CompoundTag tag) {
		tag.putString("Motive", motiveId);
		tag.putByte("Facing", (byte) this.direction.get2DDataValue());
		tag.putBoolean("Invisible", this.isInvisible());
		super.addAdditionalSaveData(tag);
	}

	public void readAdditionalSaveData(CompoundTag tag) {
		setMotiveId(tag.getString("Motive"));
		this.direction = Direction.from2DDataValue(tag.getByte("Facing"));
		this.setInvisible(tag.getBoolean("Invisible"));
		super.readAdditionalSaveData(tag);
		this.setDirection(this.direction);
	}

	@Override
	public int getWidth() {
		return this.motive.getWidth();
	}

	@Override
	public int getHeight() {
		return this.motive.getHeight();
	}
	
	public void setMotiveId(String motiveId) {
		this.motiveId = motiveId;
		this.motive = ModPaintings.SPECIAL_PAINTING_REGISTRY.get()
				.getValue(new ResourceLocation(ExampleMod.MODID, motiveId)).get();
	}

	@Override
	public void playPlacementSound() {
		this.playSound(SoundEvents.PAINTING_PLACE, 1.0F, 1.0F);
	}

	public void moveTo(double p_31929_, double p_31930_, double p_31931_, float p_31932_, float p_31933_) {
		this.setPos(p_31929_, p_31930_, p_31931_);
	}

	public void lerpTo(double p_31917_, double p_31918_, double p_31919_, float p_31920_, float p_31921_, int p_31922_,
			boolean p_31923_) {
		BlockPos blockpos = this.pos.offset(p_31917_ - this.getX(), p_31918_ - this.getY(), p_31919_ - this.getZ());
		this.setPos((double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ());
	}
}
