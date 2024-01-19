package com.ftn.PrviMavenVebProjekat.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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

import com.ftn.PrviMavenVebProjekat.model.Destinacija;
import com.ftn.PrviMavenVebProjekat.service.DestinacijaService;
import com.ftn.PrviMavenVebProjekat.service.PrevoznoSredstvoService;
import com.ftn.PrviMavenVebProjekat.model.Kategorija;
import com.ftn.PrviMavenVebProjekat.model.PrevoznoSredstvo;
import com.ftn.PrviMavenVebProjekat.model.Putovanje;
import com.ftn.PrviMavenVebProjekat.model.SmestajnaJedinica;
import com.ftn.PrviMavenVebProjekat.service.PutovanjeService;
import com.ftn.PrviMavenVebProjekat.service.SmestajnaJedinicaService;


@Controller
@RequestMapping(value="/putovanja")
public class PutovanjeController implements ServletContextAware {
	
	@Autowired
	private ServletContext servletContext;
	private  String bURL;
	
	@Autowired
	private PutovanjeService putovanjeService;
	
	@Autowired
	private DestinacijaService destinacijaService;
	
	@Autowired
	private SmestajnaJedinicaService smestajnaJedinicaService;
	
	@Autowired
	private PrevoznoSredstvoService prevoznoSredstvoService;
	
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
	    List<Destinacija> destinacije = putovanja.stream()
	            .map(putovanje -> destinacijaService.findOne(putovanje.getIdDestinacije()))
	            .collect(Collectors.toList());
	    List<SmestajnaJedinica> smestajneJedinice = putovanja.stream()
	    		.map(putovanje -> smestajnaJedinicaService.findOne(putovanje.getIdSmestajnaJedinica()))
	    		.collect(Collectors.toList());
	    List<PrevoznoSredstvo> prevoznaSredstva = putovanja.stream()
	    		.map(putovanje -> prevoznoSredstvoService.findOne(putovanje.getIdPrevoznoSredstvo()))
	    		.collect(Collectors.toList());
		ModelAndView result = new ModelAndView("putovanja");
		result.addObject("putovanja", putovanja);
		result.addObject("destinacije", destinacije);
		result.addObject("smestajneJedinice", smestajneJedinice);
		result.addObject("prevoznaSredstva", prevoznaSredstva);
		return result;
	}
	
	@GetMapping(value="/add")
	public String dodajPutovanje(HttpServletResponse response) throws IOException {
		return "dodavanjePutovanja";
	}
	
	
	@PostMapping(value="/add")
	public void dodajPutovanje(HttpServletResponse response,@RequestParam Long id,@RequestParam String sifraPutovanja,
			@RequestParam Long idDestinacije,
			@RequestParam Long idPrevoznoSredstvo,@RequestParam Long idSmestajnaJedinica,
			@RequestParam Kategorija kategorija, 
			@RequestParam String datumPolaska, @RequestParam String datumPovratka,
			@RequestParam int brojNocenja, @RequestParam double cena) throws IOException {
		Putovanje putovanje = new Putovanje(
				sifraPutovanja,
				idDestinacije,
				idPrevoznoSredstvo,
				idSmestajnaJedinica,
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
