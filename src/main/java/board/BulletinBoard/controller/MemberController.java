package board.BulletinBoard.controller;

import board.BulletinBoard.MemberDto;
import board.BulletinBoard.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class MemberController {
    private MemberService memberService;

    //회원가입 페이지
    @GetMapping("user/signup")
    public String displaySignup(){
        return "user/signup";
    }

    //회원가입 처리
    @PostMapping("user/signup")
    public String signup(MemberDto memberDto){
        memberService.joinMember(memberDto);
        return "redirect:/user/login";
    }

    //로그인 페이지
    @GetMapping("user/login")
    public String displayLogin(){
        return "user/login";
    }

    //로그인 결과 페이지
    @GetMapping("user/login/result")
    public String displayLoginResult(Principal principal, Model model){
        String email = principal.getName();
        MemberDto member = memberService.findByEmail(email);
        model.addAttribute("nickname", member.getNickname());
        return "user/loginSuccess";
    }

    //로그아웃 결과 페이지
    @GetMapping("user/logout/result")
    public String displayLogout(){
        return "user/logout";
    }

    //내 정보 페이지
    @GetMapping("user/info")
    public String displayMyInfo(Principal principal, Model model){
        String email = principal.getName();
        MemberDto member = memberService.findByEmail(email);
        model.addAttribute("memberDto", member);
        return "user/myInfo";
    }

    @GetMapping("user/admin")
    public String displayAdmin(){
        return "user/admin";
    }

    //접근 거부 페이지
    @GetMapping("user/denied")
    public String displayDenied(){
        return "user/denied";
    }


}
