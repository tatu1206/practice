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

import jp.ken.tweet.entity.Anime;
import jp.ken.tweet.entity.Tweets;
import jp.ken.tweet.model.NewAnimeModel;

@Component
public class AnimeDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private PlatformTransactionManager transactionManager;

	private RowMapper<Anime> animeMapper = new BeanPropertyRowMapper<Anime>(Anime.class);

	//画像を保存するディレクトリ
	private String imgFolder ="resources\\img\\";

	public List<Anime> getAnime(){
		String sql = "select * from profile";
		List<Anime> animeList = jdbcTemplate.query(sql, animeMapper);
		return animeList;
	}

	public List<Anime> getAnimepro(String title){
		String sql = "select * from profile where title=?";
		Object[] parameter = {title};
		List<Anime> animeList = jdbcTemplate.query(sql, parameter, animeMapper);
		return animeList;
	}

	public List<Anime> getAnimeProfile(Tweets tweets){
		String sql = "select * from profile where title=?";
		Object[] parameter = {tweets};
		List<Anime> animeList = jdbcTemplate.query(sql, parameter, animeMapper);
		return animeList;
	}

	public int insertAnime(NewAnimeModel newAnime) {
		String sql ="INSERT INTO profile (title, profile,file) VALUE(?,?,?)";
		String filePath = imgFolder + newAnime.getTitle() + ".jpg"; // ファイルの絶対パスを取得
		Object[] parameter = {newAnime.getTitle(),newAnime.getProfile(),filePath};
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

	public int deleteAni(Anime anime){

		 // 関連するツイートを先に削除
        String deleteTweetsSql = "DELETE FROM tweet WHERE title=?";
        Object[] tweetParameter = { anime.getTitle() };
        jdbcTemplate.update(deleteTweetsSql, tweetParameter);

		String sql = "Delete from profile where title=?";
		int numRow = 0;
		Object[] parameter = {anime.getTitle()};
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
