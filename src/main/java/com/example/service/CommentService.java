package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Comment;
import com.example.repository.CommentRepository;

/**
 * コメントに関するサービス.
 * 
 * @author risa.nazato
 *
 */
@Service
public class CommentService {
	@Autowired
	private CommentRepository commentRepository;

	/**
	 * コメントを登録.
	 * 
	 * @param comment コメント情報
	 * @return 登録したコメント情報
	 */
	public void insert(Comment comment) {
		commentRepository.insert(comment);
	}
	
	public List<Comment> findByArticleId(int articleId) {
		return commentRepository.findByArticleId(articleId);
	}
}

	