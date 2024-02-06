package com.pjg.secreto.room.query.repository;

import com.pjg.secreto.room.common.entity.Room;
import com.pjg.secreto.room.common.entity.RoomUser;
import com.pjg.secreto.user.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoomUserQueryRepository extends JpaRepository<RoomUser, Long>, RoomUserQueryRepositoryCustom{


    RoomUser findRoomUserByRoomAndUser(Room room, User user);

    List<RoomUser> findRoomUserByRoom(Room findRoom);

    @Query("select ru from RoomUser ru join fetch ru.user where ru.room.id = :roomNo")
    List<RoomUser> findAllByRoomId(Long roomNo);

//    @Query("select ru from RoomUser ru join fetch ru.room where ru.user.id = :userNo")
//    List<RoomUser> findAllWithRoomByUserNo(Long userNo);

    @Query("select ru from RoomUser ru where ru.user.id = :userNo and ru.room.id = :roomNo")
    Optional<RoomUser> findByUserNoAndRoomNo(Long userNo, Long roomNo);

    @Query("select ru from RoomUser ru where ru.id in :roomUserNos")
    List<RoomUser> findByRoomUserNos(List<Long> roomUserNos);

    @Query("select ru from RoomUser ru where ru.id in :roomUserNos and ru.room.id = :roomNo")
    List<RoomUser> findAllByRoomUserNosAndRoomNo(List<Long> roomUserNos, Long roomNo);

//    @Query("select ru from RoomUser ru join fetch ru.user u where ru.user.id = :userNo and ru.room.id = :roomNo")
//    Optional<RoomUser> findWithUserByUserNoAndRoomNo(Long userNo, Long roomNo);

    @Query("select ru from RoomUser ru join fetch ru.user u join fetch ru.room r where ru.user.id = :userNo and ru.room.id = :roomNo")
    Optional<RoomUser> findWithUserAndRoomByUserNoAndRoomNo(Long userNo, Long roomNo);

    @Query("select ru from RoomUser ru join fetch ru.user u join fetch ru.room r where ru.user.id = :userNo")
    List<RoomUser> findAllWithUserAndRoomByUserNo(Long userNo);

    @Query("select count(*) from RoomUser ru where ru.standbyYn = false and ru.room.id = :roomNo")
    int findParticipantCntByRoomNo(Long roomNo);

    @Query("select ru from RoomUser ru where ru.user.id = :userNo and ru.room.id = :roomNo")
    List<RoomUser> findAllByUserNoAndRoomNo(Long userNo, Long roomNo);

    @Query("select ru from RoomUser ru join fetch ru.user u join fetch ru.room r where ru.user.id = :userNo and ru.userLeaveAt is null")
    List<RoomUser> findAllWithUserAndRoomByUserNoWhereUserNotLeave(Long userNo);

    @Query("select ru from RoomUser ru where ru.room.id = :roomNo")
    List<RoomUser> findAllByRoomNo(Long roomNo);
}
