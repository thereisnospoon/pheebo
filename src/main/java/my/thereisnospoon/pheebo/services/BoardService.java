package my.thereisnospoon.pheebo.services;

import my.thereisnospoon.pheebo.persistence.model.Board;
import my.thereisnospoon.pheebo.persistence.model.Thread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class BoardService {

	public static final int THREADS_PER_PAGE = 4;
	public static final int MAX_PAGES = 1;

	private static final Logger log = LoggerFactory.getLogger(BoardService.class);

	private static final String threadsToDeleteQuery = "select t from Thread t where t.threadId not in (:actualThreadIds)";
	private static final String getNonObsoleteThreadsQuery = "select thread_id from imgboard.threads order by last_response_date desc limit :maxSize";

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<Board> getAllBoards() {
		return entityManager.createQuery("select b from Board b order by path").getResultList();
	}

	public Board getBoard(String boardPath) {
		return entityManager.find(Board.class, boardPath);
	}

	@SuppressWarnings("unchecked")
	@Scheduled(fixedDelay = 60 * 1000)
	public void deleteObsoleteThreads() {

		List<Object> resultList = entityManager.createNativeQuery(getNonObsoleteThreadsQuery).setParameter("maxSize", THREADS_PER_PAGE * MAX_PAGES).getResultList();

		log.debug("Result list: {}", resultList);

		List<Long> ids = resultList.stream().map(o -> Long.valueOf(o.toString())).collect(Collectors.toList());
		for (Object o : entityManager.createQuery(threadsToDeleteQuery).setParameter("actualThreadIds", ids).getResultList()) {

			Thread thread = (Thread) o;

			log.debug("Thread {} will be deleted as obsolete", thread.getThreadId());

			entityManager.remove(thread);
		}
	}

	/**
	 * Getter to use value within jsp
	 */
	public int getMaxPages() {
		return MAX_PAGES;
	}
}
