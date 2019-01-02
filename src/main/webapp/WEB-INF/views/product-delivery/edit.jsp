<div class="content-wrapper" style="margin: 0px;">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>Edit Delivery Product</h1>
		<ul class="top-links">
			<li><a class="btn btn-primary btn-xs"
				href="${pageContext.request.contextPath}/listDeliveryProduct"> <i
					class="fa fa-reorder"></i>Product Delivery List
			</a></li>

			<li><a class="btn btn-block btn-primary btn-xs"
				href="${pageContext.request.contextPath}/addDeliveryProduct"><i
					class="fa fa-plus"></i>Add new Delivery Product</a></li>

		</ul>
	</section>

	<!-- Main content -->
	<section class="content">

		<div class="row">
			<div class="col-xs-12">

				<!-- Horizontal Form -->
				<div class="box box-primary">
					<div class="box-header with-border">
						<h3 class="box-title center">Edit Delivery Product Form</h3>
					</div>
					<!-- /.box-header -->
					<!-- form start -->
					<form action="${pageContext.request.contextPath}/saveDeliveryProduct" name="editProductDeliveryForm" id="form2"
						method="post" class="formValMenu form-horizontal" enctype="multipart/form-data" modelAttribute="command">
						<div class="box-body">							 
							<%@include file="../product-delivery/editForm.jsp"%>
						</div>
						<!-- /.box-body -->
						<div class="box-footer">
							<a href="${pageContext.request.contextPath}/listProduct" class="btn btn-danger">
								<i class="fa fa-times"></i>Cancel
							</a>
							<button type="submit" class="btn btn-primary pull-right req-save-update-btn" name="submit_btn" value="update" id="updateBtn">
								<i class="fa fa-save"></i>Update
							</button>
						</div>
						<!-- /.box-footer -->
					</form>
				</div>
				<!-- /.box -->
			</div>
		</div>

	</section>
	<!-- /.content -->
</div>