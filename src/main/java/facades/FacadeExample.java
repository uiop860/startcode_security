package facades;

import DTO.ChuckJokeJsonDTO;
import DTO.UserJsonDTO;
import callables.Parallel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class FacadeExample {

    private static FacadeExample instance;
    private static EntityManagerFactory emf;
    private Gson gson = new Gson();
    
    //Private Constructor to ensure Singleton
    private FacadeExample() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static FacadeExample getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new FacadeExample();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    //TODO Remove/Change this before use
    public long getRenameMeCount(){
        EntityManager em = emf.createEntityManager();
        try{
            long renameMeCount = (long)em.createQuery("SELECT COUNT(r) FROM RenameMe r").getSingleResult();
            return renameMeCount;
        }finally{  
            em.close();
        }
    }

    public List<Object> getDataFromFiveServers() throws ExecutionException, InterruptedException {

        String[] hosts = {
                "https://api.chucknorris.io/jokes/random",
                "https://jsonplaceholder.typicode.com/users",
        };

        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<String>> futures = new ArrayList<>();
        List<String> data = new ArrayList<>();
        List<Object> response = new ArrayList<>();

        for (String s: hosts) {
            Future future = executor.submit(new Parallel(s));
            futures.add(future);
        }

        //Get the results
        for (Future<String> future : futures) {
            String dto = future.get();
            data.add(dto);
        }

        for (int i = 0; i < data.size(); i++) {
            switch (i){
                case 1:
                    ChuckJokeJsonDTO chuckDto = gson.fromJson(data.get(i), ChuckJokeJsonDTO.class);
                    response.add(chuckDto);
                    break;
                case 2:
                    System.out.println(data.get(i));
//                    List<UserJsonDTO> userDto = gson.fromJson(data.get(i), UserJsonDTO.class);
//                    response.add(userDto);
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
            }

        }

        return response;
    }

}
