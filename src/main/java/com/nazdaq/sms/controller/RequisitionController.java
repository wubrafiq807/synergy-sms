package com.nazdaq.sms.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.util.concurrent.AtomicDouble;
import com.nazdaq.sms.model.Category;
import com.nazdaq.sms.model.Employee;
import com.nazdaq.sms.model.Product;
import com.nazdaq.sms.model.ProductDelivery;
import com.nazdaq.sms.model.ProductPriceHistory;
import com.nazdaq.sms.model.ProductRecive;
import com.nazdaq.sms.model.Requisition;
import com.nazdaq.sms.model.RequisitionHistory;
import com.nazdaq.sms.model.RequisitionItem;
import com.nazdaq.sms.model.Settings;
import com.nazdaq.sms.model.Stock;
import com.nazdaq.sms.service.CommonService;
import com.nazdaq.sms.service.UserService;
import com.nazdaq.sms.util.Constants;
import com.nazdaq.sms.util.NumberWordConverter;

@Controller
public class RequisitionController implements Constants {

	@Autowired
	private UserService userService;

	@Autowired
	private CommonService commonService;

	@RequestMapping(value = "/addProductRequisition", method = RequestMethod.GET)
	public ModelAndView addProductRequisition(ModelMap model, Principal principal) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		String roleName = commonService.getAuthRoleName();
		boolean isVipRequisition = false;
		List<Product> theProducts=new ArrayList<>();
		if (roleName != null && roleName.trim().equals(AUTH_STORE_MANAGER)) {
			isVipRequisition = true;
			List<Employee> employeeList = commonService.getAllObjectList("Employee").stream().map(x -> (Employee) x)
					.collect(Collectors.toList());
			model.put("employeeList", employeeList);
		List<Product>	 listPro = commonService.getAllObjectList("Product").stream().map(x -> (Product) x)
					.filter(x -> x.getStatus() == 1).collect(Collectors.toList());
		List<Stock> stocks=commonService.getAllObjectList("Stock").stream().map(x->(Stock)x).filter(x->x.getVipQuantity()!=null&&x.getVipQuantity()>0).collect(Collectors.toList());
		
		for (Stock stock : stocks) {
			for (Product product : listPro) {
				if(stock.getProduct().getId().toString().equals(product.getId().toString())) {
					theProducts.add(product);
					break;
				}
			}
		}
		
		}else {
			 theProducts = commonService.getAllObjectList("Product").stream().map(x -> (Product) x)
					.filter(x -> x.getStatus() == 1).collect(Collectors.toList());
		}
		Requisition requisition = new Requisition();

		
		theProducts.forEach(x -> x.setWeightedAvgPrice(this.getWeighttedAvgPrice(x.getId())));

		model.put("command", requisition);
		model.put("productList", theProducts);
		model.put("isVipRequisition", isVipRequisition);
		return new ModelAndView("productRequestionAdd",model);
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

	private void createRequisitionItems(HttpServletRequest request, Requisition requisitionDB) {
		Integer size = Integer.parseInt(request.getParameter("size"));
		for (int i = 1; i <= size; i++) {
			if (request.getParameter("quantity_" + i) != null && request.getParameter("tempProductId_" + i) != null) {
				RequisitionItem requisitionItem = new RequisitionItem();
				Product product = (Product) commonService.getAnObjectByAnyUniqueColumn("Product", "id",
						request.getParameter("tempProductId_" + i));

				requisitionItem.setProduct(product);
				requisitionItem.setQuantity(Integer.parseInt(request.getParameter("quantity_" + i)));
				requisitionItem.setRequisition(requisitionDB);
				commonService.saveOrUpdateModelObjectToDB(requisitionItem);
			}

		}
	}

	@RequestMapping(value = "/saveRequisition", method = RequestMethod.POST)
	public ModelAndView saveRequisition(@ModelAttribute("command") Requisition requisition, Principal principal,
			HttpServletRequest request, HttpSession session) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Employee loginEmployee = (Employee) session.getAttribute("loginEmployee");

