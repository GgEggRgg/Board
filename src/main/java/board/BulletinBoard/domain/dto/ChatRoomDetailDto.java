package board.BulletinBoard.domain.dto;

import board.BulletinBoard.domain.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDetailDto {
    private Long chatRoomId;
    private String roomId;
    private String name;
    private String createdBy;

    public static ChatRoomDetailDto toChatRoomDetailDto(ChatRoom chatRoom){
        ChatRoomDetailDto detailDto = new ChatRoomDetailDto();
        detailDto.setChatRoomId(chatRoom.getId());
        detailDto.setRoomId(chatRoom.getRoomId());
        detailDto.setName(chatRoom.getRoomName());
        detailDto.setCreatedBy(chatRoom.getCreatedBy());
        return detailDto;
    }
}
