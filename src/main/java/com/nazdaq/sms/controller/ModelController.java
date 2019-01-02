package com.nazdaq.sms.controller;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.nazdaq.sms.model.Category;
import com.nazdaq.sms.model.Employee;
import com.nazdaq.sms.service.CommonService;
import com.nazdaq.sms.service.UserService;

@Controller
public class ModelController {

	@Autowired
	private UserService userService;

	@Autowired
	private CommonService commonService;

	@RequestMapping(value = "/addProductModel", method = RequestMethod.GET)
	public ModelAndView addProductModel(Model theModel, Principal principal) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		com.nazdaq.sms.model.Model model = new com.nazdaq.sms.model.Model();

		/*
		 * List<Category> categories = (List<Category>) (Object)
		 * commonService.getAllObjectList("Category"); Map<Integer, String> categoryMaps
		 * = new HashMap<Integer, String>();
		 * 
		 * for (Category category : categories) { categoryMaps.put(category.getId(),
		 * category.getName()); }
		 * 
		 * theModel.addAttribute("categoryList", categoryMaps);
		 */

		theModel.addAttribute("command", model);

		return new ModelAndView("productModelAdd");
	}

	@RequestMapping(value = "/saveProductModel", method = RequestMethod.POST)
	public ModelAndView saveProductModel(@ModelAttribute("command") com.nazdaq.sms.model.Model model,
			Principal principal, HttpSession session) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Employee loginEmployee = (Employee) session.getAttribute("loginEmployee");

		if (model.getId() != null) {

			com.nazdaq.sms.model.Model modelDb = (com.nazdaq.sms.model.Model) commonService
					.getAnObjectByAnyUniqueColumn("Model", "id", model.getId().toString());
			modelDb.setName(model.getName());
			modelDb.setRemarks(model.getRemarks());
			modelDb.setStatus(model.getStatus());
			modelDb.setModifiedDate(new Date());
			modelDb.setModifiedBy(loginEmployee);
			commonService.saveOrUpdateModelObjectToDB(modelDb);
		} else {
			model.setCreatedBy(loginEmployee);
			model.setCreatedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(model);
		}

		// commonService.saveOrUpdateModelObjectToDB(Category);
		return new ModelAndView("redirect:/listProductModel");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/listProductModel", method = RequestMethod.GET)
	public ModelAndView listProductModel(Model model, Principal principal) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		List<com.nazdaq.sms.model.Model> models = (List<com.nazdaq.sms.model.Model>) (Object) commonService
				.getAllObjectList("Model");
		model.addAttribute("models", models);
		return new ModelAndView("productModelList");

	}

	@RequestMapping(value = "/updateProductModel", method = RequestMethod.GET)
	public ModelAndView updateProductModel(@ModelAttribute("command") com.nazdaq.sms.model.Model Model, Model model,
			HttpServletRequest request) {

		com.nazdaq.sms.model.Model theModel = (com.nazdaq.sms.model.Model) commonService
				.getAnObjectByAnyUniqueColumn("Model", "id", request.getParameter("id"));
		model.addAttribute("model", theModel);
		return new ModelAndView("productModelEdit");
	}

	@RequestMapping(value = "/ajaxModelCheck", method = RequestMethod.POST)
	@ResponseBody
	public boolean searchModelName(Principal principal, HttpServletRequest request) {
		
		com.nazdaq.sms.model.Model model=(com.nazdaq.sms.model.Model) commonService.getAnObjectByAnyUniqueColumn("Model", "name", request.getParameter("query"));
		if(model==null) {
			return false;
		}else {
			return true;
		}
		
	}

}