		if (requisition.getId() != null) {

			Requisition requisitionDB = (Requisition) commonService.getAnObjectByAnyUniqueColumn("Requisition", "id",
					requisition.getId().toString());

			// final stage section
			if (requisitionDB.getSettings().getAuthRole().trim().equals(AUTH_STORE_MANAGER)) {

				// CREATE HISTORY
				RequisitionHistory requisitionHistory = new RequisitionHistory();
				requisitionHistory.setCreatedBy(loginEmployee);
				requisitionHistory.setCreatedDate(new Date());
				requisitionHistory.setRequisition(requisitionDB);
				requisitionHistory.setSettings(requisitionDB.getSettings());
				requisitionHistory.setRemarks(requisition.getRemarks());

				commonService.saveOrUpdateModelObjectToDB(requisitionHistory);

				List<Settings> settingsList = commonService
						.getObjectListByHqlQuery("from Settings where status=1 and stage>"
								+ requisitionDB.getSettings().getStage() + " order by stage ASC ")
						.stream().map(x -> (Settings) x).collect(Collectors.toList());
				if (!settingsList.isEmpty()) {
					requisitionDB.setSettings(settingsList.get(0));
				}
				requisitionDB.setModifiedBy(loginEmployee);
				requisitionDB.setModifiedDate(new Date());

				commonService.saveOrUpdateModelObjectToDB(requisitionDB);

			} else {
				requisitionDB.setModifiedBy(loginEmployee);
				requisitionDB.setModifiedDate(new Date());
				requisitionDB.setPurpose(requisition.getPurpose());
				requisitionDB.setRemarks(requisition.getRemarks());
				commonService.saveOrUpdateModelObjectToDB(requisitionDB);
			}

			List<RequisitionItem> requisitionItems = commonService
					.getObjectListByAnyColumn("RequisitionItem", "requisition_id", requisitionDB.getId().toString())
					.stream().map(x -> (RequisitionItem) x).collect(Collectors.toList());
			for (RequisitionItem requisitionItem : requisitionItems) {
				commonService.deleteAnObjectById("RequisitionItem", requisitionItem.getId());
			}

			createRequisitionItems(request, requisitionDB);

		} else {
			String roleName = commonService.getAuthRoleName();
			
			requisition.setCreatedBy(loginEmployee);
			requisition.setCreatedDate(new Date());
			requisition.setStatus(Integer.parseInt(STATUS_ACTIVE));
			RequisitionHistory requisitionHistory=new RequisitionHistory();
			if(roleName.trim().equals(AUTH_STORE_MANAGER)) {
				
				requisitionHistory.setCreatedBy(loginEmployee);
				requisitionHistory.setCreatedDate(new Date());
				
				Settings settings=(Settings) commonService.getAnObjectByAnyUniqueColumn("Settings", "auth_role", AUTH_STORE_MANAGER);
			
				requisitionHistory.setSettings(settings);
				List<Settings> settingsList = commonService
						.getObjectListByHqlQuery("from Settings where status=1 and stage>"+settings.getStage()+" order by stage ASC").stream()
						.map(x -> (Settings) x).collect(Collectors.toList());
				Employee employee=(Employee) commonService.getAnObjectByAnyUniqueColumn("Employee", "id", requisition.getEmployee_id().toString());
				
				requisition.setSettings(settingsList.get(0));
				requisition.setEmployee(employee);
				requisition.setIsVip(1);
				
			}else {
				List<Settings> settingsList = commonService
						.getObjectListByHqlQuery("from Settings where status=1 order by stage ASC").stream()
						.map(x -> (Settings) x).collect(Collectors.toList());
				
				
				requisition.setSettings(settingsList.get(0));
				requisition.setEmployee(loginEmployee);
			}
			
			
			Integer id = commonService.saveWithReturnId(requisition);
			Requisition requisitionDB = (Requisition) commonService.getAnObjectByAnyUniqueColumn("Requisition", "id",
					id.toString());
			createRequisitionItems(request, requisitionDB);
			
			if(roleName.trim().equals(AUTH_STORE_MANAGER)) {
				requisitionHistory.setRequisition(requisitionDB);
				requisitionHistory.setRemarks(requisition.getRemarks());
				commonService.saveOrUpdateModelObjectToDB(requisitionHistory);
			}
			

		}

		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/deleteReq/{id}", method = RequestMethod.GET)
	public @ResponseBody ModelAndView deleteReq(@ModelAttribute("command") Requisition command, BindingResult result,
			@PathVariable("id") String id, ModelMap model, RedirectAttributes redirectAttributes, Principal principal,
			HttpSession session) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		Requisition requisitionDB = (Requisition) commonService.getAnObjectByAnyUniqueColumn("Requisition", "id", id);
		requisitionDB.setStatus(Integer.parseInt(STATUS_DELETE));
		commonService.saveOrUpdateModelObjectToDB(requisitionDB);
		List<RequisitionItem> ItemList = commonService.getObjectListByAnyColumn("RequisitionItem", "requisition_id", id)
				.stream().map(x -> (RequisitionItem) x).collect(Collectors.toList());
		for (RequisitionItem requisitionItem : ItemList) {
			commonService.deleteAnObjectById("RequisitionItem", requisitionItem.getId());

		}

		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/approveReq", method = RequestMethod.POST)
	public @ResponseBody ModelAndView approveReq(HttpServletRequest request, ModelMap model,
			RedirectAttributes redirectAttributes, Principal principal, HttpSession session) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		Requisition requisitionDB = (Requisition) commonService.getAnObjectByAnyUniqueColumn("Requisition", "id",
				request.getParameter("req_id"));
		Employee loginEmployee = (Employee) session.getAttribute("loginEmployee");

