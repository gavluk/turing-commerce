package ua.com.gavluk.turing.ecommerce.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.gavluk.turing.ecommerce.core.Shipping;
import ua.com.gavluk.turing.ecommerce.core.ShippingRegion;
import ua.com.gavluk.turing.ecommerce.core.ShippingService;
import ua.com.gavluk.turing.ecommerce.exceptions.NotFoundException;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/shipping")
public class ShippingController {

    private final ShippingService service;

    @Autowired
    public ShippingController(ShippingService service) {
        this.service = service;
    }

    @GetMapping("/regions")
    public List<ShippingRegion> findAllShippingRegions() {
        return this.service.findAllRegions();
    }

    public List<Shipping> findAllInRegion(@PathVariable @NotNull @Positive Long regionId) throws NotFoundException {
        ShippingRegion region = this.service.findRegionById(regionId).orElseThrow(
                ()-> new NotFoundException(NotFoundException.SHIPPING_REGION_NOT_FOUND)
        );
        return this.service.findAllInRegion(region);
    }

}
