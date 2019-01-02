<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<input id="id" name="id" value="${point.id}" type="hidden"
	class="form-control" />
<div class="form-group">
	<label for="name" class="col-sm-2 control-label"> <strong>
			Name :
	</strong>
	</label>
	<div class="col-sm-6">
		<input id="name" name="name"  value="${point.name}"
			class="form-control" />
	</div>
</div>
<div class="form-group">
	<label for="name" class="col-sm-2 control-label"> <strong>
			Keyword :
	</strong>
	</label>
	<div class="col-sm-6">
		<input id="pointKeyword" name="pointKeyword"  value="${point.pointKeyword}"
			class="form-control" />
	</div>
</div>


<div class="form-group">
	<label for="pointAddress" class="col-sm-2 control-label"> <strong>Address : </strong>
	</label>
	<div class="col-sm-6">
		<textarea class="form-control" id="pointAddress" name="pointAddress">${point.pointAddress}</textarea>
	</div>
</div>

<script>

$(function () {
    var month = (new Date()).getMonth() + 1;
    var year  = (new Date()).getFullYear();
    // US Format
    $('input[type=eu-date]').w2field('date',  { format: 'dd-mm-yyyy' });
    
    $('input[type=eu-date1]').w2field('date', { format: 'dd-mm-yyyy', end: $('input[type=eu-date2]') });
    $('input[type=eu-date2]').w2field('date', { format: 'dd-mm-yyyy', start: $('input[type=eu-date1]') });
    
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
				pointKeyword : "required",
				
			},

			// Make sure the form is submitted to the destination defined
			// in the "action" attribute of the form when valid
			submitHandler : function(form) {
				form.submit();
			}
		});
	});
</script>