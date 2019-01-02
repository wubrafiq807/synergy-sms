<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<input id="id" name="id" value="${requisition.id}" type="hidden"
	class="form-control" />
<style>
.price_width, .price_com {
	width: 128px;
}
</style>
<div class="form-group">
	<label for="purpose" class="col-sm-2 control-label"> <strong>Purpose
			: </strong>
	</label>
	<div class="col-sm-8">
		<textarea class="form-control" id="purpose" name="purpose">${requisition.purpose}</textarea>
	</div>
</div>


<div class="form-group">
	<label for="remarks" class="col-sm-2 control-label"> <strong>Remarks
			: </strong>
	</label>
	<div class="col-sm-8">
		<textarea class="form-control" id="remarks" name="remarks">${requisition.remarks}</textarea>
	</div>
</div>

<div class="form-group" id="boxContainer">
	<div class="col-sm-8 col-sm-offset-2">
<c:forEach var="product" items="${productList}">

						<input type="hidden" id="product_${product.id}"
							value="${product.weightedAvgPrice}" />
					</c:forEach>
		<table class="table table-bordered" id="boxTable">
			<c:forEach var="item" items="${ItemList}" varStatus="loop">



				<tr id="boxRequistion${loop.index+1}">
					<th><label for="tempProductId">Product:</label> <select
						id="tempProductId${loop.index+1}" name="tempProductId_${loop.index+1}" onchange="run('${loop.index+1}')">
							<option value="">Select One</option>
							<c:forEach var="product" items="${productList}">
								<option value="${product.id}" ${item.product.id eq product.id?'selected':'' }>${product.name}</option>

							</c:forEach>
					</select></th>
					
					<th><label for="quantity">Quantity:</label> <input required
						id="quantity1" name="quantity_${loop.index+1}" value="${item.quantity}" type="number"
						onchange="totalPrice('1')"></th>

					<th><label for="price" style="margin-left: 36px;">Price:</label>
						<input id="price${loop.index+1}" class="price_com" name="price" value="${item.totalPrice}"
						type="number"  readonly></th>
					<th>
					<c:if test="${loop.index ne '0'}">
					<button id="btnDelete-${loop.index+1}" class="btn btn-danger" onclick="myFunction(this)"><i class="fa fa-times" aria-hidden="true"></i></button>
					</c:if>
					
					</th>
				</tr>
			</c:forEach>
			<tfoot>
				<tr>
					<td></td>
					<td></td>
					<td><label for="total">Total Price:</label> <input
						id="priceTotal" class="price_width" value="${totalPriceMain}" type="number"
						 readonly></td>
					<td><a href="" class="btn btn-primary" id="addRow"> <i
							class="fa fa-plus" aria-hidden="true"></i>
					</a></td>
				</tr>
			</tfoot>

		</table>
		<input type="hidden" name="size" value="${count+1}" id="size">
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

		$("form[name='form']").validate({

			rules : {

				purpose : "required",
				remarks : "required",

			},
			submitHandler : function(form) {
				form.submit();
			}
		});
	});

	$(function() {

		var count = ${count+1};

		document
				.getElementById("addRow")
				.addEventListener(
						"click",
						function(event) {
							event.preventDefault();
							count++;
							$('#size').val(count);
							var requisiton = '<tr id="boxRequistion' + count + '"><th><label for="tempProductId">Product:</label><select id="tempProductId'
									+ count
									+ '" name="tempProductId_'
									+ count
									+ '" onchange="run('
									+ count
									+ ')"><option value="">Select One</option><c:forEach var="product" items="${productList}"><option value="${product.id}">${product.name}</option></c:forEach></select></th><th><label for="quantity">Quantity:</label> <input required id="quantity'
									+ count
									+ '" name="quantity_'
									+ count
									+ '" value="" type="number" onblur="totalPrice('
									+ count
									+ ')"></th><th><label style="margin-left: 36px;" for="price">Price:</label> <input  id="price' + count + '" name="price" class="price_com" value="" type="number" step=0.01 readonly></th><th><button id="btnDelete-'
									+ count
									+ '" class="btn btn-danger" onclick="myFunction(this)"><i class="fa fa-times" aria-hidden="true"></i></button></th></tr>'

							document
									.getElementById('boxTable')
									.insertAdjacentHTML('beforeend', requisiton);

						});

	});

	function run(i) {
		totalPrice(i);
	}

	function totalPrice(z) {

		var productQuantity = $('#quantity' + z).val();
		var productID = $('#tempProductId' + z).val();
		var price = $('#product_' + productID).val() * productQuantity;

		$('#price' + z).val(numberformate(price));
		var total = 0.0;
		$(".price_com").each(function(index, v) {
			var val = parseFloat(v.value);
			if (!isNaN(val))
				total = total + val;

		});

		document.getElementById("priceTotal").value = total;
		console.log(total);
	}

	function numberformate(str) {

		var defaults = {
			cents : '.',
			decimal : ','
		}
		x = str.toString().split('.');
		x1 = x[0] + '.';
		x2 = x[1];
		if (x2)
			return (x1 + x2.substring(0, 2));
		else
			return (str);
	}

	function myFunction(r) {
		var data = r.id.split("-");
		var id = data[1];
		var deletedPrice = document.getElementById("price" + id).value;
		var total = parseFloat($('#priceTotal').val());
		var updatedTotalPrice = total - parseFloat(deletedPrice);
		document.getElementById("priceTotal").value = numberformate(updatedTotalPrice);
		var i = r.parentNode.parentNode.rowIndex;
		document.getElementById("boxTable").deleteRow(i);
	}
</script>