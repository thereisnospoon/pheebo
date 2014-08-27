function ph_thread() {

	var formatting = 'HH:mm:ss d MMMM, yy';

	function formatDates() {

		$('.post_date').each(function () {

			var dateSpan = $(this);
			dateSpan.text($.format.date(new Date(parseInt(dateSpan.text())), formatting));
		});
	}
	formatDates();

	function switchElement(el) {

		if (isDisabled(el)) {
			el.removeClass('disabled');
		} else {
			el.addClass('disabled');
		}
	}

	function isDisabled(el) {

		var elClasses =  el.attr('class');
		if (elClasses) {
			return null != elClasses.match(/disabled/);
		} else {
			return false;
		}
	}

	function getPostId(postElement) {

		return postElement.attr('id').match(/\d+/)[0];
	}

	(function hideFormButtonIfHomePage() {

		if (window.location.pathname == '/') {
			$('#show_form_btn_holder').css('display', 'none');
		}
	})();

	function setError(message) {
		$('#post_errors').text(message);
	}

	function clearErrors() {

		var errors = $('#post_errors');
		errors.text('');
	}

	function getImageId() {

		var elId = $('.file_upload span').attr('id');
		console.log('file upload id ' + elId);
		if (elId) {
			return elId.match(/\d+/)[0];
		}
	}

	function clearAttachment() {

		var fileInput = $('form input[type=file]');
		fileInput.replaceWith(fileInput.clone(true));
		var attachElement = $('.file_upload');
		attachElement.text('Attach image');
		attachElement.attr('id', null);
	}

	(function fileAttachment() {

		var fileInputElement = $('form input[type=file]');
		$('.file_upload').click(function() {
			fileInputElement.trigger('click');
		});

		fileInputElement.change(function() {

			var file = fileInputElement[0].files[0];
			$('.file_upload span').text('Loading..');
			switchElement($('#send-btn'));
			switchElement($('.file_upload'));

			(function upload(file) {

				var formData = new FormData;
				formData.append('file', file);
				$.ajax({
					type: 'POST',
					contentType: false,
					processData: false,
					url: '/images',
					data: formData,
					success: function(data) {

						console.log(data);

						if (data.error) {
							setError(data.error);
							clearAttachment();
						} else {

							var attachElement = $('.file_upload span');
							attachElement.text(file.name);
							attachElement.attr('id', 'img_' + data.imageId);
							clearErrors();
						}
						switchElement($('#send-btn'));
						switchElement($('.file_upload'));
					}
				});
			})(file);
		});
	})();

	function sendPost(thread, messageText, lastPostId, imageId) {

		var postData = {message: messageText};
		var postUrl = '/thread/' + thread + "?lastPostId=" + lastPostId;
		if (imageId) {
			postUrl += '&imageId=' + imageId;
		}

		clearAttachment();

		$.post(postUrl, postData, function (data) {


			var lastPost = $('.post').last();

			for (var i = 0; i < data.length; i++) {

				var newPost = data[i];
				var lastPostId = getPostId(lastPost);
				var newElement = lastPost.clone();

				newElement.attr('id', newElement.attr('id').replace(new RegExp(lastPostId), newPost.postId));
				$('.post_message', newElement).html(newPost.message);
				$('.post_date', newElement).text($.format.date(newPost.postedWhen, formatting));
				var a = $('.post_header a', newElement).first();
				var newHTML = a.html().replace(new RegExp(lastPostId), newPost.postId);
				var href = a.attr('href').replace(new RegExp(lastPostId), newPost.postId);
				a.html(newHTML);
				a.attr('href', href);

				clearErrors();

				$('.post_form textarea').val('');

				lastPost.after(newElement);
				lastPost = newElement;
			}
			$("html, body").animate({ scrollTop: $(document).height() }, 1000);
		});
	}

	function createThread(board, messageText, headerText) {

		var thread = {header: headerText, message: messageText};
		$.post('/' + board + '/thread', thread, function (data) {

			if (!data.error) {
				window.location = '/' + board + '/thread/' + data.threadId;
			}
		});
	}

	function isPostForm() {

		// get thread id from form id
		var formId = $('.post_form').first().attr('id');
		if (!formId) {
			// then this is thread creation form
			return null;
		} else {
			return formId.match(/\d+/)[0];
		}
	}

	$('.post_form').draggable();

	var btnTextWhenHidden = 'Show post form';
	var btnTextWhenVisible = 'Hide post form';

	var formControl = $('#show_form_btn_holder');

	formControl.text(btnTextWhenHidden);

	formControl.bind("click", function () {

		var postForm = $('.post_form').first();

		if (postForm.css('display') != 'none') {

			formControl.text(btnTextWhenHidden);
			postForm.css('display', 'none');
		} else {
			formControl.text(btnTextWhenVisible);
			postForm.css('display', 'initial');
		}
	});

	$('#send-btn').click(function () {

		if (isDisabled($(this))) {
			return;
		}
		var textArea = $('.post_form textarea').first();
		var messageText = textArea.val();

		if (!messageText || messageText.length > 3000) {

			var errors = $('#post_errors');
			errors.text('Message should be between 1 and 3000 symbols');
			return;
		}

		var threadId = isPostForm();
		if (threadId) {
			sendPost(threadId, messageText, getPostId($('.post').last()), getImageId());
		} else {

			var header = $('.post_form input').first().val();
			if (!header || header.length > 60) {

				var errors = $('#post_errors');
				errors.text('Thread theme length should be between 1 and 60 symbols');
				return;
			}
			var board = $('title').text().substr(1);
			createThread(board, messageText, header);
		}
	});
}
$(ph_thread);