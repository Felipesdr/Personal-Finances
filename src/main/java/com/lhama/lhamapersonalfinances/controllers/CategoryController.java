package com.lhama.lhamapersonalfinances.controllers;

import com.lhama.lhamapersonalfinances.infra.exception.ValidationException;
import com.lhama.lhamapersonalfinances.model.entities.category.*;
import com.lhama.lhamapersonalfinances.model.services.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("register")
    @Transactional
    public ResponseEntity registerGlobalCategory(@RequestBody CategoryRegisterDTO categoryRegisterData, UriComponentsBuilder uriBuilder){
        try{
            Category newCategory = categoryService.registerGlobalCategory(categoryRegisterData);
            Integer newCategoryId = newCategory.getIdCategory();
            URI uri = uriBuilder.path("register/category/{id}").buildAndExpand(newCategoryId).toUri();
            return ResponseEntity.created(uri).body(new CategoryDTO(newCategory));
        } catch (ValidationException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("register-user-created")
    @Transactional
    public ResponseEntity<CategoryDTO> registerCategoryCreatedByUser(@RequestBody CategoryRegisterCreatedByUserDTO categoryRegisterCreatedByUserData, UriComponentsBuilder uriBuilder){
        Category newCategory = categoryService.registerCategoryCreatedByUser(categoryRegisterCreatedByUserData);
        Integer newCategoryId = newCategory.getIdCategory();

        URI uri = uriBuilder.path("register/category/{id}").buildAndExpand(newCategoryId).toUri();

        return ResponseEntity.created(uri).body(new CategoryDTO(newCategory));
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<List<CategoryDTO>> getAllCategoryByIdUserAndGlobal(@PathVariable Integer idUser){
        if (idUser == null){
            return ResponseEntity.badRequest().build();
        }

        List<Category> temp = categoryService.getAllCategoryByIdUserAndGlobal(idUser);
        List<CategoryDTO> categorys = temp.stream().map(c -> new CategoryDTO((Category) c)).toList();

        return ResponseEntity.ok().body(categorys);

    }


    @PutMapping("update")
    @Transactional
    public ResponseEntity<CategoryDTO> updateCategoryById(@RequestBody CategoryUpdateDTO categoryUpdateData){
        if (categoryUpdateData == null){
            return ResponseEntity.badRequest().build();
        }

        Category updatedCategory = categoryService.updateCategoryById(categoryUpdateData);
        return ResponseEntity.ok(new CategoryDTO(updatedCategory));
    }

    @DeleteMapping("deactivate")
    @Transactional
    public ResponseEntity deactivateCategoryCreatedByUserById(@RequestBody CategoryDeactivateDTO categoryDeactivateData){
        if(categoryDeactivateData == null){
            return ResponseEntity.badRequest().build();
        }

        categoryService.deactivateCategoryCreatedByUserById(categoryDeactivateData);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("deactivate/{idCategory}")
    @Transactional
    public ResponseEntity deactivateGlobalCategory(@PathVariable Integer idCategory){
        if(idCategory == null){
            return ResponseEntity.badRequest().build();
        }

        categoryService.deactivateGlobalCategory(idCategory);
        return ResponseEntity.noContent().build();
    }
}
