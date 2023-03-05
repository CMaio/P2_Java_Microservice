package cat.tecnocampus.ReviewService.api;

import cat.tecnocampus.ReviewService.application.ReviewPersistence;
import cat.tecnocampus.ReviewService.domain.Review;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
public class ApiController {

    private ReviewPersistence persistence;

    public ApiController(ReviewPersistence persistence) {
        this.persistence = persistence;
    }

    @GetMapping("/review/{id}")
    public List<Review> getProducts(@PathVariable String id) {
        return persistence.getReviews(id);
    }


    @GetMapping("/review")
    public List<Review> getProducts() {
        return persistence.getReviews();
    }

    @PostMapping("/review")
    public void createReview(@RequestBody Review review,
                             @RequestParam(value = "delay",required = false,defaultValue = "0") int delay,
                             @RequestParam(value = "faultRatio",required = false,defaultValue = "0") int faultRatio){

        if (delay > 0) simulatedDelay(delay);
        if (faultRatio > 0) throwErrorIfBadLuck(faultRatio);
        System.out.println("author: " + review.getAuthor() + " Subject: " + review.getSubject() + " Content: " + review.getContent());
        persistence.createReview(review);
    }

    @DeleteMapping("/review/{id}")
    public void deleteProduct(@PathVariable String id,
                              @RequestParam(value = "delay",required = false,defaultValue = "0") int delay,
                              @RequestParam(value = "faultRatio",required = false,defaultValue = "0") int faultRatio){

        if (delay > 0) simulatedDelay(delay);
        if (faultRatio > 0) throwErrorIfBadLuck(faultRatio);
        persistence.deleteReview(id);
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
