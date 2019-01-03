<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style>
.clsDatePicker {
	z-index: 100000;
}
</style>

<div class="content-wrapper" style="margin: 0px;">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>Requisition Detail</h1>
		<ul class="top-links">

			<c:if test="${showAppRjctBtn}">
			
			<c:if test="${!showStoreManSection}">
			<li>
					<button title="${requisition.settings.btnText}"
						class="${requisition.settings.btnClass}" data-toggle="modal"
						data-target="#myModalApp">
						<i class="${requisition.settings.btnClass}"></i>
						${requisition.settings.btnText}
					</button>
				</li>
			</c:if>
			
			<c:if test="${showStoreManSection}">
			<li>
			<a class="${requisition.settings.btnClass}"
					href="${pageContext.request.contextPath}/editReq/${requisition.id}"><i
						class="fa fa-fw fa-edit"></i>${requisition.settings.btnText}</a>
				</li>
			</c:if>
				

				<c:if test="${requisition.settings.canReject eq '1' }">
					<li>
						<button title="${requisition.settings.btnText}"
							class="btn btn-danger btn-xs" onclick="ConfirmDelete()">
							<i class="fa fa-clock"></i> Reject
						</button>
					</li>
				</c:if>
			</c:if>

			<li><a class="btn btn-block btn-primary btn-xs"
				id="onlyCommentModalSubmitButton"
				href="${pageContext.request.contextPath}/addProductRequisition"><i
					class="fa fa-plus"></i>Add New Requisition</a></li>

			<c:if test="${showInitiatorSection}">
				<li>
					<button title="${requisition.settings.btnText}"
						class="${requisition.settings.btnClass}" data-toggle="modal"
						data-target="#myModal">
						<i class="${requisition.settings.btnClass}"></i>
						${requisition.settings.btnText}
					</button>
				</li>
				<li><a class="btn btn-block btn-warning btn-xs"
					href="${pageContext.request.contextPath}/editReq/${requisition.id}"><i
						class="fa fa-fw fa-edit"></i>Edit</a></li>



				<li><a class="btn btn-block btn-danger btn-xs"
					href="${pageContext.request.contextPath}/deleteReq/${requisition.id}"
					id="${requisition.id}"
					onclick="return confirm('Are you sure you want to delete this record?');"><i
						class="fa fa-trash"></i>Delete</a></li>


			</c:if>

		</ul>
	</section>
	<!-- 	modal section -->
	<section>
		<!-- Modal -->
		<div id="myModal" class="modal fade" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Submit Requisition</h4>
					</div>
					<div class="modal-body">
						<form action="${pageContext.request.contextPath}/approveReq"
							method="post">
							<div class="form-group">
								<label for="email">Comments</label>
								<textarea rows="" name="remarks" class="form-control" cols=""></textarea>
							</div>
							<input type="hidden" name="req_id" value="${requisition.id}">
							<button type="submit"
								onclick="return confirm('Are you sure you want to Submit?');"
								class="btn btn-info">Submit</button>
						</form>

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
		
		<div id="myModalApp" class="modal fade" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">${requisition.settings.btnText} Requisition</h4>
					</div>
					<div class="modal-body">
						<form action="${pageContext.request.contextPath}/approveReq"
							method="post">
							<div class="form-group">
								<label for="email">Comments</label>
								<textarea rows="" name="remarks" class="form-control" cols=""></textarea>
							</div>
							<input type="hidden" name="req_id" value="${requisition.id}">
							<button type="submit"
								onclick="return confirm('Are you sure you want to ${requisition.settings.btnText}?');"
								class="btn btn-info">${requisition.settings.btnText}</button>
						</form>

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
		<div id="myModalRej" class="modal fade" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Reject Requisition</h4>
					</div>
					<div class="modal-body">
						<form action="${pageContext.request.contextPath}/rejectReq"
							method="post" name="reject_form" id="reject_form">
							<div class="form-group">
								<label for="email">Rejection Reason</label>
								<textarea rows="" name="rejectionReason" id="rejectionReason" class="form-control" cols=""></textarea>
							<span style="color: red" id="rejectionReason_error"></span>
							</div>
							<input type="hidden" name="req_id" value="${requisition.id}">
							<button type="submit"
								
								class="btn btn-info">Reject</button>
						</form>

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
	</section>
	<style>
