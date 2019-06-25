package pcl.opensecurity.common.inventory.slot;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import pcl.opensecurity.common.items.ItemEnergyUpgrade;
import pcl.opensecurity.common.tileentity.TileEntityEnergyTurret;
import scala.actors.threadpool.Arrays;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class EnergyUpgradeSlot extends BaseSlot<TileEntityEnergyTurret> implements ISlotTooltip {

    public EnergyUpgradeSlot(TileEntityEnergyTurret tileEntity, IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(tileEntity, itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return stack.getItem() instanceof ItemEnergyUpgrade;
    }

    @Override
    public List<String> getTooltip(){
        return new ArrayList<>(Arrays.asList(new String[]{"Accepted Items:", "Energy Upgrade"}));
    }
}
