package com.samseung.ceas.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;

import com.samseung.ceas.dto.ProductDTO;
import com.samseung.ceas.dto.ResponseDTO;
import com.samseung.ceas.model.ProductEntity;
import com.samseung.ceas.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
//	@GetMapping
//	public ResponseEntity<?> retrieveProductList(Model model, @RequestParam(value="page", defaultValue="0") int page){
//		
//		try {
//			
//	        Page<ProductEntity> paging = this.productService.getList(page);
//	        log.info("entity: " + paging.toString());
//	        model.addAttribute("paging", paging);
//	        
//			List<ProductEntity> entities = productService.retrieveAll();
//			List<ProductDTO> dtos = entities.stream().map(ProductDTO::new).collect(Collectors.toList());
//			ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder().data(dtos).build();
//			return ResponseEntity.ok().body(response);			
//		}catch (IllegalStateException e) {
//			ResponseDTO<ProductDTO> responseDTO = ResponseDTO.<ProductDTO>builder().error("Product Table is empty").build();
//			return ResponseEntity.badRequest().body(responseDTO);
//		}catch (Exception e) {
//			ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder().error("An unexpected error occurred").build();
//			return ResponseEntity.badRequest().body(response);
//		}
//	}
	@GetMapping
	public ResponseEntity<?> retrieveProductList(){
		
		try {
			List<ProductEntity> entities = productService.retrieveAll();
			List<ProductDTO> dtos = entities.stream().map(ProductDTO::new).collect(Collectors.toList());
			ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder().data(dtos).build();
			return ResponseEntity.ok().body(response);			
		}catch (IllegalStateException e) {
			ResponseDTO<ProductDTO> responseDTO = ResponseDTO.<ProductDTO>builder().error("Product Table is empty").build();
			return ResponseEntity.badRequest().body(responseDTO);
		}catch (Exception e) {
			ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder().error("An unexpected error occurred").build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PostMapping
	public ResponseEntity<?> createProduct(@AuthenticationPrincipal String userId, @RequestBody ProductDTO dto){
		try{
			ProductEntity entity = ProductDTO.toEntity(dto);
			entity.setId(null);
			entity.setUserId(userId);
			entity.setCreatedDate(LocalDateTime.now());
			
			ProductEntity createdProduct = productService.create(entity);
			List<ProductDTO> dtos = new ArrayList<>();
			dtos.add(new ProductDTO(createdProduct));
			
			ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder().data(dtos).build();
			return ResponseEntity.ok().body(response);
		}catch (Exception e) {
			ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder().error("An unexpected error occurred").build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> retrieveProduct(@PathVariable("id") Integer id){
		try {
			ProductEntity entity = productService.retrieve(id);
			List<ProductDTO> dtos = new ArrayList<>();
			dtos.add(new ProductDTO(entity));
			
			ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder().data(dtos).build();
			return ResponseEntity.ok().body(response);			
		}catch (NoSuchElementException e) {
			ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder().error("Entity is not existed").build();
			return ResponseEntity.badRequest().body(response);
		}catch (Exception e) {
			ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder().error("An unexpected error occurred").build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateProduct(@AuthenticationPrincipal String userId, @RequestBody ProductDTO dto, @PathVariable("id") Integer id){
		try {
			ProductEntity originalEntity = productService.retrieve(id);
			originalEntity.setProductName(dto.getProductName());
			originalEntity.setCategory(dto.getCategory());
			originalEntity.setProductDescription(dto.getProductDescription());
			
			ProductEntity updatedEntity = productService.update(originalEntity);
			List<ProductDTO> dtos = new ArrayList<>();
			dtos.add(new ProductDTO(updatedEntity));
			
			ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder().data(dtos).build();
			return ResponseEntity.ok().body(response);			
		}catch (NoSuchElementException e) {
			ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder().error("Entity is not existed").build();
			return ResponseEntity.badRequest().body(response);
		}catch (Exception e) {
			ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder().error("An unexpected error occurred").build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(@AuthenticationPrincipal String userId, @PathVariable("id") Integer id){
		try {
			ProductEntity entity = productService.retrieve(id);
			productService.delete(entity);
			
			List<ProductEntity> entities =  productService.retrieveAll();
			List<ProductDTO> dtos = entities.stream().map(ProductDTO::new).collect(Collectors.toList());
			ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder().data(dtos).build();
			return ResponseEntity.ok().body(response);
		}catch (Exception e) {
			ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder().error("An error occurred while deleting a product").build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
}
