package board.BulletinBoard.controller;

import board.BulletinBoard.BoardDto;
import board.BulletinBoard.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class BoardController {
    private BoardService boardService;

    //게시글 등록페이지로 이동
    @GetMapping("post")
    public String write(){
        return "write";
    }

    //게시글 조회
    @GetMapping("list")
    public String list(Model model){
        List<BoardDto> boardList = boardService.getBoardlist();

        model.addAttribute("boardList", boardList);
        return "list";
    }

    //게시글 등록
    @PostMapping("post")
    public String write(BoardDto boardDTO){
        boardService.savePost(boardDTO);
        return "redirect:/";
    }

    //게시글 상세보기
    @GetMapping("post/{id}")
    public String detail(@PathVariable("id") Long id, Model model){
        BoardDto boardDTO = boardService.getPost(id);
        model.addAttribute("boardDto", boardDTO);
        return "detail";
    }

    //게시글 수정페이지
    @GetMapping("post/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model){
        BoardDto boardDTO = boardService.getPost(id);
        model.addAttribute("boardDto", boardDTO);
        return "update";
    }

    //게시글 수정하기
    @PutMapping("/post/edit/{id}")
    public String update(BoardDto boardDTO){
        boardService.savePost(boardDTO);
        return "redirect:/";
    }

    //게시글 삭제하기
    @DeleteMapping("/post/{id}")
    public String delete(@PathVariable("id") Long id){
        boardService.deletePost(id);
        return "redirect:/";
    }
}
