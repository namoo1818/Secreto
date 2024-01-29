//package com.pjg.secreto;
//
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.pjg.secreto.board.common.entity.Board;
//import com.pjg.secreto.board.common.entity.BoardCategory;
//import com.pjg.secreto.board.common.entity.Liked;
//import com.pjg.secreto.history.common.entity.Matching;
//import com.pjg.secreto.mission.common.entity.UserMission;
//import com.pjg.secreto.room.common.entity.Room;
//import com.pjg.secreto.room.common.entity.RoomUser;
//import jakarta.transaction.Transactional;
//import lombok.Builder;
//import lombok.Data;
//import lombok.Setter;
//import lombok.ToString;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.jpa.repository.EntityGraph;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.test.annotation.Rollback;
//
//import java.lang.reflect.Executable;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.time.temporal.ChronoUnit;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.Random;
//import java.util.concurrent.ThreadLocalRandom;
//import java.util.stream.Collectors;
//import java.util.stream.LongStream;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//interface BoardRepository extends JpaRepository<Board, Long> {
//    List<Board> findByRoomUserIn(List<RoomUser> roomUser);
//
//    @EntityGraph(attributePaths = {"roomUser"})
//    Optional<Board> findById(Long id);
//}
//
//interface UserMissionRepository extends JpaRepository<UserMission, Long> {
//    List<UserMission> findByRoomUser(RoomUser roomUser);
//}
//
//interface MatchingRepository extends JpaRepository<Matching, Long> {
//
//    List<Matching> findByRoomUserIn(List<RoomUser> roomUser);
//}
//
//interface RoomUserRepository extends JpaRepository<RoomUser, Long> {
//    Optional<RoomUser> findByRoom(Room room);
//
//    List<RoomUser> findAllByRoom(Room room);
//
//    Optional<RoomUser> findById(Long id);
//}
//
//interface RoomRepository extends JpaRepository<Room, Long> {
//    @EntityGraph(attributePaths = {"roomUsers"})
//    Optional<Room> findRoomById(Long id);
//}
//
//interface LikedRepository extends JpaRepository<Liked, Long>{
//    List<Liked> findByBoard(Board board);
//    Long countByBoard(Board board);
//}
//
//@Data
//@Builder
//@ToString
//class SearchPostResponseDto {
//    Long id;
//    RoomUser roomUser;
//    String title;
//    String content;
//    String registerAt;
//    Long hit;
//    BoardCategory boardCategory;
//    Boolean publicYn;
//    String missionCategory;
//    Long likedCount;
//    String writer;
//
//    public static SearchPostResponseDto toDto(final Board board){
//        return SearchPostResponseDto.builder()
//                .id(board.getId())
//                .roomUser(board.getRoomUser())
//                .title(board.getTitle())
//                .content(board.getContent())
//                .registerAt(board.getRegisterAt())
//                .hit(board.getHit())
//                .boardCategory(board.getBoardCategory())
//                .publicYn(board.getPublicYn())
//                .missionCategory(board.getMissionCategory())
//                .likedCount(board.getLikedCount())
//                .writer(board.getWriter())
//                .build();
//    }
//}
//
//@SpringBootTest
//@Rollback(value = false)
//@Slf4j
//public class BoardIDataInsertTest {
//    @Autowired
//    RoomRepository roomRepository;
//
//    @Autowired
//    RoomUserRepository roomUserRepository;
//
//    @Autowired
//    MatchingRepository matchingRepository;
//
//    @Autowired
//    UserMissionRepository userMissionRepository;
//
//    @Autowired
//    BoardRepository boardRepository;
//
//    @Autowired
//    LikedRepository likedRepository;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @Test
//    @Transactional
//    void getBoardByID() throws JsonProcessingException {
//        Board board = boardRepository.findById(1L).orElseThrow();
//        System.out.println(board.getId());
//        System.out.println(board.getRoomUser());
//        System.out.println(board.getContent());
//        assertNotNull(board);
//
//        SearchPostResponseDto dto = SearchPostResponseDto.toDto(board);
//        objectMapper.writeValueAsString(dto);
//    }
//    @Test
//    void replyDataInsert(){
//        List<Board> boardList = boardRepository.findAll();
//    }
//
//
//    @Test
//    @Transactional
//    void LikeInsert(){
//        List<Board> boardList = boardRepository.findAll();
//
//        for(Board board: boardList){
//            Room room = board.getRoomUser().getRoom();
//            List<RoomUser> roomUserList = roomUserRepository.findAllByRoom(room);
//
//            Random random = new Random();
//            int randomNum = random.nextInt(0, roomUserList.size() + 1);
//
//            List<RoomUser> randomSubset = getRandomSubset(roomUserList, randomNum);
//
//            List<Liked> likeList = randomSubset.stream()
//                    .map(roomUser -> new Liked(board, roomUser))
//                    .collect(Collectors.toList());
//
//            likedRepository.saveAll(likeList);
//
//        }
//    }
//
//    @Test
//    @Transactional
//    void LikeUpdate(){
//        List<Board> boardList = boardRepository.findAll();
//
//        for(Board board: boardList){
//            Long likeCount = likedRepository.countByBoard(board);
//            board.setLikedCount(likeCount);
//            boardRepository.save(board);
//        }
//    }
//
//    @Test
//    @Transactional
//    void boardUpdateTime(){
//        List<Room> allRoom = roomRepository.findAll();
//
//        for(Room room : allRoom){
//            String startTime = room.getMissionStartAt() +" " + room.getMissionSubmitTime() + ":00";
//            String endTime = room.getRoomEndAt();
//
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//            LocalDateTime startLocalDateTime = LocalDateTime.parse(startTime, formatter);
//            LocalDateTime endLocalDateTime = LocalDateTime.parse(endTime, formatter);
//
//            List<RoomUser> roomUserList = roomUserRepository.findAllByRoom(room);
//
//            List<Board> boardList = boardRepository.findByRoomUserIn(roomUserList);
//
//            for (Board board : boardList){
//                LocalDateTime randomTimeBetweenTwoDates = getRandomTimeBetweenTwoDates(startLocalDateTime, endLocalDateTime);
//                board.setRegisterAt(randomTimeBetweenTwoDates.format(formatter));
//                boardRepository.save(board);
//            }
//        }
//    }
//
//    @Test
//    void boardDataInsert() {
//        List<Long> roomIdList = LongStream
//                .range(1, 11)
//                .boxed()
//                .toList();
//
//        for (Long id : roomIdList) {
//            Room room = roomRepository.findRoomById(id).orElseThrow();
//
//            List<RoomUser> roomUsers = room.getRoomUsers();
//            List<Matching> matchingList = matchingRepository.findByRoomUserIn(roomUsers);
//
//            for (Matching matching : matchingList) {
//                Long manitiNo = matching.getManitiNo();
//                RoomUser manitoRoomUser = roomUserRepository.findById(manitiNo).orElseThrow();
//                List<UserMission> manitoMisssion = userMissionRepository.findByRoomUser(matching.getRoomUser());
//
//
//                String manitoNickName = manitoRoomUser.getNickname();
//
//                String missionCategory = "";
//                String title = "";
//                BoardCategory type = null;
//                int index = 0;
//
//                List<Board> target = new ArrayList<>();
//
//                for (UserMission userMission : manitoMisssion){
//                    missionCategory = userMission.getContent();
//                    title = manitoNickName + "미션 시행 합니다" + index++;
//                    type = BoardCategory.BOAST;
//                    Board board = 게시판(title, type, missionCategory, manitoNickName + "님의 마니또", matching.getRoomUser());
//                    target.add(board);
//                }
//
//
//                index = 0;
//
//                for (UserMission userMission : manitoMisssion){
//                    missionCategory = userMission.getContent();
//                    title = manitoNickName + "미션 인증 합니다" + index++;
//                    type = BoardCategory.CERTIFICATE;
//                    Board board = 게시판(title, type, missionCategory, manitoNickName, manitoRoomUser);
//                    target.add(board);
//                }
//
//                boardRepository.saveAll(target);
//
//            }
//        }
//    }
//
//    private static Board 게시판(String title, BoardCategory type, String missionCategory, String nickname, RoomUser roomUser) {
//        return new Board(
//                title,
//                "<img src= \" https://i.namu.wiki/i/4WJZV7M9TLMgnxMjaSY7_F-ANJ2RZmXkz3dr3Kip8G_w0j_utd-n0Zv2j1pNrcLNYfrdusdC_mIE-O1bkdFy6CxHi6DQS95LZzqZUP_FOTrSEJ9WiOxAX5csJlbbom6bUH7T-GA9cXMJbRNMtbLdRg.webp\"" + ">",
//                null,
//                0L,
//                type,
//                true,
//                missionCategory,
//                0L,
//                nickname,
//                roomUser
//        );
//    }
//
//    private static <T> List<T> getRandomSubset(List<T> originalList, int numberOfItemsToSelect) {
//        if (numberOfItemsToSelect >= originalList.size()) {
//            // 요청한 개수가 리스트 크기보다 크거나 같으면 전체 리스트를 반환
//            return new ArrayList<>(originalList);
//        }
//
//        // 랜덤으로 선택된 인덱스를 저장할 리스트
//        List<Integer> selectedIndices = new ArrayList<>();
//
//        // Random 객체 생성
//        Random random = new Random();
//
//        // 랜덤으로 선택된 유일한 인덱스를 생성
//        while (selectedIndices.size() < numberOfItemsToSelect) {
//            int randomIndex = random.nextInt(originalList.size());
//            if (!selectedIndices.contains(randomIndex)) {
//                selectedIndices.add(randomIndex);
//            }
//        }
//
//        // 랜덤으로 선택된 인덱스에 해당하는 값을 새 리스트에 추가
//        List<T> randomSubset = new ArrayList<>();
//        for (int index : selectedIndices) {
//            randomSubset.add(originalList.get(index));
//        }
//
//        return randomSubset;
//
//    }
//
//    private static LocalDateTime getRandomTimeBetweenTwoDates(LocalDateTime start, LocalDateTime end) {
//        long minutesBetween = ChronoUnit.MINUTES.between(start, end);
//        long randomMinutes = ThreadLocalRandom.current().nextLong(minutesBetween + 1);
//
//        return start.plusMinutes(randomMinutes);
//    }
//}
