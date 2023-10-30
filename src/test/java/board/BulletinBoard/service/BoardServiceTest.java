package board.BulletinBoard.service;

import board.BulletinBoard.domain.dto.BoardDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BoardServiceTest {

    @Autowired BoardService boardService;

    //게시글 저장 테스트
    @Test
    public void 게시글저장테스트() throws Exception{
        //given
        BoardDto boardDto = BoardDto.builder()
                .writer("김민수")
                .title("테스트 게시글 제목")
                .content("테스트 게시글 내용")
                .build();
        //when
        Long saveId = boardService.savePost(boardDto);
        BoardDto post = boardService.getPost(saveId);
        //then
        Assertions.assertThat(post.getId()).isEqualTo(saveId);
     }

     @Test
     public void 게시글전체조회테스트() throws Exception{
         //given
         BoardDto boardDto = BoardDto.builder()
                 .writer("김민수")
                 .title("테스트 게시글 제목")
                 .content("테스트 게시글 내용")
                 .build();
         Long saveId = boardService.savePost(boardDto);
         BoardDto boardDto2 = BoardDto.builder()
                 .writer("수민김")
                 .title("테스트 게시글 제목2")
                 .content("테스트 게시글 내용2")
                 .build();
         Long saveId2 = boardService.savePost(boardDto2);

         //when
         List<BoardDto> boardlist = boardService.getBoardlist();

         //then
         Assertions.assertThat(boardlist.size() >= 2);
      }

      @Test
      public void 개별게시글조회테스트() throws Exception{
          //given
          BoardDto boardDto = BoardDto.builder()
                  .writer("김민수")
                  .title("테스트 게시글 제목")
                  .content("테스트 게시글 내용")
                  .build();
          Long saveId = boardService.savePost(boardDto);

          //when
          BoardDto post = boardService.getPost(saveId);

          //then
          Assertions.assertThat(post.getWriter()).isEqualTo("김민수");
          Assertions.assertThat(post.getTitle()).isEqualTo("테스트 게시글 제목");
          Assertions.assertThat(post.getContent()).isEqualTo("테스트 게시글 내용");
       }

       @Test(expected = NoSuchElementException.class)
       public void 게시글삭제테스트() throws Exception{
           //given
           BoardDto boardDto = BoardDto.builder()
                   .writer("김민수")
                   .title("테스트 게시글 제목")
                   .content("테스트 게시글 내용")
                   .build();
           Long saveId = boardService.savePost(boardDto);

           //when
           boardService.deletePost(saveId);

           //then
           boardService.getPost(saveId);
           /*
           Assertions.assertThatExceptionOfType(NoSuchElementException.class)
                   .isThrownBy(() -> {
                       boardService.getPost(saveId);
                   });
            */
       }
}
