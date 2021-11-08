package facades;

import DTO.DemoDTO;
import callables.Parallel;
import entities.RenameMe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class FacadeExample {

    private static FacadeExample instance;
    private static EntityManagerFactory emf;
    
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

    public List<DemoDTO> getDataFromFiveServers() throws ExecutionException, InterruptedException {

        String[] hosts = {
                "https://api.chucknorris.io/jokes/random"
        };

        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<DemoDTO>> futures = new ArrayList<>();
        List<DemoDTO> responses = new ArrayList<>();

        for (String s: hosts) {
            Future future = executor.submit(new Parallel(s));
            futures.add(future);
        }

        //Get the results
        for (Future<DemoDTO> future : futures) {
            DemoDTO dto = future.get();
            responses.add(dto);

        }

        return responses;
    }

}
