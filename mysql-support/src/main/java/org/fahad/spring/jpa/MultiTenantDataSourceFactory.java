package org.fahad.spring.jpa;

//import com.google.common.util.concurrent.Striped;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

@Component
public class MultiTenantDataSourceFactory {
    public static final String MYSQL_DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    public static final Integer DB_CONNECT_TIMEOUT_IN_SECONDS = 10;
    public static final Integer DB_IDLE_TIMEOUT_IN_SECONDS = 150;
    public static final int DEFAULT_MIN_CONNECTIONS = 3;
    public static final int DEFAULT_MAX_CONNECTIONS = 32;
    public static final String PING_SELECT_1 = "/* ping */ SELECT 1";
    public static final String PROPS_STRING =
            "zeroDateTimeBehavior=convertToNull&cachePrepStmts=true&prepStmtCacheSize=512&useServerPreparedStmts=false&useEncoding=true&characterEncoding=UTF-8";
    public static final String SSL_STRING =
            "verifyServerCertificate=false&useSSL=true&requireSSL=true";
    public static final int LOCK_TIMEOUT = 3; // in mins
//    private final Striped<Lock> lockStriped = Striped.lock(1000);

    private String createKeyForMysqlDataSource(String moduleName, String tenantId) {
        return new StringBuilder(moduleName).append("_").append(tenantId).toString();
    }

    public DataSource getDatasourceForTenant(String moduleName, String tenantId) {
        String key = createKeyForMysqlDataSource(moduleName, tenantId);
        return createDataSource(moduleName,tenantId);
    }

    private DataSource createDataSource(String moduleName, String tenantId) {
        StringBuilder jdbcURLBuilder = new StringBuilder("jdbc:mysql://");
        String host = System.getenv("DB_HOST");
        jdbcURLBuilder
                .append(host)
                .append(":")
            .append(System.getenv("DB_PORT"))
            .append("/")
            .append(moduleName+"_"+tenantId)
                .append("?");

        jdbcURLBuilder.append(PROPS_STRING);

        String poolName = "[template] - [partner] - [partner] - [" + moduleName+"_"+tenantId + "]";
        return makeDS(
                "root", "", jdbcURLBuilder.toString(), poolName);
    }

    private static DataSource makeDS(
            String dbUser, String dbPassword, String jdbcURL, String poolName) {
        int minimumConnections = DEFAULT_MIN_CONNECTIONS;
        int maximumConnections = DEFAULT_MAX_CONNECTIONS;
        int idleTimeout = DB_IDLE_TIMEOUT_IN_SECONDS;
        return mapHikari(
                dbUser, dbPassword, jdbcURL, poolName, minimumConnections, maximumConnections, idleTimeout);
    }
    private static DataSource mapHikari(
            String dbUser,
            String dbPassword,
            String jdbcURL,
            String poolName,
            int minimumConnections,
            int maximumConnections,
            int idleTimeout) {
        HikariDataSource ds = new HikariDataSource();
        ds.setPoolName(poolName);
        ds.setDriverClassName(MYSQL_DRIVER_CLASS_NAME);
        ds.setJdbcUrl(jdbcURL);
        ds.setUsername(dbUser);
        ds.setPassword(dbPassword);
        ds.setMaximumPoolSize(maximumConnections);
        ds.setMinimumIdle(minimumConnections); // kind of minimum pool size
        ds.setConnectionTimeout(TimeUnit.SECONDS.toMillis(DB_CONNECT_TIMEOUT_IN_SECONDS));
        ds.setIdleTimeout(TimeUnit.SECONDS.toMillis(idleTimeout));
        ds.setConnectionTestQuery(PING_SELECT_1);
        //        ds.setJdbc4ConnectionTest(true);
        //        ds.setConnectionCustomizer(new DBConnectionHook());
        ds.setLeakDetectionThreshold(TimeUnit.SECONDS.toMillis(10)); // 10 second for testing
        ds.setMaxLifetime(TimeUnit.MINUTES.toMillis(30l)); // setting what the default is
        return ds;
    }

}
