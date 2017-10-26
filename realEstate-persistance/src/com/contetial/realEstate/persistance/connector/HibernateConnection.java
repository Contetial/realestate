/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contetial.realEstate.persistance.connector;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateConnection {

    private static Session session;

    public static Session getSession() {
    	if(session == null
    			|| !session.isConnected()){
    		session=openSession();
    	}
        return session;
    }
    
    private static Session openSession() throws HibernateException {       
        return HibernateFactory.getSessionFactory().openSession();
    }
    
    public static void close(Session session) {
        if (session != null) {
            try {
                session.close();
            } catch (HibernateException ignored) {
                //log.error("Couldn't close Session", ignored);
            }
        }
    }

    public static void rollback(Transaction tx) {
        try {
            if (tx != null) {
                tx.rollback();
            }
        } catch (HibernateException ignored) {
            //log.error("Couldn't rollback Transaction", ignored);
        }
    }
}
