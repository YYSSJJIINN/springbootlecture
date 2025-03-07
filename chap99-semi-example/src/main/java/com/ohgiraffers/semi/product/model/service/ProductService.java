package com.ohgiraffers.semi.product.model.service;

import com.ohgiraffers.semi.common.paging.Pagenation;
import com.ohgiraffers.semi.product.model.dao.ProductMapper;
import com.ohgiraffers.semi.product.model.dto.CategoryDTO;
import com.ohgiraffers.semi.product.model.dto.ProductDTO;
import com.ohgiraffers.semi.common.util.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.HashMap;

@Service
public class ProductService {
    
    @Value("${image.image-dir}")
    private String IMAGE_DIR;
    
    @Value("${image.image-url}")
    private String IMAGE_URL;
    
    private final ProductMapper productMapper;
    
    @Autowired
    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }
    
    public List<ProductDTO> findAllProducts() {
        List<ProductDTO> products = productMapper.findAllProducts();
        
        // 이미지 URL 완성 (상대 경로)
        for(ProductDTO product : products) {
            product.setProductImageUrl(IMAGE_URL + product.getProductImageUrl());
        }
        
        return products;
    }
    
    public List<ProductDTO> findProductsByCategory(String category) {
        List<ProductDTO> products = productMapper.findProductsByCategory(category);
        
        // 이미지 URL 완성
        for(ProductDTO product : products) {
            product.setProductImageUrl(IMAGE_URL + product.getProductImageUrl());
        }
        
        return products;
    }
    
    public ProductDTO findProductByCode(int productCode) {
        ProductDTO product = productMapper.findProductByCode(productCode);
        
        if(product != null) {
            // 이미지 URL 완성
            product.setProductImageUrl(IMAGE_URL + product.getProductImageUrl());
        }
        
        return product;
    }

    @Transactional
    public void createProduct(ProductDTO product, MultipartFile productImage) throws Exception {

        // 이미지 저장
        if (!productImage.isEmpty()) {

            String imageName = UUID.randomUUID().toString().replace("-", "");
            String replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR, imageName, productImage);
            
            product.setProductImageUrl(replaceFileName);
        }
        
        // 상품 정보 저장
        product.setProductOrderable("Y");  // 기본값 설정
        productMapper.insertProduct(product);
    }

    public List<CategoryDTO> findAllCategories() {
        return productMapper.findAllCategories();
    }

    @Transactional
    public void updateProduct(ProductDTO product, MultipartFile productImage) throws Exception {

        // 기존 상품 정보를 조회하여 이미지 URL 가져오기
        ProductDTO existingProduct = productMapper.findProductByCode(product.getProductCode());
        String originalImageUrl = existingProduct.getProductImageUrl();
        System.out.println("기존 이미지 URL: " + originalImageUrl);  // 디버깅용
        
        try {
            // 이미지가 새로 업로드된 경우
            if (productImage != null && !productImage.isEmpty()) {

                // 기존 이미지가 있다면 먼저 삭제
                if (originalImageUrl != null && !originalImageUrl.isEmpty()) {

                    /* 상품 정보를 수정할 때, 기존 상품 이미지가 아니라 새로운 이미지로 교체하려는 경우:
                     * 먼저 새롭게 수정하려는 이미지를 저장 시도한다.
                     * 새 이미지가 성공적으로 저장되었다면 기존 이미지는 삭제해줘야 한다.
                     * 왜냐하면 기존 이미지를 먼저 삭제했다가 새 이미지 저장을 실패하면 어떠한 이미지 파일도 남지 않기 때문이다.
                     * 이미지 저장은 FileUploadUtils의 saveFile 정적 메서드로 구현해드렸으니,
                     * 이미지 삭제는 deleteFile 메서드로 직접 구현해보세요~
                     * */
                    FileUploadUtils.deleteFile();
                }
                
                // 새 이미지 저장
                String imageName = UUID.randomUUID().toString().replace("-", "");
                String newImageUrl = FileUploadUtils.saveFile(IMAGE_DIR, imageName, productImage);
                System.out.println("새 이미지 저장됨: " + newImageUrl);
                
                // 상품 정보에 새 이미지 URL 설정
                product.setProductImageUrl(newImageUrl);
            } else {
                // 이미지가 변경되지 않은 경우 기존 이미지 URL 유지
                product.setProductImageUrl(originalImageUrl);
            }
            
            // 상품 정보 업데이트
            productMapper.updateProduct(product);
            
        } catch (Exception e) {
            // 새 이미지 저장 후 업데이트 실패 시, 새로 저장된 이미지도 삭제
            if (productImage != null && !productImage.isEmpty() && 
                product.getProductImageUrl() != null &&
                originalImageUrl != null &&
                !product.getProductImageUrl().equals(originalImageUrl)) {
                /* 이미지 삭제 기능 직접 구현해보세요~ */
                FileUploadUtils.deleteFile();
            }
            throw e;
        }
    }

    @Transactional
    public void deleteProduct(int productCode) throws Exception {
        // 상품 이미지 정보 조회
        ProductDTO product = productMapper.findProductByCode(productCode);
        
        if (product == null) {
            throw new IllegalArgumentException("상품이 존재하지 않습니다.");
        }
        
        // 이미지 파일 삭제
        if (product.getProductImageUrl() != null) {
            /* 이미지 삭제 기능 직접 구현해보세요~ */
            FileUploadUtils.deleteFile();
        }
        
        // 상품 정보 삭제
        productMapper.deleteProduct(productCode);
    }

    public List<ProductDTO> searchProducts(String keyword) {
        List<ProductDTO> products = productMapper.searchProducts(keyword);
        
        // 이미지 URL 완성
        for(ProductDTO product : products) {
            product.setProductImageUrl(IMAGE_URL + product.getProductImageUrl());
        }
        
        return products;
    }

    public Map<String, Object> getAllProducts(Pagenation pagenation) {
        int totalItems = productMapper.selectAllTotalCount();
        pagenation.setPageInfo(totalItems);
        
        List<ProductDTO> products = productMapper.selectAllProductsWithPaging(pagenation);
        
        // 이미지 URL 완성
        for(ProductDTO product : products) {
            product.setProductImageUrl(IMAGE_URL + product.getProductImageUrl());
        }
        
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("products", products);
        resultMap.put("pagenation", pagenation);
        
        return resultMap;
    }

    public Map<String, Object> getProductList(int categoryCode, Pagenation pagenation) {
        int totalItems = productMapper.selectTotalCount(categoryCode);
        pagenation.setPageInfo(totalItems);
        
        List<ProductDTO> products = productMapper.selectProductListWithPaging(categoryCode, pagenation);
        
        // 이미지 URL 완성 처리 추가
        for(ProductDTO product : products) {
            product.setProductImageUrl(IMAGE_URL + product.getProductImageUrl());
        }
        
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("products", products);
        resultMap.put("pagenation", pagenation);
        
        return resultMap;
    }
} 