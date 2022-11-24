package si.fri.prpo.skupina8.Servlet;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import si.fri.prpo.skupina8.Izdelek;
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


        List<Izdelek> izdelki;
        izdelki = izdelkiZrno.getIzdelki();
        writer.println(izdelki);
        //izdelki.stream().forEach(u->writer.append(u.toString()+ "\n"));

    }
}