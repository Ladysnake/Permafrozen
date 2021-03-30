package permafrozen.util;

import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

import javax.annotation.Nonnull;
import java.util.function.BooleanSupplier;

public class PermafrozenButton extends AbstractButtonBlock {

    protected PermafrozenButton(boolean isWooden, Properties properties) {
        super(isWooden, properties);
    }

    @Nonnull
    @Override
    protected SoundEvent getSoundEvent(boolean powered) {
        return powered ? SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON : SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF;
    }

}
