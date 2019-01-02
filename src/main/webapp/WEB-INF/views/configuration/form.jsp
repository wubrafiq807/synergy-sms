<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<input id="id" name="id" value="${settings.id}" type="hidden"
	class="form-control" />
<div class="form-group">
	<label for="stage" class="col-sm-2 control-label"> <strong>
			Stage : </strong>
	</label>
	<div class="col-sm-6">
		<input id="stage" name="stage" value="${settings.stage}"
			class="form-control" type="number" />
	</div>
</div>


<div class="form-group">
	<label for="rejectToId" class="col-sm-2 control-label"> <strong>
			Reject To : </strong>
	</label>
	<div class="col-sm-6">
		<select id="rejectToId" name="rejectToId" class="form-control">
			<option value="">Select One</option>
			<c:forEach var="setting" items="${settingsList}">
				<option value="${setting.id}" ${settings.rejectTo.id == setting.id?'selected':''}>${setting.stage}</option>
			</c:forEach>
		</select>
	</div>
</div>


<div class="form-group">
	<label for="stage" class="col-sm-2 control-label"> <strong>
			Authentication Role </strong>
	</label>
	<div class="col-sm-6">
		<input id="authRole" name="authRole" value="${settings.authRole}"
			type="text" class="form-control">
	</div>
</div>


<div class="form-group">
	<label for="btnText" class="col-sm-2 control-label"> <strong>
			Button Text <span style="color: red">*</span>:
	</strong>
	</label>
	<div class="col-sm-6">
		<input id="btnText" name="btnText" value="${settings.btnText}"
			type="text" class="form-control">
	</div>
</div>


<div class="form-group">
	<label for="btnClass" class="col-sm-2 control-label"> <strong>
			Button color <span style="color: red">*</span>:
	</strong>
	</label>
	<div class="col-sm-6">

		<select id="btnClass" name="btnClass" class="form-control">
			<option value="">Select One</option>
			<option value="btn btn-basic btn-xs" ${settings.btnClass eq 'btn btn-basic btn-xs'?'selected':''}>Basic</option>
			<option value="btn btn-default btn-xs" ${settings.btnClass eq 'btn btn-default btn-xs'?'selected':''}>Default</option>
			<option value="btn btn-primary btn-xs" ${settings.btnClass eq 'btn btn-primary btn-xs'?'selected':''}>Primary</option>			
			<option value="btn btn-success btn-xs" ${settings.btnClass eq 'btn btn-success btn-xs'?'selected':''}>Success</option>
			<option value="btn btn-info btn-xs" ${settings.btnClass eq 'btn btn-info btn-xs'?'selected':''}>Info</option>
			<option value="btn btn-warning btn-xs" ${settings.btnClass eq 'btn btn-warning btn-xs'?'selected':''}>Warning</option>
			<option value="btn btn-danger btn-xs" ${settings.btnClass eq 'btn btn-danger btn-xs'?'selected':''}>Danger</option>	
		</select>
	</div>
</div>

<div class="form-group">
	<label for="canReject" class="col-sm-2 control-label"> <strong>
			Reject <span style="color: red">*</span>:
	</strong>
	</label>
	<div class="col-sm-6">

		<select id="canReject" name="canReject" class="form-control">
			
			<option value="1" ${settings.canReject eq '1'?'selected':''}>Yes</option>
			<option value="0" ${settings.canReject eq '0'?'selected':''}>No</option>
			
		</select>
	</div>
</div>

<div class="form-group">
	<label for="isRejectReasonMandatory" class="col-sm-2 control-label"> <strong>
			Rejected Reason Mandatory <span style="color: red">*</span>:
	</strong>
	</label>
	<div class="col-sm-6">

		<select id="isRejectReasonMandatory" name="isRejectReasonMandatory" class="form-control">
			
			<option value="1" ${settings.isRejectReasonMandatory eq '1'?'selected':''}>Yes</option>
			<option value="0" ${settings.isRejectReasonMandatory eq '0'?'selected':''}>No</option>
			
		</select>
	</div>
</div>


<div class="form-group">
	<label for="viewText" class="col-sm-2 control-label"> <strong>
			View Text <span style="color: red">*</span>:
	</strong>
	</label>
	<div class="col-sm-6">
		<input id="viewText" name="viewText" value="${settings.viewText}"
			type="text" class="form-control">
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
				stage : "required",
				authRole : "required",
				btnText : "required",
				btnClass : "required",
				viewText : "required"

			},

			// Make sure the form is submitted to the destination defined
			// in the "action" attribute of the form when valid
			submitHandler : function(form) {
				form.submit();
			}
		});
	});


</script>