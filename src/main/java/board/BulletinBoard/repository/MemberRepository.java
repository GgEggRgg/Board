package board.BulletinBoard.repository;

import board.BulletinBoard.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
   Optional<Member> findByEmail(String memberEmail);
   Optional<Member> findNicknameByEmail(String memberEmail);
   Optional<Member> findByNickname(String memberNickname);
}
