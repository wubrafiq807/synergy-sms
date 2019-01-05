<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<input id="id" name="id" value="${product.id}" type="hidden"
	class="form-control" />

<div class="form-group">
	<label for="name" class="col-sm-2 control-label"> <strong>
			Product Name : </strong>
	</label>
	<div class="col-sm-6">
		<input id="name" name="name" value="${product.name}"
			class="form-control" /> <span id="resPname" style="color: red;"></span>
	</div>
</div>

<div class="form-group">
	<label for="purchaseQuantity" class="col-sm-2 control-label"> <strong>Purchase
			Quantity : </strong>
	</label>
	<div class="col-sm-6">
		<input id="purchaseQuantity" name="purchaseQuantity"
			value="${product.purchaseQuantity}" type="number"
			class="form-control" onblur="checkQuantityValue()" /> <span
			id="resQuantity" style="color: red;"></span>
	</div>
</div>
<div class="form-group">
	<label for="price" class="col-sm-2 control-label"> <strong>Price
			: </strong>
	</label>
	<div class="col-sm-6">
		<input id="price" name="price" value="${product.price}" type="number"
			step=0.01 class="form-control" />
	</div>
</div>

<div class="form-group">
	<label for="description" class="col-sm-2 control-label"> <strong>Description
			: </strong>
	</label>
	<div class="col-sm-6">
		<textarea class="form-control" id="description" name="description">${product.description}</textarea>
	</div>
</div>


<div class="form-group">
	<label class="col-sm-2 control-label"> <strong>Select
			Category : </strong>
	</label>
	<div class="col-sm-6">
		<select name="categoryId" class="form-control"
			onchange="runCategory()" id="selectedCategory">
			<option value="">Select</option>
			<c:forEach var="category" items="${categoryList}">
				<option value="${category.id}"
					${product.category.id ==category.id?'selected':''}>${category.name}</option>
			</c:forEach>
		</select> <span id="resPCname" style="color: red;"></span>
	</div>
</div>


<div class="form-group">
	<label for="remarks" class="col-sm-2 control-label"> <strong>Remarks
			: </strong>
	</label>
	<div class="col-sm-6">
		<textarea class="form-control" id="remarks" name="remarks">${product.remarks}</textarea>
	</div>
</div>


<div class="form-group">
	<label for="status" class="col-sm-2 control-label"> <strong>Status
			: </strong>
	</label>
	<div class="col-sm-6">
		<select name="status" class="form-control">
			<option value="">Select</option>
			<option value="1" ${product.status eq '1'?'selected':''}>Active</option>
			<option value="0" ${product.status eq '0'?'selected':''}>Inactive</option>
		</select>
	</div>
</div>

<script>
	$(function() {
		var month = (new Date()).getMonth() + 1;
		var year = (new Date()).getFullYear();
		// US Format
		$('input[type=eu-date]').w2field('date', {
			format : 'dd-mm-yyyy'
		});

		$('input[type=eu-date1]').w2field('date', {
			format : 'dd-mm-yyyy',
			end : $('input[type=eu-date2]')
		});
		$('input[type=eu-date2]').w2field('date', {
			format : 'dd-mm-yyyy',
			start : $('input[type=eu-date1]')
		});

	});

	$(function() {

		// Initialize form validation on the registration form.
		// It has the name attribute "registration"
		$("form[name='form']").validate({
			// Specify validation rules
			rules : {
				// The key name on the left side is the name attribute
				// of an input field. Validation rules are defined
				// on the right side
				name : "required",
				status : "required",
				price : "required",
				categoryId : "required",
				modelId : "required",

			},

			// Make sure the form is submitted to the destination defined
			// in the "action" attribute of the form when valid
			submitHandler : function(form) {
				form.submit();
			}
		});
	});

	

	var productName;
	var modelId;
	var categoryId;

	document.getElementById('name').onblur = function() {
		productName = document.getElementById('name').value;
		categoryId = document.getElementById("selectedCategory").value;
		
		if ((productName !== null && productName !== undefined && productName.length > 0)
				&& (categoryId !== null && categoryId !== undefined && categoryId.length > 0)) {

			cehckDuplicateProductCategoryModel(productName, categoryId);

		}

	}

	// 	function runModel() {
	// 		modelId = document.getElementById("selectedModel").value;

	// 	}

	function runCategory() {
		categoryId = document.getElementById("selectedCategory").value;

		// 		if ((productName !== null && productName !== undefined && productName.length > 0)
		// 				&& (modelId !== null && modelId !== undefined && modelId.length > 0)
		// 				&& (categoryId !== null && categoryId !== undefined && categoryId.length > 0)) {

		// 			cehckDuplicateProductCategoryModel(productName, modelId, categoryId);

		// 		} 

		if ((productName !== null && productName !== undefined && productName.length > 0)
				&& (categoryId !== null && categoryId !== undefined && categoryId.length > 0)) {

			cehckDuplicateProductCategoryModel(productName, categoryId);

		}

		//console.log(modelId);
		console.log(productName);
		console.log(categoryId);
	}

	function cehckDuplicateProductCategoryModel(productName, categoryId) {
		$
				.ajax({
					url : "${pageContext.request.contextPath}"
							+ "/ajaxDuplicateProductCategoryModelCheck",
					method : 'POST',
					data : {
						productName : productName,
						categoryId : categoryId,
					},
					dataType : 'json',
					success : function(data) {

						if (data) {
							document.getElementById('submitBtn').disabled = false;
							document.getElementById('resPname').innerHTML = "";
							// 							document.getElementById('resPMname').innerHTML = "";
							document.getElementById('resPCname').innerHTML = "";
						} else {
							document.getElementById('submitBtn').disabled = true;
							document.getElementById('resPname').innerHTML = "Same product already exist";
							// 							document.getElementById('resPMname').innerHTML = "Same product with same model already exist";
							document.getElementById('resPCname').innerHTML = "Same product with same model and category already exist";
						}

					}
				})

	}

	function checkQuantityValue() {
		var quantity = Number(document.getElementById("purchaseQuantity").value);

		if (quantity === parseInt(quantity, 10)) {

			document.getElementById('submitBtn').disabled = false;
			document.getElementById('resQuantity').innerHTML = "";
		}

		else {
			console.log("data is not an integer")

			document.getElementById('submitBtn').disabled = true;
			document.getElementById('resQuantity').innerHTML = "choose a proper quantiy number";

		}

		//console.log(Number.isInteger(quantity));

	}
</script>



