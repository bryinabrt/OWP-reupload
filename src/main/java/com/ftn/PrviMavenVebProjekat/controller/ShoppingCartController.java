package com.ftn.PrviMavenVebProjekat.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.PrviMavenVebProjekat.model.Destinacija;
import com.ftn.PrviMavenVebProjekat.model.Price;
import com.ftn.PrviMavenVebProjekat.model.Putovanje;
import com.ftn.PrviMavenVebProjekat.model.ShoppingCart;
import com.ftn.PrviMavenVebProjekat.model.SmestajnaJedinica;
import com.ftn.PrviMavenVebProjekat.model.TipJedinice;
import com.ftn.PrviMavenVebProjekat.model.PrevoznoSredstvo;
import com.ftn.PrviMavenVebProjekat.service.DestinacijaService;
import com.ftn.PrviMavenVebProjekat.service.KorisnikService;
import com.ftn.PrviMavenVebProjekat.service.PrevoznoSredstvoService;
import com.ftn.PrviMavenVebProjekat.service.PriceService;
import com.ftn.PrviMavenVebProjekat.service.PutovanjeService;
import com.ftn.PrviMavenVebProjekat.service.ShoppingCartService;
import com.ftn.PrviMavenVebProjekat.service.SmestajnaJedinicaService;
import com.ftn.PrviMavenVebProjekat.service.TipJediniceService;


@Controller
@RequestMapping(value="/kolica")
public class ShoppingCartController implements ServletContextAware {
	
	@Autowired
	private ServletContext servletContext;
	private  String bURL;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private DestinacijaService destinacijaService;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private PutovanjeService putovanjeService;
	
	@Autowired
	private PriceService priceService;
	
	@Autowired
	private PrevoznoSredstvoService prevoznoSredstvoService;
	
	@Autowired
	private SmestajnaJedinicaService smestajnaJedinicaService;
	
	
	@PostConstruct
	public void init() {	
		bURL = servletContext.getContextPath()+"/";
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	} 
	
	/*@GetMapping()
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
	}*/
	
	@GetMapping(value="/add")
	@ResponseBody
	public ModelAndView dodajUKolica(@RequestParam Long id, @RequestParam String grad, @RequestParam String tipSredstva,
			@RequestParam String nazivJedinice, @RequestParam String startDate, @RequestParam String endDate, @RequestParam Double priceOfTravel
			,@RequestParam Long priceId){

		Putovanje putovanje = putovanjeService.findOne(id);
		
		System.out.println("id put " +putovanje.getId());
		
		PrevoznoSredstvo prevoznoSredstvo = prevoznoSredstvoService.findOne(putovanje.getIdPrevoznoSredstvo());
		
		List<SmestajnaJedinica> smestajneJedinice = smestajnaJedinicaService.findOneByDestinacija(putovanje.getIdSmestajnaJedinica());
		
		Destinacija destinacija = destinacijaService.findOneByGrad(grad);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		
        LocalDateTime startDateString = LocalDateTime.parse(startDate, formatter);
        System.out.println("start " + startDate);

        LocalDateTime endDateString = LocalDateTime.parse(endDate, formatter);
        System.out.println("end " + endDate);
		
		Price price = new Price(priceId, startDateString, endDateString, priceOfTravel);
		
		ModelAndView result = new ModelAndView("dodavanjeUKolica");
		
		result.addObject("shoppingCart", new ShoppingCart());
		result.addObject("smestajneJedinice", smestajneJedinice);
		result.addObject("prevoznoSredstvo", prevoznoSredstvo);
		result.addObject("destinacija", destinacija);
		result.addObject("putovanje", putovanje);
		result.addObject("price", price);
		
		return result;
	}
	
	@PostMapping(value="/add")
	public void dodajUKolica(HttpServletResponse response, @RequestParam Long korisnikId,
			@RequestParam Long rezervisanoPutovanjeId,
			@RequestParam Integer brojPutnika, @RequestParam Double ukupnaCena,
			@RequestParam Long pricesId) throws IOException {
		
		System.out.println("Prices ID: " +pricesId);
		
		ShoppingCart shoppingCart = new ShoppingCart(korisnikId, rezervisanoPutovanjeId, brojPutnika, ukupnaCena, pricesId);
		
		shoppingCartService.save(shoppingCart);
		response.sendRedirect(bURL);
	}
	
}
