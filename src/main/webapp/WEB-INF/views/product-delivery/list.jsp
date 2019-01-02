<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="content-wrapper" style="margin: 0px;">
	<section class="content-header">
		<h1>Product Delivery List</h1>
		<ul class="top-links">
			<li><a class="btn btn-block btn-primary btn-xs"
				href="${pageContext.request.contextPath}/addDeliveryProduct"><i
					class="fa fa-plus"></i>Add Delivery Product</a></li>
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
									<th class="text-center">ID</th>
									<th class="text-center">Product Name</th>
									<th class="text-center">Delivery Quantity</th>

									<th class="text-center">Product Model</th>
									<th class="text-center">Product Category</th>
									<th class="text-center">Action</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${productDeliveryLists}" var="productDeliveryList"
									varStatus="tr">
									<tr class="row_no_${tr.count}">

										<td class="text-center" title="Show Category List">
											${tr.count}</td>
										<td class="text-center">${productDeliveryList.id}</td>
										<td class="text-center">${productDeliveryList.product.name}</td>
										<td class="text-center">${productDeliveryList.quantity}</td>
										<td class="text-center">${productDeliveryList.product.model.name}</td>
										<td class="text-center">${productDeliveryList.product.category.name}</td>
										<td class="text-center"><a
											href="updateDeliveryProduct?id=${productDeliveryList.id}"><i
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