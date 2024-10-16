
-- ssar이 2권 예약중
INSERT INTO reservation_tb (user_id, book_id, reservation_date, sequence) VALUES (1, '9788932473901', '2024-10-15 11:30:17.921182', 1);
INSERT INTO reservation_tb (user_id, book_id, reservation_date, sequence) VALUES (1, '9788937460258', '2024-10-15 17:20:17.921182', 1);

-- 즐겨찾기를 위한 더미추가
INSERT INTO reservation_tb (user_id, book_id, reservation_date, sequence) VALUES (3, '9788937460586', '2024-10-15 16:30:17.921182', 1);
INSERT INTO reservation_tb (user_id, book_id, reservation_date, sequence) VALUES (4, '9788937460586', '2024-10-15 18:11:17.921182', 2);
INSERT INTO reservation_tb (user_id, book_id, reservation_date, sequence) VALUES (5, '9788937460586', '2024-10-15 20:48:17.921182', 3);
INSERT INTO reservation_tb (user_id, book_id, reservation_date, sequence) VALUES (4, '9788937443848', '2024-10-15 20:50:17.921182', 1);
INSERT INTO reservation_tb (user_id, book_id, reservation_date, sequence) VALUES (5, '9788937443848', '2024-10-15 22:35:17.921182', 2);


/*
INSERT INTO reservation_tb (user_id, book_id, reservation_date, sequence) VALUES (1, '9788937460432', now(), 1);
INSERT INTO reservation_tb (user_id, book_id, reservation_date, sequence) VALUES (1, '9791139707137', now(), 1);
INSERT INTO reservation_tb (user_id, book_id, reservation_date, sequence) VALUES (1, '9788962185027', now(), 1);

INSERT INTO reservation_tb (user_id, book_id, reservation_date, sequence) VALUES (2, '9788937462788', now(), 1);
INSERT INTO reservation_tb (user_id, book_id, reservation_date, sequence) VALUES (2, '9788937462849', now(), 1);

INSERT INTO reservation_tb (user_id, book_id, reservation_date, sequence) VALUES (3, '9791187011590', now(), 3);
INSERT INTO reservation_tb (user_id, book_id, reservation_date, sequence) VALUES (3, '9788937462788', now(), 2);
INSERT INTO reservation_tb (user_id, book_id, reservation_date, sequence) VALUES (3, '9788937462849', now(), 2);

-- 반납 테스트용
INSERT INTO reservation_tb (user_id, book_id, reservation_date, sequence) VALUES (2, '9791187142591', now(), 1);*/