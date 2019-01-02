<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<input id="id" name="id" value="${category.id}" type="hidden"
	class="form-control" />
<div class="form-group">
	<label for="name" class="col-sm-2 control-label"> <strong>
			Category Name : </strong>
	</label>
	<div class="col-sm-6">
		<input id="name" name="name" value="${category.name}"
			class="form-control" /> <span id="res" style="color: red;"></span>
	</div>
</div>



<div class="form-group">
	<label for="remarks" class="col-sm-2 control-label"> <strong>Remarks
			: </strong>
	</label>
	<div class="col-sm-6">
		<textarea class="form-control" id="remarks" name="remarks">${category.remarks}</textarea>
	</div>
</div>

<div class="form-group">
	<label for="status" class="col-sm-2 control-label"> <strong>Status
			: </strong>
	</label>
	<div class="col-sm-6">
		<select name="status" class="form-control">
			<option value="">Select</option>
			<option value="1" ${category.status eq '1'?'selected':''}>Active</option>
			<option value="0" ${category.status eq '0'?'selected':''}>Inactive</option>
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

			},

			// Make sure the form is submitted to the destination defined
			// in the "action" attribute of the form when valid
			submitHandler : function(form) {
				form.submit();
			}
		});
	});

	function fetchCategoryData(query) {
		$
				.ajax({
					url : "${pageContext.request.contextPath}"
							+ "/ajaxCategoryCheck",
					method : 'POST',
					data : {
						query : query
					},
					dataType : 'json',
					success : function(data) {
						if (data) {
							document.getElementById('res').innerHTML = "Category already Exist";
							document.getElementById('submitBtn').disabled = true;
						}

						else {
							document.getElementById('res').innerHTML = "";
							document.getElementById('submitBtn').disabled = false;
						}

					}
				})

	}

	var inputElement = document.getElementById('name');

	inputElement.onblur = function() {

		var categoryName = document.getElementById('name').value;
		fetchCategoryData(categoryName)
		console.log(categoryName);
	};
</script>