package evacuatzia_proj;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.apache.tomcat.dbcp.dbcp.BasicDataSourceFactory;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Environment;
import org.hibernate.service.jdbc.connections.internal.ConnectionProviderInitiator;
import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *	The following is based on code taken from http://www.mkyong.com/hibernate/how-to-configure-dbcp-connection-pool-in-hibernate/
 *	For using tomcat-dbcp connection pool with Hibernate.
 */
public class DBCPConnectionProvider implements ConnectionProvider {
	/**
	 * 
	 */
	private static final long serialVersionUID = -470565708438833355L;
	private static final Logger log = LoggerFactory.getLogger(DBCPConnectionProvider.class);
	private static final String PREFIX = "hibernate.dbcp.";
	private BasicDataSource ds;
 
	// Old Environment property for backward-compatibility (property removed in
	// Hibernate3)
	private static final String DBCP_PS_MAXACTIVE = "hibernate.dbcp.ps.maxActive";
 
	// Property doesn't exists in Hibernate2
	private static final String AUTOCOMMIT = "hibernate.connection.autocommit";
 
	public void configure(Properties props) throws HibernateException {
		try {
 log.info("GILAD1");
			log.debug("Configure DBCPConnectionProvider");
 
			// DBCP properties used to create the BasicDataSource
			Properties dbcpProperties = new Properties();
 
			// DriverClass & url
			String jdbcDriverClass = props.getProperty(Environment.DRIVER);
			String jdbcUrl = props.getProperty(Environment.URL);
			dbcpProperties.put("driverClassName", jdbcDriverClass);
			dbcpProperties.put("url", jdbcUrl);
 
			// Username / password
			String username = props.getProperty(Environment.USER);
			String password = props.getProperty(Environment.PASS);
			dbcpProperties.put("username", username);
			dbcpProperties.put("password", password);
 
			// Isolation level
			String isolationLevel = props.getProperty(Environment.ISOLATION);
			if ((isolationLevel != null)
					&& (isolationLevel.trim().length() > 0)) {
				dbcpProperties.put("defaultTransactionIsolation",
						isolationLevel);
			}
 
			// Turn off autocommit (unless autocommit property is set)
			String autocommit = props.getProperty(AUTOCOMMIT);
			if ((autocommit != null) && (autocommit.trim().length() > 0)) {
				dbcpProperties.put("defaultAutoCommit", autocommit);
			} else {
				dbcpProperties.put("defaultAutoCommit",
						String.valueOf(Boolean.FALSE));
			}
 
			// Pool size
			String poolSize = props.getProperty(Environment.POOL_SIZE);
			if ((poolSize != null) && (poolSize.trim().length() > 0)
					&& (Integer.parseInt(poolSize) > 0)) {
				dbcpProperties.put("maxActive", poolSize);
			}
 log.info("GILAD1");
			// Copy all "driver" properties into "connectionProperties"
			Properties driverProps = ConnectionProviderInitiator
					.getConnectionProperties(props);
log.info("GILAD2");
			if (driverProps.size() > 0) {
				StringBuffer connectionProperties = new StringBuffer();
				for (Iterator iter = driverProps.entrySet().iterator(); iter
						.hasNext();) {
					Map.Entry entry = (Map.Entry) iter.next();
					String key = (String) entry.getKey();
					String value = (String) entry.getValue();
					connectionProperties.append(key).append('=').append(value);
					if (iter.hasNext()) {
						connectionProperties.append(';');
					}
				}
				dbcpProperties.put("connectionProperties",
						connectionProperties.toString());
			}
 
			// Copy all DBCP properties removing the prefix
			for (Iterator iter = props.entrySet().iterator(); iter.hasNext();) {
				Map.Entry entry = (Map.Entry) iter.next();
				String key = (String) entry.getKey();
				if (key.startsWith(PREFIX)) {
					String property = key.substring(PREFIX.length());
					String value = (String) entry.getValue();
					dbcpProperties.put(property, value);
				}
			}
 
			// Backward-compatibility
			if (props.getProperty(DBCP_PS_MAXACTIVE) != null) {
				dbcpProperties.put("poolPreparedStatements",
						String.valueOf(Boolean.TRUE));
				dbcpProperties.put("maxOpenPreparedStatements",
						props.getProperty(DBCP_PS_MAXACTIVE));
			}
 
			// Some debug info
			if (log.isDebugEnabled()) {
				StringWriter sw = new StringWriter();
				dbcpProperties.list(new PrintWriter(sw, true));
				log.debug(sw.toString());
			}
 
			// Let the factory create the pool
			ds = (BasicDataSource) BasicDataSourceFactory
					.createDataSource(dbcpProperties);
 
			// The BasicDataSource has lazy initialization
			// borrowing a connection will start the DataSource
			// and make sure it is configured correctly.
			Connection conn = ds.getConnection();
			conn.close();
 
			// Log pool statistics before continuing.
			logStatistics();
		} catch (Exception e) {
			String message = "Could not create a DBCP pool";
			log.error(message, e);
			if (ds != null) {
				try {
					ds.close();
				} catch (Exception e2) {
					// ignore
				}
				ds = null;
			}
			throw new HibernateException(message, e);
		}
		log.debug("Configure DBCPConnectionProvider complete");
	}
 
	public Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} finally {
			logStatistics();
		}
		return conn;
	}
 
	public void closeConnection(Connection conn) throws SQLException {
		try {
			conn.close();
		} finally {
			logStatistics();
		}
	}
 
	public void close() throws HibernateException {
		log.debug("Close DBCPConnectionProvider");
		logStatistics();
		try {
			if (ds != null) {
				ds.close();
				ds = null;
			} else {
				log.warn("Cannot close DBCP pool (not initialized)");
			}
		} catch (Exception e) {
			throw new HibernateException("Could not close DBCP pool", e);
		}
		log.debug("Close DBCPConnectionProvider complete");
	}
 
	protected void logStatistics() {
		if (log.isInfoEnabled()) {
			log.info("active: " + ds.getNumActive() + " (max: "
					+ ds.getMaxActive() + ")   " + "idle: " + ds.getNumIdle()
					+ "(max: " + ds.getMaxIdle() + ")");
		}
	}
 
	public boolean supportsAggressiveRelease() {
		return false;
	}

	@Override
	public boolean isUnwrappableAs(Class unwrapType) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> unwrapType) {
		// TODO Auto-generated method stub
		return null;
	}
}