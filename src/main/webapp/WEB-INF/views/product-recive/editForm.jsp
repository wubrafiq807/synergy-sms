<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<input id="id" name="id" value="${productRecive.id}" type="hidden"
	class="form-control" />
<input id="productId" name="productId"
	value="${productRecive.product.id}" type="hidden" class="form-control" />



<!-- <div class="form-group"> -->
<!-- 	<label class="col-sm-8 col-sm-offset-1 pull-left"> <strong> -->
<%-- 			Product : <span>${productRecive.product.name}</span> --%>
<!-- 	</strong> -->
<!-- 	</label> -->
<!-- </div> -->

<div class="form-group">
	<label for="quantity" class="col-sm-2  control-label"> <strong>Product
			Name : </strong>
	</label>
	<div class="col-sm-6">
		<input placeholder="${productRecive.product.name}" type="number"
			class="form-control" readonly />
	</div>
</div>


<div class="form-group">
	<label for="quantity" class="col-sm-2  control-label"> <strong>Product
			Quantity : </strong>
	</label>
	<div class="col-sm-6">
		<input id="quantity" name="quantity" value="${productRecive.quantity}"
			type="number" class="form-control" />
	</div>
</div>


<script>
	$(function() {

		// Initialize form validation on the registration form.
		// It has the name attribute "registration"
		$("form[name='form']").validate({
			// Specify validation rules
			rules : {

				quantity : "required",

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