		List<Settings> settingsList = commonService
				.getObjectListByHqlQuery("from Settings where status=1 and  stage>"
						+ requisitionDB.getSettings().getStage() + " order by stage ASC ")
				.stream().map(x -> (Settings) x).collect(Collectors.toList());
		if (!settingsList.isEmpty()) {

			// CREATE HISTORY
			RequisitionHistory requisitionHistory = new RequisitionHistory();
			requisitionHistory.setCreatedBy(loginEmployee);
			requisitionHistory.setCreatedDate(new Date());
			requisitionHistory.setRequisition(requisitionDB);
			requisitionHistory.setSettings(requisitionDB.getSettings());
			requisitionHistory.setRemarks(request.getParameter("remarks"));

			// send to next starge
			if (settingsList.size() == 1) {
				requisitionDB.setStatus(Integer.parseInt(STATUS_CLOSE));
				List<RequisitionItem> requisitionItems = commonService
						.getObjectListByAnyColumn("RequisitionItem", "requisition_id", requisitionDB.getId().toString())
						.stream().map(x -> (RequisitionItem) x).collect(Collectors.toList());
				;
				// deliver validation start
				boolean flag = false;

				String errorMessage = "";
				for (RequisitionItem requisitionItem : requisitionItems) {
					Stock stock = (Stock) commonService.getAnObjectByAnyUniqueColumn("Stock", "product_id",
							requisitionItem.getProduct().getId().toString());
					if (stock != null) {
						if (requisitionDB.getIsVip() == 1) {
							if (stock.getVipQuantity() < requisitionItem.getQuantity()) {
								flag = true;

								errorMessage += "," + requisitionItem.getProduct().getName() + "-"
										+ requisitionItem.getProduct().getId();
							}
						} else {
							if (stock.getQuantity() < requisitionItem.getQuantity()) {
								flag = true;
								errorMessage += "," + requisitionItem.getProduct().getName() + "-"
										+ requisitionItem.getProduct().getId();
							}
						}
					} else {
						flag = true;
						errorMessage += "," + requisitionItem.getProduct().getName() + "-"
								+ requisitionItem.getProduct().getId();
					}
				}
				// delivery validation end
				if (flag) {
					redirectAttributes.addFlashAttribute("errorMessage", errorMessage.trim().substring(1));
					return new ModelAndView("redirect:/showReq/" + requisitionDB.getId());
				} else {
					// generate delivery
					for (RequisitionItem requisitionItem : requisitionItems) {
						Stock stock = (Stock) commonService.getAnObjectByAnyUniqueColumn("Stock", "product_id",
								requisitionItem.getProduct().getId().toString());
						stock.setModifiedBy(loginEmployee);
						stock.setModifiedDate(new Date());
						if (requisitionDB.getIsVip() == 1) {
							stock.setVipQuantity(stock.getVipQuantity() - requisitionItem.getQuantity());
						} else {
							stock.setQuantity(stock.getQuantity() - requisitionItem.getQuantity());
						}
						commonService.saveOrUpdateModelObjectToDB(stock);
						ProductDelivery productDelivery = new ProductDelivery();
						productDelivery.setProduct(requisitionItem.getProduct());
						productDelivery.setCreatedBy(loginEmployee);
						productDelivery.setCreatedDate(new Date());
						productDelivery.setStatus(1);
						productDelivery.setQuantity(requisitionItem.getQuantity());
						commonService.saveOrUpdateModelObjectToDB(productDelivery);
					}
				}
			}
			requisitionDB.setSettings(settingsList.get(0));
			requisitionDB.setModifiedBy(loginEmployee);
			requisitionDB.setModifiedDate(new Date());
			requisitionDB.setRemarks(request.getParameter("remarks"));

			if (requisitionDB.getStatus().toString().equals(STATUS_REJECT)) {
				requisitionDB.setStatus(Integer.parseInt(STATUS_ACTIVE));
			}

			commonService.saveOrUpdateModelObjectToDB(requisitionHistory);
			commonService.saveOrUpdateModelObjectToDB(requisitionDB);

		}
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/rejectReq", method = RequestMethod.POST)
	public @ResponseBody ModelAndView rejectReq(HttpServletRequest request, ModelMap model,
			RedirectAttributes redirectAttributes, Principal principal, HttpSession session) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		Requisition requisitionDB = (Requisition) commonService.getAnObjectByAnyUniqueColumn("Requisition", "id",
				request.getParameter("req_id"));
		Employee loginEmployee = (Employee) session.getAttribute("loginEmployee");

