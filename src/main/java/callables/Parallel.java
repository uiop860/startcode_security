package callables;

import utils.HttpUtils;
import java.util.concurrent.Callable;

public class Parallel implements Callable<String> {

    private String host;

    public Parallel(String host) {
        this.host = host;
    }

    @Override
    public String call() throws Exception {
        String response = null;

        response = HttpUtils.fetchData(host);

        return response;
    }
}
