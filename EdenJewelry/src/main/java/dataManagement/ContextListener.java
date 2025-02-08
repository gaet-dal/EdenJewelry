package main.java.dataManagement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.util.logging.Logger;

@WebListener
public class ContextListener implements ServletContextListener {
    private static Logger logger = Logger.getLogger(ContextListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();

        DataSource ds = null;

        try{
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");

            ds = (DataSource) envCtx.lookup("jdbc/MyDataSource");

            sc.setAttribute("MyDataSource", ds);
            logger.info("Datasource creato "+ ds.toString());
        }catch(NamingException e){
            e.printStackTrace();
            logger.warning("Errore durante il lookup");
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Contesto distrutto");
    }
}
