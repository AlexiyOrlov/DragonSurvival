package by.jackraidenph.dragonsurvival.blocks.nest;

import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

import static by.jackraidenph.dragonsurvival.handlers.BlockInit.filler_nest_tile;

public class FillerNestBlock extends ContainerBlock {
    public FillerNestBlock() {
        super(Properties.create(Material.ROCK)
                .harvestLevel(2)
                .hardnessAndResistance(10000)
                .lightValue(1)
                .sound(SoundType.WET_GRASS));
        setRegistryName("filler_nest");
        RenderTypeLookup.setRenderLayer(this, RenderType.getTranslucent());
    }

    @Override
    public VoxelShape getRenderShape(BlockState p_196247_1_, IBlockReader p_196247_2_, BlockPos p_196247_3_) {
        return VoxelShapes.empty();
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new TileFillerNest();
    }

    public static class TileFillerNest extends TileEntity implements INestDamageble {
        public TileFillerNest() {
            super(filler_nest_tile);
        }

        public BlockPos masterBlock;

        @Override
        public void damage(double amount) {
            ((MasterNestBlock.TileMasterNest) world.getTileEntity(masterBlock)).damage(amount);
        }
    }
}
