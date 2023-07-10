package jp.ken.tweet.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import jp.ken.tweet.dao.AnimeDao;
import jp.ken.tweet.dao.TweetModelDao;
import jp.ken.tweet.entity.Anime;
import jp.ken.tweet.entity.Tweets;
import jp.ken.tweet.model.DeleteAnimeModel;
import jp.ken.tweet.model.InputModel;
import jp.ken.tweet.model.LoginModel;
import jp.ken.tweet.model.NewAnimeModel;
import jp.ken.tweet.model.TweetModel;

@Controller
@SessionAttributes({"inputModel", "loginModel"})
public class LoginController {

	@Autowired
	private TweetModelDao tweetDao;

	@Autowired
	private AnimeDao animeDao;

	@ModelAttribute("loginModel")
	public LoginModel setloginModel() {
		return new LoginModel();
	}

	@ModelAttribute("inputModel")
	public InputModel setinputModel() {
		return new InputModel();
	}

	@ModelAttribute("deleteAnimeModel")
	public DeleteAnimeModel deleteAnimeModel() {
	    return new DeleteAnimeModel();
	}

	@RequestMapping(value = "tweet/login", method = RequestMethod.GET)
	public String loginForm() {
		return "login";
	}

	@RequestMapping(value = "tweet/login", method = RequestMethod.POST)
	public String loginpost(@Validated @ModelAttribute LoginModel loginModel, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "login";
		} else if (loginModel.getId().equals("MasterKey") && loginModel.getPassword().equals("123456")) {
			return "redirect:/mastertop";
		} else {
			model.addAttribute("message", "IDもしくはpasswordが間違えています");
			return "login";
		}
	}

	@ModelAttribute("newAnimeModel")
	public NewAnimeModel newAnimeModel() {
		return new NewAnimeModel();
	}

	@RequestMapping(value = "/mastertop", method = RequestMethod.GET)
	public String mastertop(Model model) {
		List<Anime> animeList = animeDao.getAnime();
		model.addAttribute("animeList", animeList);
		return "mastertop";
	}

	//---------------------アニメの登録を処理--------------------------
	@RequestMapping(value = "/mastertop", method = RequestMethod.POST, params = "btn=登録")
	public String masterOftop(@RequestParam("file") MultipartFile file,@RequestParam("btn") String btn, @ModelAttribute NewAnimeModel newAnimeModel,
			Model model) {
		NewAnimeModel newAnime = new NewAnimeModel();
		newAnime.setTitle(newAnimeModel.getTitle());
		newAnime.setProfile(newAnimeModel.getProfile());

		if(btn.equals("登録")) {
		if (!file.isEmpty()) {
            try {
                // デスクトップに保存するためのディレクトリのパスを指定
                String desktopPath = "C:\\Users\\tatu\\Documents\\java\\TweetSample-version3-\\src\\main\\webapp\\resources\\img\\";
                File directory = new File(desktopPath);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // ファイル名を生成
                String originalFilename = file.getOriginalFilename();
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String fileName = newAnimeModel.getTitle() + extension;

                // ファイルを保存
                File saveFile = new File(desktopPath + File.separator + fileName);
                FileCopyUtils.copy(file.getBytes(), saveFile);

                // 保存したファイルの相対パスをモデルに設定
                model.addAttribute("filePath", desktopPath + File.separator + fileName);

        		int numberOfRow = animeDao.insertAnime(newAnime);
        			if (numberOfRow == 0) {
        				model.addAttribute("message2", "登録に失敗しました。");
        			}
            	} catch (IOException e) {
            		e.printStackTrace();
            	}
			}
		}

		return "redirect:/mastertop";
	}
	//-----------------------------------------------------------------
	//---------------------アニメの削除を処理--------------------------

	@RequestMapping(value = "/mastertop", method = RequestMethod.POST, params = "btn=削除")
	public String masterOfTop(@RequestParam("btn") String btn,
			Model model, @ModelAttribute DeleteAnimeModel deleteAnimeModel) {

		if(btn.equals("削除")) {
			Anime animes = new Anime();
			animes.setTitle(deleteAnimeModel.getTitle());
			int numRow = animeDao.deleteAni(animes);
			if (numRow > 0) {
				model.addAttribute("message", "アニメリストが削除されました。");
			} else {
				model.addAttribute("message", "アニメリストの削除に失敗しました。");
			}

		//-----------------------------------------------------------------
		}
		return "redirect:/mastertop";
	}


	@RequestMapping(value = "/master/{title}", method = RequestMethod.GET)
	public String masterForm(@PathVariable String title, @ModelAttribute TweetModel tweetModel, Model model,  @ModelAttribute InputModel inputModel) {
		List<Tweets> tweetsList = tweetDao.getList(title);
		model.addAttribute("tweetsList", tweetsList);
		inputModel.setTitle(title);
		model.addAttribute("inputModel", inputModel);
		return "redirect:/master";
	}

	@RequestMapping(value="/master", method = RequestMethod.GET)
	public String create_tweet(@ModelAttribute TweetModel tweetModel, Model model, @ModelAttribute InputModel inputModel) {
		Tweets tweets = new Tweets();
		tweets.setTitle(inputModel.getTitle());
		List<Tweets> tweetsList = tweetDao.getByList(tweets);
		model.addAttribute("tweetsList", tweetsList);
		return "master";
	}

	@RequestMapping(value = "/master", method = RequestMethod.POST)
	public String loginpost(@RequestParam("id") int id, @ModelAttribute TweetModel tweetModel, Model model, @ModelAttribute InputModel inputModel) {
		Tweets tweets = new Tweets();
		tweets.setId(id);
		int numRow = tweetDao.delete(tweets);
		if (numRow > 0) {
			model.addAttribute("message", "ツイートが削除されました。");
		} else {
			model.addAttribute("message", "ツイートが削除に失敗しました。");
		}

		tweets.setTitle(inputModel.getTitle());
		List<Tweets> tweetsList = tweetDao.getByList(tweets);//ここなおす
		model.addAttribute("tweetsList", tweetsList);

		return "redirect:/master";
	}

}
