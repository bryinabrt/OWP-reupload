package com.ftn.PrviMavenVebProjekat.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ftn.PrviMavenVebProjekat.model.*;
import com.ftn.PrviMavenVebProjekat.service.*;
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


@Controller
@RequestMapping(value="/kolica")
public class ShoppingCartController implements ServletContextAware {

	public static final String KOLICA_KEY = "shoppingCart";

	private List<ShoppingCart> cartinjo = new ArrayList<>();
	
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
	private LoyaltyKarticaService loyaltyKarticaService;
	
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
	
	@GetMapping()
	@ResponseBody
	public ModelAndView index(HttpSession session) {
		
		ArrayList<ShoppingCart> shoppingCarts = (ArrayList<ShoppingCart>) session.getAttribute(KOLICA_KEY);
		if (shoppingCarts == null) {
			shoppingCarts = new ArrayList<>();
		}

		Korisnik korisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);

		System.out.println("korisnik: "+korisnik.getId());

		LoyaltyKartica loyaltyKartica = loyaltyKarticaService.getByKorId(korisnik.getId());

		System.out.println("kartitza: "+loyaltyKartica);

		//List<ShoppingCart> shoppingCarts = (List<ShoppingCart>) session.getAttribute(CART_KEY);
		//if (cart == null) {
		//	cart = new ArrayList<>();
		//	session.setAttribute(KOLICA_KEY, cart);
		//}

		
		List<Putovanje> putovanja = shoppingCarts.stream()
	            .map(shoppingCart -> putovanjeService.findOne(shoppingCart.getRezervisanoPutovanjeId()))
	            .collect(Collectors.toList());
		
		List<Price> prices = shoppingCarts.stream()
	            .map(shoppingCart -> priceService.findOne(shoppingCart.getPricesId()))
	            .collect(Collectors.toList());
		
	    List<Destinacija> destinacije = putovanja.stream()
	            .map(putovanje -> destinacijaService.findOne(putovanje.getIdDestinacije()))
	            .collect(Collectors.toList());
	    List<SmestajnaJedinica> smestajneJedinice = putovanja.stream()
	            .map(putovanje -> smestajnaJedinicaService.findOne(putovanje.getIdSmestajnaJedinica()))
	            .collect(Collectors.toList());
	    List<PrevoznoSredstvo> prevoznaSredstva = putovanja.stream()
	            .map(putovanje -> prevoznoSredstvoService.findOne(putovanje.getIdPrevoznoSredstvo()))
	            .collect(Collectors.toList());
		System.out.println("kartinjo: " + session.getAttribute(KOLICA_KEY));
		
		ModelAndView result = new ModelAndView("korpa");

		result.addObject("loyaltyKartica", loyaltyKartica);
		result.addObject("shoppingCarts", shoppingCarts);
		result.addObject("putovanja", putovanja);
		result.addObject("prices", prices);
		result.addObject("destinacije", destinacije);
		result.addObject("smestajneJedinice", smestajneJedinice);
		result.addObject("prevoznaSredstva", prevoznaSredstva);
		return result;
	}
	
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
		
		Price price = new Price(priceId, startDateString, endDateString, prevoznoSredstvo.getBrojSedista(), priceOfTravel);
		
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
			@RequestParam Long rezervisanoPutovanjeId, @RequestParam String nazivJedinice,
			@RequestParam Integer brojPutnika, @RequestParam Double ukupnaCena,
			@RequestParam Long pricesId, HttpSession session) throws IOException {
		
		System.out.println("Prices ID: " +pricesId);

		SmestajnaJedinica smestajnaJedinica = smestajnaJedinicaService.findOneByNaziv(nazivJedinice);

		List<ShoppingCart> cartinjo = (List<ShoppingCart>) session.getAttribute(KOLICA_KEY);
		if (cartinjo == null) {
			cartinjo = new ArrayList<>();
		}

		Long idKolica = Long.valueOf(cartinjo.size()+1);

		ShoppingCart shoppingCart = new ShoppingCart(idKolica, korisnikId, rezervisanoPutovanjeId, smestajnaJedinica.getId(), brojPutnika, ukupnaCena, pricesId);

		cartinjo.add(shoppingCart);

		session.setAttribute(KOLICA_KEY, cartinjo);

		//carts.add(new ShoppingCart(korisnikId, rezervisanoPutovanjeId, brojPutnika, ukupnaCena, pricesId));

		response.sendRedirect(bURL);
	}
	
	@PostMapping(value="/delete")
	public void delete(@RequestParam Long cartId, @RequestParam Long korisnikId, HttpServletResponse response, HttpSession session) throws IOException {
		List<ShoppingCart> cartinjo = (List<ShoppingCart>) session.getAttribute(KOLICA_KEY);
		shoppingCartService.remove(cartId, korisnikId, session);
		response.sendRedirect(bURL+"kolica?id=" + korisnikId);
	}
	
	@PostMapping(value="/edit")
	public void edit(@RequestParam Long cartId, @RequestParam Long pricesId, @RequestParam Integer brojPutnika, @RequestParam Long korisnikId,
			HttpServletResponse response) throws IOException {
		Price price = priceService.findOne(pricesId);
		Double cenaprice = price.getPriceOfTravel();
		System.out.println("cena "+cenaprice);
		Double brojPutnikaD = (double) brojPutnika;
		System.out.println("putnika "+brojPutnikaD);
		Double ukupnaCena = cenaprice*brojPutnikaD;
		System.out.println("ukupnaCena "+ukupnaCena);
		ShoppingCart shoppingCart = new ShoppingCart(korisnikId, brojPutnika, ukupnaCena, pricesId);
		shoppingCartService.update(shoppingCart);
		response.sendRedirect(bURL+"kolica?id="+korisnikId);
	}
	
}
