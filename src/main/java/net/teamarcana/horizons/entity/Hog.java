package net.teamarcana.horizons.entity;

import com.google.common.annotations.VisibleForTesting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.teamarcana.horizons.init.HorizonEntities;
import net.teamarcana.horizons.init.HorizonTags;

import javax.annotation.Nullable;

public class Hog extends Animal{
    public static final EntityDataAccessor<Long> LAST_POSE_CHANGE_TICK = SynchedEntityData.defineId(Hog.class, EntityDataSerializers.LONG);
    public final AnimationState sniffingAnimationState = new AnimationState();
    public final AnimationState sitAnimationState = new AnimationState();
    public final AnimationState sitPoseAnimationState = new AnimationState();
    public final AnimationState sitUpAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public Hog(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.MOVEMENT_SPEED, 0.15f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.4F)
                .add(Attributes.FOLLOW_RANGE, 6)
                .add(Attributes.STEP_HEIGHT, 1);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putLong("LastPoseTick", this.entityData.get(LAST_POSE_CHANGE_TICK));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        long i = pCompound.getLong("LastPoseTick");
        if (i < 0L) {
            this.setPose(Pose.SITTING);
        }

        this.resetLastPoseChangeTick(i);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(LAST_POSE_CHANGE_TICK, 0L);
    }

    // the AI
    // fun fact, if you shake the hog around, you could faintly hear a small bouncy ball bouncing around
    // inside the hog's brain space
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.0));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.7));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2, item -> item.is(HorizonTags.Items.HOG_FOOD), false));
    }

    public boolean isTempted() {
        return this.brain.getMemory(MemoryModuleType.IS_TEMPTED).orElse(false);
    }
    public boolean canSniff() {
        return !this.isTempted() && !this.isPanicking() && !this.isInWater() && !this.isInLove() && this.onGround() && !this.isPassenger() && !this.isLeashed();
    }

    public boolean refuseToMove() {
        return this.isHogSitting() || this.isInPoseTransition();
    }
    public boolean isHogSitting() {
        return this.entityData.get(LAST_POSE_CHANGE_TICK) < 0L;
    }
    public boolean isInPoseTransition() {
        long i = this.getPoseTime();
        return i < (long)(this.isHogSitting() ? 40 : 52);
    }

    private boolean isVisuallySittingDown() {
        return this.isHogSitting() && this.getPoseTime() < 40L && this.getPoseTime() >= 0L;
    }

    public void sitDown() {
        if (!this.isHogSitting()) {
            this.makeSound(SoundEvents.CAMEL_SIT);
            this.setPose(Pose.SITTING);
            this.gameEvent(GameEvent.ENTITY_ACTION);
            this.resetLastPoseChangeTick(-this.level().getGameTime());
        }
    }

    public void standUp() {
        if (this.isHogSitting()) {
            this.makeSound(SoundEvents.CAMEL_STAND);
            this.setPose(Pose.STANDING);
            this.gameEvent(GameEvent.ENTITY_ACTION);
            this.resetLastPoseChangeTick(this.level().getGameTime());
        }
    }

    public void standUpInstantly() {
        this.setPose(Pose.STANDING);
        this.gameEvent(GameEvent.ENTITY_ACTION);
        this.resetLastPoseChangeTickToFullStand(this.level().getGameTime());
    }
    @VisibleForTesting
    public void resetLastPoseChangeTick(long pLastPoseChangeTick) {
        this.entityData.set(LAST_POSE_CHANGE_TICK, pLastPoseChangeTick);
    }

    private void resetLastPoseChangeTickToFullStand(long pLastPoseChangedTick) {
        this.resetLastPoseChangeTick(Math.max(0L, pLastPoseChangedTick - 52L - 1L));
    }

    public long getPoseTime() {
        return this.level().getGameTime() - Math.abs(this.entityData.get(LAST_POSE_CHANGE_TICK));
    }
    public boolean canHogChangePose() {
        return this.wouldNotSuffocateAtTargetPose(this.isHogSitting() ? Pose.STANDING : Pose.SITTING);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.HOGLIN_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.HOGLIN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.HOGLIN_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pPos, BlockState pBlock) {
        this.playSound(SoundEvents.HOGLIN_STEP, 0.15F, 1.0F);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            this.idleAnimationTimeout--;
        }

        if (this.isVisuallySittingDown()) {
            this.sitUpAnimationState.stop();
            if (this.isVisuallySittingDown()) {
                this.sitAnimationState.startIfStopped(this.tickCount);
                this.sitPoseAnimationState.stop();
            } else {
                this.sitAnimationState.stop();
                this.sitPoseAnimationState.startIfStopped(this.tickCount);
            }
        } else {
            this.sitAnimationState.stop();
            this.sitPoseAnimationState.stop();
            this.sitUpAnimationState.animateWhen(this.isInPoseTransition() && this.getPoseTime() >= 0L, this.tickCount);
        }
    }

    private void resetAnimations() {
        this.sniffingAnimationState.stop();
    }

    private Hog onScentingStart() {
        this.playSound(SoundEvents.SNIFFER_SCENTING, 1.0F, this.isBaby() ? 1.3F : 1.0F);
        return this;
    }

    class HogMoveControl extends MoveControl {
        public HogMoveControl() {
            super(Hog.this);
        }

        @Override
        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO
                    && !Hog.this.isLeashed()
                    && Hog.this.isHogSitting()
                    && !Hog.this.isInPoseTransition()
                    && Hog.this.canHogChangePose()) {
                Hog.this.standUp();
            }

            super.tick();
        }
    }

    @Nullable
    public Hog getBreedOffspring(ServerLevel level, AgeableMob parent) {
        return HorizonEntities.HOG.get().create(level);
    }

    @Override
    public boolean isFood(ItemStack item) {
        return item.is(HorizonTags.Items.HOG_FOOD);
    }
}
