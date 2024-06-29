package com.ftn.PrviMavenVebProjekat.controller;

import com.ftn.PrviMavenVebProjekat.model.*;
import com.ftn.PrviMavenVebProjekat.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/listeZelja")
public class ListaZeljaController implements ServletContextAware {

    @Autowired
    private ServletContext servletContext;
    private  String bURL;

    @Autowired
    private ListaZeljaService listaZeljaService;

    @Autowired
    private PutovanjeService putovanjeService;

    @Autowired
    private PriceService priceService;

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

    @GetMapping
    @ResponseBody
    public ModelAndView index(HttpSession session) {
        System.out.println("ZELJE:");
        Korisnik korisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
        List<ListaZelja> listeZelja = listaZeljaService.getAllByKorId(korisnik.getId());

        List<Putovanje> svaPutovanja = putovanjeService.findAll();
        List<Putovanje> putovanja = new ArrayList<>();

        for (ListaZelja listaZelja : listeZelja) {
            for (Putovanje putovanje1 : svaPutovanja) {
                if (putovanje1.getId() == listaZelja.getIdPutovanja()) {
                    putovanja.add(putovanje1);
                }
            }
        }

        for (Putovanje putovanje : putovanja) {

            Long putovanjeId = putovanje.getId();

            // Fetch prices for the current Putovanje
            List<Price> prices = priceService.findAllByPutovanjeId(putovanjeId);

            // Set the prices for the current Putovanje
            putovanje.setPrices(prices);
        }

        List<Destinacija> destinacije = putovanja.stream()
                .map(putovanje -> destinacijaService.findOne(putovanje.getIdDestinacije()))
                .collect(Collectors.toList());
        List<SmestajnaJedinica> smestajneJedinice = putovanja.stream()
                .map(putovanje -> smestajnaJedinicaService.findOne(putovanje.getIdSmestajnaJedinica()))
                .collect(Collectors.toList());
        List<PrevoznoSredstvo> prevoznaSredstva = putovanja.stream()
                .map(putovanje -> prevoznoSredstvoService.findOne(putovanje.getIdPrevoznoSredstvo()))
                .collect(Collectors.toList());
        System.out.println("Trazenje prices...");
	    /*List<Price> prices = putovanja.stream()
	            .map(putovanje -> priceService.findAllByPutovanjeId(putovanje.getId()))
	            .flatMap(List::stream)
	            .collect(Collectors.toList());*/
        List<List<Price>> pricesList = putovanja.stream()
                .map(putovanje -> priceService.findAllByPutovanjeId(putovanje.getId()))
                .collect(Collectors.toList());


        ModelAndView result = new ModelAndView("listeZelja");
        result.addObject("listeZelja", listeZelja);
        result.addObject("putovanja", putovanja);
        result.addObject("destinacije", destinacije);
        result.addObject("smestajneJedinice", smestajneJedinice);
        result.addObject("prevoznaSredstva", prevoznaSredstva);
        result.addObject("pricesList", pricesList);
        return result;
    }

    @PostMapping(value="/add")
    public void dodajZelju(HttpServletResponse response, @RequestParam Long id, HttpSession session) throws IOException {
        Korisnik korisnik = (Korisnik) session.getAttribute(KorisnikController.KORISNIK_KEY);
        ListaZelja listaZelja = new ListaZelja(korisnik.getId(), id);
        listaZeljaService.save(listaZelja);
        response.sendRedirect(bURL);
    }

    @PostMapping(value="/delete")
    public void obrisiZelju(HttpServletResponse response, @RequestParam Long id) throws IOException {
        System.out.println("AJ DI: "+id);
        listaZeljaService.delete(id);
        response.sendRedirect(bURL);
    }
}
