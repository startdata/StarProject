package com.sparta.StarProject.repository;

import com.sparta.StarProject.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}