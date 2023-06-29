$(function() {
	console.log('ajax create common post');

	$('#submitButton').on('click', function() {
		let createJson = new FormData();
		let commonDto = {};

		let empCode = document.getElementById('employeeSelectBox').value;
		let custName = document.getElementById('custName').value;
		let rrnNo = document.getElementById('rrnNo').value;
		let description = document.getElementById('description').value;
		let cclassId = document.getElementById('cclassId').value;
		let userClass = document.getElementById('userClass').value;
		let eclassId = document.getElementById('eclassId').value;

		commonDto.empCode = empCode;
		commonDto.custName = custName;
		commonDto.rrnNo = rrnNo;
		commonDto.description = description;
		commonDto.cclassId = cclassId;
		commonDto.userClass = userClass;
		commonDto.eclassId = eclassId;

		let commonDtoBlob = new Blob([JSON.stringify(commonDto)], {type: "application/json"});
		createJson.append("commonDto", commonDtoBlob);
		// DTO와 MultipartFile을 같이 보내려면 DTO를 Blob선언 후 따로 파싱해서 type 지정해서 보내주어야 함

		if (document.getElementById('slipFiles') !== null && document.getElementById('slipFiles') !== undefined && document.getElementById('slipFiles') !== "") {
			for (let i = 0; i < document.getElementById('slipFiles').files.length; i++) {
				createJson.append('slipFiles', document.getElementById('slipFiles').files[i]);
			}
			console.log('append to slipFiles complete!');
		}
		if (document.getElementById('sealFiles') !== null && document.getElementById('sealFiles') !== undefined && document.getElementById('sealFiles') !== "") {
			for (let i = 0; i < document.getElementById('sealFiles').files.length; i++) {
				createJson.append('sealFiles', document.getElementById('sealFiles').files[i]);
			}
			console.log('append to sealFiles complete!');
		}
		if (document.getElementById('copyFiles') !== null && document.getElementById('copyFiles') !== undefined && document.getElementById('copyFiles') !== "") {
			for (let i = 0; i < document.getElementById('copyFiles').files.length; i++) {
				createJson.append('copyFiles', document.getElementById('copyFiles').files[i]);
			}
			console.log('append to copyFiles complete!');
		}
		if (document.getElementById('familyFiles') !== null && document.getElementById('familyFiles') !== undefined && document.getElementById('familyFiles') !== "") {
			for (let i = 0; i < document.getElementById('familyFiles').files.length; i++) {
				createJson.append('familyFiles', document.getElementById('familyFiles').files[i]);
			}
			console.log('append to familyFiles complete!');
		}
		if (document.getElementById('nonresidentFiles') !== null && document.getElementById('nonresidentFiles') !== undefined && document.getElementById('nonresidentFiles') !== "") {
			for (let i = 0; i < document.getElementById('nonresidentFiles').files.length; i++) {
				createJson.append('nonresidentFiles', document.getElementById('nonresidentFiles').files[i]);
			}
			console.log('append to nonresidentFiles complete!');
		}
		if (document.getElementById('entrepreneurFiles') !== null && document.getElementById('entrepreneurFiles') !== undefined && document.getElementById('entrepreneurFiles') !== "") {
			for (let i = 0; i < document.getElementById('entrepreneurFiles').files.length; i++) {
				createJson.append('entrepreneurFiles', document.getElementById('entrepreneurFiles').files[i]);
			}
			console.log('append to entrepreneurFiles complete!');
		}
		if (document.getElementById('groupFiles') !== null && document.getElementById('groupFiles') !== undefined && document.getElementById('groupFiles') !== "") {
			for (let i = 0; i < document.getElementById('groupFiles').files.length; i++) {
				createJson.append('groupFiles', document.getElementById('groupFiles').files[i]);
			}
			console.log('append to groupFiles complete!');
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

		AjaxUtil.ajaxFile("/toy/common", "POST", createJson, success, error);
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
