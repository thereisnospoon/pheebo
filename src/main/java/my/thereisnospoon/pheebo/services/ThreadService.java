package my.thereisnospoon.pheebo.services;

import my.thereisnospoon.pheebo.persistance.model.*;
import my.thereisnospoon.pheebo.persistance.model.Thread;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class ThreadService {

	private static final String getLastThreadPostsQuery =
			"select p from Post p " +
					"where p.thread.threadId = :id " +
					"order by p.postedWhen desc";

	private static final String getFirstThreadPostsQuery =
			"select p from Post p " +
					"where p.thread.threadId = :id " +
					"order by p.postedWhen";

	public static final int NUMBER_OF_POSTS_FOR_PREVIEW = 3;

	@PersistenceContext
	private EntityManager entityManager;

	public Thread getThread(Long threadId) {
		return threadId != null ? entityManager.find(Thread.class, threadId) : null;
	}

	public List<Thread> getBoardsThreads(String boardPath) {
		return new LinkedList<>(entityManager.find(Board.class, boardPath).getThreads());
	}


	/**
	 * Returns head post of thread and last three thread's posts
	 * @param threadId	thread id
	 * @return list of posts
	 */
	@SuppressWarnings("unchecked")
	public List<Post> getThreadPreview(Long threadId) {

		List<Post> lastPosts = entityManager.createNamedQuery(getLastThreadPostsQuery).setParameter("id", threadId)
				.setMaxResults(NUMBER_OF_POSTS_FOR_PREVIEW).getResultList();

		Post headPost = (Post) entityManager.createNamedQuery(getFirstThreadPostsQuery).setMaxResults(1)
				.setParameter("id", threadId)
				.getResultList().get(0);

		if (!lastPosts.contains(headPost)) {
			lastPosts.add(headPost);
		}

		Collections.reverse(lastPosts);

		return lastPosts;
	}
}
