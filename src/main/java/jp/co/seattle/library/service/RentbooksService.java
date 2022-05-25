package jp.co.seattle.library.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jp.co.seattle.library.dto.HistoryBookInfo;
import jp.co.seattle.library.rowMapper.HistoryBookInfoRowMapper;


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
	    	 String sql = "insert into rentbooks(rentbook_id,rent_date) select "+ bookId +",now() where not exists (select * from rentbooks where rentbook_id="
	                 + bookId + ")";

	         jdbcTemplate.update(sql);
	     }

	    /**
	     * 書籍のカウント
	     * @param bookId
	     * @return
	     */
	    public Integer countRentBook(Integer bookId) {
	            
	        	String sql = "select count (rentbook_id) from rentbooks where rentbook_id = "  + bookId ;
	        	
	    		return jdbcTemplate.queryForObject(sql,Integer.class);
	    }
	    
	    
	    /**
	     * 書籍の返却
	     * @param bookId
	     */
	    public void returnbook(Integer bookId) {

	        String sql = "delete from rentbooks where rentbook_id = "+ bookId +";";
	      
	        jdbcTemplate.update(sql);
	    }     
	    
	    /**
	     * 返却情報取得
	     * @param bookId
	     */
	    public void returnbookInfo(Integer bookId) {
	       
			// TODO SQL生成
	    	 String sql = "insert into rentbooks(returnbook_id,return_date) select "+ bookId +",now() where not exists (select * from rentbooks where returnbook_id="
	                 + bookId + ")";

	         jdbcTemplate.update(sql);
	     }
	    

	    /**
	     * 書籍の返却
	     * @param bookId
	     */
	    public void returndeletebook(Integer bookId) {

	        String sql = "delete from rentbooks where returnbook_id ="+ bookId; 
	      
	        jdbcTemplate.update(sql);
	    }  
	    
	    /**
	     * 貸出履歴リスト
	     *
	     * @return 書籍リスト
	     */
	    public List<HistoryBookInfo> historygetrentBookList() {

	        // TODO 取得したい情報を取得するようにSQLを修正
	        List<HistoryBookInfo> historygetrentBookList = jdbcTemplate.query(
	        		"select books.id,books.title,rentbook_id,returnbook_id,rent_date,return_date from rentbooks join books on rentbooks.rentbook_id=books.id or rentbooks.returnbook_id=books.id;",
	        		new HistoryBookInfoRowMapper());

	        return historygetrentBookList;
	    }
}
