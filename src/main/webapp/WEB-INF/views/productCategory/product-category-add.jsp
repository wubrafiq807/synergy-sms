<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container">

	<div class="row">
		<div class="col-md-12 text-center" style="margin-top: 50px">
			<span style="font-size: 30px; font-weight: 700; color: #3e8f3e">ADD
				PRODUCT CATEGORY</span>
		</div>


		<div class="col-md-6 col-md-offset-3" style="margin-top: 50px">


			<form:form action="saveProductCategory" modelAttribute="category"
				method="POST">

				<form:hidden path="id" />




				<%-- 	<div class="form-group">
					<label for="inputCategoryName" class="col-sm-3 control-label">Category
						Name:</label>

					<div class="col-sm-9">
						<form:input path="name" cssClass="form-control" />
					</div>

				</div> --%>

				<div class="form-group">

					<label for="inputCategoryName">Category Name:</label>

					<form:input path="name" cssClass="form-control" />

				</div>







				<%-- 	<div class="form-group">
					<label for="inputCategoryRemarks" class="col-sm-3 control-label">Category
						Remarks:</label>

					<div class="col-sm-9">
						<form:textarea path="remarks" cssClass="form-control" rows="5"
							cols="30" />
					</div>
				</div> --%>


				<div class="form-group">

					<label for="inputCategoryRemarks">Category Remarks:</label>

					<form:textarea path="remarks" cssClass="form-control" rows="5"
						cols="30" />

				</div>






				<%-- 	<div class="form-group">

					<div style="margin-top: 130px">

						<label class="col-sm-3 control-label">Category Status:</label>
						<div class="col-sm-9">

							<form:radiobutton path="status" value="1" />
							Active
							<form:radiobutton path="status" value="0" />
							Inactive
						</div>
					</div>
				</div> --%>




				<div class="form-group">

					<label for="inputCategoryRemarks">Category Status:</label>

					<form:radiobutton path="status" value="1" />
					Active
					<form:radiobutton path="status" value="0" />
					Inactive

				</div>




			<!-- 	<div class="form-group">
					<label class="col-sm-3 control-label"></label>
					<div class="col-sm-9">
						<input type="submit" class="btn btn-success" name="btn"
							value="save" />
					</div>
				</div> -->


				<button type="submit" class="btn btn-success">Submit</button>

			</form:form>

		</div>
	</div>
</div>


