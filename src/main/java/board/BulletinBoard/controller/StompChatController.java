package board.BulletinBoard.controller;

import board.BulletinBoard.domain.ChatMessage;
import board.BulletinBoard.domain.ChatRoom;
import board.BulletinBoard.domain.dto.ChatMessageDetailDto;
import board.BulletinBoard.domain.dto.ChatMessageSaveDto;
import board.BulletinBoard.repository.ChatRepository;
import board.BulletinBoard.repository.ChatRoomRepository;
import board.BulletinBoard.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final SimpMessagingTemplate template; //특정 broker로 메시지 전달
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatService chatService;

    //클라이언트가 SEND할 수 있는 경로
    //config에서 설정한 applicationDestinationPrefixes와 MessageMapping 경로가 병합됨
    // ex) /pub/chat/enter
    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageDetailDto message){
        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");

        List<ChatMessageDetailDto> chatList = chatService.findAllChatByRoomId(message.getRoomId());
        if(chatList != null){
            for(ChatMessageDetailDto c : chatList){
                message.setWriter(c.getWriter());
                message.setMessage(c.getMessage());
            }
        }

        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);

        //DB에 저장
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(message.getRoomId());
        ChatMessageSaveDto chatMessageSaveDto = new ChatMessageSaveDto(
                message.getRoomId(), message.getWriter(), message.getMessage());
        chatRepository.save(ChatMessage.toChatEntity(chatMessageSaveDto, chatRoom));
;    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageDetailDto message){
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);

        //DB에 저장
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(message.getRoomId());
        ChatMessageSaveDto chatMessageSaveDto = new ChatMessageSaveDto(
                message.getRoomId(), message.getWriter(), message.getMessage());
        chatRepository.save(ChatMessage.toChatEntity(chatMessageSaveDto, chatRoom));
    }

}
