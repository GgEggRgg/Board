package board.BulletinBoard.service;

import board.BulletinBoard.BoardDto;
import board.BulletinBoard.domain.Board;
import board.BulletinBoard.repository.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class BoardService {
    private BoardRepository boardRepository;

    //게시글 저장
    public Long savePost(BoardDto boardDTO){
        return boardRepository.save(boardDTO.toEntity()).getId();
    }

    //게시글 전체 조회
    public List<BoardDto> getBoardlist(){
        List<Board> boards = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();

        for (Board board : boards) {
            BoardDto boardDTO = BoardDto.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .writer(board.getWriter())
                    .createdDate(board.getCreatedDate())
                    .build();
            boardDtoList.add(boardDTO);
        }
        return boardDtoList;
    }

    //개별 게시글 조회
    public BoardDto getPost(Long id){
        Optional<Board> boardWrapper = boardRepository.findById(id);
        Board board = boardWrapper.get();

        BoardDto boardDTO = BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .createdDate(board.getCreatedDate())
                .build();
        return boardDTO;
    }

    //게시글 삭제
    public void deletePost(Long id){
        boardRepository.deleteById(id);
    }
    
}
