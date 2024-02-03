package com.ftn.PrviMavenVebProjekat.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ftn.PrviMavenVebProjekat.service.DestinacijaService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ftn.PrviMavenVebProjekat.bean.SecondConfiguration.ApplicationMemory;
import com.ftn.PrviMavenVebProjekat.model.Destinacija;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/destinacije")
public class DestinacijeController implements ServletContextAware {

	
	@Autowired
	private ServletContext servletContext;
	private  String bURL;
	
	@Autowired
	private DestinacijaService destinacijaService;
	
	@Autowired
	private ApplicationMemory memorijaAplikacije;

	@PostConstruct
	public void init() {
		bURL = servletContext.getContextPath()+"/";
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@GetMapping
	public ModelAndView index() {
		
		List<Destinacija> destinacije = destinacijaService.findAll();
		
		ModelAndView result = new ModelAndView("destinacije");
		result.addObject("destinacije", destinacije);
		return result;
	}

	@GetMapping(value="/add")
	public String create(HttpServletResponse response) throws IOException {
		return "dodavanjeDestinacije";
	}

	@PostMapping(value="/add")
	public void create(@RequestParam String grad, @RequestParam String drzava,
					   @RequestParam String kontinent, HttpServletResponse response) throws IOException {

		Destinacija destinacija = new Destinacija(grad, drzava, kontinent);
		Destinacija saved = destinacijaService.save(destinacija);
		response.sendRedirect(bURL+"destinacije");
	}

	@PostMapping(value="/edit")
	public void edit(@ModelAttribute Destinacija destinacijaEdited , HttpServletResponse response) throws IOException {	
		destinacijaService.update(destinacijaEdited);
		response.sendRedirect(bURL+"destinacije");
	}

	@PostMapping(value="/delete")
	public void delete(@RequestParam Long id, HttpServletResponse response) throws IOException {
		destinacijaService.delete(id);
		response.sendRedirect(bURL+"destinacije");
	}

	@GetMapping(value="/details")
	public ModelAndView details(@RequestParam Long id, HttpServletResponse response) throws IOException {

		Destinacija destinacija = destinacijaService.findOne(id);
		
		ModelAndView result = new ModelAndView("destinacija");
		result.addObject("destinacija", destinacija);
		return result;
	}
}
