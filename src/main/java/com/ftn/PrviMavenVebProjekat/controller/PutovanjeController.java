package com.ftn.PrviMavenVebProjekat.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ftn.PrviMavenVebProjekat.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.joda.LocalDateTimeParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.PrviMavenVebProjekat.service.DestinacijaService;
import com.ftn.PrviMavenVebProjekat.service.PrevoznoSredstvoService;
import com.ftn.PrviMavenVebProjekat.service.PriceService;
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
	
	@Autowired
	private PriceService priceService;
	
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
	public ModelAndView index(
			@RequestParam(required=false) String destinacijaId,
			@RequestParam(required=false) String prevoznoSredstvoId,
			@RequestParam(required=false) String smestajnaJedinicaId,
			@RequestParam(required=false) String kategorijaPutovanjaId,
			@RequestParam(required=false) String datumOd,
			@RequestParam(required=false) String datumDo,
			@RequestParam(required=false) Double cenaOd,
			@RequestParam(required=false) Double cenaDo,
			@RequestParam(required=false) String sortDes,
			@RequestParam(required=false) String sortPS,
			@RequestParam(required=false) String sortSJ,
			@RequestParam(required=false) String sortKat,
			@RequestParam(required=false) String sortDatumStart,
			@RequestParam(required=false) String sortDatumEnd,
			@RequestParam(required=false) String sortCena,
			@RequestParam(required=false) Integer brojNocenjaOd,
			@RequestParam(required=false) Integer brojNocenjaDo,
			@RequestParam(required=false) String sortNoc,
			@RequestParam(required=false) Integer brMesta,
			@RequestParam(required=false) Integer pId,
			HttpSession session)  throws IOException, ParseException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		LocalDateTime dateOd = null;
		LocalDateTime dateDo = null;

		if (datumOd == null) {
			dateOd = LocalDateTime.of(1970, 01, 1, 00, 00, 00);
		} else {
			dateOd = LocalDateTime.parse(datumOd, formatter);
		}
		if (datumDo == null) {
			dateDo = LocalDateTime.of(2070, 01, 1, 00, 00, 00);
		} else {
			dateDo = LocalDateTime.parse(datumDo, formatter);
		}

		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		// Convert the dates to the desired format
		String formattedDateOd = dateOd.format(outputFormatter);
		String formattedDateDo = dateDo.format(outputFormatter);

		List<Putovanje> putovanja = putovanjeService.find(destinacijaId, prevoznoSredstvoId, smestajnaJedinicaId,
				kategorijaPutovanjaId, formattedDateOd, formattedDateDo, cenaOd, cenaDo, sortDes, sortPS,
				sortSJ, sortKat, sortDatumStart, sortDatumEnd, sortCena, brojNocenjaOd, brojNocenjaDo, sortNoc, brMesta, pId);

		for (Putovanje putovanje : putovanja) {
			Long putovanjeId = putovanje.getId();
			System.out.println("Id put: "+putovanjeId);

			// Fetch prices for the current Putovanje
			List<Price> prices = priceService.findAllByPutovanjeId(putovanjeId);

			// Set the prices for the current Putovanje
			putovanje.setPrices(prices);
		}
		List<Destinacija> destinacije = destinacijaService.findAll();
		List<SmestajnaJedinica> smestajneJedinice = smestajnaJedinicaService.findAll();
		List<PrevoznoSredstvo> prevoznaSredstva = prevoznoSredstvoService.findAll();
		
	    /*List<Destinacija> destinacije = putovanja.stream()
	            .map(putovanje -> destinacijaService.findOne(putovanje.getIdDestinacije()))
	            .collect(Collectors.toList());
	    List<SmestajnaJedinica> smestajneJedinice = putovanja.stream()
	    		.map(putovanje -> smestajnaJedinicaService.findOne(putovanje.getIdSmestajnaJedinica()))
	    		.collect(Collectors.toList());
	    List<PrevoznoSredstvo> prevoznaSredstva = putovanja.stream()
	    		.map(putovanje -> prevoznoSredstvoService.findOne(putovanje.getIdPrevoznoSredstvo()))
	    		.collect(Collectors.toList());*/
		System.out.println("Trazenje prices...");
	    /*List<Price> prices = putovanja.stream()
	            .map(putovanje -> priceService.findAllByPutovanjeId(putovanje.getId()))
	            .flatMap(List::stream)
	            .collect(Collectors.toList());*/
		List<List<Price>> pricesList = putovanja.stream()
				.map(putovanje -> priceService.findAllByPutovanjeId(putovanje.getId()))
				.collect(Collectors.toList());
		ModelAndView result = new ModelAndView("putovanja");
		result.addObject("putovanja", putovanja);
		result.addObject("destinacije", destinacije);
		result.addObject("smestajneJedinice", smestajneJedinice);
		result.addObject("prevoznaSredstva", prevoznaSredstva);
		result.addObject("kategorija", Kategorija.values());
		result.addObject("pricesList", pricesList);
		return result;
	}
	
	@GetMapping(value="/odabirDestinacije")
	@ResponseBody
	public ModelAndView odabirDestinacije() {
		List<Destinacija> destinacije = destinacijaService.findAll();
		ModelAndView result = new ModelAndView("odabirDestinacije");
		result.addObject("destinacije", destinacije);
		return result;
	}
	
	@GetMapping(value="/add")
	@ResponseBody
	public ModelAndView dodajPutovanje(@RequestParam("grad") String grad){
		
		Destinacija destinacija = destinacijaService.findOneByGrad(grad);
		
		Long idDestinacijeSmestaja = destinacija.getId();
		Long krajnjaDestinacija = destinacija.getId();
		
		List<SmestajnaJedinica> smestajneJedinice = smestajnaJedinicaService.findOneByDestinacija(idDestinacijeSmestaja);
		
		List<PrevoznoSredstvo> prevoznaSredstva = prevoznoSredstvoService.findOneByDestinacija(krajnjaDestinacija);
		
		List<Price> prices = priceService.findAll();
		
		ModelAndView result = new ModelAndView("dodavanjePutovanja");
		//result.addObject("destinacije", destinacije);
		result.addObject("smestajneJedinice", smestajneJedinice);
		result.addObject("prevoznaSredstva", prevoznaSredstva);
		result.addObject("prices", prices);
		return result;
	}
	
	
	@PostMapping(value="/add")
	public void dodajPutovanje(HttpServletResponse response, @RequestParam Long id, @RequestParam String sifraPutovanja,
	        @RequestParam String grad, @RequestParam String tipSredstva, @RequestParam String nazivJedinice,
	        @RequestParam Kategorija kategorija, @RequestParam List<String> startDateList,
	        @RequestParam List<String> endDateList, @RequestParam List<Double> priceOfTravelList,
	        @RequestParam int brojNocenja, @RequestParam String slika) throws IOException {
		
		Destinacija destinacija = destinacijaService.findOneByGrad(grad);
		
		PrevoznoSredstvo prevoznoSredstvo = prevoznoSredstvoService.findOneByTip(tipSredstva);
		
		SmestajnaJedinica smestajnaJedinica = smestajnaJedinicaService.findOneByNaziv(nazivJedinice);
		
		Long idDestinacije = destinacija.getId();
		
		Long idPrevoznoSredstvo = prevoznoSredstvo.getId();
		
		Long idSmestajnaJedinica = smestajnaJedinica.getId();
		
		Putovanje putovanje = new Putovanje(
				sifraPutovanja,
				idDestinacije,
				idPrevoznoSredstvo,
				idSmestajnaJedinica,
				kategorija,
				brojNocenja,
				"images/"+slika);
		putovanjeService.save(putovanje);
		
		Putovanje putovanjePrices = putovanjeService.findOneBySifra(sifraPutovanja);
		
		Long putovanjeId = putovanjePrices.getId();

        
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		
	    
	    for (int i = 0; i < startDateList.size(); i++) {
	        String startDateString = startDateList.get(i);
	        System.out.println("start " + startDateString);
	        String endDateString = endDateList.get(i);
	        System.out.println("end " + endDateString);
	        Double priceOfTravel = priceOfTravelList.get(i);
	        

	        LocalDateTime startDate = LocalDateTime.parse(startDateString, formatter);
	        System.out.println("start " + startDate);

	        LocalDateTime endDate = LocalDateTime.parse(endDateString, formatter);
	        System.out.println("end " + endDate);

	        Price prices = new Price(idDestinacije, putovanjeId, startDate, endDate, prevoznoSredstvo.getBrojSedista(), priceOfTravel);
	        priceService.save(prices);
	    }
		
		response.sendRedirect(bURL);
	}
	
	@GetMapping(value="/details")
	public ModelAndView details(@RequestParam Long id, HttpServletResponse response) throws IOException {

		Putovanje putovanje = putovanjeService.findOne(id);

		System.out.println("Destinacija: "+putovanje.getDestinacija().getGrad());
		
		Destinacija destinacija = destinacijaService.findOne(putovanje.getIdDestinacije());
		
		PrevoznoSredstvo prevoznoSredstvo = prevoznoSredstvoService.findOne(putovanje.getIdPrevoznoSredstvo());
		
		SmestajnaJedinica smestajnaJedinica = smestajnaJedinicaService.findOne(putovanje.getIdSmestajnaJedinica());
		
		List<Price> prices = priceService.findAllByPutovanjeId(putovanje.getId());
		
		
		ModelAndView result = new ModelAndView("putovanje");
		result.addObject("putovanje", putovanje);
		result.addObject("destinacija", destinacija);
		result.addObject("prevoznoSredstvo", prevoznoSredstvo);
		result.addObject("smestajnaJedinica", smestajnaJedinica);
		result.addObject("prices", prices);
		return result;
	}

	@PostMapping(value="/search")
	@ResponseBody
	public Map<String, Object> search(
			@RequestParam(required=false) String destinacijaId,
			@RequestParam(required=false) String prevoznoSredstvoId,
			@RequestParam(required=false) String smestajnaJedinicaId,
			@RequestParam(required=false) String kategorijaPutovanjaId,
			@RequestParam(required=false) String datumOd,
			@RequestParam(required=false) String datumDo,
			@RequestParam(required=false) Double cenaOd,
			@RequestParam(required=false) Double cenaDo,
			@RequestParam(required=false) String sortDes,
			@RequestParam(required=false) String sortPS,
			@RequestParam(required=false) String sortSJ,
			@RequestParam(required=false) String sortKat,
			@RequestParam(required=false) String sortDatumStart,
			@RequestParam(required=false) String sortDatumEnd,
			@RequestParam(required=false) String sortCena,
			@RequestParam(required=false) int brojNocenjaOd,
			@RequestParam(required=false) int brojNocenjaDo,
			@RequestParam(required=false) String sortNoc,
			@RequestParam(required=false) int brMesta,
			@RequestParam(required=false) Integer pId,
			HttpSession session)  throws IOException, ParseException {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		LocalDateTime dateOd = LocalDateTime.parse(datumOd, formatter);
		LocalDateTime dateDo = LocalDateTime.parse(datumDo, formatter);

		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		// Convert the dates to the desired format
		String formattedDateOd = dateOd.format(outputFormatter);
		String formattedDateDo = dateDo.format(outputFormatter);

		List<Putovanje> putovanja = putovanjeService.find(destinacijaId, prevoznoSredstvoId, smestajnaJedinicaId,
				kategorijaPutovanjaId, formattedDateOd, formattedDateDo, cenaOd, cenaDo, sortDes, sortPS,
				sortSJ, sortKat, sortDatumStart, sortDatumEnd, sortCena, brojNocenjaOd, brojNocenjaDo, sortNoc, brMesta,pId);
		for (Putovanje putovanje : putovanja) {
			System.out.println("GRAD: "+putovanje.getDestinacija().getGrad());
		}
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
		Map<String, Object> odgovor = new LinkedHashMap<>();
		System.out.println("putovanjabrt "+putovanja);
		odgovor.put("status", "ok");
		odgovor.put("putovanja", putovanja);
		if(prijavljeniKorisnik != null) {
			odgovor.put("ulogaIme", prijavljeniKorisnik.getUloga());
		}

		return odgovor;
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
