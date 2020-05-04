package me.swirtzly.regeneration.client.animation;

import me.swirtzly.animateme.AnimationManager;
import me.swirtzly.regeneration.common.capability.RegenCap;
import me.swirtzly.regeneration.common.entity.TimelordEntity;
import me.swirtzly.regeneration.common.item.FobWatchItem;
import me.swirtzly.regeneration.util.PlayerUtil;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;

public class GeneralAnimations implements AnimationManager.IAnimate {

    public static void copyAnglesToWear(BipedModel modelBiped) {
        // if (modelBiped instanceof PlayerModel) {
        // PlayerModel playerModel = (PlayerModel) modelBiped;
        // ClientUtil.copyAnglesToWear(playerModel);
        // }
    }

    public static void makeZombieArms(BipedModel modelBiped) {
        modelBiped.bipedRightArm.rotateAngleY = -0.1F + modelBiped.bipedHead.rotateAngleY - 0.4F;
        modelBiped.bipedLeftArm.rotateAngleY = 0.1F + modelBiped.bipedHead.rotateAngleY;
        modelBiped.bipedRightArm.rotateAngleX = -((float) Math.PI / 2F) + modelBiped.bipedHead.rotateAngleX;
        modelBiped.bipedLeftArm.rotateAngleX = -((float) Math.PI / 2F) + modelBiped.bipedHead.rotateAngleX;
    }

    @Override
    public void preRenderCallBack(LivingRenderer renderer, LivingEntity entity) {
        if (entity instanceof PlayerEntity || entity instanceof TimelordEntity) {

            RegenCap.get(entity).ifPresent((data) -> {
                BipedModel modelPlayer = (BipedModel) renderer.getEntityModel();

                if (data.hasDroppedHand() && data.getState() == PlayerUtil.RegenState.POST) {
                    modelPlayer.bipedRightArm.isHidden = data.getCutoffHand() == HandSide.RIGHT;
                    modelPlayer.bipedLeftArm.isHidden = data.getCutoffHand() == HandSide.LEFT;
                } else {
                    modelPlayer.bipedLeftArm.isHidden = false;
                    modelPlayer.bipedRightArm.isHidden = false;
                }
            });
        }
    }

    @Override
    public void preAnimation(BipedModel model, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void postAnimation(BipedModel modelBiped, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity instanceof AbstractClientPlayerEntity) {
            AbstractClientPlayerEntity player = (AbstractClientPlayerEntity) entity;

            ItemStack stack = player.getHeldItemMainhand();
            ItemStack offStack = player.getHeldItemOffhand();

            // ==============FOB WATCH & JAR START==============
            boolean isOpen;

            // MAINHAND
            if (stack.getItem() instanceof FobWatchItem) {
                isOpen = FobWatchItem.getOpen(stack) == 1;
                if (isOpen) {
                    makeZombieArms(modelBiped);
                    copyAnglesToWear(modelBiped);
                }
            }

            // OFFHAND
            if (offStack.getItem() instanceof FobWatchItem) {
                isOpen = FobWatchItem.getOpen(stack) == 1;
                if (isOpen) {
                    makeZombieArms(modelBiped);
                    copyAnglesToWear(modelBiped);
                }
            }
            // ==============FOB WATCH END==============

            RegenCap.get(player).ifPresent((data) -> {
                // JAR SYNCING
                if (data.isSyncingToJar()) {
                    makeZombieArms(modelBiped);
                    modelBiped.bipedHead.rotateAngleX = (float) Math.toRadians(45);
                    copyAnglesToWear(modelBiped);
                }
            });

        }
    }

    @Override
    public boolean useVanilla() {
        return false;
    }
}