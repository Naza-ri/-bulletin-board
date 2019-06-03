package com.example.form;

/**
 * 
 * 記事のリクエストパラメータが入るフォーム.
 * 
 * @author risa.nazato
 *
 */
public class ArticleForm {
	/** 名前 */
	private String name;
	/** コンテント */
	private String content;

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
