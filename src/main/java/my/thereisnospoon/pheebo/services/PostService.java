package my.thereisnospoon.pheebo.services;

import my.thereisnospoon.pheebo.persistance.model.Post;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Service
@Transactional
public class PostService {

	@PersistenceContext
	EntityManager entityManager;

	public Post getPost(Long postId) {
		return postId != null ? entityManager.find(Post.class, postId) : null;
	}

	public Post storePost(Post post) {
		entityManager.merge(post);
		return post;
	}
}
