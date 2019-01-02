<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	
<script src="resource/tabs/jquery-ui.js"></script> 
<link href="resource/tabs/jquery-ui.css" rel="stylesheet">

<div class="content-wrapper" style="margin: 0px;">
	<section class="content-header">
		<h1>Administrator Dashboard</h1>
	</section>
	<style>
.com_status {
	width: 74px;
}

.white-box {
	background: #fff;
	padding: 25px;
	margin-bottom: 30px;
}

.white-box .box-title {
	margin: 0 0 12px;
	font-weight: 500;
	text-transform: uppercase;
	font-size: 16px;
}

.analytics-info .list-inline {
	margin-bottom: 0;
}

.list-inline {
	padding-left: 0;
	margin-left: -5px;
	list-style: none;
}

.list-inline>li {
	display: inline-block;
	padding-left: 5px;
	padding-right: 5px;
}

.text-success {
	color: #7ace4c;
}

.text-right {
	text-align: right;
}

.list-inline>li {
	display: inherit;
	padding-left: 5px;
	padding-right: 5px;
}
</style>

	<script>
		$(function() {
			$("#tabs").tabs();
		});
	</script>

	<!-- Main content -->
	<section class="content">
		<div class="" style="background-color: #E4E6E9;">
			<div class="col-xs-12">
				<div class="col-lg-4 col-sm-6 col-xs-12">
					<div class="white-box analytics-info">
						<h3 class="box-title blue">Approved Requisitions (${fiscalYearName})</h3>
						<ul class="list-inline two-part">
							<li style="align-content: center;">
								<h2 class="center success" style="color: green;">${empty totalApprovedReq ? 0: totalApprovedReq}</h2>
							</li>
						</ul>
					</div>
				</div>
				<div class="col-lg-4 col-sm-6 col-xs-12">
					<div class="white-box analytics-info">
						<h3 class="box-title blue">Pending Requisitions</h3>
						<ul class="list-inline two-part">
							<li style="align-content: center;">
								<h2 class="center warning" style="color: orange;">${empty totalPendingReq ? 0: totalPendingReq}</h2>
							</li>
						</ul>
					</div>
				</div>
				<div class="col-lg-4 col-sm-6 col-xs-12">
					<div class="white-box analytics-info">
						<h3 class="box-title blue">Rejected Requisitions</h3>
						<ul class="list-inline two-part">
							<li style="align-content: center;">
								<h2 class="center danger" style="color: red;">${empty totalRejectedJobReq ? 0: totalRejectedJobReq}</h2>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-xs-12">

				<div id="tabs">
					<ul>
						<li><a href="#tabs-pending-jobs">Pending Requisitions</a></li>
						<li><a href="#tabs-approved-jobs">Approved Requisitions</a></li>
						<li><a href="#tabs-rejected-jobs">Rejected Requisitions</a></li>
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
										<c:forEach items="${pendingReqList}" var="pendingReq" varStatus="tr">
											<tr class="row_no_${tr.count}">
												<td class="text-center" title="Show Job Advance"><a
													href="${pageContext.request.contextPath}/showReq/${pendingReq.id}">${tr.count}</a>
												</td>
												
												<td class="text-center">${pendingReq.employee.name}</td>
												<td class="text-center">${pendingReq.purpose}</td>
												<td class="text-center"><c:if
														test="${pendingReq.status == '1'}">
														<span class="btn btn-success btn-xs com_status">
															Active </span>
													</c:if> <c:if test="${pendingReq.status == '2'}">
														<span class="btn btn-warning btn-xs com_status">
															Rejected</span>
													</c:if> <c:if test="${pendingReq.status == '3'}">
														<span class="btn btn-info btn-xs com_status">
															Approved </span>
													</c:if></td>
												<td class="text-center"><span style="width: 241px"
													class="${pendingReq.settings.btnClass} ">
														${pendingReq.settings.viewText} </span></td>
																
												<td class="text-center"> <fmt:formatDate pattern="dd-MM-yyyy hh:mm:ss a" value="${pendingReq.createdDate}" /></td>
		
												<td class="text-center">
												<a class="btn btn-info btn-xs" title="Show Job Advance"
													href="${pageContext.request.contextPath}/showReq/${pendingReq.id}"><i class="fa fa-eye" aria-hidden="true"> </i>Show</a>
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
											<th class="text-center">Approved Date</th>
											<th class="text-center">Action</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${approvedReqList}" var="approvedReq" varStatus="tr">
											<tr class="row_no_${tr.count}">
												<td class="text-center" title="Show Job Advance"><a
													href="${pageContext.request.contextPath}/showReq/${approvedReq.id}">${tr.count}</a>
												</td>
												
												<td class="text-center">${approvedReq.employee.name}</td>
												<td class="text-center">${approvedReq.purpose}</td>
												<td class="text-center"><c:if
														test="${pendingReq.status == '1'}">
														<span class="btn btn-success btn-xs com_status">
															Active </span>
													</c:if> <c:if test="${pendingReq.status == '2'}">
														<span class="btn btn-warning btn-xs com_status">
															Rejected</span>
													</c:if> <c:if test="${pendingReq.status == '3'}">
														<span class="btn btn-info btn-xs com_status">
															Approved </span>
													</c:if></td>
												<td class="text-center"><span style="width: 241px"
													class="${approvedReq.settings.btnClass} ">
														${approvedReq.settings.viewText} </span></td>
																
												<td class="text-center"> <fmt:formatDate pattern="dd-MM-yyyy hh:mm:ss a" value="${approvedReq.modifiedDate}" /></td>
		
												<td class="text-center">
												<a class="btn btn-info btn-xs" title="Show Requisition"
													href="${pageContext.request.contextPath}/showReq/${modifiedDate.id}"><i class="fa fa-eye" aria-hidden="true"> </i>Show</a>
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
											<th class="text-center">Rejected Date</th>
											<th class="text-center">Rejected By</th>
											<th class="text-center">Action</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${rejectedReqList}" var="rejectedReq" varStatus="tr">
											<tr class="row_no_${tr.count}">
												<td class="text-center" title="Show Req"><a
													href="${pageContext.request.contextPath}/showReq/${rejectedReq.id}">${tr.count}</a>
												</td>
												
												<td class="text-center">${rejectedReq.employee.name}</td>
												<td class="text-center">${rejectedReq.purpose}</td>
												<td class="text-center"><c:if
														test="${rejectedReq.status == '1'}">
														<span class="btn btn-success btn-xs com_status">
															Active </span>
													</c:if> <c:if test="${rejectedReq.status == '2'}">
														<span class="btn btn-warning btn-xs com_status">
															Rejected</span>
													</c:if> <c:if test="${rejectedReq.status == '3'}">
														<span class="btn btn-info btn-xs com_status">
															Approved </span>
													</c:if></td>
												<td class="text-center"><span style="width: 241px"
													class="${rejectedReq.settings.btnClass} ">
														${rejectedReq.settings.viewText} </span></td>
																
												<td class="text-center"> <fmt:formatDate pattern="dd-MM-yyyy hh:mm:ss a" value="${rejectedReq.modifiedDate}" /></td>
												<td class="text-center">${rejectedReq.modifiedBy.name}</td>
		
												<td class="text-center">
												<a class="btn btn-info btn-xs" title="Show Requisition"
													href="${pageContext.request.contextPath}/showReq/${modifiedDate.id}"><i class="fa fa-eye" aria-hidden="true"> </i>Show</a>
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
				$('#table_id_2').DataTable({
					"aoColumnDefs" : [ {
						"sClass" : "my_class",
						"aTargets" : [ 0 ]
					} ]
				});
			});
			
			$(document).ready(function() {
				$('#table_id_3').DataTable({
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