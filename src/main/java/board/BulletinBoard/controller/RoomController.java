package board.BulletinBoard.controller;

import board.BulletinBoard.domain.ChatMessage;
import board.BulletinBoard.domain.dto.ChatRoomDetailDto;
import board.BulletinBoard.domain.dto.ChatRoomDto;
import board.BulletinBoard.domain.dto.MemberDto;
import board.BulletinBoard.repository.ChatRoomRepository;
import board.BulletinBoard.service.ChatService;
import board.BulletinBoard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class RoomController {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatService chatService;
    private final MemberService memberService;

    //채팅방 목록 조회
    @GetMapping(value = "chat/list")
    public String list(Model model){
        List<ChatRoomDetailDto> chatRoomList = chatService.getChatRoomList();
        model.addAttribute("chatRoomList", chatRoomList);
        return "chat/list";
    }

    //채팅방 개설 페이지
    @GetMapping(value = "chat/room")
    public String create(Principal principal, Model model){
        String email = principal.getName();
        MemberDto member = memberService.findByEmail(email);
        model.addAttribute("nickname", member.getNickname());
        return "chat/create";
    }

    //채팅방 개설
    @PostMapping(value = "chat/room")
    public String create(Principal principal, @RequestParam String roomName){
        ChatRoomDto chatRoom = ChatRoomDto.create(roomName, principal.getName());
        String roomId = chatService.saveChatRoom(chatRoom);
        return "redirect:/chat/"+roomId;
    }

    //채팅방 조회
    @GetMapping(value = "chat/{roomId}")
    public String detail(Principal principal, @PathVariable("roomId") String roomId,
                         Model model){
        MemberDto member = memberService.findByEmail(principal.getName());
        ChatRoomDetailDto chatRoomDetailDto = chatService.findRoomByRoomId(roomId);
        List<ChatMessage> messages = chatService.findAllChatByRoomId(roomId);
        model.addAttribute("nickname", member.getNickname());
        model.addAttribute("chatRoom", chatRoomDetailDto);
        model.addAttribute("messages", messages);
        return "chat/detail";
    }


}
