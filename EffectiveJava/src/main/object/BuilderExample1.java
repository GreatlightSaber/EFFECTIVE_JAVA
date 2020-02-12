package main.object;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.util.Random;

public class BuilderExample1 {
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
	
	// 예시 1 - 자바빈즈 패턴 )
	/*
	 * 매개변수들은 (기본값이 있다면) 기본값으롤 초기화된다.
	 * 코드가 길어지지만 인스턴스를 만들기 쉽고, 그 결과 더 읽기 쉬운 코드가 되었습니다.
	 * 
	 * */
	private int servingSize1 	= -1;
	private int servings1		= -1;
	private int calories1		= 0;
	private int fat1			= 0;
	private int sodium1			= 0;
	private int carbohydrate1	= 0;
	
	// 예시일뿐 애러는 신경쓰지말자
	public BuilderExample1() {}
	
	// 새터 메서드
	public void setServingSize1(int servingSize1) {
		this.servingSize1 = servingSize1;
	}
	public void setServings1(int servings1) {
		this.servings1 = servings1;
	}
	public void setCalories1(int calories1) {
		this.calories1 = calories1;
	}
	public void setFat1(int fat1) {
		this.fat1 = fat1;
	}
	public void setSodium1(int sodium1) {
		this.sodium1 = sodium1;
	}
	public void setCarbohydrate1(int carbohydrate1) {
		this.carbohydrate1 = carbohydrate1;
	}

	// 자바빈즈 패턴 객체 생성 예시 )
	public void practice1(){
		
		BuilderExample1 builder1 = new BuilderExample1();
		builder1.setServingSize1(240);
		builder1.setServings1(8);
		builder1.setCalories1(100);
		builder1.setSodium1(35);
		builder1.setCarbohydrate1(27);
		
	}
	
	
	// ##############################################################################
	
	
	// 예시 2 - 빌더 패턴 - 유효성 검사 코드는 생략 )
	private final int servingSize2;
	private final int servings2;
	private final int calories2;
	private final int fat2;
	private final int sodium2;
	private final int carbohydrate2;
	
	public static class Builder{
		// 필수 매개변수
		private final int servingSize2;
		private final int servings2;
		
		// 성택 매개변수 - 기본값으로 초기화한다.
		private int calories2		= 0;
		private int fat2			= 0;
		private int sodium2			= 0;
		private int carbohydrate2	= 0;
		
		public Builder(int servingSize2, int servings2) {
			this.servingSize2 = servingSize2;
			this.servings2 = servings2;
		}
		public Builder caloreis(int val) {
			this.calories2 = val;
			return this;
		}
		public Builder fat(int val) {
			this.fat2 = val;
			return this;
		}
		public Builder sodium(int val) {
			this.sodium2 = val;
			return this;
		}
		public Builder carbohydrate(int val) {
			this.carbohydrate2 = val;
			return this;
		}
		
		public BuilderExample1 build() {
			return new BuilderExample1(this);
		}
	}
	
	private BuilderExample1(Builder builder) {
		servingSize2	= builder.servingSize2;
		servings2		= builder.servings2;
		calories2		= builder.calories2;
		fat2			= builder.fat2;
		sodium2			= builder.sodium2;
		carbohydrate2	= builder.carbohydrate2;
	}
	
	// 빌드 패턴 객체 생성 예시 )
	public void practice2() {
		
		BuilderExample1 builder2 = new Builder(240,8).caloreis(100).sodium(35).carbohydrate(27).build();
		
	}
	
	
}
