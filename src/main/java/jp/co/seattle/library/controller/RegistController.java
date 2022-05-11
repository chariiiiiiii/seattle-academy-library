package jp.co.seattle.library.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;

	

	@Controller //APIの入り口
	public class RegistController {
	    final static Logger logger = LoggerFactory.getLogger(RegistController.class);
	    
	    @Autowired
	    private BooksService booksService;
	 
	    
	    /**
	     * 書籍一括登録コントローラー
	     * @param model
	     * @return
	     */
		 @RequestMapping(value = "/regist", method = RequestMethod.GET) //value＝actionで指定したパラメータ
		    //RequestParamでname属性を取得
		    public String login(Model model) {
		        return "regist";
		 }
		 
		 
		 /**
		  * 書籍を一括登録する
		  * @param locale
		  * @param uploadFile
		  * @param model
		  * @return
		  */
		 
    @RequestMapping(value = "/registBook", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")	        
	public String registbook(Locale locale,
				@RequestParam("upload_file") MultipartFile uploadFile,
	            Model model){
    	
   
    	
    	  List<String> list1 = new ArrayList<String>();
	      List<BookDetailsInfo> list2 = new ArrayList<BookDetailsInfo>();
	      
	      
	 
	try (BufferedReader br = new BufferedReader(new InputStreamReader(uploadFile.getInputStream(),StandardCharsets.UTF_8))){
		String line;
	 
		int count = 1;
		
	 	while((line = br.readLine()) != null) {
		 

	 		String[] split = line.split(",",-1);
	 		BookDetailsInfo bookInfo= new BookDetailsInfo();
	 		
	 		boolean errorRequired = split[0].isEmpty() || split[1].isEmpty() || split[2].isEmpty() ||split[3].isEmpty();
	 		boolean errorPublishDate = ! (split[3].length() == 8 && split[3].matches("^[0-9]+$"));
	 		boolean errorISBN = !(split[4].length() == 10 || split[4].length() == 13 || split[4].length() == 0);
         
	 		//必須項目
	 		if(errorRequired||errorPublishDate||errorISBN) {
	 			list1.add(count+"行目の書籍登録でエラーが起きました。");
	 			
	 		}else{
	 			bookInfo.setTitle(split[0]);
		 		bookInfo.setAuthor(split[1]);
		 		bookInfo.setPublisher(split[2]);
		 		bookInfo.setPublishDate(split[3]);
		 		bookInfo.setIsbn(split[4]);
		 		bookInfo.setColum(split[5]);
		 		
	 			list2.add(bookInfo);
	 		}
	 		count ++;
	 	}
	 		
	 		if(list2.size() == 0) {model.addAttribute("errorMessage","CSVに書籍情報がありません。");
	 		return "regist";

	 		}
	 		
	 	
	 	

	 }catch (IOException e) {
	   throw new RuntimeException("ファイルが読み込めません", e);
	   	
    }
	
	if(list1.size() != 0) {
		model.addAttribute("errorlist",list1);
		return "regist";
		
		
	}

	else{
		for(BookDetailsInfo bookInfo:list2) {
			booksService.registBook(bookInfo);
		}
		 model.addAttribute("bookList", booksService.getBookList());
		 return "home";
	 }

    
    

    
   
}
	
	
	
	}


		 
	
	

