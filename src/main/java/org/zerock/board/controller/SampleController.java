package org.zerock.board.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.board.dto.SampleDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//@RestController // JSON을 기본으로 처리
@Controller
@RequestMapping("/sample") // http:/localhost/sample
@Log4j2
public class SampleController {

    @GetMapping("/hello")
    public String[] hello(){
        return new String[]{"Hello", "World"};
    }

    @GetMapping("/ex1")
    public void ex1(){
        log.info("ex1 메소드 실행");
    }

    @GetMapping({"/ex2", "/exlink"})
    public void exModel(Model model){
        // Spring은 Model 타입으로 모든 객체나 데이터를 가지고 있음

        List<SampleDTO> list = IntStream.rangeClosed(1, 20).asLongStream().mapToObj(i -> {
            // 빌더 패턴을 이용해 값을 리스트로 만듬
            SampleDTO dto = SampleDTO.builder()
                    .sno(i).first("첫 번째 필드 .. " + i).last("마지막 필드 .. " + i).regTime(LocalDateTime.now())
                    .build();
            return dto;
        }).collect(Collectors.toList());

//        model.addAllAttributes() : 모델에 여러 개의 객체를 추가할 때 사용
        model.addAttribute("list", list); // 모델에 한 개의 객체를 추가할 때 사용
//        프론트에서 list를 호출하면 list 객체가 나옴
    }

    @GetMapping("/exinline")
    public String exInline(RedirectAttributes redirectAttributes){

        log.info("exinline .........");

        SampleDTO dto = SampleDTO.builder()
                .sno(100l).first("First..100").last("Last..100").regTime(LocalDateTime.now()).build();

        redirectAttributes.addFlashAttribute("result", "success");
        redirectAttributes.addFlashAttribute("dto", dto);

        return "redirect:/sample/ex3";
    }

    @GetMapping("/ex3")
    public void ex3(){
        log.info("ex3");
    }

    @GetMapping({"/exlayout1", "/exlayout2", "/exTemplate", "/exSidebar"})
    public void exlayout1(){
        log.info("exlayout...............");
    }
}
