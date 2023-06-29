$(function() {
	$(document).ready(function() {
		// URL의 파라미터 제거
		// history.replaceState({}, null, location.pathname);
	});
	$(document).on('click', '.sendToykeyToModal', function() {
		let listToyKey = $(this).attr('data-listToyKey');
		$('#imageListModal').find('#exampleModalLabel').text(listToyKey);

		$.ajax({
			url: '/toy/inquiry/inquiryLoan',
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify({ 'listToyKey' : listToyKey }),
			dataType : 'html',
			success: function(response) {
			  	$('#imageListBody').empty(); // 초기화
			  	$('#imageListBody').append(response); // imageModal append

			  	// 스크립트 코드를 실행하기 위해 <script> 요소 생성
			  	var script = document.createElement('script');
			
			  	// <script> 요소에 스크립트 코드 설정
			  	script.innerHTML = `
					$('#imageListTable').on('click', '#elementIdView', function() {
						let elementId = $(this).closest("tr").find("td:first-child").attr("data-elementId");
						let extension = $(this).closest("tr").find("td[data-extension]").attr("data-extension");
						let urlAddress = "";

						switch (extension) {
							case 'jpg' :
								urlAddress = "/toy/viewJpg";
								break;
							case 'jpeg' :
								urlAddress = "/toy/viewJpg";
								break;
							case 'png' :
								urlAddress = "/toy/viewPng";
								break;
							case 'pdf' :
								urlAddress = "/toy/viewPdf";
								break;
							case 'txt' :
								urlAddress = "/toy/viewText";
								break;
							default :
								break;
						}

						viewJson = {
							'elementIdView' : elementId,
						};

						$.ajax({
							url: urlAddress,
							method: 'GET',
							contentType: 'application/json',
							data: viewJson,
							dataType : 'html',
							success: function(response) {
								console.log('success');

								switch (extension) {
									case 'jpg' :
										window.open("/toy/viewJpg?elementIdView=" + elementId, "_blank");
										break;
									case 'jpeg' :
										window.open("/toy/viewJpg?elementIdView=" + elementId, "_blank");
										break;
									case 'png' :
										window.open("/toy/viewPng?elementIdView=" + elementId, "_blank");
										break;
									case 'pdf' :
										window.open("/toy/viewPdf?elementIdView=" + elementId, "_blank");
										break;
									case 'txt' :
										window.open("/toy/viewText?elementIdView=" + elementId, "_blank");
										break;
								}
							},
							error: function(error) {
								console.log('error');
							}
						});
					});

					$('#imageListTable').on('click', '#deleteImage', function() {
						var result = confirm("삭제하시겠습니까?");

						if (result == true) {
							let elementId = $(this).closest("tr").find("td:first-child").attr("data-elementId");
	
							$.ajax({
								url: '/toy/delete',
								method: 'POST',
								contentType: 'application/json',
								data: JSON.stringify({ 'elementIdDel' : elementId }),
								dataType : 'html',
								success: function(response) {
									console.log('success');
									$('#test').load(location.reload());
								},
								error: function(error) {
									console.log('error');
									$('#test').load(location.reload());
								}
							});
						} else {
							alert("취소되었습니다.");
						}
			    	});

					$('#imageListTable').on('click', '#downloadImage', function() {
						let downloadJson = {};
						var result = confirm("다운로드 받으시겠습니까?");

						if (result == true) {
							let elementId = $(this).closest("tr").find("td:first-child").attr("data-elementId");
	
							downloadJson = {
								'elementIdDown' : elementId,
							};

							$.ajax({
								url: '/toy/download',
								method: 'GET',
								contentType: 'application/json',
								data: downloadJson,
								dataType : 'html',
								success: function(response) {
									window.location = "/toy/download?elementIdDown=" + elementId;
								},
								error: function(error) {
									console.log('error : ' + error);
									console.log('error:', error.message);
									console.log('error:', error.stack);

								}
							});
						} else {
							alert("취소되었습니다.");
						}
			    	});
			  	`;
				// <script> 요소를 <body> 태그에 추가
				document.body.appendChild(script);
			  	console.log('success');
			},
			error: function(error, request) {
				console.log('error');
			}
		  });
	});

	$('#searchButton').on('click', function() {
		let loanJson = {};

		let toyKey = document.getElementById('toyKey').value;
		let custNm = document.getElementById('custNm').value;
		let rrnNo = document.getElementById('rrnNo').value;
		
		loanJson = {
			'toyKey' : toyKey,
			'custNm' : custNm,
			'rrnNo'  : rrnNo
		}

		let success = function(result) {
			$("#test").replaceWith(result.responseText);
			
			console.log('search success');
		}
		let error = function(result) {
			console.log('search failed');
		}

		AjaxUtil.ajax("/toy/inquiry/inquiryLoan", "POST", loanJson, success, error, true);
	})

	$(document).on('click', 'ul.pagination li.page-item', function() {
		let loanJson = {};

		let toyKey = document.getElementById('toyKey').value;
		let custNm = document.getElementById('custNm').value;
		let rrnNo = document.getElementById('rrnNo').value;
		let page = document.getElementById('page').value;
		
		loanJson = {
			'toyKey' : toyKey,
			'custNm' : custNm,
			'rrnNo'  : rrnNo
		}

		let success = function(result) {
			$("#test").replaceWith(result.responseText);

			console.log('search success');
		}
		let error = function(result) {
			console.log('search failed');
		}
		// let test = $('form').serializeObject();

		AjaxUtil.ajax("/toy/inquiry/inquiryLoan?page=" + page, "POST", loanJson, success, error, true);
		
	});
});

let AjaxUtil = {
	ajax : function($url, $type, $data, $success, $error, $async) {	
		let $json = ($data) ? JSON.stringify($data) : "";
		let $asyncOpt = ($async) ? true : false;
		
		$.ajax({
			headers : { 
		        'Accept' : 'application/json',
		        'Content-Type' : 'application/json' 
			},
			url : $url,
			type : $type,
			async : $asyncOpt,
			beforeSend : function() {
				$('.wrap-loading').css('display', 'flex');
			},
			complete : function() {
				$('.wrap-loading').css('display', 'none');
			},
			dataType : 'json',
			data : $json,
			contentType : 'application/json;charset=UTF-8',
			mimeType : 'application/json',
			success : function(response, textStatus) {
				if (textStatus=="success")
					$success(response);
				else
					$error(response);
			},
			error : function(response) {
				if (response.status=="200")
					$success(response);
				else
					$error(response);
			}
		});
	}
}