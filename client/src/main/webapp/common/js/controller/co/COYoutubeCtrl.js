var COYoutubeCtrl = (function(){

	var apiKey = 'AIzaSyDY6JjQeZewmFFikzu58VqNm7qbxd8EpH0';

	var youtube_search = function(youtubeForm, videoId){

		var videoInfoForm = {};


		$.ajax({
			url: 'https://www.googleapis.com/youtube/v3/videos',
			type: 'GET',
			async: false,
			data: {
				part: 'snippet,contentDetails',
				id: videoId,
				key: apiKey
			},
			success: function (data) {
				if (data.items.length > 0) {
					var videoInfo = data.items[0];

					var title = videoInfo.snippet.title;
					var description = videoInfo.snippet.description;
					var thumbnailUrl = videoInfo.snippet.thumbnails.default.url;

					// contentDetails에 접근하여 duration을 얻습니다.
					var duration = parseDuration(videoInfo.contentDetails.duration);
					videoInfoForm.title = title;
					videoInfoForm.description = description;
					videoInfoForm.thumbnailUrl = thumbnailUrl;
					videoInfoForm.duration = duration;


				} else {
					$('#videoInfo').html('Video not found.');
				}

				return videoInfoForm;
			},
			error: function (error) {
				console.error('Error fetching video info:', error);
			}
		});



		return videoInfoForm;
	}

	var parseDuration = function(duration){
		var match = duration.match(/PT(\d+H)?(\d+M)?(\d+S)?/);

		var hours = parseInt(match[1]) || 0;
		var minutes = parseInt(match[2]) || 0;
		var seconds = parseInt(match[3]) || 0;

		var totalSeconds = hours * 3600 + minutes * 60 + seconds;

		var result = '';

		if (hours > 0) {
			result += hours + 'h ';
		}

		if (minutes > 0 || hours > 0) {
			result += minutes + 'm ';
		}

		result = (hours*60) + minutes;

		return result;
	}

	// 가져온 영상 정보를 화면에 표시하는 함수
	var displayVideoInfo = function(videoInfo) {
		var title = videoInfo.snippet.title;
		var description = videoInfo.snippet.description;
		var thumbnailUrl = videoInfo.snippet.thumbnails.default.url;

		// contentDetails에 접근하여 duration을 얻습니다.
		var duration = parseDuration(videoInfo.contentDetails.duration);

		console.log("title = " + title);
		console.log("description = " + description);
		console.log("thumbnailUrl = " + thumbnailUrl);
		console.log("duration = " + duration);
	}

	return {

		youtubeSearch : youtube_search


	}
}());