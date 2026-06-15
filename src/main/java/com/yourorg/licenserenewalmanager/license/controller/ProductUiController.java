package com.yourorg.licenserenewalmanager.license.controller;

import com.yourorg.licenserenewalmanager.license.dto.ProductDto;
import com.yourorg.licenserenewalmanager.license.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/ui/products")
public class ProductUiController {

    private final ProductService productService;

    public ProductUiController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String list(Model model) {
        List<ProductDto> products = productService.getAll();
        model.addAttribute("products", products);
        model.addAttribute("activeMenu", "products");
        return "product/productlist";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        ProductDto form = new ProductDto(null, null, null, null, null, null);
        model.addAttribute("form", form);
        model.addAttribute("mode", "create");
        model.addAttribute("activeMenu", "products");
        return "product/productform";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("form") ProductDto form,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("mode", "create");
            model.addAttribute("activeMenu", "products");
            return "product/productform";
        }
        productService.create(form);
        redirectAttributes.addFlashAttribute("successMessage", "Product created.");
        return "redirect:/ui/products";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        ProductDto form = productService.getById(id);
        model.addAttribute("form", form);
        model.addAttribute("mode", "edit");
        model.addAttribute("activeMenu", "products");
        return "product/productform";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("form") ProductDto form,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("mode", "edit");
            model.addAttribute("activeMenu", "products");
            return "product/productform";
        }
        productService.update(id, form);
        redirectAttributes.addFlashAttribute("successMessage", "Product updated.");
        return "redirect:/ui/products";
    }
}