package jp.co.seattle.library.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


@Service
public class RentbooksService {
	 final static Logger logger = LoggerFactory.getLogger(BooksService.class);
	    @Autowired
	    private JdbcTemplate jdbcTemplate;
	    
	    /**
	     * 書籍情報取得
	     * @param bookId
	     */
	    public void rentbookInfo(Integer bookId) {
	       
			// TODO SQL生成
	    	 String sql = "insert into rentbooks(book_id) select "+ bookId +" where not exists (select * from rentbooks where book_id = "
	                 +bookId + ")";

	         jdbcTemplate.update(sql);
	     }

	    
	    public Integer countRentBook(Integer bookId) {
	            
	        	String sql = "select count (book_id) from rentbooks where book_id = "  + bookId ;
	        	
	    		return jdbcTemplate.queryForObject(sql,Integer.class);
	    }

}
