package com.example.Repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.Domain.Article;

/**
 * Articleテーブルの処理
 * 
 * @author risa.nazato
 *
 */
@Repository
public class ArticleRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Article> COMMENT_ROW_MAPPER = (rs, i) -> {
		Article article = new Article();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));
//		article.setCommentList(rs.getArray("commentList"));
		return article;

	};

	/**
	 * 全件検索を行う.
	 * 
	 * @return 全記事一覧
	 */
	public List<Article> findAll() {
		String sql = "SELECT ";

		List<Article> articleList = template.query(sql, COMMENT_ROW_MAPPER);

		return articleList;
	}

	/**
	 * 渡した記事情報を保存する.
	 * 
	 * @param article 記事
	 */
	public void insert(Article article) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);

		String insertSql = "INSERT INTO articles(id,name,content,article_id) + VALUES(:id,:name:content,:article_id";

		template.update(insertSql, param);

	}

	/**
	 * 主キーを使って１件の従業員情報を削除する.
	 * 
	 * @param id ID
	 */
	public void deleteById(int id) {
		String deleteSql = "DELETE FROM articles WHERE id=:id";

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		template.update(deleteSql, param);
	}
}
