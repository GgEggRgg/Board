package board.BulletinBoard.domain.dto;

import board.BulletinBoard.domain.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDetailDto {
    private Long chatId;
    private Long chatRoomId;
    private String roomId;
    private String writer;
    private String message;

    public static ChatMessageDetailDto toChatMessageDetailDto(ChatMessage chatMessage){
        ChatMessageDetailDto detailDto = new ChatMessageDetailDto();
        detailDto.setChatId(chatMessage.getId());
        detailDto.setChatRoomId(chatMessage.getChatRoom().getId());
        detailDto.setRoomId(chatMessage.getChatRoom().getRoomId());
        detailDto.setWriter(chatMessage.getWriter());
        detailDto.setMessage(chatMessage.getMessage());
        return detailDto;
    }
}
