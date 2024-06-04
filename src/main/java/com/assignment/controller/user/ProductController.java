package com.assignment.controller.user;

import com.assignment.dto.ShoppingCartDTO;
import com.assignment.entity.*;
import com.assignment.exception.BrandsNotFoundException;
import com.assignment.exception.ProductNotFoundException;
import com.assignment.service.*;
import com.assignment.utils.EntityDtoUtils;
import com.assignment.utils.SessionAtrr;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("san-pham")
public class ProductController {

    @Autowired
    ProductsService productsService;

    @Autowired
    ProductTypeService productTypeService;

    @Autowired
    BrandsService brandsService;

    @Autowired
    ShoppingCartService cartServer;

    @Autowired
    CategoryService categoryService;

    @Autowired
    HttpSession session;

    @GetMapping
    public String showProducts(@RequestParam(name = "ten-san-pham", required = false) String search_product, Model model) {
        List<Categorys> categories = new ArrayList<>(); // Tạo danh sách trống ban đầu


        if (search_product == null || search_product.isEmpty()) {
            // Nếu không có giá trị tìm kiếm, hiển thị tất cả sản phẩm theo danh mục
            categories = getAllCategoriesWithProducts();
        } else {
            // Nếu có giá trị tìm kiếm, thực hiện tìm kiếm theo tên sản phẩm
            List<Products> searchResults = productsService.findByProductNameContaining(search_product);
            if (!searchResults.isEmpty()) {
                // Nếu tìm thấy kết quả, lấy danh mục và loại sản phẩm tương ứng
                for (Products product : searchResults) {
                    Categorys category = categoryService.findByCategoryId(product.getProductTypesByProductTypeId().getCategorysByCategoryId().getCategoryId());
                    if (!categories.contains(category)) {
                        categories.add(category);
                    }
                }
            }
        }

        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("categories_productsType_products", categories);
        return "views/user/page/product";
    }


    public List<Categorys> getAllCategoriesWithProducts() {
        List<Categorys> categories = categoryService.findAll();
        for (Categorys category : categories) {
            List<ProductTypes> productTypes = productTypeService.findByCategoryId(category.getCategoryId());
            for (ProductTypes productType : productTypes) {
                List<Products> products = productsService.findByProductTypeId(productType.getProductTypeId());
                productType.setProductsByProductTypeId(products);
            }
            category.setProductTypesByCategoryId(productTypes);
        }
        return categories;
    }

    public boolean isProductListInCategoryNotEmpty(List<Categorys> categories, long categoryId) {
        for (Categorys category : categories) {
            if (category.getCategoryId() == categoryId) {
                for (ProductTypes productType : category.getProductTypesByCategoryId()) {
                    if (productType.getProductsByProductTypeId() != null && !productType.getProductsByProductTypeId().isEmpty()) {
                        return true;
                    }
                }
                break;
            }
        }
        return false;
    }

    @GetMapping("san-pham-chi-tiet")
    public String showProductDetails(@RequestParam("ma-san-pham") String productId, Model model) throws ProductNotFoundException {
        if (!productId.isEmpty()) {
            Optional<Products> product = productsService.findById(productId);
            model.addAttribute("product", product.get());
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("products", productsService.findByProductTypeId(1));
        }
        return "views/user/page/product_details";
    }

    @GetMapping("loai-san-pham")
    public String showProductType(@RequestParam("ma-loai") long ProductTypeId, Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "views/user/page/product-type";
    }

    @GetMapping("loai-danh-muc")
    public String showCategories(@RequestParam("danh-muc") long categoryId, Model model) {
        List<Products> filteredProducts = productsService.findByCategoryId(categoryId);

        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("productByCategoryId", filteredProducts);
        model.addAttribute("productsTypeByCategoryId", productTypeService.findByCategoryId(categoryId));
        model.addAttribute("selectedCategory", categoryId);
        return "views/user/page/product-type";
    }

    @GetMapping("them-vao-gio-hang/{id}")
    public String addProductIntoCart(@PathVariable String id) {
        ShoppingCartDTO cartDTO = new ShoppingCartDTO();

        Accounts accounts = (Accounts) session.getAttribute(SessionAtrr.CURRENT_USER);
        Products products = productsService.findByIdAddToCart(id);

        if (products != null) {
            ShoppingCart existingCartItem = cartServer.findProductExits(accounts.getAccountId(), products.getProductId());

            if (existingCartItem != null) {
                existingCartItem.setAmount(existingCartItem.getAmount() + 1);
                cartServer.save(existingCartItem);
                session.setAttribute("toastSuccess", "Tăng số lượng thành công !");

            } else {
                cartDTO.setAccountId(accounts.getAccountId());
                cartDTO.setProductId(products.getProductId());
                cartDTO.setAmount(1);
                cartDTO.setPrice(products.getPrice());
                cartDTO.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                ShoppingCart shoppingCart = EntityDtoUtils.convertToEntity(cartDTO, ShoppingCart.class);
                cartServer.save(shoppingCart);
                session.setAttribute("toastSuccess", "Thêm sản phẩm thành công !");
            }
        }
        return "redirect:/gio-hang";
    }

    @GetMapping("load-products")
    public ResponseEntity<?> loadProduct(@RequestParam("danh-muc") long categoryId, @RequestParam(name = "productType", required = false) long productTypeId) {
        List<Products> filteredProducts;
        if (productTypeId == -1) {
            filteredProducts = productsService.findByCategoryId(categoryId);
        } else {
            filteredProducts = productsService.findProductsByCategoryAndTypeName(categoryId, productTypeId);
        }
        return ResponseEntity.ok().body(filteredProducts);
    }

    @GetMapping("get-brand-name")
    public ResponseEntity<String> getBrandName(@RequestParam("brandId") String brandId) throws BrandsNotFoundException {
        Optional<Brands> brand = brandsService.findById(brandId);
        if (brand != null) {
            return ResponseEntity.ok(brand.get().getBrandName());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
