package cat.tecnocampus.recommendations.api;

import cat.tecnocampus.recommendations.application.ApplicationController;
import cat.tecnocampus.recommendations.domain.Recommendation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
public class ApiController {

    private ApplicationController applicationController;

    public ApiController(ApplicationController applicationController) {
        this.applicationController = applicationController;
    }

    @GetMapping("/recommendations/{productId}")
    public List<Recommendation> getRecommendations(@PathVariable String productId){
        return applicationController.getRecommendation(productId);
    }

    @PostMapping("/recommendations")
    public void createRecommendation(@RequestBody Recommendation recommendation,
                                     @RequestParam(value = "delay",required = false,defaultValue = "0") int delay,
                                     @RequestParam(value = "faultRatio",required = false,defaultValue = "0") int faultRatio){

        if (delay > 0) simulatedDelay(delay);
        if (faultRatio > 0) throwErrorIfBadLuck(faultRatio);
        System.out.println("name: " + recommendation.getId() + " for the product with ID: " + recommendation.getProductId());
        applicationController.createRecommendation(recommendation);
    }

    @DeleteMapping("/recommendations/{productId}")
    public void deleteRecommendation(@PathVariable String productId,
                                     @RequestParam(value = "delay",required = false,defaultValue = "0") int delay,
                                     @RequestParam(value = "faultRatio",required = false,defaultValue = "0") int faultRatio){

        if (delay > 0) simulatedDelay(delay);
        if (faultRatio > 0) throwErrorIfBadLuck(faultRatio);
        applicationController.deleteRecommendation(productId);
    }



    private void simulatedDelay(int delay){
        System.out.println("Sleeping for "+delay+" seconds...");
        try{Thread.sleep(delay*1000);} catch(InterruptedException e){}
        System.out.println("Moving on...");
    }

    private void throwErrorIfBadLuck(int faultRatio){
        int randomThreshold = getRandomNumber(1,100);
        if(faultRatio<randomThreshold){
            System.out.println("We got lucky, no error occurred ...");
        }else{
            System.out.println("Bad luck, an error occurred ... "+faultRatio);
            throw new RuntimeException("Something went wrong...");
        }
    }

    private final Random randomNumberGenerator = new Random();
    private int getRandomNumber(int min, int max){
        if (max < min){
            throw new RuntimeException("Max must be greater than min");
        }
        return randomNumberGenerator.nextInt((max-min)+1)+min;
    }
}
