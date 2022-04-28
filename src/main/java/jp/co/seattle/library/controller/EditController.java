package jp.co.seattle.library.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.ThumbnailService;

/**
 * Handles requests for the application home page.
 */
@Controller // APIの入口
public class EditController {
	    final static Logger logger = LoggerFactory.getLogger(BooksService.class);

	    @Autowired
	    private BooksService booksService;
	    @Autowired
	    private ThumbnailService thumbnailService;
	  
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST) 
		public String edit(Locale locale,
	            @RequestParam("bookId") int bookId,
	          Model model) {
		logger.info("Welcome editControler.java! The client locale is {}.", locale);
		model.addAttribute("editInfo", booksService.getBookInfo(bookId));
		
		return "edit";

	}
	/**
     * 書籍情報を登録する
     * @param locale ロケール情報
     * @param title 書籍名
     * @param author 著者名
     * @param publisher 出版社
     * @param file サムネイルファイル
     * @param model モデル
     * @param isbn 
     * @param colum 
	 * @param bookId 
     * @return 遷移先画面
     */
    @Transactional
    @RequestMapping(value = "/editbook", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    public String editbook(Locale locale,
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("publisher") String publisher,
            @RequestParam("thumbnail") MultipartFile file,
            @RequestParam("isbn") String isbn,
            @RequestParam("colum") String colum,
            @RequestParam("publish_date") String publishDate,
            @RequestParam("bookId") int bookId,
            Model model) {
        logger.info("Welcome editbooks.java! The client locale is {}.", locale);

        // パラメータで受け取った書籍情報をDtoに格納する。
        BookDetailsInfo bookInfo = new BookDetailsInfo();
        bookInfo.setTitle(title);
        bookInfo.setAuthor(author);
        bookInfo.setPublisher(publisher);
        bookInfo.setIsbn(isbn);
        bookInfo.setPublishDate(publishDate);
        bookInfo.setColum(colum);
        bookInfo.setBookId(bookId);

        // クライアントのファイルシステムにある元のファイル名を設定する
        String thumbnail = file.getOriginalFilename();

        if (!file.isEmpty()) {
            try {
                // サムネイル画像をアップロード
                String fileName = thumbnailService.uploadThumbnail(thumbnail, file);
                // URLを取得
                String thumbnailUrl = thumbnailService.getURL(fileName);

                bookInfo.setThumbnailName(fileName);
                bookInfo.setThumbnailUrl(thumbnailUrl);

            } catch (Exception e) {

                // 異常終了時の処理
                logger.error("サムネイルアップロードでエラー発生", e);
                model.addAttribute("bookEditInfo", bookInfo);
                return "edit";
            }
        }
        
        List<String> list = new ArrayList<String>();
        
        boolean errorRequired = title.isEmpty() || author.isEmpty() || publisher.isEmpty() || publishDate.isEmpty();
        boolean errorPublishDate = ! (publishDate.length() == 8 && publishDate.matches("^[0-9]+$"));
        boolean errorISBN = !(isbn.length() == 10 || isbn.length() == 13 || isbn.length() == 0);
        
        //必須項目
        if(errorRequired) {
        	list.add("必須項目を入力してください");
        	
        }
        
        //出版日
        if(errorPublishDate) {
        	list.add("出版日は半角英数字の形式で入力してください");
        	
        }

        //ISBN
        if(errorISBN) {
        	list.add("ISBNの桁数または半角英数字が正しくありません");
        	
        }
        
   
        if(errorRequired || errorPublishDate || errorISBN) {
        	model.addAttribute("editInfo",bookInfo);
        	model.addAttribute("errorlists",list);
        	return "edit";
        }
        
        
        
        // 書籍情報を新規登録する
        booksService.editbook(bookInfo);
   

        // TODO 登録した書籍の詳細情報を表示するように実装
        
            model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookInfo.getBookId()));
            
         
        //  詳細画面に遷移する
        return "details";
    }

}

	

