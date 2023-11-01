package board.BulletinBoard.repository;

import board.BulletinBoard.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllChatByChatRoomId(Long id);
}
