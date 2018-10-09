package com.deepexi.service.rest;

import com.github.optimus.config.Payload;
import com.github.optimus.constant.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.deepexi.api.ProductService;
import com.deepexi.domain.eo.Product;
import com.deepexi.service.rest.api.ProductRestService;

import javax.ws.rs.*;

@Service("productRestService")
@Path("/api/v1/products")
@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public class ProductRestServiceImpl implements ProductRestService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductService productService;

    @GET
    @Path("/")
    public Payload getProductList() {
        return new Payload(productService.getProductList());
    }

    @GET
    @Path("{id : \\d+}")
    public Payload getProductById(@PathParam("id") Integer id) {
        return new Payload(productService.getProductById(id));
    }

    @POST
    @Path("/")
    public Payload createProduct(Product product) {
        return new Payload(productService.createProduct(product));
    }

    @PUT
    @Path("{id : \\d+}")
    public Payload updateProductById(@PathParam("id") Integer id, Product product) {
        return new Payload(null);
    }

    @DELETE
    @Path("{id : \\d+}")
    public Payload deleteProductById(@PathParam("id") Integer id) {
        return new Payload(productService.deleteProductById(id));
    }
}
