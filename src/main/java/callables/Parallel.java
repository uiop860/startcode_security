package callables;

import DTO.DemoDTO;
import com.google.gson.Gson;
import org.jsoup.Jsoup;

import java.util.concurrent.Callable;

public class Parallel implements Callable<DemoDTO> {

    private String host;
    private boolean isCalled = false;
    private Gson gson = new Gson();

    public Parallel(String host) {
        this.host = host;
    }

    @Override
    public DemoDTO call() throws Exception {
        DemoDTO response = null;

        if(isCalled){
            return null;
        }
        isCalled= true;
        try {
            response = gson.fromJson(Jsoup.connect(host).ignoreContentType(true).execute().body(), DemoDTO.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return response;
    }
}
