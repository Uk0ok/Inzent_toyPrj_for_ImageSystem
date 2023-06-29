$(function() {
	console.log('ajax create deposit post');

	$('#submitButton').on('click', function() {
		let createJson = new FormData();
		let depositDto = {};

		let empCode = document.getElementById('employeeSelectBox').value;
		let custName = document.getElementById('custName').value;
		let rrnNo = document.getElementById('rrnNo').value;
		let description = document.getElementById('description').value;
		let cclassId = document.getElementById('cclassId').value;
		let userClass = document.getElementById('userClass').value;
		let eclassId = document.getElementById('eclassId').value;

		depositDto.empCode = empCode;
		depositDto.custName = custName;
		depositDto.rrnNo = rrnNo;
		depositDto.description = description;
		depositDto.cclassId = cclassId;
		depositDto.userClass = userClass;
		depositDto.eclassId = eclassId;

		let depositDtoBlob = new Blob([JSON.stringify(depositDto)], {type: "application/json"});
		createJson.append("depositDto", depositDtoBlob);
		// DTO와 MultipartFile을 같이 보내려면 DTO를 Blob선언 후 따로 파싱해서 type 지정해서 보내주어야 함

		if (document.getElementById('transactionFiles') !== null && document.getElementById('transactionFiles') !== undefined && document.getElementById('transactionFiles') !== "") {
			for (let i = 0; i < document.getElementById('transactionFiles').files.length; i++) {
				createJson.append('transactionFiles', document.getElementById('transactionFiles').files[i]);
			}
			console.log('append to transactionFiles complete!');
		}
		if (document.getElementById('depositBalanceFiles') !== null && document.getElementById('depositBalanceFiles') !== undefined && document.getElementById('depositBalanceFiles') !== "") {
			for (let i = 0; i < document.getElementById('depositBalanceFiles').files.length; i++) {
				createJson.append('depositBalanceFiles', document.getElementById('depositBalanceFiles').files[i]);
			}
			console.log('append to depositBalanceFiles complete!');
		}
		if (document.getElementById('attorneyFiles') !== null && document.getElementById('attorneyFiles') !== undefined && document.getElementById('attorneyFiles') !== "") {
			for (let i = 0; i < document.getElementById('attorneyFiles').files.length; i++) {
				createJson.append('attorneyFiles', document.getElementById('attorneyFiles').files[i]);
			}
			console.log('append to attorneyFiles complete!');
		}
		if (document.getElementById('automaticFiles') !== null && document.getElementById('automaticFiles') !== undefined && document.getElementById('automaticFiles') !== "") {
			for (let i = 0; i < document.getElementById('automaticFiles').files.length; i++) {
				createJson.append('automaticFiles', document.getElementById('automaticFiles').files[i]);
			}
			console.log('append to automaticFiles complete!');
		}
		if (document.getElementById('reissuanceFiles') !== null && document.getElementById('reissuanceFiles') !== undefined && document.getElementById('reissuanceFiles') !== "") {
			for (let i = 0; i < document.getElementById('reissuanceFiles').files.length; i++) {
				createJson.append('reissuanceFiles', document.getElementById('reissuanceFiles').files[i]);
			}
			console.log('append to reissuanceFiles complete!');
		}
		if (document.getElementById('electronicFiles') !== null && document.getElementById('electronicFiles') !== undefined && document.getElementById('electronicFiles') !== "") {
			for (let i = 0; i < document.getElementById('electronicFiles').files.length; i++) {
				createJson.append('electronicFiles', document.getElementById('electronicFiles').files[i]);
			}
			console.log('append to electronicFiles complete!');
		}
		if (document.getElementById('consentFiles') !== null && document.getElementById('consentFiles') !== undefined && document.getElementById('consentFiles') !== "") {
			for (let i = 0; i < document.getElementById('consentFiles').files.length; i++) {
				createJson.append('consentFiles', document.getElementById('consentFiles').files[i]);
			}
			console.log('append to consentFiles complete!');
		}
		if (document.getElementById('cmsFiles') !== null && document.getElementById('cmsFiles') !== undefined && document.getElementById('cmsFiles') !== "") {
			for (let i = 0; i < document.getElementById('cmsFiles').files.length; i++) {
				createJson.append('cmsFiles', document.getElementById('cmsFiles').files[i]);
			}
			console.log('append to cmsFiles complete!');
		}
		if (document.getElementById('judgingFiles') !== null && document.getElementById('judgingFiles') !== undefined && document.getElementById('judgingFiles') !== "") {
			for (let i = 0; i < document.getElementById('judgingFiles').files.length; i++) {
				createJson.append('judgingFiles', document.getElementById('judgingFiles').files[i]);
			}
			console.log('append to judgingFiles complete!');
		}
		if (document.getElementById('exclusionFiles') !== null && document.getElementById('exclusionFiles') !== undefined && document.getElementById('exclusionFiles') !== "") {
			for (let i = 0; i < document.getElementById('exclusionFiles').files.length; i++) {
				createJson.append('exclusionFiles', document.getElementById('exclusionFiles').files[i]);
			}
			console.log('append to exclusionFiles complete!');
		}
		
		let success = function(result) {
			alert("등록이 완료되었습니다.");
			history.go(0);
			console.log('create success');
		}
		let error = function(result) {
			console.log('create failed');
		}
		for (let key of createJson.keys()) {
			console.log(key, " : ", createJson.get(key));
		}

		AjaxUtil.ajaxFile("/toy/deposit", "POST", createJson, success, error);
	})
});

let AjaxUtil = {
	ajaxFile : function($url, $type, $data, $success, $error) {
		$.ajax({
			enctype: 'multipart/form-data',
			url : $url,
			type : $type,
			async : true,
			beforeSend : function() {
				$('.wrap-loading').css('display', 'flex');
			},
			complete : function() {
				$('.wrap-loading').css('display', 'none');
			},
			data : $data,
			contentType : false,
			processData : false,
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
