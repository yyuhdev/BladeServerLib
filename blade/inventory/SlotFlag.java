package club.revived.blade.inventory;

import java.util.function.Predicate;

public enum SlotFlag {
    MAIN(Slot::isMain),
    HOT_BAR(Slot::isHotbar),
    ARMOR(Slot::isArmor),
    OFF_HAND(Slot::isOffHand);

    private final Predicate<Slot> matcher;

    SlotFlag(Predicate<Slot> matcher) {
        this.matcher = matcher;
    }

    public boolean matchesSlot(Slot slot) {
        return matcher.test(slot);
    }
}
