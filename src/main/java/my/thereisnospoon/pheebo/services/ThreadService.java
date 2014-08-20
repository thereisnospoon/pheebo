package my.thereisnospoon.pheebo.services;

import my.thereisnospoon.pheebo.persistance.model.*;
import my.thereisnospoon.pheebo.persistance.model.Thread;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class ThreadService {

	@PersistenceContext
	private EntityManager entityManager;

	public Thread getThread(Long threadId) {
		return threadId != null ? entityManager.find(Thread.class, threadId) : null;
	}

	public List<Thread> getBoardsThreads(String boardPath) {
		return new LinkedList<>(entityManager.find(Board.class, boardPath).getThreads());
	}
}
