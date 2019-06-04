package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.service.ArticleService;
import com.example.service.CommentService;

/**
 * 掲示板アプリケーションを操作するコントローラ.
 * 
 * @author risa.nazato
 *
 */
@Controller
@RequestMapping("/bulletin-board")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private CommentService commentService;

	/**
	 * 記事のフォームをrequestスコープに格納.
	 * 
	 * @return 記事フォーム
	 */
	@ModelAttribute
	public ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}

	/**
	 * コメントのフォームをrequestスコープに格納.
	 * 
	 * @return コメントフォーム
	 */
	@ModelAttribute
	public CommentForm setUpCommentForm() {
		return new CommentForm();
	}

	/**
	 * 
	 * 記事投稿画面の表示
	 * 
	 * @return 記事投稿画面
	 */
	@RequestMapping("")
	public String index(Model model) {
		List<Article> articleList = articleService.findAll();
		for(int i=0; i< articleList.size(); i++) {
			List<Comment> commentList = commentService.findByArticleId(articleList.get(i).getId());
			articleList.get(i).setCommentList(commentList);
		}
		model.addAttribute("articleList", articleList);
		return "article";
	}

	/**
	 * 
	 * 記事の投稿.
	 * 
	 * @param form フォーム
	 * @return 掲示板画面
	 */
	@RequestMapping("/postarticle")
	public String insertArticle(ArticleForm form) {
		Article article = new Article();
		BeanUtils.copyProperties(form, article);
		articleService.insert(article);
		return "redirect:/bulletin-board";
	}

	/**
	 * コメントの投稿.
	 * 
	 * @param form フォーム
	 * @return 掲示板画面
	 */
	@RequestMapping("/postcomment")
	public String insertComment(ArticleForm form) {
		Comment comment = new Comment();
		BeanUtils.copyProperties(form, comment);
		commentService.insert(comment);
		return "redirect:/bulletin-board";
	}

	/**
	 * 
	 * 記事の削除.
	 * 
	 * @param id 記事ID
	 * @return 掲示板画面
	 */
	@RequestMapping("/deletearticle")
	public String deleteArticle(Integer id) {
		articleService.deleteArticleAndComment(id);
		return "redirect:/bulletin-board";
	}
}
