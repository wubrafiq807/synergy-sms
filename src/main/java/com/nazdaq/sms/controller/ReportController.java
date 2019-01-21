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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nazdaq.sms.beans.RequisitionBean;
import com.nazdaq.sms.beans.SmsAdvanceBean;
import com.nazdaq.sms.beans.StockBean;
import com.nazdaq.sms.beans.SubReportBean;
import com.nazdaq.sms.model.ProductPriceHistory;
import com.nazdaq.sms.model.Requisition;
import com.nazdaq.sms.model.RequisitionHistory;
import com.nazdaq.sms.model.RequisitionItem;
import com.nazdaq.sms.model.Stock;
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

			subReportBean.setProductName(requisitionItem.getProduct().getName() + "-"
					+ requisitionItem.getProduct().getCategory().getName());
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
				if (requisition.getId().toString().length() > 1)
					smsAdvanceBean.setReqNumber("REQ NO:-0" + requisition.getId());
				else
					smsAdvanceBean.setReqNumber("REQ NO:-00" + requisition.getId());

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

	@RequestMapping(value = "/smsReqReport", method = RequestMethod.POST)
	public void smsReqReport(Principal principal, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws JRException, IOException, ParseException {

		JRDataSource jRdataSource = null;
		String status = request.getParameter("status").trim();
		String title = status.equals(STATUS_CLOSE) ? "Closed" : status.equals(STATUS_ACTIVE) ? "Pending" : "Rejected";
		title += " Requisition list";
		String statusText = status.equals(STATUS_CLOSE) ? "Closed"
				: status.equals(STATUS_ACTIVE) ? "Pending" : "Rejected";
		String hqlQuery = "From Requisition where status = " + Integer.parseInt(status);
		if (request.getParameter("startDate") != null && request.getParameter("startDate").trim().length() > 0
				&& request.getParameter("endDate") != null && request.getParameter("endDate").trim().length() > 0) {

			if (status.equals(STATUS_ACTIVE)) {
				hqlQuery += " and created_date >= '" + request.getParameter("startDate") + "' and created_date <='"
						+ request.getParameter("endDate") + " 23:59:59.999'";
			} else {
				hqlQuery += " and modified_date >= '" + request.getParameter("startDate") + "' and modified_date <='"
						+ request.getParameter("endDate") + " 23:59:59.999'";
			}

			title += " from " + request.getParameter("startDate") + " to " + request.getParameter("endDate");

		}

		hqlQuery += " order by id asc";
		// @formatter:on
		List<RequisitionBean> requisitionBeans = new ArrayList<>();
		List<Requisition> requisitionList = commonService.getObjectListByHqlQuery(hqlQuery).stream()
				.map(x -> (Requisition) x).collect(Collectors.toList());
		Double totalPriceSum = 0.0;
		for (Requisition requisition2 : requisitionList) {
			RequisitionBean requisitionBean = new RequisitionBean();
			requisitionBean.setDate(
					NumberWordConverter.getCustomDateFromDateFormate(requisition2.getCreatedDate().toString()));
			requisitionBean.setApprovedAmount(NumberWordConverter.convertDoubleToCurrency(getTotalPrice(requisition2)));
			totalPriceSum += getTotalPrice(requisition2);
			requisitionBean.setEmployeeDesignation(requisition2.getEmployee().getDesignation());
			requisitionBean.setEmployeeId(requisition2.getEmployee().getLxnId());
			requisitionBean.setEmployeeName(requisition2.getEmployee().getName());
			String statusLocal = requisition2.getStatus().toString().trim();
			requisitionBean.setReqNumber(requisition2.getId().toString().length() > 1 ? "0" + requisition2.getId()
					: "00" + requisition2.getId());
			// requisitionBean.setStatus(statusLocal.equals(STATUS_CLOSE) ? "Closed"
			// : statusLocal.equals(STATUS_ACTIVE) ? "Pending" : "Rejected");
			requisitionBean.setStatus(requisition2.getSettings().getViewText());
			requisitionBean.setReqPurpose(requisition2.getPurpose());

			requisitionBeans.add(requisitionBean);
		}

		InputStream jasperStream = null;

		jasperStream = this.getClass().getResourceAsStream("/report/smsReqReport.jasper");
		RequisitionBean requisitionBean = new RequisitionBean();
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> datasourcemap = new HashMap<>();
		datasourcemap.put("requisitionBean", requisitionBean);
		jRdataSource = new JRBeanCollectionDataSource(requisitionBeans, false);

		params.put("datasource", jRdataSource);
		params.put("title", title.toUpperCase());
		params.put("totalAmount", NumberWordConverter.convertDoubleToCurrency(totalPriceSum));

		// prepare report first for one
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jRdataSource);

		response.setContentType("application/x-pdf");
		response.setHeader("Content-disposition", "inline; filename=req_List_" + statusText + ".pdf");
		final OutputStream outStream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);

	}

	// methods to company show
	@RequestMapping(value = "/smsStockReport", method = RequestMethod.POST)
	public void smsStockReport(Principal principal, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws JRException, IOException, ParseException {

		JRDataSource jRdataSource = null;
		

		String title = "Product Stocks";

		String hqlQuery = "From Stock";
		if (request.getParameter("startDate") != null && request.getParameter("startDate").trim().length() > 0
				&& request.getParameter("endDate") != null && request.getParameter("endDate").trim().length() > 0) {

			hqlQuery += " where  created_date >= '" + request.getParameter("startDate") + "' and created_date <='"
					+ request.getParameter("endDate") + " 23:59:59.999'";

			title += " from " + request.getParameter("startDate") + " to " + request.getParameter("endDate");

		}

		hqlQuery += " order by id asc";
		// @formatter:on
		List<StockBean> stockBeans = new ArrayList<>();
		List<Stock> stockList = commonService.getObjectListByHqlQuery(hqlQuery).stream()
				.map(x -> (Stock) x).collect(Collectors.toList());
		
		Double totalSum=0.0;
		for (Stock stock : stockList) {
			StockBean stockBean=new StockBean();
			Integer totalStock=0;
			Double totalPrice=0.0;
			if(stock.getCreatedDate()!=null)
			stockBean.setDate(NumberWordConverter.getCustomDateFromDateFormate(stock.getCreatedDate().toString()));
			stockBean.setEmployeeName(stock.getCreatedBy().getName());
			stockBean.setGeneralQuantity(stock.getQuantity());
			stockBean.setVipQuantity(stock.getVipQuantity());
			stockBean.setProductId(stock.getProduct().getId());
			stockBean.setProductName(stock.getProduct().getName());
			if(stock.getQuantity()!=null)
				totalStock+=stock.getQuantity();
			if(stock.getVipQuantity()!=null)
				totalStock+=stock.getVipQuantity();
			stockBean.setTotalStock(totalStock);
			totalPrice=totalStock*getWeighttedAvgPrice(stock.getProduct().getId());
			totalSum+=totalPrice;
			stockBean.setTotalPrice(NumberWordConverter.convertDoubleToCurrency(totalPrice));			
			stockBeans.add(stockBean);
		}

		InputStream jasperStream = null;

		jasperStream = this.getClass().getResourceAsStream("/report/smsProductStock.jasper");
		StockBean stockBean = new StockBean();
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> datasourcemap = new HashMap<>();
		datasourcemap.put("stockBean", stockBean);
		jRdataSource = new JRBeanCollectionDataSource(stockBeans, false);

		params.put("datasource", jRdataSource);
		params.put("title", title.toUpperCase());
		params.put("totalPrice", NumberWordConverter.convertDoubleToCurrency(totalSum));
		

		// prepare report first for one
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jRdataSource);

		response.setContentType("application/x-pdf");
		response.setHeader("Content-disposition", "inline; filename=req_stock_list.pdf");
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

		if (requisitionHistory.getSettings().getBtnText().equals("Delivery")) {
			return new String("Delivered");
		} else {
			return new String((requisitionHistory.getSettings().getBtnText() + text));
		}

	}

	private Double getTotalPrice(Requisition requisition) {
		Double sum = 0.0;
		List<RequisitionItem> requisitionItems = commonService
				.getObjectListByAnyColumn("RequisitionItem", "requisition_id", requisition.getId().toString()).stream()
				.map(x -> (RequisitionItem) x).collect(Collectors.toList());
		for (RequisitionItem requisitionItem : requisitionItems) {
			sum += this.getWeighttedAvgPrice(requisitionItem.getProduct().getId()) * requisitionItem.getQuantity();
		}

		return sum;

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
