package com.nazdaq.sms.controller;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.nazdaq.sms.model.Category;
import com.nazdaq.sms.model.Employee;
import com.nazdaq.sms.model.Model;
import com.nazdaq.sms.model.Product;
import com.nazdaq.sms.model.ProductDelivery;
import com.nazdaq.sms.model.ProductPriceHistory;
import com.nazdaq.sms.model.ProductRecive;
import com.nazdaq.sms.model.Stock;
import com.nazdaq.sms.service.CommonService;
import com.nazdaq.sms.service.UserService;
import com.nazdaq.sms.util.NumberWordConverter;

@Controller
public class ProductController {

	@Autowired
	private UserService userService;

	@Autowired
	private CommonService commonService;

	@RequestMapping(value = "/addProduct", method = RequestMethod.GET)
	public ModelAndView addProduct(ModelMap theModel, Principal principal) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		List<Category> theCategories = commonService.getAllObjectList("Category").stream().map(x -> (Category) x)
				.filter(x -> x.getStatus() == 1).collect(Collectors.toList());
		List<Model> themodels = commonService.getAllObjectList("Model").stream().map(x -> (Model) x)
				.filter(x -> x.getStatus() == 1).collect(Collectors.toList());
		theModel.addAttribute("categoryList", theCategories);

		theModel.addAttribute("modelList", themodels);

		Product product = new Product();

		theModel.addAttribute("command", product);

