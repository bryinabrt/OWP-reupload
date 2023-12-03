package com.ftn.PrviMavenVebProjekat.controller;

import java.io.IOException;

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

import com.ftn.PrviMavenVebProjekat.model.Korisnik;
import com.ftn.PrviMavenVebProjekat.model.Uloga;
import com.ftn.PrviMavenVebProjekat.service.KorisnikService;

@Controller
@RequestMapping(value="/registracija")
public class KorisnikController {
	
	@Autowired
	private ServletContext servletContext;
	private  String bURL;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@PostConstruct
	public void init() {	
		bURL = servletContext.getContextPath()+"/";
	}
	
	@GetMapping()
	@ResponseBody
	public String index() {
		StringBuilder sb = new StringBuilder();
		sb.append("<!DOCTYPE html>\r\n"
				+ "<html>\r\n"
				+ "<head>\r\n"
				+ "	<meta charset=\"UTF-8\" />\r\n"
				+ "	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviForma.css\"/>\r\n"
				+ "	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviHorizontalniMeni.css\"/>\r\n"
				+ "	<base href=\"/PrviMavenVebProjekat/\">\r\n"
				+ "	<title>Osnovna stranica projekta</title>\r\n"
				+ "</head>\r\n"
				+ "<body>\r\n"
				+ "	<ul>\r\n"
				+ "		<li><a href=\"registracija.html\">Registruj se</a></li>\r\n"
				+ "	</ul>\r\n"
				+ "	<br/>\r\n"
				+ "	<form method=\"post\" action=\"registracija/add\">\r\n"
				+ "		<table>\r\n"
				+ "			<caption>Prijava korisnika na sistem</caption>\r\n"
				+ "			<input type=\"hidden\" name=\"id\">"
				+ "			<tr><th>Korisnicko Ime:</th><td><input type=\"text\" value=\"\" name=\"korisnickoIme\" required/></td></tr>\r\n"
				+ "			<tr><th>Lozinka:</th><td><input type=\"password\" value=\"\" name=\"lozinka\" required/></td></tr>\r\n"
				+ "			<tr><th>Email:</th><td><input type=\"text\" value=\"\" name=\"email\" required/></td></tr>\r\n"
				+ "			<tr><th>Ime:</th><td><input type=\"text\" value=\"\" name=\"ime\" required/></td></tr>\r\n"
				+ "			<tr><th>Prezime:</th><td><input type=\"text\" value=\"\" name=\"prezime\" required/></td></tr>\r\n"
				+ "			<tr><th>Datum Rodjenja:</th><td><input type=\"date\" value=\"\" name=\"datumRodjenja\" required/></td></tr>\r\n"
				+ "			<tr><th>Adresa:</th><td><input type=\"text\" value=\"\" name=\"adresa\" required/></td></tr>\r\n"
				+ "			<tr><th>Broj Telefona:</th><td><input type=\"text\" value=\"\" name=\"brojTelefona\" required/></td></tr>\r\n"
				+ "			<input type=\"hidden\" name=\"trenutnoVreme\" value=\""+korisnikService.trenutnoVreme()+"\" "
				+ "			<tr><th>Uloga:</th><td><select name=\"uloga\">\r\n");
				
		for (Uloga uloga: Uloga.values()) {
			sb.append("<option value=\""+uloga+"\">"+uloga+"</option>");
		}
		
		sb.append(
							"</select></td></tr>\r\n"
				+ "			<tr><th></th><td><input type=\"submit\" name=\"dugme\" value=\"Submit\" /></td>\r\n"
				+ "		</table>\r\n"
				+ "	</form>\r\n"
				+ "</body>\r\n"
				+ "</html>");
		return sb.toString();
	}
	
	@PostMapping(value="/add")
	public void dodajKorisnika(HttpServletResponse response,@RequestParam Long id,@RequestParam String korisnickoIme,
			@RequestParam String lozinka,
			@RequestParam String email,@RequestParam String ime, @RequestParam String prezime, 
			@RequestParam String datumRodjenja, @RequestParam String adresa,
			@RequestParam String brojTelefona,@RequestParam String trenutnoVreme,@RequestParam Uloga uloga
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
				trenutnoVreme,
				uloga);
		
		korisnikService.save(korisnik);
		response.sendRedirect(bURL);
	}
	
	
}
