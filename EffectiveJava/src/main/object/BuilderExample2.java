package main.object;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

public abstract class BuilderExample2 {
	// ----- 1.2 생성자에 매개변수가 많다면 빌더를 고려하라 -----
	
	/*
	 * 정적 팩터리 와 생성자는 공통적으로 선택적 매개변수가 많을 때 적절히 대응하기가 어렵다는 문제점이 있다.
	 * 이에 따른 점층적 생성자 패턴을 쓸 수 있지만, 매개변수 개수가 많아지면 클라이언트 코드를 작성하거나 읽기 어렵다.
	 * 
	 * 
	 *  * 자바 빈즈 패턴(JavaBeans Pattern) 사용 - 생성자로 객체를 만든 후 setter 메서드를 호출 해  원하는 배겨변수의 값을 설정
	 *  * 자바 빈즈 패턴에서는 객체 하나를 만들려면 메서드를 여러 개 호출해야 하고, 객체가 완전히 생성되기 전까지 일관성이(consistency) 무너진 상태에 놓이게 된다. 때문에 클래스를 불변으로 만들 수 없다 
	 *
	 *    
	 *  * 빌더 객체(Builder pattern)를 이용해 클라이언트는 필요한 객체를 직접 만드는 대신, 필수 매개변수만으로 생성자를 호출해 빌더 객체를 얻는다.
	 *  	그 다음에 빌더객채가 제공하는 세터 메서드들로 원하는 선택 매개변수들을 설정,
	 *  	마지막으로 매개변수가 없는 build 메서드를 호출해 원하는 객체를 얻는다
	 *  * 빌더 패턴은 (파이썬과 스칼라에 있는) 명명된 선택적 매개변수(named optional parameters)를 흉내 낸 것이다.
	 *  * 밸더 패턴은 계층적으로 설계된 클래스와 함께 쓰기에 좋다. (BuilderExample2.java 에 계속....)
	 *   
	 *    ※ 핵심 정리
	 * 
	 * 
	 * 
	 * */
	
	// 예시 3 -  계층적으로 설계된 클래스 사용 예
	/*
	 * 매개변수들은 (기본값이 있다면) 기본값으롤 초기화된다.
	 * 코드가 길어지지만 인스턴스를 만들기 쉽고, 그 결과 더 읽기 쉬운 코드가 되었습니다.
	 * 
	 * */
	
	
	public enum Topping {HAM, MUSHROOM, ONION, PEPPER, SAUSAGE};
	final Set<Topping> toppings;
	
	abstract static class Builder<T extends Builder<T>>{
		EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);
		
		public T addTopping(Topping topping) {
			toppings.add(Objects.requireNonNull(topping));
			return self();
		}
		
		abstract BuilderExample2 build();
		
		// 하위 클래스는 이 메서드를 재정의(overriding) 하여
		// "this"를 반환하도록 한다.
		protected abstract T self();
		
	}
	
	BuilderExample2(Builder<?> builder){
		toppings = builder.toppings.clone();
	}
	
}
