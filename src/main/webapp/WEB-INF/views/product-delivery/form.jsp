<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<input id="id" name="id" value="${productDelivery.id}" type="hidden"
	class="form-control" />



<div class="form-group">
	<label class="col-sm-2 control-label"> <strong>Select
			Product : </strong>
	</label>
	<div class="col-sm-6">
		<select name="productId" class="form-control" onchange="run()"
			id="selectedProduct">
			<option value="">Select</option>
			<c:forEach var="productList" items="${productsList}">
				<option value="${productList.id}"
					${productDelivery.product.id ==productList.id?'selected':''}>${productList.name}</option>
			</c:forEach>
		</select>
	</div>
	<div id="boxStock" style="display: none;">
		<label class="col-sm-4 "> <strong style="color: green">Available
				In Stock:</strong> <span id="amount">200</span>
		</label>
	</div>

</div>




<div class="form-group">
	<label for="quantity" class="col-sm-2 control-label"> <strong>Product
			Quantity : </strong>
	</label>
	<div class="col-sm-6">
		<input id="quantity" name="quantity"
			value="${productDelivery.quantity}" type="number"
			class="form-control" /><span id="res" style="color: red;"></span>
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

				quantity : "required",
				productId : "required"

			},

			// Make sure the form is submitted to the destination defined
			// in the "action" attribute of the form when valid
			submitHandler : function(form) {
				form.submit();
			}
		});
	});

	var productId;

	function run() {
		productId = document.getElementById("selectedProduct").value;

		if (productId !== null && productId !== undefined
				&& productId.length > 0) {
			cehckProductQuantity(productId);
		}

	}

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
							document.getElementById('submitBtn').disabled = true;
						}

						else {
							document.getElementById('res').innerHTML = "";
							document.getElementById('submitBtn').disabled = false;
						}

					}
				})

	}

	function cehckProductQuantity(productId) {
		$
				.ajax({
					url : "${pageContext.request.contextPath}"
							+ "/ajaxProductQuantityCheckOnly",
					method : 'POST',
					data : {
						productId : productId

					},
					dataType : 'json',
					success : function(data) {

						document.getElementById("boxStock").style.display = "inline";
						document.getElementById('amount').innerHTML = data;

					}
				})

	}
</script>



