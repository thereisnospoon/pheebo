package my.thereisnospoon.pheebo.services;

import my.thereisnospoon.pheebo.persistence.model.*;
import my.thereisnospoon.pheebo.persistence.model.Thread;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PostService {

	@PersistenceContext
	EntityManager entityManager;

	public Post getPost(Long postId) {
		return postId != null ? entityManager.find(Post.class, postId) : null;
	}

	public Post storePost(Post post, Long threadId, Long imageId) {

		if (imageId != null) {
			post.setImage(entityManager.find(Image.class, imageId));
		}

		post.setThread(entityManager.find(Thread.class, threadId));
		post.setPostedWhen(new Date());
		return entityManager.merge(post);
	}

	@SuppressWarnings("unchecked")
	public List<Post> getPostsAfter(Long postId, Long threadId) {

		return entityManager.createQuery("select p from Post p where p.thread.threadId = :threadId and p.postId > :lastPostId order by postId")
				.setParameter("lastPostId", postId)
				.setParameter("threadId", threadId)
				.getResultList();
	}
}
