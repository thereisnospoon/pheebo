package my.thereisnospoon.pheebo.services;

import my.thereisnospoon.pheebo.persistence.model.*;
import my.thereisnospoon.pheebo.persistence.model.Thread;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class PostService {

	@PersistenceContext
	EntityManager entityManager;

	public Post getPost(Long postId) {
		return postId != null ? entityManager.find(Post.class, postId) : null;
	}

	public Post storePost(Post post, Long threadId) {

		post.setThread(entityManager.find(Thread.class, threadId));
		post.setPostedWhen(new Date());
		entityManager.merge(post);
		return post;
	}
}
