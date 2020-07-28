package org.Lunaros.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import org.Lunaros.global.GlobalConstant;
import org.Lunaros.util.DatabaseType;
import org.Lunaros.util.StringUtils;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


/**
 * Created by feiliu206363 on 2017/3/27.
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "org.Lunaros.framework.api.mapper.graph", sqlSessionFactoryRef = "graphSqlSessionFactory")
public class GraphConfiguration {

    @Bean(initMethod = "init", destroyMethod = "close", name = "graphDataSource")
    public DataSource graphDataSource() throws Exception {
        String mysqlHost = System.getenv(GlobalConstant.GRAPH_MYSQL_HOST);
        if (StringUtils.isBlank(mysqlHost)) {
            mysqlHost = System.getenv(GlobalConstant.LunarOS_MYSQL_HOST);
        }
        String mysqlPort = System.getenv(GlobalConstant.GRAPH_MYSQL_PORT);
        if (StringUtils.isBlank(mysqlPort)) {
            mysqlPort = System.getenv(GlobalConstant.LunarOS_MYSQL_PORT);
        }
        String mysqlUsername = System.getenv(GlobalConstant.GRAPH_MYSQL_USERNAME);
        if (StringUtils.isBlank(mysqlUsername)) {
            mysqlUsername = System.getenv(GlobalConstant.LunarOS_MYSQL_USERNAME);
        }
        String mysqlPassword = System.getenv(GlobalConstant.GRAPH_MYSQL_PASSWORD);
        if (StringUtils.isBlank(mysqlPassword)) {
            mysqlPassword = System.getenv(GlobalConstant.LunarOS_MYSQL_PASSWORD);
        }
        String mysqlDB = System.getenv(GlobalConstant.GRAPH_MYSQL_DB);
        if (StringUtils.isBlank(mysqlDB)) {
            mysqlDB = "graph";
        }

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://" + mysqlHost + ":" + mysqlPort + "/" + mysqlDB
                + "?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true");
        dataSource.setPassword(mysqlPassword);
        dataSource.setUsername(mysqlUsername);
        dataSource.setMaxActive(80);
        dataSource.setMaxWait(50000);
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setMinEvictableIdleTimeMillis(20000);
        dataSource.setTimeBetweenEvictionRunsMillis(20000);
        dataSource.setInitialSize(5);
        dataSource.setMinIdle(1);
        dataSource.setFilters("stat,wall,log4j");
        return dataSource;
    }

    @Bean(name = "graphTransactionManager")
    public DataSourceTransactionManager graphTransactionManager() throws Exception {
        return new DataSourceTransactionManager(graphDataSource());
    }

    @Bean(name = "graphSqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactory(@Qualifier("graphDataSource") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        GlobalConstant.DATABASETYPE = DatabaseType.MYSQL;
        return sessionFactory;
    }
}
