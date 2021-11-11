package facades;

import DTO.reddit.MainJsonDTO;
import DTO.reddit.RedditJsonDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class RedditFacade {

    private static RedditFacade instance;
    private static EntityManagerFactory emf;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    //Private Constructor to ensure Singleton
    public RedditFacade() {}

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static RedditFacade getRedditExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new RedditFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public String getDataFromUrl(String url) throws IOException {
        String response = null;
        System.out.println(url);
        URLConnection connection = new URL(url).openConnection();
        connection.setRequestProperty("Accept","application/json");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");


        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String i;
        while ((i = br.readLine()) != null)
        {
            System.out.println(i);
            response = i;
        }

        return response;
    }

    public MainJsonDTO getDataFromPopular() throws IOException {

        RedditJsonDTO dto = gson.fromJson(getDataFromUrl("https://www.reddit.com/r/popular/.json"), RedditJsonDTO.class);


        MainJsonDTO data = new MainJsonDTO(dto.getData().getChildren());


        return data;
    }

}
