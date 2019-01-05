package com.nazdaq.sms.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.nazdaq.sms.model.Employee;
import com.nazdaq.sms.model.Requisition;
import com.nazdaq.sms.model.RequisitionHistory;
import com.nazdaq.sms.model.Settings;
import com.nazdaq.sms.service.CommonService;
import com.nazdaq.sms.util.Constants;
import com.nazdaq.sms.util.NumberWordConverter;

@Controller
@PropertySource("classpath:common.properties")
public class DashboardController implements Constants {

	@Autowired
	private CommonService commonService;

	@Autowired
	private JavaMailSender mailSender;

	@Value("${cc.email.addresss}")
	String ccEmailAddresss;

	@RequestMapping(value = "/userDashboard", method = RequestMethod.GET)
	public ModelAndView userDashboard(ModelMap model, Principal principal, HttpSession session) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		Employee loginEmployee = (Employee) session.getAttribute("loginEmployee");
		List<String> statusList = new ArrayList<>();
		statusList.add(STATUS_ACTIVE);
		statusList.add(STATUS_REJECT);
		statusList.add(STATUS_CLOSE);
		List<Requisition> myreqList = commonService
				.getObjectListByAnyColumnValueListAndOneColumn("Requisition", "status", statusList, "employee.id",
						loginEmployee.getId().toString())
				.stream().map(e -> (Requisition) e).collect(Collectors.toList());
		List<Requisition> subordinateReqList = commonService
				.getObjectListByAnyColumnValueListAndOneColumn("Requisition", "status", statusList,
						"employee.supervisorId", loginEmployee.getPunchId().toString())
				.stream().map(e -> (Requisition) e).collect(Collectors.toList());

		model.put("myreqList", myreqList);
		model.put("subordinateReqList", subordinateReqList);
		List<RequisitionHistory> reqCreadtedByConcenrlist = commonService
				.getObjectListByAnyColumn("RequisitionHistory", "created_by", loginEmployee.getId().toString()).stream()
				.map(x -> (RequisitionHistory) x).collect(Collectors.toList());
		List<RequisitionHistory> reqCreadtedByConcenRejectedList = null;

		List<RequisitionHistory> reqCreadtedByConcenApprovvedList = null;
		if (!reqCreadtedByConcenrlist.isEmpty()) {
			reqCreadtedByConcenRejectedList = reqCreadtedByConcenrlist.stream().filter(x -> x.getIsRejected() == 1)
					.collect(Collectors.toList());
			reqCreadtedByConcenApprovvedList = reqCreadtedByConcenrlist.stream().filter(x -> x.getIsRejected() != 1)
					.collect(Collectors.toList());
		}

