package si.fri.prpo.skupina8.Interceptorji;

import si.fri.prpo.skupina8.Anotacije.BeleziKlice;
import si.fri.prpo.skupina8.Zrna.BelezenjeKlicevZrno;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Logger;

@Interceptor
@BeleziKlice
@Priority(1)
public class BelezenjeKlicevInterceptor {
    Logger log = Logger.getLogger(BelezenjeKlicevInterceptor.class.getName());

    @Inject
    private BelezenjeKlicevZrno belezenjeKlicevZrno;

    @AroundInvoke
    public Object beleziKlice(InvocationContext ctx) throws Exception{
        String method = ctx.getMethod().getName();
        HashMap<String,Integer> mapa = belezenjeKlicevZrno.getStKlicev();

        belezenjeKlicevZrno.povecaj(mapa,method);
        return ctx.proceed();
    }
}
