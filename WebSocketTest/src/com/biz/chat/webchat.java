package com.biz.chat;


import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@ServerEndpoint("/websocket")
public class webchat {    //웹 소켓 정의

   private static Set<Session> clients = 
		   Collections.synchronizedSet(new HashSet<Session>()); 
   
   		// synchronizedSet은 여러개가 아닌 하나의 스래드만 접근할 수 있게 하기 위함
   		// Collections은 자바에서 무한적으로 객체 데이터를 저장하는 방식  : 자바에서 대용량의 데이터를 추가 삭제하면서 처리가 필요할 때 자바 컬렉션을 사용한다.
   		// Set은 순서를  유지하지 않는 데이터들의 집합이고 데이터 중복을 허용하지 않는다.
   		// session 상태유지 - 다른 페이지로 이동하더라도 그 전 페이지 정보 유지.. 서버쪽의 웹 컨테이너에 상태 유지
   		// HashSet - 순서가 필요없는 데이터를 hashTable에 저장  ( 여러번 접속해도 ip 가 하나라면 하나로 카운트 -set)
   
   
   @OnOpen   //웹 소켓이 연결되면 호출되는 이벤트
   public void onOpen(Session session) {
	   // Add session to the connected sessions set
	   System.out.println(session);
	   clients.add(session);
   }

   
   @OnMessage
   public void onMessage(String message, Session session) throws IOException {
      System.out.println(message);
      synchronized (clients) {
    	 // 연결된 세션을 반복하고 수신 된 메시지를 브로드 캐스트합니다.
         for (Session client : clients) {  // 
            if (!client.equals(session)) {  // 클라이언트와 세션이 같지 않으면  
            	
               client.getBasicRemote().sendText(message); // ??   getBasicRemote()로 사용자의 스트림(인아웃풋)을 돕는다
               
            }
         }
      }
   }
   
   //synchronized 여러개가 안닌 하나의 스레드만.. // 스레드는 한 프로세스 내에서 두가지 이상의 일을 동시에 하는것 (보통은 한개의 프로세스는 한 가지의 일을 한다.)


   @OnClose
   public void onClose(Session session) {
      // Remove session from the connected sessions set
      clients.remove(session);
   }
}


/*@ServerEndpoint 부분이 클라이언트에서 접속할 서버 주소이다

@OnMessage는 클라이언트로부터 메시지가 도착했을 경우 처리 방법이며

@OnOpen과 @onClose는 클라이언트가 접속을 할 때와 접속이 끊어졌을떄의 처리이다
*/