/**
 2. 스토리 페이지
 (1) 스토리 로드하기
 (2) 스토리 스크롤 페이징하기
 (3) 좋아요, 안좋아요
 (4) 댓글쓰기
 (5) 댓글삭제
 */

// (1) 스토리 로드하기
let page = 0
function storyLoad() {
    $.ajax({
        url: `/api/image?page=${page}`,
        dataType: "json"
    }).done(res => {
        console.log(res);
        res.data.content.forEach((image) => {
            let storyItem = getStoryItem(image);
            $("#storyList").append(storyItem);
        });
    }).fail(error => {
        console.log("실패", error);
    });
}

storyLoad();

function getStoryItem(image) {
    let item = `
	<div class="story-list__item">
	<div class="sl__item__header">
	<div>
	<img class="profile-image" src="/upload/${image.user.profileImageUrl}"
	onerror="this.src='/images/person.png'" />
	</div>
	<div>${image.user.username}</div>
	</div>
	<div class="sl__item__img">
	<img src="/upload/${image.postImageUrl}" />
	</div>
	<div class="sl__item__contents">
	<div class="sl__item__contents__icon">
	
	    <!--좋아요 버튼-->
        <button>`;

            if(image.likeState) {
                item += `<i class="fas fa-heart active" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;
            } else {
                item += `<i class="fa-heart far" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;
            }

    item += `
        </button>
	</div>
	<span class="like"><b id="storyLikeCount-${image.id}">${image.likeCount} </b>likes</span>
	<div class="sl__item__contents__content">
	<p>${image.caption}</p>
	</div>
	<div id="storyCommentList-1">
	<div class="sl__item__contents__comment" id="storyCommentItem-1">
	<p>
	<b>Lovely :</b> 부럽습니다.
	</p>
	<button>
	<i class="fas fa-times"></i>
	</button>
	</div>
	</div>
	<div class="sl__item__input">
	<input type="text" placeholder="comment..." id="storyCommentInput-1" />
	<button type="button" onClick="addComment()">post</button>
	</div>
	</div>
	</div>`;

    return item;
}

// (2) 스토리 스크롤 페이징하기
$(window).scroll(() => {
    // console.log("윈도우 스크롤탑", $(window).scrollTop());
    // console.log("문서의 높이", $(document).height());
    // console.log("윈도우 높이", $(window).height());

    let chkNum = $(window).scrollTop() - ($(document).height() - $(window).height());
    console.log(chkNum);
    if (chkNum > -1  && chkNum < 1) {
        page++
        storyLoad();
    }
});


// (3) 좋아요, 안좋아요
function toggleLike(imageId) {
    let likeIcon = $(`#storyLikeIcon-${imageId}`);
    
    if (likeIcon.hasClass("far")) { // 좋아요 누르지 않은 상태
        $.ajax({
            type: "post",
            url: `/api/image/${imageId}/likes`,
            dataType: "json"
        }).done(res => {
            // 빨간 하트
            likeIcon.addClass("fas");
            likeIcon.addClass("active");
            likeIcon.removeClass("far");

            // 좋아요 수 증가
            let likeCountStr = $(`#storyLikeCount-${imageId}`).text();
            let likeCount = Number(likeCountStr) + 1;
            $(`#storyLikeCount-${imageId}`).text(likeCount);
        }).fail(error => {
            console.log("실패", error);
        });
    } else { // 좋아요 누른 상태
        $.ajax({
            type: "delete",
            url: `/api/image/${imageId}/likes`,
            dataType: "json"
        }).done(res => {
            // 빈 하트
            likeIcon.removeClass("fas");
            likeIcon.removeClass("active");
            likeIcon.addClass("far");

            // 좋아요 수 감소
            let likeCountStr = $(`#storyLikeCount-${imageId}`).text();
            let likeCount = Number(likeCountStr) - 1;
            $(`#storyLikeCount-${imageId}`).text(likeCount);
        }).fail(error => {
            console.log("실패", error);
        });
    }
}

// (4) 댓글쓰기
function addComment() {
    let commentInput = $("#storyCommentInput-1");
    let commentList = $("#storyCommentList-1");

    let data = {
        content: commentInput.val()
    }

    if (data.content === "") {
        alert("댓글을 작성해주세요!");
        return;
    }

    let content = `
			  <div class="sl__item__contents__comment" id="storyCommentItem-2""> 
			    <p>
			      <b>GilDong :</b>
			      댓글 샘플입니다.
			    </p>
			    <button><i class="fas fa-times"></i></button>
			  </div>
	`;
    commentList.prepend(content);
    commentInput.val("");
}

// (5) 댓글 삭제
function deleteComment() {

}
