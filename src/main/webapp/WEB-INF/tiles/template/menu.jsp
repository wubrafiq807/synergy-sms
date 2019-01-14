<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*,java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<!-- Title and other stuffs -->
<title></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="author" content="">

<!-- bootstrap & fontawesome -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/assets/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/css/AdminLTE.min.css" />

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/assets/font-awesome/4.5.0/css/font-awesome.min.css" />

<!-- <link rel="stylesheet" -->
<!-- 	href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" -->
<!-- 	integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" -->
<!-- 	crossorigin="anonymous"> -->



<!-- text fonts -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/assets/css/fonts.googleapis.com.css" />
<!-- ace styles -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/assets/css/ace.min.css"
	class="ace-main-stylesheet" id="main-ace-style" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/assets/css/ace-skins.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/assets/css/ace-rtl.min.css" />
<!-- ace settings handler -->
<script
	src="${pageContext.request.contextPath}/resource/assets/js/ace-extra.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resource/bufferGrid/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resource/bufferGrid/w2ui.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resource/bufferGrid/w2ui.min.css" />

<script src="${pageContext.request.contextPath}/resource/js/common.js"></script>

<link
	href="${pageContext.request.contextPath}/resource/dataTable/jquery.dataTables.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resource/dataTable/buttons.dataTables.min.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/resource/dataTable/jquery-1.12.3.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/dataTable/jquery.dataTables.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/dataTable/dataTables.buttons.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/dataTable/buttons.flash.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/dataTable/jszip.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/dataTable/pdfmake.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/dataTable/vfs_fonts.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/dataTable/buttons.html5.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/dataTable/buttons.print.min.js"></script>


<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/js/bootstrap-datepicker.min.js"></script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/css/bootstrap-datepicker.css"
	rel="stylesheet" type="text/css">

<link
	href="${pageContext.request.contextPath}/resource/select2/select2.min.css"
	rel="stylesheet" type="text/css">
<script
	src="${pageContext.request.contextPath}/resource/select2/select2.min.js"></script>

<link
	href="${pageContext.request.contextPath}/resource/w2ui/w2ui-1.5.rc1.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resource/w2ui/w2ui-1.5.rc1.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/resource/w2ui/w2ui-1.5.rc1.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/w2ui/w2ui-1.5.rc1.js"></script>
<script src="https://cdn.ckeditor.com/4.10.0/standard-all/ckeditor.js"></script>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/css/style.css" />



<style>
.switch {
	position: relative;
	display: inline-block;
	width: 60px;
	height: 34px;
}

.switch input {
	opacity: 0;
	width: 0;
	height: 0;
}

.slider {
	position: absolute;
	cursor: pointer;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background-color: red;
	-webkit-transition: .4s;
	transition: .4s;
}

.slider:before {
	position: absolute;
	content: "";
	height: 26px;
	width: 26px;
	left: 4px;
	bottom: 4px;
	background-color: white;
	-webkit-transition: .4s;
	transition: .4s;
}

input:checked+.slider {
	background-color: green;
}

input:focus+.slider {
	
}

input:checked+.slider:before {
	-webkit-transform: translateX(26px);
	-ms-transform: translateX(26px);
	transform: translateX(26px);
}

/* Rounded sliders */
.slider.round {
	border-radius: 34px;
}

