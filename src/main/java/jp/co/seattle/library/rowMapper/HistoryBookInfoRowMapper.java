package jp.co.seattle.library.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import jp.co.seattle.library.dto.HistoryBookInfo;

@Configuration
public class HistoryBookInfoRowMapper implements RowMapper<HistoryBookInfo> {

    @Override
    public HistoryBookInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        // Query結果（ResultSet rs）を、オブジェクトに格納する実装
    	HistoryBookInfo historyBookInfo = new HistoryBookInfo();

    	historyBookInfo.setBookId(rs.getInt("id"));
    	historyBookInfo.setTitle(rs.getString("title"));
    	historyBookInfo.setRentdate(rs.getDate("rent_date"));
    	historyBookInfo.setReturndate(rs.getDate("return_date"));  
        return historyBookInfo;
    }

}