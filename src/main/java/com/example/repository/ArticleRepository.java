package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;

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

	private static final RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, i) -> {
		Article article = new Article();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));
		return article;

	};

	/**
	 * 全件検索を行う.
	 * 
	 * @return 全記事一覧
	 */
	public List<Article> findAll() {
		//テーブル分割
		String sql = "SELECT id,name,content FROM articles ORDER BY id DESC;";
		//テーブル結合(中級問題・記事一括表示)
//		String sql = "SELECT a.id,a.name,a.content,c.id,c.name,c.content,c.article_id "
//				+ "FROM articles a INNER JOIN comments c ON a.id = c.article_id ORDER BY a.id DESC, a.id;";

		List<Article> articleList = template.query(sql, ARTICLE_ROW_MAPPER);

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
