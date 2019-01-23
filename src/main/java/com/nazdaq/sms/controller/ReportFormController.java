package com.nazdaq.sms.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nazdaq.sms.beans.RequisitionBean;
import com.nazdaq.sms.beans.SmsAdvanceBean;
import com.nazdaq.sms.beans.SubReportBean;
import com.nazdaq.sms.model.Employee;
import com.nazdaq.sms.model.ProductPriceHistory;
import com.nazdaq.sms.model.Requisition;
import com.nazdaq.sms.model.RequisitionHistory;
import com.nazdaq.sms.model.RequisitionItem;
import com.nazdaq.sms.service.CommonService;
import com.nazdaq.sms.util.Constants;
import com.nazdaq.sms.util.EnglishNumberToWords;
import com.nazdaq.sms.util.NumberWordConverter;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import java.io.InputStream;
import java.io.OutputStream;

@Controller
@PropertySource("classpath:common.properties")
public class ReportFormController implements Constants {

	@Autowired
	private CommonService commonService;

	@Autowired
	private JavaMailSender mailSender;

	@Value("${cc.email.addresss}")
	String ccEmailAddresss;

	
	@RequestMapping(value = "/smsReqForm", method = RequestMethod.GET)
	public ModelAndView smsReqForm(Principal principal,ModelMap model)
			throws JRException, IOException, ParseException {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		List<Employee> employees=commonService.getObjectListByAnyColumn("Employee", "status", "1").stream().map(x->(Employee)x).collect(Collectors.toList());
		
		model.put("employees", employees);
		
		return new ModelAndView("smsReqForm");
	}
	

	@RequestMapping(value = "/smsStockReport", method = RequestMethod.GET)
	public ModelAndView smsStockReport(Principal principal)
			throws JRException, IOException, ParseException {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		return new ModelAndView("smsStockReport");
	}

	
	
	@RequestMapping(value = "/smsProductReport", method = RequestMethod.GET)
	public ModelAndView smsProductReport(Principal principal)
			throws JRException, IOException, ParseException {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		return new ModelAndView("smsProductReport");
	}
	

}
