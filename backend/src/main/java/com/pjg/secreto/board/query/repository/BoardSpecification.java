package com.pjg.secreto.board.query.repository;

import com.pjg.secreto.board.common.entity.Board;
import com.pjg.secreto.board.common.entity.BoardCategory;
import org.springframework.data.jpa.domain.Specification;

public class BoardSpecification {

    public static Specification<Board> boardRoomUserAndCategoryAndTitle(Long roomUserNo, String boardCategory, String title) {
        return (root, query, criteriaBuilder) -> {
            root.fetch("roomUser");
            root.fetch("likeds");
            root.fetch("replies");
            root.fetch("boardEntryLogs");

            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("boardCategory"), BoardCategory.valueOf(boardCategory)),
                    criteriaBuilder.like(root.get("title"), "%" + title + "%"),
                    criteriaBuilder.equal(root.get("roomUser").get("id"), roomUserNo)
            );
        };
    }

    public static Specification<Board> boardRoomUserAndCategoryAndContent(Long roomUserNo, String boardCategory, String content) {
        return (root, query, criteriaBuilder) -> {
            root.fetch("roomUser");
            root.fetch("likeds");
            root.fetch("replies");
            root.fetch("boardEntryLogs");

            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("boardCategory"), BoardCategory.valueOf(boardCategory)),
                    criteriaBuilder.like(root.get("content"), "%" + content + "%"),
                    criteriaBuilder.equal(root.get("roomUser").get("id"), roomUserNo)
            );
        };
    }

    public static Specification<Board> boardRoomUserAndCategoryAndWriter(Long roomUserNo, String boardCategory, String writer) {
        return (root, query, criteriaBuilder) -> {
            root.fetch("roomUser");
            root.fetch("likeds");
            root.fetch("replies");
            root.fetch("boardEntryLogs");

            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("boardCategory"), BoardCategory.valueOf(boardCategory)),
                    criteriaBuilder.like(root.get("writer"), "%" + writer + "%"),
                    criteriaBuilder.equal(root.get("roomUser").get("id"), roomUserNo)
            );
        };
    }
}
