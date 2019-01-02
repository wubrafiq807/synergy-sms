package com.nazdaq.sms.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.nazdaq.sms.model.Category;
import com.nazdaq.sms.model.Employee;
import com.nazdaq.sms.service.CommonService;
import com.nazdaq.sms.service.UserService;

@Controller
public class CategoryController {

	@Autowired
	private UserService userService;

	@Autowired
	private CommonService commonService;

	@RequestMapping(value = "/addProductCategory", method = RequestMethod.GET)
	public ModelAndView addProductCategory(Model model, Principal principal) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Category Category = new Category();
		model.addAttribute("command", Category);
		return new ModelAndView("productCategoryAdd");
	}

	@RequestMapping(value = "/saveProductCategory", method = RequestMethod.POST)
	public ModelAndView saveProductCategory(@ModelAttribute("command") Category category, Principal principal,HttpSession session) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		Employee loginEmployee=(Employee) session.getAttribute("loginEmployee");
		
		if(category.getId()!=null) {
			
			Category categoryDb=(Category) commonService.getAnObjectByAnyUniqueColumn("Category", "id", category.getId().toString());
			categoryDb.setName(category.getName());
			categoryDb.setRemarks(category.getRemarks());
			categoryDb.setStatus(category.getStatus());
			categoryDb.setModifiedDate(new Date());
		   categoryDb.setModifiedBy(loginEmployee);
		   commonService.saveOrUpdateModelObjectToDB(categoryDb);
		}else {
			category.setCreatedBy(loginEmployee);
			category.setCreatedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(category);
		}

		//commonService.saveOrUpdateModelObjectToDB(Category);
		return new ModelAndView("redirect:/listProductCategory");
	}

	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test(Model model, Principal principal) {

		return "test";

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/listProductCategory", method = RequestMethod.GET)
	public ModelAndView listProductCategory(Model model, Principal principal) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		List<Category> categories = (List<Category>) (Object) commonService.getAllObjectList("Category");

		model.addAttribute("categories", categories);

		return new ModelAndView("productCategoryList");

	}

	@RequestMapping(value = "/updateProductCategory", method = RequestMethod.GET)
	public ModelAndView updateProductCategory(@ModelAttribute("command") Category Category, Model model,HttpServletRequest request) {
		
		Category category = (Category) commonService.getAnObjectByAnyUniqueColumn("Category", "id", request.getParameter("id"));
		model.addAttribute("category", category);
		return new ModelAndView("productCategoryEdit");
	}
	
	
	
	@RequestMapping(value = "/ajaxCategoryCheck", method = RequestMethod.POST)
	@ResponseBody
	public boolean searchCategoryName(Principal principal, HttpServletRequest request) {
		
		Category category=(Category) commonService.getAnObjectByAnyUniqueColumn("Category", "name", request.getParameter("query"));
		if(category==null) {
			return false;
		}else {
			return true;
		}
		
	}
		
}
