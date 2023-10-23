package board.BulletinBoard.repository;

import board.BulletinBoard.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    //Containing == like search ( %{keyword}% )
    List<Board> findByTitleContaining(String keyword);
    List<Board> findByContentContaining(String keyword);
    List<Board> findByWriter(String writer);
}
