<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="content-wrapper" style="margin: 0px;">
	<section class="content-header">
		<h1>Product List</h1>
		<ul class="top-links">
			<li><a class="btn btn-block btn-primary btn-xs"
				href="${pageContext.request.contextPath}/addProduct"><i
					class="fa fa-plus"></i>Add New Product</a></li>
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
									<th class="text-center">Name</th>
									<th class="text-center">Last Price</th>
									<th class="text-center">Weighted Average Price</th>									
									<th class="text-center">Description</th>
									<th class="text-center">Status</th>
									<!-- <th class="text-center">Model</th> -->
									<th class="text-center">Category</th>
									<th class="text-center">Remarks</th>
									<th class="text-center">Created By</th>
									<th class="text-center">Created Date</th>
									<th class="text-center">Modified By</th>
									<th class="text-center">Modified Date</th>

									<th class="text-center">Action</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${products}" var="product" varStatus="tr">
									<tr class="row_no_${tr.count}">

										<td class="text-center" title="Show Category List">
											${tr.count}</td>
										<td class="text-center">${product.id}</td>
										<td class="text-center">${product.name}</td>
										<td class="text-center">${product.price}</td>
										<td class="text-center">${product.weightedAvgPriceCurrency}</td>
										
										<td class="text-center">${product.description}</td>
										<td class="text-center">${product.status=='1'?'Active':'Inactive'}</td>
										<%-- <td class="text-center">${product.model.name}</td> --%>
										<td class="text-center">${product.category.name}</td>
										<td class="text-center">${product.remarks}</td>
										<td class="text-center">${product.createdBy.name}</td>
										<td class="text-center">${product.createdDate}</td>
										<td class="text-center">${product.modifiedBy.name}</td>
										<td class="text-center">${product.modifiedDate}</td>

										<!-- onclick="return confirm('Are you sure you want to delete this item?');" -->


										<td class="text-center"><a
											href="updateProduct?id=${product.id}"><i
												class="fa fa-pencil-square-o" aria-hidden="true"
												title="Edit"></i></a>| <a type="button" href="deleteProduct?id=${product.id}" onClick="deleteconfirm('${product.id}')" ><i
												style="color: red;" class="fa fa-trash" aria-hidden="true"
												title="Delete" id="deleteBtn" ></i></a></td>

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

							function deleteconfirm(id){
								event.preventDefault();
								swal({
									  title: "Are you sure?",
									  text: "Once deleted, you will not be able to recover this imaginary file!",
									  icon: "warning",
									  buttons: true,
									  dangerMode: true,
									})
									.then((willDelete) => {
									  if (willDelete) {
									    swal("success! Your Product Document has been deleted!", {								    	
									      icon: "success",
									    });
									    
									    window.location.href = 'deleteProduct?id='+id;									    
									  } else {
									    swal("Your Product Document is safe!");
									  }
									});
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