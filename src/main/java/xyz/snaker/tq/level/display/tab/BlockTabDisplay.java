package xyz.snaker.tq.level.display.tab;

import java.util.function.Consumer;

import xyz.snaker.snakerlib.utility.item.ItemProperties;
import xyz.snaker.tq.client.renderer.icon.BlockTabDisplayRenderer;
import xyz.snaker.tq.utility.NoTexture;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 12/06/2023
 **/
@NoTexture
public class BlockTabDisplay extends Item
{
    public BlockTabDisplay(Properties properties)
    {
        super(properties);
    }

    public BlockTabDisplay()
    {
        super(ItemProperties.EMPTY);
    }

    @Override
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer)
    {
        consumer.accept(new IClientItemExtensions()
        {
            public BlockTabDisplayRenderer getRenderer()
            {
                return new BlockTabDisplayRenderer();
            }

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer()
            {
                return getRenderer();
            }
        });
    }
}
