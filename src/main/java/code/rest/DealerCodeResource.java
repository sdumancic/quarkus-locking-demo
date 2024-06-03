package code.rest;

import code.domain.DealerCode;
import code.service.DealerCodeService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Path("/dealer-codes")
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class DealerCodeResource {

    @Inject
    DealerCodeService dealerCodeService;

    @GET
    @Path("/dealers/{dealerId}")
    public Response getDealerCodeByYearAndDealerId(@QueryParam("year") Short year, @PathParam("dealerId") Integer dealerId) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        // Define the task to be executed in parallel
        Callable<DealerCode> task = () -> dealerCodeService.findByYearAndDealerId(year, dealerId);

        // Submit 5 parallel tasks
        Future<DealerCode> future1 = executorService.submit(task);
        Future<DealerCode> future2 = executorService.submit(task);
        Future<DealerCode> future3 = executorService.submit(task);
        Future<DealerCode> future4 = executorService.submit(task);
        Future<DealerCode> future5 = executorService.submit(task);

        // Retrieve and process the results
        DealerCode result1 = null;
        DealerCode result2 = null;
        DealerCode result3 = null;
        DealerCode result4 = null;
        DealerCode result5 = null;
        try {
            result1 = future1.get();
            result2 = future2.get();
            result3 = future3.get();
            result4 = future4.get();
            result5 = future5.get();

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            // Shut down the executor service
            executorService.shutdown();
        }

        log.info(result1 != null ? result1.getLastCode().toString() : "N/A");
        log.info(result2 != null ? result2.getLastCode().toString() : "N/A");
        log.info(result3 != null ? result3.getLastCode().toString() : "N/A");
        log.info(result4 != null ? result4.getLastCode().toString() : "N/A");
        log.info(result5 != null ? result5.getLastCode().toString() : "N/A");

        return Response.ok("ok").build();

    }
}
