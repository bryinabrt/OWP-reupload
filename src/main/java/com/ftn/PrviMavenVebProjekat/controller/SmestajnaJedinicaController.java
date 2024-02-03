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
import com.ftn.PrviMavenVebProjekat.model.SmestajnaJedinica;
import com.ftn.PrviMavenVebProjekat.model.TipJedinice;
import com.ftn.PrviMavenVebProjekat.service.DestinacijaService;
import com.ftn.PrviMavenVebProjekat.service.SmestajnaJedinicaService;
import com.ftn.PrviMavenVebProjekat.service.TipJediniceService;

@Controller
@RequestMapping(value="/smestajneJedinice")
public class SmestajnaJedinicaController implements ServletContextAware {
	
	@Autowired
	private ServletContext servletContext;
	private  String bURL;
	
	@Autowired
	private SmestajnaJedinicaService smestajnaJedinicaService;
	
	@Autowired
	private DestinacijaService destinacijaService;
	
	@Autowired
	private TipJediniceService tipJediniceService;
	
	
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
		
		List<SmestajnaJedinica> smestajneJedinice = smestajnaJedinicaService.findAll();
	    List<TipJedinice> tipJedinica = smestajneJedinice.stream()
	            .map(smestajnaJedinica -> tipJediniceService.findOne(smestajnaJedinica.getIdTipJedinice()))
	            .collect(Collectors.toList());
	    List<Destinacija> destinacije = smestajneJedinice.stream()
	            .map(smestajnaJedinica -> destinacijaService.findOne(smestajnaJedinica.getIdDestinacijeSmestaja()))
	            .collect(Collectors.toList());
		ModelAndView result = new ModelAndView("smestajneJedinice");
		result.addObject("smestajneJedinice", smestajneJedinice);
		result.addObject("tipJedinica", tipJedinica);
		result.addObject("destinacije", destinacije);
		return result;
	}
	
	@GetMapping(value="/add")
	@ResponseBody
	public ModelAndView dodajSmestajnuJedinicu(){
		List<TipJedinice> tipJedinica = tipJediniceService.findAll();
		
		List<Destinacija> destinacije = destinacijaService.findAll();
		
		ModelAndView result = new ModelAndView("dodavanjeSmestajneJedinice");
		//dodata prazna smestajna jedinica zbog boolova
		result.addObject("smestajnaJedinica", new SmestajnaJedinica());
		result.addObject("tipJedinica", tipJedinica);
		result.addObject("destinacije", destinacije);
		
		return result;
	}
	
	
	@PostMapping(value="/add")
	public void dodajSmestajnuJedinicu(HttpServletResponse response,@RequestParam Long id,@RequestParam String nazivJedinice,
			@RequestParam String nazivTipaJedinice,
			@RequestParam Integer kapacitet, @RequestParam String grad,
			@RequestParam Double recenzija, 
			@RequestParam Boolean uslugaWifi, @RequestParam Boolean uslugaKupatilo, 
			@RequestParam Boolean uslugaTv, @RequestParam String opis) throws IOException {
		

		
		Destinacija destinacija = destinacijaService.findOneByGrad(grad);
		
		TipJedinice tipJedinice = tipJediniceService.findOneByNaziv(nazivTipaJedinice);
		
		Long idDestinacije = destinacija.getId();
		
		Long idTipJedinice = tipJedinice.getId();
		
		SmestajnaJedinica SmestajnaJedinica = new SmestajnaJedinica(
				nazivJedinice,
				idTipJedinice,
				kapacitet,
				idDestinacije,
				recenzija,
				uslugaWifi,
				uslugaKupatilo,
				uslugaTv,
				opis);
		
		smestajnaJedinicaService.save(SmestajnaJedinica);
		response.sendRedirect(bURL);
	}
	
	@GetMapping(value="/details")
	public ModelAndView details(@RequestParam Long id, HttpServletResponse response) throws IOException {
		
		SmestajnaJedinica smestajnaJedinica = smestajnaJedinicaService.findOne(id);
		
	    TipJedinice tipJedinice = tipJediniceService.findOne(smestajnaJedinica.getIdTipJedinice());
	    
	    Destinacija destinacija = destinacijaService.findOne(smestajnaJedinica.getIdDestinacijeSmestaja());
	    
		ModelAndView result = new ModelAndView("smestajnaJedinica");
		result.addObject("smestajnaJedinica", smestajnaJedinica);
		result.addObject("tipJedinice", tipJedinice);
		result.addObject("destinacija", destinacija);
		return result;
	}
	
	@PostMapping(value="/delete")
	public void delete(@RequestParam Long id, HttpServletResponse response) throws IOException {
		smestajnaJedinicaService.delete(id);
		response.sendRedirect(bURL+"smestajneJedinice");
	}
	
	@PostMapping(value="/edit")
	public void edit(@ModelAttribute SmestajnaJedinica smestajnaJedinicaEdited , HttpServletResponse response) throws IOException {	
		smestajnaJedinicaService.update(smestajnaJedinicaEdited);
		response.sendRedirect(bURL+"smestajneJedinice");
	}

}
