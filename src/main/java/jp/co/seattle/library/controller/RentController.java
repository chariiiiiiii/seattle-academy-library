package jp.co.seattle.library.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.RentbooksService;


@Controller
class RentController {
	final static Logger logger = LoggerFactory.getLogger(RentbooksService.class);
	
	 @Autowired
	    private BooksService booksService;
	 @Autowired
	    private RentbooksService rentbooksService;
	    
	 
	 /**
	  * 書籍を貸出する
	  * @param locale
	  * @param bookId
	  * @param model
	  * @return
	  */
	 
	    @RequestMapping(value = "/rent", method = RequestMethod.POST) 
	public String rent(Locale locale,
	            @RequestParam("bookId") int bookId,
	          Model model) {
		logger.info("Welcome editControler.java! The client locale is {}.", locale);
		
		
        Integer count = rentbooksService.countRentBook(bookId); 
        rentbooksService.rentbookInfo(bookId);
        Integer rentcount = rentbooksService.countRentBook(bookId);
        
        
        if (count < rentcount) {
        	model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));
			
		}else {
			model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));
			model.addAttribute("errorMessage","貸出し済みです。");
				
		}
        return "details";
		
	}
	  
}

