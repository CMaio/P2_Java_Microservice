package cat.tecnocampus.productcomposite.api;

import cat.tecnocampus.productcomposite.application.ApplicationController;
import cat.tecnocampus.productcomposite.domain.ProductComposite;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiController {
    private ApplicationController applicationController;

    public ApiController(ApplicationController applicationController) {
        this.applicationController = applicationController;
    }

    @GetMapping("/productsc")
    public List<ProductComposite> getProducts() {
        return applicationController.getProductsComposite();
    }

    @GetMapping("/productsc/{id}")
    public ProductComposite getProduct(@PathVariable String id) {
        return applicationController.getProductsComposite(id);
    }

    @DeleteMapping("/productsc/{id}")
    public void deleteProduct(@PathVariable String id,
                              @RequestParam(value = "delay",required = false,defaultValue = "0") int delay,
                              @RequestParam(value = "faultRatio",required = false,defaultValue = "0") int faultRatio) {
        applicationController.deleteProductComposite(id);
    }

    @PostMapping("/productsc")
    public void createProduct(@RequestBody ProductComposite productComposite,
                              @RequestParam(value = "delay",required = false,defaultValue = "0") int delay,
                              @RequestParam(value = "faultRatio",required = false,defaultValue = "0") int faultRatio) {
        System.out.println("name: " + productComposite.getProduct().getName());
        applicationController.createProductComposite(productComposite);
    }
}
