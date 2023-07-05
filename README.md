# A. 개발 팀원

김민재

윤현우


# B. 개발 환경

OS : Android(minSdk: 24, targetSdk: 31)

Language : Java

IDE : Android Studio

Target Device : Galaxy S7


# C. 앱 소개


## Tab1. 연락처

<img width="206" alt="Tab1" src="https://github.com/akmj4869/App/assets/57134776/3be3c543-c348-4707-a282-ab18ac2ef275">
<img width="194" alt="Tab1-2" src="https://github.com/akmj4869/App/assets/57134776/c16891fb-704f-4c38-88d5-60dacb863a78">

### 주요 기능

각 이름을 터치하면 그 이름과 전화번호가 포함된 프로필을 확인할 수 있다.

우측 하단에 위치한  + 버튼을 눌러 새로운 연락처를 추가할 수 있다.

프로필 창에서 우측하단에 위치한 연필 버튼을 누르면 갤러리로 이동할 수 있고, 여기서 프로필 이미지를 변경할 수 있다.

프로필 사진을 변경한 후 원래 탭으로 돌아오면 각 이름 옆의 작은 프로필의 사진 역시 변경된다.

프로필 창 하단의 전화기 버튼을 누르면 통화가 가능하며, 눈 모양을 누르게 되면 해당 인물의 연락처를 목록에서 삭제할 수 있다.


### 기술 설명

Recyclerview를 통해 json 파일에 예시로 저장되어 있는 연락처들을 parsing해서 화면에 띄워준다.

각 이름을 누르면 새로운 Activity로 이동해 프로필을 보여준다.

통화 권한을 받아와 통화가 가능하며, 파일 접근 권한을 받아 갤러리의 이미지 사진으로 프로필 사진을 변경한다.

## Tab2. 갤러리

<img width="193" alt="Tab2" src="https://github.com/akmj4869/App/assets/57134776/c0faa112-c6cb-4d88-a3b9-0bc843708f3c">
<img width="201" alt="Tab2-2" src="https://github.com/akmj4869/App/assets/57134776/a23319a1-c0b1-4bb7-ba86-05f95ae53d6d">
<img width="199" alt="Tab2-3" src="https://github.com/akmj4869/App/assets/57134776/374ffefd-0ef5-484d-a80d-ba6453b41e09">

### 주요 기능

우측 하단의 버튼을 눌러 갤러리로 이동하거나 카메라 화면을 띄울 수 있다. 

갤러리에서 이미지를 불러오거나 혹은 사진을 찍은 후 파일 이름을 입력한 뒤 저장 버튼을 누르면 파일이 갤러리 상에 업로드 되는 것을 확인할 수 있다.

이미지를 터치하면 해당 이미지가 확대되는데, 이때 카메라로 찍은 경우 상단에 저장했던 파일 명이 뜨게 된다. 하단의 쓰레기통을 누르면 해당 이미지를 갤러리에서 삭제할 수 있다.

이미지가 확대된 창에서 좌우로 슬라이드를 하면 이전이나 다음 이미지로 이동할 수 있으며, 아래로 슬라이드하면 확대 창을 닫고 다시 갤러리로 돌아갈 수 있다.


### 기술 설명

Grid와 recyclerview를 통해 갤러리를 구축했으며, 사진을 터치하면 새로운 Activity로 넘어가 Viewpager와 GalleryAdapter로 확대된 이미지를 보여준다.

Floatingbutton을 통해 갤러리와 카메라 버튼을 구현했으며 파일 접근 권한을 받아 갤러리로 접근할 수 있다. 해당 이미지를 받아와 특정 파일에 저장한다.

카메라 권한을 받아와 사진을 찍으면 위와 같은 파일에 마찬가지로 이미지와 그 파일명을 저장해 이후 Adapter를 업데이트해준다.
