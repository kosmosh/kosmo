<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Testing websockets</title>
</head>
<body>

    <fieldset>
    
    
        <textarea id="messageWindow" rows="10" cols="50" readonly="true"></textarea> <br/>
        
        <input id="inputMessage" type="text"/>
        <input type="submit" value="send" onclick="send()" />  
        
    </fieldset>
    
     
</body>


<script type="text/javascript">

        var webSocket = new WebSocket('ws://localhost/websocket');//클래스 이름
        
        var textarea = document.getElementById("messageWindow");//(messageWindow)에있는것을 가져와라
        
        var inputMessage = document.getElementById('inputMessage');//(inputMessage)에있는것을 가져와라 = getElementById()= 
        
    
    webSocket.onopen = function(event) {
    	textarea.value += "연결 성공\n";  //대화창 화면(messageWindow)에 연결성공 띄우기
    };
    
    webSocket.onmessage = function(event) {  
    	textarea.value += "상대 : " + event.data + "\n";
    };
    
    webSocket.onerror = function(event) { //웹소켓 에러 났을때 호출되는     	
    	alert(event.data);    // 잘 모르겠음// 물어보기!
    };
    
    
    
    function send() {  //send 버튼 누르면
        textarea.value += "나 : " + inputMessage.value + "\n";  //inputMessage에 적은 내용  대화창에 출력
        
        webSocket.send(inputMessage.value);  //웹 소켓으로 inputMessage의 객체의 값을 보낸다.
        
        inputMessage.value = "";   //inputMessage 객체의 값 초기화
    }
    
  </script>
</html>

