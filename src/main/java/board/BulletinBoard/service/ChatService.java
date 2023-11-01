package board.BulletinBoard.service;

import board.BulletinBoard.domain.ChatMessage;
import board.BulletinBoard.domain.ChatRoom;
import board.BulletinBoard.domain.dto.ChatRoomDetailDto;
import board.BulletinBoard.domain.dto.ChatRoomDto;
import board.BulletinBoard.repository.ChatRepository;
import board.BulletinBoard.repository.ChatRoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;

    //해당 채팅룸의 메세지들 불러오기(채팅 리포)
    public List<ChatMessage> findAllChatByRoomId(String roomId) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId);
        return chatRepository.findAllChatByChatRoomId(chatRoom.getId());
    }

    //채팅룸 목록 전체 조회하기(채팅방 리포)
    public List<ChatRoomDetailDto> getChatRoomList(){
        List<ChatRoomDetailDto> chatRoomList = new ArrayList<>();
        List<ChatRoom> rooms = chatRoomRepository.findAll();
        for (ChatRoom room : rooms) {
            ChatRoomDetailDto detail = ChatRoomDetailDto.toChatRoomDetailDto(room);
            chatRoomList.add(detail);
        }
        return chatRoomList;
    }

    //채팅방 개설(채팅방 리포)
    public String saveChatRoom(ChatRoomDto chatRoomDto){
        ChatRoom chatRoom = ChatRoom.toChatRoomEntity(
                chatRoomDto.getRoomId(),
                chatRoomDto.getRoomName(),
                chatRoomDto.getCreatedBy());
        return chatRoomRepository.save(chatRoom).getRoomId();
    }

    public ChatRoomDetailDto findRoomByRoomId(String roomId){
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId);
        ChatRoomDetailDto detail = ChatRoomDetailDto.toChatRoomDetailDto(chatRoom);
        return detail;
    }

}
