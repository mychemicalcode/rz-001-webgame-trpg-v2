package com.study.rz001webgametrpgv2.dice;

import lombok.Data;

/**
 * 주사위 굴리기 요청 DTO
 */
@Data
public class DiceRollRequest {
    
    /**
     * 주사위 표현식 (예: "1d20+5", "3d6", "2d8-1")
     */
    private String expression;
    
    /**
     * 플레이어 이름
     */
    private String playerName;
    
    /**
     * 어드밴티지 타입 (기본값: NORMAL)
     */
    private DiceRoll.AdvantageType advantageType = DiceRoll.AdvantageType.NORMAL;
}