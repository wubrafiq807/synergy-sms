<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container">
	<div class="row">
		<div class="col-md-6 col-md-offset-3" style="margin-top: 100px">
			<a href="addProductCategory" class="btn btn-success"
				style="margin-bottom: 20px">Add Product Category</a>
			<table class="table table-bordered">
				<tr>
					<th>#</th>
					<th>Category Name</th>
					<th>Remarks</th>
					<th>Status</th>
					<th>Action</th>
				</tr>
				<%
					int i = 1;
				%>
				<c:forEach var="category" items="${categories}">

					<c:url var="updateLink" value="/updateProductCategory">
						<c:param name="categoryId" value="${category.id}" />
					</c:url>

					<tr>
						<td><%=i++%></td>
						<td>${category.name}</td>
						<td>${category.remarks}</td>
						<td>${category.status eq 1 ? 'active' : 'inactive' }</td>
						<td><a href="${updateLink}" class="btn btn-info">Update</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</div>














