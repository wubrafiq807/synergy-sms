<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="content-wrapper" style="margin: 0px;">
	<section class="content-header">
		<h1>Product Stock List</h1>
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
									<th class="text-center">Stock Quantity</th>

									<th class="text-center">Product Model</th>
									<th class="text-center">Product Category</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${stocks}" var="stock" varStatus="tr">
									<tr class="row_no_${tr.count}">

										<td class="text-center" title="Show Category List">
											${tr.count}</td>
										<td class="text-center">${stock.id}</td>
										<td class="text-center">${stock.product.name}</td>
										<td class="text-center">${stock.quantity}</td>
										<td class="text-center">${stock.product.model.name}</td>
										<td class="text-center">${stock.product.category.name}</td>
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