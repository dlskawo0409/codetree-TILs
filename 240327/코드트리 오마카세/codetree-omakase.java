import java.util.*;
import java.io.*;

class People{
	String name;
	int n;
	People(String name, int n){
		this.name = name;
		this.n = n;
	}
}

class Susi{
	String name;
	boolean eat = false;
	
	Susi(String name){
		this.name = name; 
	}
}


class Solution{
	int L;
	int Q;
	People[] people;
	boolean[] set;
	
	public void solution() throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		L = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());
		
		LinkedList<Susi>[] belt = new LinkedList[L];
		for(int i = 0; i<belt.length; i++) {
			belt[i] = new LinkedList<Susi>();
		}
		int point = 0;
		people = new People[L];
		set = new boolean[L];
		int time = 1;
		for(int q = 0; q<Q; q++) {
			st = new StringTokenizer(br.readLine());
			int order = Integer.parseInt(st.nextToken());
			int t = Integer.parseInt(st.nextToken());
			for(int i = 0; i<t- time; i++) {
				point = rotation(point);
//				System.out.println("time : "+ (i+time+1));
//				for(int a = 0; a<L; a++) {
//					for(Susi now : belt[findX(point, a)]) {
//						System.out.print(a+" :");
//						if(!now.eat)
//						System.out.print(" " +now.name+" ");
//					}
//					System.out.println();
//				}
//				
//				System.out.println(time+" "+ point);
				eat(point, belt);

			}
			if(q!=0)
				point = rotation(point);
			
			time = t+1;
			
			if(order == 100) {
				int x = Integer.parseInt(st.nextToken());
				x = findX(point, x);
				belt[x].add(new Susi(st.nextToken()));
				
				eat(point, belt);
//				getPicture(belt);
			}
			else if(order == 200) {
				int x = Integer.parseInt(st.nextToken());
				people[x] = new People(st.nextToken(), Integer.parseInt(st.nextToken()));
				set[x] = true;
				eat(point, belt);
//				getPicture(belt);
			}
			else {
				
				eat(point, belt);
				for(int w =0; w<L; w++) {
					if(set[w]) {
//						System.out.println(people[w].name+" "+ people[w].n);
					}
				}
				getPicture(belt);
				
			}
			
//			System.out.println("time : "+(time-1));
//			for(int i = 0; i<L; i++) {
//				for(Susi now : belt[findX(point, i)]) {
//					System.out.print(i+" :");
//					if(!now.eat)
//					System.out.print(" " +now.name+" ");
//				}
//				System.out.println();
//			}
//			
//			System.out.println(time+" "+ point);
			
		}
	}
	
	protected int findX(int point, int x) {
		return point+ x >= L? (point + x) % L : point + x;
	}
	
	protected int rotation(int point) {
		return point -1 < 0 ? L-1 : point-1;
	}
	
	protected void eat(int point, LinkedList<Susi>[] belt) {
		for(int i = 0; i<L; i++) {
			if(set[i]) { // 자리에 사람이 있으면
				int x = findX(point, i);
//				System.out.println(point+" "+x +" "+i );
				for(Susi now : belt[x]) {
					if(!now.eat && now.name.equals(people[i].name)) {
						now.eat = true;
						people[i].n--;
						if(people[i].n <= 0) { // 다 먹어서 나감
							people[i] = null;
							set[i] = false;
							break;
						}
					}
				}
			}
		}
	}
	
	protected void getPicture(LinkedList<Susi>[] belt) {
		int peopleCount = 0;
		int susiCount = 0;
		for(int i = 0; i<L; i++) {
			if(set[i]) {
				peopleCount++;
			}
			for(Susi now : belt[i]) {
				if(!now.eat) {
					susiCount++;
				}
			}
		}
		System.out.println(peopleCount+" "+susiCount);
	}
}

public class Main {
	public static void main(String[] args) throws Exception{
		new Solution().solution();
	}
}