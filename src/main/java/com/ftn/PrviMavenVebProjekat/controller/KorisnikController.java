package com.ftn.PrviMavenVebProjekat.controller;

import java.util.List;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.ftn.PrviMavenVebProjekat.model.Korisnik;
import com.ftn.PrviMavenVebProjekat.model.Uloga;
import com.ftn.PrviMavenVebProjekat.service.KorisnikService;

@Controller
@RequestMapping(value="/korisnici")
public class KorisnikController implements ServletContextAware {
	
	public static final String KORISNIK_KEY = "prijavljeniKorisnik";
	
	@Autowired
	private ServletContext servletContext;
	private  String bURL;
	
	@Autowired
	private KorisnikService korisnikService;
	
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
			@RequestParam(required=false) String korIme,
			@RequestParam(required=false) String sortKorIme,
			@RequestParam(required=false) String ulogaBrt,
			@RequestParam(required=false) String sortUloga,
			HttpSession session,HttpServletResponse response)  {
		
		List<Korisnik> korisnici = korisnikService.find(korIme, ulogaBrt, sortKorIme, sortUloga);
		System.out.println("Korisnici: "+korisnici);
		ModelAndView result = new ModelAndView("korisnici");
		result.addObject("uloge", Uloga.values());
		result.addObject("korisnici", korisnici);
		return result;
	}
	
	
	@GetMapping(value="/registracija")
	public String create(HttpServletResponse response) throws IOException {
		return "registracija";
	}
	
	@PostMapping(value="/add")
	public void dodajKorisnika(HttpServletResponse response,@RequestParam Long id,@RequestParam String korisnickoIme,
			@RequestParam String lozinka,
			@RequestParam String email,@RequestParam String ime, @RequestParam String prezime, 
			@RequestParam String datumRodjenja, @RequestParam String adresa,
			@RequestParam String brojTelefona,@RequestParam String datumRegistracije, @RequestParam Uloga uloga
			) throws IOException {
		Korisnik korisnik = new Korisnik(id,
				korisnickoIme,
				lozinka,
				email,
				ime,
				prezime,
				datumRodjenja,
				adresa,
				brojTelefona,
				datumRegistracije,
				uloga);
		
		korisnikService.save(korisnik);
		response.sendRedirect(bURL);
	}
	
	@GetMapping(value="login")
	public String login(HttpServletResponse response) throws IOException {
		return "login";
	}
	
	@PostMapping(value="/login")
	public ModelAndView login(@RequestParam String email, @RequestParam String lozinka,
									 HttpSession session, HttpServletResponse response) throws IOException {
		try {
			Korisnik korisnik = korisnikService.findOne(email, lozinka);
			if (korisnik == null) {
				System.out.println("neuspeh: ");
				throw new Exception("Neispravno korisničko ime ili lozinka!");
			}
			if (korisnik.isPrijavljen()) {
				System.out.println("vec prijavljen ");
				throw new Exception("Već ste prijavljeni!");
			}

			korisnik.setUlogovan(true);
			System.out.println("uspeh jej");
			session.setAttribute(KorisnikController.KORISNIK_KEY, korisnik);
			
			response.sendRedirect(bURL);
			return null;
		} 	catch (Exception ex) {
			// ispis greške
			String greska = ex.getMessage();

			ModelAndView rezultat = new ModelAndView("login");
			rezultat.addObject("greska", greska);

			return rezultat;
		}
	}
	
	@GetMapping(value="/logout")
	public void logout(HttpSession session, HttpServletResponse response) throws IOException {
		// čitanje
		Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);

		// odjava
		if (prijavljeniKorisnik != null)
			prijavljeniKorisnik.setUlogovan(false);
	
		session.invalidate();
		System.out.println("Izlogovan" + prijavljeniKorisnik.getEmail());
		response.sendRedirect(bURL);
	}
	
	@GetMapping(value="/details")
	public ModelAndView details(Long id, HttpServletResponse response) throws IOException {

		Korisnik korisnik = korisnikService.findOneById(id);
		
		ModelAndView result = new ModelAndView("korisnik");
		result.addObject("korisnik", korisnik);
		return result;
	}
	
	@PostMapping(value="/delete")
	public void delete(@RequestParam Long id, HttpServletResponse response) throws IOException {
		korisnikService.delete(id);
		response.sendRedirect(bURL+"korisnici");
	}

	@PostMapping(value="/blokiraj")
	public void blokiraj(@RequestParam Long id, HttpServletResponse response) throws IOException {
		korisnikService.blokiraj(id);
		response.sendRedirect(bURL+"korisnici");
	}

	@PostMapping(value = "/deblokiraj")
	public void deblokiraj(@RequestParam Long id, HttpServletResponse response) throws IOException {
		korisnikService.deblokiraj(id);
		response.sendRedirect(bURL+"korisnici");
	}
	
	@PostMapping(value="/edit")
	public void edit(HttpServletResponse response,@RequestParam Long id,@RequestParam String korisnickoIme,
					 @RequestParam String lozinka,
					 @RequestParam String email,@RequestParam String ime, @RequestParam String prezime,
					 @RequestParam String datumRodjenja, @RequestParam String adresa,
					 @RequestParam String brojTelefona,@RequestParam String datumRegistracije, @RequestParam Uloga uloga
	) throws IOException {
		Korisnik korisnik = new Korisnik(id,
				korisnickoIme,
				lozinka,
				email,
				ime,
				prezime,
				datumRodjenja,
				adresa,
				brojTelefona,
				datumRegistracije,
				uloga);
		korisnikService.update(korisnik);
		response.sendRedirect(bURL);
	}
	
}
