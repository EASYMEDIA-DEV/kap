var Maps = function(){
	//커스텀 오버레이 닫기 함수를 위해 변수 설정
	var overlay;
	//커스텀 오버레이 콘텐츠 변수
	var content;
	//다중 마커 및 커스텀오버레이 관련 노출 시켜줄 map 객체 변수 설정
	var map;
	//다중 커스텀 오버레이에서 마커클릭시 기존 노출된 오버레이를 미노출로 변경을 위한 다중 커스텀 오버레이 객체 담는 배열
	var arrOverlay = new Array();

	return{
		//검색한 주소를 바탕으로 맵 호출 함수
		//address = 주소, title = 타이틀, url = 관련리크, address2 = 지번주소
		addressSearch : function(address, title, url, address2)
		{
			//파라미터 기본값 설정 하기
			title = (typeof title !== "undefined") ? title : "";
			url = (typeof url !== "undefined") ? url : "";
			address2 = (typeof address2 !== "undefined") ? address2 : "";

			// 주소-좌표 변환 객체를 생성합니다
			var geocoder = new daum.maps.services.Geocoder();

			// 주소로 좌표를 검색합니다
			geocoder.addressSearch(address, function(result, status) {
				// 정상적으로 검색이 완료됐으면 
				if (status === daum.maps.services.Status.OK) {

					//오버레이 콘텐츠 구성 함수 호출로 오버레이 콘텐츠 삽입
					//address = 주소, title = 타이틀, url = 관련링크, address2 = 지번주소
					content = Maps.overLayContent(address, title, url, address2);
				
					//검색한 주소의 X좌표, Y좌표 input hidden 값에 담기
					jQuery("#resultX").val(result[0].x);
					jQuery("#resultY").val(result[0].y);

					var coords = new daum.maps.LatLng(result[0].y, result[0].x);

					var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
						mapOption = {
							center: new daum.maps.LatLng(result[0].y, result[0].x), // 지도의 중심좌표
							level: 3, // 지도의 확대 레벨
							mapTypeId: daum.maps.MapTypeId.ROADMAP
						};  

					// 지도를 생성합니다    
					var map = new daum.maps.Map(mapContainer, mapOption); 

					// 결과값으로 받은 위치를 마커로 표시합니다
					var marker = new daum.maps.Marker({
						map: map,
						position: coords
					});

					// 마커 위에 커스텀오버레이를 표시합니다
					// 마커를 중심으로 커스텀 오버레이를 표시하기위해 CSS를 이용해 위치를 설정했습니다
					overlay = new daum.maps.CustomOverlay({
						content: content,
						map: map,
						position: marker.getPosition()       
					});

					// 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
					daum.maps.event.addListener(marker, 'click', function() {
						overlay.setMap(map);
					});

					// 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
					map.setCenter(coords);
					overlay.setMap(null);
				}
				else if (status == "ZERO_RESULT")
				{
					alert("없는 주소 입니다.")
					jQuery("#map").hide();
					return false;
				}
			});
			jQuery("#map").show();
		},
		//기본적인 맵가져오기(예 : 오시는길 페이지 사용)
		//address = 주소, title = 타이틀, url = 관련 링크, address2 = 지번주소
		addressMap : function(address, title, url, address2)
		{
			//파라미터 기본값 설정 하기
			title = (typeof title !== "undefined") ? title : "";
			url = (typeof url !== "undefined") ? url : "";
			address2 = (typeof address2 !== "undefined") ? address2 : "";

			//console.log(address);
			// 주소-좌표 변환 객체를 생성합니다
			var geocoder = new daum.maps.services.Geocoder();

			// 주소로 좌표를 검색합니다
			geocoder.addressSearch(address, function(result, status) {

				// 정상적으로 검색이 완료됐으면 
				 if (status === daum.maps.services.Status.OK) {

					//오버레이 콘텐츠 구성 함수 호출로 오버레이 콘텐츠 삽입
					//address = 주소, title = 타이틀, url = 관련링크, address2 = 지번주소
					content = Maps.overLayContent(address, title, url, address2);

					var coords = new daum.maps.LatLng(result[0].y, result[0].x);

					var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
						mapOption = {
							center: new daum.maps.LatLng(result[0].y, result[0].x), // 지도의 중심좌표
							level: 3 // 지도의 확대 레벨
						};  

					// 지도를 생성합니다    
					var map = new daum.maps.Map(mapContainer, mapOption); 

					// 결과값으로 받은 위치를 마커로 표시합니다
					var marker = new daum.maps.Marker({
						map: map,
						position: coords
					});

					// 마커 위에 커스텀오버레이를 표시합니다
					// 마커를 중심으로 커스텀 오버레이를 표시하기위해 CSS를 이용해 위치를 설정했습니다
					overlay = new daum.maps.CustomOverlay({
						content: content,
						map: map,
						position: marker.getPosition()       
					});

					// 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
					daum.maps.event.addListener(marker, 'click', function() {
						overlay.setMap(map);
					});					

					// 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
					map.setCenter(coords);
					overlay.setMap(null);
				}
				else if (status == "ZERO_RESULT")
				{
					alert("없는 주소 입니다.")
					jQuery("#map").hide();
					return false;
				}
			});

			jQuery("#map").show();			

		},
		//커스텀 오버레이 닫기 버튼 클릭시 호출 함수
		closeOverlay : function()
		{
			overlay.setMap(null);
		},
		//커스텀 오버레이 컨텐츠 구성 함수
		//address = 주소, title = 타이틀, url = 관련링크, address2 = 지번주소
		overLayContent : function(address, title, url, address2)
		{
			//파라미터 기본값 설정 하기
			title = (typeof title !== "undefined") ? title : "";
			url = (typeof url !== "undefined") ? url : "";
			address2 = (typeof address2 !== "undefined") ? address2 : "";

			// 커스텀 오버레이에 표시할 컨텐츠 입니다
			// 커스텀 오버레이는 아래와 같이 사용자가 자유롭게 컨텐츠를 구성하고 이벤트를 제어할 수 있기 때문에
			// 별도의 이벤트 메소드를 제공하지 않습니다 
			if (url == "")
			{
				url = "javascript:";
			}

			content = "";
			content = '<div class="wrap">' + 
						'    <div class="info">' + 
						'        <div class="title">' +  title +
						'            <div class="close" onclick="Maps.closeOverlay()" title="닫기"></div>' + 
						'        </div>' + 
						'        <div class="body">' + 
						'            <div class="img">' +
						'                <img src="http://cfile181.uf.daum.net/image/250649365602043421936D" width="73" height="70">' +
						'           </div>' + 
						'            <div class="desc">' + 
						'                <div class="ellipsis">' + address + '</div>' + 
						'                <div class="jibun ellipsis">' + address2 + '</div>' + 
						'                <div><a href="'+ url +'" target="_blank" class="link">홈페이지</a></div>' + 
						'            </div>' + 
						'        </div>' + 
						'    </div>' +    
						'</div>';

			return content;
		
		},
		//맵 생성 및 다중 커스텀 오버레이 생성 함수 호출(address : 주소(배열), title : 타이틀(배열), url : url(배열), address2 : 지번주소(배열))
		//맵의 중심은 배열에 담은 주소중 첫번째 주소를 맵의 센터로 잡는다.
		multiAddressMap : function(address, title, url, address2)
		{
			//파라미터 기본값 설정 하기
			title = (typeof title !== "undefined") ? title : "";
			url = (typeof url !== "undefined") ? url : "";
			address2 = (typeof address2 !== "undefined") ? address2 : "";

			var geocoder = new daum.maps.services.Geocoder();

			geocoder.addressSearch(address[0], function(result, status) {
				if (status === daum.maps.services.Status.OK) {

					var coords = new daum.maps.LatLng(result[0].y, result[0].x);

					// 주소-좌표 변환 객체를 생성합니다
					var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
						mapOption = {
							center: new daum.maps.LatLng(result[0].y, result[0].x), // 지도의 중심좌표
							level: 12 // 지도의 확대 레벨
						};  

					// 지도를 생성합니다    
					map = new daum.maps.Map(mapContainer, mapOption);

					// 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
					map.setCenter(coords);

					//커스텀오버레이 함수 호출
					Maps.multiOverlay(address, title, url, address2);
				}
				else if (status == "ZERO_RESULT")
				{
					alert("없는 주소 입니다.")
					jQuery("#map").hide();
					return false;
				}
			});				
		},
		//다중 커스텀 오버레이 생성 함수(address : 주소(배열), title : 타이틀(배열), url : url(배열), address2 : 지번주소(배열))
		multiOverlay : function(address, title, url, address2)
		{
			//파라미터 기본값 설정 하기
			title = (typeof title !== "undefined") ? title : "";
			url = (typeof url !== "undefined") ? url : "";
			address2 = (typeof address2 !== "undefined") ? address2 : "";

			var geocoder = new daum.maps.services.Geocoder();

			address.forEach(function(addr, index)
			{
				// 주소로 좌표를 검색합니다
				geocoder.addressSearch(addr, function(result, status) {						
					if (status === daum.maps.services.Status.OK) {
						//console.log(index);

						// 정상적으로 검색이 완료됐으면 
						if (status === daum.maps.services.Status.OK) {			

							var coords = new daum.maps.LatLng(result[0].y, result[0].x);

							//var icon = new daum.maps.MarkerImage(
							//	'http://localimg.daum-img.net/localimages/07/2009/map/icon/blog_icon01_on.png',
							//	new daum.maps.Size(50, 55),
							//	{
							//		offset: new daum.maps.Point(16, 34),
							//		alt: "마커 이미지 예제",
							//		shape: "poly",
							//		coords: "1,20,1,9,5,2,10,0,21,0,27,3,30,9,30,20,17,33,14,33"
							//	}
							//);

							var marker = new daum.maps.Marker({
								map: map,
								position: coords
							//	image: icon
								//image : markerImage // 마커 이미지
							});

							// 마커 위에 커스텀오버레이를 표시합니다
							// 마커를 중심으로 커스텀 오버레이를 표시하기위해 CSS를 이용해 위치를 설정했습니다
							var CustomOverlay = new daum.maps.CustomOverlay({
								//content: content,
								map: map,
								position: marker.getPosition()
							});						
//console.log(marker.getPosition())
							//마커 위에 커스텀오버레이 콘텐츠 Dom으로 구현 시작
							var Customcontent = document.createElement('div');
							Customcontent.className = "wrap";

							var info = document.createElement('div');
							info.className = "info"			
							Customcontent.appendChild(info);

							//커스텀오버레이 타이틀
							var contentTitle = document.createElement("div");
							contentTitle.className = "title"
							contentTitle.appendChild(document.createTextNode(title[index]));
							info.appendChild(contentTitle);

							//커스텀오버레이 닫기 버튼
							var closeBtn = document.createElement("div");
							closeBtn.className = "close";
							closeBtn.setAttribute("title","닫기");
							closeBtn.onclick = function() { CustomOverlay.setMap(null); };
							contentTitle.appendChild(closeBtn);

							var bodyContent = document.createElement("div");
							bodyContent.className = "body";
							info.appendChild(bodyContent);

							var imgDiv = document.createElement("div");
							imgDiv.className = "img";
							bodyContent.appendChild(imgDiv);

							//커스텀오버레이 이미지
							var imgContent = document.createElement("img");
							imgContent.setAttribute("src", "http://cfile181.uf.daum.net/image/250649365602043421936D");
							imgContent.setAttribute("width", "73");
							imgContent.setAttribute("heigth", "70");
							imgDiv.appendChild(imgContent);

							var descContent = document.createElement("div");
							descContent.className = "desc"
							bodyContent.appendChild(descContent);

							//커스텀오버레이 주소			
							var addressContent = document.createElement("div");
							addressContent.className = "ellipsis";
							addressContent.appendChild(document.createTextNode(result[0].address_name));
							descContent.appendChild(addressContent);

							//커스텀오버레이 지번주소
							var address2Content = document.createElement("div");
							address2Content.className = "jibun ellipsis";
							address2Content.appendChild(document.createTextNode(address2[index]));
							descContent.appendChild(address2Content);

							var LinkDiv = document.createElement("div");
							descContent.appendChild(LinkDiv);

							//커스텀오버레이 링크
							var LinkContent = document.createElement("a");
							if (url == "")
							{
								url = "javascript:"
							}
							LinkContent.setAttribute("href", url[index]);
							if (url != "javascript:")
							{
								LinkContent.setAttribute("target", "_blank");
							}
							LinkContent.className = "link";
							LinkContent.appendChild(document.createTextNode("홈페이지"));
							LinkDiv.appendChild(LinkContent);
							//마커 위에 커스텀오버레이 콘텐츠 Dom으로 구현 끝

							CustomOverlay.setContent(Customcontent);

							//다중 커스텀 오버레이시 커스텀 오버레이 객체 생성시마다 전역변수에 배열로 담아두기
							//이 배열은 추후 마커 클릭시 기존 노출된 오버레이를 삭제하기 위해 사용
							arrOverlay.push(CustomOverlay);
				

							// 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
							daum.maps.event.addListener(marker, 'click', function() {

								//마커 클릭시 기존에 열린 커스텀 오버레이 전체를 닫는다
								for (var k = 0; k < arrOverlay.length; k ++)
								{
									arrOverlay[k].setMap(null);
								}

								//클릭한 오버레이만 노출
								CustomOverlay.setMap(map);

							});								
							
							// 인포윈도우로 장소에 대한 설명을 표시합니다
							//var infowindow = new daum.maps.InfoWindow({
							//	content: '<div style="width:150px;text-align:center;padding:6px 0;">우리회사</div>'
							//});
							//infowindow.open(map, marker);

							CustomOverlay.setMap(null);
							

						}
						else if (status == "ZERO_RESULT")
						{
							alert("없는 주소 입니다.")
							jQuery("#map").hide();
							return false;
						}

					}
				});				
			});

		}
	
	}
}();