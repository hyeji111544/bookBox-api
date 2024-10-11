package green.mtcoding.bookbox.book;

import green.mtcoding.bookbox.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, String> {
    //검색으로 찾는 것
    @Query("SELECT b FROM Book b WHERE b.title LIKE %:searchTerm% OR b.author LIKE %:searchTerm% OR b.publisher LIKE %:searchTerm%")
    List<Book> mFindAll(@Param("searchTerm") String searchTerm);
    //메인에 보일 것
    @Query("select b from Book b left join fetch b.category c")
    List<Book> mFindAllWithCategory();
    //메인에 보일 것 테스트
    @Query("select b from Book b left join fetch b.category c")
    List<Category> mFindAllWithCategoryV2();

    //카테고리 누르면 해당 책들 나올 수 있게 만들기
    @Query("select b from Book b where b.category.id =:id")
    List<Book> mFindByCategoryId(@Param("id") String id);


}
