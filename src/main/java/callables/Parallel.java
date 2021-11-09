package callables;

import DTO.DemoDTO;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import utils.HttpUtils;

import java.net.HttpURLConnection;
import java.util.concurrent.Callable;

public class Parallel implements Callable<DemoDTO> {

    private String host;
    private Gson gson = new Gson();

    public Parallel(String host) {
        this.host = host;
    }

    @Override
    public DemoDTO call() throws Exception {
        DemoDTO response = null;

        response = gson.fromJson(HttpUtils.fetchData(host), DemoDTO.class);

        return response;
    }
}
