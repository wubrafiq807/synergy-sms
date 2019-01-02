<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<input id="id" name="id" value="${product.id}" type="hidden"
	class="form-control" />

<div class="form-group">
	<label for="name" class="col-sm-2 control-label"> <strong>
			Product Name : </strong>
	</label>
	<div class="col-sm-6">
		<input id="name" name="name" value="${product.name}"
			class="form-control" />
	</div>
</div>

<div class="form-group">
	<label for="purchaseQuantity" class="col-sm-2 control-label"> <strong>Purchase Quantity
			: </strong>
	</label>
	<div class="col-sm-6">
		<input id="purchaseQuantity" name="purchaseQuantity" value="${product.purchaseQuantity}" type="number"
			 class="form-control" />
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
			Model : </strong>
	</label>
	<div class="col-sm-6">
		<select name="modelId" class="form-control">
			<option value="">Select</option>
			<c:forEach var="model" items="${modelList}">
				<option value="${model.id}" ${product.model.id ==model.id?'selected':''}>${model.name}</option>
			</c:forEach>
		</select>
	</div>
</div>

<div class="form-group">
	<label class="col-sm-2 control-label"> <strong>Select
			Category : </strong>
	</label>
	<div class="col-sm-6">
		<select name="categoryId" class="form-control">
			<option value="">Select</option>
			<c:forEach var="category" items="${categoryList}">
				<option value="${category.id}" ${product.category.id ==category.id?'selected':''}>${category.name}</option>
			</c:forEach>
		</select>
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
	

//     document.forms['editProductForm'].elements['categoryId'].value = '${product.category.id}';
// 	document.forms['editProductForm'].elements['modelId'].value = '${product.model.id}'  
	

/* 	
	 $('form').find('input[name="categoryId"]').val('${product.category.id}');
	$('form').find('input[name="modelId"]').val('${product.model.id}'); */ 
</script>



