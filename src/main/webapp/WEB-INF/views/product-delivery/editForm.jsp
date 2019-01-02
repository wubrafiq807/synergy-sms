<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<input id="id" name="id" value="${productDelivery.id}" type="hidden"
	class="form-control" />
<input id="productId" name="productId"
	value="${productDelivery.product.id}" type="hidden" class="form-control" />


<div class="form-group">
	<label for="quantity" class="col-sm-2  control-label"> <strong>Product
			Name : </strong>
	</label>
	<div class="col-sm-6">
		<input placeholder="${productDelivery.product.name}" type="number"
			class="form-control" readonly />
	</div>
</div>


<div class="form-group">
	<label for="quantity" class="col-sm-2  control-label"> <strong>Product
			Quantity : </strong>
	</label>
	<div class="col-sm-6">
		<input id="quantity" name="quantity" value="${productDelivery.quantity}"
			type="number" class="form-control" /><span id="res" style="color: red;"></span>
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
	
	
	
	var productId = document.getElementById("productId").value;
	
	console.log(productId);
	
	document.getElementById("quantity").onkeyup = function() {

		var deliveryQuantity = document.getElementById("quantity").value;
		if ((productId !== null && productId !== undefined && productId.length > 0)
				&& (deliveryQuantity !== null && deliveryQuantity !== undefined && deliveryQuantity.length > 0)) {

			cehckProductData(productId, deliveryQuantity);

		} else {

		}

	};

	function cehckProductData(productId, deliveryQuantity) {
		$
				.ajax({
					url : "${pageContext.request.contextPath}"
							+ "/ajaxProductQuantityCheck",
					method : 'POST',
					data : {
						productId : productId,
						deliveryQuantity : deliveryQuantity
					},
					dataType : 'json',
					success : function(data) {

						if (data) {
							document.getElementById('res').innerHTML = "Stock Overflow";
							document.getElementById('updateBtn').disabled = true;
						}

						else {
							document.getElementById('res').innerHTML = "";
							document.getElementById('updateBtn').disabled = false;
						}

					}
				})

	}
	
	
	
	
	
	
	
	

</script>



