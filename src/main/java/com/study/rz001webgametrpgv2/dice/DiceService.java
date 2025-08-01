package com.study.rz001webgametrpgv2.dice;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * D&D 스타일 주사위 굴리기 서비스
 */
@Service
public class DiceService {

    private final Random random = new Random();
    
    // 주사위 표현식 파싱을 위한 정규식 (예: "2d20+5", "1d8-2")
    private static final Pattern DICE_PATTERN = Pattern.compile("(\\d+)d(\\d+)([+-]\\d+)?");

    /**
     * 주사위 표현식을 파싱하여 굴리기
     * @param expression 주사위 표현식 (예: "2d20+5", "1d8", "3d6+2")
     * @param playerName 플레이어 이름
     * @param advantageType 어드밴티지 타입
     * @return 주사위 굴리기 결과
     */
    public DiceRoll rollDice(String expression, String playerName, DiceRoll.AdvantageType advantageType) {
        Matcher matcher = DICE_PATTERN.matcher(expression);
        
        if (!matcher.matches()) {
            throw new IllegalArgumentException("잘못된 주사위 표현식: " + expression);
        }
        
        int count = Integer.parseInt(matcher.group(1));
        int sides = Integer.parseInt(matcher.group(2));
        int modifier = matcher.group(3) != null ? Integer.parseInt(matcher.group(3)) : 0;
        
        List<Integer> rolls = new ArrayList<>();
        
        // 어드밴티지/디스어드밴티지 처리 (d20에만 적용)
        if (advantageType != DiceRoll.AdvantageType.NORMAL && sides == 20 && count == 1) {
            int roll1 = rollSingle(sides);
            int roll2 = rollSingle(sides);
            rolls.add(roll1);
            rolls.add(roll2);
            
            // 어드밴티지면 높은 값, 디스어드밴티지면 낮은 값 선택
            int selectedRoll = advantageType == DiceRoll.AdvantageType.ADVANTAGE ? 
                Math.max(roll1, roll2) : Math.min(roll1, roll2);
            rolls = List.of(selectedRoll);
        } else {
            // 일반 굴리기
            for (int i = 0; i < count; i++) {
                rolls.add(rollSingle(sides));
            }
        }
        
        int total = rolls.stream().mapToInt(Integer::intValue).sum() + modifier;
        
        // 크리티컬 판정 (d20에서만)
        boolean criticalHit = sides == 20 && rolls.contains(20);
        boolean criticalMiss = sides == 20 && rolls.contains(1);
        
        return DiceRoll.builder()
                .expression(expression)
                .rolls(rolls)
                .modifier(modifier)
                .total(total)
                .advantageType(advantageType)
                .criticalHit(criticalHit)
                .criticalMiss(criticalMiss)
                .rolledAt(LocalDateTime.now())
                .playerName(playerName)
                .build();
    }
    
    /**
     * 간단한 주사위 굴리기
     */
    public DiceRoll rollDice(String expression, String playerName) {
        return rollDice(expression, playerName, DiceRoll.AdvantageType.NORMAL);
    }
    
    /**
     * d20 어드밴티지 굴리기
     */
    public DiceRoll rollWithAdvantage(String playerName) {
        return rollDice("1d20", playerName, DiceRoll.AdvantageType.ADVANTAGE);
    }
    
    /**
     * d20 디스어드밴티지 굴리기
     */
    public DiceRoll rollWithDisadvantage(String playerName) {
        return rollDice("1d20", playerName, DiceRoll.AdvantageType.DISADVANTAGE);
    }
    
    /**
     * 능력치 굴리기 (4d6에서 가장 낮은 값 제거)
     */
    public DiceRoll rollAbilityScore(String playerName) {
        List<Integer> rolls = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            rolls.add(rollSingle(6));
        }
        
        Collections.sort(rolls);
        rolls.remove(0); // 가장 낮은 값 제거
        
        int total = rolls.stream().mapToInt(Integer::intValue).sum();
        
        return DiceRoll.builder()
                .expression("4d6 (drop lowest)")
                .rolls(rolls)
                .modifier(0)
                .total(total)
                .advantageType(DiceRoll.AdvantageType.NORMAL)
                .criticalHit(false)
                .criticalMiss(false)
                .rolledAt(LocalDateTime.now())
                .playerName(playerName)
                .build();
    }
    
    /**
     * 단일 주사위 굴리기
     */
    private int rollSingle(int sides) {
        return random.nextInt(sides) + 1;
    }
}