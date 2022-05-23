package jp.co.seattle.library.controller;

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

import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.RentbooksService;

@Controller // APIの入り口
public class HistoryBooksController {

	final static Logger logger = LoggerFactory.getLogger(RegistController.class);

	@Autowired
	private RentbooksService rentbooksService;

	@Autowired
	private BooksService bookdService;

	/**
	 * 書籍一括登録コントローラー
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/history", method = RequestMethod.GET) // value＝actionで指定したパラメータ
	// RequestParamでname属性を取得
	public String historybook(Model model) {

		model.addAttribute("rentbookList", rentbooksService.historygetrentBookList());

		return "history";

	}

	/**
	 * 詳細画面に遷移する
	 * 
	 * @param locale
	 * @param bookId
	 * @param model
	 * @return
	 */
	@Transactional
	@RequestMapping(value = "/updatehistory", method = RequestMethod.GET)
	public String history(Locale locale, @RequestParam("bookId") int bookId, Model model) {
		// デバッグ用ログ
		logger.info("Welcome detailsControler.java! The client locale is {}.", locale);

		model.addAttribute("bookDetailsInfo", bookdService.getBookInfo(bookId));

		return "details";
	}

}
