package green.mtcoding.bookbox.lend;

import green.mtcoding.bookbox.book.Book;
import green.mtcoding.bookbox.book.BookRepository;
import green.mtcoding.bookbox.core.exception.api.ExceptionApi400;
import green.mtcoding.bookbox.core.exception.api.ExceptionApi404;
import green.mtcoding.bookbox.core.exception.api.ExceptionApi500;
import green.mtcoding.bookbox.reservation.ReservationRepository;
import green.mtcoding.bookbox.reservation.ReservationService;
import green.mtcoding.bookbox.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LendService {

    private final LendRepository lendRepository;
    private final BookRepository bookRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;

    @Transactional
    public LendResponse.LendDTO 대여하기(Long userId, LendRequest.SaveDTO request) {
        // 1. 해당 isbn의 도서가 있는지 체크 ( 유저는 token에서 꺼낸거니까 체크안함 )
        Book bookPS = bookRepository.findById(request.getIsbn13()).orElseThrow(() -> new ExceptionApi404("요청하신 도서가 존재하지 않습니다."));
        // 2. 누군가 lend 했는지 체크
        Boolean b = bookRepository.mCheckLendStatus(request.getIsbn13()).orElseThrow(() -> new ExceptionApi404("요청하신 도서가 존재하지 않습니다."));
        // 2-1. lend = true 상태이면 throw -> 누군가 lend해서 대여불가
        if (b.booleanValue()) {
            throw new ExceptionApi400("이미 대여된 도서입니다.");

        }
        // 3. 대여해주기 -> book 대여상태 true, 대여수+1 하고 lend 테이블에도 추가
        Integer i = bookRepository.mUpdateLendStatusAndCount(request.getIsbn13());

        if (1 == 0) {
            throw new ExceptionApi500("대여 실패");
        }

        // 4. 유저와 책 객체 만들어서 저장
        User user = new User();
        user.setId(userId);  // userId만 설정

        // 5. Lend 엔티티 save
        Lend lendEntity = request.toEntity(user, bookPS);
        lendRepository.save(lendEntity);

        // 6. 대여정보 return
        return new LendResponse.LendDTO(lendEntity);

    }


    // book_Tb -> 대여상태 false, 대여수 -1
    // lend_tb -> 반납한 일자 (now), 반납상태 -> true
    @Transactional
    public LendResponse.ReturnDTO 직접반납하기(Long userId, LendRequest.ReturnDTO request) {

        // 1. 대여상태인지 확인
        Boolean b = bookRepository.mCheckLendStatus(request.getIsbn13()).orElseThrow(() -> new ExceptionApi404("요청하신 도서가 존재하지 않습니다."));

        if (!b) {
            throw new ExceptionApi404("대여중인 도서가 아닙니다.");
        }

        // 2. booktb 대여 상태 바꾸기
        int updateCount = bookRepository.mUpdateLendStatusAndCountReturn(request.getIsbn13());

        // 업데이트가 성공했는지 확인 (1이 아니면 실패)
        if (updateCount != 1) {
            throw new ExceptionApi500("도서 반납 처리 중 문제가 발생했습니다.");
        }

        // 3.lend_tb 대여 상태 바꾸기
        int returnStatus = lendRepository.mReturnLend(userId, request.getIsbn13());

        // 업데이트 성공했는지 확인 (1이 아니면 실패)
        if (returnStatus != 1) {
            throw new ExceptionApi500("도서 반납 처리 중 문제가 발생했습니다.");
        }


        // TODO: 반납 후 예약자가 있는지 확인하여 처리 - 신민재
        boolean hasReservations = reservationRepository.countCurrentReservations(request.getIsbn13()) > 0;

        if (hasReservations) {
            // 첫 번째 예약자에게 자동 대여 처리
            reservationService.자동대여(request.getIsbn13());
        }


        // 4. 반납정보 return
        Lend lendPS = lendRepository.findLatestReturnedLendNative(userId, request.getIsbn13())
                .orElseThrow(() -> new ExceptionApi404("반납된 기록을 찾을 수 없습니다."));

        return new LendResponse.ReturnDTO(lendPS);

    }

    // TODO: 자동 반납 예약 부분 잘 안됨 실행시키지마셈 ㅠ
    // 자동으로 반납시키기 ( 반납일 12시에 자동으로 반납됨 )
    // 스케줄링 설정
    @Transactional
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void 자동반납() {

        // 반납 날짜가 오늘인 대여 정보 조회
        List<Lend> lendHistoryPS = lendRepository.mFindAllByReturnDateAndReturnStatusFalse();

        // 반납할 책이 없으면 종료
        if (lendHistoryPS.isEmpty()) {
            return;
        }

        // 반납 처리
        for (Lend lend : lendHistoryPS) {
            lend.setReturnStatus(true); // 반납 상태 업데이트
            lend.setReturnDate(new Timestamp(System.currentTimeMillis())); // 반납일 설정
            lendRepository.save(lend); // 저장

            // 대여상태=false & 대여 수 -1
            Book book = lend.getBook();
            bookRepository.mUpdateLendStatusAndCountReturn(book.getIsbn13());

            // 다음 예약자 있는지 조회 (예약 상태가 true이고 예약수가 1 이상일 경우)
            // 여러건 조회되어서 터짐 -> 수정
            List<Book> reservedBook = bookRepository.mFindBookWithActiveReservation(book.getIsbn13());

            // 다음 예약자 대여 실행
            if (!reservedBook.isEmpty()) {
                // 예약자 중 reservation_id가 가장 작은 userId 조회
                // TODO: 예약자 중 sequence가 가장 작은 UserId 조회하는 걸로 수정하기
                Long userId = reservationRepository.findFirstUserIdByIsbn13(book.getIsbn13());

                // 새로운 대여 처리
                Lend newLend = new Lend();
                newLend.setUser(new User(userId));
                newLend.setBook(book);
                newLend.setLendDate(new Timestamp(System.currentTimeMillis()));
                newLend.setReturnStatus(false);
                lendRepository.save(newLend); // 새로운 대여 정보 저장

                // 예약 테이블에서 해당 예약자 삭제
                reservationRepository.deleteByUserIdAndIsbn13(userId, book.getIsbn13());
                // 다음 예약자 있으면 순번 update 해주기
            }
        }
    }





    public LendResponse.ListDTO 대여중인도서목록조회(Long userId) {

        List<Object[]> objectsPS = lendRepository.mFindBooksByUserId(userId);

        return new LendResponse.ListDTO(objectsPS); // 도서 cover, title, isbn13 -> list로!
    }

    // 연장 상태가 false 이면 true로 바꾸고, 반납일자 +7일
    // 연장 상태가 true 이면 연장불가
    @Transactional
    public LendResponse.ExtensionDTO 대여중인도서연장(Long userId, LendRequest.ExtendDTO request) {

        // 1. 조회
        Boolean resultPS = lendRepository.mCheckExtendStatus(userId, request.getIsbn13()).orElseThrow(() -> new ExceptionApi404("대여정보가 없습니다."));

        if (resultPS) {
            throw new ExceptionApi400("이미 연장한 도서입니다. 더이상 연장이 불가합니다.");
        }

        // 2. 연장해주기
        lendRepository.mExtendLend(userId, request.getIsbn13());

        // 3. 연장 내역 return
        Lend lendPS = lendRepository.mFindLend(userId, request.getIsbn13());

        return new LendResponse.ExtensionDTO(lendPS);

    }

    // userId로 조회
    // 현재 대여중인 도서 포함
    public List<LendResponse.HistoryDTO> 지금까지대여한도서들목록(Long userId) {

        List<Object[]> objsPS = lendRepository.mFindLendsAndBooksByUserId(userId);

        //Map<String, Book> uniqueBooks = new HashMap<>(); // 중복을 체크하기 위한 Map
        //Set<Book> distinctBooks = new HashSet<>();
        //List<Lend> lends = new ArrayList<>();


        // 결과를 저장할 DTO 리스트
        List<LendResponse.HistoryDTO> historyDTOList = new ArrayList<>();

        // 유저가 아직 대여한 적이 없는 경우 처리
        if (objsPS.isEmpty()) {
            throw new ExceptionApi404("대여 내역이 없습니다.");
        }

        // 쿼리 결과 반복
        for (Object[] obj : objsPS) {
            Lend lend = (Lend) obj[0];
            Book book = (Book) obj[1];

            // 각 대여와 도서 정보를 기반으로 HistoryDTO 생성 후 리스트에 추가
            historyDTOList.add(new LendResponse.HistoryDTO(lend, book));
        }

        return historyDTOList;  // 모든 lend에 대한 HistoryDTO 리스트 반환

        // 중복된 책을 제외하기 위해 isbn13을 기준으로 Map에 저장
            /*if (!uniqueBooks.containsKey(book.getIsbn13())) {
                uniqueBooks.put(book.getIsbn13(), book);
                // 중복되지 않은 책과 해당 대여 내역을 기반으로 DTO 생성
                historyDTOList.add(new LendResponse.HistoryDTO(lend, book));
            }*/

    }

}
