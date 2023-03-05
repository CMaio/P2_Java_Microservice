package cat.tecncampus.productService.api;


import cat.tecncampus.productService.application.ProductServicePersistence;
import cat.tecncampus.productService.domain.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController
public class ApiController {

    private ProductServicePersistence persistence;

    public ApiController(ProductServicePersistence persistence) {
        this.persistence = persistence;
    }

    @GetMapping("/products")
    public List<ProductService> getProducts() {
        return persistence.getAllProducts();
    }

    @GetMapping("/products/{id}")
    public ProductService getProduct(@PathVariable String id){


        return persistence.getProduct(id);
    }

    @PostMapping("/products")
    public ProductService createProduct(@RequestBody ProductService productService,
                                        @RequestParam(value = "delay",required = false,defaultValue = "0") int delay,
                                        @RequestParam(value = "faultRatio",required = false,defaultValue = "0") int faultRatio){

        if (delay > 0) simulatedDelay(delay);
        if (faultRatio > 0) throwErrorIfBadLuck(faultRatio);
        System.out.println("name: " + productService.getName() + " description: " + productService.getDescription() + " weight: "+ productService.getWeight());
        productService.setId(UUID.randomUUID().toString());
        return persistence.createProduct(productService);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable String id,
                              @RequestParam(value = "delay",required = false,defaultValue = "0") int delay,
                              @RequestParam(value = "faultRatio",required = false,defaultValue = "0") int faultRatio){

        if (delay > 0) simulatedDelay(delay);
        if (faultRatio > 0) throwErrorIfBadLuck(faultRatio);
        persistence.deleteProductService(id);
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
