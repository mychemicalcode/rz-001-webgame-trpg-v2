package com.study.rz001webgametrpgv2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 메인 게임 페이지 컨트롤러
 */
@Controller
public class GameController {

    /**
     * 메인 게임 페이지
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 주사위 테스트 페이지
     */
    @GetMapping("/dice-test")
    public String diceTest() {
        return "dice-test";
    }
}