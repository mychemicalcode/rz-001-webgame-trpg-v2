package com.study.rz001webgametrpgv2.dice;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 주사위 굴리기 결과
 */
@Getter
@Builder
@ToString
public class DiceRoll {
    
    /**
     * 굴린 주사위 표현식 (예: "2d20+5", "1d8+3")
     */
    private final String expression;
    
    /**
     * 각 주사위의 결과값들
     */
    private final List<Integer> rolls;
    
    /**
     * 수정자 (보너스/페널티)
     */
    private final int modifier;
    
    /**
     * 최종 합계
     */
    private final int total;
    
    /**
     * 어드밴티지/디스어드밴티지 여부
     */
    private final AdvantageType advantageType;
    
    /**
     * 크리티컬 히트 여부 (d20에서 20)
     */
    private final boolean criticalHit;
    
    /**
     * 크리티컬 미스 여부 (d20에서 1)
     */
    private final boolean criticalMiss;
    
    /**
     * 굴린 시간
     */
    private final LocalDateTime rolledAt;
    
    /**
     * 굴린 플레이어
     */
    private final String playerName;
    
    public enum AdvantageType {
        NORMAL("일반"),
        ADVANTAGE("어드밴티지"),
        DISADVANTAGE("디스어드밴티지");
        
        private final String displayName;
        
        AdvantageType(String displayName) {
            this.displayName = displayName;
        }
        
        @Override
        public String toString() {
            return displayName;
        }
    }
}