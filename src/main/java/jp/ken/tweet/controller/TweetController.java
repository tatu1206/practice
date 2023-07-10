package jp.ken.tweet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.ken.tweet.dao.AnimeDao;
import jp.ken.tweet.dao.TweetModelDao;
import jp.ken.tweet.entity.Anime;
import jp.ken.tweet.entity.Tweets;
import jp.ken.tweet.model.InputModel;
import jp.ken.tweet.model.TweetModel;

@SessionAttributes("inputModel")
@Controller
public class TweetController {
	@Autowired
	private TweetModelDao tweetDao;

	@Autowired
	private AnimeDao animedao;

	@ModelAttribute("tweetModel")
	public TweetModel setTweetModel() {
		return new TweetModel();
	}

	@ModelAttribute("inputModel")
	public InputModel setinputModel() {
		return new InputModel();
	}

	@RequestMapping(value="/{title}", method = RequestMethod.GET)
	public String cre_tweet(@PathVariable String title, @ModelAttribute TweetModel tweetModel, Model model,
			@ModelAttribute InputModel inputModel) {
		List<Tweets> tweetsList = tweetDao.getList(title);
		model.addAttribute("tweetsList", tweetsList);
		inputModel.setTitle(title);
		model.addAttribute("inputModel", inputModel);

		//-----レビュー画面に画像・タイトル・説明文の取得処理--------
		List<Anime> animesList = animedao.getAnimepro(title);
		model.addAttribute("animeList", animesList);
		//---------------------------------------------------------------

		return "redirect:/tweet";
	}

	@RequestMapping(value="/tweet", method = RequestMethod.GET)
		public String create_tweet(@ModelAttribute TweetModel tweetModel, Model model, 
				@ModelAttribute InputModel inputModel) {
			Tweets tweets = new Tweets();
			tweets.setTitle(inputModel.getTitle());
			List<Tweets> tweetsList = tweetDao.getByList(tweets);
			model.addAttribute("tweetsList", tweetsList);

			//-----レビュー画面に画像・タイトル・説明文の取得処理--------
			String title = tweets.getTitle();
			List<Anime> animesList = animedao.getAnimepro(title);
			model.addAttribute("animeList", animesList);
			//---------------------------------------------------------------

			return "tweet";
		}

	@RequestMapping(value="/tweet", method = RequestMethod.POST)
	public String get_tweet(@Validated @ModelAttribute TweetModel tweetModel, @ModelAttribute InputModel inputModel, BindingResult result, Model model) {
		Tweets tweets = new Tweets();//setNameが使えるように
		tweets.setTitle(inputModel.getTitle());
		List<Tweets> tweetsList = tweetDao.getByList(tweets);

		model.addAttribute("tweetsList", tweetsList);

		if(result.hasErrors()) {
			return "tweet";
		}

		if(tweetsList.isEmpty()) {
		model.addAttribute("message", "現在ツイートはありません。");
		}else {
			model.addAttribute("tweetsList", tweetsList);
		}

		tweets.setName(tweetModel.getName());
		tweets.setComment(tweetModel.getComment());


		int numberOfRow = tweetDao.insert(tweets);
		if(numberOfRow == 0) {
			model.addAttribute("message2", "登録に失敗しました。");
		}

		//-----レビュー画面に画像・タイトル・説明文の取得処理--------
		List<Anime> animesList = animedao.getAnimepro(tweets.getTitle());
		model.addAttribute("animeList", animesList);
		//---------------------------------------------------------------

		return "redirect:/tweet";
	}
}
