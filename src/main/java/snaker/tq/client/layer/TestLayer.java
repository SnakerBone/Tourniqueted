package snaker.tq.client.layer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import org.jetbrains.annotations.NotNull;
import snaker.snakerlib.utility.RenderStuff;
import snaker.tq.client.RenderTypes;
import snaker.tq.client.Shaders;
import snaker.tq.client.model.entity.TestModel;
import snaker.tq.level.entity.mob.Test;
import snaker.snakerlib.utility.ResourcePath;

/**
 * Created by SnakerBone on 30/07/2023
 **/
public class TestLayer extends RenderLayer<Test, TestModel>
{
    public TestLayer(RenderLayerParent<Test, TestModel> parent)
    {
        super(parent);
    }

    @Override
    public void render(@NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight, @NotNull Test test, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch)
    {
        RenderType type = RenderTypes.custom(DefaultVertexFormat.POSITION_TEX, RenderTypes.sampler(Shaders::getCrystalized, new ResourcePath("textures/sampler/noise_white.png"), true, false));
        RenderStuff.renderLayer(this, stack, source, type, test, packedLight);
    }
}