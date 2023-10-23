package board.BulletinBoard.service;

import board.BulletinBoard.BoardDto;
import board.BulletinBoard.domain.Board;
import board.BulletinBoard.repository.BoardRepository;
import board.BulletinBoard.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    private MemberRepository memberRepository;
    private static final int BLOCK_PAGE_NUM_COUNT = 5; //블럭 페이지 번호 수
    private static final int PAGE_POST_COUNT = 4; //한 페이지 게시글 수

    //게시글 저장
    public Long savePost(BoardDto boardDTO){

        return boardRepository.save(boardDTO.toEntity()).getId();
    }

    //게시글 전체 조회(페이징)
    public List<BoardDto> getBoardlist(Integer pageNum){
        Page<Board> page = boardRepository.findAll(PageRequest.of(pageNum - 1,
                PAGE_POST_COUNT, Sort.by(Sort.Direction.ASC, "createdDate")));

        List<Board> boards = page.getContent();
        List<BoardDto> boardDtoList = new ArrayList<>();

        for (Board board : boards) {
            boardDtoList.add(this.convertEntityToDto(board));
        }

        return boardDtoList;
    }

    public Long getBoardCount(){
        return boardRepository.count();
    }

    public Integer[] getPageList(Integer curPageNum){
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];
        Double postsTotalCount = Double.valueOf(this.getBoardCount()); // 총 게시글 수
        // 미자믹 페이지 번호
        Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));

        // 현재 페이지 기준 블럭의 마지막 페이지 번호
        Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
                ? curPageNum + BLOCK_PAGE_NUM_COUNT
                : totalLastPageNum;

        // 페이지 시작번호 조정
        curPageNum = (curPageNum <= 3) ? 1 : curPageNum -2;

        //페이지 번호 할당
        for(int i = curPageNum, idx = 0; i <= blockLastPageNum; i++, idx++){
            pageList[idx] = i;
        }

        return pageList;
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

    //게시글 검색
    public List<BoardDto> searchPosts(String keyword, String category){
        List<BoardDto> boardDtoList = new ArrayList<>();

        List<Board> boards = switch (category) {
            case "title" -> boardRepository.findByTitleContaining(keyword);
            case "content" -> boardRepository.findByContentContaining(keyword);
            case "writer" -> boardRepository.findByWriter(keyword);
            default -> new ArrayList<>();
        };

        if(boards.isEmpty()) return boardDtoList;

        for (Board board : boards) {
            boardDtoList.add(this.convertEntityToDto(board));
        }

        return boardDtoList;
    }

    private BoardDto convertEntityToDto(Board board){
        return BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .createdDate(board.getCreatedDate())
                .build();
    }
    
}
