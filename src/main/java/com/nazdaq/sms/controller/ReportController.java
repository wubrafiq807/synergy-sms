package com.nazdaq.sms.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nazdaq.sms.beans.SmsAdvanceBean;
import com.nazdaq.sms.beans.SubReportBean;
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
public class ReportController implements Constants {

	@Autowired
	private CommonService commonService;

	@Autowired
	private JavaMailSender mailSender;

	@Value("${cc.email.addresss}")
	String ccEmailAddresss;

	// methods to company show
	@RequestMapping(value = "/reqReport/{id}", method = RequestMethod.GET)
	public void jobAdvanceReport(@PathVariable("id") String id, ModelMap model, RedirectAttributes redirectAttributes,
			Principal principal, HttpSession session, HttpServletResponse response)
			throws JRException, IOException, ParseException {

		JRDataSource jRdataSource = null;

		Requisition requisition = (Requisition) commonService.getAnObjectByAnyUniqueColumn("Requisition", "id", id);

		String hqlQuery = "From RequisitionHistory where requisition_id = " + requisition.getId() + " order by id asc";
		List<RequisitionHistory> smsRequisitionHistoryList = commonService.getObjectListByHqlQuery(hqlQuery).stream()
				.map(e -> (RequisitionHistory) e).filter(x -> x.getIsRejected() != 1).collect(Collectors.toList());

		List<SmsAdvanceBean> smsAdvanceBeans = new ArrayList<>();

		String hqlQueryForRequistionItem = "From RequisitionItem where requisition_id = " + requisition.getId()
				+ " order by id asc";

		List<RequisitionItem> requisitionItems = commonService.getObjectListByHqlQuery(hqlQueryForRequistionItem)
				.stream().map(x -> (RequisitionItem) x).collect(Collectors.toList());

		Double totalApproveAmount = 0.0;
		List<SubReportBean> subReportBeans = new ArrayList<>();

		for (RequisitionItem requisitionItem : requisitionItems) {
			SubReportBean subReportBean = new SubReportBean();

			Integer pId = requisitionItem.getProduct().getId();

			Double wAvg = getWeighttedAvgPrice(pId);
			Double totalAmount = wAvg * requisitionItem.getQuantity();
			totalApproveAmount += totalAmount;

			subReportBean.setProductName(requisitionItem.getProduct().getName());
			subReportBean.setQuantity(requisitionItem.getQuantity());
			subReportBean.setPrice(NumberWordConverter.convertDoubleToCurrency(totalAmount));
			subReportBean.setSinglePrice(NumberWordConverter.convertDoubleToCurrency(wAvg));
			
			subReportBeans.add(subReportBean);

		}

		for (RequisitionHistory requisitionHistory : smsRequisitionHistoryList) {
			if (requisitionHistory.getSettings() != null) {
				SmsAdvanceBean smsAdvanceBean = new SmsAdvanceBean();

				smsAdvanceBean.setEmployeeDesignation(requisition.getEmployee().getDesignation());

				smsAdvanceBean.setEmployeeId(requisition.getEmployee().getLxnId());
				smsAdvanceBean.setEmployeeName(requisition.getEmployee().getName());

				smsAdvanceBean.setApprovedAmount(NumberWordConverter.convertDoubleToCurrency(totalApproveAmount));

				smsAdvanceBean.setReqPurpose(requisition.getPurpose());
				smsAdvanceBean.setReqNumber("REQ NO:-" + requisition.getId());

				smsAdvanceBean.setSubmissionDate(
						NumberWordConverter.getCustomDateFromDateFormate(requisition.getCreatedDate().toString()));

				smsAdvanceBean.setStagename(this.getamounText(requisitionHistory) + " by" + " "
						+ requisitionHistory.getCreatedBy().getName() + "("
						+ requisitionHistory.getCreatedBy().getDesignation() + ")");

				smsAdvanceBean.setStatus("Status : " + this.getamounText(requisitionHistory));

				smsAdvanceBean.setButtontext(this.getamounText(requisitionHistory) + " By");
				smsAdvanceBean.setActtionBy(requisitionHistory.getCreatedBy().getName());

				smsAdvanceBean.setActionedDateText(this.getamounText(requisitionHistory) + " Date");

				smsAdvanceBean.setActionedDate(NumberWordConverter
						.getCustomDateFromDateFormate(requisitionHistory.getCreatedDate().toString()));

				smsAdvanceBean.setComments(requisitionHistory.getRemarks());

				smsAdvanceBeans.add(smsAdvanceBean);

			}
		}
		// @formatter:on

		InputStream jasperStream = null;

		jasperStream = this.getClass().getResourceAsStream("/report/smsReportForm.jasper");
		SmsAdvanceBean smsAdvanceBean = new SmsAdvanceBean();
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> datasourcemap = new HashMap<>();
		datasourcemap.put("SmsAdvanceBean", smsAdvanceBean);
		jRdataSource = new JRBeanCollectionDataSource(smsAdvanceBeans, false);

		params.put("datasource", jRdataSource);
		params.put("TOTAL_PRICE_WORD", EnglishNumberToWords.convert(Math.round(totalApproveAmount)).toUpperCase());
		params.put("TOTAL_PRICE", NumberWordConverter.convertDoubleToCurrency(totalApproveAmount));
		params.put("SUBREPORT_DATA", subReportBeans);

		// prepare report first for one
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jRdataSource);

		response.setContentType("application/x-pdf");
		response.setHeader("Content-disposition", "inline; filename=req_" + requisition.getId() + "_"
				+ requisition.getEmployee().getName().trim().replaceAll("\\.", "_").replace(" ", "_") + "" + ".pdf");
		final OutputStream outStream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);

	}

	private String getamounText(RequisitionHistory requisitionHistory) {
		String text = "";
		if (requisitionHistory.getSettings().getBtnText().equals("Approve")) {
			text = "d";
		} else if (requisitionHistory.getSettings().getBtnText().equals("Submit")) {
			text = "ted";
		} else {
			text = "ed";
		}
		return new String((requisitionHistory.getSettings().getBtnText() + text));
	}

	private Double getWeighttedAvgPrice(Integer productID) {
		List<ProductPriceHistory> productPriceHistories = commonService
				.getObjectListByAnyColumn("ProductPriceHistory", "product_id", productID.toString()).stream()
				.map(x -> (ProductPriceHistory) x).collect(Collectors.toList());
		Double sum = 0.0;
		int sumQuantity = 0;
		for (ProductPriceHistory productPriceHistory : productPriceHistories) {
			sum += productPriceHistory.getPrice() * productPriceHistory.getPurchaseQuantity();
			sumQuantity += productPriceHistory.getPurchaseQuantity();
		}

		return sum / sumQuantity;
	}

}
