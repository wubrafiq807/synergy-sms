<div class="footer">
	<div class="footer-inner">
		<div class="footer-content">

			<span class="bigger-120"> <span class="blue bolder">Synergy
					Inc.</span> &copy; <script> document.write(new Date().getFullYear()); </script>
			</span> &nbsp; &nbsp; <span> Design &amp; Developed By : <span
				class="bigger-120"> <a href="javascript:void(0)"
					style="text-decoration: none; font-style: italic;">
						nazdaqTechnologies Inc. </a>
			</span>
			</span>
		</div>
	</div>
</div>
<!-- basic scripts -->
<!--[if !IE]> -->
<%-- <script src="${pageContext.request.contextPath}/resources/assets/js/jquery-2.1.4.min.js"></script> --%>
<script type="text/javascript">
	if('ontouchstart' in document.documentElement) document.write("<script src='${pageContext.request.contextPath}/resource/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
</script>
<script
	src="${pageContext.request.contextPath}/resource/assets/js/bootstrap.min.js"></script>
<!-- ace scripts -->
<script
	src="${pageContext.request.contextPath}/resource/assets/js/ace-elements.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/assets/js/ace.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resource/js/additional-methods.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/js/jquery.validate.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resource/js/sweetalert.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resource/js/resizeImage.js"></script>




<script type="text/javascript">
function archiveFunction(url, msg) {
	event.preventDefault(); // prevent form submit
	swal({
		  title: "Are you sure?",
		  text: msg,
		  icon: "warning",
		  buttons: ["No", "Yes"],	
		  closeOnClickOutside : false,
		  dangerMode: true,
		}).then((willDelete) => {
		  if (willDelete) {
		  
		    window.location.href = url;
		  } 
		});
	}
	
	
	
	// JS code for change requisition status
	
	
	
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
													console.log("success");
													if (document
															.getElementById("finalStatus").value == 1) {
														
// 														console.log("active");
														
// 														document.getElementById('span-active').innerHTML = "Active";
// 														document.getElementById('span-inactive').innerHTML = "";

														 $('.red_li').show();
														  $('.green_li').hide();

													} else {
														
// 														console.log("Inactive");													
														
// 														document.getElementById('span-active').innerHTML = "";
// 														document.getElementById('span-inactive').innerHTML = "Inactive";

														$('.red_li').hide();
														  $('.green_li').show();
														
													}

												}

											}
										})
							}
	
	
	
							$( document ).ready(function() {
							  var status= $('#finalStatus').val();
							  if(status=='1'){
								  $('.red_li').show();
								  $('.green_li').hide();
							  }else{
								  $('.red_li').hide();
								  $('.green_li').show();
							  }
							});
	
</script>