package com.assignment.controller.admin;

import com.assignment.dto.ProductImagesDTO;
import com.assignment.entity.Accounts;
import com.assignment.entity.ProductImages;
import com.assignment.exception.ProductImageNotFoundException;
import com.assignment.service.HistoryService;
import com.assignment.service.ProductImagesService;
import com.assignment.service.ProductsService;
import com.assignment.utils.EntityDtoUtils;
import com.assignment.utils.SessionAtrr;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("/admin/them-hinh-anh")
public class AddImageCotroller {

    @Autowired
    ProductImagesService productImagesService;

    @Autowired
    ProductsService productsService;

    @Autowired
    HistoryService historyService;

    @Autowired
    HttpSession session;

    @RequestMapping("")
    public String image_add(@Validated @ModelAttribute ProductImagesDTO productImagesDTO, Model model) {
        model.addAttribute("productImagesDTO", productImagesDTO);
        model.addAttribute("listProductID", productsService.findAll());
        return "views/admin/page/crud/image/image-add";
    }

    @RequestMapping("/xem-hinh-anh/{productId}")
    public ModelAndView showImage(@Validated @ModelAttribute ProductImagesDTO productImagesDTO, @PathVariable("productId") String productId, Model model) throws ProductImageNotFoundException {
        model.addAttribute("listProductID", productsService.findAll());
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("imageProduct", productImagesService.findImgByProductId(productId));

        modelAndView.addObject("selectedProductIdToImage", productId);
        modelAndView.setViewName("views/admin/page/crud/image/image-add");
        return modelAndView;
    }

    @PostMapping("/save")
    public String save(@Validated @ModelAttribute ProductImagesDTO productImagesDTO, @RequestParam("file01") MultipartFile file01, @RequestParam("file02") MultipartFile file02, @RequestParam("file03") MultipartFile file03, @RequestParam("file04") MultipartFile file04, RedirectAttributes redirectAttributes, Model model) throws IOException, ProductImageNotFoundException {
        model.addAttribute("listProductID", productsService.findAll());

        if (!file01.isEmpty() || !file02.isEmpty() || !file03.isEmpty() || !file04.isEmpty()) {
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

                productImagesDTO.setImageId("IMG01_" + productImagesDTO.getProductId());
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

                productImagesDTO.setImageId("IMG02_" + productImagesDTO.getProductId());
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

                productImagesDTO.setImageId("IMG03_" + productImagesDTO.getProductId());
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

                productImagesDTO.setImageId("IMG04_" + productImagesDTO.getProductId());
                productImagesDTO.setImagePath(fileName);

                ProductImages productImages = EntityDtoUtils.convertToEntity(productImagesDTO, ProductImages.class);

                productImagesService.insert(productImages);
            }

            session.setAttribute("toastSuccess", "Cập nhật ảnh thành công");
            historyService.addHistoryUpdateProducts((Accounts) session.getAttribute(SessionAtrr.CURRENT_ADMIN), "Cập nhật ảnh");
            return "redirect:/admin/them-hinh-anh";
        } else {
            redirectAttributes.addFlashAttribute("uploadFileImgSuccess", "Vui lòng chọn hình ảnh");
            return "views/admin/page/crud/image/image-add";
        }
    }
}
