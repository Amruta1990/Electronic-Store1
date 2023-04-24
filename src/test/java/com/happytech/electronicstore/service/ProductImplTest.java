package com.happytech.electronicstore.service;

import com.happytech.electronicstore.BaseTest;
import com.happytech.electronicstore.Service.ProductService;
import com.happytech.electronicstore.dtos.PagableResponse;
import com.happytech.electronicstore.dtos.ProductDto;
import com.happytech.electronicstore.entity.Category;
import com.happytech.electronicstore.entity.Product;
import com.happytech.electronicstore.repository.CategoryRepo;
import com.happytech.electronicstore.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductImplTest extends BaseTest {

    @MockBean
    private ProductRepository productRepo;

    @MockBean
    private CategoryRepo categoryRepo;
    @Autowired
    private ProductService productService;
    @Autowired
    private ModelMapper modelMapper;



    Product product1,product2,product3;

    List<Product> products;

    ProductDto productDto;

    Category category;


    @BeforeEach
    public void init()
    {
        product1=Product.builder()
                .productId(1L)

                .title("Cosmetics")
                .price(1000.0).live(true).stock(true)
                .description("All cosmetics are available")
                .quantity(10).discountedPrice("20%").build();
        product2=Product.builder()

                .title("Cosmetics")
                .price(1000.0).live(true).stock(true)
                .description("All cosmetics are available")
                .quantity(10).discountedPrice("20%").build();
        product3=Product.builder()

                .title("Cometics")
                .price(1000.0).live(true).stock(true)
                .description("All cosmetics are available")
                .quantity(10).discountedPrice("20%").build();

       /* category=Category.builder()
                .title("Cosmetics").
                description("All Products").
                coverImage("abc.png").build();
//                products(products).build();*/

        productDto= ProductDto.builder()

                .title("Cometics")
                .price(1000).live(true).stock(true)
                .description("All cosmetics are avilable")
                .quantity(10).discountedPrice("20%").build();

        products=new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);

    }

    @Test
    public void createProductTest()
    {
        Mockito.when(productRepo.save(Mockito.any())).thenReturn(product1);

        ProductDto product = productService.create(modelMapper.map(product1, ProductDto.class));
        Assertions.assertNotNull(product);
        Assertions.assertEquals(product1.getQuantity(),product.getQuantity());


    }
  /* @Test
    public void createProductwithCategoryTest()
    {
        Long catid=1l;
        Mockito.when(categoryRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(category));
        Mockito.when(productRepo.save(Mockito.any())).thenReturn(product1);
        ProductDto productwithCategory = productService.createProductwithCategory(catid, productDto);
        Assertions.assertEquals(product1.getBrand(),productwithCategory.getBrand());

    }*/
    @Test
    public void updateProducts()
    {
        Long id=1l;
        Mockito.when(productRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(product1));
        Mockito.when(productRepo.save(Mockito.any())).thenReturn(product1);
        ProductDto productDtos = productService.update(productDto, id);

        Assertions.assertNotNull(productDtos);
        Assertions.assertEquals(product1.getQuantity(),productDtos.getQuantity());

    }

    @Test
    public void deleteProductTest()
    {
        Long id=10l;
        Mockito.when(productRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(product1));

        productService.delete(id);
        Mockito.verify(productRepo,Mockito.times(1)).delete(product1);

    }

    @Test
    public void getByIdTest()
    {
        Long id=1L;
        Mockito.when(productRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(product1));

        ProductDto productbyid = productService.get(id);

        Assertions.assertEquals(product1.getProductId(),productbyid.getProductId());

    }
    @Test
    public void getAllProductsTest()
    {
        Page page=new PageImpl(products);
        Mockito.when(productRepo.findAll((Pageable) Mockito.any())).thenReturn(page);

        PagableResponse<ProductDto> allProducts = productService.getAll(1, 5, "brand", "asc");

        Assertions.assertEquals(3,allProducts.getContent().size());

    }
    @Test
    public void searchByTitleTest()
    {

        // String title ="Cosmetics";
        Mockito.when(productRepo.findByTitleContaining(Mockito.anyString(),Mockito.any())).thenReturn(new PageImpl(products));

        PagableResponse<ProductDto> searchByTitle = productService.searchByTitle("Cometics", 1, 5, "title", "asc");

        Assertions.assertEquals(3,searchByTitle.getContent().size(),"Not present data!");
    }


    @Test
    public void searchByLive()
    {

        // String title ="Cosmetics";
        Mockito.when(productRepo.findByLiveTrue(Mockito.any())).thenReturn(new PageImpl(products));

        PagableResponse<ProductDto> searchByLive = productService.getAllLive(true, 1, 5, "title", "asc");

        Assertions.assertEquals(3,searchByLive.getContent().size(),"Not present data!");
    }

}