		List<Settings> settingsList = commonService
				.getObjectListByHqlQuery("from Settings where status=1  order by stage ASC").stream()
				.map(x -> (Settings) x).collect(Collectors.toList());

		// CREATE HISTORY
		RequisitionHistory requisitionHistory = new RequisitionHistory();
		requisitionHistory.setCreatedBy(loginEmployee);
		requisitionHistory.setCreatedDate(new Date());
		requisitionHistory.setRequisition(requisitionDB);
		requisitionHistory.setSettings(requisitionDB.getSettings());
		requisitionHistory.setRemarks(request.getParameter("rejectionReason"));
		requisitionHistory.setIsRejected(1);

		requisitionDB.setSettings(settingsList.get(0));
		requisitionDB.setModifiedBy(loginEmployee);
		requisitionDB.setModifiedDate(new Date());
		requisitionDB.setRejectionReason(request.getParameter("rejectionReason"));
		requisitionDB.setStatus(Integer.parseInt(STATUS_REJECT));

		commonService.saveOrUpdateModelObjectToDB(requisitionHistory);
		commonService.saveOrUpdateModelObjectToDB(requisitionDB);

		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/editReq/{id}", method = RequestMethod.GET)
	public @ResponseBody ModelAndView editReq(@ModelAttribute("command") Requisition command, BindingResult result,
			@PathVariable("id") String id, ModelMap model, RedirectAttributes redirectAttributes, Principal principal,
			HttpSession session) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		Requisition requisition = (Requisition) commonService.getAnObjectByAnyUniqueColumn("Requisition", "id", id);
		List<RequisitionItem> ItemList = commonService
				.getObjectListByAnyColumn("RequisitionItem", "requisition_id", requisition.getId().toString()).stream()
				.map(x -> (RequisitionItem) x).collect(Collectors.toList());

		ItemList.forEach(x -> {
			x.getProduct().setWeightedAvgPrice(this.getWeighttedAvgPrice(x.getProduct().getId()));
			x.getProduct().setWeightedAvgPriceCurrency(
					NumberWordConverter.convertDoubleToCurrency(this.getWeighttedAvgPrice(x.getProduct().getId())));
			x.setTotalPriceCurrency(NumberWordConverter
					.convertDoubleToCurrency(x.getQuantity() * x.getProduct().getWeightedAvgPrice()));
			x.setTotalPrice(NumberWordConverter.round(x.getQuantity() * x.getProduct().getWeightedAvgPrice(), 2));

		});
		Double totalPriceMain = ItemList.stream()
				.mapToDouble(item -> item.getProduct().getWeightedAvgPrice() * item.getQuantity()).sum();

		List<Product> theProducts = commonService.getAllObjectList("Product").stream().map(x -> (Product) x)
				.filter(x -> x.getStatus() == 1).collect(Collectors.toList());
		theProducts.forEach(x -> x.setWeightedAvgPrice(this.getWeighttedAvgPrice(x.getId())));
		boolean showStoreManSection = false;

		if (requisition.getSettings().getAuthRole().trim().equals(AUTH_STORE_MANAGER)) {
			showStoreManSection = true;
		}
		model.put("productList", theProducts);

		model.put("requisition", requisition);
		model.put("totalPriceMain", NumberWordConverter.round(totalPriceMain, 2));
		model.put("ItemList", ItemList);
		model.put("count", ItemList.size() - 1);
		model.put("showStoreManSection", showStoreManSection);

