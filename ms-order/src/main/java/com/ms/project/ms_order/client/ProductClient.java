package com.ms.project.ms_order.client;

import com.ms.project.ms_order.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "ms-product")
public interface ProductClient {

    @GetMapping("/api/products")
    List<ProductResponse> getAllProducts();
}
