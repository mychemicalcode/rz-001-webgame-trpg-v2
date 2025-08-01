package com.study.rz001webgametrpgv2.dice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 주사위 굴리기 REST API 컨트롤러
 */
@RestController
@RequestMapping("/api/dice")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // 개발용
public class DiceController {

    private final DiceService diceService;

    /**
     * 주사위 굴리기
     * @param request 주사위 굴리기 요청
     * @return 주사위 굴리기 결과
     */
    @PostMapping("/roll")
    public ResponseEntity<DiceRoll> rollDice(@RequestBody DiceRollRequest request) {
        try {
            DiceRoll result = diceService.rollDice(
                request.getExpression(), 
                request.getPlayerName(), 
                request.getAdvantageType()
            );
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 어드밴티지로 d20 굴리기
     */
    @PostMapping("/roll/advantage")
    public ResponseEntity<DiceRoll> rollWithAdvantage(@RequestParam String playerName) {
        DiceRoll result = diceService.rollWithAdvantage(playerName);
        return ResponseEntity.ok(result);
    }

    /**
     * 디스어드밴티지로 d20 굴리기
     */
    @PostMapping("/roll/disadvantage")
    public ResponseEntity<DiceRoll> rollWithDisadvantage(@RequestParam String playerName) {
        DiceRoll result = diceService.rollWithDisadvantage(playerName);
        return ResponseEntity.ok(result);
    }

    /**
     * 능력치 굴리기 (4d6, drop lowest)
     */
    @PostMapping("/roll/ability")
    public ResponseEntity<DiceRoll> rollAbilityScore(@RequestParam String playerName) {
        DiceRoll result = diceService.rollAbilityScore(playerName);
        return ResponseEntity.ok(result);
    }

    /**
     * 빠른 주사위 굴리기 (GET 요청)
     */
    @GetMapping("/roll")
    public ResponseEntity<DiceRoll> quickRoll(
            @RequestParam String expression,
            @RequestParam(defaultValue = "Anonymous") String playerName) {
        try {
            DiceRoll result = diceService.rollDice(expression, playerName);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}