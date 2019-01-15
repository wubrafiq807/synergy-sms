<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="form-group">
	<label for="fromDate"  title="format: DD-MM-YYYY" class="col-sm-2 control-label"> <strong>
			From Date : </strong>
	</label>
	<div class="col-sm-6">
		<input id="startDate" name="startDate"  type="eu-date1" class="form-control" title="format: DD-MM-YYYY"/>
	</div>
</div>

<div class="form-group">
	<label for="toDate" class="col-sm-2 control-label"> <strong>
			To Date : </strong>
	</label>
	<div class="col-sm-6">
		<input id="endDate" name="endDate"  type="eu-date2"
			class="form-control" />
	</div>
</div>

<div class="form-group">
	<label for="status" class="col-sm-2 control-label"> <strong>Status <span style="color: red">*</span>
			: </strong>
	</label>
	<div class="col-sm-6">
		<select name="status" id="status" class="form-control">
			<option value="">Select</option>
			<option value="1" >Pending</option>
			<option value="3" >Closed</option>
			<option value="2" >Rejected</option>
		</select>
	</div>
</div>

<script>
	$(function() {
		var month = (new Date()).getMonth() + 1;
		var year = (new Date()).getFullYear();
		// US Format
		$('input[type=eu-date]').w2field('date', {
			format : 'yyyy-mm-dd'
		});

		$('input[type=eu-date1]').w2field('date', {
			format : 'yyyy-mm-dd',
			end : $('input[type=eu-date2]')
		});
		$('input[type=eu-date2]').w2field('date', {
			format : 'yyyy-mm-dd',
			start : $('input[type=eu-date1]')
		});

	});
	
	$(function() {

		$("form[name='form']")
				.on(
						'submit',
						function(e) {
							
							if ($('#status').val() == '') {
								w2alert('Please Select Status');
								return false;
							}
							
							if ($('#startDate').val() != '') {
								if ($('#endDate').val() == '') {
									w2alert('Please Select ToDate');
									return false;
								}
							}

						});

		
	});

</script>