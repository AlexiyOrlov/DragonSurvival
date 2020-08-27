package by.jackraidenph.dragonsurvival.blocks.nest;

import by.jackraidenph.dragonsurvival.capability.PlayerStateProvider;
import by.jackraidenph.dragonsurvival.handlers.BlockInit;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Consumer;

import static by.jackraidenph.dragonsurvival.handlers.BlockInit.master_nest_tile;

public class MasterNestBlock extends ContainerBlock {
    public final AxisAlignedBB bounds;
    public final double hp;

    public MasterNestBlock(AxisAlignedBB bounds, double hp) {
        super(Properties.create(Material.ROCK)
                .harvestLevel(2)
                .hardnessAndResistance(10000)
                .lightValue(1)
                .sound(SoundType.WET_GRASS));
        this.bounds = bounds;
        this.hp = hp;
        RenderTypeLookup.setRenderLayer(this, RenderType.getTranslucent());
    }

    @Override
    public VoxelShape getRenderShape(BlockState p_196247_1_, IBlockReader p_196247_2_, BlockPos p_196247_3_) {
        return VoxelShapes.empty();
    }

    public BlockRenderType getRenderType(BlockState p_149645_1_) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onBlockPlacedBy(World p_180633_1_, BlockPos p_180633_2_, BlockState p_180633_3_, @Nullable LivingEntity p_180633_4_, ItemStack p_180633_5_) {
        iterateBounds(p_180633_2_, pos -> {
            if (!pos.equals(p_180633_2_)) {
                System.out.println("test1");
                p_180633_1_.setBlockState(pos, BlockInit.filler_nest.getDefaultState());
                ((FillerNestBlock.TileFillerNest) p_180633_1_.getTileEntity(pos)).masterBlock = p_180633_2_;
            }
        });
    }

    private void iterateBounds(BlockPos start, Consumer<BlockPos> f) {
        for (int x = (int) Math.ceil(bounds.minX); x <= (int) Math.floor(bounds.maxX); x++)
            for (int y = (int) Math.ceil(bounds.minY); y <= (int) Math.floor(bounds.maxY); y++)
                for (int z = (int) Math.ceil(bounds.minZ); z <= (int) Math.floor(bounds.maxZ); z++)
                    f.accept(start.add(x, y, z));
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new TileMasterNest(hp);
    }

    public static class TileMasterNest extends TileEntity implements INestDamageble, ITickableTileEntity {
        public TileMasterNest() {
            super(master_nest_tile);
            hp = 100;
        }

        public TileMasterNest(double hp) {
            super(master_nest_tile);
            this.hp = hp;
        }

        public double hp;
        private int attackCooldown = 0;
        public UUID playerId;

        @Override
        public void damage(double amount) {
            if (attackCooldown == 0) {
                attackCooldown = 20 * 60;//one minute
                hp -= amount;
                if (hp <= 0)
                    destroyNest();
            }

        }

        private void destroyNest() {
            if (!world.isRemote) {
                ((MasterNestBlock) world.getBlockState(pos).getBlock()).iterateBounds(pos, p -> world.removeBlock(p, true));
                if (playerId != null)
                    PlayerStateProvider.getCap(world.getPlayerByUuid(playerId)).ifPresent(capa -> capa.setIsDragon(false));
            }
        }

        @Override
        public void tick() {
            if (!world.isRemote)
                if (attackCooldown > 0)
                    attackCooldown--;
        }
    }
}
