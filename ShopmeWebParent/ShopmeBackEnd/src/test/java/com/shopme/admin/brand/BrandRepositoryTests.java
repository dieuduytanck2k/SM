package com.shopme.admin.brand;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.admin.category.CategoryRepository;
import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class BrandRepositoryTests {
	
	@Autowired
	private BrandRepository brandRepo;
	
	@Autowired
	private CategoryRepository catRepo;
	
	@Test
	public void testCreateAcerBrands(){
		Category cat = catRepo.findByName("laptops");
		Brand brand = new Brand("Acer", "brand-logo.png");
		Set<Category> categories = brand.getCategories();
		categories.add(cat);
		
		brandRepo.save(brand);
	}
	
	@Test
	public void testCreateAppleBrands(){
		Category cat = catRepo.findById(4).get();
		Brand brand = new Brand("Apple", "brand-logo.png");
		Set<Category> categories = brand.getCategories();
		categories.add(cat);
		
		brandRepo.save(brand);
	}
	
	@Test
	public void testUpdateFalseBrands(){
		Category cat = catRepo.findById(7).get();
		Brand brandDB = brandRepo.findById(2).get();
		Set<Category> categories = brandDB.getCategories();
		categories.add(cat);
		
		brandRepo.save(brandDB);
	}
	
	@Test
	public void testCreateSamsungBrands(){
		Category cat1 = catRepo.findById(24).get();
		Category cat2 = catRepo.findById(29).get();
		Brand brand = new Brand("Samsung", "brand-logo.png");
		Set<Category> categories = brand.getCategories();
		categories.add(cat1);
		categories.add(cat2);
		
		brandRepo.save(brand);
	}
	
	@Test
	public void findAllBrands() {
		List<Brand> brands = (List<Brand>) brandRepo.findAll();
		for (Brand brand : brands) {
			System.out.println(brand.getId() + " " + brand.getName() + " " + nameListCategories(brand));
		}
	}
	
	private String nameListCategories(Brand brand) {
		Set<Category> categories = brand.getCategories();
		String listCat = "";
		for (Category category : categories) {
			listCat += category.getName() + ", ";
		}
		listCat = listCat.substring(0, listCat.length() - 2);
		listCat = listCat.concat(".");
		return listCat;
	}
	
	@Test 
	public void testGetBrand(){
		Brand findById = brandRepo.findById(1).get();
		System.out.println(findById.getName());
	}
	 
	@Test
	public void testUpdate() {
		Brand findById = brandRepo.findById(3).get();
		findById.setName("Sam sung Electronics");
		
		brandRepo.save(findById);
	}
	
	@Test
	public void deleteBrand() {
		brandRepo.deleteById(2);
	}
	
	@Test
	public void show() {
		List<Brand> brands = (List<Brand>) brandRepo.findAll();
		for (Brand brand : brands) {
			System.out.println(brand.toString());
		}
	}
}
