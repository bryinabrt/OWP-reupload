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

@Controller
@RequestMapping(value="/destinacije")
public class DestinacijeController implements ServletContextAware {

	public static final String DESTINACIJE_KEY = "destinacije";
	
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
	@ResponseBody
	public void index(HttpServletResponse response) throws IOException {
		
		
		
		PrintWriter out;
		out = response.getWriter();
		File htmlFile = new ClassPathResource("static/template.html").getFile();
		Document doc = Jsoup.parse(htmlFile, "UTF-8");

		Element body = doc.select("body").first();

		Element ulTag = new Element(Tag.valueOf("ul"), "");
		Element liTagDestinacije = new Element(Tag.valueOf("li"), "");
		Element aTagDestinacije = new Element(Tag.valueOf("a"), "").attr("href", "/PrviMavenVebProjekat/destinacije").text("Destinacije");
		liTagDestinacije.appendChild(aTagDestinacije);
		ulTag.appendChild(liTagDestinacije);

		Element table = new Element(Tag.valueOf("table"), "");
		Element caption = new Element(Tag.valueOf("caption"), "").text("Destinacije");
		Element trZaglavlje = new Element(Tag.valueOf("tr"), "");
		Element thRedniBroj = new Element(Tag.valueOf("th"), "").text("R. br.");
		Element thDetails = new Element(Tag.valueOf("th"), "").text("Details");
		Element thGrad = new Element(Tag.valueOf("th"), "").text("Grad");
		Element thDrzava = new Element(Tag.valueOf("th"), "").text("Drzava");
		Element thKontinent = new Element(Tag.valueOf("th"), "").text("Kontinent");

		trZaglavlje.appendChild(thRedniBroj);
		trZaglavlje.appendChild(thDetails);
		trZaglavlje.appendChild(thGrad);
		trZaglavlje.appendChild(thDrzava);
		trZaglavlje.appendChild(thKontinent);

		table.appendChild(caption);
		table.appendChild(trZaglavlje);

		List<Destinacija> listaDestinacija = destinacijaService.findAll();
		for (int i=0; i < listaDestinacija.size(); i++) {
			Element trDestinacija = new Element(Tag.valueOf("tr"), "");
			Element tdRedniBroj = new Element(Tag.valueOf("td"), "").text(String.valueOf(i + 1));
			Element tdDetails = new Element(Tag.valueOf("td"), "");
			Element aDetails = new Element(Tag.valueOf("a"), "").attr("href","destinacije/details?id="+listaDestinacija.get(i).getId()).text(listaDestinacija.get(i).getGrad());
			Element tdGrad = new Element(Tag.valueOf("td"), "").text(listaDestinacija.get(i).getGrad());
			Element tdDrzava = new Element(Tag.valueOf("td"), "").text(listaDestinacija.get(i).getDrzava());
			Element tdKontinent = new Element(Tag.valueOf("td"), "").text(listaDestinacija.get(i).getKontinent());

			tdDetails.appendChild(aDetails);
			trDestinacija.appendChild(tdRedniBroj);
			trDestinacija.appendChild(tdDetails);
			trDestinacija.appendChild(tdGrad);
			trDestinacija.appendChild(tdDrzava);
			trDestinacija.appendChild(tdKontinent);

			Element tdForm = new Element(Tag.valueOf("td"), "");
			Element form = new Element(Tag.valueOf("form"), "").attr("method", "post").attr("action", "destinacije/delete");
			Element inputHidden = new Element(Tag.valueOf("input"), "").attr("type", "hidden").attr("name", "id").attr("value", String.valueOf(listaDestinacija.get(i).getId()));
			Element inputSubmit = new Element(Tag.valueOf("input"), "").attr("type", "submit").attr("value", "Obrisi");
			form.appendChild(inputHidden);
			form.appendChild(inputSubmit);
			tdForm.appendChild(form);
			trDestinacija.appendChild(tdForm);
			table.appendChild(trDestinacija);
		}

		Element ulTagDodajDestinaciju = new Element(Tag.valueOf("ul"), "");
		Element liTagDodajDestinaciju = new Element(Tag.valueOf("li"), "");
		Element aTagDodajDestinaciju = new Element(Tag.valueOf("a"), "").attr("href", "destinacije/add").text("Dodaj destinaciju");
		liTagDodajDestinaciju.appendChild(aTagDodajDestinaciju);
		ulTagDodajDestinaciju.appendChild(liTagDodajDestinaciju);

		body.appendChild(ulTag);
		body.appendChild(table);
		body.appendChild(ulTagDodajDestinaciju);

		out.write(doc.html());
		return;
	}

