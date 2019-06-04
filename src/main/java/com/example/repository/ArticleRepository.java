package com.example.repository;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;
import com.example.domain.Comment;

/**
 * articlesテーブルを処理するリポジトリ.
 * 
 * @author risa.nazato
 *
 */
@Repository
public class ArticleRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;


	/**
	 * 初級問題.
	 */
//	private static final RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, i) -> {
//		
//		Article article = new Article();
//		article.setId(rs.getInt("id"));
//		article.setName(rs.getString("name"));
//		article.setContent(rs.getString("content"));
//		return article;
//
//	};

	
	/**
	 * 中級問題.
	 * 記事をコメントと共に一括で表示させるためのインタフェース.
	 * 
	 */
	private static final ResultSetExtractor<List<Article>> RESULT_SET_EXTRACTOR = (rs) -> {
		List<Article> articleList = new LinkedList<>();
		List<Comment> commentList = null;
		Article article = null;
		int previousarticleid = 0;

		while (rs.next()) {
			if (rs.getInt("id") != previousarticleid) {
				article = new Article();
				article.setId(rs.getInt("id"));
				article.setName(rs.getString("name"));
				article.setContent(rs.getString("content"));
				commentList = new LinkedList<>();
				article.setCommentList(commentList);
				articleList.add(article);
			}

			if (rs.getInt("com_id") != 0) {
				Comment comment = new Comment();
				comment.setId(rs.getInt("com_id"));
				comment.setName(rs.getString("com_name"));
				comment.setContent(rs.getString("com_content"));
				comment.setArticleId(rs.getInt("article_id"));
				commentList.add(comment);
			}
			previousarticleid = article.getId();
		}

		return articleList;
	};

	/**
	 * 全件検索を行う.
	 * 
	 * @return 全記事一覧
	 */
	public List<Article> findAll() {
//		テーブル分割(初級)
//		String sql = "SELECT id,name,content FROM articles ORDER BY id DESC;";
//		List<Article> articleList = template.query(sql, ARTICLE_ROW_MAPPER);
//		return articleList;

		// テーブル結合(中級問題・記事一括表示)
		String sql = "SELECT a.id,a.name,a.content,c.id com_id,c.name com_name,c.content com_content,c.article_id  "
				+ "FROM articles a LEFT OUTER JOIN comments c ON a.id = c.article_id ORDER BY a.id DESC,c.id DESC;";
		List<Article> articleList = template.query(sql, RESULT_SET_EXTRACTOR);
		return articleList;
	}

	/**
	 * 記事を投稿する.
	 * 
	 * @param article 記事
	 * @return 記事
	 */
	public Article insert(Article article) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
		String sql = "INSERT INTO articles(name, content) VALUES(:name, :content)";
		template.update(sql, param);
		return article;
	}

	/**
	 * 主キーを使って１件の記事情報を削除する.
	 * 
	 * @param id ID
	 */
	public void deleteById(int id) {
		String sql = "DELETE FROM articles WHERE id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}
}
