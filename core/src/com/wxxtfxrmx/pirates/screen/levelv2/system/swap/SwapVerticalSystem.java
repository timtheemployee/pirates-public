package com.wxxtfxrmx.pirates.screen.levelv2.system.swap;

import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.screen.levelv2.component.BoundsComponent;

public class SwapVerticalSystem extends SwapSystem {

    @Override
    boolean isInAcceptableBounds(BoundsComponent current, BoundsComponent acceptable) {
        return current.bounds.x == acceptable.bounds.x &&
                (isOnBottom(current, acceptable) || isOnTop(current, acceptable));
    }

    private boolean isOnBottom(BoundsComponent current, BoundsComponent acceptable) {
        return current.bounds.y - Constants.UNIT == acceptable.bounds.y;
    }

    private boolean isOnTop(BoundsComponent current, BoundsComponent acceptable) {
        return current.bounds.y + Constants.UNIT == acceptable.bounds.y;
    }
}