		model.put("reqConcenRejectedList", reqCreadtedByConcenRejectedList);
		model.put("reqConcenApprovvedList", reqCreadtedByConcenApprovvedList);
		return new ModelAndView("userDashboard", model);
	}

	@RequestMapping(value = "/adminDashboard", method = RequestMethod.GET)
	public ModelAndView adminDashboard(ModelMap model, Principal principal, HttpSession session,
			HttpServletRequest request) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		String fiscalYearName = request.getParameter("fiscalYearName");

		List<Requisition> approvedReqList = null;

		// String roleName = commonService.getAuthRoleName();

		// Employee loginEmployee = (Employee) session.getAttribute("loginEmployee");

		List<Requisition> pendingReqList = commonService
				.getObjectListByAnyColumn("Requisition", "status", STATUS_ACTIVE).stream().map(e -> (Requisition) e)
				.collect(Collectors.toList());

		List<Requisition> rejectedReqList = commonService
				.getObjectListByAnyColumn("Requisition", "status", STATUS_REJECT).stream().map(e -> (Requisition) e)
				.collect(Collectors.toList());

		approvedReqList = commonService.getObjectListByAnyColumn("Requisition", "status", STATUS_CLOSE).stream()
				.map(e -> (Requisition) e).collect(Collectors.toList());
		approvedReqList.forEach(i -> {
			if (i.getId().toString().length() > 0)
				i.setReqNo("0" + i.getId());
			else
				i.setReqNo("00" + i.getId());

		});
		model.put("fiscalYearName", this.getFiscalYearName());

		if (!request.isUserInRole("ROLE_ADMIN") || !request.isUserInRole("ROLE_JOB_ADMIN")) {
			Collections.reverse(pendingReqList);
			Collections.reverse(approvedReqList);
			Collections.reverse(rejectedReqList);

			model.put("pendingReqList", pendingReqList);
			model.put("approvedReqList", approvedReqList);
			model.put("rejectedJobList", rejectedReqList);

			model.put("totalPendingReq", pendingReqList.size());
			model.put("totalApprovedReq", approvedReqList.size());
			model.put("totalRejectedJobReq", rejectedReqList.size());
		}

		return new ModelAndView("adminDashboard", model);
	}

	@RequestMapping(value = "/concernDashboard", method = RequestMethod.GET)
	public ModelAndView concernDashboard(ModelMap model, Principal principal, HttpSession session) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		String roleName = commonService.getAuthRoleName();

		Employee loginEmployee = (Employee) session.getAttribute("loginEmployee");

		// JobSettings js =
		// (JobSettings)commonService.getAnObjectByAnyUniqueColumn("JobSettings",
		// "authRole", roleName);
		String jobSetId = "";
		List<Settings> jsList = commonService.getObjectListByAnyColumn("Settings", "authRole", roleName).stream()
				.map(e -> (Settings) e).collect(Collectors.toList());
		for (Settings jobSettings : jsList) {
			jobSetId = jobSetId + "'" + jobSettings.getId().toString() + "', ";
		}

		jobSetId = jobSetId.trim();
		jobSetId = jobSetId.substring(0, jobSetId.length() - 1);

		List<String> statusList = new ArrayList<>();
		statusList.add(STATUS_ACTIVE);
		statusList.add(STATUS_CLOSE);

		/*
		 * List<JobAdvance> pendingJobList = commonService
		 * .getObjectListByAnyColumnValueListAndOneColumn("JobAdvance", "status",
		 * statusList, "jobSetting.id", js.getId().toString()) .stream().map(e ->
		 * (JobAdvance) e).collect(Collectors.toList());
		 */

		String hqlQuery = "From Requisition WHERE status in ('" + STATUS_ACTIVE + "', '" + STATUS_CLOSE
				+ "') AND settings_id in (" + jobSetId + ")";
		List<Requisition> pendingReqList = commonService.getObjectListByHqlQuery(hqlQuery).stream()
				.map(e -> (Requisition) e).collect(Collectors.toList());

		Collections.reverse(pendingReqList);

		model.put("pendingReqList", pendingReqList);
		List<RequisitionHistory> reqCreadtedByConcenrlist = commonService
				.getObjectListByAnyColumn("RequisitionHistory", "created_by", loginEmployee.getId().toString()).stream()
				.map(x -> (RequisitionHistory) x).collect(Collectors.toList());
		List<RequisitionHistory> reqConcenRejectedList = reqCreadtedByConcenrlist.stream()
				.filter(x -> x.getIsRejected() == 1).collect(Collectors.toList());
		List<RequisitionHistory> reqConcenApprovvedList = reqCreadtedByConcenrlist.stream()
				.filter(x -> x.getIsRejected() != 1).collect(Collectors.toList());

		model.put("reqConcenRejectedList", reqConcenRejectedList);
		model.put("reqConcenApprovvedList", reqConcenApprovvedList);
		return new ModelAndView("concernDashboard", model);
	}

	private String getFiscalYearName() {
		String fiscalYearName = "";
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		;
		calendar.setTime(now);

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);

		if (month < 6) {
			fiscalYearName = (year - 1) + "-" + year;
		} else {
			fiscalYearName = year + "-" + (year + 1);
		}
		return fiscalYearName.replace("20", "").trim().toString();
	}
}
