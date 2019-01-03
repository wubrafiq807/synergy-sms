<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<script src="resource/tabs/jquery-ui.js"></script>
<link href="resource/tabs/jquery-ui.css" rel="stylesheet">

<div class="content-wrapper" style="margin: 0px;">
	<section class="content-header">
		<h1>Concern Dashboard</h1>
	</section>
	<style>
.com_status {
	width: 74px;
}
</style>

	<script>
		$(function() {
			$("#tabs").tabs();
		});
	</script>

	<!-- Main content -->
	<section class="content">
		<div class="row">
			<div class="col-xs-12">

				<div id="tabs">
					<ul>
						<li><a href="#tabs-pending-jobs">Pending Requisitions Request</a></li>
						<li><a href="#tabs-approved-jobs">Approved/Forwarded/Submitted Requisitions by Me</a></li>
						<li><a href="#tabs-rejected-jobs">Rejected Requisitions by Me</a></li>
					</ul>


					<div id="tabs-pending-jobs">
						<div class="box box-primary">
							<div class="box-body table-responsive no-padding">
								<!-- ----------Start table ----------- -->
								<table class="table table-striped display" id="table_id_1">
									<thead>
										<tr style="background-color: #428bca;">
											<th class="text-center">Sl NO.</th>											
											<th class="text-center">Employee</th>
											<th class="text-center">Purpose</th>
											<th class="text-center">Status</th>
											<th class="text-center" style="width: 270px;">Work Flow</th>
											<th class="text-center">Created Date</th>
											<th class="text-center">Action</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${pendingReqList}" var="myReq" varStatus="tr">
											<tr class="row_no_${tr.count}">
												<td class="text-center" title="Show Job Advance"><a
													href="${pageContext.request.contextPath}/showReq/${myReq.id}">${tr.count}</a>
												</td>
												
												<td class="text-center">${myReq.employee.name}</td>
												<td class="text-center">${myReq.purpose}</td>
												<td class="text-center"><c:if
														test="${myReq.status == '1'}">
														<span class="btn btn-success btn-xs com_status">
															Active </span>
													</c:if> <c:if test="${myReq.status == '2'}">
														<span class="btn btn-warning btn-xs com_status">
															Rejected</span>
													</c:if> <c:if test="${myReq.status == '3'}">
														<span class="btn btn-info btn-xs com_status">
															Approved </span>
													</c:if></td>
												<td class="text-center"><span style="width: 241px"
													class="${myReq.settings.btnClass} ">
														${myReq.settings.viewText} </span></td>
																
												<td class="text-center"> <fmt:formatDate pattern="dd-MM-yyyy hh:mm:ss a" value="${myReq.createdDate}" /></td>
		
												<td class="text-center">
												<a class="btn btn-info btn-xs" title="Show Job Advance"
													href="${pageContext.request.contextPath}/showReq/${myReq.id}"><i class="fa fa-eye" aria-hidden="true"> </i>Show</a>
													<%-- <a class="btn btn-success btn-xs" title="Show Job Advance History"
													href="${pageContext.request.contextPath}/jobAdvanceReport/${pendingJob.id}"><i class="fa fa-eye" aria-hidden="true"> </i>History</a> --%>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<!-- ----------End Table ------------- -->
							</div>
							<!-- /.box-body -->
						</div>
						<!-- /.box -->
					</div>
<div id="tabs-approved-jobs">
						<div class="box box-primary">
							<div class="box-body table-responsive no-padding">
								<!-- ----------Start table ----------- -->
								<table class="table table-striped display" id="table_id_1">
									<thead>
										<tr style="background-color: #428bca;">
											<th class="text-center">Sl NO.</th>											
											<th class="text-center">Employee</th>
											<th class="text-center">Purpose</th>
											<th class="text-center">Status</th>
											<th class="text-center" style="width: 270px;">Work Flow</th>
											<th class="text-center">Created Date</th>
											
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${reqConcenApprovvedList}" var="myReq" varStatus="tr">
											<tr class="row_no_${tr.count}">
												<td class="text-center" title="Show Job Advance">
												</td>
												
												<td class="text-center">${myReq.requisition.employee.name}</td>
												<td class="text-center">${myReq.requisition.purpose}</td>
												<td class="text-center"><c:if
														test="${myReq.requisition.status == '1'}">
														<span class="btn btn-success btn-xs com_status">
															Active </span>
													</c:if> <c:if test="${myReq.requisition.status == '2'}">
														<span class="btn btn-warning btn-xs com_status">
															Rejected</span>
													</c:if> <c:if test="${myReq.requisition.status == '3'}">
														<span class="btn btn-info btn-xs com_status">
															Approved </span>
													</c:if></td>
												<td class="text-center"><span style="width: 241px"
													class="${myReq.settings.btnClass} ">
														${myReq.settings.viewText} </span></td>
																
												<td class="text-center"> <fmt:formatDate pattern="dd-MM-yyyy hh:mm:ss a" value="${myReq.createdDate}" /></td>
		
												
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<!-- ----------End Table ------------- -->
							</div>
							<!-- /.box-body -->
						</div>
						<!-- /.box -->
					</div>
					<div id="tabs-rejected-jobs">
						<div class="box box-primary">
							<div class="box-body table-responsive no-padding">
								<!-- ----------Start table ----------- -->
								<table class="table table-striped display" id="table_id_1">
									<thead>
										<tr style="background-color: #428bca;">
											<th class="text-center">Sl NO.</th>											
											<th class="text-center">Employee</th>
											<th class="text-center">Purpose</th>
											<th class="text-center">Status</th>
											<th class="text-center" style="width: 270px;">Work Flow</th>
											<th class="text-center">Created Date</th>
											
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${reqConcenRejectedList}" var="myReq" varStatus="tr">
											<tr class="row_no_${tr.count}">
												<td class="text-center" title="Show Job Advance">
												</td>
												
												<td class="text-center">${myReq.requisition.employee.name}</td>
												<td class="text-center">${myReq.requisition.purpose}</td>
												<td class="text-center"><c:if
														test="${myReq.requisition.status == '1'}">
														<span class="btn btn-success btn-xs com_status">
															Active </span>
													</c:if> <c:if test="${myReq.requisition.status == '2'}">
														<span class="btn btn-warning btn-xs com_status">
															Rejected</span>
													</c:if> <c:if test="${myReq.requisition.status == '3'}">
														<span class="btn btn-info btn-xs com_status">
															Approved </span>
													</c:if></td>
												<td class="text-center"><span style="width: 241px"
													class="${myReq.settings.btnClass} ">
														${myReq.settings.viewText} </span></td>
																
												<td class="text-center"> <fmt:formatDate pattern="dd-MM-yyyy hh:mm:ss a" value="${myReq.createdDate}" /></td>
		
												<td class="text-center">
												
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<!-- ----------End Table ------------- -->
							</div>
							<!-- /.box-body -->
						</div>
						<!-- /.box -->
					</div>
				</div>



			</div>
		</div>

		<script type="text/javascript">
			$(document).ready(function() {
				$('#table_id_1').DataTable({
					"aoColumnDefs" : [ {
						"sClass" : "my_class",
						"aTargets" : [ 0 ]
					} ]
				});
			});

			$(document).ready(function() {
				$("#collapseBtn").click(function() {
					$('#collapseIcn').toggleClass('fa-plus fa-minus');
					$(this).toggleClass('btn-info btn-warning');
				});
			});
		</script>

	</section>
	<!-- /.content -->
</div>