.com_status {
	width: 74px;
}
</style>
	<!-- Main content -->
	<section class="content">

		<div class="row">
			<div class="col-xs-12">

				<div class="box box-primary">
					<div class="box-header">
						<h3 class="box-title">Requisition Detail View</h3>
					</div>
					<!-- /.box-header -->
					<div class="box-body no-padding">

						<div class="box-header">
							<button data-toggle="collapse"
								class="box-title btn btn-info btn-sm" data-target="#job_history">Requisition
								History</button>
							<button data-toggle="collapse"
								class="box-title btn btn-success btn-sm" data-target="#job_docs">Requisition
								Items</button>
						</div>
						<c:if test="${not empty message}">
							<div class="col-sm-12" id="deletesuccess">
								<div class="alert alert-success">
									<strong>Success!</strong> ${message}

								</div>
							</div>
						</c:if>


						<div id="job_history" class="collapse">
							<%-- <c:set var="jobHistoryList"
								value="${requisition.requisitionHistory}" /> --%>

							<c:if test="${!empty reqHistoryList}">


								<div class="col-sm-12">
									<div class="table-responsive">
										<table class="table table-striped">
											<c:forEach var="history" items="${reqHistoryList}">

												<tr>


													<c:if test="${history.isRejected=='0'}">
														<td>Action:
															${history.settings.btnText}${history.settings.btnText=='Submit'?'ted':history.settings.btnText=='Approve'?'d':'ed'}</td>
														<td style="color: green">Status: Done</td>
													</c:if>

													<c:if test="${history.isRejected=='1'}">
														<td>Action: Rejected</td>
														<td style="color: red">Status: Rejected</td>
													</c:if>

													<td><c:if test="${history.isRejected=='1'}">												
																Reject Reason: ${history.rejectionReason}
															</c:if></td>
													<c:if test="${history.isRejected=='0'}">
														<td>${history.settings.btnText}${history.settings.btnText=='Submit'?'ted':history.settings.btnText=='Approve'?'d':'ed'}
															By: ${history.createdBy.name}</td>
														<td>${history.settings.btnText}${history.settings.btnText=='Submit'?'ted':history.settings.btnText=='Approve'?'d':'ed'}
															Date: <fmt:formatDate pattern="dd-MM-yyyy hh:mm:ss a"
																value="${history.createdDate}" />
													</c:if>
													<c:if test="${history.isRejected=='1'}">
														<td style="color: red">Rejected By:
															${history.createdBy.name}</td>
														<td style="color: red">Rejected Date: <fmt:formatDate
																pattern="dd-MM-yyyy hh:mm:ss a"
																value="${history.createdDate}" />
													</c:if>

													</td>
													<td>Comments : ${history.remarks}</td>
												</tr>

											</c:forEach>
										</table>
									</div>

								</div>

							</c:if>
						</div>
						<div id="job_docs" class="collapse">
							<%-- <c:set var="jobDocuments" value="${requisition.jobDocuments}" /> --%>
							<table class="table table-striped">
								<thead>
									<tr>
										<th>SL No.</th>
										<th>Product</th>
										<th>Price</th>
										<th>Quantity</th>
										<th style="text-align: center;">Sub Total Price</th>

									</tr>
								</thead>
								<tbody>
									<c:forEach var="doc" items="${ItemList}" varStatus="trdoc">
										<tr>
											<td>${trdoc.index+1}</td>
											<td>${doc.product.name}</td>
											<td>${doc.product.weightedAvgPriceCurrency}</td>
											<td>${doc.quantity}</td>
											<td style="text-align: center;">${doc.totalPriceCurrency}</td>

										</tr>

									</c:forEach>
								</tbody>
								<tfoot>
									<tr style="border: 2px solid; background: #d4e5d8;">

										<td colspan="4"
											style="text-align: right; border-right: 2px solid; font-size: 18px; color: #03213a;">Total
											Price</td>
										<td
											style="text-align: center; font-size: 18px; color: #03213a;">${totalPriceMain}</td>

									</tr>
								</tfoot>

							</table>
						</div>



						<table class="table table-striped">
							<tr>
								<th colspan="2"><h4>Employee Information</h4></th>
							</tr>
							<c:if test="${!empty requisition.employee.name}">
								<tr>
									<th>Employee name</th>
									<td>${requisition.employee.name}</td>
								</tr>
							</c:if>
							<c:if test="${!empty requisition.employee.lxnId}">
								<tr>
									<th>Employee ID</th>
									<td>${requisition.employee.lxnId}</td>
								</tr>
							</c:if>

							<c:if test="${!empty requisition.employee.designation}">
								<tr>
									<th>Designation</th>
									<td>${requisition.employee.designation}</td>
								</tr>
							</c:if>

							<c:if test="${!empty requisition.employee.department}">
								<tr>
									<th>Department</th>
									<td>${requisition.employee.department}</td>
								</tr>
							</c:if>
							<c:if test="${!empty requisition.employee.company}">
								<tr>
									<th>Company</th>
									<td>${requisition.employee.company.name}</td>
								</tr>
							</c:if>

							<tr>
								<th colspan="2"><h4>Requisition Information</h4></th>
							</tr>

							<c:if test="${!empty requisition.id}">
								<tr>
									<th>Req No</th>
									<td>${requisition.id}</td>
								</tr>
							</c:if>


							<c:if test="${!empty requisition.purpose}">
								<tr>
									<th>Req Purpose</th>
									<td>${requisition.purpose}</td>
								</tr>
							</c:if>


							<c:if test="${!empty requisition.createdDate}">
								<tr>
									<th>Apply Date</th>
									<td><fmt:formatDate pattern="dd-MM-yyyy hh:mm:ss a"
											value="${requisition.createdDate}" /></td>
								</tr>
							</c:if>



							<c:if test="${!empty requisition.status}">
								<tr>
									<th>Status</th>
									<td><c:if test="${requisition.status == '1'}">
											<span class="btn btn-success btn-xs com_status">
												Active </span>
										</c:if> <c:if test="${requisition.status == '2'}">
											<span class="btn btn-warning btn-xs com_status">
												Rejected</span>
										</c:if> <c:if test="${requisition.status == '3'}">
											<span class="btn btn-info btn-xs com_status"> Approved
											</span>
										</c:if></td>
								</tr>
							</c:if>
							<c:if test="${!empty requisition.settings.viewText}">
								<tr>
									<th>Current Work Flow</th>
									<td><span class="${requisition.settings.btnClass} ">
											${requisition.settings.viewText} </span></td>
								</tr>
							</c:if>


							<c:if test="${!empty requisition.createdBy.name}">
								<tr>
									<th>Created By</th>
									<td>${requisition.createdBy.name}</td>
								</tr>
							</c:if>

							<c:if test="${!empty requisition.modifiedBy.name}">
								<tr>
									<th>Modified By</th>
									<td>${requisition.modifiedBy.name}</td>
								</tr>
							</c:if>

							<c:if test="${!empty requisition.modifiedDate}">
								<tr>
									<th>Modified Date</th>
									<td><fmt:formatDate pattern="dd-MM-yyyy hh:mm:ss a"
											value="${requisition.modifiedDate}" /></td>
								</tr>
							</c:if>




						</table>
					</div>
					<!-- /.box-body -->
				</div>
				<!-- /.box -->
			</div>
		</div>
	</section>
	<!-- /.content -->
</div>
<script>
	$(document).ready(function() {
		$('#thead').hide();
		$('#reject_form').submit(function() {
			   var status = ${requisition.settings.isRejectReasonMandatory};
			   var reason=$('#rejectionReason').val().trim();
			   
			   if(status==1 &&reason.length<=0){
				   $('#rejectionReason_error').text('Please Enter Rejected Reason....');
				   return false;
			   }
			   
			   else{
				   $('#rejectionReason_error').text('');
			   return true; 
			   }
			  });
	});
	
	
	
	function ConfirmDelete()
	{
	  var x = confirm("Are you sure you want to Reject?");
	  if (x)
		  $('#myModalRej').modal('show');
	  else
	    return false;
	}
</script>
