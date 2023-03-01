# JuBTI

# 기능명세서
<html> <head> <meta charset="EUC-KR"> <title>API 명세서</title> </head> <body> <table border="1"> <th>요구사항ID</th><th>요구사항 이름</th><th>요구사항 설명</th> <th>중요도</th> <th>비고</th> <tr><td></td> <td>메인페이지 출력</td>  <td>화면 중단에 게시판 형태 리스트</td> <td></td>  <td>로그인된 사용자만 가능</td>  </tr>
<tr><td></td><td>로그인 시</td> <td>세션? 쿠키? 활동하지않으면 일정시간후 로그아웃</td>   <td></td>  <td></td>  
<tr><td></td><td>비로그인 시</td> <td>로그인 화면으로 강제 송출</td>   <td></td>  <td></td>
<tr> <td></td> <td>네비게이션바</td> <td> - 메인페이지로 이동하는 좌측상단 로고아이콘<br>- 로그아웃 버튼 우측상단<br>- 글쓰기 폼으로 이동하는 우측하단 링크 "내 Recipe 추천"</td>  <td></td><td>로그인된 사용자만 가능</td>  </tr>
<tr> <td></td> <td>회원가입</td> <td>사용자의 아이디, 비밀번호, 닉네임, mbti를 받아 서버에 저장</td>  <td></td><td>• username은 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)로 구성되어야 한다.<br>• password는 최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자로 구성되어야 한다.</td>  </tr>
<tr> <td></td> <td>글쓰기</td> <td>사진, 레시피명, mbti, 재료명 select폼, 설명 을 적어서 서버로 보낸다</td>  <td></td><td>로그인된 사용자만 가능</td>  </tr>
<tr><td></td> <td>상세페이지</td> <td>사용자 닉네임, 사진, 레시피명, bmti, 재료명, 설명을 서버에서 가져와서 화면에 보여준다</td>  <td></td><td>로그인된 사용자만 가능</td>  </tr>
<tr><td></td> <td>상세페이지 수정 / 삭제</td> <td>상세페이지 내용을 수정하거나 삭제한다</td>  <td></td><td>권한 : 해당 글을 작성한 사용자만 가능</td>  </tr>
<tr><td></td> <td>페이지네이션</td> <td>- 페이지네이션 버튼 링크 5개로 보여준다<br> - 한 페이지에 게시물 9~12개</td>  <td></td><td>로그인된 사용자만 가능</td>  </tr>
<tr><td></td> <td>채팅방</td> <td>과부화를 줄이기위해 여러개의 채팅방을 구성 - 사용자의 닉네임을 서버에서 가져옴</td>  <td></td><td>로그인된 사용자만 가능</td>  </tr>
<tr><td></td> <td>댓글</td> <td>해당 상세페이지의 댓글기능<br> - 댓글 다는 사람들의 닉네임과 댓글내용을 서버에 보내고 가져와서 보여준다</td>  <td></td><td>로그인된 사용자만 가능</td>  </tr>
<tr><td></td> <td>좋아요</td> <td>로그인된 사용자만 가능</td>  <td></td><td>로그인된 사용자만 가능</td>  </tr><table> </body> </html>















# API 명세서
<html> <head> <meta charset="EUC-KR"> <title>API 명세서</title> </head> <body> <table border="1"> <th>분류</th> <th>기능</th><th>Method</th><th>URL</th> <td>RequestHeader</td><td>ResponsHeader</td><th>RequestBody</th> <th>ResponseBody</th> 
<tr><td rowspan="2" >회원</td> <td>회원가입</td>  <td>POST</td> <td>/api/user/signup</td> <td></td><td></td> <td>{<br>"id": "아이디",<br>"nickname": "닉네임",<br>"password": "비밀번호",<br>"passwordCheck": "비밀번호",<br>"MBTI": "ENFP"<br>}</td>  <td>200, "회원가입 완료됐습니다."</td></tr>
<tr> <td>로그인</td><td>POST</td> <td>/api/user/login</td> <td></td><td>Authorization : Bearer <br>~~~~토큰값</td>  <td>{<br>"id": "아이디",<br>"password": "비밀번호"<br>}</td><td>200, "로그인 완료됐습니다."</td></tr>
<tr><td rowspan="7" >레시피</td><td>작성</td> <td>POST</td> <td>/api/recipe</td> <td>Authorization : Bearer <br>~~~~토큰값</td><td></td>  <td>{<br>"MBTI": "ENFP",<br>"title": "레시피명"<br>"materiral": "재료"<br>}</td>  <td>200, "회원가입 완료됐습니다."</td></tr>
<td >좋아요</td> <td>POST</td> <td>/api/recipe/{id}</td> <td>Authorization : Bearer <br>~~~~토큰값</td><td></td>  <td></td>  <td>200, “좋아요”</td>
<tr><td>전체조회</td> <td>GET</td> <td>/api/recipe</td>  <td></td><td></td> <td></td><td>{ <br>”id”:”1”, ”image”: ”이미지 url”, ”nickname”: “닉네임”, ”title”: ”제목”, ”mbti”: ”INFP”, ”like”: ”좋아요 수” }</td> </tr>
<tr><td>페이지 조회</td> <td>GET</td> <td>/api/recipe/{a}/{b}</td>  <td></td><td></td> <td></td><td>{ <br>”id”:”1”, ”image”: ”이미지 url”, ”nickname”: “닉네임”, ”title”: ”제목”, ”mbti”: ”INFP”, ”like”: ”좋아요 수”<br> }, <br>{<br> ”id”:”2”, ”image”: ”이미지 url”, ”nickname”: “닉네임”, ”title”: ”제목”, ”mbti”: ”INFP”, ”like”: ”좋아요 수”<br> }</td> </tr>
<tr><td>상세보기</td> <td>GET</td> <td>/api/recipe/{id}</td>  <td></td><td></td> <td></td><td>{ <br>”image”: ”이미지 url”, ”nickname”: “닉네임”, ”title”: “제목”, ”material”: ”재료”, ”content”: “조리법”, ”mbti”: ”INFP”,, ”like”: ”좋아요 수”, ”comments”: ”댓글” ”createdAt”: “2022-07-25T12:43:01.226062”, ”modifiedAt”: “2022-07-25T12:43:01.226062” <br>}</td> </tr>
<tr><td>수정</td> <td>PUT</td> <td>/api/recipe</td><td>Authorization : Bearer <br>~~~~토큰값</td><td></td>   <td></td><td>200, “수정 완료”</td></tr>
<tr><td>삭제</td> <td>DELETE</td> <td>/api/recipe</td> <td>Authorization : Bearer <br>~~~~토큰값</td><td></td>  <td></td><td>200, "삭제 완료 됐습니다."</td>  </tr>
<tr><td rowspan="3" >댓글</td><td>작성</td> <td>POST</td> <td>/api/recipe/{id}/comment</td> <td>Authorization : Bearer <br>~~~~토큰값</td><td></td>  <td>{ <br>”comments”: ”댓글내용”<br> }</td>  <td>200, “작성 완료”</td>
<tr><td>수정</td> <td>PUT</td> <td>/api/recipe</td><td>Authorization : Bearer <br>~~~~토큰값</td><td></td>   <td>{ <br>”comments”: ”댓글내용”<br> }</td><td>200, “수정 완료”</td>
<tr><td>삭제</td> <td>DELETE</td> <td>/api/recipe</td> <td>Authorization : Bearer <br>~~~~토큰값</td><td></td>  <td></td><td>200, "삭제 완료 "</td>  </tr>
</table> </body> </html>
