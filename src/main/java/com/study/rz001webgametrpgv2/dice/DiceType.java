package com.study.rz001webgametrpgv2.dice;

import lombok.Getter;

/**
 * D&D에서 사용하는 주사위 타입들
 */
@Getter
public enum DiceType {
    D4(4, "d4"),
    D6(6, "d6"),
    D8(8, "d8"),
    D10(10, "d10"),
    D12(12, "d12"),
    D20(20, "d20"),
    D100(100, "d100");

    private final int sides;
    private final String displayName;

    DiceType(int sides, String displayName) {
        this.sides = sides;
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}