.slider.round:before {
	border-radius: 50%;
}
</style>
<body>

	<div id="sidebar" class="sidebar responsive ace-save-state">
		<script type="text/javascript">
			try {
				ace.settings.loadState('sidebar')
			} catch (e) {
			}
		</script>

		<div class="sidebar-shortcuts" id="sidebar-shortcuts">
			<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
				<button class="btn btn-success">
					<i class="ace-icon fa fa-signal"></i>
				</button>

				<button class="btn btn-info">
					<i class="ace-icon fa fa-pencil"></i>
				</button>

				<button class="btn btn-warning">
					<i class="ace-icon fa fa-users"></i>
				</button>

				<button class="btn btn-danger">
					<i class="ace-icon fa fa-cogs"></i>
				</button>
			</div>

			<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
				<span class="btn btn-success"></span> <span class="btn btn-info"></span>

				<span class="btn btn-warning"></span> <span class="btn btn-danger"></span>
			</div>
		</div>
		<!-- /.sidebar-shortcuts -->

		<ul class="nav nav-list">
			<li class=""><a href="${pageContext.request.contextPath}/">
					<i class="menu-icon fa fa-tachometer"></i> <span class="menu-text">
						Dashboard </span>
			</a> <b class="arrow"></b></li>

			<%-- 			<sec:authorize access="hasRole('ROLE_ADMIN')"> --%>
			<!-- 				<li class=""><a href="javascript:void(0)" -->
			<!-- 					class="dropdown-toggle"> <i class="menu-icon fa fa-life-ring"></i> -->
			<!-- 						<span class="menu-text"> Model </span> <b -->
			<!-- 						class="arrow fa fa-angle-down"></b> -->
			<!-- 				</a> <b class="arrow"></b> -->

			<!-- 					<ul class="submenu"> -->


			<!-- 						<li class=""><a -->
			<%-- 							href="${pageContext.request.contextPath}/addProductModel"> <i --%>
			<!-- 								class="menu-icon fa fa-plus purple"></i> Add MOdel -->
			<!-- 						</a> <b class="arrow"></b></li> -->

			<!-- 						<li class=""><a -->
			<%-- 							href="${pageContext.request.contextPath}/listProductModel"> <i --%>
			<!-- 								class="menu-icon fa fa-list"></i> Model List -->
			<!-- 						</a> <b class="arrow"></b></li> -->
			<!-- 					</ul></li> -->

			<%-- 			</sec:authorize> --%>


			<sec:authorize access="hasRole('ROLE_ADMIN')">
				<li class=""><a href="javascript:void(0)"
					class="dropdown-toggle"> <i class="menu-icon fa fa-bar-chart"></i>
						<span class="menu-text"> Category </span> <b
						class="arrow fa fa-angle-down"></b>
				</a> <b class="arrow"></b>

					<ul class="submenu">


						<li class=""><a
							href="${pageContext.request.contextPath}/addProductCategory">
								<i class="menu-icon fa fa-plus purple"></i> Add Category
						</a> <b class="arrow"></b></li>

						<li class=""><a
							href="${pageContext.request.contextPath}/listProductCategory">
								<i class="menu-icon fa fa-list"></i>Category List
						</a> <b class="arrow"></b></li>


					</ul></li>

			</sec:authorize>

			<sec:authorize access="hasRole('ROLE_ADMIN')">
				<li class=""><a href="javascript:void(0)"
					class="dropdown-toggle"> <i class="menu-icon fa fa-pencil"></i>
						<span class="menu-text"> Product </span> <b
						class="arrow fa fa-angle-down"></b>
				</a> <b class="arrow"></b>

					<ul class="submenu">


						<li class=""><a
							href="${pageContext.request.contextPath}/addProduct"> <i
								class="menu-icon fa fa-plus purple"></i> Add Product
						</a><b class="arrow"></b></li>

						<li class=""><a
							href="${pageContext.request.contextPath}/listProduct"> <i
								class="menu-icon fa fa-list"></i>Product List
						</a> <b class="arrow"></b></li>

						<li class=""><a
							href="${pageContext.request.contextPath}/listStock"> <i
								class="menu-icon fa fa-list"></i>Product Stock List
						</a> <b class="arrow"></b></li>

						<li class=""><a
							href="${pageContext.request.contextPath}/addReciveProduct"> <i
								class="menu-icon fa fa-plus purple"></i>Add Receive Product
						</a> <b class="arrow"></b></li>

						<li class=""><a
							href="${pageContext.request.contextPath}/listReceiveProduct">
								<i class="menu-icon fa fa-list"></i>Product Receive List
						</a> <b class="arrow"></b></li>

						<li class=""><a
							href="${pageContext.request.contextPath}/addDeliveryProduct">
								<i class="menu-icon fa fa-plus purple"></i>Add Delivery Product
						</a> <b class="arrow"></b></li>

						<li class=""><a
							href="${pageContext.request.contextPath}/listDeliveryProduct">
								<i class="menu-icon fa fa-list"></i>Product Delivery List
						</a> <b class="arrow"></b></li>

					</ul></li>

			</sec:authorize>


			<sec:authorize access="hasRole('ROLE_ADMIN')">
				<li class=""><a href="javascript:void(0)"
					class="dropdown-toggle"> <i
						class="menu-icon fa fa-gear fa-spin"></i> <span class="menu-text">
							Settings </span> <b class="arrow fa fa-angle-down"></b>
				</a> <b class="arrow"></b>

					<ul class="submenu">


						<li class=""><a href="javascript:void(0)"
							class="dropdown-toggle"> <i
								class="menu-icon fa fa-cogs green"></i> Configuration <b
								class="arrow fa fa-angle-down"></b>
						</a> <b class="arrow"></b>

							<ul class="submenu">
								<li class=""><a
									href="${pageContext.request.contextPath}/addConfiguraation">
										<i class="menu-icon fa fa-plus purple"></i> Add Configuration
								</a> <b class="arrow"></b></li>

								<li class=""><a
									href="${pageContext.request.contextPath}/listConfiguraation">
										<i class="menu-icon fa fa-list"></i> Configuration List
								</a> <b class="arrow"></b></li>
							</ul></li>
					</ul></li>

			</sec:authorize>


			<sec:authorize
				access="hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_SUPERVISOR') or hasRole('ROLE_STORE_MANAGER')">
				<li class=""><a href="javascript:void(0)"
					class="dropdown-toggle"> <i
						class="menu-icon fa fa-chain-broken"></i> <span class="menu-text">
							Requisition</span> <b class="arrow fa fa-angle-down"></b>
				</a> <b class="arrow"></b>

					<ul class="submenu">

						<li class=""><a
							href="${pageContext.request.contextPath}/addProductRequisition">
								<i class="menu-icon fa fa-plus purple"></i> Add Requisition
						</a> <b class="arrow"></b></li>



					</ul></li>

			</sec:authorize>




			<sec:authorize access="hasRole('ROLE_ADMIN')">
				<li class=""><a
					href="${pageContext.request.contextPath}/viewRequisitionStatus">
						<i class="menu-icon fa fa-bell-o"></i> <span class="menu-text">
							Requisition Status </span>
				</a> <b class="arrow"></b></li>

			</sec:authorize>

<sec:authorize access="hasRole('ROLE_ADMIN')">
				<li class=""><a href="javascript:void(0)"
					class="dropdown-toggle"> <i
						class="menu-icon fa fa-chain-broken"></i> <span class="menu-text">
							Report</span> <b class="arrow fa fa-angle-down"></b>
				</a> <b class="arrow"></b>

					<ul class="submenu">

						<li class=""><a
							href="${pageContext.request.contextPath}/smsReqForm">
								<i class="menu-icon fa fa-plus purple"></i> Requisitions
						</a> <b class="arrow"></b></li>


						
					</ul></li>

			</sec:authorize>



		</ul>
		<!-- /.nav-list -->

		<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
			<i id="sidebar-toggle-icon"
				class="ace-icon fa fa-angle-double-left ace-save-state"
				data-icon1="ace-icon fa fa-angle-double-left"
				data-icon2="ace-icon fa fa-angle-double-right"></i>
		</div>
	</div>




</body>
</html>

