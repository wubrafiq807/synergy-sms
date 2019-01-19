<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<input id="id" name="id" value="${productRecive.id}" type="hidden"
	class="form-control" />



<div class="form-group">
	<label class="col-sm-2 control-label"> <strong>Select
			Product : </strong>
	</label>
	<div class="col-sm-6">
		<select name="productId" class="form-control">
			<option value="">Select</option>
			<c:forEach var="productList" items="${productsList}">
				<option value="${productList.id}"
					${productRecive.product.id ==productList.id?'selected':''}>${productList.name}</option>
			</c:forEach>
		</select>
	</div>
</div>




<div class="form-group">
	<label for="quantity" class="col-sm-2 control-label"> <strong>General
			Quantity : </strong>
	</label>
	<div class="col-sm-6">
		<input id="quantity" name="quantity" value="${productRecive.quantity}"
			type="number" class="form-control" />
	</div>
</div>

<div class="form-group">
	<label for="quantity" class="col-sm-2 control-label"> <strong>VIP
			Quantity : </strong>
	</label>
	<div class="col-sm-6">
		<input id="vipPurchaseQuantity" name="vipPurchaseQuantity" value="${productRecive.vipPurchaseQuantity}"
			type="number" class="form-control" />
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
				productId : "required",
				quantity : "required",
				vipPurchaseQuantity : "required",

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