		return new ModelAndView("editReq", model);
	}

	// methos to company show
	@RequestMapping(value = "/showReq/{id}", method = RequestMethod.GET)
	public @ResponseBody ModelAndView showJobAdvance(@ModelAttribute("command") Requisition command,
			BindingResult result, @PathVariable("id") String id, ModelMap model, RedirectAttributes redirectAttributes,
			Principal principal, HttpSession session) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		boolean showAppRjctBtn = false, showInitiatorSection = false, showStoreManSection = false;

		String roleName = commonService.getAuthRoleName();

		Employee loginEmployee = (Employee) session.getAttribute("loginEmployee");

		Requisition requisition = (Requisition) commonService.getAnObjectByAnyUniqueColumn("Requisition", "id", id);
		if (requisition.getId().toString().length() > 1)
			requisition.setReqNo("0" + requisition.getId());
		else
			requisition.setReqNo("00" + requisition.getId());

		if (requisition.getEmployee().getId().toString().equals(loginEmployee.getId().toString())
				&& requisition.getSettings().getStage().toString().trim().equals("10")) {
			showInitiatorSection = true;
		}

		List<RequisitionItem> ItemList = commonService
				.getObjectListByAnyColumn("RequisitionItem", "requisition_id", requisition.getId().toString()).stream()
				.map(x -> (RequisitionItem) x).collect(Collectors.toList());

		if (requisition.getEmployee().getId().toString().equals(loginEmployee.getId().toString())) {
			showAppRjctBtn = false;
		}

		if (requisition.getStatus().toString().equals(STATUS_CLOSE)
				|| requisition.getStatus().toString().equals(STATUS_REJECT)
				|| requisition.getStatus().toString().equals(STATUS_DELETE)) {
			showAppRjctBtn = false;
		}

		if (roleName != null && requisition != null && roleName.equals(requisition.getSettings().getAuthRole())) {
			showAppRjctBtn = true;
		}

		if (roleName.equals("ROLE_ADMIN") || roleName.equals("ROLE_SUPER_ADMIN")) {
			showAppRjctBtn = true;
		}

		if (requisition.getStatus().toString().equals("3")) {
			showAppRjctBtn = false;
		}
		if (requisition.getSettings().getAuthRole().trim().equals(AUTH_STORE_MANAGER)) {
			showStoreManSection = true;
		}

		List<RequisitionHistory> reqHistoryList = commonService
				.getObjectListByAnyColumn("RequisitionHistory", "requisition_id", requisition.getId().toString())
				.stream().map(x -> (RequisitionHistory) x).collect(Collectors.toList());
		reqHistoryList.sort(Comparator.comparing(RequisitionHistory::getId).reversed());
		// Double totalPriceMain=0.0;

		ItemList.forEach(x -> {
			x.getProduct().setWeightedAvgPrice(this.getWeighttedAvgPrice(x.getProduct().getId()));
			x.getProduct().setWeightedAvgPriceCurrency(
					NumberWordConverter.convertDoubleToCurrency(this.getWeighttedAvgPrice(x.getProduct().getId())));
			x.setTotalPriceCurrency(NumberWordConverter
					.convertDoubleToCurrency(x.getQuantity() * x.getProduct().getWeightedAvgPrice()));

		});
		Double totalPriceMain = ItemList.stream()
				.mapToDouble(item -> item.getProduct().getWeightedAvgPrice() * item.getQuantity()).sum();

		model.put("requisition", requisition);
		model.put("totalPriceMain", NumberWordConverter.convertDoubleToCurrency(totalPriceMain));
		model.put("ItemList", ItemList);
		model.put("showAppRjctBtn", showAppRjctBtn);
		model.put("reqHistoryList", reqHistoryList);
		model.put("showInitiatorSection", showInitiatorSection);
		model.put("showStoreManSection", showStoreManSection);

		return new ModelAndView("showReq", model);
	}

	@RequestMapping(value = "/ajaxProductQuantityCheckForRequisition", method = RequestMethod.POST)
	@ResponseBody
	public Boolean ajaxProductQuantityCheckForRequisition(Principal principal, HttpServletRequest request) {

		String productValue = request.getParameter("productValue");
		int quantityValue = Integer.parseInt(request.getParameter("quantityValue"));
		Stock stock = (Stock) commonService.getAnObjectByAnyUniqueColumn("Stock", "product_id", productValue);

		if (quantityValue > stock.getQuantity()) {
			return true;
		} else {
			return false;
		}

	}
	
	
	
	
	
	@RequestMapping(value = "/ajaxProductQuantityCheckForVIPRequisition", method = RequestMethod.POST)
	@ResponseBody
	public Boolean ajaxProductQuantityCheckForVIPRequisition(Principal principal, HttpServletRequest request) {

		String productValue = request.getParameter("productValue");
		int quantityValue = Integer.parseInt(request.getParameter("quantityValue"));
		Stock stock = (Stock) commonService.getAnObjectByAnyUniqueColumn("Stock", "product_id", productValue);

		if (quantityValue > stock.getVipQuantity()) {
			return true;
		} else {
			return false;
		}

	}
	
	
	
	
	
	
	

}
