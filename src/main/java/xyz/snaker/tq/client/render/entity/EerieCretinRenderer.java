package xyz.snaker.tq.client.render.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import xyz.snaker.tq.client.layer.EerieCretinLayer;
import xyz.snaker.tq.client.model.entity.EerieCretinModel;
import xyz.snaker.tq.level.entity.mob.EerieCretin;
import xyz.snaker.snakerlib.utility.ResourcePath;

/**
 * Created by SnakerBone on 2/01/2023
 **/
public class EerieCretinRenderer extends MobRenderer<EerieCretin, EerieCretinModel>
{
    public EerieCretinRenderer(EntityRendererProvider.Context context)
    {
        super(context, new EerieCretinModel(context.bakeLayer(EerieCretinModel.LAYER_LOCATION)), 0.5F);
        addLayer(new EerieCretinLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull EerieCretin cretin)
    {
        return ResourcePath.SOLID_TEXTURE;
    }
}