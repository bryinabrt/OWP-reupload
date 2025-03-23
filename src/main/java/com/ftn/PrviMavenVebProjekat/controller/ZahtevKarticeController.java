package com.ftn.PrviMavenVebProjekat.controller;

import com.ftn.PrviMavenVebProjekat.model.Korisnik;
import com.ftn.PrviMavenVebProjekat.model.LoyaltyKartica;
import com.ftn.PrviMavenVebProjekat.model.ZahtevKartice;
import com.ftn.PrviMavenVebProjekat.service.KorisnikService;
import com.ftn.PrviMavenVebProjekat.service.LoyaltyKarticaService;
import com.ftn.PrviMavenVebProjekat.service.ZahtevKarticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value="/zahtevi")
public class ZahtevKarticeController {
    @Autowired
    private ServletContext servletContext;

    private String bURL;

    @Autowired
    private ZahtevKarticeService zahtevKarticeService;

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private LoyaltyKarticaService loyaltyKarticaService;

    @PostConstruct
    public void init() {
        bURL = servletContext.getContextPath()+"/";
    }

    @GetMapping()
    @ResponseBody
    public ModelAndView index() {
        List<ZahtevKartice> zahteviKartice = zahtevKarticeService.getAll();

        System.out.println("ZAHTEVI AAA: "+zahteviKartice);

        List<Korisnik> korisnici = zahteviKartice.stream()
                .map(zahtevKartice -> korisnikService.findOneById(zahtevKartice.getIdKorisnika()))
                .collect(Collectors.toList());

        System.out.println("KORISNICI AAA: "+korisnici);

        ModelAndView result = new ModelAndView("zahteviKartice");
        result.addObject("zahteviKartice", zahteviKartice);
        result.addObject("korisnici", korisnici);
        return result;
    }

    @PostMapping("/odobri")
    public void odobri(HttpServletResponse response, @RequestParam Long id, @RequestParam Long idKorisnika) throws IOException {
        LoyaltyKartica loyaltyKartica = new LoyaltyKartica(idKorisnika, 5, 0.0);
        zahtevKarticeService.odbij(id);
        loyaltyKarticaService.save(loyaltyKartica);
        response.sendRedirect(bURL);
    }

    @PostMapping("/naruci")
    public void naruci(HttpServletResponse response, @RequestParam Long id) throws IOException {
        ZahtevKartice zk = new ZahtevKartice(id);
        System.out.println("JEBA: "+zahtevKarticeService.getByKorId(id));
        if (zahtevKarticeService.getByKorId(id) != null) {
            System.out.println("Vec si poslo zahtev");
            response.sendRedirect(bURL);
            return;
        }
        System.out.println("narucivanje dokurca");
        zahtevKarticeService.naruciKarticu(zk);
        response.sendRedirect(bURL);
    }

    @PostMapping("/odbij")
    public void odbij(HttpServletResponse response, @RequestParam Long id) throws IOException {
        zahtevKarticeService.odbij(id);
        response.sendRedirect(bURL);
    }
}
