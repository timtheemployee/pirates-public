package com.wxxtfxrmx.pirates.screen.levelv2.system.swap;

import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.screen.levelv2.component.BoundsComponent;

public class SwapHorizontalSystem extends SwapSystem {

    @Override
    boolean isInAcceptableBounds(BoundsComponent current, BoundsComponent acceptable) {
        return current.bounds.y == acceptable.bounds.y &&
                (isOnLeft(current, acceptable) || isOnRight(current, acceptable));
    }

    private boolean isOnLeft(BoundsComponent current, BoundsComponent acceptable) {
        return current.bounds.x - Constants.UNIT == acceptable.bounds.x;
    }

    private boolean isOnRight(BoundsComponent current, BoundsComponent acceptable) {
        return current.bounds.x + Constants.UNIT == acceptable.bounds.x;
    }
}
