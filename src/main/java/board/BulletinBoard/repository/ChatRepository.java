package board.BulletinBoard.repository;

import board.BulletinBoard.domain.ChatMessage;
import board.BulletinBoard.domain.dto.ChatMessageDetailDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessageDetailDto> findAllChatByChatRoomId(String roomId);
}
