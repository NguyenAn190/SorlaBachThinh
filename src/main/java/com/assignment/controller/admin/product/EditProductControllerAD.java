package com.assignment.controller.admin.product;

import com.assignment.dto.ProductsDTO;
import com.assignment.entity.Accounts;
import com.assignment.entity.Brands;
import com.assignment.entity.ProductTypes;
import com.assignment.entity.Products;
import com.assignment.exception.ProductNotFoundException;
import com.assignment.service.BrandsService;
import com.assignment.service.HistoryService;
import com.assignment.service.ProductTypeService;
import com.assignment.service.ProductsService;
import com.assignment.utils.EntityDtoUtils;
import com.assignment.utils.ReplaceUtils;
import com.assignment.utils.SessionAtrr;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/sua-san-pham")
public class EditProductControllerAD {

    @Autowired
    ProductsService productsService;

    @Autowired
    BrandsService brandsService;

    @Autowired
    HistoryService historyService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpSession session;

    @Autowired
    ProductTypeService productTypeService;

    @RequestMapping(value = "{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String addProducts(@ModelAttribute ProductsDTO productsDT, @PathVariable("id") String id, Model model) throws ProductNotFoundException {
        model.addAttribute("productsDTO", new ProductsDTO());
        Optional<Products> products = Optional.of(productsService.findById(id).get());

        model.addAttribute("priceFormat", ReplaceUtils.formatPrice(products.get().getPrice()));
        model.addAttribute("saleOffFormat", ReplaceUtils.formatPrice(products.get().getSaleOff()));
        model.addAttribute("products", products);
        return "views/admin/page/crud/product/products-edit";
    }

    @RequestMapping("edit/{id}")
    public String editProducts(@Valid @ModelAttribute ProductsDTO productsDTO, BindingResult result, @PathVariable("id") String id, Model model) throws ProductNotFoundException {
        try {
            String price = request.getParameter("price");
            String saleOff = request.getParameter("saleOff");
            String date = request.getParameter("date");

            productsDTO.setProductId(id);
            productsDTO.setPrice(ReplaceUtils.replacePrice(price));
            productsDTO.setSaleOff(ReplaceUtils.replacePrice(saleOff));
            productsDTO.setDateCreated(Timestamp.valueOf(date));
            Products save = EntityDtoUtils.convertToEntity(productsDTO, Products.class);

            productsService.save(save);
            session.setAttribute("toastSuccess", "Cập nhật thành công !");
            historyService.addHistoryUpdateProducts((Accounts) session.getAttribute(SessionAtrr.CURRENT_ADMIN), "Cập nhật sản phẩm");
        } catch (Exception e) {
            Optional<Products> products = Optional.of(productsService.findById(productsDTO.getProductId()).get());
            model.addAttribute("products", products);
            return "views/admin/page/crud/product/products-edit";
        }

        return "redirect:/admin/san-pham";
    }

    @RequestMapping("delete/{id}")
    public String deleteProducts(@PathVariable("id") String id) throws ProductNotFoundException {
        Optional<Products> productsOptional = Optional.of(productsService.findById(id).get());
        if (productsOptional.isPresent()) {
            Products product = productsOptional.get();
            product.setStatusDelete("Ngừng kinh doanh");
            productsService.save(product);
            session.setAttribute("toastSuccess", "Xóa thành công !");
            historyService.addHistoryUpdateProducts((Accounts) session.getAttribute(SessionAtrr.CURRENT_ADMIN), "Xóa sản phẩm");
            return "redirect:/admin/san-pham";
        } else {
            return "lỗi";
        }
    }

    @ModelAttribute("brandsOptions")
    public List<Brands> brandsOptions() {
        return brandsService.findAll();
    }

    @ModelAttribute("productTypeOptions")
    public List<ProductTypes> productTypeOptions() {
        return productTypeService.findAll();
    }

    @ModelAttribute("isStatusDelete")
    public List<String> statusOptions() {
        List<String> options = new ArrayList<>();
        options.add("Đang bán");
        options.add("Ngừng kinh doanh");
        return options;
    }
}
