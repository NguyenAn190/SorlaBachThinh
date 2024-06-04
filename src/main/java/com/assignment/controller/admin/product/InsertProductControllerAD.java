package com.assignment.controller.admin.product;

import com.assignment.dto.ProductImagesDTO;
import com.assignment.dto.ProductsDTO;
import com.assignment.entity.*;
import com.assignment.service.*;
import com.assignment.utils.EntityDtoUtils;
import com.assignment.utils.ReplaceUtils;
import com.assignment.utils.SessionAtrr;
import com.assignment.utils.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/them-san-pham")
public class InsertProductControllerAD {

    @Autowired
    ProductsService productsService;

    @Autowired
    BrandsService brandsService;

    @Autowired
    HistoryService historyService;

    @Autowired
    HttpSession session;

    @Autowired
    HttpServletRequest request;

    @Autowired
    ProductTypeService productTypeService;

    @Autowired
    ProductImagesService productImagesService;
    List<Products> productsList = new ArrayList<>();

    @RequestMapping(value = "", method = {RequestMethod.GET, RequestMethod.POST})
    public String addProducts(@ModelAttribute ProductsDTO productsDT, @ModelAttribute ProductImagesDTO productImagesDTO, Model model) {
        model.addAttribute("productsDTO", new ProductsDTO());
        model.addAttribute("listProductID", productsService.findAll());
        return "views/admin/page/crud/product/products-add";
    }

    @RequestMapping("/save")
    public String saveProducts(@Valid @ModelAttribute ProductsDTO productsDTO, BindingResult bindingResult, @RequestParam("file01") MultipartFile file01, @RequestParam("file02") MultipartFile file02, @RequestParam("file03") MultipartFile file03, @RequestParam("file04") MultipartFile file04) {
        ProductImagesDTO productImagesDTO = new ProductImagesDTO();

        if (!file01.isEmpty() && !file02.isEmpty() && !file03.isEmpty() && !file04.isEmpty()) {
            String price = request.getParameter("price");
            String saleOff = request.getParameter("saleOff");

            productsDTO.setPrice(ReplaceUtils.replacePrice(price));
            productsDTO.setSaleOff(ReplaceUtils.replacePrice(saleOff));
            productsDTO.setDateCreated(new Timestamp(System.currentTimeMillis()));
            productsDTO.setProductId(productIdValue());
            productsDTO.setIsStatusDelete("Đang bán");

            Products save = EntityDtoUtils.convertToEntity(productsDTO, Products.class);

            productsService.save(save);

            if (!file01.isEmpty()) {
                Path path = Paths.get("src/main/resources/static/upload/");

                if (!path.toFile().exists()) {
                    path.toFile().mkdirs();
                }
                try {
                    InputStream inputStream = file01.getInputStream();
                    Files.copy(inputStream, path.resolve(file01.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String fileName = file01.getOriginalFilename();

                productImagesDTO.setImageId("IMG01_" + productsDTO.getProductId());
                productImagesDTO.setProductId(productsDTO.getProductId());
                productImagesDTO.setImagePath(fileName);

                ProductImages productImages = EntityDtoUtils.convertToEntity(productImagesDTO, ProductImages.class);
                productImagesService.insert(productImages);
            }

            if (!file02.isEmpty()) {
                Path path = Paths.get("src/main/resources/static/upload/");

                if (!path.toFile().exists()) {
                    path.toFile().mkdirs();
                }
                try {
                    InputStream inputStream = file02.getInputStream();
                    Files.copy(inputStream, path.resolve(file02.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String fileName = file02.getOriginalFilename();

                productImagesDTO.setImageId("IMG02_" + productsDTO.getProductId());
                productImagesDTO.setProductId(productsDTO.getProductId());
                productImagesDTO.setImagePath(fileName);

                ProductImages productImages = EntityDtoUtils.convertToEntity(productImagesDTO, ProductImages.class);

                productImagesService.insert(productImages);
            }

            if (!file03.isEmpty()) {
                Path path = Paths.get("src/main/resources/static/upload/");

                if (!path.toFile().exists()) {
                    path.toFile().mkdirs();
                }
                try {
                    InputStream inputStream = file03.getInputStream();
                    Files.copy(inputStream, path.resolve(file03.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String fileName = file03.getOriginalFilename();

                productImagesDTO.setImageId("IMG03_" + productsDTO.getProductId());
                productImagesDTO.setProductId(productsDTO.getProductId());
                productImagesDTO.setImagePath(fileName);

                ProductImages productImages = EntityDtoUtils.convertToEntity(productImagesDTO, ProductImages.class);

                productImagesService.insert(productImages);
            }

            if (!file04.isEmpty()) {
                Path path = Paths.get("src/main/resources/static/upload/");

                if (!path.toFile().exists()) {
                    path.toFile().mkdirs();
                }
                try {
                    InputStream inputStream = file04.getInputStream();
                    Files.copy(inputStream, path.resolve(file04.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String fileName = file04.getOriginalFilename();

                productImagesDTO.setImageId("IMG04_" + productsDTO.getProductId());
                productImagesDTO.setProductId(productsDTO.getProductId());
                productImagesDTO.setImagePath(fileName);

                ProductImages productImages = EntityDtoUtils.convertToEntity(productImagesDTO, ProductImages.class);

                productImagesService.insert(productImages);
            }

            SessionUtils.setAttribute("toastSuccess", "Thêm sản phẩm thành công !");
            historyService.addHistoryUpdateProducts((Accounts) session.getAttribute(SessionAtrr.CURRENT_ADMIN), "Thêm sản phẩm");
        } else {
            return "views/admin/page/crud/product/products-add";
        }
        return "redirect:/admin/san-pham";
    }

    @ModelAttribute("brandsOptions")
    public List<Brands> brandsOptions() {
        return brandsService.findAll();
    }

    @ModelAttribute("productTypeOptions")
    public List<ProductTypes> productTypeOptions() {
        return productTypeService.findAll();
    }

    @ModelAttribute("productIdValue")
    public String productIdValue() {
        productsList = productsService.findAll();
        String productId;
        if (productsList.isEmpty()) {
            productId = "SP0001";
        } else {
            String input = productsList.get(productsList.size() - 1).getProductId();
            String prefix = input.substring(0, 2);
            int number = Integer.parseInt(input.substring(2));
            String newNumber = String.format("%04d", number + 1); // Thay đổi 1 thành number + 1 và "%06d" thành "%04d"

            productId = prefix + newNumber;
        }
        return productId;
    }
}
