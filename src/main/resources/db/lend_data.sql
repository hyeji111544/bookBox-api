INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date) VALUES (1, '9791187011590', now(), false, true, '2024-10-12 20:49:17.921182');
INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date) VALUES (1, '9791187011590', now(), false, true, '2024-10-12 20:55:17.921182');
INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date) VALUES (1, '9791187011590', now(), false, true, '2024-10-13 20:20:17.921182');
INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date) VALUES (1, '9791187011590', now(), false, false, '2024-10-16 22:10:17.921182');
INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date) VALUES (1, '9788937461798', now(), false, false, now());
INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date) VALUES (1, '9788937462788', now(), false, false, '2024-10-16 20:49:17.921182');
INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date) VALUES (1, '9788937462849', now(), false, false, '2024-10-16 20:49:17.921182');
INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date) VALUES (1, '9788937460470', now(), false, false, now());
INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date) VALUES (1, '9788937460470', now(), false, false, DATEADD(day, 7, now()));
INSERT INTO lend_tb (user_id, book_id, lend_date, extend_status, return_status, return_date) VALUES (1, '9791167740984', now(), false, false, DATEADD(day, 7, now()));
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
update lend_tb set return_date = CURRENT_DATE where user_id = 4;