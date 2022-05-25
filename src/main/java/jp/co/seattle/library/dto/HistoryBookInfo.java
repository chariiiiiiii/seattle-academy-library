package jp.co.seattle.library.dto;

import java.sql.Date;

import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * 書籍詳細情報格納DTO
 *
 */
@Configuration
@Data
public class HistoryBookInfo {

    private int bookId;
    private String title;
    private Date rentdate;
    private Date returndate;
   

    public HistoryBookInfo() {

    }

    public HistoryBookInfo(int bookId, String title,Date rentdate,Date returndate){
        this.bookId = bookId;
        this.title = title;
        this.rentdate = rentdate;
        this.returndate = returndate;
      
        
    }



}