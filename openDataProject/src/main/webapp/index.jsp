<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- jQuery 라이브러리 -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>
	<h2>실시간 대기 오염 정보</h2>
	
	지역 : 
	<select id="location">
		<option>서울</option>
		<option>부산</option>
		<option>대전</option>
	</select>
	<button id="btn1">해당 지역 대기오염 정보</button>
	
	<table id="result1" border="1" align="center">
		<thead>
			<tr>
				<th>측정소명</th>
				<th>측정일시</th>
				<th>통합대기환경수치</th>
				<th>미세먼지농도</th>
				<th>일산화탄소농도</th>
				<th>이산화질소농도</th>
				<th>아황산가스농도</th>
				<th>오존농도</th>
			</tr>
		</thead>
		<tbody></tbody>
	</table>
	
	<script>
		$(function(){
			$("#btn1").click(function(){
				
				/* JSON형식의 응답데이터 받을 때 예시
				$.ajax({
					url:"air.do",
					data:{location:$("#location").val()},
					success:function(data){
						//console.log(data.response.body.items);
						
						const itemArr = data.response.body.items;
						
						let value = "";
						for(let i in itemArr){
							let item = itemArr[i];
							
							value += "<tr>"
									 +	  "<td>"+ item.stationName +"</td>"
									 +	  "<td>"+ item.dataTime +"</td>"
									 +	  "<td>"+ item.khaiValue +"</td>"
									 +	  "<td>"+ item.pm10Value +"</td>"
									 +	  "<td>"+ item.coValue +"</td>"
									 +	  "<td>"+ item.no2Value +"</td>"
									 +	  "<td>"+ item.so2Value +"</td>"
									 +	  "<td>"+ item.o3Value +"</td>"
									 + "</tr>"
						}
						$("#result1 tbody").html(value);
					},
					error:function(){
						console.log("대기오염 조회 ajax 요청 실패")
					}
				})
				*/
				
				$.ajax({
					url:"air.do",
					data:{location:$("#location").val()},
					success:function(data){
						//console.log(data)
						//jQuert에서의 find메소드 : 기준이 되는 요소의 하위 요소들 중 특정 요소를 찾을 때 사용(html. xml 다 사용 가능)
						//console.log($(data).find("item"))
						
						//xml형식의 응답데이터를 받았을 때
						//1. 응답데이터 안에 실제 데이터가 담겨있는 요소 선택
						let itemArr = ($(data).find("item"))
						
						//2. 반복문을 통해 실제 담긴 데이터에 접근해서 동적으로 요소 만들기
						let value =""
						itemArr.each(function(i, item){
							//console.log($(item).find("stationName").text())
							value += "<tr>"
							      +		"<td>" +$(item).find("stationName").text()+"</td>"
							      +		"<td>" +$(item).find("dataTime").text()+"</td>"
							      +		"<td>" +$(item).find("khaiValue").text()+"</td>"
							      +		"<td>" +$(item).find("pm10Value").text()+"</td>"
							      +		"<td>" +$(item).find("coValue").text()+"</td>"
							      +		"<td>" +$(item).find("no2Value").text()+"</td>"
							      +		"<td>" +$(item).find("so2Value").text()+"</td>"
							      +		"<td>" +$(item).find("o3Value").text()+"</td>"
							      +  "</tr>"
						})
						
						//3.동적으로 만들어낸 요소를 화면에 출력
						$("#result1 tbody").html(value);
					},
					error:function(){
						console.log("대기오염 조회 ajax 요청 실패")
					}
				})
			})
		})
	</script>
	
	<hr>
	
	<h2>중앙선거관리위원회 코드정보 - 정당정보</h2>
	<input type="button" value="조회하기" id="btn2">
	
	<div id="result2">

	</div>
	
	<script>
		$(function(){
			$("#btn2").click(function(){
				
				/*
				$.ajax({
					url:"jd.do",
					success:function(data){
						const itemArr = data.response.body.items.item;
						//console.log(itemArr);
						
						let value = "";
						for(let i in itemArr){
							let item = itemArr[i];
							
							value += "<tr>"
							      + 	"<td>" + item.sgId + "</td>"
							      + 	"<td>" + item.jdName + "</td>"
							      + 	"<td>" + item.pOrder + "</td>"
							      +  "</tr>"
						}
						
						$("#table1 tbody").html(value);
					},
					error:function(){
						console.log("정당코드 조회 실패")
					}
				})
				*/
				
				$.ajax({
					url:"jd.do",
					success:function(data){
						//console.log(data)
						//console.log($(data).find("item"))
						
						let $table = $("<table border='1'></table>");
						let $thead = $("<thead></thead>");
						let headTr = "<tr>"
								   +	"<th>선거ID</th>"
								   +	"<th>정당명</th>"
								   +	"<th>순서</th>"
								   + "</tr>";
						$thead.html(headTr);
						
						let $tbody = $("<tbody></tbody>");
						let bodyTr = "";
						
						$(data).find("item").each(function(i, jd){
							//console.log($(jd).find("jdName").text()); //<jdName>더불어민주당</jdName> 여기서 .text()까지 써야지 innerText를 가져옴
							
							bodyTr += "<tr>"
							        +	"<td>" +$(jd).find("sgId").text() + "</td>"
							        +	"<td>" +$(jd).find("jdName").text() + "</td>"
							        +	"<td>" +$(jd).find("pOrder").text() + "</td>"
							        + "</tr>";
						})
						
						$tbody.html(bodyTr);
						
						/*
						$table.append($thead, $tbody);
						$table.appendTo("#result2");
						*/
						$table.append($thead, $tbody).appendTo("#result2");
						
						/*
						==============================시험=====================================
							0. 만일 위의 반복문을 돌리지 않고 아래와 같이 진행한다면?
									
							$table.append($thead)
								  .append(data) //**응답 데이터 xml형식을 곧바로 table안에 넣는다 => 문제 발생!
								  
							문제점 : xml문서 안의 요소들을 바로 html화 시킬 수 없음 => 반복문 돌려가면서 각각의 요소에 접근해서 tr생성!
							
							1.서비스 키 값 있는지 확인
							2. =이나 &확인
							3. xml방식으로 리턴하는데 produces가 이상하거나
							4. @ResponseBody안쓰거나
							
							5. br.close(), urlConnecion.disconnect()로 반납을 안하거나
							6. 예를들어 정당코드를 조회하는데 미세먼지 url을 적어놨을 수도 있음
						*/
					},
					error:function(){
						console.log("정당코드 조회 실패");
					}
				})
			})
		})
	</script>
	
	
</body>
</html>