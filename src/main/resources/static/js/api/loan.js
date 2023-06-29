$(function() {
	console.log('ajax create loan post');

	$('#submitButton').on('click', function() {
		let createJson = new FormData();
		let loanDto = {};

		let empCode = document.getElementById('employeeSelectBox').value;
		let custName = document.getElementById('custName').value;
		let rrnNo = document.getElementById('rrnNo').value;
		let description = document.getElementById('description').value;
		let cclassId = document.getElementById('cclassId').value;
		let userClass = document.getElementById('userClass').value;
		let eclassId = document.getElementById('eclassId').value;

		loanDto.empCode = empCode;
		loanDto.custName = custName;
		loanDto.rrnNo = rrnNo;
		loanDto.description = description;
		loanDto.cclassId = cclassId;
		loanDto.userClass = userClass;
		loanDto.eclassId = eclassId;

		let loanDtoBlob = new Blob([JSON.stringify(loanDto)], {type: "application/json"});
		createJson.append("loanDto", loanDtoBlob);
		// DTO와 MultipartFile을 같이 보내려면 DTO를 Blob선언 후 따로 파싱해서 type 지정해서 보내주어야 함

		if (document.getElementById('attachmentFiles') !== null && document.getElementById('attachmentFiles') !== undefined && document.getElementById('attachmentFiles') !== "") {
			for (let i = 0; i < document.getElementById('attachmentFiles').files.length; i++) {
				createJson.append('attachmentFiles', document.getElementById('attachmentFiles').files[i]);
			}
			console.log('append to attachmentFiles complete!');
		}
		if (document.getElementById('deliberationFiles') !== null && document.getElementById('deliberationFiles') !== undefined && document.getElementById('deliberationFiles') !== "") {
			for (let i = 0; i < document.getElementById('deliberationFiles').files.length; i++) {
				createJson.append('deliberationFiles', document.getElementById('deliberationFiles').files[i]);
			}
			console.log('append to deliberationFiles complete!');
		}
		if (document.getElementById('exemptDeliberationFiles') !== null && document.getElementById('exemptDeliberationFiles') !== undefined && document.getElementById('exemptDeliberationFiles') !== "") {
			for (let i = 0; i < document.getElementById('exemptDeliberationFiles').files.length; i++) {
				createJson.append('exemptDeliberationFiles', document.getElementById('exemptDeliberationFiles').files[i]);
			}
			console.log('append to exemptDeliberationFiles complete!');
		}
		if (document.getElementById('appraisalFiles') !== null && document.getElementById('appraisalFiles') !== undefined && document.getElementById('appraisalFiles') !== "") {
			for (let i = 0; i < document.getElementById('appraisalFiles').files.length; i++) {
				createJson.append('appraisalFiles', document.getElementById('appraisalFiles').files[i]);
			}
			console.log('append to appraisalFiles complete!');
		}
		if (document.getElementById('approvalFiles') !== null && document.getElementById('approvalFiles') !== undefined && document.getElementById('approvalFiles') !== "") {
			for (let i = 0; i < document.getElementById('approvalFiles').files.length; i++) {
				createJson.append('approvalFiles', document.getElementById('approvalFiles').files[i]);
			}
			console.log('append to approvalFiles complete!');
		}
		if (document.getElementById('changeOfConditionsFiles') !== null && document.getElementById('changeOfConditionsFiles') !== undefined && document.getElementById('changeOfConditionsFiles') !== "") {
			for (let i = 0; i < document.getElementById('changeOfConditionsFiles').files.length; i++) {
				createJson.append('changeOfConditionsFiles', document.getElementById('changeOfConditionsFiles').files[i]);
			}
			console.log('append to changeOfConditionsFiles complete!');
		}
		if (document.getElementById('agreementFiles') !== null && document.getElementById('agreementFiles') !== undefined && document.getElementById('agreementFiles') !== "") {
			for (let i = 0; i < document.getElementById('agreementFiles').files.length; i++) {
				createJson.append('agreementFiles', document.getElementById('agreementFiles').files[i]);
			}
			console.log('append to agreementFiles complete!');
		}
		if (document.getElementById('creditFiles') !== null && document.getElementById('creditFiles') !== undefined && document.getElementById('creditFiles') !== "") {
			for (let i = 0; i < document.getElementById('creditFiles').files.length; i++) {
				createJson.append('creditFiles', document.getElementById('creditFiles').files[i]);
			}
			console.log('append to creditFiles complete!');
		}
		if (document.getElementById('limitFiles') !== null && document.getElementById('limitFiles') !== undefined && document.getElementById('limitFiles') !== "") {
			for (let i = 0; i < document.getElementById('limitFiles').files.length; i++) {
				createJson.append('limitFiles', document.getElementById('limitFiles').files[i]);
			}
			console.log('append to limitFiles complete!');
		}
		if (document.getElementById('repaymentFiles') !== null && document.getElementById('repaymentFiles') !== undefined && document.getElementById('repaymentFiles') !== "") {
			for (let i = 0; i < document.getElementById('repaymentFiles').files.length; i++) {
				createJson.append('repaymentFiles', document.getElementById('repaymentFiles').files[i]);
			}
			console.log('append to repaymentFiles complete!');
		}
		if (document.getElementById('aptFiles') !== null && document.getElementById('aptFiles') !== undefined && document.getElementById('aptFiles') !== "") {
			for (let i = 0; i < document.getElementById('aptFiles').files.length; i++) {
				createJson.append('aptFiles', document.getElementById('aptFiles').files[i]);
			}
			console.log('append to aptFiles complete!');
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

		AjaxUtil.ajaxFile("/toy/loan", "POST", createJson, success, error);
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
