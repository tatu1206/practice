package jp.ken.tweet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.ken.tweet.dao.AnimeDao;
import jp.ken.tweet.entity.Anime;
import jp.ken.tweet.model.InputModel;
@SessionAttributes("inputModel")
@Controller
public class AnimeTopController {

	@Autowired
	private AnimeDao animeDao;

	@ModelAttribute("inputModel")
	public InputModel setinputModel() {
		return new InputModel();
	}

	@RequestMapping(value="/top", method=RequestMethod.GET)
	public String top(Model model) {
		List<Anime> animeList = animeDao.getAnime();
		model.addAttribute("animeList", animeList);

		return "anime_top";
	}

	@RequestMapping(value="/top", method=RequestMethod.POST)
	public String tgetop(@ModelAttribute InputModel inputModel, Model model) {
		List<Anime> animeList = animeDao.getAnime();
		model.addAttribute("animeList", animeList);

		return "tweet";
	}

}
