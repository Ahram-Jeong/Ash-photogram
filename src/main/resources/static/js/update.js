// (1) 회원 정보 수정
function update(userId, event) {
    event.preventDefault(); // form 태그 action 막기
    let data = $("#profileUpdate").serialize(); // .serialize(): form data 내의 값들을 한 번에 가져오는 jQuery 함수
    console.log(data);

    $.ajax({
        type: "put",
        url: `/api/user/${userId}`,
        data: data,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json"
    }).done(res=> { // done : HttpStatus 상태코드가 200번대 일 때
        console.log("성공", res);
        location.href = `/user/${userId}`;
    }).fail(error=> { // fail : HttpStatus 상태코드가 200번대가 아닐 때
        console.log("실패", error.responseJSON.data);
        if(error.data == null) {
            alert(error.responseJSON.message);
        } else {
            alert(JSON.stringify(error.responseJSON.data));
        }
    });
}