	@GetMapping(value="/add")
	@ResponseBody
	public void create(HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		File htmlFile = new ClassPathResource("static/template.html").getFile();
		Document doc = Jsoup.parse(htmlFile, "UTF-8");

		Element body = doc.select("body").first();

		Element ulTag = new Element(Tag.valueOf("ul"), "");
		Element liTagDestinacije = new Element(Tag.valueOf("li"), "");
		Element aTagDestinacije = new Element(Tag.valueOf("a"), "").attr("href", "/PrviMavenVebProjekat/destinacije").text("Destinacije");
		liTagDestinacije.appendChild(aTagDestinacije);
		ulTag.appendChild(liTagDestinacije);

		Element form = new Element(Tag.valueOf("form"), "").attr("method", "post").attr("action", "add");
		Element table = new Element(Tag.valueOf("table"), "");
		Element caption = new Element(Tag.valueOf("caption"), "").text("Destinacija");

		Element trGrad = new Element(Tag.valueOf("tr"), "");
		Element thGrad = new Element(Tag.valueOf("th"), "").text("grad");
		Element tdGrad = new Element(Tag.valueOf("td"), "");
		Element inputGrad = new Element(Tag.valueOf("input"), "").attr("type", "text").attr("name", "grad");

		tdGrad.appendChild(inputGrad);
		trGrad.appendChild(thGrad);
		trGrad.appendChild(tdGrad);

		Element trDrzava = new Element(Tag.valueOf("tr"), "");
		Element thDrzava = new Element(Tag.valueOf("th"), "").text("Drzava");
		Element tdDrzava = new Element(Tag.valueOf("td"), "");
		Element inputDrzava = new Element(Tag.valueOf("input"), "").attr("type", "text").attr("name", "drzava");

		tdDrzava.appendChild(inputDrzava);
		trDrzava.appendChild(thDrzava);
		trDrzava.appendChild(tdDrzava);

		Element trKontinent = new Element(Tag.valueOf("tr"), "");
		Element thKontinent = new Element(Tag.valueOf("th"), "").text("Kontinent");
		Element tdKontinent = new Element(Tag.valueOf("td"), "");
		Element inputKontinent = new Element(Tag.valueOf("input"), "").attr("type", "text").attr("name", "kontinent");

		tdKontinent.appendChild(inputKontinent);
		trKontinent.appendChild(thKontinent);
		trKontinent.appendChild(tdKontinent);

		Element trSubmit = new Element(Tag.valueOf("tr"), "");
		Element thSubmit = new Element(Tag.valueOf("th"), "");
		Element tdSubmit = new Element(Tag.valueOf("td"), "");
		Element inputSubmit = new Element(Tag.valueOf("input"), "").attr("type", "submit").attr("value", "Dodaj");

		tdSubmit.appendChild(inputSubmit);
		trSubmit.appendChild(thSubmit);
		trSubmit.appendChild(tdSubmit);

		table.appendChild(caption);

		table.appendChild(trGrad);
		table.appendChild(trDrzava);
		table.appendChild(trKontinent);
		table.appendChild(trSubmit);

		form.appendChild(table);

		body.appendChild(ulTag);
		body.appendChild(form);

		out.write(doc.html());
		return;
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
	@ResponseBody
	public void details(@RequestParam Long id, HttpServletResponse response) throws IOException {

		Destinacija destinacija = destinacijaService.findOne(id);

		PrintWriter out;
		out = response.getWriter();
		File htmlFile = new ClassPathResource("static/template.html").getFile();
		Document doc = Jsoup.parse(htmlFile, "UTF-8");

		Element body = doc.select("body").first();

		Element ulTag = new Element(Tag.valueOf("ul"), "");
		Element liTagDestinacije = new Element(Tag.valueOf("li"), "");
		Element aTagDestinacije = new Element(Tag.valueOf("a"), "").attr("href", "/PrviMavenVebProjekat/destinacije").text("Destinacije");
		liTagDestinacije.appendChild(aTagDestinacije);
		ulTag.appendChild(liTagDestinacije);

		Element form = new Element(Tag.valueOf("form"), "").attr("method", "post").attr("action", "edit");
		Element table = new Element(Tag.valueOf("table"), "");
		Element caption = new Element(Tag.valueOf("caption"), "").text("Destinacija");

		Element inputHidden = new Element(Tag.valueOf("input"), "").attr("type", "hidden").attr("name", "id").attr("value", String.valueOf(destinacija.getId()));

		Element trGrad = new Element(Tag.valueOf("tr"), "");
		Element thGrad = new Element(Tag.valueOf("th"), "").text("Grad");
		Element tdGrad = new Element(Tag.valueOf("td"), "");
		Element inputGrad = new Element(Tag.valueOf("input"), "").attr("type", "text").attr("name", "grad").attr("value", destinacija.getGrad());

		tdGrad.appendChild(inputGrad);
		trGrad.appendChild(thGrad);
		trGrad.appendChild(tdGrad);

		Element trDrzava = new Element(Tag.valueOf("tr"), "");
		Element thDrzava = new Element(Tag.valueOf("th"), "").text("Drzava");
		Element tdDrzava = new Element(Tag.valueOf("td"), "");
		Element inputDrzava = new Element(Tag.valueOf("input"), "").attr("type", "text").attr("name", "drzava").attr("value", destinacija.getDrzava());

		tdDrzava.appendChild(inputDrzava);
		trDrzava.appendChild(thDrzava);
		trDrzava.appendChild(tdDrzava);

		Element trKontinent = new Element(Tag.valueOf("tr"), "");
		Element thKontinent = new Element(Tag.valueOf("th"), "").text("Kontinent");
		Element tdKontinent = new Element(Tag.valueOf("td"), "");
		Element inputKontinent = new Element(Tag.valueOf("input"), "").attr("type", "text").attr("name", "kontinent").attr("value", destinacija.getKontinent());

		tdKontinent.appendChild(inputKontinent);
		trKontinent.appendChild(thKontinent);
		trKontinent.appendChild(tdKontinent);

		Element trSubmit = new Element(Tag.valueOf("tr"), "");
		Element thSubmit = new Element(Tag.valueOf("th"), "");
		Element tdSubmit = new Element(Tag.valueOf("td"), "");
		Element inputSubmit = new Element(Tag.valueOf("input"), "").attr("type", "submit").attr("value", "Izmeni");

		tdSubmit.appendChild(inputSubmit);
		trSubmit.appendChild(thSubmit);
		trSubmit.appendChild(tdSubmit);

		table.appendChild(caption);

		table.appendChild(trGrad);
		table.appendChild(trDrzava);
		table.appendChild(trKontinent);
		table.appendChild(trSubmit);

		form.appendChild(inputHidden);
		form.appendChild(table);

		Element formBrisanje = new Element(Tag.valueOf("form"), "").attr("method", "post").attr("action", "delete");
		Element inputSubmitBrisanje = new Element(Tag.valueOf("input"), "").attr("type", "submit").attr("value", "Obrisi");
		Element inputHiddenBrisanje = new Element(Tag.valueOf("input"), "").attr("type", "hidden").attr("name", "id").attr("value", String.valueOf(destinacija.getId()));;
		formBrisanje.appendChild(inputHiddenBrisanje);
		formBrisanje.appendChild(inputSubmitBrisanje);

		body.appendChild(ulTag);
		body.appendChild(form);
		body.appendChild(formBrisanje);

		out.write(doc.html());
		return;
	}
}
