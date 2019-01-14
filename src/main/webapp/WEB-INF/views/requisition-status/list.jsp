<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="content-wrapper" style="margin: 0px;">
	<c:forEach items="${requisitionStatus}" var="requisitionStatus">
		<input type="hidden" id="finalStatus"
			value="${requisitionStatus.reqStatus}" />

	</c:forEach>
	<section class="content-header">
		<h1>Product Requisition Status</h1>

	</section>

	<!-- Main content -->
	<section class="content">

		<div class="row">
			<div class="col-xs-12">
				<div class="box box-primary">
					<div class="box-body table-responsive no-padding">
						<!-- ----------Start table ----------- -->
						<table class="table table-striped display" id="table_id">
							<thead>
								<tr style="background-color: #428bca;">
									<th class="text-center">ID</th>
									<th class="text-center">Requisition Status</th>
									<th class="text-center">Action</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${requisitionStatus}" var="requisitionStatus"
									varStatus="tr">
									<tr class="row_no_${tr.count}">

										<td class="text-center" title="Show Category List">
											${tr.count}</td>

										<%-- 											${requisitionStatus.reqStatus=='1'?'Active':'Inactive'} --%>
										<td class="text-center"><span id="statusResult"></span></td>
										<td class="text-center"><c:choose>
												<c:when test="${requisitionStatus.reqStatus=='1'}">





													<label class="switch"> <input type="checkbox"
														checked onclick="changeActiveToInactive()"> <span
														class="slider round"></span>
													</label>




													<!-- 													<a -->
													<%-- 														href="inactiveRequisitionItem?id=${requisitionStatus.id}" --%>
													<!-- 														style="font-size: 30px;" class="text-success"><i -->
													<!-- 														class="fa fa-arrow-up" aria-hidden="true" -->
													<!-- 														title="Inactive Requisition"></i></a> -->


												</c:when>
												<c:otherwise>

													<%-- 													<a href="activeRequisitionItem?id=${requisitionStatus.id}" --%>
													<!-- 														style="font-size: 30px;" class="text-danger"><i -->
													<!-- 														class="fa fa-arrow-down" aria-hidden="true" -->
													<!-- 														title="Active Requisition"></i></a> -->




													<label class="switch"> <input type="checkbox"
														onclick="changeInActiveToActive()"> <span
														class="slider round"></span>
													</label>





												</c:otherwise>
											</c:choose></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>

						<script type="text/javascript">
							$(document).ready(function() {
								$('#table_id').DataTable({
									"aoColumnDefs" : [ {
										"sClass" : "my_class",
										"aTargets" : [ 0 ]
									} ]
								});
							});

							$(document)
									.ready(
											function() {
												$("#collapseBtn")
														.click(
																function() {
																	$(
																			'#collapseIcn')
																			.toggleClass(
																					'fa-plus fa-minus');
																	$(this)
																			.toggleClass(
																					'btn-info btn-warning');
																});
											});

							if (document.getElementById("finalStatus").value == 1) {
								document.getElementById('statusResult').innerHTML = "Active";

							} else {
								document.getElementById('statusResult').innerHTML = "Inactive";
							}

							function changeActiveToInactive() {

								var status = document
										.getElementById("finalStatus").value;
								if (status == 1) {
									console.log('changeActiveToInActive');
									document.getElementById("finalStatus").value = '0';

									changeProductStatus(document
											.getElementById("finalStatus").value);

								} else {
									console.log('changeInActiveToActive');
									document.getElementById("finalStatus").value = '1';

									changeProductStatus(document
											.getElementById("finalStatus").value);
								}

							}

							function changeInActiveToActive() {

								var status = document
										.getElementById("finalStatus").value;
								if (status == 0) {
									console.log('changeInActiveToActive');
									document.getElementById("finalStatus").value = '1';

									changeProductStatus(document
											.getElementById("finalStatus").value);
								} else {
									console.log('changeActiveToInActive');
									document.getElementById("finalStatus").value = '0';

									changeProductStatus(document
											.getElementById("finalStatus").value);
								}

							}

							function changeProductStatus(status) {
								$
										.ajax({
											url : "${pageContext.request.contextPath}"
													+ "/ajaxChangeProductRequisitionStatus",
											method : 'POST',
											data : {
												status : status
											},
											dataType : 'json',
											success : function(data) {
												if (data) {
													//console.log("success");
													if (document
															.getElementById("finalStatus").value == 1) {
														document
																.getElementById('statusResult').innerHTML = "Active";

													} else {
														document
																.getElementById('statusResult').innerHTML = "Inactive";
													}

												}

											}
										})
							}
						</script>
						<!-- ----------End Table ------------- -->
					</div>
					<!-- /.box-body -->
				</div>
				<!-- /.box -->
			</div>
		</div>

	</section>
	<!-- /.content -->
</div>