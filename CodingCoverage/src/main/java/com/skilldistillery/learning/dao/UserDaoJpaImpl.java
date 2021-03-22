package com.skilldistillery.learning.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.skilldistillery.learning.entities.User;

@Service
@Transactional
public class UserDaoJpaImpl implements UserDAO {

	@PersistenceContext
	private EntityManager em;

	// Get
	@Override
	public User findById(int userId) {

		return em.find(User.class, userId);
	}

	@Override
	public List<User> findAll() {
		String getAllUsers = "SELECT u FROM User u";

		List<User> allUsers = em.createQuery(getAllUsers, User.class).getResultList();

		return allUsers;
	}

	// Create
	@Override
	public User createUser(User newUser) {
		em.persist(newUser);
		em.flush();

		return newUser;
	}

	// Update
	@Override
	public User updateUser(User updatedUser) {

		return em.merge(updatedUser);
	}

	// Deleted
	@Override
	public boolean deleteUser(User targetUser) {
		boolean deleted = false;

		if (targetUser != null) {
			em.remove(targetUser);
		}
		deleted = !em.contains(targetUser);

		return deleted;
	}
	
	// archive 
	@Override
	public boolean archiveUser(User targetUser) {
		User tempUser = null;
		targetUser.setEnabled(false);
		tempUser = em.merge(targetUser);
		
		if (tempUser.getEnabled()) {
			return false;
		}
		
		return true;
	}

}
