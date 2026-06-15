package com.yourorg.licenserenewalmanager.license.controller;

import com.yourorg.licenserenewalmanager.license.dto.DepartmentDto;
import com.yourorg.licenserenewalmanager.license.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/ui/departments")
public class DepartmentUiController {

    private final DepartmentService departmentService;

    public DepartmentUiController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public String list(Model model) {
        List<DepartmentDto> departments = departmentService.getAll();
        model.addAttribute("departments", departments);
        model.addAttribute("activeMenu", "departments");
        return "department/departmentlist";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        DepartmentDto form = new DepartmentDto(null, null, null);
        model.addAttribute("form", form);
        model.addAttribute("mode", "create");
        model.addAttribute("activeMenu", "departments");
        return "department/departmentform";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("form") DepartmentDto form,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("mode", "create");
            model.addAttribute("activeMenu", "departments");
            return "department/departmentform";
        }
        departmentService.create(form);
        redirectAttributes.addFlashAttribute("successMessage", "Department created.");
        return "redirect:/ui/departments";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        DepartmentDto form = departmentService.getById(id);
        model.addAttribute("form", form);
        model.addAttribute("mode", "edit");
        model.addAttribute("activeMenu", "departments");
        return "department/departmentform";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("form") DepartmentDto form,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("mode", "edit");
            model.addAttribute("activeMenu", "departments");
            return "department/departmentform";
        }
        departmentService.update(id, form);
        redirectAttributes.addFlashAttribute("successMessage", "Department updated.");
        return "redirect:/ui/departments";
    }
}