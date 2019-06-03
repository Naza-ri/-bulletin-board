package com.example.form;

/**
 * 
 * コメントのリクエストパラメータが入るフォーム.
 * 
 * @author risa.nazato
 *
 */
public class CommentForm {

	/** 記事のID */
	private String articleId;
	/** 名前 */
	private String name;
	/** コンテント */
	private String content;

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
