package board.BulletinBoard.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDto {

    private String roomId;
    private String roomName;
    private String createdMember;

    private Set<WebSocketSession> session = new HashSet<>();

    public static ChatRoomDto create(String roomName, String createdBy){
        ChatRoomDto room = new ChatRoomDto();
        room.roomId = UUID.randomUUID().toString();
        room.roomName = roomName;
        room.createdMember = createdBy;
        return room;
    }
}
