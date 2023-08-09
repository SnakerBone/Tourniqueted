package xyz.snaker.tq.level.entity.creature;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.snaker.snakerlib.level.entity.SnakerFlyingCreature;
import xyz.snaker.snakerlib.utility.LevelStuff;
import xyz.snaker.tq.rego.Rego;

import java.util.function.Predicate;

/**
 * Created by SnakerBone on 2/01/2023
 **/
public class Frolicker extends SnakerFlyingCreature
{
    private final Predicate<BlockState> blocksToIgnore = state -> state.is(Blocks.WATER) || state.is(Blocks.LAVA) || state.is(Blocks.AIR) || state.is(BlockTags.LEAVES) || state.is(BlockTags.BEE_GROWABLES) || state.is(BlockTags.FLOWERS);
    private int onGroundTicks;

    public Frolicker(EntityType<? extends SnakerFlyingCreature> type, Level level)
    {
        super(type, level);
    }

    public static boolean spawnRules(EntityType<Frolicker> type, ServerLevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random)
    {
        return LevelStuff.isDimension(level, Rego.Keys.COMATOSE) || LevelStuff.isDimension(level, Level.OVERWORLD);
    }

    public boolean canDoFunny()
    {
        return LevelStuff.isDimension(level(), Rego.Keys.COMATOSE) && !isActuallyOnGround();
    }

    public boolean isActuallyOnGround()
    {
        return isOnGround() && onGroundTicks >= 10;
    }

    public boolean isOnGround()
    {
        return !blocksToIgnore.test(getBlockStateOn());
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mate)
    {
        return Rego.ENTITY_FROLICKER.get().create(level);
    }

    @Override
    public boolean isFood(@NotNull ItemStack stack)
    {
        return stack.is(ItemTags.FLOWERS);
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand)
    {
        isFood(player.getItemInHand(hand));
        return super.mobInteract(player, hand);
    }

    public static AttributeSupplier attributes()
    {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 4)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.FLYING_SPEED, 0.25).build();
    }

    @Override
    protected void registerGoals()
    {
        super.registerGoals();
        goalSelector.addGoal(5, new AvoidEntityGoal<>(this, Spider.class, 6, 1, 1.2));
        goalSelector.addGoal(5, new AvoidEntityGoal<>(this, Parrot.class, 6, 1, 1.2));
        goalSelector.addGoal(3, new TemptGoal(this, 1.25, Ingredient.of(ItemTags.FLOWERS), false));
        goalSelector.addGoal(2, new BreedGoal(this, 1));
    }

    @Override
    public void tick()
    {
        if (isOnGround()) {
            onGroundTicks++;
        } else {
            onGroundTicks = 0;
        }
        super.tick();
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}