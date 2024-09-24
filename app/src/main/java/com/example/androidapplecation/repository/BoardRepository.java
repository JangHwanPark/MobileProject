package com.example.androidapplecation.repository;

import com.example.androidapplecation.model.Board;

import java.util.ArrayList;
import java.util.Date;

public class BoardRepository {
    private static BoardRepository instance;
    private ArrayList<Board> boardList;

    // 싱글톤 패턴을 적용한 생성자
    private BoardRepository() {
        boardList = new ArrayList<>();
        initializeBoardData(); // 기본 게시글 10개 추가
    }

    // 싱글톤 인스턴스 반환
    public static BoardRepository getInstance() {
        if (instance == null) {
            instance = new BoardRepository();
        }
        return instance;
    }

    // 기본 게시글 10개를 추가하는 메서드
    private void initializeBoardData() {
        for (int i = 1; i <= 10; i++) {
            Board board = new Board(
                    "Test User" + i, // 작성자 이름
                    "Title " + i, // 게시글 제목
                    "This is the content of article " + i, // 게시글 내용
                    new Date() // 현재 날짜
            );
            boardList.add(board);
        }
    }

    // 게시글 목록을 반환하는 메서드
    public ArrayList<Board> getBoardList() {
        return boardList;
    }

    // 새로운 게시글을 추가하는 메서드
    public void addBoard(Board board) {
        boardList.add(board);
    }
}
