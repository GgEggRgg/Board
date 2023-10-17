package board.BulletinBoard.controller;

import board.BulletinBoard.BoardDto;
import board.BulletinBoard.MemberDto;
import board.BulletinBoard.service.BoardService;
import board.BulletinBoard.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class BoardController {
    private BoardService boardService;
    private MemberService memberService;

    //게시글 등록페이지로 이동
    @GetMapping("post")
    public String write(Principal principal, Model model){
        String email = principal.getName();
        MemberDto member = memberService.findByEmail(email);
        model.addAttribute("nickname", member.getNickname());
        return "board/write";
    }

    //게시글 조회
    @GetMapping("list")
    public String list(Model model,
                       @RequestParam(value="page", defaultValue = "1") Integer pageNum){
        List<BoardDto> boardList = boardService.getBoardlist(pageNum);
        Integer[] pageList = boardService.getPageList(pageNum);

        model.addAttribute("boardList", boardList);
        model.addAttribute("pageList", pageList);

        return "board/list";
    }

    //게시글 등록
    @PostMapping("post")
    public String write(Principal principal, BoardDto boardDTO){
        String email = principal.getName();
        MemberDto member = memberService.findByEmail(email);
        boardDTO.setWriter(member.getNickname());
        boardService.savePost(boardDTO);
        return "redirect:/";
    }

    //게시글 상세보기
    @GetMapping("post/{id}")
    public String detail(@PathVariable("id") Long id, Model model){
        BoardDto boardDTO = boardService.getPost(id);
        model.addAttribute("boardDto", boardDTO);
        return "board/detail";
    }

    //게시글 수정페이지
    @GetMapping("post/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model){
        BoardDto boardDTO = boardService.getPost(id);
        model.addAttribute("boardDto", boardDTO);
        return "board/update";
    }

    //게시글 수정하기
    @PutMapping("post/edit/{id}")
    public String update(BoardDto boardDTO){
        boardService.savePost(boardDTO);
        return "redirect:/";
    }

    //게시글 삭제하기
    @DeleteMapping("post/{id}")
    public String delete(@PathVariable("id") Long id){
        boardService.deletePost(id);
        return "redirect:/";
    }

    //게시글 검색하기
    @GetMapping("board/search")
    public String search(@RequestParam(value="keyword") String keyword, Model model){
        List<BoardDto> boardDtoList = boardService.searchPosts(keyword);
        model.addAttribute("boardList", boardDtoList);
        return "board/list";
    }
}
