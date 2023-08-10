package xyz.snaker.snakerlib.internal.mixin;

import xyz.snaker.snakerlib.internal.LevelSavingEvent;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.MinecraftServer;

/**
 * Created by SnakerBone on 25/06/2023
 **/
@Mixin(MinecraftServer.class)
public class MinecraftServerMixin
{
    @Inject(method = "stopServer", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/eventbus/api/IEventBus;post(Lnet/minecraftforge/eventbus/api/Event;)Z"))
    public void stopServer(CallbackInfo ci)
    {
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new LevelSavingEvent());
    }
}
