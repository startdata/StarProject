package com.sparta.StarProject.service;

import com.sparta.StarProject.domain.board.Board;
import com.sparta.StarProject.domain.board.Camping;
import com.sparta.StarProject.domain.board.UserMake;
import com.sparta.StarProject.dto.DetailBoardDto;
import com.sparta.StarProject.repository.BoardRepository;
import com.sparta.StarProject.repository.CampingRepository;
import com.sparta.StarProject.repository.UserMakeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final CampingRepository campingRepository;
    private final UserMakeRepository userMakeRepository;


    public DetailBoardDto getDetailBoard(Long id) {
        Board findBoard = boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException("존재하는 게시글이 없습니다.")
        );

        if (findBoard instanceof Camping){
            findBoard = (Camping)findBoard;
        }
        else if (findBoard instanceof UserMake){
            findBoard = (UserMake)findBoard;
        }

        DetailBoardDto newDetailBoardDto = new DetailBoardDto(findBoard.getId(), findBoard.getUser().getNickname(),
                findBoard.getLocationName(), findBoard.getLocation().getAddress(), findBoard.getImg(),
                findBoard.getContent(), findBoard.getLocation().getXLocation(), findBoard.getLocation().getYLocation());


        return newDetailBoardDto;
    }
}
