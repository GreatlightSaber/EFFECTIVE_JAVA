package main.object;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.util.Random;

public class StaticMethod {
	// 생성자 대신 정적 팩터리 메서드를 고려하라
	
	/*
	 * 클라이언트가 클래스의 인스턴스를 생성자, 정적 메서드를 이용해 반환 할 수 있으며 각각 장단점이 존재한다.
	 * 
	 * 
	 *  * 정적 팩터리 매서드의 장점
	 *  	1. 이름을 가질 수 있다.
	 *  		이름만 잘 지으면 반환될 객체의 특성을 쉽게 묘사할 수 있다.
	 *  		생성자로 사용 할 경우에는 문서를 보지 않는 이상 해당 생서자가 어떤기능을 의미하는지 알 수가 없음
	 *  		정적 팩터리 메서드는 이름을 명시 할 수 있기 때문에 이러한 제약에 조금 더 쟈유로울 수 있음
	 *  		한 클래스에 시그니처가 같은 생성자가 여러 개 필요할 것 같으면, 생성자를 정적 팩터리 메서드로 바꾸고 각각의 차이를 잘 드러내는 이름을 짓도록 지향
	 *  
	 *  		예) 	생성자 - BigInteger(int, int, Random)
	 *  		   	정적메서드 - BigInteger.probablePrime
	 *  
	 *   	2. 호출될 때마다 인스턴스를 새로 생성하지 않아도 된다.
	 *   		인스턴스를 미리 만들어 놓거나 새로 생성한 인스턴스를 캐싱하여 재활용 하는 식으로 불필요한 객체 생성을 피할 수 있다.
	 *   		플라이웨이트(flyWeight) 패턴도 이와 같은 기법
	 *   		덕분에 가튼 객체가 자주 요청되는 상황 이라면 성능을 상당히 끌어올려준다.
	 *   		이러한 클래스를 인스턴스 통제 클래스라 한다.
	 *   
	 *   		인스턴스 통제 클래스(instance-controlled) - 인스턴스를 통제하는 이유?
	 *   			- 싱글턴으로 만들 수 있다
	 *   			- 인스턴스화 불가로 만들 수 있다.
	 *   			- 불변 값 클래스에서 동치인 인스턴스가 단 하나뿐임을 보장할 수 있다.
	 *   		
	 *   		예) Boolean.valueOf(boolean) 매서드는 객체를 아예 생성하지 않는다.
	 *   
	 *   	3. 반환 티입의 하위 타입 개체를 반환할 수 있는 능력이 있다.
	 *   	
	 *   
	 *   	4. 입력 매개변수에 따라 매번 다른 클래스의 객체를 반환할 수 있다.
	 *   
	 *   
	 *   	5. 정적 팩터리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다.
	 *   
	 *   * 정적 팩터리 메서드의 단점
	 *   	1. 상속을 하려면 public이나 protected 생성자가 필요하니 정적 팩터리 메서드만 제공하면 하위 클래스를 만들 수 없다.
	 *   
	 *   
	 *   	2. 정책 픽터리 메서드는 프로그래머가 찾기 어렵다.
	 *   		 정적 팩터리 메서드에 흔히 사용하는 명명 방식
	 *   			- from: 매개변수를 하나 받아서 해당 카입의 인스턴스를 반환하는 형변화 메서드
	 *   			- of: 여러 매개변수들 받아 적합한 타입의 인스턴스를 반환하는 집계 메서드
	 *   			- valueOf: from과 of의 더 자세한 버전
	 *   			- instance 혹은 getInstance: (매개변수를 받는다면) 매개변수로 명시한 인스턴스를 반환하지만, 같은 인스턴스임을 보장하지 않는다.
	 *   			- create 혹은 newInstance: instance 혹은 getInstance와 같지만, 매번 새로운 인스턴스를 생성해 반환함을 보장한다.
	 *   			- getType: getInstance와 같으나, 생성할 클래스가 아닌 다른 클래스에 팩터리 메서드를 정의할 때 쓴다. "Type"은 팩터리 메서드가 반환할 객체의 타입이다."
	 *   			- newType: newInstance와 같으나, 생성할 클래스가 아닌 다른 클래스에 팩터리 메서드를 정의할 때 쓴다. "Type"은 팩터리 메서드가 반환할 객체의 타입이다.
	 *    			- Type: getType과 newType의 간결한 버전
	 *    
	 *    
	 *    ※ 핵심 정리
	 *    정적 팩터리 메서드와 public 생성자는 각자의 쓰임새가 있으니 상대적인 장단점을 이해하고 사용하는것이 좋다. 그렇다고 하더라도 정적 팩터리를 사용하는 게 유리한 경우가 더 많으므로 무작정 public 생성자를 제공하던 습관이 있다면 고치자. 
	 * 
	 * 
	 * 
	 * */
	
	public void practice() throws IOException {
		
		
		// 예시 장점 1) 밑에 두개의 명령은 같은 동작을 실행 한다.
		BigInteger temp = new BigInteger(10, 10, new Random());		// 객체 생성
		BigInteger.probablePrime(10, new Random());					// 정적 팩터리 메서드
		
		
		// 예시 단점 2) 정적 팩터리 메서드 명명 방식
		// Set<Rank> faceCards = EnumSet.of(JACK,QUEEN,KING);
		BigInteger prime = BigInteger.valueOf(Integer.MAX_VALUE);
		// StackWalker luke = StackWalker.getInstance(options);
		Object newArray = Array.newInstance((Class<?>) new Object(), 10);
		FileStore fs = Files.getFileStore(null);
		BufferedReader br = Files.newBufferedReader(null);
		// List<Complaint> = litany = Collectionsd.list(legacyLitany);
		
	}
}
