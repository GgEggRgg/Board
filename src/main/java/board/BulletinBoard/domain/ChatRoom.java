package board.BulletinBoard.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "chatRoom_table")
public class ChatRoom {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatRoom_id")
    private Long id;

    @Column
    private String roomId;

    @Column
    private String roomName;

    @Column
    private String createdBy;

    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessage> chatMessageList = new ArrayList<>();

    public static ChatRoom toChatRoomEntity(String roomId, String roomName, String createdBy){
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setRoomId(roomId);
        chatRoom.setRoomName(roomName);
        chatRoom.setCreatedBy(createdBy);
        return chatRoom;
    }

}
