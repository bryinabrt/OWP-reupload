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
import com.ftn.PrviMavenVebProjekat.model.PrevoznoSredstvo;
import com.ftn.PrviMavenVebProjekat.model.SmestajnaJedinica;
import com.ftn.PrviMavenVebProjekat.model.TipJedinice;
import com.ftn.PrviMavenVebProjekat.service.DestinacijaService;
import com.ftn.PrviMavenVebProjekat.service.PrevoznoSredstvoService;
@Controller
@RequestMapping(value="/prevoznaSredstva")
public class PrevoznoSredstvoController implements ServletContextAware{
	
	@Autowired
	private ServletContext servletContext;
	private String bURL;
	
	@Autowired
	private PrevoznoSredstvoService prevoznoSredstvoService;
	
	@Autowired
	private DestinacijaService destinacijaService;
	
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
		
		List<PrevoznoSredstvo> prevoznaSredstva = prevoznoSredstvoService.findAll();
		List<Destinacija> destinacije = prevoznaSredstva.stream()
	            .map(prevoznoSredstvo -> destinacijaService.findOne(prevoznoSredstvo.getKrajnjaDestinacija()))
	            .collect(Collectors.toList());
		ModelAndView result = new ModelAndView("prevoznaSredstva");
		result.addObject("prevoznaSredstva", prevoznaSredstva);
		result.addObject("destinacije", destinacije);
		return result;
	}
	
	@GetMapping(value="/add")
	@ResponseBody
	public ModelAndView dodajPrevoznoSredstvo(HttpServletResponse response) throws IOException {
		List<Destinacija> destinacije = destinacijaService.findAll();
		ModelAndView result = new ModelAndView("dodavanjePrevoznogSredstva");
		result.addObject("destinacije", destinacije);
		return result;
	}
	
	@PostMapping(value="/add")
	public void dodajPrevoznoSredstvo(HttpServletResponse response, @RequestParam String tipSredstva,
			@RequestParam Integer brojSedista, @RequestParam String grad, @RequestParam String opis) throws IOException {
		Destinacija destinacija = destinacijaService.findOneByGrad(grad);
		PrevoznoSredstvo prevoznoSredstvo = new PrevoznoSredstvo(
				tipSredstva,
				brojSedista,
				destinacija.getId(),
				opis);
		
		prevoznoSredstvoService.save(prevoznoSredstvo);
		response.sendRedirect(bURL);
	}
	
	@GetMapping(value="/details")
	public ModelAndView details(@RequestParam Long id, HttpServletResponse response) throws IOException {

		PrevoznoSredstvo prevoznoSredstvo = prevoznoSredstvoService.findOne(id);
		
		Destinacija destinacija = destinacijaService.findOne(prevoznoSredstvo.getKrajnjaDestinacija());
		
		ModelAndView result = new ModelAndView("prevoznoSredstvo");
		result.addObject("destinacija", destinacija);
		result.addObject("prevoznoSredstvo", prevoznoSredstvo);
		return result;
	}
	
	@PostMapping(value="/delete")
	public void delete(@RequestParam Long id, HttpServletResponse response) throws IOException {
		prevoznoSredstvoService.delete(id);
		response.sendRedirect(bURL+"prevoznaSredstva");
	}
	
	@PostMapping(value="/edit")
	public void edit(@ModelAttribute PrevoznoSredstvo prevoznoSredstvoEdited , HttpServletResponse response) throws IOException {	
		prevoznoSredstvoService.update(prevoznoSredstvoEdited);
		response.sendRedirect(bURL+"prevoznaSredstva");
	}

}
