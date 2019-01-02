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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.nazdaq.sms.model.Category;
import com.nazdaq.sms.model.Employee;
import com.nazdaq.sms.model.Settings;
import com.nazdaq.sms.service.CommonService;
import com.nazdaq.sms.service.UserService;

@Controller
public class ConfigurationController {

	@Autowired
	private UserService userService;

	@Autowired
	private CommonService commonService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addConfiguraation", method = RequestMethod.GET)
	public ModelAndView addProductCategory(Model model, Principal principal) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		List<Settings> theSettings = (List<Settings>) (Object) commonService.getAllObjectList("Settings");

		Settings settings = new Settings();
		model.addAttribute("command", settings);
		model.addAttribute("settingsList", theSettings);
		return new ModelAndView("configurationAdd");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveConfiguration", method = RequestMethod.POST)
	public ModelAndView saveConfiguration(@ModelAttribute("command") Settings settings, Principal principal,
			HttpSession session) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		if (settings.getId() != null) {

			Settings settingsDb = (Settings) commonService.getAnObjectByAnyUniqueColumn("Settings", "id",
					settings.getId().toString());
			settingsDb.setCanReject(settings.getCanReject());
			settingsDb.setIsRejectReasonMandatory(settings.getIsRejectReasonMandatory());

			Settings settingsOb = null;
			int flag = 0;
			try {
				settingsOb = (Settings) commonService.getAnObjectByAnyUniqueColumn("Settings", "id",
						settings.getRejectToId().toString());
				flag = 1;
			} catch (Exception e) {
				flag = 0;
			}

			if (flag == 1) {
				settingsDb.setStage(settings.getStage());
				settingsDb.setAuthRole(settings.getAuthRole());
				settingsDb.setBtnText(settings.getBtnText());
				settingsDb.setBtnClass(settings.getBtnClass());
				settingsDb.setViewText(settings.getViewText());
				settingsDb.setRejectTo(settingsOb);
			} else {
				settingsDb.setStage(settings.getStage());
				settingsDb.setAuthRole(settings.getAuthRole());
				settingsDb.setBtnText(settings.getBtnText());
				settingsDb.setBtnClass(settings.getBtnClass());
				settingsDb.setViewText(settings.getViewText());		
			}

			commonService.saveOrUpdateModelObjectToDB(settingsDb);
		} else {

			List<Settings> theSettings = (List<Settings>) (Object) commonService.getAllObjectList("Settings");

			if (theSettings == null || theSettings.isEmpty()) {

				commonService.saveOrUpdateModelObjectToDB(settings);
			} else {
				Settings settingsOb = (Settings) commonService.getAnObjectByAnyUniqueColumn("Settings", "id",
						settings.getRejectToId().toString());
				settings.setRejectTo(settingsOb);
				commonService.saveOrUpdateModelObjectToDB(settings);
			}
		}
		return new ModelAndView("redirect:/listConfiguraation");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/listConfiguraation", method = RequestMethod.GET)
	public ModelAndView listConfiguraation(Model model, Principal principal) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		List<Settings> settings = (List<Settings>) (Object) commonService.getAllObjectList("Settings");

		model.addAttribute("settings", settings);

		return new ModelAndView("configurationList");

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateConfiguration", method = RequestMethod.GET)
	public ModelAndView updateConfiguration(@ModelAttribute("command") Settings Settings, Model model,
			HttpServletRequest request) {

		Settings settings = (Settings) commonService.getAnObjectByAnyUniqueColumn("Settings", "id",
				request.getParameter("id"));

		List<Settings> theSettings = (List<Settings>) (Object) commonService.getAllObjectList("Settings");

		model.addAttribute("settings", settings);
		model.addAttribute("settingsList", theSettings);
		return new ModelAndView("configurationEdit");
	}

}
