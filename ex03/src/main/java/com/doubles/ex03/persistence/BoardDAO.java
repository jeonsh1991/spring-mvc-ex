package com.doubles.ex03.persistence;

import com.doubles.ex03.domain.BoardVO;

import java.util.List;

// Data Access Object : 데이터에 접근하기 위한 트랜잭션 객체
// persistence 계층 : database에 data를 CRUD하는 계층
public interface BoardDAO {

    // 입력
    public void create(BoardVO boardVO) throws Exception;

    // 조회
    public BoardVO read(Integer bno) throws Exception;

    // 수정
    public void update(BoardVO boardVO) throws Exception;

    // 삭제
    public void delete(Integer bno) throws Exception;

    // 목록 : 기본
    public List<BoardVO> list() throws Exception;

    // 목록 : 페이징

    // 목록 : 페이징 + 검색
}
