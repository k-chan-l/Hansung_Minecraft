# Mod


---

## 사용 방법

모든 플러그인 모두 다운 받은 후에 서버플러그인 폴더에 [설치](https://github.com/k-chan-l/Hansung_Minecraft/wiki/%EC%82%AC%EC%9A%A9%EB%B0%A9%EB%B2%95#3-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EB%B9%8C%EB%93%9C%ED%95%98%EA%B8%B0)하면 된다.  
링크를 따라 끝까지 계속해서 진행

---


## MqttHolo

mqtt 메시지를 받아서 홀로그램으로 나타내주는 플러그인


---


## MqttLungrow

mqtt 메시지를 받아 전달하는 플러그인  
각 플러그인 끼리 이벤트를 주고 받으려면 해당 플러그인을 꼭 라이브러리에 [등록](https://github.com/k-chan-l/Hansung_Minecraft/wiki/JAVA-%EB%9D%BC%EC%9D%B4%EB%B8%8C%EB%9F%AC%EB%A6%AC-%EC%A0%81%EC%9A%A9%ED%95%98%EA%B8%B0#%EB%9D%BC%EC%9D%B4%EB%B8%8C%EB%9F%AC%EB%A6%AC-%EC%83%9D%EC%84%B1)해야함


---


## NPCPlugin

npc를 만들고 제어하는 플러그인  
각 플러그인 끼리 이벤트를 주고 받으려면 해당 플러그인을 꼭 라이브러리에 [등록](https://github.com/k-chan-l/Hansung_Minecraft/wiki/JAVA-%EB%9D%BC%EC%9D%B4%EB%B8%8C%EB%9F%AC%EB%A6%AC-%EC%A0%81%EC%9A%A9%ED%95%98%EA%B8%B0#%EB%9D%BC%EC%9D%B4%EB%B8%8C%EB%9F%AC%EB%A6%AC-%EC%83%9D%EC%84%B1)해야함


---


## Npcchatbubble

npc가 말하는 대사를 표시하는 플러그인


---


## checkpath

플레이어가 이동하는 경로를 저장하는 플러그인


---


## httpconnection

http연결을 하여 DynamoDB와 통신하는 플러그인  
각 플러그인 끼리 이벤트를 주고 받으려면 해당 플러그인을 꼭 라이브러리에 [등록](https://github.com/k-chan-l/Hansung_Minecraft/wiki/JAVA-%EB%9D%BC%EC%9D%B4%EB%B8%8C%EB%9F%AC%EB%A6%AC-%EC%A0%81%EC%9A%A9%ED%95%98%EA%B8%B0#%EB%9D%BC%EC%9D%B4%EB%B8%8C%EB%9F%AC%EB%A6%AC-%EC%83%9D%EC%84%B1)해야함


---

## letterBuild

글자를 입력하는데 도움이 되는 플러그인  
사용시 블럭으로 글자를 입력한다.
  
usage : /letter <option> <instance>  
\<option> -> (list, help, build)  
list : 건설할 블록 종류를 사용자에게 보여줍니다.  
\<instance> : x  
  
help : 사용방법을 알려줍니다.  
\<instance> : x  
  
build : 문자를 입력하면 해당 문자에 해당하는 5*5 블럭을 건설합니다.  
\<instance> : \<X> \<Y> \<Z> \<letter> \<MainBlockType> \<BackgroundBlockType>  
위의 instance들을 순서대로 입력  
  
\<X> : 글자를 건설 할 왼쪽 아래의 블럭의 X좌표  
\<Y> : 글자를 건설 할 왼쪽 아래의 블럭의 Y좌표  
\<Z> : 글자를 건설 할 왼쪽 아래의 블럭의 Z좌표  
\<letter> : 건설할 글자의 종류 숫자,알파벳 대소문자 사용가능  
\<MainBlockType> : 글자의 블록 종류  
\<BackgroundBlockType> : 배경의 블록 종류
