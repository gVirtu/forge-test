package com.example.examplemod.content.item;

import com.example.examplemod.content.entity.SpecialPaintingEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HangingEntityItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class SpecialPaintingItem extends HangingEntityItem {
	String DEFAULT_MOTIVE = "test";

	public SpecialPaintingItem(EntityType<? extends HangingEntity> type, Properties properties) {
		super(type, properties);
	}

	@Override
	public InteractionResult useOn(UseOnContext ctx) {
		BlockPos blockpos = ctx.getClickedPos();
		Direction direction = ctx.getClickedFace();
		BlockPos blockpos1 = blockpos.relative(direction);
		Player player = ctx.getPlayer();
		ItemStack itemstack = ctx.getItemInHand();
		if (player != null && !this.mayPlace(player, direction, itemstack, blockpos1)) {
			return InteractionResult.FAIL;
		} else {
			Level level = ctx.getLevel();
			SpecialPaintingEntity hangingentity = new SpecialPaintingEntity(level, blockpos1, direction);

			CompoundTag compoundtag = itemstack.getTag();
			if (compoundtag != null) {
				EntityType.updateCustomEntityTag(level, player, hangingentity, compoundtag);
			}

			if (hangingentity.survives()) {
				if (!level.isClientSide) {
					hangingentity.playPlacementSound();
					level.gameEvent(player, GameEvent.ENTITY_PLACE, blockpos);
					level.addFreshEntity(hangingentity);
				}

				itemstack.shrink(1);
				return InteractionResult.sidedSuccess(level.isClientSide);
			} else {
				return InteractionResult.CONSUME;
			}
		}
	}
}
