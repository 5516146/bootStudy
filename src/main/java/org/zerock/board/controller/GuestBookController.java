package org.zerock.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.board.dto.GuestBookDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.serviece.GuestBookService;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor
public class GuestBookController {

    private final GuestBookService service;

    @GetMapping("/")
    public String index(){
        return "redirect:/guestbook/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        log.info("list ... " + pageRequestDTO);

        model.addAttribute("result", service.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register(){
        log.info("register get ... ");
    }

    @PostMapping("/register")
    public String registerPost(GuestBookDTO dto, RedirectAttributes redirectAttributes){
        log.info("dto ... " + dto);

        // 새로 추가된 엔티티 번호
        Long gno = service.register(dto);

        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(Long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
        log.info("gno : " + gno);

        GuestBookDTO dto = service.read(gno);

        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String modify(GuestBookDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){
        log.info("post modify .............................");
        log.info("dto : " + dto);

        service.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
        redirectAttributes.addAttribute("gno", dto.getGno());

        return "redirect:/guestbook/read";
    }

    @PostMapping("/remove")
    public String remove(Long gno, RedirectAttributes redirectAttributes){
        log.info("gno : " + gno);

        service.remove(gno);

        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }
}
