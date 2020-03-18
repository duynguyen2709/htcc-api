package htcc.gateway.service.config.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class BaseDataSouceConfig {

    @Bean
    @Primary
    public DataSource adminDataSource(@Value("${spring.admin-datasource.url}") String url,
                                        @Value("${spring.admin-datasource.username}") String username,
                                        @Value("${spring.admin-datasource.password}") String password) {

        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(url);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);
        hikariDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariDataSource.setConnectionTestQuery("SELECT 1");
        addDataSourceProperty(hikariDataSource);
        return hikariDataSource;
    }

    @Bean
    public DataSource companyDataSource(@Value("${spring.company-datasource.url}") String url,
                                      @Value("${spring.company-datasource.username}") String username,
                                      @Value("${spring.company-datasource.password}") String password) {

        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(url);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);
        hikariDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariDataSource.setConnectionTestQuery("SELECT 1");
        addDataSourceProperty(hikariDataSource);

        return hikariDataSource;
    }


    /**
     * HikariCP recommend
     * @param hikariDataSource
     */
    private void addDataSourceProperty(HikariDataSource hikariDataSource) {
        hikariDataSource.addDataSourceProperty("dataSource.cachePrepStmts", "true");
        hikariDataSource.addDataSourceProperty("dataSource.prepStmtCacheSize", "250");
        hikariDataSource.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit", "2048");
        hikariDataSource.addDataSourceProperty("dataSource.useServerPrepStmts", "true");
        hikariDataSource.addDataSourceProperty("dataSource.useLocalSessionState", "true");
        hikariDataSource.addDataSourceProperty("dataSource.rewriteBatchedStatements", "true");
        hikariDataSource.addDataSourceProperty("dataSource.cacheResultSetMetadata", "true");
        hikariDataSource.addDataSourceProperty("dataSource.cacheServerConfiguration", "true");
        hikariDataSource.addDataSourceProperty("dataSource.dataSource.elideSetAutoCommits", "true");
        hikariDataSource.addDataSourceProperty("dataSource.maintainTimeStats", "false");

    }
}
