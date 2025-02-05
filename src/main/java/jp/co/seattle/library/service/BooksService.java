package jp.co.seattle.library.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.rowMapper.BookDetailsInfoRowMapper;
import jp.co.seattle.library.rowMapper.BookInfoRowMapper;

/**
 * 書籍サービス
 * 
 *  booksテーブルに関する処理を実装する
 */
@Service
public class BooksService {
    final static Logger logger = LoggerFactory.getLogger(BooksService.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 書籍リストを取得する
     *
     * @return 書籍リスト
     */
    public List<BookInfo> getBookList() {

        // TODO 取得したい情報を取得するようにSQLを修正
        List<BookInfo> getedBookList = jdbcTemplate.query(
                "select id,title,author,publisher,publish_date,thumbnail_url from books order by title asc",
                new BookInfoRowMapper());

        return getedBookList;
    }

    /**
     * 書籍IDに紐づく書籍詳細情報を取得する
     *
     * @param bookId 書籍ID
     * @return 書籍情報
     */
    public BookDetailsInfo getBookInfo(int bookId) {

        // JSPに渡すデータを設定する
        String sql = "SELECT *, CASE WHEN rentbook_id > 0 THEN '貸出し中' ELSE '貸出し可' end "
        		+ "FROM books left join rentbooks on books.id = rentbooks.rentbook_id where books.id ="+ bookId;

        BookDetailsInfo bookDetailsInfo = jdbcTemplate.queryForObject(sql, new BookDetailsInfoRowMapper());
 
        return bookDetailsInfo;
    }
    
    /**
     * 書籍IDに紐づく書籍詳細情報を取得する
     *
     * @param bookId 書籍ID
     * @return 書籍情報
     */
    public BookDetailsInfo getbook() {
    	String sql = " SELECT *, CASE WHEN rentbook_id > 0 THEN '貸出し中' ELSE '貸出し可' end "
    			+"FROM books left join rentbooks on books.id = rentbooks.rentbook_id where books.id = (select max(id) from books);";
    	
    	BookDetailsInfo latestBookDetailsInfo = jdbcTemplate.queryForObject(sql, new BookDetailsInfoRowMapper());
    	return latestBookDetailsInfo;
    }
    
    /**
     * 書籍を登録する
     *
     * @param bookInfo 書籍情報
     */
    public void registBook(BookDetailsInfo bookInfo) {

        String sql = "INSERT INTO books (title, author,publisher,thumbnail_name,thumbnail_url,publish_date,colum,isbn,reg_date,upd_date) VALUES ('"
                + bookInfo.getTitle() + "','" + bookInfo.getAuthor() + "','" + bookInfo.getPublisher() + "','"
                + bookInfo.getThumbnailName() + "','"
                + bookInfo.getThumbnailUrl() + "','"
                + bookInfo.getPublishDate() + "','"
                + bookInfo.getColum() + "','"
                + bookInfo.getIsbn() + "',"
                + "now(),"
                + "now())";
      
        jdbcTemplate.update(sql);
    }      
       
    public void deleteBook(Integer bookId) {

        String sql = "delete from books where id = "+ bookId +";";
      
        jdbcTemplate.update(sql);
    }      
    
    
    
    
    /**
     * 書籍を更新する
     *
     * @param bookInfo 書籍情報
     */
    public void editbook(BookDetailsInfo bookInfo) {

        String sql = "UPDATE books set title='"+bookInfo.getTitle()+"',author='"+bookInfo.getAuthor()+"',publisher='"+bookInfo.getPublisher()
        		+"',thumbnail_name='"+bookInfo.getThumbnailName()+"',thumbnail_url='"+bookInfo.getThumbnailUrl()
        		+"',publish_date='"+bookInfo.getPublishDate()+"',colum='"+bookInfo.getColum()
        		+"',isbn='"+bookInfo.getIsbn()+"',reg_date=now(),upd_date=now() where id="+bookInfo.getBookId()+";" ;
        
      
        jdbcTemplate.update(sql);
        
    }      
    
    
    /**
     * 書籍リストを取得する
     *
     * @return 書籍リスト
     */
    public List<BookInfo> searchbookList(String searchword) {

        // TODO 取得したい情報を取得するようにSQLを修正
        List<BookInfo> searchBookList = jdbcTemplate.query(
        		"SELECT * FROM books WHERE title LIKE '%"+searchword+"%'",
                new BookInfoRowMapper());

        return searchBookList;
    }
   
 
        	
    
    
        	
    
    
}
