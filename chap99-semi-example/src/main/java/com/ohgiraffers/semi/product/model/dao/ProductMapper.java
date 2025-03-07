package com.ohgiraffers.semi.product.model.dao;

import com.ohgiraffers.semi.common.paging.Pagenation;
import com.ohgiraffers.semi.product.model.dto.CategoryDTO;
import com.ohgiraffers.semi.product.model.dto.ProductDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductMapper {
    
    List<ProductDTO> findAllProducts();
    
    List<ProductDTO> findProductsByCategory(String category);
    
    ProductDTO findProductByCode(int productCode);
    
    void insertProduct(ProductDTO product);
    
    List<CategoryDTO> findAllCategories();
    
    void updateProduct(ProductDTO product);
    
    void deleteProduct(int productCode);
    
    void updateProductStock(ProductDTO product);
    
    List<ProductDTO> searchProducts(String keyword);

    List<ProductDTO> searchProductsWithPaging(String keyword, Pagenation pagenation);

    int searchTotalCount(String keyword);

    List<ProductDTO> selectProductListWithPaging(int categoryCode, Pagenation pagenation);

    int selectTotalCount(int categoryCode);

    List<ProductDTO> selectAllProductsWithPaging(Pagenation pagenation);

    int selectAllTotalCount();
} 