		return new ModelAndView("productAdd");
	}

	@RequestMapping(value = "/saveProduct", method = RequestMethod.POST)
	public ModelAndView saveProduct(@ModelAttribute("command") Product product, Principal principal,
			HttpSession session) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Employee loginEmployee = (Employee) session.getAttribute("loginEmployee");

		if (product.getId() != null) {

			Product productDb = (Product) commonService.getAnObjectByAnyUniqueColumn("Product", "id",
					product.getId().toString());

			Category category = (Category) commonService.getAnObjectByAnyUniqueColumn("Category", "id",
					product.getCategoryId().toString());

//			Model model = (Model) commonService.getAnObjectByAnyUniqueColumn("Model", "id",
//					product.getModelId().toString());
			Stock stock = (Stock) commonService.getAnObjectByAnyUniqueColumn("Stock", "product_id",
					product.getId().toString());
			Integer totalQuantity=product.getPurchaseQuantity()+product.getVipPurchaseQuantity();
			Integer totalQuantityDB=productDb.getPurchaseQuantity()+productDb.getVipPurchaseQuantity();

			if (totalQuantity != totalQuantityDB) {

				// UPDATING THE PRODUCT QUANTITY TABLE

				stock.setQuantity(product.getPurchaseQuantity());
				stock.setVipQuantity(product.getVipPurchaseQuantity());
				commonService.saveOrUpdateModelObjectToDB(stock);

				// SAVING NEW DATA TO PRODUCT RECIVE TABLE

				ProductRecive productRecive = new ProductRecive();
				productRecive.setProduct(product);
				productRecive.setQuantity(totalQuantity);
				productRecive.setModifiedBy(loginEmployee);
				productRecive.setModifiedDate(new Date());
				commonService.saveOrUpdateModelObjectToDB(productRecive);

			}

			productDb.setCategory(category);
			//productDb.setModel(model);
			productDb.setName(product.getName());
			productDb.setRemarks(product.getRemarks());
			productDb.setStatus(product.getStatus());
			productDb.setPrice(product.getPrice());
			productDb.setDescription(product.getDescription());
			productDb.setPurchaseQuantity(product.getPurchaseQuantity());
			productDb.setVipPurchaseQuantity(product.getVipPurchaseQuantity());
			productDb.setModifiedDate(new Date());
			productDb.setModifiedBy(loginEmployee);
			commonService.saveOrUpdateModelObjectToDB(productDb);

			List<ProductPriceHistory> priceHistories = commonService
					.getObjectListByHqlQuery(
							"from ProductPriceHistory where product_id=" + product.getId() + " order by id desc")
					.stream().map(x -> (ProductPriceHistory) x).collect(Collectors.toList());
			if (!priceHistories.isEmpty()) {
				if (product.getPrice() != priceHistories.get(0).getPrice()
						|| totalQuantity != priceHistories.get(0).getPurchaseQuantity()) {
					ProductPriceHistory productPriceHistory = new ProductPriceHistory();
					productPriceHistory.setProduct(product);
					productPriceHistory.setPrice(product.getPrice());
					productPriceHistory.setCreatedBy(loginEmployee);
					productPriceHistory.setCreatedDate(new Date());
					productPriceHistory.setPurchaseQuantity(product.getPurchaseQuantity()+product.getVipPurchaseQuantity());
					commonService.saveOrUpdateModelObjectToDB(productPriceHistory);
				}
			}

		} else {

			// PRODUCT HISTORY GENERATE

			Category category = (Category) commonService.getAnObjectByAnyUniqueColumn("Category", "id",
					product.getCategoryId().toString());
//			Model model = (Model) commonService.getAnObjectByAnyUniqueColumn("Model", "id",
//					product.getModelId().toString());

			product.setCategory(category);
			//product.setModel(model);
			product.setCreatedBy(loginEmployee);
			product.setCreatedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(product);

			// PRODUCT PRICE HISTORY GENERATE

			ProductPriceHistory productPriceHistory = new ProductPriceHistory();
			productPriceHistory.setProduct(product);
			productPriceHistory.setPrice(product.getPrice());
			
			productPriceHistory.setPurchaseQuantity(product.getPurchaseQuantity()+product.getVipPurchaseQuantity());
			productPriceHistory.setCreatedBy(loginEmployee);
			productPriceHistory.setCreatedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(productPriceHistory);

			// STOCK BLANCE GENERATE

			Stock stock = new Stock();
			stock.setProduct(product);
			stock.setQuantity(product.getPurchaseQuantity());
			stock.setVipQuantity(product.getVipPurchaseQuantity());
			stock.setCreatedBy(loginEmployee);
			stock.setCreatedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(stock);

			// PRODUCT RECEIVE GENERATE

			ProductRecive productRecive = new ProductRecive();
			productRecive.setProduct(product);
			productRecive.setQuantity(product.getPurchaseQuantity()+product.getVipPurchaseQuantity());
			productRecive.setCreatedBy(loginEmployee);
			productRecive.setCreatedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(productRecive);
		}

		return new ModelAndView("redirect:/listProduct");
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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/listProduct", method = RequestMethod.GET)
	public ModelAndView listProduct(ModelMap theModel, Principal principal) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		List<Product> products = (List<Product>) (Object) commonService.getAllObjectList("Product").stream()
				.map(x -> (Product) x).filter(x -> x.getStatus() != 2).collect(Collectors.toList());
		products.forEach(x ->{
			x.setWeightedAvgPriceCurrency(
					NumberWordConverter.convertDoubleToCurrency(this.getWeighttedAvgPrice(x.getId())));
			if(x.getVipPurchaseQuantity()!=null)
			x.setPurchaseQuantity(x.getPurchaseQuantity()+x.getVipPurchaseQuantity());
		});

		theModel.addAttribute("products", products);

		return new ModelAndView("productList");

	}

	@RequestMapping(value = "/updateProduct", method = RequestMethod.GET)
	public ModelAndView updateProduct(@ModelAttribute("command") Product Product, ModelMap theModel,
			HttpServletRequest request) {

		Product product = (Product) commonService.getAnObjectByAnyUniqueColumn("Product", "id",
				request.getParameter("id"));

		List<Category> theCategories = commonService.getAllObjectList("Category").stream().map(x -> (Category) x)
				.filter(x -> x.getStatus() == 1).collect(Collectors.toList());
//		List<Model> themodels = commonService.getAllObjectList("Model").stream().map(x -> (Model) x)
//				.filter(x -> x.getStatus() == 1).collect(Collectors.toList());

		theModel.addAttribute("categoryList", theCategories);
		//theModel.addAttribute("modelList", themodels);
		theModel.addAttribute("product", product);

		return new ModelAndView("productEdit");
	}

	@RequestMapping(value = "/deleteProduct", method = RequestMethod.GET)
	public ModelAndView deleteProduct(@ModelAttribute("command") Product Product, ModelMap theModel,
			HttpServletRequest request) {

		Product product = (Product) commonService.getAnObjectByAnyUniqueColumn("Product", "id",
				request.getParameter("id"));

		product.setStatus(2);

		commonService.saveOrUpdateModelObjectToDB(product);
		return new ModelAndView("redirect:/listProduct");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/listStock", method = RequestMethod.GET)
	public ModelAndView listStock(ModelMap theModel, Principal principal) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		List<Stock> stocks = (List<Stock>) (Object) commonService.getAllObjectList("Stock");

		theModel.addAttribute("stocks", stocks);

		return new ModelAndView("productStockList");

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addReciveProduct", method = RequestMethod.GET)
	public ModelAndView addReciveProduct(ModelMap theModel, Principal principal) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		List<Product> productsList = (List<Product>) (Object) commonService.getAllObjectList("Product");
		theModel.addAttribute("productsList", productsList);
		ProductRecive productRecive = new ProductRecive();
		theModel.addAttribute("command", productRecive);
		return new ModelAndView("reciveProductAdd");

	}

	@RequestMapping(value = "/saveReceiveProduct", method = RequestMethod.POST)
	public ModelAndView saveProductCategory(@ModelAttribute("command") ProductRecive productReceive,
			Principal principal, HttpSession session) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Employee loginEmployee = (Employee) session.getAttribute("loginEmployee");

		if (productReceive.getId() != null) {

			ProductRecive productReceiveDb = (ProductRecive) commonService.getAnObjectByAnyUniqueColumn("ProductRecive",
					"id", productReceive.getId().toString());

			Stock stock = (Stock) commonService.getAnObjectByAnyUniqueColumn("Stock", "product_id",
					productReceive.getProductId().toString());

			stock.setQuantity((stock.getQuantity() - productReceiveDb.getQuantity()) + productReceive.getQuantity());
			commonService.saveOrUpdateModelObjectToDB(stock);

			productReceiveDb.setQuantity(productReceive.getQuantity());
			productReceiveDb.setModifiedDate(new Date());
			productReceiveDb.setModifiedBy(loginEmployee);

			commonService.saveOrUpdateModelObjectToDB(productReceiveDb);
		} else {

			Product product = (Product) commonService.getAnObjectByAnyUniqueColumn("Product", "id",
					productReceive.getProductId().toString());

			productReceive.setProduct(product);
			productReceive.setQuantity(productReceive.getQuantity());
			productReceive.setCreatedBy(loginEmployee);
			productReceive.setCreatedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(productReceive);

			Stock stock = (Stock) commonService.getAnObjectByAnyUniqueColumn("Stock", "product_id",
					product.getId().toString());

			stock.setQuantity(stock.getQuantity() + productReceive.getQuantity());
			stock.setModifiedBy(loginEmployee);
			stock.setModifiedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(stock);
		}

		// commonService.saveOrUpdateModelObjectToDB(Category);
		return new ModelAndView("redirect:/listReceiveProduct");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/listReceiveProduct", method = RequestMethod.GET)
	public ModelAndView listReceiveProduct(ModelMap theModel, Principal principal) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		List<ProductRecive> productReciveLists = (List<ProductRecive>) (Object) commonService
				.getAllObjectList("ProductRecive");

		theModel.addAttribute("productReciveLists", productReciveLists);

		return new ModelAndView("productReceiveList");

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateReceiveProduct", method = RequestMethod.GET)
	public ModelAndView updateReceiveProduct(@ModelAttribute("command") Category Category, ModelMap theModel,
			HttpServletRequest request) {

		List<Product> productsList = (List<Product>) (Object) commonService.getAllObjectList("Product");
		theModel.addAttribute("productsList", productsList);
		ProductRecive productRecive = (ProductRecive) commonService.getAnObjectByAnyUniqueColumn("ProductRecive", "id",
				request.getParameter("id"));
		theModel.addAttribute("productRecive", productRecive);
		return new ModelAndView("productReceiveEdit");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addDeliveryProduct", method = RequestMethod.GET)
	public ModelAndView addDeliveryProduct(ModelMap theModel, Principal principal) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		List<Product> productsList = (List<Product>) (Object) commonService.getAllObjectList("Product");
		theModel.addAttribute("productsList", productsList);
		ProductDelivery productDelivery = new ProductDelivery();
		theModel.addAttribute("command", productDelivery);
		return new ModelAndView("deliveryProductAdd");

	}

	@RequestMapping(value = "/saveDeliveryProduct", method = RequestMethod.POST)
	public ModelAndView saveDeliveryProduct(@ModelAttribute("command") ProductDelivery productDelivery,
			Principal principal, HttpSession session) {

		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		Employee loginEmployee = (Employee) session.getAttribute("loginEmployee");

		if (productDelivery.getId() != null) {

			ProductDelivery productDeliveryDb = (ProductDelivery) commonService
					.getAnObjectByAnyUniqueColumn("ProductDelivery", "id", productDelivery.getId().toString());

			Stock stock = (Stock) commonService.getAnObjectByAnyUniqueColumn("Stock", "product_id",
					productDelivery.getProductId().toString());

			stock.setQuantity((stock.getQuantity() + productDeliveryDb.getQuantity()) - productDelivery.getQuantity());
			commonService.saveOrUpdateModelObjectToDB(stock);

			productDeliveryDb.setQuantity(productDelivery.getQuantity());
			productDeliveryDb.setModifiedDate(new Date());
			productDeliveryDb.setModifiedBy(loginEmployee);

			commonService.saveOrUpdateModelObjectToDB(productDeliveryDb);
		} else {

			Product product = (Product) commonService.getAnObjectByAnyUniqueColumn("Product", "id",
					productDelivery.getProductId().toString());

			productDelivery.setProduct(product);
			productDelivery.setQuantity(productDelivery.getQuantity());
			productDelivery.setCreatedBy(loginEmployee);
			productDelivery.setCreatedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(productDelivery);

			Stock stock = (Stock) commonService.getAnObjectByAnyUniqueColumn("Stock", "product_id",
					product.getId().toString());

			stock.setQuantity(stock.getQuantity() - productDelivery.getQuantity());
			stock.setModifiedBy(loginEmployee);
			stock.setModifiedDate(new Date());
			commonService.saveOrUpdateModelObjectToDB(stock);
		}

		// commonService.saveOrUpdateModelObjectToDB(Category);
		return new ModelAndView("redirect:/listDeliveryProduct");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/listDeliveryProduct", method = RequestMethod.GET)
	public ModelAndView listDeliveryProduct(ModelMap theModel, Principal principal) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}

		List<ProductDelivery> productDeliveryLists = (List<ProductDelivery>) (Object) commonService
				.getAllObjectList("ProductDelivery");

		theModel.addAttribute("productDeliveryLists", productDeliveryLists);

		return new ModelAndView("productDeliveryList");

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateDeliveryProduct", method = RequestMethod.GET)
	public ModelAndView updateDeliveryProduct(@ModelAttribute("command") Category Category, ModelMap theModel,
			HttpServletRequest request) {

		List<Product> productsList = (List<Product>) (Object) commonService.getAllObjectList("Product");
		theModel.addAttribute("productsList", productsList);
		ProductDelivery productDelivery = (ProductDelivery) commonService
				.getAnObjectByAnyUniqueColumn("ProductDelivery", "id", request.getParameter("id"));
		theModel.addAttribute("productDelivery", productDelivery);
		return new ModelAndView("productDeliveryEdit");
	}

	@RequestMapping(value = "/ajaxProductQuantityCheck", method = RequestMethod.POST)
	@ResponseBody
	public Boolean ajaxProductQuantityCheck(Principal principal, HttpServletRequest request) {

		String productId = request.getParameter("productId");
		int deliveryQuantity = Integer.parseInt(request.getParameter("deliveryQuantity"));
		Stock stock = (Stock) commonService.getAnObjectByAnyUniqueColumn("Stock", "product_id", productId);

		if (deliveryQuantity > stock.getQuantity()) {
			return true;
		} else {
			return false;
		}

	}

	@RequestMapping(value = "/ajaxProductQuantityCheckOnly", method = RequestMethod.POST)
	@ResponseBody
	public int ajaxProductQuantityCheckOnly(Principal principal, HttpServletRequest request) {

		String productId = request.getParameter("productId");
		Stock stock = (Stock) commonService.getAnObjectByAnyUniqueColumn("Stock", "product_id", productId);

		return stock.getQuantity();

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ajaxDuplicateProductCategoryModelCheck", method = RequestMethod.POST)
	@ResponseBody
	public Boolean ajaxDuplicateProductCategoryModelCheck(Principal principal, HttpServletRequest request) {

		String productName = request.getParameter("productName");
		//String modelId = request.getParameter("modelId");
		String categoryId = request.getParameter("categoryId");

//		String queryString = "from Product where name = '" + productName + "'" + " and model_id = " + modelId
//				+ " and category_id = " + categoryId;
		
		String queryString = "from Product where name = '" + productName + "'" + " and category_id = " + categoryId;

		List<Product> productDeliveryLists = (List<Product>) (Object) commonService
				.getObjectListByHqlQuery(queryString);

		if (productDeliveryLists == null || productDeliveryLists.isEmpty()) {
			return true;
		} else {
			return false;
		}

	}

}
