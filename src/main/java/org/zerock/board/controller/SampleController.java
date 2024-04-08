package org.zerock.board.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // JSON을 기본으로 처리
public class SampleController {

    @GetMapping("/hello")
    public String[] hello(){
        return new String[]{"Hello", "World"};
    }
}
