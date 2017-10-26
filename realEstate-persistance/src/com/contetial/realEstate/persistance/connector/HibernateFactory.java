package com.contetial.realEstate.persistance.connector;

import java.io.IOException;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.contetial.realEstate.persistance.exception.DataAccessLayerException;
import com.contetial.realEstate.utility.propReader.ReadConfigurations;

public class HibernateFactory {

	private static SessionFactory sessionFactory;
	//private static Log log = LogFactory.getLog(HibernateFactory.class);

	/**
	 * Constructs a new Singleton SessionFactory
	 * @return
	 * @throws HibernateException
	 * @throws IOException 
	 */
	protected static SessionFactory buildSessionFactory() throws DataAccessLayerException {
		if (sessionFactory != null) {
			closeFactory();
		}
		try {
			return configureSessionFactory();
		} catch (HibernateException | IOException e) {
			throw new DataAccessLayerException(e);
		}
	}

	/**
	 * Builds a SessionFactory, if it hasn't been already.
	 * @throws IOException 
	 */
	private static SessionFactory buildIfNeeded() throws DataAccessLayerException{
		if (sessionFactory != null) {
			return sessionFactory;
		}
		try {
			return configureSessionFactory();
		} catch (HibernateException | IOException e) {
			throw new DataAccessLayerException(e);
		}
	}
		
	protected static SessionFactory getSessionFactory() throws DataAccessLayerException {
		return buildIfNeeded();
	}

	protected static void closeFactory() {
		if (sessionFactory != null) {
			try {
				sessionFactory.close();
			} catch (HibernateException ignored) {
				//log.error("Couldn't close SessionFactory", ignored);
			}
		}
	}


	/**
	 *
	 * @return
	 * @throws HibernateException
	 * @throws IOException 
	 */
	private static SessionFactory configureSessionFactory() throws HibernateException, IOException {
		Configuration configuration = new Configuration().configure();
		configuration.configure("hibernate.cfg.xml");
		try {
			configuration.addProperties(
					ReadConfigurations.getInstance("./conf/application.properties").getProps());
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
			//Properties props = new Properties();
			
		}
		
		ServiceRegistry builder = new ServiceRegistryBuilder().
				applySettings(configuration.getProperties()).buildServiceRegistry();
	
		sessionFactory = configuration.buildSessionFactory(builder);
		return sessionFactory;
	}
}




