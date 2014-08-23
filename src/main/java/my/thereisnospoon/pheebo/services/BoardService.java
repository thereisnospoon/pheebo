package my.thereisnospoon.pheebo.services;

import my.thereisnospoon.pheebo.persistence.model.Board;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class BoardService {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<Board> getAllBoards() {
		return entityManager.createQuery("select b from Board b order by path").getResultList();
	}

	public Board getBoard(String boardPath) {
		return entityManager.find(Board.class, boardPath);
	}
}
