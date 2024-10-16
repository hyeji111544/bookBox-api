-- ssar 유저 대여중 도서 8권 추가
INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9791187011590', '2024-10-12 20:49:17.921182', false, false, DATEADD(day, 7, '2024-10-12 20:49:17.921182'));

INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9788937461798', '2024-10-12 13:12:17.921182', false, false, DATEADD(day, 7, '2024-10-12 13:12:17.921182'));

INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9788937462788', '2024-10-12 19:15:17.921182', false, false, DATEADD(day, 7, '2024-10-12 19:15:17.921182'));

INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9791159312236', '2024-10-13 16:20:17.921182', false, false, DATEADD(day, 7, '2024-10-13 16:20:17.921182'));

INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9788937461033', '2024-10-13 19:21:17.921182', false, false, DATEADD(day, 7, '2024-10-13 19:21:17.921182'));

INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9791190669238', '2024-10-13 07:50:17.921182', false, false, DATEADD(day, 7, '2024-10-13 07:50:17.921182'));

INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9788937462849', '2024-10-14 10:20:17.921182', false, false, DATEADD(day, 7, '2024-10-14 10:20:17.921182'));

INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9788937460616', '2024-10-14 15:30:17.921182', false, false, DATEADD(day, 7, '2024-10-14 15:30:17.921182'));


-- ssar이 예약한 책 2권 다른애들이 대여함
INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date) VALUES (2, '9788932473901', '2024-10-12 15:30:17.921182', false, false, DATEADD(day, 7, '2024-10-12 15:30:17.921182'));
INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date) VALUES (2, '9788937460258', '2024-10-13 17:20:17.921182', false, false, DATEADD(day, 7, '2024-10-13 17:20:17.921182'));

-- 즐겨찾기를 위한 더미추가
INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date) VALUES (2, '9788937460586', '2024-10-15 14:30:17.921182', false, false, DATEADD(day, 7, '2024-10-15 14:30:17.921182'));
INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date) VALUES (2, '9788937443848', '2024-10-15 19:30:17.921182', false, false, DATEADD(day, 7, '2024-10-15 19:30:17.921182'));





-- ssar 대여기록 20개
INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9788932924649', '2024-04-12 20:49:17.921182', false, true, '2024-04-16 20:22:17.921182');

INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9788932924663', '2024-06-13 14:49:17.921182', false, true, '2024-06-15 18:22:17.921182');

INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9788937460500', '2024-06-14 22:49:17.921182', false, true, '2024-06-19 12:22:17.921182');

INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9791139707137', '2024-07-13 09:44:17.921182', false, true, '2024-07-19 19:29:17.921182');

INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9788934942467', '2024-07-18 10:42:17.921182', false, true, '2024-07-23 12:24:17.921182');

INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9791141138936', '2024-09-20 17:22:17.921182', false, true, '2024-09-25 12:59:17.921182');

INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9791167740984', '2024-09-27 19:39:17.921182', false, true, '2024-09-29 14:26:17.921182');

INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9791136791047', '2024-10-01 17:19:17.921182', false, true, '2024-10-06 15:20:17.921182');


INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9791185994703', '2024-10-02 10:00:00.000000', false, true, '2024-10-05 14:30:00.000000');

INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9789813350212', '2024-10-03 09:15:00.000000', false, true, '2024-10-07 11:20:00.000000');

INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9791168761841', '2024-10-04 11:30:00.000000', false, true, '2024-10-09 16:45:00.000000');

INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9791172882785', '2024-10-05 15:00:00.000000', false, true, '2024-10-08 18:00:00.000000');

INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9788934991328', '2024-10-06 18:20:00.000000', false, true, '2024-10-10 12:45:00.000000');


INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9791167741509', '2024-10-02 09:45:00.000000', false, true, '2024-10-04 13:30:00.000000');

INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9791190186094', '2024-10-03 14:10:00.000000', false, true, '2024-10-06 15:20:00.000000');

INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9791194033301', '2024-10-04 17:20:00.000000', false, true, '2024-10-08 19:00:00.000000');

INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9791193712481', '2024-10-05 11:30:00.000000', false, true, '2024-10-09 13:15:00.000000');

INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9791188331796', '2024-10-06 16:50:00.000000', false, true, '2024-10-10 11:20:00.000000');

INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9791167740984', '2024-10-02 10:20:00.000000', false, true, '2024-10-04 17:10:00.000000');

INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9791169850919', '2024-10-03 18:35:00.000000', false, true, '2024-10-05 12:00:00.000000');

INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9791193063644', '2024-10-04 15:45:00.000000', false, true, '2024-10-08 20:20:00.000000');

INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9791198853905', '2024-10-05 14:30:00.000000', false, true, '2024-10-07 18:00:00.000000');

INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9788962624953', '2024-10-06 12:10:00.000000', false, true, '2024-10-09 16:30:00.000000');

INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9791198886101', '2024-10-02 09:00:00.000000', false, true, '2024-10-05 14:00:00.000000');

INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9788927880448', '2024-10-03 08:15:00.000000', false, true, '2024-10-05 18:10:00.000000');

INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9788934942467', '2024-10-04 19:00:00.000000', false, true, '2024-10-06 20:40:00.000000');

INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9788937460500', '2024-10-05 12:45:00.000000', false, true, '2024-10-07 14:50:00.000000');

INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date)
VALUES (1, '9788937460777', '2024-10-06 16:20:00.000000', false, true, '2024-10-09 11:35:00.000000');







/*
INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date) VALUES (2, '9788937460432', now(), false, false, now());
INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date) VALUES (2, '9791139707137', now(), false, false, DATEADD(day, 7, now()));
INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date) VALUES (2, '9788962185027', now(), false, false, DATEADD(day, 7, now()));
INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date) VALUES (2, '9791163034735', now(), false, false, DATEADD(day, 7, now()));
INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date) VALUES (2, '9789813350212', now(), false, false, DATEADD(day, 7, now()));
INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date) VALUES (3, '9791193080375', now(), false, false, DATEADD(day, 7, now()));
INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date) VALUES (3, '9788927880059', now(), false, false, DATEADD(day, 7, now()));
INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date) VALUES (3, '9771228773007', now(), false, false, DATEADD(day, 7, now()));
INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date) VALUES (3, '9791161373355', now(), false, false, DATEADD(day, 7, now()));
INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date) VALUES (3, '9788932473901', now(), false, false, now());

-- 반납 테스트용
INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date) VALUES (1, '9791187142591', now(), false, false, DATEADD(day, 7, now()));

-- 대여기간 만료로 인한 자동반납 테스트용 데이터
update lend_tb set return_date = CURRENT_DATE where user_id = 4;*/