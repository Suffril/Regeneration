package me.swirtzly.regeneration.client.rendering.types;

import me.swirtzly.regeneration.common.capability.IRegen;
import me.swirtzly.regeneration.common.capability.RegenCap;
import me.swirtzly.regeneration.common.types.ElixirType;
import me.swirtzly.regeneration.common.types.TypeManager;
import me.swirtzly.regeneration.util.PlayerUtil;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.HandSide;
import net.minecraftforge.client.event.RenderPlayerEvent;

import static me.swirtzly.regeneration.client.animation.GeneralAnimations.copyAnglesToWear;

public class ElixirRenderer extends ATypeRenderer<ElixirType> {

    public static final ElixirRenderer INSTANCE = new ElixirRenderer();

    public ElixirRenderer() {
    }

    @Override
    protected void renderRegeneratingPlayerPre(ElixirType type, RenderPlayerEvent.Pre event, IRegen capability) {

    }

    @Override
    protected void renderRegeneratingPlayerPost(ElixirType type, RenderPlayerEvent.Post event, IRegen capability) {

    }

    @Override
    protected void renderRegenerationLayer(ElixirType type, LivingRenderer renderLivingBase, IRegen capability, PlayerEntity entityPlayer, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {

    }

    @Override
    public void renderHand(PlayerEntity player, HandSide handSide, LivingRenderer render) {

    }


    @Override
    public void preRenderCallBack(LivingRenderer renderer, LivingEntity entity) {

    }

    @Override
    public void preAnimation(BipedModel model, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void postAnimation(BipedModel modelBiped, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        RegenCap.get(entity).ifPresent((data) -> {
            if (data.getType() == TypeManager.Type.CONFUSED && data.getState() == PlayerUtil.RegenState.REGENERATING) {
                modelBiped.bipedRightArm.rotateAngleX = (float) Math.toRadians(-90);
                modelBiped.bipedLeftArm.rotateAngleX = (float) Math.toRadians(-90);
                copyAnglesToWear(modelBiped);
            }
        });
    }

    @Override
    public boolean useVanilla() {
        return false;
    }
}