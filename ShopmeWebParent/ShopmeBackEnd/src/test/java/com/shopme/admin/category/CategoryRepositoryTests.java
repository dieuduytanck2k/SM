package com.shopme.admin.category;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import com.shopme.common.entity.Category;

@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTests {
	
	@Autowired
	private CategoryRepository repo;
	
	@Test
	public void testCreateRootCategory() {
		Category category = new Category("Electronics");
		Category savedCategory = repo.save(category);
		
		assertThat(savedCategory.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateSubCategory() {
		Category parent = new Category(7);
		Category subCategory = new Category("iPhone", parent);
		Category savedCategory = repo.save(subCategory);
		
		repo.save(subCategory);
	}
	
	@Test
	public void testGetCategory() {
		Category category = repo.findById(5).get();
		System.out.println(category.getName());
		
		Set<Category> children = category.getChildren();
		for (Category subcategory : children) {
			System.out.println(subcategory.getName());
		}
		
		assertThat(children.size()).isGreaterThan(0);
	}
	
	@Test
	public void testPrintHierarchicalCategories() {
		Iterable<Category> categories = repo.findAll();
		
		for (Category category : categories) {
			if (category.getParent() == null) {
				System.out.println(category.getName());
				
				Set<Category> children = category.getChildren();
				
				for (Category subCategory : children) {
					System.out.println("--" + subCategory.getName());
					printChildren(subCategory, 1);
				}
			}
		}
	}
	
	private void printChildren(Category parent, int subLevel) {
		int newSubLevel = subLevel + 1;
		
		Set<Category> children = parent.getChildren();
		
		for (Category subCategory : children) {
			for (int i = 0; i < newSubLevel; i++) {
				System.out.print("--");
			}
			
			System.out.println(subCategory.getName());  
			printChildren(subCategory, newSubLevel);
		}
	}
	
	@Test
	public void testFindAll() {
		Iterable<Category> findAll = repo.findAll();
		for (Category category : findAll) {
			System.out.println(category.getName());
		}
	}
	
	@Test
	public void testDir() {
		
		String dirName = "../category-images";
		Path userPhotosDir = Paths.get(dirName);
		
		String userPhotosPath = userPhotosDir.toFile().getAbsolutePath();
		
		System.out.println("dirName: " + dirName);
        System.out.println("userPhotosDir: " + userPhotosDir);
        System.out.println("userPhotosPath: " + userPhotosPath);
        
	}
	
	@Test
	public void enabled() {
		repo.updateEnabledStatus(1, true);
	}

	@Test
	public void testFindByName() {
		Category category = repo.findByAlias("electronics");
		System.out.println(category.getName());
	}
	
	@Test
	public void test() {
		Category category = new Category();
		System.out.println(category.isHasChildren());
	}
}
