package com.modesteam.urutau.interceptor;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.annotation.ReloadUser;
import com.modesteam.urutau.model.UrutaUser;

import br.com.caelum.vraptor.AfterCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.interceptor.AcceptsWithAnnotations;

/**
 * This should create a self {@link EntityManager} to reload 
 * actual user logged. 
 */
@Intercepts
@AcceptsWithAnnotations(ReloadUser.class)
public class ReloaderInterceptor {
	private final EntityManagerFactory factory;

	private final UserSession session;

	private EntityManager manager;
	
	public ReloaderInterceptor() {
		this(null, null);
	}
	
	@Inject
	public ReloaderInterceptor(EntityManagerFactory factory, UserSession session) {
		this.factory = factory;
		this.session = session;
	}

	@AfterCall
	public void intercepts() {
		manager = factory.createEntityManager();
		EntityTransaction transaction = null;
		
		try {
			transaction = manager.getTransaction();
			transaction.begin();

			reloadUserLogged();

			transaction.commit();
		} finally {
			// Transaction should be closed after commit
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
		}
	}

	/**
	 * Reload actual userLogged 
	 */
	private void reloadUserLogged() {
		UrutaUser reloadedUser = manager.find(UrutaUser.class, session.getUserLogged().getUserID());
		session.login(reloadedUser);
	}
}
