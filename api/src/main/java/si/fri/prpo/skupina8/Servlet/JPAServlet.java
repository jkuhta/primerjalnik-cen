package si.fri.prpo.skupina8.Servlet;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import si.fri.prpo.skupina8.Dtos.KategorijaDto;
import si.fri.prpo.skupina8.Dtos.KosaricaIzdelekDto;
import si.fri.prpo.skupina8.Dtos.KosaricaTrgovinaDto;
import si.fri.prpo.skupina8.Izdelek;
import si.fri.prpo.skupina8.Kosarica;
import si.fri.prpo.skupina8.NinjasObj;
import si.fri.prpo.skupina8.PoslovnaZrna.UpravljanjeIzdelkovZrno;
import si.fri.prpo.skupina8.PoslovnaZrna.UpravljanjeKosariceZrno;
import si.fri.prpo.skupina8.Zrna.CalorieNinjasZrno;
import si.fri.prpo.skupina8.Zrna.IzdelkiZrno;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet("/servlet")
public class JPAServlet extends HttpServlet {

    @Inject
    private IzdelkiZrno izdelkiZrno;

    @Inject
    private UpravljanjeKosariceZrno upravljanjeKosariceZrno;

    @Inject
    private UpravljanjeIzdelkovZrno upravljanjeIzdelkovZrno;

    @Inject
    private CalorieNinjasZrno calorieNinjasZrno;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        PrintWriter writer = resp.getWriter();
        System.out.println("Zagon Servleta");
        writer.println("Pozdravljeni iz servleta");

        final String name = ConfigurationUtil.getInstance().get("kumuluzee.name").get();
        final String ver = ConfigurationUtil.getInstance().get("kumuluzee.version").get();
        final String env = ConfigurationUtil.getInstance().get("kumuluzee.env.name").get();
        resp.getWriter().println(name);
        resp.getWriter().println(ver);
        resp.getWriter().println(env);

        Optional<String> microserviceName = ConfigurationUtil.getInstance().get("kumuluzee.name");
        microserviceName.ifPresent(s -> writer.println("Izpis generiran v mikrostoritvi " + s + "\n"));

        NinjasObj obj;
        obj = calorieNinjasZrno.vrniNutritionIzdelka("tomato");
        writer.println(obj);
        //izdelki.stream().forEach(u->writer.append(u.toString()+ "\n"));
        /*List<Izdelek> izdelki;
        izdelki = izdelkiZrno.getIzdelki();
        writer.println(izdelki);
        //izdelki.stream().forEach(u->writer.append(u.toString()+ "\n"));

        //ustvari kosarico
        Kosarica kosarica = upravljanjeKosariceZrno.ustvariKosarico();

        //dodaj v kosarico
        KosaricaIzdelekDto kosaricaIzdelekDto = new KosaricaIzdelekDto();
        kosaricaIzdelekDto.setIzdelek_id(1);
        kosaricaIzdelekDto.setKosarica_id(1);
        KosaricaIzdelekDto kosaricaIzdelekDto2 = new KosaricaIzdelekDto();
        kosaricaIzdelekDto2.setIzdelek_id(2);
        kosaricaIzdelekDto2.setKosarica_id(1);

        kosarica = upravljanjeKosariceZrno.dodajIzdelekVKosarico(kosaricaIzdelekDto);
        kosarica = upravljanjeKosariceZrno.dodajIzdelekVKosarico(kosaricaIzdelekDto2);

        //izracun cene kosarice
        //v trgovini 1
        KosaricaTrgovinaDto kosaricaTrgovinaDto = new KosaricaTrgovinaDto();

        Integer cena = upravljanjeKosariceZrno.izracunajCenoKosariceVTrgovini(1, 1);


        //vrni seznam najpopularnejsih izdelkov
        writer.println("Top 3 izdelki:\n");

        List<Izdelek> izdelkiPopular;
        izdelkiPopular = upravljanjeIzdelkovZrno.vrniNajpopularnejše();
        writer.println(izdelkiPopular);
        writer.println("\n\n");

        //vrni seznam izdelkov v kategoriji

        writer.println("Izdelki v kategoriji Hrana");
        List<Izdelek> izdelkiKategorija;
        izdelkiKategorija = upravljanjeIzdelkovZrno.vrniSeznamIzdelkovVKategoriji(2);
        writer.println(izdelkiKategorija);
        writer.println("\n\n");

    */
    }
}