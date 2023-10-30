package board.BulletinBoard.service;

import board.BulletinBoard.domain.ChatRoom;
import board.BulletinBoard.domain.dto.ChatMessageDetailDto;
import board.BulletinBoard.domain.dto.ChatRoomDetailDto;
import board.BulletinBoard.domain.dto.ChatRoomDto;
import board.BulletinBoard.repository.ChatRepository;
import board.BulletinBoard.repository.ChatRoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;

    //해당 채팅룸의 메세지들 불러오기(채팅 리포)
    public List<ChatMessageDetailDto> findAllChatByRoomId(String roomId) {
        return chatRepository.findAllChatByChatRoomId(roomId);
    }

    //채팅룸 목록 전체 조회하기(채팅방 리포)
    public List<ChatRoomDto> getChatRoomList(){
        List<ChatRoomDto> chatRoomList = new ArrayList<>();
        List<ChatRoom> rooms = chatRoomRepository.findAll();
        for (ChatRoom room : rooms) {
            ChatRoomDto chatRoomDto = ChatRoomDto.create(
                    room.getRoomName(), room.getCreatedBy());
            chatRoomList.add(chatRoomDto);
        }
        return chatRoomList;
    }

    //채팅방 개설(채팅방 리포)
    public Long saveChatRoom(ChatRoomDto chatRoomDto){
        ChatRoom chatRoom = ChatRoom.toChatRoomEntity(
                chatRoomDto.getRoomName(), chatRoomDto.getRoomId());
        return chatRoomRepository.save(chatRoom).getId();
    }

    public ChatRoomDetailDto findRoomById(Long id){
        Optional<ChatRoom> chatRoomWrapper = chatRoomRepository.findById(id);
        ChatRoom chatRoom = chatRoomWrapper.get();
        ChatRoomDetailDto detail = ChatRoomDetailDto.toChatRoomDetailDto(chatRoom);
        return detail;
    }

}
