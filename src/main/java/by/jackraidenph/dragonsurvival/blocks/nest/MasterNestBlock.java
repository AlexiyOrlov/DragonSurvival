package by.jackraidenph.dragonsurvival.blocks.nest;

import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static by.jackraidenph.dragonsurvival.handlers.BlockInit.master_nest_tile;

public class MasterNestBlock extends ContainerBlock {
    public MasterNestBlock() {
        super(Properties.create(Material.ROCK)
                .harvestLevel(2)
                .hardnessAndResistance(10000)
                .lightValue(1)
                .sound(SoundType.WET_GRASS));
        setRegistryName("master_nest");
    }

    @Override
    public void onBlockPlacedBy(World p_180633_1_, BlockPos p_180633_2_, BlockState p_180633_3_, @Nullable LivingEntity p_180633_4_, ItemStack p_180633_5_) {
        System.out.println("test");
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new TileMasterNest();
    }

    public static class TileMasterNest extends TileEntity {
        public TileMasterNest() {
            super(master_nest_tile);
        }
    }
}
