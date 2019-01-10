<div class="content-wrapper" style="margin: 0px;">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>Edit Requisition</h1>
		<ul class="top-links">
			<li><a class="btn btn-primary btn-xs"
				href="${pageContext.request.contextPath}/">
					<i class="fa fa-reorder"></i>Requisition List
			</a></li>

			<li><a class="btn btn-block btn-primary btn-xs"
				href="${pageContext.request.contextPath}/addProductRequisition"><i
					class="fa fa-plus"></i>Add new Requisition</a></li>

		</ul>
	</section>

	<!-- Main content -->
	<section class="content">

		<div class="row">
			<div class="col-xs-12">

				<!-- Horizontal Form -->
				<div class="box box-primary">
					<div class="box-header with-border">
						<h3 class="box-title center">Requisition Form</h3>
					</div>
					<!-- /.box-header -->
					<!-- form start -->
					<form action="${pageContext.request.contextPath}/saveRequisition"
						name="form" method="post" class="formValMenu form-horizontal"
						enctype="multipart/form-data" modelAttribute="command">
						<div class="box-body">

							<%@include file="../requisition/form_edit.jsp"%>

						</div>
						<!-- /.box-body -->
						<div class="box-footer">

							<c:if test="${!showStoreManSection}">
								<a href="${pageContext.request.contextPath}/"
									class="btn btn-danger"> <i class="fa fa-times"></i>Cancel
								</a>
								<button type="submit"
									class="btn btn-primary pull-right req-save-update-btn"
									name="submit_btn" value="save" id="submitBtn">
									<i class="fa fa-save"></i>update
								</button>
							</c:if>
							<c:if test="${showStoreManSection}">

								<button type="submit"
									class="${requisition.settings.btnClass} pull-right req-save-update-btn"
									name="submit_btn" value="save" id="submitBtn">
									<i class="fa fa-save"></i>${requisition.settings.btnText}
								</button>
								
							</c:if>

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