package com.nazdaq.sms.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.nazdaq.sms.model.RequisitionStatus;
import com.nazdaq.sms.service.CommonService;
import com.nazdaq.sms.service.UserService;

@Controller
public class RequisitionStatusController {

	@Autowired
	private UserService userService;

	@Autowired
	private CommonService commonService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewRequisitionStatus", method = RequestMethod.GET)
	public ModelAndView viewRequisitionStatus(Model model, Principal principal) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		List<com.nazdaq.sms.model.RequisitionStatus> requisitionStatus = (List<com.nazdaq.sms.model.RequisitionStatus>) (Object) commonService
				.getAllObjectList("RequisitionStatus");
		model.addAttribute("requisitionStatus", requisitionStatus);
		return new ModelAndView("productRequisitionStatus");

	}

	@RequestMapping(value = "/ajaxChangeProductRequisitionStatus", method = RequestMethod.POST)
	@ResponseBody
	public Boolean ajaxChangeProductRequisitionStatus(Principal principal, HttpServletRequest request) {

		Integer reqStatus = Integer.parseInt(request.getParameter("status"));

		RequisitionStatus requisitionStatus = (RequisitionStatus) commonService
				.getAnObjectByAnyUniqueColumn("RequisitionStatus", "id", "1");

		requisitionStatus.setReqStatus(reqStatus);
		commonService.saveOrUpdateModelObjectToDB(requisitionStatus);

		return true;

	}

}
