package jp.ken.tweet.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import jp.ken.tweet.entity.Tweets;

@Component
public class TweetModelDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private PlatformTransactionManager transactionManager;

	private RowMapper<Tweets> tweetsMapper = new BeanPropertyRowMapper<Tweets>(Tweets.class);

	public List<Tweets> getList(String title){
		String sql = "select * from tweet where title=?";
		Object[] parameter= {title};
		List<Tweets> tweetsList = jdbcTemplate.query(sql, parameter, tweetsMapper);
		return tweetsList;
	}

	public List<Tweets> getByList(Tweets tweet){
		String sql = "select * from tweet where title=?";
		Object[] parameter= {tweet.getTitle()};
		List<Tweets> tweetsList = jdbcTemplate.query(sql, parameter, tweetsMapper);
		return tweetsList;
	}

	public int insert(Tweets tweets) {
		String sql ="INSERT INTO tweet (title,name, comment) VALUE(?,?,?)";
		Object[] parameter = {tweets.getTitle(),tweets.getName(),tweets.getComment()};
		//トランザクションの状態を保持する
		TransactionStatus transactionStatus = null;
		//トランザクションの設定を管理する
		DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		int numberOfRow = 0;
		try {
			//トランザクションの開始
			transactionStatus = transactionManager.getTransaction(transactionDefinition);
			numberOfRow = jdbcTemplate.update(sql, parameter);
			//トランザクションをコミットさせ変更を確定する
			transactionManager.commit(transactionStatus);
		}catch(DataAccessException e) {
			e.printStackTrace();
			transactionManager.rollback(transactionStatus);
		}catch(TransactionException e) {
			e.printStackTrace();
			if(transactionStatus != null) {
			transactionManager.rollback(transactionStatus);
			}
		}
		return numberOfRow;
	}

	public int delete(Tweets tweets){
		String sql = "Delete from tweet where id=?";
		int numRow = 0;
		Object[] parameter = {tweets.getId()};
		TransactionStatus transactionStatus = null;
		DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		try {
			transactionStatus = transactionManager.getTransaction(transactionDefinition);
			numRow = jdbcTemplate.update(sql, parameter);
			transactionManager.commit(transactionStatus);
		}catch(DataAccessException e) {
			e.printStackTrace();
			transactionManager.rollback(transactionStatus);
		}catch(TransactionException e) {
			e.printStackTrace();
			if(transactionStatus != null) {
			transactionManager.rollback(transactionStatus);
			}
		}
		return numRow;
	}
}
