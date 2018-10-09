package com.deepexi.service.rpc;

import com.alibaba.boot.hsf.annotation.HSFProvider;
import com.deepexi.api.ProductService;
import com.deepexi.domain.eo.Product;
import com.deepexi.repository.ProductRepository;
import com.github.optimus.config.Payload;
import com.github.optimus.constant.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@HSFProvider(serviceInterface = ProductService.class)
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Object getProductList() {
        return productRepository.findAll();
    }

    public Object getProductById(Integer id) {
        return productRepository.findOne(id);
    }

    public Object createProduct(Product product) {
        return productRepository.save(product);
    }

    public Object deleteProductById(Integer id) {
        productRepository.delete(id);
        return true;
    }
}
