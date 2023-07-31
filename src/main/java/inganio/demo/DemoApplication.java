package inganio.demo;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory factory) {
	    return new SqlSessionTemplate(factory);
	}
	
	@Bean
	public SqlSessionFactory SqlSessionFactory(DataSource ds) throws Exception {

		SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
		factory.setDataSource(ds);
		factory.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:/mybatis/mybatis-config.xml"));;
		factory.setMapperLocations(
		        new PathMatchingResourcePatternResolver().getResources("classpath:/mappers/*.xml")
		        );
		JdbcTransactionFactory trn= new JdbcTransactionFactory();
        trn.newTransaction(ds, null, false);  //트랜젝션 설정에서 기본 auto commit 값을 false로 해 줍니다.
        factory.setTransactionFactory(trn);
        
		return factory.getObject();
	}
	
	//json 파싱
	@Bean
    MappingJackson2JsonView jsonView(){
        return new MappingJackson2JsonView();
    }
}
