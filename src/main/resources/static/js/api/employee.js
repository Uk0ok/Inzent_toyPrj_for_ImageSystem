$(function() {
	$('#enrollEmployee').on('click', function() {
		let createEmployeeJson = {};

		let empCd = document.getElementById('empCd').value;
		let empName = document.getElementById('empName').value;
		let phoneNo = document.getElementById('phoneNo').value;
		let email = document.getElementById('email').value;
		let orgCd = document.getElementById('orgCd').value;

		createEmployeeJson = {
			'empCd'   : empCd,
			'phoneNo' : phoneNo,
			'email'   : email,
			'empName' : empName,
			'orgCd'   : orgCd
		}

		let success = function(result) {
			alert("등록이 완료되었습니다.");
			console.log('create success');
			history.go(0);
			location.reload();
		}
		let error = function(result) {
			alert("등록에 실패하였습니다..");
			console.log('create failed');
			history.go(0);
			location.reload();
		}

		AjaxUtil.ajax("/toy/employee/enroll", "POST", createEmployeeJson, success, error);
	})

});

$(function() {
		function deleteClick(btn) {
		let deleteEmployeeJson = {};
		
		var result = confirm("삭제하시겠습니까?");

		if (result == true) {
			var row = $(btn).closest('tr');  // 클릭한 버튼이 속한 행
            var empCd = row.find('td:eq(0)').text();    // 첫 번째 <td> 요소의 텍스트 값
            var empName = row.find('td:eq(1)').text();  // 두 번째 <td> 요소의 텍스트 값
            var phoneNo = row.find('td:eq(2)').text();  // 세 번째 <td> 요소의 텍스트 값
            var email = row.find('td:eq(3)').text();    // 네 번째 <td> 요소의 텍스트 값
            var orgCd = row.find('td:eq(4)').text();    // 다섯 번째 <td> 요소의 텍스트 값

			deleteEmployeeJson = {
				'empCd'   : empCd,
				'empName' : empName,
				'phoneNo' : phoneNo,
				'email'   : email,
				'orgCd'   : orgCd
			}
	
			let success = function(result) {
				alert("삭제가 완료되었습니다.");
				console.log('delete success');
				history.go(0);
				location.reload();
			}
			let error = function(result) { 
				alert("삭제에 실패하였습니다..");
				console.log('delete failed');
				history.go(0);
				location.reload();
			}
	
			AjaxUtil.ajax("/toy/employee/delete", "POST", deleteEmployeeJson, success, error);
		}
	}
	window.deleteClick = deleteClick;
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
