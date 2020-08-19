package by.jackraidenph.dragonsurvival.blocks.nest;

import net.minecraft.block.ContainerBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
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
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new TileFillerNest();
    }

    public static class TileFillerNest extends TileEntity {
        public TileFillerNest() {
            super(filler_nest_tile);
        }
    }
}
