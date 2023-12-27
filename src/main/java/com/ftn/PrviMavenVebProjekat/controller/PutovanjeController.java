package com.ftn.PrviMavenVebProjekat.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.PrviMavenVebProjekat.model.Kategorija;
import com.ftn.PrviMavenVebProjekat.model.Putovanje;
import com.ftn.PrviMavenVebProjekat.service.PutovanjeService;


@Controller
@RequestMapping(value="/putovanja")
public class PutovanjeController implements ServletContextAware {
	
	@Autowired
	private ServletContext servletContext;
	private  String bURL;
	
	@Autowired
	private PutovanjeService putovanjeService;
	
	@PostConstruct
	public void init() {	
		bURL = servletContext.getContextPath()+"/";
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	} 
	
	@GetMapping()
	@ResponseBody
	public ModelAndView index() {
		
		List<Putovanje> putovanja = putovanjeService.findAll();
		ModelAndView result = new ModelAndView("putovanja");
		result.addObject("putovanja", putovanja);
		return result;
	}
	
	@GetMapping(value="/add")
	public String dodajPutovanje(HttpServletResponse response) throws IOException {
		return "dodavanjePutovanja";
	}
	
	
	@PostMapping(value="/add")
	public void dodajPutovanje(HttpServletResponse response,@RequestParam Long id,@RequestParam String sifraPutovanja,
			@RequestParam Long idDestinacije,
			@RequestParam String prevoznoSredstvo,@RequestParam String smestajnaJedinica,
			@RequestParam Kategorija kategorija, 
			@RequestParam String datumPolaska, @RequestParam String datumPovratka,
			@RequestParam int brojNocenja, @RequestParam double cena) throws IOException {
		Putovanje putovanje = new Putovanje(
				sifraPutovanja,
				idDestinacije,
				prevoznoSredstvo,
				smestajnaJedinica,
				kategorija,
				datumPolaska,
				datumPovratka,
				brojNocenja,
				cena);
		
		putovanjeService.save(putovanje);
		response.sendRedirect(bURL);
	}
	
	@GetMapping(value="/details")
	public ModelAndView details(@RequestParam Long id, HttpServletResponse response) throws IOException {

		Putovanje putovanje = putovanjeService.findOne(id);
		
		ModelAndView result = new ModelAndView("putovanje");
		result.addObject("putovanje", putovanje);
		return result;
	}
	
	@PostMapping(value="/delete")
	public void delete(@RequestParam Long id, HttpServletResponse response) throws IOException {
		putovanjeService.delete(id);
		response.sendRedirect(bURL+"putovanja");
	}
	
	@PostMapping(value="/edit")
	public void edit(@ModelAttribute Putovanje putovanjeEdited , HttpServletResponse response) throws IOException {	
		putovanjeService.update(putovanjeEdited);
		response.sendRedirect(bURL+"putovanja");
	}

}
