package jp.co.seattle.library.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.service.BooksService;

@Controller
public class SearchBookController {
	final static Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private BooksService booksService;

    /**
     * 検索フォーム
     * @param model
     * *@param searchword
     * @return
     */
    @Transactional
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(@RequestParam("searchword") String searchword,
    		@RequestParam("search") String search,
    		Model model) {
    	
    	if(search.equals("allsearch")) {
    		model.addAttribute("bookList", booksService.allsearchbookList(searchword));
    	}
    	else{
    		model.addAttribute("bookList", booksService.searchbookList(searchword));
	
    	}
    	 return "home";
    }
}
	
	
