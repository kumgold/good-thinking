# 좋은 생각 카드

## 설명
좋은 생각 카드라는 명언 저장 서비스를 기획하게 되었습니다. Gemini API를 두 가지 형태로 활용하였습니다.
하나는 단순한 프롬프트 형식으로 요청하여 명언을 저장, 수정, 삭제 할 수 있습니다.
다른 하나는 모바일에서 자주 사용하는 형태인 대화형 챗봇 형태로 서비스를 구현하였습니다.

## 화면
| Main                                    | Gemini                                  | Draw                                    | Edit                                    | Chat                                    |
|-----------------------------------------|-----------------------------------------|-----------------------------------------|-----------------------------------------|-----------------------------------------|
| <img src="./images/good_thinking1.png"> | <img src="./images/good_thinking2.png"> | <img src="./images/good_thinking4.png"> | <img src="./images/good_thinking3.png"> | <img src="./images/good_thinking6.png"> |

## 프로젝트 구조
<img src="./images/good_thinking5.png">

## 구현 기능
- 내부 데이터베이스 CRUD
- MVI 패턴 사용
- 테스트 코드 작성

## 기술 스택
Language : Kotlin <br>
View : Compose <br>
AndroidX : Room, ViewModel, Hilt, AndroidX-Flow-Lifecycle <br>
Kotlin : Coroutine, StateFlow <br>
etc : Gemini API, JUnit4 <br>