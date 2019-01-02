<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="content-wrapper" style="margin: 0px;">
	<section class="content-header">
		<h1>Configuration List</h1>
		<ul class="top-links">
			<li><a class="btn btn-block btn-primary btn-xs"
				href="${pageContext.request.contextPath}/addConfiguraation"><i
					class="fa fa-plus"></i>Add New Configuration</a></li>
		</ul>
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
									<th class="text-center">Sl NO.</th>
									<th class="text-center">Stage</th>
									<th class="text-center">Button Text</th>
									<th class="text-center">Button Class</th>
									<th class="text-center">View Text</th>
									<th class="text-center">Reject To</th>
									<th class="text-center">Auth Role</th>
									<th class="text-center">Action</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${settings}" var="setting" varStatus="tr">
									<tr class="row_no_${tr.count}">

										<td class="text-center" title="Show Configuration List">
											${tr.count}</td>
										<td class="text-center">${setting.stage}</td>
										<td class="text-center">${setting.btnText}</td>


										<td class="text-center"><span class="${setting.btnClass}">
												${setting.btnClass} </span></td>

										<td class="text-center">${setting.viewText}</td>
										<td class="text-center">${setting.rejectTo.stage}</td>
										<td class="text-center">${setting.authRole}</td>
										<td class="text-center"><a
											href="updateConfiguration?id=${setting.id}"><i
												class="fa fa-pencil-square-o" aria-hidden="true"
												title="Edit"></i></a>|</td>
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