package com.contetial.realEstate.persistance.connector;

import java.io.IOException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.contetial.realEstate.utility.propReader.ReadConfigurations;

public class HibernateUtil {

	private static ServiceRegistry serviceRegistry;
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	private static SessionFactory sessionFactory;

	private static SessionFactory configureSessionFactory() throws IOException, HibernateException {
		try {

			Configuration configuration = new Configuration().configure();
			configuration.configure("hibernate.cfg.xml");
			try {
				configuration.addProperties(
						ReadConfigurations.getInstance("./conf/application.properties").getProps());
			} catch (IOException ioe) {
				ioe.printStackTrace();
				throw ioe;				
			}
			
			serviceRegistry = new ServiceRegistryBuilder().
					applySettings(configuration.getProperties()).buildServiceRegistry();
		
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);			
		} catch (HibernateException e) {
			System.out.append("** Exception in SessionFactory **");
			e.printStackTrace();
			throw e;
		}
		return sessionFactory;
	}


	static {
		try {
			sessionFactory = configureSessionFactory();
		} catch (Exception e) {
			System.err.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
	}

	private HibernateUtil() {
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static Session getSession() throws HibernateException {
		Session session = threadLocal.get();

		if (session == null || !session.isOpen()) {
			if (sessionFactory == null) {
				rebuildSessionFactory();
			}
			session = (sessionFactory != null) ? sessionFactory.openSession() : null;
			threadLocal.set(session);
		}

		return session;
	}

	public static void rebuildSessionFactory() {
		try {
			sessionFactory = configureSessionFactory();
		} catch (Exception e) {
			System.err.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
	}

	public static void closeSession() throws HibernateException {
		Session session = (Session) threadLocal.get();
		threadLocal.set(null);

		if (session != null) {
			session.close();
		}
	}
}