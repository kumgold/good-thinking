# 좋은 생각 카드

## 설명
좋은 생각 카드라는 명언 저장 서비스입니다. 서비스에서 Gemini API를 두 가지 형태로 활용하였습니다.
하나는 단순한 프롬프트 형식으로 명언을 요청할 수 있으며 반환된 명언을 저장, 수정, 삭제 할 수 있습니다. 저장된 명언은 랜덤 카드 뽑기 형식으로 뽑을 수 있어 유저에게 즐거움을 선사합니다.
다른 하나는 모바일에서 자주 사용하는 형태인 대화형 챗봇 형태로 서비스를 구현하였습니다. 챗봇을 통해 다양한 격려를 받을 수 있습니다.

## 화면
| Main                                               | Gemini                                             | Draw                                               | Edit                                               | Chat                                               |
|----------------------------------------------------|----------------------------------------------------|----------------------------------------------------|----------------------------------------------------|----------------------------------------------------|
| <img src="./images/good_thinking1.png" height=300> | <img src="./images/good_thinking2.png" height=300> | <img src="./images/good_thinking4.png" height=300> | <img src="./images/good_thinking3.png" height=300> | <img src="./images/good_thinking6.png" height=300> |

## 구현 기능
- 내부 데이터베이스 CRUD (Room 사용)
- MVI 패턴 사용 (Compose - StateFlow)
- 테스트 코드 작성

## 기술 스택
Language : Kotlin <br>
View : Compose <br>
AndroidX : Room, ViewModel, Hilt, AndroidX-Flow-Lifecycle <br>
Kotlin : Coroutine, StateFlow <br>
etc : Gemini API, JUnit4 <br>